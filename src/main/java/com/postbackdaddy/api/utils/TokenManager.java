package com.postbackdaddy.api.utils;

/**
 * Token Manager Utility
 * Manages authentication tokens for API requests
 *
 * Uses static variables (not ThreadLocal) to preserve tokens across dependent test methods
 */
public class TokenManager {

    // Static variables to persist tokens across test executions
    // ThreadLocal doesn't work well with TestNG's test dependency model
    private static String authToken;
    private static String refreshToken;

    /**
     * Set authentication token
     * @param token Authentication token
     */
    public static void setAuthToken(String token) {
        authToken = token;
    }

    /**
     * Get authentication token
     * @return Authentication token
     */
    public static String getAuthToken() {
        return authToken;
    }

    /**
     * Set refresh token
     * @param token Refresh token
     */
    public static void setRefreshToken(String token) {
        refreshToken = token;
    }

    /**
     * Get refresh token
     * @return Refresh token
     */
    public static String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Clear all tokens
     */
    public static void clearTokens() {
        authToken = null;
        refreshToken = null;
    }

    /**
     * Check if token exists
     * @return true if token exists, false otherwise
     */
    public static boolean hasAuthToken() {
        return authToken != null && !authToken.isEmpty();
    }

    /**
     * Check if refresh token exists
     * @return true if refresh token exists, false otherwise
     */
    public static boolean hasRefreshToken() {
        return refreshToken != null && !refreshToken.isEmpty();
    }

}

