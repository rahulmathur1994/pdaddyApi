package com.postbackdaddy.tests.data;

import com.postbackdaddy.api.constants.AppConstants;
import org.testng.annotations.DataProvider;

/**
 * Test Data Provider for TestNG Data-Driven Tests
 * Provides test data for Login, Logout, and Password Reset scenarios
 */
public class TestDataProvider {

    /**
     * Login test data provider
     * @return 2D array of test data [email, password, expectedResult]
     */
    @DataProvider(name = "loginCredentials")
    public Object[][] getLoginCredentials() {
        return new Object[][] {
            { "test@postbackdaddy.com", "Test@123", "success" },
            { "invalid@postbackdaddy.com", "InvalidPass@123", "failure" },
            { "", "Test@123", "failure" },
            { "test@postbackdaddy.com", "", "failure" }
        };
    }

    /**
     * Forgot password test data provider
     * @return 2D array of test data [email, expectedResult]
     */
    @DataProvider(name = "forgetPasswordData")
    public Object[][] getForgetPasswordData() {
        return new Object[][] {
            { "test@postbackdaddy.com", "success" },
            { "nonexistent@postbackdaddy.com", "failure" },
            { "", "failure" },
            { "invalid-email", "failure" }
        };
    }

    /**
     * Reset password test data provider
     * @return 2D array of test data [token, newPassword, confirmPassword, expectedResult]
     */
    @DataProvider(name = "resetPasswordData")
    public Object[][] getResetPasswordData() {
        return new Object[][] {
            { "valid-token-123", "NewPass@123", "NewPass@123", "success" },
            { "invalid-token", "NewPass@123", "NewPass@123", "failure" },
            { "valid-token-123", "NewPass@123", "DifferentPass@123", "failure" },
            { "", "NewPass@123", "NewPass@123", "failure" }
        };
    }

    /**
     * User registration test data provider
     * @return 2D array of test data
     */
    @DataProvider(name = "registrationData")
    public Object[][] getRegistrationData() {
        return new Object[][] {
            { "newuser@postbackdaddy.com", "FirstName", "LastName", "Pass@123", "success" },
            { "existing@postbackdaddy.com", "FirstName", "LastName", "Pass@123", "failure" },
            { "invalid-email", "FirstName", "LastName", "Pass@123", "failure" }
        };
    }
}

