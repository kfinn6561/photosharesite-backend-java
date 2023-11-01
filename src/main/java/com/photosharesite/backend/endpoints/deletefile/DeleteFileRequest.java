package com.photosharesite.backend.endpoints.deletefile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteFileRequest {
    @JsonProperty("IPAddress")
    private String IPAddress;

    public DeleteFileRequest(){
        //Jackson Deserialisation
    }

    public DeleteFileRequest(String IPAddress) {
        this.IPAddress = IPAddress;
    }
}
