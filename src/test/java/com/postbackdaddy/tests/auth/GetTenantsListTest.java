package com.postbackdaddy.tests.auth;

import com.postbackdaddy.api.models.response.GetTenantsResponse;
import com.postbackdaddy.api.services.GetTenantList;
import com.postbackdaddy.api.utils.TokenManager;
import com.postbackdaddy.tests.base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.SkipException;

/**
 * Get Tenants List Test
 * Fetches list of tenants, prints details, and caches tenant IDs for use in other tests
 */
public class GetTenantsListTest extends BaseTest {

    private GetTenantList tenantListService;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        tenantListService = new GetTenantList();
    }

    @Test(
        description = "Fetch and display tenants list",
        dependsOnMethods = "com.postbackdaddy.tests.auth.LoginTests.testSuccessfulLogin"
    )
    public void testGetTenantsList() {
        extentTest.info("=== Fetching Tenants List ===");

        String accessToken = TokenManager.getAuthToken();

        if (accessToken == null || accessToken.isEmpty()) {
            extentTest.warning("Access token missing.");
            throw new SkipException("No valid access token available.");
        }

        extentTest.info("Using access token from login");

        try {
            // Fetch tenants with pagination (page=1, limit=10)
            GetTenantsResponse response = tenantListService.getTenants(1, 10);

            Assert.assertNotNull(response, "Tenants response should not be null");
            Assert.assertTrue(response.isStatus(), "Response status should be true");
            Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

            extentTest.info("Response Status: " + response.getStatusCode());
            extentTest.info("Message: " + response.getMessage());

            // Verify data exists
            Assert.assertNotNull(response.getData(), "Tenants data should not be null");
            Assert.assertFalse(response.getData().isEmpty(), "Tenants list should not be empty");

            // Print tenants list to console
            System.out.println("\n>>> FETCHED TENANTS <<<");
            tenantListService.printTenantsList(response);

            // Extract and log tenant information
            int tenantCount = response.getData().size();
            extentTest.info("Total tenants in response: " + tenantCount);

            // Print each tenant ID and name
            System.out.println("\n>>> TENANT IDs AND NAMES <<<");
            int index = 1;
            for (GetTenantsResponse.TenantData tenant : response.getData()) {
                String tenantId = tenant.getId();
                String tenantName = tenant.getName();
                String email = tenant.getEmail();
                String status = tenant.getStatus();

                System.out.println(index + ". Tenant ID: " + tenantId + " | Name: " + tenantName +
                                 " | Email: " + email + " | Status: " + status);

                extentTest.info("Tenant " + index + " - ID: " + tenantId + ", Name: " + tenantName +
                               ", Status: " + status);
                index++;
            }

            // Print cached tenant IDs (stored in service)
            System.out.println("\n>>> CACHED TENANT IDs FOR LATER USE <<<");
            GetTenantList.printCachedTenants();

            extentTest.info("Tenant IDs cached successfully for use in other tests");

            // Verify pagination info
            if (response.getMeta() != null) {
                int total = response.getMeta().getTotal();
                int page = response.getMeta().getPage();
                int limit = response.getMeta().getLimit();
                int totalPages = response.getMeta().getTotal_page();

                extentTest.info("Pagination - Total: " + total + ", Page: " + page +
                               ", Limit: " + limit + ", Total Pages: " + totalPages);
            }

            extentTest.pass("✓ Tenants list fetched successfully");

        } catch (Exception e) {
            extentTest.fail("Exception during tenant list fetch: " + e.getMessage());
            throw e;
        }
    }

    @Test(
        description = "Fetch tenants with custom pagination",
        dependsOnMethods = "com.postbackdaddy.tests.auth.LoginTests.testSuccessfulLogin"
    )
    public void testGetTenantsWithCustomPagination() {
        extentTest.info("=== Fetching Tenants with Custom Pagination ===");

        String accessToken = TokenManager.getAuthToken();

        if (accessToken == null || accessToken.isEmpty()) {
            extentTest.warning("Access token missing.");
            throw new SkipException("No valid access token available.");
        }

        try {
            // Fetch with custom page and limit
            int page = 1;
            int limit = 5;

            extentTest.info("Fetching tenants - Page: " + page + ", Limit: " + limit);

            GetTenantsResponse response = tenantListService.getTenants(page, limit);

            Assert.assertNotNull(response, "Tenants response should not be null");
            Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
            Assert.assertNotNull(response.getData(), "Tenants data should not be null");

            // Print results
            System.out.println("\n>>> TENANTS (Page " + page + ", Limit " + limit + ") <<<");
            tenantListService.printTenantsList(response);

            extentTest.info("Tenants fetched with custom pagination");
            extentTest.info("Records returned: " + response.getData().size());

            extentTest.pass("✓ Custom pagination test passed");

        } catch (Exception e) {
            extentTest.fail("Exception during custom pagination test: " + e.getMessage());
            throw e;
        }
    }
}

