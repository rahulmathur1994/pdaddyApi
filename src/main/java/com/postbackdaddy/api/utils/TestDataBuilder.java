package com.postbackdaddy.api.utils;

import com.postbackdaddy.api.models.request.LoginRequest;
import com.postbackdaddy.api.models.request.ForgetPasswordRequest;
import com.postbackdaddy.api.models.request.ResetPasswordRequest;

/**
 * Test Data Builder Utility
 * Builds test objects with default or custom values
 */
public class TestDataBuilder {

    /**
     * Build Login Request with default values
     * @return LoginRequest object
     */
    public static LoginRequest buildDefaultLoginRequest() {
        return new LoginRequest(
            "test@postbackdaddy.com",
            "Test@123",
            true,
            "12345678"
        );
    }

    /**
     * Build Login Request with custom email and password
     * @param email Email address
     * @param password Password
     * @return LoginRequest object
     */
    public static LoginRequest buildLoginRequest(String email, String password) {
        return new LoginRequest(email, password, true, "12345678");
    }

    /**
     * Build Forget Password Request with email
     * @param email Email address
     * @return ForgetPasswordRequest object
     */
    public static ForgetPasswordRequest buildForgetPasswordRequest(String email) {
        return new ForgetPasswordRequest(email);
    }

    /**
     * Build Reset Password Request
     * @param token Reset token
     * @param newPassword New password
     * @param confirmPassword Confirm password
     * @return ResetPasswordRequest object
     */
    public static ResetPasswordRequest buildResetPasswordRequest(
            String token, String newPassword, String confirmPassword) {
        return new ResetPasswordRequest(token, newPassword, confirmPassword);
    }

    /**
     * Build Reset Password Request with matching passwords
     * @param token Reset token
     * @param newPassword New password
     * @return ResetPasswordRequest object
     */
    public static ResetPasswordRequest buildResetPasswordRequest(String token, String newPassword) {
        return new ResetPasswordRequest(token, newPassword, newPassword);
    }
}
