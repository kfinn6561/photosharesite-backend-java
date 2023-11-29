package com.photosharesite.backend.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {
    @Override
    public Response toResponse(AccessDeniedException ex) {
        return Response
                .status(Response.Status.FORBIDDEN)
                .entity(new ErrorBody(ex.getErrorMessage()))
                .build();
    }
}
