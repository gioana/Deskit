package com.deskit.controller;

import com.deskit.model.Faculty;
import com.deskit.model.Subject;
import com.deskit.model.University;

public interface ItemsController {

    University getCurrentUniversity();

    Faculty getCurrentFaculty();

    Subject getCurrentSubject();
}
