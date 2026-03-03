package com.postbackdaddy.api.models.request;

public class ConnectionConfigRequest {

    private String email;
    private String password;
    private boolean rememberMe;
    private String deviceId;

    public ConnectionConfigRequest(String email, String password,
                                   boolean rememberMe, String deviceId) {
        this.email = email;
        this.password = password;
        this.rememberMe = rememberMe;
        this.deviceId = deviceId;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public boolean isRememberMe() { return rememberMe; }
    public String getDeviceId() { return deviceId; }

}