package com.photosharesite.backend.exceptions;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends Exception {
  private String errorMessage;

  public EntityNotFoundException(String errorMessage) {
    super(errorMessage);
    this.errorMessage = errorMessage;
  }

  public EntityNotFoundException() {
    // Jackson Deserialisation
  }
}
