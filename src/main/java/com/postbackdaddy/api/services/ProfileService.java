package com.postbackdaddy.api.services;

import com.postbackdaddy.api.models.response.ProfileResponse;
import com.postbackdaddy.api.utils.TokenManager;
import com.postbackdaddy.api.utils.ConfigReader;
import com.postbackdaddy.api.constants.Endpoints;
import com.postbackdaddy.api.base.BaseSpecBuilder;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

/**
 * Profile Service - Handles user profile operations
 */
public class ProfileService {

    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);
    private String baseURI;

    public ProfileService() {
        this.baseURI = ConfigReader.getBaseURI();
    }

    public ProfileService(String baseURI) {
        this.baseURI = baseURI;
    }

    /**
     * Get user profile
     *
     * @return Response object containing profile details
     */
    public Response getProfile() {
        logger.info("Fetching user profile with GET /v1/profile");

        String token = TokenManager.getAuthToken();
        if (token == null || token.isEmpty()) {
            logger.error("No authentication token available");
            throw new IllegalStateException("User not authenticated. Please login first.");
        }

        logger.info("Using access token: {}...", token.substring(0, Math.min(20, token.length())));

        Response response = given()
                .spec(BaseSpecBuilder.getRequestSpecWithAuth(baseURI, token))
                .when()
                .get(Endpoints.PROFILE)
                .then()
                .extract()
                .response();

        logger.info("Profile fetch response: statusCode={}", response.getStatusCode());
        logger.debug("Profile response body: {}", response.getBody().asString());

        return response;
    }

    /**
     * Get user profile and return as ProfileResponse object
     *
     * @return ProfileResponse object
     */
    public ProfileResponse getProfileData() {
        Response response = getProfile();

        if (response.getStatusCode() == 200) {
            ProfileResponse profileResponse = response.as(ProfileResponse.class);
            logger.info("Profile data parsed successfully");
            return profileResponse;
        } else {
            logger.error("Failed to fetch profile. Status: {}, Body: {}",
                    response.getStatusCode(), response.getBody().asString());
            return null;
        }
    }

    /**
     * Print profile details to console
     *
     * @param profile ProfileResponse object
     */
    public void printProfileDetails(ProfileResponse profile) {
        if (profile != null && profile.getData() != null) {
            ProfileResponse.ProfileData data = profile.getData();

            System.out.println("\n========================================");
            System.out.println("        PROFILE DETAILS");
            System.out.println("========================================");
            System.out.println("ID:           " + data.getId());
            System.out.println("Role ID:      " + data.getRoleId());
            System.out.println("Email:        " + data.getEmail());
            System.out.println("Name:         " + data.getFirstName() + " " + data.getLastName());
            System.out.println("Status:       " + data.getStatus());
            System.out.println("Tenant ID:    " + data.getTenantId());
            System.out.println("Created At:   " + data.getCreatedAt());
            System.out.println("========================================\n");

            logger.info("Profile details printed to console");
        }
    }
}