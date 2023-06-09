package com.photosharesite.backend.db.selectfiles;

import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.stream.Collectors;

public class SelectFilesAccess {
    public static final String SELECT_FILES_PROC_NAME = "SelectFiles";
    private final Jdbi jdbi;

    public SelectFilesAccess(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<SelectFilesResponse> SelectFiles() {
        return  jdbi.withHandle(handle -> handle.createQuery("CALL " + SELECT_FILES_PROC_NAME + "()")
                .mapToBean(SelectFilesResponse.class)
                .collect(Collectors.toList())
        );
    }
}
