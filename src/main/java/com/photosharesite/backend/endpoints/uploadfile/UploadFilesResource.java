package com.photosharesite.backend.endpoints.uploadfile;

import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/files/upload")
@Api(value = "Upload File")
@Produces(MediaType.APPLICATION_JSON)
public class UploadFilesResource {
    @POST
    @ApiOperation(value = "Upload File", response = UploadFilesResponse.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public UploadFilesResponse getAllFiles(@Valid UploadFilesRequest request) {
        return new UploadFilesResponse(true);
    }
}
