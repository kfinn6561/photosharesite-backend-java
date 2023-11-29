package com.photosharesite.backend.db.userexists;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserExistsResponse {
  private int IDExists;

  public UserExistsResponse() {
    // Jackson deserialisation
  }

  public UserExistsResponse(int IDExists) {
    this.IDExists = IDExists;
  }
}
