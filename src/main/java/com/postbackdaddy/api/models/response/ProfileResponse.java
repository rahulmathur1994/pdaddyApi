package com.postbackdaddy.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Profile Response Model
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileResponse {

    private boolean status;
    private int statusCode;
    private String message;
    private ProfileData data;
    private Object meta;
    private Object[] error;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProfileData {
        private String id;
        private String email;
        private String firstName;
        private String lastName;
        private String status;

        @JsonProperty("role_id")
        private String roleId;

        @JsonProperty("tenant_id")
        private String tenantId;

        @JsonProperty("created_at")
        private String createdAt;
    }
}