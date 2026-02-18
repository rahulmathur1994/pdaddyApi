package com.postbackdaddy.api.services;

import com.postbackdaddy.api.models.request.CreateTenantRequest;
import com.postbackdaddy.api.models.response.CreateTenantResponse;
import com.postbackdaddy.api.base.BaseSpecBuilder;
import com.postbackdaddy.api.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

/**
 * Implementation of TenantService for creating tenants
 * Uses RestAssured for API calls
 */
public class TenantServiceImpl implements TenantService {

    private static final String TENANT_ENDPOINT = "/v1/tenants";

    /**
     * Creates a new tenant via API
     *
     * @param request The CreateTenantRequest containing tenant details
     * @param accessToken The authorization access token
     * @return CreateTenantResponse containing the created tenant data
     */
    @Override
    public CreateTenantResponse createTenant(CreateTenantRequest request, String accessToken) {

        String baseUri = ConfigReader.getProperty("base.uri");
        RequestSpecification requestSpec = BaseSpecBuilder.getRequestSpecWithAuth(baseUri, accessToken);

        CreateTenantResponse response = RestAssured
                .given()
                    .spec(requestSpec)
                    .body(request)
                .when()
                    .post(TENANT_ENDPOINT)
                .then()
                    .extract()
                    .as(CreateTenantResponse.class);

        return response;
    }
}

