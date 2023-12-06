package com.photosharesite.backend.db.deletefile;

import com.google.inject.Inject;
import org.jdbi.v3.core.Jdbi;

public class DeleteFileAccess {
  public static final String DELETE_FILE_PROC_NAME = "DeleteFile";
  private final Jdbi jdbi;

  @Inject
  public DeleteFileAccess(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  public void DeleteFile(int fileID) {
    jdbi.useHandle(
        handle ->
            handle
                .createCall("CALL " + DELETE_FILE_PROC_NAME + "(:FileID)")
                .bind("FileID", fileID)
                .invoke());
  }
}
