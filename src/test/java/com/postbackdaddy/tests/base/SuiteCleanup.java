package com.postbackdaddy.tests.base;
import com.postbackdaddy.api.utils.TokenManager;
import org.testng.annotations.AfterSuite;


public class SuiteCleanup {

    @AfterSuite(alwaysRun = true)
    public void cleanTokensAfterSuite() {
        TokenManager.clearTokens();
        System.out.println(">>> Tokens cleared after entire test suite");
    }
}
