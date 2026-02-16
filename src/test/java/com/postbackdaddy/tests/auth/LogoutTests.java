package com.postbackdaddy.tests.auth;

import com.postbackdaddy.api.models.request.LoginRequest;
import com.postbackdaddy.api.services.AuthService;
import com.postbackdaddy.api.constants.AppConstants;
import com.postbackdaddy.api.utils.TokenManager;
import com.postbackdaddy.tests.base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

/**
 * Logout Test Class
 * Tests all logout scenarios
 */
public class LogoutTests extends BaseTest {

    private AuthService authService;

    @BeforeMethod(alwaysRun = true)
    public void setUpLogin() {
        authService = new AuthService();
        // Login before each test
        LoginRequest loginRequest = new LoginRequest("rahul.mathur@codeclouds.in", "password@1234", true, "12345678");
        Response loginResponse = authService.login(loginRequest);

        if (loginResponse.getStatusCode() != AppConstants.STATUS_CODE_OK) {
            extentTest.warning("Failed to login before test");
        }
    }

    @Test(description = "Successful logout with valid token")
    public void testSuccessfulLogout() {
        extentTest.info("Testing successful logout with valid token");

        Assert.assertTrue(TokenManager.hasAuthToken(), "Token should exist before logout");

        Response response = authService.logout();
        extentTest.info("Response status code: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), AppConstants.STATUS_CODE_OK);
        Assert.assertFalse(TokenManager.hasAuthToken(), "Token should be cleared after logout");

        extentTest.pass("Logout successful and token cleared");
    }

    @Test(description = "Logout clears authentication token")
    public void testLogoutClearsToken() {
        extentTest.info("Testing that logout clears authentication token");

        String tokenBeforeLogout = TokenManager.getAuthToken();
        Assert.assertNotNull(tokenBeforeLogout);

        authService.logout();

        Assert.assertNull(TokenManager.getAuthToken(), "Token should be null after logout");
        extentTest.pass("Token cleared successfully");
    }

}
