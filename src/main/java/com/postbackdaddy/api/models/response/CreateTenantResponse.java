package com.postbackdaddy.api.models.response;

import lombok.Data;

import java.util.List;

@Data
public class CreateTenantResponse {

    private boolean status;
    private int statusCode;
    private String message;
    private TenantData data;
    private Object meta;
    private List<Object> error;

    @Data
    public static class TenantData {

        private String id;
        private String created_at;
        private String updated_at;
        private String deleted_at;
        private String created_by;
        private String updated_by;
        private String deleted_by;
        private String email;
        private String name;
        private String phone_number;
        private String country_code;
        private String status;
    }
}
