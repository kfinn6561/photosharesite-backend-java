package com.photosharesite.backend.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class GetFilesRequest {
    @Getter
    @Setter
    private String IPAddress;

    public GetFilesRequest(@JsonProperty("IPAddress") String IPAddress) {
        this.IPAddress = IPAddress;
    }
}
