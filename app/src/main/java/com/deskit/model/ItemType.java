package com.deskit.model;

import android.support.annotation.IntDef;

import static com.deskit.model.ItemType.FACULTY;
import static com.deskit.model.ItemType.RESOURCE;
import static com.deskit.model.ItemType.SUBJECT;
import static com.deskit.model.ItemType.UNIVERSITY;

@IntDef({UNIVERSITY, FACULTY, SUBJECT, RESOURCE})
public @interface ItemType {

    int UNIVERSITY = 1;

    int FACULTY = 2;

    int SUBJECT = 3;

    int RESOURCE = 4;
}
