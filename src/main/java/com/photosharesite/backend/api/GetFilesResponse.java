package com.photosharesite.backend.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetFilesResponse {
    private int FileID;
    private String DownloadURL;
    private String FileName;
    private boolean IsModifyable;

    public GetFilesResponse(){
        //Jackson Deserialisation
    }

    public GetFilesResponse(int fileID, String downloadURL, String fileName, boolean isModifyable) {
        FileID = fileID;
        DownloadURL = downloadURL;
        FileName = fileName;
        IsModifyable = isModifyable;
    }
}
