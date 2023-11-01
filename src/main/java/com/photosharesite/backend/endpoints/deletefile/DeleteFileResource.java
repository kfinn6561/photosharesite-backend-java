package com.photosharesite.backend.endpoints.deletefile;

import com.codahale.metrics.annotation.Timed;
import com.photosharesite.backend.db.selectfiles.SelectFilesAccess;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/files/delete")
@Produces(MediaType.APPLICATION_JSON)
public class DeleteFileResource {
    private final SelectFilesAccess dao;

    public DeleteFileResource(SelectFilesAccess dao) {
        this.dao = dao;
    }

    @POST
    @Operation(description = "Delete a file from the DB and the server")
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public void deleteFile(@Valid DeleteFileRequest request) {
        return;
    }
}
