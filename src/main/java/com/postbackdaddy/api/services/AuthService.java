package com.postbackdaddy.api.services;

import com.postbackdaddy.api.base.BaseSpecBuilder;
import com.postbackdaddy.api.constants.Endpoints;
import com.postbackdaddy.api.models.request.LoginRequest;
import com.postbackdaddy.api.models.response.LoginResponse;
import com.postbackdaddy.api.utils.ConfigReader;
import com.postbackdaddy.api.utils.TokenManager;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

/**
 * Authentication Service - Page Object Model for Auth APIs
 * Handles Login and Logout operations
 */
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private String baseURI;

    public AuthService() {

        this.baseURI = ConfigReader.getBaseURI();
    }

    public AuthService(String baseURI) {
        this.baseURI = baseURI;
    }

    /**
     * Login with email and password
     *
     * @param loginRequest Login credentials
     * @return Response object containing login details
     */
    public Response login(LoginRequest loginRequest) {
        logger.info("Login attempt: email={}, deviceId={}", loginRequest.getEmail(), loginRequest.getDeviceId());
        Response response = given()
                .spec(BaseSpecBuilder.getRequestSpec(baseURI))
                .body(loginRequest)
                .when()
                .post(Endpoints.LOGIN)
                .then()
                .extract()
                .response();
        logger.info("Login response: statusCode={}, body={}", response.getStatusCode(), response.getBody().asString());

        // Store tokens if login is successful
        if (response.getStatusCode() == 200) {
            extractAndStoreTokens(response);
        }

        return response;
    }

    /**
     * Login with email and password (convenience method)
     *
     * @param email    User email
     * @param password User password
     * @return Response object
     */
    public Response login(String email, String password) {
        // Provide default values for rememberMe and deviceId
        LoginRequest loginRequest = new LoginRequest(email, password, true, "12345678");
        return login(loginRequest);
    }

    /**
     * Logout user
     *
     * @return Response object
     */
    public Response logout() {
        Response response = given()
                .spec(BaseSpecBuilder.getRequestSpecWithAuth(baseURI, TokenManager.getAuthToken()))
                .when()
                .post(Endpoints.LOGOUT)
                .then()
                .extract()
                .response();

        logger.info("Logout response: statusCode={}", response.getStatusCode());

        // Clear tokens on successful logout
        if (response.getStatusCode() == 200) {
            TokenManager.clearTokens();
            logger.info("Tokens cleared successfully after logout");
        }

        return response;
    }

    /**
     * Refresh access token using refresh token
     *
     * @return Response object
     */
    public Response refreshToken() {
        logger.info("Token refresh attempt");

        String refreshToken = TokenManager.getRefreshToken();
        if (refreshToken == null || refreshToken.isEmpty()) {
            logger.error("No refresh token available for refresh operation");
            throw new IllegalStateException("Refresh token not found. Please login first.");
        }

        // Create request body with refresh token
        String requestBody = String.format("{\"refresh_token\":\"%s\"}", refreshToken);

        Response response = given()
                .spec(BaseSpecBuilder.getRequestSpec(baseURI))
                .body(requestBody)
                .when()
                .post(Endpoints.REFRESH_TOKEN) // Add this endpoint to your Endpoints class
                .then()
                .extract()
                .response();

        logger.info("Token refresh response: statusCode={}", response.getStatusCode());

        // Update tokens on successful refresh
        if (response.getStatusCode() == 200) {
            extractAndStoreTokens(response);
            logger.info("Tokens refreshed and stored successfully");
        }

        return response;
    }

    /**
     * Extract and store tokens from login/refresh response
     * Handles both access_token and refresh_token
     *
     * @param response API response containing tokens
     */
    private void extractAndStoreTokens(Response response) {
        try {
            LoginResponse loginResponse = response.as(LoginResponse.class);

            if (loginResponse.getMeta() != null) {
                String accessToken = loginResponse.getMeta().getAccess_token();
                String refreshToken = loginResponse.getMeta().getRefresh_token();

                // Store access token
                if (accessToken != null && !accessToken.isEmpty()) {
                    TokenManager.setAuthToken(accessToken);
                    logger.debug("Access token stored successfully");
                } else {
                    logger.warn("Access token is null or empty in response");
                }

                // Store refresh token
                if (refreshToken != null && !refreshToken.isEmpty()) {
                    TokenManager.setRefreshToken(refreshToken);
                    logger.debug("Refresh token stored successfully");
                } else {
                    logger.warn("Refresh token is null or empty in response");
                }
            } else {
                logger.error("Meta object is null in login response");
            }
        } catch (Exception e) {
            logger.error("Failed to extract and store tokens from response: {}", e.getMessage(), e);
        }
    }

    /**
     * Verify if user is currently authenticated (has valid auth token)
     *
     * @return true if auth token exists, false otherwise
     */
    public boolean isAuthenticated() {
        boolean hasToken = TokenManager.hasAuthToken();
        logger.debug("Authentication check: {}", hasToken);
        return hasToken;
    }

    /**
     * Get current auth token
     *
     * @return Current auth token or null if not authenticated
     */
    public String getCurrentAuthToken() {
        return TokenManager.getAuthToken();
    }

    /**
     * Get current refresh token
     *
     * @return Current refresh token or null if not available
     */
    public String getCurrentRefreshToken() {
        return TokenManager.getRefreshToken();
    }

    /**
     * Clear all stored tokens (useful for cleanup in tests)
     */
    public void clearAllTokens() {
        TokenManager.clearTokens();
        logger.info("All tokens cleared manually");
    }
}
