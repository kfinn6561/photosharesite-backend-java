package com.photosharesite.backend.resources;

import com.codahale.metrics.annotation.Timed;
import com.photosharesite.backend.api.LookupUserRequest;
import com.photosharesite.backend.api.LookupUserResponse;
import io.swagger.annotations.Api;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user/lookup")
@Api(value = "Lookup User")
@Produces(MediaType.APPLICATION_JSON)
public class LookupUserResource {
    public static final String lookupUserProcName = "InsertOrSelectUser";
    private final Jdbi jdbi;

    public LookupUserResource(Jdbi jdbi) {
        this.jdbi=jdbi;
    }

    @POST
    @Timed
    public LookupUserResponse lookupUser(LookupUserRequest request) {
        return jdbi.withHandle(handle ->
            handle.createQuery("CALL "+lookupUserProcName+"(:IPAddress)")
                    .bind("IPAddress",request.getIPAddress())
                    .mapTo(LookupUserResponse.class)
                    .one()
        );
    }
}