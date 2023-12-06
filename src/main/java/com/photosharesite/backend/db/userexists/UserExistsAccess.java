package com.photosharesite.backend.db.userexists;

import com.google.inject.Inject;
import org.jdbi.v3.core.Jdbi;

public class UserExistsAccess {
  public static final String USER_EXISTS_PROC_NAME = "UserExists";
  private final Jdbi jdbi;

  @Inject
  public UserExistsAccess(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  public boolean UserExists(int UserID) {
    UserExistsResponse response =
        jdbi.withHandle(
            handle ->
                handle
                    .createQuery("CALL " + USER_EXISTS_PROC_NAME + "(:UserID)")
                    .bind("UserID", UserID)
                    .mapToBean(UserExistsResponse.class)
                    .one());
    return response.getIDExists() == 1;
  }
}
