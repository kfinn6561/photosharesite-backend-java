package com.photosharesite.backend.endpoints.deletefile;

import com.codahale.metrics.annotation.Timed;
import com.photosharesite.backend.db.deletefile.DeleteFileAccess;
import com.photosharesite.backend.db.getfiledetails.GetFileDetailsAccess;
import com.photosharesite.backend.db.getfiledetails.GetFileDetailsResponse;
import com.photosharesite.backend.db.insertorselectuser.InsertOrSelectUserAccess;
import com.photosharesite.backend.exceptions.AccessDeniedException;
import com.photosharesite.backend.exceptions.EntityNotFoundException;
import com.photosharesite.backend.filemanipulation.FileDeleter;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Optional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/files/delete")
@Produces(MediaType.APPLICATION_JSON)
public class DeleteFileResource {
  private final DeleteFileAccess deleteFileDAO;
  private final GetFileDetailsAccess getFileDetailsDAO;
  private final InsertOrSelectUserAccess selectUserDAO;
  private final FileDeleter fileDeleter;

  public DeleteFileResource(
      DeleteFileAccess deleteFileDAO,
      GetFileDetailsAccess getFileDetailsDAO,
      InsertOrSelectUserAccess selectUserDAO,
      FileDeleter fileDeleter) {
    this.deleteFileDAO = deleteFileDAO;
    this.getFileDetailsDAO = getFileDetailsDAO;
    this.selectUserDAO = selectUserDAO;
    this.fileDeleter = fileDeleter;
  }

  @POST
  @Operation(description = "Delete a file from the DB and the server")
  @Consumes(MediaType.APPLICATION_JSON)
  @Timed
  public void deleteFile(@Valid DeleteFileRequest request)
      throws AccessDeniedException, EntityNotFoundException {

    int userID = selectUserDAO.InsertOrSelectUser(request.getIPAddress());
    Optional<GetFileDetailsResponse> fileDetails =
        getFileDetailsDAO.GetFileDetails(request.getFileID());

    if (fileDetails.isEmpty()) {
      throw new EntityNotFoundException("this file does not exist");
    }

    if (userID != fileDetails.get().getOwnerID()) {
      throw new AccessDeniedException("You do not have permission to delete this file.");
    }

    deleteFileDAO.DeleteFile(request.getFileID());
    fileDeleter.DeleteFile(fileDetails.get().getFileName());
  }
}
