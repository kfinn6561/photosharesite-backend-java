package com.photosharesite.backend.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {
  @Override
  public Response toResponse(EntityNotFoundException ex) {
    return Response.status(Response.Status.NOT_FOUND)
        .entity(new ErrorBody(ex.getErrorMessage()))
        .build();
  }
}
