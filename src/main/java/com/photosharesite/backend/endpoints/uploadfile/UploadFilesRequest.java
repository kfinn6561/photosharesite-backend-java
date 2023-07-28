package com.photosharesite.backend.endpoints.uploadfile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFilesRequest {
    private int UserId;
    private String FileName;

    public UploadFilesRequest(@JsonProperty("UserID") int userId, @JsonProperty("FileName") String fileName) {
        UserId = userId;
        FileName = fileName;
    }
}
