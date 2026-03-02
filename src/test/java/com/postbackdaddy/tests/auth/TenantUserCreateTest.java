package com.postbackdaddy.tests.auth;

import com.postbackdaddy.api.models.request.TenantUserCreateRequest;
import com.postbackdaddy.api.models.response.TenantUserCreateResponse;
import com.postbackdaddy.api.services.GetTenantList;
import com.postbackdaddy.api.services.TenantUserCreate;
import com.postbackdaddy.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

/**
 * Tenant User Creation Test
 * Uses cached tenantId from GetTenantList
 */
public class TenantUserCreateTest extends BaseTest {

    private final TenantUserCreate tenantUserService =
            new TenantUserCreate();

    // Fixed Tenant User Role ID
    private static final String TENANT_USER_ROLE_ID =
            "1d703cfa-4619-4b82-8701-efc190abc324";

    @Test(description = "Create Tenant User using cached tenantId")
    public void testCreateTenantUser() {

        extentTest.info("=== Tenant User Creation Started ===");

        // ✅ Fetch cached tenantId dynamically
        //String tenantId = GetTenantList.getSecondTenantId();
        String tenantId = GetTenantList.fetchTenantId(1);

        if (tenantId == null) {
            extentTest.warning("Tenant ID not available.");
            throw new SkipException("No cached tenant available.");
        }

        extentTest.info("Using Tenant ID: " + tenantId);

        // Build request body
        TenantUserCreateRequest request =
                new TenantUserCreateRequest();

        // unique email every run
        String email =
                "tenantuser" + System.currentTimeMillis()
                        + "@codeclouds.in";

        request.setEmail(email);
        request.setPassword("12345678");
        request.setFirstName("Tenant");
        request.setLastName("User");
        request.setRoleId(TENANT_USER_ROLE_ID);

        extentTest.info("Creating user with email: " + email);

        TenantUserCreateResponse response =
                tenantUserService.createTenantUser(tenantId, request);

        // ===== ASSERTIONS =====

        Assert.assertNotNull(response, "Response should not be null");
        Assert.assertTrue(response.isStatus(), "Status should be true");
        Assert.assertEquals(response.getStatusCode(), 201);

        Assert.assertEquals(
                response.getData().getTenantId(),
                tenantId,
                "TenantId should match"
        );

        Assert.assertEquals(
                response.getData().getRoleId(),
                TENANT_USER_ROLE_ID
        );

        extentTest.pass("✅ Tenant user created successfully");
    }
}