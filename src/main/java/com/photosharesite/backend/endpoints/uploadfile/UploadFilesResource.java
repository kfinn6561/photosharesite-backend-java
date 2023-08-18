package com.photosharesite.backend.endpoints.uploadfile;

import com.codahale.metrics.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Operation(description = "upload a file")
    @Timed
    public UploadFileResponse uploadFile(
            @Parameter(schema = @Schema(type="string", format = "binary")) @FormDataParam("file") InputStream inputStream,
            @Parameter(hidden = true) @FormDataParam("file") FormDataContentDisposition fileDetail
    ) {
        return new UploadFileResponse(true);
    }
}
