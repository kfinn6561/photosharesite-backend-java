package com.photosharesite.backend.resources;

import com.codahale.metrics.annotation.Timed;
import com.photosharesite.backend.api.GetFilesRequest;
import com.photosharesite.backend.api.GetFilesResponse;
import com.photosharesite.backend.db.SelectFilesResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jdbi.v3.core.Jdbi;

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
    public static final String selectFilesProcName = "SelectFiles";
    private final Jdbi jdbi;

    public GetFilesResource(Jdbi jdbi) {
        this.jdbi=jdbi;
    }

    @POST
    @ApiOperation(value = "Get All Files", response = GetFilesResponse.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public List<GetFilesResponse> lookupUser(@Valid GetFilesRequest request) {
        return  jdbi.withHandle(handle -> handle.createQuery("CALL " + selectFilesProcName + "(:IPAddress)")
                .bind("IPAddress", request.getIPAddress())
                .mapToBean(SelectFilesResponse.class)
                .stream()
                .map(r->convertDBResponseToAPIResponse(r,request.getIPAddress()))
                .collect(Collectors.toList())
        );
    }

    private GetFilesResponse convertDBResponseToAPIResponse(SelectFilesResponse dbResponse, String ipAddress){
        return new GetFilesResponse(
                dbResponse.getFileID(),
                dbResponse.getURL(),
                dbResponse.getFileName(),
                dbResponse.getIPAddress() == ipAddress
        );
    }
}
