package com.postbackdaddy.tests.auth;

import com.postbackdaddy.api.models.response.ResetPasswordResponse;
import com.postbackdaddy.api.services.PasswordService;
import com.postbackdaddy.api.constants.AppConstants;
import com.postbackdaddy.tests.base.BaseTest;
import com.postbackdaddy.tests.data.TestDataProvider;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;

/**
 * Reset Password Test Class
 * Tests all reset password scenarios
 */
public class ResetPasswordTests extends BaseTest {

    private PasswordService passwordService;
    private static final String VALID_RESET_TOKEN = "valid-reset-token-123";
    private static final String EXPIRED_RESET_TOKEN = "expired-reset-token-456";

    @Test(description = "Successful password reset with valid token and matching passwords")
    public void testSuccessfulPasswordReset() {
        extentTest.info("Testing successful password reset");
        passwordService = new PasswordService();

        Response response = passwordService.resetPassword(
            VALID_RESET_TOKEN,
            "NewPassword@123",
            "NewPassword@123"
        );
        extentTest.info("Response status code: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), AppConstants.STATUS_CODE_OK);

        ResetPasswordResponse resetResponse = response.as(ResetPasswordResponse.class);
        Assert.assertNotNull(resetResponse.getMessage());

        extentTest.pass("Password reset successful");
    }

    @Test(description = "Password reset with expired token")
    public void testPasswordResetWithExpiredToken() {
        extentTest.info("Testing password reset with expired token");
        passwordService = new PasswordService();

        Response response = passwordService.resetPassword(
            EXPIRED_RESET_TOKEN,
            "NewPassword@123",
            "NewPassword@123"
        );
        extentTest.info("Response status code: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), AppConstants.STATUS_CODE_BAD_REQUEST);
        extentTest.pass("Request rejected as expected for expired token");
    }

    @Test(description = "Password reset with non-matching passwords")
    public void testPasswordResetWithNonMatchingPasswords() {
        extentTest.info("Testing password reset with non-matching passwords");
        passwordService = new PasswordService();

        Response response = passwordService.resetPassword(
            VALID_RESET_TOKEN,
            "NewPassword@123",
            "DifferentPassword@123"
        );
        extentTest.info("Response status code: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), AppConstants.STATUS_CODE_BAD_REQUEST);
        extentTest.pass("Request rejected as expected for non-matching passwords");
    }

    @Test(description = "Password reset with empty token")
    public void testPasswordResetWithEmptyToken() {
        extentTest.info("Testing password reset with empty token");
        passwordService = new PasswordService();

        Response response = passwordService.resetPassword(
            "",
            "NewPassword@123",
            "NewPassword@123"
        );
        extentTest.info("Response status code: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), AppConstants.STATUS_CODE_BAD_REQUEST);
        extentTest.pass("Request rejected as expected for empty token");
    }

    @Test(description = "Password reset with weak password")
    public void testPasswordResetWithWeakPassword() {
        extentTest.info("Testing password reset with weak password");
        passwordService = new PasswordService();

        Response response = passwordService.resetPassword(
            VALID_RESET_TOKEN,
            "weak",
            "weak"
        );
        extentTest.info("Response status code: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), AppConstants.STATUS_CODE_BAD_REQUEST);
        extentTest.pass("Request rejected as expected for weak password");
    }

    @Test(description = "Password reset with convenience method",
          dataProvider = "resetPasswordData",
          dataProviderClass = TestDataProvider.class)
    public void testPasswordResetConvenience(String token, String newPassword,
                                            String confirmPassword, String expectedResult) {
        extentTest.info("Testing password reset with token: " + token);
        passwordService = new PasswordService();

        Response response = passwordService.resetPassword(token, newPassword, confirmPassword);

        if ("success".equals(expectedResult)) {
            Assert.assertEquals(response.getStatusCode(), AppConstants.STATUS_CODE_OK);
            extentTest.pass("Password reset successful");
        } else {
            Assert.assertNotEquals(response.getStatusCode(), AppConstants.STATUS_CODE_OK);
            extentTest.pass("Password reset failed as expected");
        }
    }
}

