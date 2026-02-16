package com.postbackdaddy.api.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO for Forgot Password Request
 */
public class ForgetPasswordRequest {

    @JsonProperty("email")
    private String email;

    // Constructors
    public ForgetPasswordRequest() {
    }

    public ForgetPasswordRequest(String email) {
        this.email = email;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

