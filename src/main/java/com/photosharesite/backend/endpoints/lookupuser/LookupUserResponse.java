package com.photosharesite.backend.endpoints.lookupuser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LookupUserResponse {
  private int UserID;

  public LookupUserResponse() {
    // Jackson deserialization
  }

  public LookupUserResponse(int userID) {
    UserID = userID;
  }
}
