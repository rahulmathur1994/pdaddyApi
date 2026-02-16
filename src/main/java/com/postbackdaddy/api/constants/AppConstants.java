package com.postbackdaddy.api.constants;

/**
 * Application Constants
 * Contains fixed values and configuration constants
 */
public class AppConstants {

    // API Response Status Codes
    public static final int STATUS_CODE_OK = 200;
    public static final int STATUS_CODE_CREATED = 201;
    public static final int STATUS_CODE_BAD_REQUEST = 400;
    public static final int STATUS_CODE_UNAUTHORIZED = 401;
    public static final int STATUS_CODE_FORBIDDEN = 403;
    public static final int STATUS_CODE_NOT_FOUND = 404;
    public static final int STATUS_CODE_INTERNAL_SERVER_ERROR = 500;

    // Response Messages
    public static final String SUCCESS_MESSAGE = "Success";
    public static final String ERROR_MESSAGE = "Error";

    // Token related
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String CONTENT_TYPE_JSON = "application/json";

    // Default Timeout (in seconds)
    public static final int DEFAULT_TIMEOUT = 10;

    // Test Data Constants
    public static final String TEST_USER_EMAIL = "test@postbackdaddy.com";
    public static final String TEST_USER_PASSWORD = "Test@123";
}

