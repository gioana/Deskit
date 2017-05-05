package com.deskit.model;

import android.support.annotation.IntDef;

import static com.deskit.model.FileType.DOC;
import static com.deskit.model.FileType.JPEG;
import static com.deskit.model.FileType.PDF;

@IntDef({JPEG, PDF, DOC})
public @interface FileType {

    int JPEG = 0;

    int PDF = 1;

    int DOC = 2;
}
