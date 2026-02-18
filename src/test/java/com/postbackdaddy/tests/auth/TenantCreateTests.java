package com.postbackdaddy.tests.auth;

import com.postbackdaddy.api.models.request.CreateTenantRequest;
import com.postbackdaddy.api.models.response.CreateTenantResponse;
import com.postbackdaddy.api.services.TenantService;
import com.postbackdaddy.api.services.TenantServiceImpl;
import com.postbackdaddy.api.constants.AppConstants;
import com.postbackdaddy.api.utils.TokenManager;
import com.postbackdaddy.tests.base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.SkipException;

/**
 * Tenant Creation Test Class
 * Tests tenant creation via API
 */
public class TenantCreateTests extends BaseTest {

    private TenantService tenantService;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        tenantService = new TenantServiceImpl();
    }

    @Test
    public void testSuccessfulTenantCreation() {

        extentTest.info("Testing successful tenant creation");

        String accessToken = TokenManager.getAuthToken();

        if (accessToken == null || accessToken.isEmpty()) {
            extentTest.warning("Access token missing. Run login test first.");
            throw new SkipException("No valid access token available.");
        }

        // ===== Create Request =====
        CreateTenantRequest tenantRequest = new CreateTenantRequest();
        tenantRequest.setName("Tanent LLC");
        tenantRequest.setEmail("test14+ccc@codeclouds.in");
        tenantRequest.setCountry_code("+1");
        tenantRequest.setPhone_number("7894561236");
        tenantRequest.setStatus("ACTIVE");

        extentTest.info("Creating tenant: " + tenantRequest.getName());

        try {
            // ===== API CALL =====
            CreateTenantResponse response =
                    tenantService.createTenant(tenantRequest, accessToken);

            Assert.assertNotNull(response, "API response should not be null");

            extentTest.info("Status Code: " + response.getStatusCode());
            extentTest.info("Message: " + response.getMessage());

            // ===== CORE VALIDATION (Enough for API success) =====
            Assert.assertEquals(response.getStatusCode(), 201,
                    "Tenant creation failed: " + response.getMessage());

            Assert.assertTrue(response.isStatus(),
                    "API returned unsuccessful status");

            // ===== Extract Created Tenant =====
            CreateTenantResponse.TenantData data = response.getData();

            Assert.assertNotNull(data, "Tenant data should exist");

            // ===== PRINT CREATED TENANT DETAILS =====
            System.out.println("\n" + "=".repeat(70));
            System.out.println(">>> CREATED TENANT DETAILS <<<");
            System.out.println("=".repeat(70));
            System.out.println("Tenant ID     : " + data.getId());
            System.out.println("Name          : " + data.getName());
            System.out.println("Email         : " + data.getEmail());
            System.out.println("Phone         : " + data.getCountry_code()
                    + " " + data.getPhone_number());
            System.out.println("Status        : " + data.getStatus());
            System.out.println("Created At    : " + data.getCreated_at());
            System.out.println("Created By    : " + data.getCreated_by());
            System.out.println("=".repeat(70) + "\n");

            // ===== SAVE TENANT ID FOR FUTURE REQUESTS =====
            TokenManager.setTenantId(data.getId());
            System.out.println("✓ Tenant ID saved to TokenManager: " + TokenManager.getTenantId() + "\n");

            // ===== EXTENT REPORT LOGGING =====
            extentTest.info("Tenant ID: " + data.getId());
            extentTest.info("Tenant Email: " + data.getEmail());
            extentTest.info("Tenant Status: " + data.getStatus());
            extentTest.info("✓ Tenant ID saved to TokenManager for future requests");

            extentTest.pass("Tenant created successfully");

        } catch (Exception e) {
            extentTest.fail("Exception during tenant creation: " + e.getMessage());
            throw e;
        }
    }



    @Test(groups = "tenant")
    public void testTenantCreationWithDuplicateEmail() {
        extentTest.info("Testing tenant creation with duplicate email");

        String accessToken = TokenManager.getAuthToken();

        CreateTenantRequest tenantRequest = new CreateTenantRequest();
        tenantRequest.setName("DuplicateTenant");
        tenantRequest.setEmail("test12+cccc@codeclouds.in");  // Duplicate email
        tenantRequest.setCountry_code("+1");
        tenantRequest.setPhone_number("9876543210");
        tenantRequest.setStatus("ACTIVE");

        try {
            CreateTenantResponse response = tenantService.createTenant(tenantRequest, accessToken);
            Assert.assertNotEquals(response.getStatusCode(), 201, "Should fail with duplicate email");
            extentTest.pass("Duplicate email error received as expected");
        } catch (Exception e) {
            extentTest.pass("Exception received as expected");
        }
    }
}

