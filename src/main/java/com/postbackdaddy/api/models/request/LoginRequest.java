package com.postbackdaddy.api.models.request;

public class LoginRequest {
    private String email;
    private String password;
    private boolean rememberMe;
    private String deviceId;

    public LoginRequest() {}

    public LoginRequest(String email, String password, boolean rememberMe, String deviceId) {
        this.email = email;
        this.password = password;
        this.rememberMe = rememberMe;
        this.deviceId = deviceId;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean isRememberMe() { return rememberMe; }
    public void setRememberMe(boolean rememberMe) { this.rememberMe = rememberMe; }
    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
}

