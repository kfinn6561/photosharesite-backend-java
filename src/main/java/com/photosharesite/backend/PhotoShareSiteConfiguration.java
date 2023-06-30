package com.photosharesite.backend;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class PhotoShareSiteConfiguration extends Configuration {
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @NotNull
    private String AWSAccessKey;

    @NotNull
    private String AWSSecretkey;

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory factory) {
        this.database = factory;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    public String getAWSAccessKey() {
        return AWSAccessKey;
    }

    public void setAWSAccessKey(String AWSAccessKey) {
        this.AWSAccessKey = AWSAccessKey;
    }

    public String getAWSSecretkey() {
        return AWSSecretkey;
    }

    public void setAWSSecretkey(String AWSSecretkey) {
        this.AWSSecretkey = AWSSecretkey;
    }
}
