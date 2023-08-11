package com.photosharesite.backend.endpoints.uploadfile;

import com.codahale.metrics.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

@Path("/files/upload")
@Produces(MediaType.APPLICATION_JSON)
public class UploadFilesResource {
//    private final S3Client s3Client;
//
//    public UploadFilesResource(S3Client client){
//        this.s3Client=client;
//    }

    public UploadFilesResource() {
    }

    @POST
    @Operation(description = "bbbb")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Timed
    public UploadFileResponse uploadFile(
            //@Valid @FormDataParam("json-body") UploadFilesRequest request,
            @FormDataParam("file-data") InputStream body,
            @FormDataParam("file-details") FormDataContentDisposition fileDetails
    ) {
        return new UploadFileResponse(true);
    }
}
