package com.postbackdaddy.api.services;

import com.postbackdaddy.api.models.request.CreateTenantRequest;
import com.postbackdaddy.api.models.response.CreateTenantResponse;

public interface TenantService {

    /**
     * Creates a new tenant in the system
     *
     * @param request The CreateTenantRequest containing tenant details
     * @param accessToken The authorization access token
     * @return CreateTenantResponse containing the created tenant data
     */
    CreateTenantResponse createTenant(CreateTenantRequest request, String accessToken);
}

