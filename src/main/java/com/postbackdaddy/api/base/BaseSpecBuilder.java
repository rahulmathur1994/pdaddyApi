package com.postbackdaddy.api.base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

/**
 * Base class for building RequestSpecification and ResponseSpecification
 * Provides common request/response configurations
 */
public class BaseSpecBuilder {

    /**
     * Builds a common request specification
     * @param baseURI Base URI for the API
     * @return RequestSpecification with common settings
     */
    public static RequestSpecification getRequestSpec(String baseURI) {
        return new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setContentType("application/json")
                .build();
    }

    /**
     * Builds a common response specification
     * @return ResponseSpecification with common settings
     */
    public static ResponseSpecification getResponseSpec() {
        return new ResponseSpecBuilder()
                .expectContentType("application/json")
                .build();
    }

    /**
     * Builds a request specification with authentication token
     * @param baseURI Base URI for the API
     * @param token Authorization token
     * @return RequestSpecification with authentication
     */
    public static RequestSpecification getRequestSpecWithAuth(String baseURI, String token) {
        return new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setContentType("application/json")
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
}

