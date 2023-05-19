package com.photosharesite.backend.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class LookupUserRequest {
    @Getter
    @Setter
    private String IPAddress;

    public LookupUserRequest(@JsonProperty("IPAddress") String IPAddress) {
        this.IPAddress = IPAddress;
    }
}
