package com.postbackdaddy.api.store;

/**
 * Stores connection configuration IDs globally
 * Accessible across tests
 */
public class ConnectionConfigStore {

    private static String targetId;
    private static String sourceId;

    // ===== TARGET =====
    public static void setTargetId(String id) {
        targetId = id;
    }

    public static String getTargetId() {
        return targetId;
    }

    // ===== SOURCE =====
    public static void setSourceId(String id) {
        sourceId = id;
    }

    public static String getSourceId() {
        return sourceId;
    }

    // Optional cleanup
    public static void clear() {
        targetId = null;
        sourceId = null;
    }
}