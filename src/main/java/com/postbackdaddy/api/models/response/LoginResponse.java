package com.postbackdaddy.api.models.response;

import java.util.List;

public class LoginResponse {
    private boolean status;
    private int statusCode;
    private String message;
    private Data data;
    private Meta meta;
    private List<String> error;

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Data getData() { return data; }
    public void setData(Data data) { this.data = data; }
    public Meta getMeta() { return meta; }
    public void setMeta(Meta meta) { this.meta = meta; }
    public List<String> getError() { return error; }
    public void setError(List<String> error) { this.error = error; }

    public static class Data {
        private String id;
        private String email;
        private String firstName;
        private String lastName;
        private String status;
        private String role_id;
        private String role;
        private String tenant_id;
        private List<String> permissions;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getRole_id() { return role_id; }
        public void setRole_id(String role_id) { this.role_id = role_id; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public String getTenant_id() { return tenant_id; }
        public void setTenant_id(String tenant_id) { this.tenant_id = tenant_id; }
        public List<String> getPermissions() { return permissions; }
        public void setPermissions(List<String> permissions) { this.permissions = permissions; }
    }

    public static class Meta {
        private String access_token;
        private String refresh_token;

        public String getAccess_token() { return access_token; }
        public void setAccess_token(String access_token) { this.access_token = access_token; }
        public String getRefresh_token() { return refresh_token; }
        public void setRefresh_token(String refresh_token) { this.refresh_token = refresh_token; }
    }
}

