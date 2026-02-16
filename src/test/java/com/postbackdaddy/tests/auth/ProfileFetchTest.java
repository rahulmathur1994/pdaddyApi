package com.postbackdaddy.tests.auth;

import com.postbackdaddy.api.models.response.ProfileResponse;
import com.postbackdaddy.api.services.ProfileService;
import com.postbackdaddy.api.utils.TokenManager;
import com.postbackdaddy.tests.base.BaseTest;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

/**
 * Profile Fetch Test
 * Depends on successful login (testSuccessfulLogin)
 * Uses stored token from login to fetch and print profile data
 *
 * IMPORTANT: Do NOT clear tokens to reuse token from login test
 */
public class ProfileFetchTest extends BaseTest {

    private ProfileService profileService = new ProfileService();

    @BeforeMethod(alwaysRun = true)
    public void setUpTest(ITestContext context) {
        // Override parent's @BeforeMethod to prevent token clearing
        // Get test name from context
        String testName = context.getName();
        // Create extent test (same as parent BaseTest)
        extentTest = extentReports.createTest(testName);
        extentTest.info("Test: " + testName + " Started");

        // Log that we're reusing token from login
        String tokenStatus = TokenManager.hasAuthToken() ? "✓ Available" : "✗ NOT Available";
        System.out.println("\n>>> Token Status Before Test: " + tokenStatus);
        extentTest.info("Setup: Reusing stored access token from previous login test. Status: " + tokenStatus);
    }

    @Test(description = "Fetch profile", dependsOnGroups = "auth")
    public void testFetchProfile() {
        extentTest.info("=== Fetching Profile ===");

        // Reuse token from successful login
        String accessToken = TokenManager.getAuthToken();
        System.out.println(">>> Token Value: " + (accessToken != null ? accessToken.substring(0, Math.min(30, accessToken.length())) + "..." : "NULL"));

        Assert.assertNotNull(accessToken, "Access token should be available from login");

        extentTest.info("Using stored access token from login");

        // Fetch profile
        ProfileResponse profile = profileService.getProfileData();
        Assert.assertNotNull(profile, "Profile response should not be null");
        Assert.assertNotNull(profile.getData(), "Profile data should not be null");

        extentTest.info("Profile fetched successfully");

        // Extract and print profile data
        ProfileResponse.ProfileData data = profile.getData();
        String userId = data.getId();
        String roleId = data.getRoleId();

        // Print profile details
        System.out.println("\n" + "=".repeat(70));
        System.out.println(">>> PROFILE DATA <<<");
        System.out.println("=".repeat(70));
        System.out.println("User ID    : " + userId);
        System.out.println("Role ID    : " + roleId);
        System.out.println("Email      : " + data.getEmail());
        System.out.println("Full Name  : " + data.getFirstName() + " " + data.getLastName());
        System.out.println("Status     : " + data.getStatus());
        System.out.println("Created At : " + data.getCreatedAt());
        System.out.println("=".repeat(70) + "\n");

        // Log to report
        extentTest.info("User ID: " + userId);
        extentTest.info("Role ID: " + roleId);
        extentTest.info("Email: " + data.getEmail());
        extentTest.info("Full Name: " + data.getFirstName() + " " + data.getLastName());

        profileService.printProfileDetails(profile);
        extentTest.pass("✓ Profile fetched and printed successfully");
    }

    @AfterClass(alwaysRun = true)
    public void tearDownExtentReports() {
        // Override parent's tearDownExtentReports to NOT clear tokens
        // This allows dependent tests to access the stored token
        if (extentReports != null) {
            extentReports.flush();
        }
        // DO NOT clear tokens here - they may be needed by dependent tests
        System.out.println(">>> ProfileFetchTest.tearDownExtentReports: NOT clearing tokens (preserved for dependencies)");
    }
}