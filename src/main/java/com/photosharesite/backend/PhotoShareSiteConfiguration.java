package com.photosharesite.backend;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class PhotoShareSiteConfiguration extends Configuration {
  @Valid @NotNull private DataSourceFactory database = new DataSourceFactory();

  @NotNull private String mediaFilesBucketName;

  @JsonProperty("database")
  public void setDataSourceFactory(DataSourceFactory factory) {
    this.database = factory;
  }

  @JsonProperty("database")
  public DataSourceFactory getDataSourceFactory() {
    return database;
  }

  @JsonProperty("mediaFilesBucketName")
  public String getMediaFilesBucketName() {
    return mediaFilesBucketName;
  }

  @JsonProperty("mediaFilesBucketName")
  public void setMediaFilesBucketName(String mediaFilesBucketName) {
    this.mediaFilesBucketName = mediaFilesBucketName;
  }
}
