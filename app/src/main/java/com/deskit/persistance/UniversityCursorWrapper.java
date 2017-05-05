package com.deskit.persistance;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.deskit.model.City;
import com.deskit.model.University;

class UniversityCursorWrapper extends CursorWrapper {

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    UniversityCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    University getUniversity() {
        int id = getInt(getColumnIndex(DatabaseSchema.UniversityTable.Columns.ID));
        String name = getString(getColumnIndex(DatabaseSchema.UniversityTable.Columns.UNIVERSITY_NAME));
        int cityId = getInt(getColumnIndex(DatabaseSchema.UniversityTable.Columns.CITY_ID));
        String cityName = getString(getColumnIndex(DatabaseSchema.CityTable.Columns.CITY_NAME));

        City city = new City(cityId, cityName);

        return new University(id, name, city);
    }
}
