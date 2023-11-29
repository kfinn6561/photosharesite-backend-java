package com.photosharesite.backend.db.selectfiles;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectFilesResponse {
  private int FileID;
  private String URL;
  private String FileName;
  private String IPAddress;

  public SelectFilesResponse() {
    // Jackson deserialisation
  }

  public SelectFilesResponse(int fileID, String fileName, String URL, String IPAddress) {
    FileID = fileID;
    this.URL = URL;
    FileName = fileName;
    this.IPAddress = IPAddress;
  }
}
