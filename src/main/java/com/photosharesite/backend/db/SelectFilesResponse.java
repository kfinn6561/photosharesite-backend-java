package com.photosharesite.backend.db;

import lombok.Getter;
import lombok.Setter;

public class SelectFilesResponse {
    @Getter
    @Setter
    private int FileID;

    @Getter
    @Setter
    private String URL;

    @Getter
    @Setter
    private String FileName;

    @Getter
    @Setter
    private String IPAddress;

    public SelectFilesResponse(){
        // Jackson deserialisation
    }
}
