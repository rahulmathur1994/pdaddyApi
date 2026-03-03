package com.postbackdaddy.api.store;

/**
 * Stores connection configuration IDs globally
 * Accessible across tests
 */
public class ConnectionConfigStore {

    private static String targetId;
    private static String sourceId;

    private static String sourceConnectionId;
    private static String sourceConnectionName;

    // ================= TARGET CONFIG =================

    public static void setTargetId(String id)
    {

        targetId = id;
    }

    public static String getTargetId()
    {

        return targetId;
    }

    // ===== SOURCE =====
    public static void setSourceId(String id)
    {

        sourceId = id;
    }

    public static String getSourceId()
    {

        return sourceId;
    }


    // SOURCE CONNECTION DATA
    public static void setSourceConnection(String id, String name) {
        sourceConnectionId = id;
        sourceConnectionName = name;
    }

    public static String getSourceConnectionId() {
        return sourceConnectionId;
    }

    public static String getSourceConnectionName() {
        return sourceConnectionName;
    }
    // Optional cleanup
    public static void clear() {
        targetId = null;
        sourceId = null;
    }

}