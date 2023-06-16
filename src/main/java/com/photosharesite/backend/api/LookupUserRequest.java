package com.photosharesite.backend.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LookupUserRequest {
    private String IPAddress;

    public LookupUserRequest(@JsonProperty("IPAddress") String IPAddress) {
        this.IPAddress = IPAddress;
    }
}
