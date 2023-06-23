package com.photosharesite.backend.endpoints.lookupuser;

import com.codahale.metrics.annotation.Timed;
import com.photosharesite.backend.db.insertorselectuser.InsertOrSelectUserAccess;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user/lookup")
@Api(value = "Lookup User")
@Produces(MediaType.APPLICATION_JSON)
public class LookupUserResource {
    private final InsertOrSelectUserAccess dao;

    public LookupUserResource(InsertOrSelectUserAccess dao) {
        this.dao = dao;
    }


    @POST
    @ApiOperation(value = "Lookup User", response = LookupUserResponse.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public LookupUserResponse lookupUser(@Valid LookupUserRequest request) {
        int userID =  dao.InsertOrSelectUser(request.getIPAddress());
        return new LookupUserResponse(userID);
    }
}