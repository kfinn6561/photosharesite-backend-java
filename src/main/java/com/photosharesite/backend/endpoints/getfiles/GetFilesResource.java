package com.photosharesite.backend.endpoints.getfiles;

import com.codahale.metrics.annotation.Timed;
import com.photosharesite.backend.db.selectfiles.SelectFilesAccess;
import com.photosharesite.backend.db.selectfiles.SelectFilesResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/files/all")
@Api(value = "Get All Files")
@Produces(MediaType.APPLICATION_JSON)
public class GetFilesResource {
    private final SelectFilesAccess dao;

    public GetFilesResource(SelectFilesAccess dao) {
        this.dao = dao;
    }

    @POST
    @ApiOperation(value = "Get All Files", response = GetFilesResponse.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public List<GetFilesResponse> getAllFiles(@Valid GetFilesRequest request) {
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
