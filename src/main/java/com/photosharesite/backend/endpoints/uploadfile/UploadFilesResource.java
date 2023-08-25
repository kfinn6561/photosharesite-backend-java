package com.photosharesite.backend.endpoints.uploadfile;

import com.codahale.metrics.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

@Path("/files/upload")
@Produces(MediaType.APPLICATION_JSON)
public class UploadFilesResource {
    private final S3Client s3Client;
    private final String BUCKET_NAME;

    public UploadFilesResource(S3Client client, String bucketName){
        this.s3Client=client;
        this.BUCKET_NAME=bucketName;
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Operation(description = "upload a file")
    @Timed
    public UploadFileResponse uploadFile(
            @Parameter(schema = @Schema(type="string", format = "binary")) @FormDataParam("file") InputStream inputStream,
            @Parameter(hidden = true) @FormDataParam("file") FormDataContentDisposition fileDetail,
            @QueryParam("UserID") int userID
    ) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(fileDetail.getFileName())
                .build();
        //s3Client.putObject(objectRequest, RequestBody.fromInputStream(inputStream,fileDetail.getSize()));
        return new UploadFileResponse(true);
    }
}
