package com.postbackdaddy.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Response model for Tenant User creation
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TenantUserCreateResponse {

    private boolean status;
    private int statusCode;
    private String message;
    private TenantUserData data;
    private Object meta;
    private Object[] error;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TenantUserData {

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