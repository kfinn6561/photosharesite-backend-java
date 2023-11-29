package com.photosharesite.backend.endpoints.getfiles;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetFilesRequest {
  @JsonProperty("IPAddress")
  private String IPAddress;

  public GetFilesRequest() {
    // Jackson Deserialisation
  }

  public GetFilesRequest(String IPAddress) {
    this.IPAddress = IPAddress;
  }
}
