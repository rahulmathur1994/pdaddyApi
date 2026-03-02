package com.postbackdaddy.api.store;

/**
 * Stores created connection runtime data
 */
public class ConnectionRuntimeStore {

    private static String connectionId;
    private static String connectionName;

    public static void setConnection(String id, String name) {
        connectionId = id;
        connectionName = name;
    }

    public static String getConnectionId() {
        return connectionId;
    }

    public static String getConnectionName() {
        return connectionName;
    }

    public static void clear() {
        connectionId = null;
        connectionName = null;
    }
}