package com.postbackdaddy.api.services;

import com.postbackdaddy.api.base.BaseSpecBuilder;
import com.postbackdaddy.api.constants.Endpoints;
import com.postbackdaddy.api.models.response.GetTenantsResponse;
import com.postbackdaddy.api.utils.ConfigReader;
import com.postbackdaddy.api.utils.TokenManager;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Tenant List Service
 * Fetches list of tenants with pagination support
 * Stores tenant IDs and names for later use in other tests
 */
public class GetTenantList {

    private static final Logger logger = LoggerFactory.getLogger(GetTenantList.class);
    private String baseURI;

    // Static storage for tenant data (can be accessed across tests)
    private static final Map<String, String> tenantIdMap = new HashMap<>();  // id -> name
    private static final List<String> tenantIds = new ArrayList<>();

    public GetTenantList() {
        this.baseURI = ConfigReader.getBaseURI();
    }

    public GetTenantList(String baseURI) {
        this.baseURI = baseURI;
    }

    /**
     * Fetch tenants list with pagination
     *
     * @param page   Page number (default: 1)
     * @param limit  Records per page (default: 10)
     * @return GetTenantsResponse with tenant list
     */
    public GetTenantsResponse getTenants(int page, int limit) {
        String accessToken = TokenManager.getAuthToken();

        if (accessToken == null || accessToken.isEmpty()) {
            logger.error("No authentication token available");
            throw new IllegalStateException("User not authenticated. Please login first.");
        }

        logger.info("Fetching tenants list with page={}, limit={}", page, limit);

        Response response = given()
                .spec(BaseSpecBuilder.getRequestSpecWithAuth(baseURI, accessToken))
                .queryParam("page", page)
                .queryParam("limit", limit)
                .when()
                .get(Endpoints.GET_TENANTS)
                .then()
                .extract()
                .response();

        logger.info("Get tenants response: statusCode={}", response.getStatusCode());
        logger.debug("Response body: {}", response.getBody().asString());

        if (response.getStatusCode() == 200) {
            try {
                GetTenantsResponse tenantsResponse = response.as(GetTenantsResponse.class);

                // Cache tenant IDs and names
                cacheTenantData(tenantsResponse);

                logger.info("Tenants fetched successfully. Total: {}",
                           tenantsResponse.getMeta() != null ? tenantsResponse.getMeta().getTotal() : 0);

                return tenantsResponse;
            } catch (Exception e) {
                logger.error("Failed to parse tenants response: {}", e.getMessage(), e);
                return null;
            }
        } else {
            logger.error("Failed to fetch tenants. Status: {}", response.getStatusCode());
            return null;
        }
    }

    /**
     * Fetch tenants with default pagination (page=1, limit=10)
     *
     * @return GetTenantsResponse with tenant list
     */
    public GetTenantsResponse getTenants() {
        return getTenants(1, 10);
    }

    /**
     * Cache tenant data in static map for later access
     * Stores: tenant ID -> tenant Name mapping
     *
     * @param response GetTenantsResponse containing tenant data
     */
    private void cacheTenantData(GetTenantsResponse response) {
        if (response != null && response.getData() != null) {
            tenantIdMap.clear();
            tenantIds.clear();

            for (GetTenantsResponse.TenantData tenant : response.getData()) {
                tenantIdMap.put(tenant.getId(), tenant.getName());
                tenantIds.add(tenant.getId());
                logger.debug("Cached tenant - ID: {}, Name: {}", tenant.getId(), tenant.getName());
            }

            logger.info("Total tenants cached: {}", tenantIdMap.size());
        }
    }

    /**
     * Print all tenants in a formatted table
     *
     * @param response GetTenantsResponse with tenant data
     */
    public void printTenantsList(GetTenantsResponse response) {
        if (response != null && response.getData() != null && !response.getData().isEmpty()) {
            System.out.println("\n" + "=".repeat(100));
            System.out.println(">>> TENANTS LIST <<<");
            System.out.println("=".repeat(100));
            System.out.printf("%-40s | %-30s | %-15s | %-15s%n", "ID", "Name", "Email", "Status");
            System.out.println("-".repeat(100));

            for (GetTenantsResponse.TenantData tenant : response.getData()) {
                System.out.printf("%-40s | %-30s | %-15s | %-15s%n",
                        tenant.getId(),
                        tenant.getName(),
                        tenant.getEmail(),
                        tenant.getStatus());
            }

            System.out.println("=".repeat(100));

            if (response.getMeta() != null) {
                System.out.println("\nPagination Info:");
                System.out.println("  Total Records: " + response.getMeta().getTotal());
                System.out.println("  Current Page:  " + response.getMeta().getPage());
                System.out.println("  Limit:         " + response.getMeta().getLimit());
                System.out.println("  Total Pages:   " + response.getMeta().getTotal_page());
            }
            System.out.println();

            logger.info("Tenants list printed to console");
        } else {
            System.out.println("\nNo tenants data available to print.");
            logger.warn("No tenants data available");
        }
    }

    /**
     * Get cached tenant name by ID
     *
     * @param tenantId The tenant ID
     * @return Tenant name or null if not found
     */
    public static String getTenantNameById(String tenantId) {
        return tenantIdMap.get(tenantId);
    }

    /**
     * Get all cached tenant IDs
     *
     * @return List of cached tenant IDs
     */
    public static List<String> getAllCachedTenantIds() {
        return new ArrayList<>(tenantIds);
    }

    /**
     * Get all cached tenant ID-Name mappings
     *
     * @return Map of tenant ID -> Name
     */
    public static Map<String, String> getAllCachedTenants() {
        return new HashMap<>(tenantIdMap);
    }

    /**
     * Get first tenant ID from cache
     *
     * @return First tenant ID or null if no tenants cached
     */
    public static String getFirstTenantId() {
        return tenantIds.isEmpty() ? null : tenantIds.get(0);
    }

    /**
     * Get the second tenant ID from cache
     *
     * @return Second tenant ID or null if not available
     */
    public static String getSecondTenantId() {
        return tenantIds.size() > 1 ? tenantIds.get(1) : null;
    }
    /**
     * Get tenant ID by index
     *
     * @param index Index in cached list
     * @return Tenant ID at index or null if index out of bounds
     */
    public static String getTenantIdByIndex(int index) {
        return (index >= 0 && index < tenantIds.size()) ? tenantIds.get(index) : null;
    }



    /**
     * Clear all cached tenant data
     */
    public static void clearCache() {
        tenantIdMap.clear();
        tenantIds.clear();
        logger.info("Tenant cache cleared");
    }

    /**
     * Print cached tenant IDs
     */
    public static void printCachedTenants() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(">>> CACHED TENANT IDs <<<");
        System.out.println("=".repeat(60));

        if (tenantIdMap.isEmpty()) {
            System.out.println("No cached tenants.");
        } else {
            int index = 1;
            for (Map.Entry<String, String> entry : tenantIdMap.entrySet()) {
                System.out.println(index + ". ID: " + entry.getKey() + " | Name: " + entry.getValue());
                index++;
            }
        }

        System.out.println("=".repeat(60) + "\n");
    }
}
