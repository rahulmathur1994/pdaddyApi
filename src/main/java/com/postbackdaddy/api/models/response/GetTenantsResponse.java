package com.postbackdaddy.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

/**
 * Get Tenants List Response Model
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetTenantsResponse {
    private boolean status;
    private int statusCode;
    private String message;
    private List<TenantData> data;
    private Meta meta;
    private Object[] error;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TenantData {
        private String id;
        private String name;
        private String email;
        private String status;
        private String phone_number;
        private String country_code;

        @JsonProperty("created_at")
        private String createdAt;

        @JsonProperty("updated_at")
        private String updatedAt;

        @JsonProperty("created_by")
        private String createdBy;

        @JsonProperty("updated_by")
        private String updatedBy;

        @JsonProperty("deleted_at")
        private String deletedAt;

        @JsonProperty("deleted_by")
        private String deletedBy;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Meta {
        private int total;
        private int page;
        private int limit;
        private int total_page;
    }
}
