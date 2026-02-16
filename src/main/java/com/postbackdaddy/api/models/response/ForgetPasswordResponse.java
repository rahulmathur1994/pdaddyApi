package com.postbackdaddy.api.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO for Forgot Password Response
 */
public class ForgetPasswordResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private ForgetPasswordData data;

    // Inner class for forget password data
    public static class ForgetPasswordData {
        @JsonProperty("resetToken")
        private String resetToken;

        @JsonProperty("expiresIn")
        private Long expiresIn;

        @JsonProperty("message")
        private String message;

        // Getters and Setters
        public String getResetToken() {
            return resetToken;
        }

        public void setResetToken(String resetToken) {
            this.resetToken = resetToken;
        }

        public Long getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(Long expiresIn) {
            this.expiresIn = expiresIn;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    // Constructors
    public ForgetPasswordResponse() {
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ForgetPasswordData getData() {
        return data;
    }

    public void setData(ForgetPasswordData data) {
        this.data = data;
    }
}

