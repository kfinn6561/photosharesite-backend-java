package com.photosharesite.backend.endpoints.uploadfile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFileResponse {
    private boolean Success;

    public UploadFileResponse(){
        //Jackson Deserialisation
    }
    public UploadFileResponse(boolean success) {
        Success = success;
    }
}
