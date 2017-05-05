package com.deskit.persistance;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.deskit.model.City;
import com.deskit.model.Faculty;
import com.deskit.model.University;

public class FacultyCursorWrapper extends CursorWrapper {

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public FacultyCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Faculty getFaculty() {
        int id = getInt(getColumnIndex(DatabaseSchema.FacultyTable.Columns.ID));
        String name = getString(getColumnIndex(DatabaseSchema.FacultyTable.Columns.FACULTY_NAME));

        return new Faculty(id, name);
    }

    public Faculty getFacultyFullData() {
        int id = getInt(getColumnIndex(DatabaseSchema.FacultyTable.Columns.ID));
        String name = getString(getColumnIndex(DatabaseSchema.FacultyTable.Columns.FACULTY_NAME));
        int universityId = getInt(getColumnIndex(DatabaseSchema.UniversityTable.Columns.ID));
        String universityName = getString(getColumnIndex(DatabaseSchema.UniversityTable.Columns.UNIVERSITY_NAME));
        int cityId = getInt(getColumnIndex(DatabaseSchema.CityTable.Columns.ID));
        String cityName = getString(getColumnIndex(DatabaseSchema.CityTable.Columns.CITY_NAME));

        City city = new City(cityId, cityName);
        University university = new University(universityId, universityName, city);

        return new Faculty(id, name, university);
    }
}
