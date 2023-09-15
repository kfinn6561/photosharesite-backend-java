package com.photosharesite.backend.exceptions;

public class ErrorBody {
    private String errorMessage;
    public ErrorBody() {
        //Jackson Deserialisation
    }
    public ErrorBody(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
