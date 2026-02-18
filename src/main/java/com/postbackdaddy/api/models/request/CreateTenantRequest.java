package com.postbackdaddy.api.models.request;

import lombok.Data;

@Data
public class CreateTenantRequest {

    private String name;
    private String email;
    private String country_code;
    private String phone_number;
    private String status;
}
