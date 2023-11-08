package com.photosharesite.backend.db.getfiledetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetFileDetailsResponse {
    private String URL;
    private String FileName;
    private int OwnerID;

    public GetFileDetailsResponse() {
        // Jackson deserialisation
    }

    public GetFileDetailsResponse(String URL, String fileName, int ownerID) {
        this.URL = URL;
        FileName = fileName;
        OwnerID = ownerID;
    }
}
