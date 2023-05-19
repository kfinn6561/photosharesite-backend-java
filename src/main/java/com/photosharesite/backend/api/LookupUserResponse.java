package com.photosharesite.backend.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.jdbi.v3.core.mapper.reflect.ColumnName;
import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;

public class LookupUserResponse {
    @Getter
    @Setter
    @JsonProperty("UserID")
    private int UserID;

    public LookupUserResponse() {
        // Jackson deserialization
    }

    @JsonCreator
    @JdbiConstructor
    public LookupUserResponse(@ColumnName("UserID") int UserID) {
        this.UserID = UserID;
    }
}
