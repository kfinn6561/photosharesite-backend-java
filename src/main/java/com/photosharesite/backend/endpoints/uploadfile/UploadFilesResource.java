package com.photosharesite.backend.endpoints.uploadfile;

import com.codahale.metrics.annotation.Timed;
import com.photosharesite.backend.db.insertfile.InsertFileAccess;
import com.photosharesite.backend.db.userexists.UserExistsAccess;
import com.photosharesite.backend.exceptions.EntityNotFoundException;
import com.photosharesite.backend.filemanipulation.FileUploader;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import software.amazon.awssdk.services.s3.S3Client;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;

@Path("/files/upload")
@Produces(MediaType.APPLICATION_JSON)
public class UploadFilesResource {
    private final UserExistsAccess userExistsDAO;
    private final InsertFileAccess insertFileAccessDAO;
    private final FileUploader fileUploader;

    public UploadFilesResource(S3Client client, UserExistsAccess userExistsDAO, InsertFileAccess insertFileAccessDAO, String bucketName){
        this.userExistsDAO = userExistsDAO;
        this.insertFileAccessDAO = insertFileAccessDAO;
        this.fileUploader = new FileUploader(client, bucketName);
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

        String url = fileUploader.UploadFileMultipart(keyName, inputStream);

        insertFileAccessDAO.InsertFile(url, keyName, userID);
    }
}
