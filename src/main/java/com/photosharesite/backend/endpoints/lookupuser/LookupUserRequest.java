package com.photosharesite.backend.endpoints.lookupuser;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LookupUserRequest {
    @JsonProperty("IPAddress")
    private String IPAddress;

    public LookupUserRequest(){
        //Jackson Deserialisation
    }

    public LookupUserRequest(String IPAddress) {
        this.IPAddress = IPAddress;
    }
}
