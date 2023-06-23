package com.photosharesite.backend.db.insertorselectuser;

import com.photosharesite.backend.endpoints.lookupuser.LookupUserResponse;
import org.jdbi.v3.core.Jdbi;

public class InsertOrSelectUserAccess {
    public static final String INSERT_OR_SELECT_USER_PROC_NAME = "InsertOrSelectUser";
    private final Jdbi jdbi;

    public InsertOrSelectUserAccess(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public int InsertOrSelectUser(String IPAddress) {
        return jdbi.withHandle(handle -> handle.createQuery("CALL " + INSERT_OR_SELECT_USER_PROC_NAME + "(:IPAddress)")
                .bind("IPAddress", IPAddress)
                .mapToBean(LookupUserResponse.class)
                .one()
                .getUserID()
        );
    }

}
