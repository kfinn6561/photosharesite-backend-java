package com.photosharesite.backend.endpoints.uploadfile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFileResponse {
    private boolean Success;
    private String Reason;

    public UploadFileResponse(){
        //Jackson Deserialisation
    }
    public UploadFileResponse(boolean success, String reason) {
        Success = success;
        Reason = reason;
    }
}
