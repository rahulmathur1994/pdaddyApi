package com.postbackdaddy.api.constants;

/**
 * API Endpoints configuration class
 * Contains all API endpoint URLs
 */
public class Endpoints {

    // Authentication Endpoints
    public static final String LOGIN = "/v1/auth/login";
    public static final String LOGOUT = "/v1/auth/logout";
    public static final String FORGOT_PASSWORD = "/api/v1/auth/forgot-password";
    public static final String RESET_PASSWORD = "/api/v1/auth/reset-password";
    public static final String VERIFY_OTP = "/api/v1/auth/verify-otp";
    public static final String REFRESH_TOKEN = "/v1/auth/refresh";
    public static final String USER_VALIDATE = "/v1/auth/validate";

    // User Endpoints
    public static final String GET_USER_PROFILE = "/api/v1/users/profile";
    public static final String UPDATE_USER_PROFILE = "/api/v1/users/profile";
    public static final String DELETE_USER_ACCOUNT = "/api/v1/users/account";
    public static final String PROFILE = "/v1/profile";

    // Additional Endpoints can be added here
}
