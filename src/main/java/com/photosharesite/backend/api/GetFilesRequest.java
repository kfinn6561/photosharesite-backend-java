package com.photosharesite.backend.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class GetFilesRequest {
    @Getter
    @Setter
    private Integer UserID;

    public GetFilesRequest(@JsonProperty("UserID") Integer UserID) {
        this.UserID = UserID;
    }
}
