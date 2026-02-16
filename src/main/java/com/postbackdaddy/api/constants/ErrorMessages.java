package com.postbackdaddy.api.constants;

/**
 * Error Messages and Codes
 * Contains all error messages and codes returned by API
 */
public class ErrorMessages {

    // Authentication Errors
    public static final String INVALID_CREDENTIALS = "Invalid email or password";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_ALREADY_EXISTS = "User already exists";
    public static final String INVALID_TOKEN = "Invalid or expired token";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized access";

    // Password Related Errors
    public static final String PASSWORD_RESET_LINK_EXPIRED = "Password reset link has expired";
    public static final String INVALID_OTP = "Invalid or expired OTP";
    public static final String OTP_ALREADY_VERIFIED = "OTP already verified";

    // Validation Errors
    public static final String INVALID_EMAIL_FORMAT = "Invalid email format";
    public static final String WEAK_PASSWORD = "Password does not meet security requirements";
    public static final String MISSING_REQUIRED_FIELD = "Missing required field";

    // Server Errors
    public static final String INTERNAL_SERVER_ERROR = "Internal server error";
    public static final String SERVICE_UNAVAILABLE = "Service temporarily unavailable";
}

