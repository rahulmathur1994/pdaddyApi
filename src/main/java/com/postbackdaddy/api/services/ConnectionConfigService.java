package com.postbackdaddy.api.services;

import com.postbackdaddy.api.store.ConnectionConfigStore;
import com.postbackdaddy.api.utils.ConfigReader;
import com.postbackdaddy.api.utils.TokenManager;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class ConnectionConfigService {

    private static final Logger logger =
            LoggerFactory.getLogger(ConnectionConfigService.class);

    public static Response fetchTargetConnectionConfig() {

        String token = TokenManager.getAuthToken();

        if (token == null || token.isEmpty()) {
            throw new IllegalStateException("Access token not available. Login required.");
        }

        logger.info("Fetching TARGET connection configs...");

        Response response =
                given()
                        .baseUri(ConfigReader.getBaseURI())
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + token)
                        .queryParam("direction", "TARGET")
                        .when()
                        .get("/v1/connection-configs")
                        .then()
                        .extract()
                        .response();

        logger.info("Response Code: {}", response.getStatusCode());
        logger.debug("Response Body: {}", response.asPrettyString());

        // ✅ Extract targetId
        if (response.getStatusCode() == 200 &&
                response.jsonPath().getBoolean("status")) {

            String targetId =
                    response.jsonPath().getString("data[0].id");

            ConnectionConfigStore.setTargetId(targetId);

            logger.info("Saved targetId -> {}", targetId);
        }

        return response;
    }

    public static Response fetchSourceConnectionConfig() {

        String token = TokenManager.getAuthToken();

        if (token == null || token.isEmpty()) {
            throw new IllegalStateException("Access token not available. Login required.");
        }

        logger.info("Fetching SOURCE connection configs...");

        Response response =
                given()
                        .baseUri(ConfigReader.getBaseURI())
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + token)
                        .queryParam("direction", "SOURCE")
                        .when()
                        .get("/v1/connection-configs")
                        .then()
                        .extract()
                        .response();

        logger.info("Response Code: {}", response.getStatusCode());
        logger.debug("Response Body: {}", response.asPrettyString());

        // ✅ Extract sourceId
        if (response.getStatusCode() == 200 &&
                response.jsonPath().getBoolean("status")) {

            String sourceId =
                    response.jsonPath().getString("data[0].id");

            ConnectionConfigStore.setSourceId(sourceId);

            logger.info("Saved sourceId -> {}", sourceId);
        }

        return response;
    }
}