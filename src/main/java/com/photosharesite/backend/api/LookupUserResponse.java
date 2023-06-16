package com.photosharesite.backend.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LookupUserResponse {
    private int UserID;

    public LookupUserResponse() {
        // Jackson deserialization
    }
}
