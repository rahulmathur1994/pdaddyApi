package com.postbackdaddy.tests.auth;

import com.postbackdaddy.api.models.response.ForgetPasswordResponse;
import com.postbackdaddy.api.services.PasswordService;
import com.postbackdaddy.api.constants.AppConstants;
import com.postbackdaddy.tests.base.BaseTest;
import com.postbackdaddy.tests.data.TestDataProvider;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;

/**
 * Forget Password Test Class
 * Tests all forget password scenarios
 */
public class ForgetPasswordTests extends BaseTest {

    private PasswordService passwordService;

    @Test(description = "Successful forgot password request with valid email")
    public void testSuccessfulForgetPassword() {
        extentTest.info("Testing successful forgot password request");
        passwordService = new PasswordService();

        Response response = passwordService.forgetPassword("test@postbackdaddy.com");
        extentTest.info("Response status code: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), AppConstants.STATUS_CODE_OK);

        ForgetPasswordResponse forgetResponse = response.as(ForgetPasswordResponse.class);
        Assert.assertNotNull(forgetResponse.getData().getResetToken());
        Assert.assertNotNull(forgetResponse.getData().getExpiresIn());

        extentTest.pass("Forgot password request successful and reset token received");
    }

    @Test(description = "Forgot password with non-existent email")
    public void testForgetPasswordWithNonExistentEmail() {
        extentTest.info("Testing forgot password with non-existent email");
        passwordService = new PasswordService();

        Response response = passwordService.forgetPassword("nonexistent@postbackdaddy.com");
        extentTest.info("Response status code: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), AppConstants.STATUS_CODE_NOT_FOUND);
        extentTest.pass("Request rejected as expected for non-existent email");
    }

    @Test(description = "Forgot password with empty email")
    public void testForgetPasswordWithEmptyEmail() {
        extentTest.info("Testing forgot password with empty email");
        passwordService = new PasswordService();

        Response response = passwordService.forgetPassword("");
        extentTest.info("Response status code: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), AppConstants.STATUS_CODE_BAD_REQUEST);
        extentTest.pass("Request rejected as expected for empty email");
    }

    @Test(description = "Forgot password with invalid email format")
    public void testForgetPasswordWithInvalidEmailFormat() {
        extentTest.info("Testing forgot password with invalid email format");
        passwordService = new PasswordService();

        Response response = passwordService.forgetPassword("invalid-email");
        extentTest.info("Response status code: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), AppConstants.STATUS_CODE_BAD_REQUEST);
        extentTest.pass("Request rejected as expected for invalid email format");
    }

    @Test(description = "Forgot password response contains required fields",
          dataProvider = "forgetPasswordData",
          dataProviderClass = TestDataProvider.class)
    public void testForgetPasswordResponseFields(String email, String expectedResult) {
        extentTest.info("Testing forgot password response with email: " + email);
        passwordService = new PasswordService();

        Response response = passwordService.forgetPassword(email);

        if ("success".equals(expectedResult)) {
            ForgetPasswordResponse forgetResponse = response.as(ForgetPasswordResponse.class);
            Assert.assertNotNull(forgetResponse.getStatus());
            Assert.assertNotNull(forgetResponse.getMessage());
            extentTest.pass("Response contains all required fields");
        }
    }
}

