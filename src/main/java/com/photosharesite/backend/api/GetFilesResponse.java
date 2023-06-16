package com.photosharesite.backend.api;

import lombok.Getter;
import lombok.Setter;

public class GetFilesResponse {
    @Getter
    @Setter
    private int FileID;

    @Getter
    @Setter
    private String DownloadURL;

    @Getter
    @Setter
    private String FileName;

    @Getter
    @Setter
    private boolean IsModifyable;

    public GetFilesResponse(){
        //Jackson Deserialisation
    }
}
