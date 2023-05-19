package com.photosharesite.backend.api;

import lombok.Getter;
import lombok.Setter;

public class LookupUserResponse {
    @Getter
    @Setter
    private int UserID;

    public LookupUserResponse() {
        // Jackson deserialization
    }
}
