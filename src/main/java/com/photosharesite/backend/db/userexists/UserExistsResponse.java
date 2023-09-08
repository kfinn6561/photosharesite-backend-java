package com.photosharesite.backend.db.userexists;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserExistsResponse {
    private int exists;

    public UserExistsResponse(){
        // Jackson deserialisation
    }

    public UserExistsResponse(int exists) {
        this.exists = exists;
    }
}
