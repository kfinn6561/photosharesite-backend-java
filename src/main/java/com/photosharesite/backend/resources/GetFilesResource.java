package com.photosharesite.backend.resources;

import org.jdbi.v3.core.Jdbi;

public class GetFilesResource {
    public static final String lookupUserProcName = "InsertOrSelectUser";
    private final Jdbi jdbi;

    public GetFilesResource(Jdbi jdbi) {
        this.jdbi=jdbi;
    }
}
