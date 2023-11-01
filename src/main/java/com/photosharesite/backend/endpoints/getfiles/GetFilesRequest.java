package com.photosharesite.backend.endpoints.getfiles;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GetFilesRequest {
    private final String IPAddress;

    public GetFilesRequest(@JsonProperty("IPAddress") String IPAddress) {
        this.IPAddress = IPAddress;
    }
}
