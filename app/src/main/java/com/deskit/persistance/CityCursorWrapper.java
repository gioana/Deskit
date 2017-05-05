package com.deskit.persistance;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.deskit.model.City;

public class CityCursorWrapper extends CursorWrapper {

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CityCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public City getCity() {
        int id = getInt(getColumnIndex(DatabaseSchema.CityTable.Columns.ID));
        String name = getString(getColumnIndex(DatabaseSchema.CityTable.Columns.CITY_NAME));

        return new City(id, name);
    }
}
