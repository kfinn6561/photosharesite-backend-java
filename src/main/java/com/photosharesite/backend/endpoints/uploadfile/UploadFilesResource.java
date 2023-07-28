package com.photosharesite.backend.endpoints.uploadfile;

import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import software.amazon.awssdk.services.s3.S3Client;

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
    private final S3Client s3Client;

    public UploadFilesResource(S3Client client){
        this.s3Client=client;
    }

    @POST
    @ApiOperation(value = "Upload File", response = UploadFilesResponse.class)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Timed
    public UploadFilesResponse getAllFiles(@Valid UploadFilesRequest request) {
        return new UploadFilesResponse(true);
    }
}
