package com.photosharesite.backend.exceptions;

import lombok.Getter;

@Getter
public class AccessDeniedException extends Exception {
    private String errorMessage;
    public AccessDeniedException(String errorMessage){
        super(errorMessage);
        this.errorMessage=errorMessage;
    }

    public AccessDeniedException() {
        //Jackson Deserialisation
    }
}
