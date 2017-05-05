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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    //The Android's default system path of your application database.
    private static final String DB_PATH = "/databases";

    private static final String DB_NAME = "database.db";

    private static final String GET_UNIVERSITIES_QUERY = "SELECT * FROM "
            + UniversityTable.NAME + " u" + " LEFT JOIN " + CityTable.NAME + " c"
            + " ON u." + UniversityTable.Columns.CITY_ID + " = c." + CityTable.Columns.ID;

    private static final String GET_FACULTIES_QUERY = "SELECT * FROM "
            + FacultyTable.NAME + " f" + " LEFT JOIN " + UniversityTable.NAME + " u"
            + " ON f." + FacultyTable.Columns.UNIVERSITY_ID + " = u." + UniversityTable.Columns.ID
            + " LEFT JOIN " + CityTable.NAME + " c"
            + " ON u." + UniversityTable.Columns.CITY_ID + " = " + " c." + CityTable.Columns.ID;

    private static DatabaseHelper instance;

    private SQLiteDatabase database;

    private final Context context;

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public boolean createDataBase(OnSuccessListener<FileDownloadTask.TaskSnapshot> successListener,
                                  OnFailureListener failureListener) throws IOException {

        boolean dbExist = checkDataBase();
        if (!dbExist) {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            downloadDatabase(successListener, failureListener);
        }
        return dbExist;
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = context.getDatabasePath(DB_NAME).getPath();
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
            return true;
        } catch (SQLiteException e) {
            Log.d(TAG, "checkDatabase - SQLiteException: the database doesn't exist yet");
            return false;
        } finally {
            if (checkDB != null) {
                checkDB.close();
            }
        }
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void downloadDatabase(OnSuccessListener<FileDownloadTask.TaskSnapshot> successListener, OnFailureListener failureListener) {
        File databaseFile = context.getDatabasePath(DB_NAME);

        StorageReference httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(UrlConstants.DATABASE_URL);
        httpsReference
                .getFile(databaseFile)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }

    private void openDataBase() throws SQLException {
        String myPath = context.getDatabasePath(DB_NAME).getPath();
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

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return database.query(....)" so it'd be easy
    // to you to create adapters for your views.

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

    private UniversityCursorWrapper queryUniversities() {
        Cursor cursor = database.rawQuery(GET_UNIVERSITIES_QUERY, null);
        return new UniversityCursorWrapper(cursor);
    }

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

    public List<Faculty> getFaculties(University university) {
        openDataBase();

        List<Faculty> facultyList = new ArrayList<>();

        String whereClause = FacultyTable.Columns.UNIVERSITY_ID + " = ?";

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

    private FacultyCursorWrapper queryFaculties() {
        Cursor cursor = database.rawQuery(GET_FACULTIES_QUERY, null);
        return new FacultyCursorWrapper(cursor);
    }
}