package com.postbackdaddy.tests.auth;

import com.postbackdaddy.api.services.ConnectionConfigService;
import com.postbackdaddy.api.store.ConnectionConfigStore;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ConnectionConfigFetchTest {

    @Test(description = "Fetch TARGET connection configs and store targetId")
    public void testFetchTargetConnectionConfig() {

        Response response =
                ConnectionConfigService.fetchTargetConnectionConfig();

        Assert.assertEquals(response.getStatusCode(), 200);

        boolean status =
                response.jsonPath().getBoolean("status");

        Assert.assertTrue(status, "Status should be true");

        String targetId = ConnectionConfigStore.getTargetId();

        Assert.assertNotNull(targetId, "targetId must be saved");

        System.out.println("Stored TARGET ID = " + targetId);
    }

    @Test(description = "Fetch SOURCE connection configuration")
    public void testFetchSourceConfig() {

        Response response =
                ConnectionConfigService.fetchSourceConnectionConfig();

        Assert.assertEquals(response.getStatusCode(), 200);

        String sourceId = ConnectionConfigStore.getSourceId();

        Assert.assertNotNull(sourceId,
                "Source ID should be saved");

        System.out.println("SOURCE ID = " + sourceId);
    }

}
