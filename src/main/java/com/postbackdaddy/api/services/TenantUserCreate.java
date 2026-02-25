package com.postbackdaddy.api.services;

import com.postbackdaddy.api.base.BaseSpecBuilder;
import com.postbackdaddy.api.constants.Endpoints;
import com.postbackdaddy.api.models.request.TenantUserCreateRequest;
import com.postbackdaddy.api.models.response.TenantUserCreateResponse;
import com.postbackdaddy.api.utils.ConfigReader;
import com.postbackdaddy.api.utils.TokenManager;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

/**
 * Service for creating Tenant (Organization) Users
 */
public class TenantUserCreate {

    private static final Logger logger =
            LoggerFactory.getLogger(TenantUserCreate.class);

    private final String baseURI;

    public TenantUserCreate() {
        this.baseURI = ConfigReader.getBaseURI();
    }

    /**
     * Create tenant user using admin access token
     */
    public TenantUserCreateResponse createTenantUser(
            String tenantId,
            TenantUserCreateRequest requestBody) {

        String accessToken = TokenManager.getAuthToken();

        if (accessToken == null || accessToken.isEmpty()) {
            logger.error("Access token missing.");
            throw new IllegalStateException("User not authenticated.");
        }

        String endpoint =
                String.format(Endpoints.CREATE_TENANT_USER, tenantId);

        logger.info("Creating tenant user for tenantId={}", tenantId);

        Response response =
                given()
                        .spec(BaseSpecBuilder.getRequestSpecWithAuth(baseURI, accessToken))
                        .body(requestBody)
                        .when()
                        .post(endpoint)
                        .then()
                        .extract()
                        .response();

        logger.info("Create Tenant User Response Code: {}",
                response.getStatusCode());

        logger.debug("Response Body: {}",
                response.getBody().asString());

        return response.as(TenantUserCreateResponse.class);
    }
}