package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthzRequest {

    @JsonProperty("user")
    private String user;

    public AuthzRequest() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
