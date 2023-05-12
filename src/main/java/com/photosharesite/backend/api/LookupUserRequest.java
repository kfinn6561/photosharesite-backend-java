package com.photosharesite.backend.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class LookupUserRequest {
    @Getter
    @Setter
    @JsonProperty("IPAddress")
    private String IPAddress;

    public LookupUserRequest() {
        // Jackson deserialization
    }

    public LookupUserRequest(String IPAddress) {
        this.IPAddress = IPAddress;
    }
}
