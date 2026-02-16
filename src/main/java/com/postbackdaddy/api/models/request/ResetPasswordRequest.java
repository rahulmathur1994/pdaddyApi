package com.postbackdaddy.api.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO for Reset Password Request
 */
public class ResetPasswordRequest {

    @JsonProperty("token")
    private String token;

    @JsonProperty("newPassword")
    private String newPassword;

    @JsonProperty("confirmPassword")
    private String confirmPassword;

    // Constructors
    public ResetPasswordRequest() {
    }

    public ResetPasswordRequest(String token, String newPassword, String confirmPassword) {
        this.token = token;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}

