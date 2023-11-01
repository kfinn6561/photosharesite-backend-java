package com.photosharesite.backend.endpoints.deletefile;

import com.codahale.metrics.annotation.Timed;
import com.photosharesite.backend.db.selectfiles.SelectFilesAccess;
import com.photosharesite.backend.db.selectfiles.SelectFilesResponse;
import com.photosharesite.backend.endpoints.getfiles.GetFilesResponse;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/files/all")
@Produces(MediaType.APPLICATION_JSON)
public class DeleteFileResource {
    private final SelectFilesAccess dao;

    public DeleteFileResource(SelectFilesAccess dao) {
        this.dao = dao;
    }

    @POST
    @Operation(description = "Get details of all files")
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public List<GetFilesResponse> getAllFiles(@Valid DeleteFileRequest request) {
        List<SelectFilesResponse> dbResponse = dao.SelectFiles();
        return dbResponse
                .stream()
                .map(r->convertDBResponseToAPIResponse(r,request.getIPAddress()))
                .collect(Collectors.toList());
    }

    private GetFilesResponse convertDBResponseToAPIResponse(SelectFilesResponse dbResponse, String ipAddress){
        return new GetFilesResponse(
                dbResponse.getFileID(),
                dbResponse.getURL(),
                dbResponse.getFileName(),
                dbResponse.getIPAddress().equals(ipAddress)
        );
    }
}
