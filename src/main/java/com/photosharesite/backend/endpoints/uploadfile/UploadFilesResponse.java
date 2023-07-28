package com.photosharesite.backend.endpoints.uploadfile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFilesResponse {
    private boolean Success;

    public UploadFilesResponse(){
        //Jackson Deserialisation
    }
    public UploadFilesResponse(boolean success) {
        Success = success;
    }
}
