package com.deskit.persistance;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.deskit.model.Faculty;
import com.deskit.model.University;
import com.deskit.persistance.DatabaseSchema.CityTable;
import com.deskit.persistance.DatabaseSchema.FacultyTable;
import com.deskit.persistance.DatabaseSchema.UniversityTable;
import com.deskit.utils.UrlConstants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    /**
     * Android's default name of a application database folder.
     */
    private static final String DB_FOLDER_NAME = "/databases";

    /**
     * The name of the application's database.
     */
    private static final String DB_FILE_NAME = "database.db";

    /**
     * Part of a query that represents the SELECT action.
     */
    private static final String SELECT = " SELECT ";

    /**
     * Part of a query that represents the FROM action.
     */
    private static final String FROM = " FROM ";

    /**
     * Part of a query that represents the LEFT JOIN action.
     */
    private static final String LEFT_JOIN = " LEFT JOIN ";

    /**
     * Part of a query that represents the ON action used in a JOIN action.
     */
    private static final String ON = " ON ";

    /**
     * Part of a query that represents the = action.
     */
    private static final String EQUALS = " = ";

    /**
     * Part of a query that represents a comma: , used to separate items of same category.
     * E.g. separating a list of column names used in a SELECT action: SELECT id, name, type.
     */
    private static final String COMMA = ", ";

    /**
     * Part of a query that represents a dot: . used in parent of item specification.
     * E.g. defining a column name of a certain table: table.columnName.
     */
    private static final String DOT = ".";

    /**
     * Part of a query that represents the *, used to represent "all" items of a certain type.
     * E.g. selecting all columns in a SELECT action: SELECT * FROM table.
     */
    private static final String ALL = " * ";

    /**
     * Part of a query that represents the question mark: ? used as place holder for an actual
     * argument that will be given as parameter.
     */
    private static final String QUESTION_MARK = " ? ";

    /**
     * Query used to get all universities in database.
     * <p>
     * This query left joins {@link UniversityTable} with {@link CityTable} to get a list of
     * {@link University} objects that contain a valid {@link com.deskit.model.City} field.
     * </p>
     * The query looks like this:
     * <p>
     * <code> SELECT universities.id, university_name, city_id
     * FROM universities LEFT JOIN cities ON city_id = cities.id </code>
     * </p>
     */
    private static final String GET_UNIVERSITIES_QUERY = SELECT + UniversityTable.NAME + DOT
            + UniversityTable.Columns.ID + COMMA + UniversityTable.Columns.UNIVERSITY_NAME + COMMA
            + UniversityTable.Columns.CITY_ID + COMMA + CityTable.NAME + DOT
            + CityTable.Columns.CITY_NAME + FROM + UniversityTable.NAME + LEFT_JOIN
            + CityTable.NAME + ON + UniversityTable.Columns.CITY_ID + EQUALS + CityTable.NAME
            + DOT + CityTable.Columns.ID;
    /**
     * Query used to get all faculties in database from a certain university.
     * <p>
     * This query left join three tables: {@link UniversityTable}, {@link FacultyTable} and
     * {@link CityTable} to get a list of {@link Faculty} objects that have a valid
     * {@link University} field that in turn has a valid {@link com.deskit.model.City} filed.
     * </p>
     * <p>The query looks like this:</p>
     * <code>SELECT * FROM faculties LEFT JOIN universities ON university_id = universities.id
     * LEFT JOIN cities ON city_id = cities.id</code>
     */
    private static final String GET_FACULTIES_QUERY = SELECT + ALL + FROM + FacultyTable.NAME
            + LEFT_JOIN + UniversityTable.NAME + ON + FacultyTable.Columns.UNIVERSITY_ID + EQUALS
            + UniversityTable.NAME + DOT + UniversityTable.Columns.ID + LEFT_JOIN + CityTable.NAME
            + ON + UniversityTable.Columns.CITY_ID + EQUALS + CityTable.NAME + DOT + CityTable.Columns.ID;

    /**
     * Static instance of a the DatabaseHelper class. Used for implementing the Singleton pattern.
     * Only one instance of this class can be created per run.
     */
    private static DatabaseHelper instance;

    /**
     * Object that represents the application's database.
     */
    private SQLiteDatabase database;

    /**
     * Application's context.
     */
    private final Context context;

    /**
     * Gets the only instance of this class. This instance will be used through the whole app's
     * lifecycle.
     *
     * @param context The application context must be provided, because this is the only context
     *                that lives through the app's lifecycle. Providing an activity or an activity's
     *                context can cause exceptions to be thrown when operating on the database.
     * @return the unique instance of the {@link DatabaseHelper} class.
     */
    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    /**
     * Takes and keeps a reference of the passed context in order to access to the application
     * assets and resources.
     *
     * @param context The application context must be provided, because this is the only context
     *                that lives through the app's lifecycle. Providing an activity or an activity's
     *                context can cause exceptions to be thrown when operating on the database.
     */
    private DatabaseHelper(Context context) {
        super(context, DB_FILE_NAME, null, 1);
        this.context = context;
    }

    /**
     * Creates an empty database into the default system path of the application's database folder
     * and rewrites it with a database downloaded from the server side if the database is not
     * already created.
     *
     * @param successListener {@link OnSuccessListener} that listens for successful database
     *                        download operation.
     * @param failureListener {@link OnFailureListener} that listens for failed database
     *                        download operation.
     * @return <code>true</code> if the database exists or was created with success,
     * <code>false</code> otherwise.
     */
    public boolean createDataBase(OnSuccessListener<FileDownloadTask.TaskSnapshot> successListener,
                                  OnFailureListener failureListener) {

        boolean dbExist = checkDataBase();
        if (!dbExist) {
            this.getReadableDatabase();
            downloadDatabase(successListener, failureListener);
        }
        return dbExist;
    }

    /**
     * Checks if the database already exists to avoid re-copying the file each time the application
     * is opened.
     *
     * @return <code>true</code> if it exists, <code>false</code> if it doesn't.
     */
    private boolean checkDataBase() {
        String myPath = context.getDatabasePath(DB_FILE_NAME).getPath();
        try (SQLiteDatabase checkDB =
                     SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)) {
            return true;
        } catch (SQLiteException e) {
            Log.d(TAG, "checkDatabase - SQLiteException: the database doesn't exist yet");
            return false;
        }
    }

    /**
     * Downloads the database from server side and if the download is successful it copies the
     * database to the just created empty database in the system folder, from where it can be
     * accessed and handled. This is done using the firebase getFile API.
     *
     * @param successListener {@link OnSuccessListener} that listens for successful database
     *                        download operation.
     * @param failureListener {@link OnFailureListener} that listens for failed database
     *                        download operation.
     */
    private void downloadDatabase(OnSuccessListener<FileDownloadTask.TaskSnapshot> successListener, OnFailureListener failureListener) {
        File databaseFile = context.getDatabasePath(DB_FILE_NAME);

        StorageReference httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(UrlConstants.DATABASE_URL);
        httpsReference
                .getFile(databaseFile)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }

    /**
     * Opens the application's database in {@link SQLiteDatabase#OPEN_READONLY} mode. The newly
     * opened database is stored in the {@link DatabaseHelper#database} field.
     *
     * @throws SQLException if the database cannot be opened.
     */
    private void openDataBase() throws SQLException {
        String myPath = context.getDatabasePath(DB_FILE_NAME).getPath();
        database = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Gets the list of all universities in the database.
     *
     * @return a list of {@link University} objects or an empty list if there are no universities
     * or an error occurred.
     */
    public List<University> getUniversities() {
        openDataBase();

        List<University> universityList = new ArrayList<>();

        try (UniversityCursorWrapper cursor = queryUniversities()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                University university = cursor.getUniversity();
                universityList.add(university);
                cursor.moveToNext();
            }
        }

        close();

        return universityList;
    }

    /**
     * Queries the database for the universities using
     * {@link DatabaseHelper#GET_UNIVERSITIES_QUERY} query.
     *
     * @return {@link UniversityCursorWrapper}.
     */
    private UniversityCursorWrapper queryUniversities() {
        Cursor cursor = database.rawQuery(GET_UNIVERSITIES_QUERY, null);
        return new UniversityCursorWrapper(cursor);
    }

    /**
     * Gets the list of all faculties in the database.
     *
     * @return list of {@link Faculty} objects or an empty list if there are no faculties or an
     * error occurred.
     */
    public List<Faculty> getFaculties() {
        openDataBase();

        List<Faculty> facultyList = new ArrayList<>();

        try (FacultyCursorWrapper cursor = queryFaculties()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                facultyList.add(cursor.getFacultyFullData());
                cursor.moveToNext();
            }
        }

        close();

        return facultyList;
    }

    /**
     * Gets the list of all faculties of a certain university.
     *
     * @param university {@link University} for which the faculties will be retrieved.
     * @return a list of {@link Faculty} objects or an empty list if no faculties are found or if
     * an error occurred.
     */
    public List<Faculty> getFaculties(University university) {
        openDataBase();

        List<Faculty> facultyList = new ArrayList<>();

        String whereClause = FacultyTable.Columns.UNIVERSITY_ID + EQUALS + QUESTION_MARK;

        try (FacultyCursorWrapper cursor =
                     queryFaculties(whereClause, new String[]{String.valueOf(university.getId())})) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Faculty faculty = cursor.getFaculty();
                faculty.setUniversity(university);
                facultyList.add(faculty);
                cursor.moveToNext();
            }
        }

        close();

        return facultyList;
    }

    /**
     * Queries the database for faculties.
     *
     * @param whereClause A filter declaring which rows to return, formatted as an
     *                    SQL WHERE clause (excluding the WHERE itself). Passing null
     *                    will return all rows for the given table.
     * @param whereArgs   You may include ?s in selection, which will be
     *                    replaced by the values from selectionArgs, in order that they
     *                    appear in the selection. The values will be bound as Strings.
     * @return {@link FacultyCursorWrapper}
     */
    private FacultyCursorWrapper queryFaculties(String whereClause, String[] whereArgs) {
        Cursor cursor = database.query(
                FacultyTable.NAME,
                null, // all columns
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new FacultyCursorWrapper(cursor);
    }

    /**
     * Queries the database for faculties using {@link DatabaseHelper#GET_FACULTIES_QUERY} query.
     *
     * @return {@link FacultyCursorWrapper}.
     */
    private FacultyCursorWrapper queryFaculties() {
        Cursor cursor = database.rawQuery(GET_FACULTIES_QUERY, null);
        return new FacultyCursorWrapper(cursor);
    }
}