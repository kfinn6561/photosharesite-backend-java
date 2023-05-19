package com.photosharesite.backend.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jdbi.v3.core.mapper.reflect.ColumnName;
import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;

public class LookupUserResponse {
    @JsonProperty("UserID")
    @ColumnName("UserID")
    private int UserID;

    @JsonCreator
    @JdbiConstructor
    public LookupUserResponse() {
        // Jackson deserialization
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }
}
