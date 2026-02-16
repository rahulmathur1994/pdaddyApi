package com.postbackdaddy.api.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO for Reset Password Response
 */
public class ResetPasswordResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private ResetPasswordData data;

    // Inner class for reset password data
    public static class ResetPasswordData {
        @JsonProperty("message")
        private String message;

        @JsonProperty("resetDate")
        private String resetDate;

        // Getters and Setters
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getResetDate() {
            return resetDate;
        }

        public void setResetDate(String resetDate) {
            this.resetDate = resetDate;
        }
    }

    // Constructors
    public ResetPasswordResponse() {
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

    public ResetPasswordData getData() {
        return data;
    }

    public void setData(ResetPasswordData data) {
        this.data = data;
    }
}

