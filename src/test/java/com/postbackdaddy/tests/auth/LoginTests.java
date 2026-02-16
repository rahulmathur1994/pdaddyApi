package com.postbackdaddy.tests.auth;

import com.postbackdaddy.api.models.request.LoginRequest;
import com.postbackdaddy.api.models.response.LoginResponse;
import com.postbackdaddy.api.services.AuthService;
import com.postbackdaddy.api.constants.AppConstants;
import com.postbackdaddy.api.utils.TokenManager;
import com.postbackdaddy.api.utils.ConfigReader;
import com.postbackdaddy.tests.base.BaseTest;
import com.postbackdaddy.tests.data.TestDataProvider;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

/**
 * Login Test Class
 * Tests all login scenarios
 */
public class LoginTests extends BaseTest {

    private AuthService authService;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        authService = new AuthService();  // Create once
        authService.clearAllTokens(); // Clear tokens before each test
    }

    @Test(groups = "auth")
    public void testSuccessfulLogin() {
        extentTest.info("Testing successful login with System Admin credentials");

        LoginRequest loginRequest = new LoginRequest(
            ConfigReader.getSystemAdminEmail(),
            ConfigReader.getSystemAdminPassword(),
            true,
            "12345678"
        );

        Response response = authService.login(loginRequest);
        extentTest.info("Response status code: " + response.getStatusCode());

        // Verify response status
        Assert.assertEquals(response.getStatusCode(), AppConstants.STATUS_CODE_OK,"Login should return 200 OK");

        // Verify user is authenticated
        Assert.assertTrue(authService.isAuthenticated(),
                "User should be authenticated after successful login");

        // Verify tokens are stored (auto-stored by AuthService)
        Assert.assertTrue(TokenManager.hasAuthToken(),
                "Access token should be stored after successful login");
        Assert.assertTrue(TokenManager.hasRefreshToken(),
                "Refresh token should be stored after successful login");

        extentTest.pass("Login successful with both tokens stored");
    }

    @Test(description = "Login with invalid email")
    public void testLoginWithInvalidEmail() {
        extentTest.info("Testing login with invalid email");

        LoginRequest loginRequest = new LoginRequest(
            "invalid@postbackdaddy.com",
            "Test@123",
            true,
            "12345678"
        );

        Response response = authService.login(loginRequest);
        extentTest.info("Response status code: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), AppConstants.STATUS_CODE_UNAUTHORIZED);
        Assert.assertFalse(authService.isAuthenticated(),
                "User should not be authenticated with invalid credentials");

        extentTest.pass("Login failed as expected with invalid email");
    }

    @Test(description = "Login with invalid password")
    public void testLoginWithInvalidPassword() {
        extentTest.info("Testing login with invalid password");

        LoginRequest loginRequest = new LoginRequest(
            "test@postbackdaddy.com",
            "WrongPassword@123",
            true,
            "12345678"
        );

        Response response = authService.login(loginRequest);
        extentTest.info("Response status code: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), AppConstants.STATUS_CODE_UNAUTHORIZED);
        Assert.assertFalse(authService.isAuthenticated(),
                "User should not be authenticated with invalid password");

        extentTest.pass("Login failed as expected with invalid password");
    }


    @Test(description = "Verify auth token is stored after login")
    public void testTokenStorageAfterLogin() {
        extentTest.info("Verifying token storage after successful login");

        // Verify no tokens exist initially (cleared in setUp)
        Assert.assertFalse(TokenManager.hasAuthToken(),
                "Should have no auth token initially");
        Assert.assertFalse(TokenManager.hasRefreshToken(),
                "Should have no refresh token initially");


        Response response = authService.login(
                ConfigReader.getSystemAdminEmail(),
                ConfigReader.getSystemAdminPassword()
        );

        Assert.assertEquals(response.getStatusCode(), AppConstants.STATUS_CODE_OK,
                "Login should be successful");

        // Verify tokens are now stored
        Assert.assertTrue(TokenManager.hasAuthToken(),
                "Auth token should be stored after successful login");
        Assert.assertNotNull(TokenManager.getAuthToken(),
                "Auth token should not be null");
        Assert.assertTrue(TokenManager.hasRefreshToken(),
                "Refresh token should be stored after successful login");
        Assert.assertNotNull(TokenManager.getRefreshToken(),
                "Refresh token should not be null");

        extentTest.pass("Tokens successfully stored after login");
    }

    @Test(description = "Login response contains both tokens")
    public void testLoginReturnsTokens(){
        extentTest.info("Verifying login response contains both tokens");
        LoginRequest loginRequest = new LoginRequest(
                ConfigReader.getSystemAdminEmail(),
                ConfigReader.getSystemAdminPassword(),
                true,
                "12345678"
        );
        Response response = authService.login(loginRequest);
        Assert.assertEquals(response.getStatusCode(), AppConstants.STATUS_CODE_OK);

        // Verify tokens can be retrieved
        String accessToken = authService.getCurrentAuthToken();
        String refreshToken = authService.getCurrentRefreshToken();

        Assert.assertNotNull(accessToken, "Access token should be retrievable");
        Assert.assertNotNull(refreshToken, "Refresh token should be retrievable");
        Assert.assertFalse(accessToken.isEmpty(), "Access token should not be empty");
        Assert.assertFalse(refreshToken.isEmpty(), "Refresh token should not be empty");

        // Optionally verify JWT format (3 parts separated by dots)
        Assert.assertEquals(accessToken.split("\\.").length, 3,
                "Access token should be in JWT format");
        Assert.assertEquals(refreshToken.split("\\.").length, 3,
                "Refresh token should be in JWT format");
        extentTest.pass("Both tokens present and retrievable");
    }
}
