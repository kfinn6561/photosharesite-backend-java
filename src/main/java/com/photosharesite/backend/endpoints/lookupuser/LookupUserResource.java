package com.photosharesite.backend.endpoints.lookupuser;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.photosharesite.backend.db.insertorselectuser.InsertOrSelectUserAccess;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user/lookup")
@Produces(MediaType.APPLICATION_JSON)
public class LookupUserResource {
  private final InsertOrSelectUserAccess dao;

  @Inject
  public LookupUserResource(InsertOrSelectUserAccess dao) {
    this.dao = dao;
  }

  @POST
  @Operation(
      description =
          "Return the UserID associated with an IP address. Create a new entry if this IP address has not been used before.")
  @Consumes(MediaType.APPLICATION_JSON)
  @Timed
  public LookupUserResponse lookupUser(@Valid LookupUserRequest request) {
    int userID = dao.InsertOrSelectUser(request.getIPAddress());
    return new LookupUserResponse(userID);
  }
}
