package com.photosharesite.backend.db.getfiledetails;

import com.google.inject.Inject;
import java.util.Optional;
import org.jdbi.v3.core.Jdbi;

public class GetFileDetailsAccess {
  public static final String GET_FILE_DETAILS_PROC_NAME = "GetFileDetails";
  private final Jdbi jdbi;

  @Inject
  public GetFileDetailsAccess(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  public Optional<GetFileDetailsResponse> GetFileDetails(int FileID) {
    return jdbi.withHandle(
        handle ->
            handle
                .createQuery("CALL " + GET_FILE_DETAILS_PROC_NAME + "(:FileID)")
                .bind("FileID", FileID)
                .mapToBean(GetFileDetailsResponse.class)
                .findOne());
  }
}
