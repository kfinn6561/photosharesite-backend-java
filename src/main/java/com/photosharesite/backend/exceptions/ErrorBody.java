package com.photosharesite.backend.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorBody {
    private String errorMessage;
    public ErrorBody() {
        //Jackson Deserialisation
    }
    public ErrorBody(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
