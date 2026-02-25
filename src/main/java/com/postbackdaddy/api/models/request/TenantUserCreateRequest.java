package com.postbackdaddy.api.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Request model for creating Tenant (Organization) User
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TenantUserCreateRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String roleId;   // Fixed Tenant User Role
}