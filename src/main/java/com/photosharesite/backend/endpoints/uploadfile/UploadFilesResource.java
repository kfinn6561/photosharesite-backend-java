package com.photosharesite.backend.endpoints.uploadfile;

import com.codahale.metrics.annotation.Timed;
import com.photosharesite.backend.db.userexists.UserExistsAccess;
import com.photosharesite.backend.exceptions.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Path("/files/upload")
@Produces(MediaType.APPLICATION_JSON)
public class UploadFilesResource {
    private final S3Client s3Client;
    private final UserExistsAccess userExistsDAO;
    private final String BUCKET_NAME;

    private static final int PART_SIZE = 5* 1024*1024;

    public UploadFilesResource(S3Client client, UserExistsAccess userExistsDAO, String bucketName){
        this.s3Client=client;
        this.userExistsDAO = userExistsDAO;
        this.BUCKET_NAME=bucketName;
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Operation(description = "upload a file")
    @Timed
    public void uploadFile(
            @Parameter(schema = @Schema(type="string", format = "binary")) @FormDataParam("file") InputStream inputStream,
            @Parameter(hidden = true) @FormDataParam("file") FormDataContentDisposition fileDetail,
            @QueryParam("UserID") int userID
    ) throws IOException, EntityNotFoundException {

        if (!userExistsDAO.UserExists(userID)){
            throw new EntityNotFoundException(String.format("No user exists with ID=%d", userID));
        }

        String keyName = fileDetail.getFileName();

        // First create a multipart upload and get the upload id
        CreateMultipartUploadRequest createMultipartUploadRequest = CreateMultipartUploadRequest.builder()
                .bucket(BUCKET_NAME)
                .key(keyName)
                .build();

        CreateMultipartUploadResponse initResponse = s3Client.createMultipartUpload(createMultipartUploadRequest);
        String uploadId = initResponse.uploadId();

        // Create a list of CompletedParts objects
        List<CompletedPart> completedParts = new ArrayList<>();

        int i =1; // part numbers must start at 1
        int readBytes;
        byte[] buffer = new byte[PART_SIZE];
        while (true){
            readBytes = inputStream.read(buffer);
            if (readBytes == -1) break;

            // Create the request to upload a part.
            UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(keyName)
                    .uploadId(uploadId)
                    .partNumber(i).build();

            String eTag = s3Client.uploadPart(uploadPartRequest, RequestBody.fromBytes(buffer)).eTag();
            CompletedPart part = CompletedPart.builder().partNumber(i).eTag(eTag).build();
            completedParts.add(part);
            i++;
        }

        // Finally call completeMultipartUpload operation to tell S3 to merge all uploaded
        // parts and finish the multipart operation.
        CompletedMultipartUpload completedMultipartUpload = CompletedMultipartUpload.builder()
                .parts(completedParts)
                .build();

        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                CompleteMultipartUploadRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(keyName)
                        .uploadId(uploadId)
                        .multipartUpload(completedMultipartUpload)
                        .build();

        s3Client.completeMultipartUpload(completeMultipartUploadRequest);
    }
}
