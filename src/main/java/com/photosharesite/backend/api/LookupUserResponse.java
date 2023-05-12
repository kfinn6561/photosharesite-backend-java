package com.photosharesite.backend.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class LookupUserResponse {
    @Getter
    @Setter
    @JsonProperty("UserID")
    private int UserID;

    public LookupUserResponse() {
        // Jackson deserialization
    }

    public LookupUserResponse(int UserID) {
        this.UserID = UserID;
    }
}
