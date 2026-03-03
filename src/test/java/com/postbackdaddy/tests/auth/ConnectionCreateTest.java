package com.postbackdaddy.tests.auth;

import com.postbackdaddy.api.services.ConnectionService;
import com.postbackdaddy.api.services.GetTenantList;
import com.postbackdaddy.api.store.ConnectionRuntimeStore;
import com.postbackdaddy.tests.base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Connection Creation Tests
 */
public class ConnectionCreateTest extends BaseTest {

    @Test(description = "Create TARGET Connection using cached tenant + target config")
    public void testCreateTargetConnection() {

        // ✅ tenantId dynamically from tenant cache
        String tenantId = GetTenantList.getFirstTenantId();

        Assert.assertNotNull(tenantId,
                "TenantId should be available before connection creation");

        Response response =
                ConnectionService.createTargetConnection(tenantId);

        Assert.assertEquals(response.getStatusCode(), 201);

        // ✅ Validate saved runtime data
        String connectionId =
                ConnectionRuntimeStore.getConnectionId();

        String connectionName =
                ConnectionRuntimeStore.getConnectionName();

        Assert.assertNotNull(connectionId,
                "Connection ID must be stored");

        Assert.assertNotNull(connectionName,
                "Connection Name must be stored");

        System.out.println("Created Connection ID: " + connectionId);
        System.out.println("Created Connection Name: " + connectionName);
    }

    @Test(priority = 4, dependsOnMethods = "testFetchSourceConfig")
    public void createSourceConnectionTest() {

        String tenantId = GetTenantList.getFirstTenantId();

        ConnectionService service = new ConnectionService();
        Response response = service.createSourceConnection(tenantId);

       // Assert.assertEquals(response.getStatusCode(), 201);
    }
}