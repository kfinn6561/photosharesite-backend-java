package com.photosharesite.backend.db.insertfile;

import org.jdbi.v3.core.Jdbi;

public class InsertFileAccess {
  public static final String INSERT_FILE_PROC_NAME = "InsertFile";
  private final Jdbi jdbi;

  public InsertFileAccess(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  public void InsertFile(String FileName, String URL, int OwnerID) {
    jdbi.useHandle(
        handle ->
            handle
                .createCall("CALL " + INSERT_FILE_PROC_NAME + "(:FileName, :URL, :OwnerID)")
                .bind("FileName", FileName)
                .bind("URL", URL)
                .bind("OwnerID", OwnerID)
                .invoke());
  }
}
