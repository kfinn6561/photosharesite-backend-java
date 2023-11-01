package com.photosharesite.backend.db.deletefile;

import org.jdbi.v3.core.Jdbi;

public class DeleteFileAccess {
    public static final String DELETE_FILE_PROC_NAME = "DeleteFile";
    private final Jdbi jdbi;

    public DeleteFileAccess(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public boolean DeleteFile(String fileName){
        return true;
    }
}
