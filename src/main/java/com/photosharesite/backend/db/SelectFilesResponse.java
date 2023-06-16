package com.photosharesite.backend.db;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectFilesResponse {
    private int FileID;
    private String URL;
    private String FileName;
    private String IPAddress;

    public SelectFilesResponse(){
        // Jackson deserialisation
    }
}
