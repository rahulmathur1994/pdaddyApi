package com.postbackdaddy.api.services;

import com.postbackdaddy.api.base.BaseSpecBuilder;
import com.postbackdaddy.api.store.ConnectionConfigStore;
import com.postbackdaddy.api.store.ConnectionRuntimeStore;
import com.postbackdaddy.api.utils.ConfigReader;
import com.postbackdaddy.api.utils.TokenManager;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ConnectionService {

    private static final Logger logger =
            LoggerFactory.getLogger(ConnectionService.class);

    /**
     * Create TARGET Connection
     */
    public static Response createTargetConnection(String tenantId) {

        String token = TokenManager.getAuthToken();
        String targetConfigId = ConnectionConfigStore.getTargetId();

        if (tenantId == null)
            throw new IllegalStateException("TenantId not available");

        if (targetConfigId == null)
            throw new IllegalStateException("Target ConfigId not available");

        logger.info("Creating TARGET connection for tenant: {}", tenantId);

        String requestBody = """
                {
                    "name": "QA test axon",
                    "configId": "%s",
                    "credentials": {
                        "base_url": "https://api.axon.finance/v1",
                        "api_token": "MY_AXON_API_TOKEN"
                    }
                }
                """.formatted(targetConfigId);

        Response response =
                given()
                        .baseUri(ConfigReader.getBaseURI())
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + token)
                        .body(requestBody)
                        .when()
                        .post("/v1/tenants/" + tenantId + "/connections")
                        .then()
                        .extract()
                        .response();

        logger.info("Create Connection Status: {}", response.getStatusCode());
        logger.debug("Response: {}", response.asPrettyString());

        // ✅ Save connection id + name
        if (response.getStatusCode() == 201 &&
                response.jsonPath().getBoolean("status")) {

            String connectionId =
                    response.jsonPath().getString("data.id");

            String connectionName =
                    response.jsonPath().getString("data.name");

            ConnectionRuntimeStore.setConnection(connectionId, connectionName);

            logger.info("Saved Connection -> ID: {}, Name: {}",
                    connectionId, connectionName);
        }

        return response;
    }


    public Response createSourceConnection(String tenantId) {

        String sourceId = ConnectionConfigStore.getSourceId();

        Map<String, Object> credentials = new HashMap<>();
        credentials.put("api_key", "12345678");

        Map<String, Object> body = new HashMap<>();
        body.put("name", "QA Source Auto");
        body.put("configId", sourceId);
        body.put("credentials", credentials);

        Response response = given()
                .spec(BaseSpecBuilder.getRequestSpec(ConfigReader.getBaseURI()))
                .body(body)
                .when()
                .post("/v1/tenants/" + tenantId + "/connections")
                .then()
                .extract()
                .response();

        if (response.getStatusCode() == 201 &&
                response.jsonPath().getBoolean("status")) {

            String id = response.jsonPath().getString("data.id");
            String name = response.jsonPath().getString("data.name");

            ConnectionConfigStore.setSourceConnection(id, name);
        }

        return response;
    }
}