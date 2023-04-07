package com.photosharesite.backend.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Greeting {
    private long id;

    private String content;

    public Greeting() {
        // Jackson deserialization
    }

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }
}
