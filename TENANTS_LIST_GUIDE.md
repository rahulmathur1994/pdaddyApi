# Tenants List API Integration - Complete Guide

## Overview

This implementation provides a complete solution for fetching, displaying, and caching tenant lists from the API. The tenant IDs are cached and can be reused in other test methods without making redundant API calls.

---

## Features

✅ **Fetch Tenant List** - GET `/v1/tenants?page=1&limit=10`
✅ **Pagination Support** - Supports page and limit query parameters
✅ **Print Formatted Output** - Beautiful table display of tenants
✅ **Cache Tenant IDs** - Store tenant IDs and names in static maps
✅ **Reuse Cached Data** - Access cached tenant IDs in other tests
✅ **Token Management** - Uses stored access token from login

---

## API Endpoint

```
GET {{BASE_URL}}/v1/tenants?page=1&limit=10
Headers: Authorization: Bearer {accessToken}
```

**Query Parameters:**
- `page` - Page number (default: 1)
- `limit` - Records per page (default: 10)

**Response Structure:**
```json
{
  "status": true,
  "statusCode": 200,
  "message": "Organizations fetched successfully",
  "data": [
    {
      "id": "bfb5df8e-2583-48c8-b3bc-48494c626c81",
      "name": "TestTanentP",
      "email": "test14@codeclouds.in",
      "phone_number": "7894561236",
      "country_code": "+1",
      "status": "PENDING",
      "created_at": "2026-02-19T10:56:49.586Z",
      "updated_at": "2026-02-19T10:56:49.586Z",
      "created_by": "af83b52b-1781-481c-a20b-50f3e0c6918d",
      "updated_by": "af83b52b-1781-481c-a20b-50f3e0c6918d",
      "deleted_at": null,
      "deleted_by": null
    }
  ],
  "meta": {
    "total": 5,
    "page": 1,
    "limit": 10,
    "total_page": 1
  },
  "error": []
}
```

---

## Files Created/Modified

### Created Files:
1. **GetTenantsListTest.java** - Test class with 3 test methods
   - `testGetTenantsList()` - Fetches and caches tenants
   - `testGetTenantsWithCustomPagination()` - Custom pagination example
   - `testAccessCachedTenantIds()` - Access cached data in dependent tests

### Modified Files:
1. **GetTenantList.java** - Complete service implementation
2. **GetTenantsResponse.java** - Enhanced response model with proper annotations
3. **Endpoints.java** - Added GET_TENANTS endpoint
4. **testng-login-runner.xml** - Added GetTenantsListTest to test suite

---

## GetTenantList Service

### Main Methods:

#### 1. getTenants(int page, int limit)
```java
// Fetch tenants with pagination
GetTenantsResponse response = tenantListService.getTenants(1, 10);
```

#### 2. getTenants()
```java
// Fetch tenants with default pagination (page=1, limit=10)
GetTenantsResponse response = tenantListService.getTenants();
```

#### 3. printTenantsList(GetTenantsResponse response)
```java
// Print formatted table of tenants
tenantListService.printTenantsList(response);
```

### Static Cache Methods:

#### 4. getTenantNameById(String tenantId)
```java
String name = GetTenantList.getTenantNameById("bfb5df8e-2583-48c8-b3bc-48494c626c81");
// Returns: "TestTanentP"
```

#### 5. getAllCachedTenantIds()
```java
List<String> ids = GetTenantList.getAllCachedTenantIds();
// Returns: [id1, id2, id3, id4, id5]
```

#### 6. getFirstTenantId()
```java
String firstId = GetTenantList.getFirstTenantId();
// Returns: "bfb5df8e-2583-48c8-b3bc-48494c626c81"
```

#### 7. getTenantIdByIndex(int index)
```java
String secondId = GetTenantList.getTenantIdByIndex(1);
// Returns: ID of second tenant
```

#### 8. getAllCachedTenants()
```java
Map<String, String> map = GetTenantList.getAllCachedTenants();
// Returns: Map<TenantId, TenantName>
```

#### 9. printCachedTenants()
```java
GetTenantList.printCachedTenants();
// Prints all cached tenant IDs and names
```

---

## Test Execution Flow

```
┌─────────────────────────────────┐
│ 1. LoginTests                   │
│    testSuccessfulLogin()        │
│    (Stores: authToken)          │
└──────────────┬──────────────────┘
               ↓
┌─────────────────────────────────┐
│ 2. LoginTests (other tests)     │
│    - testLoginWithInvalidEmail  │
│    - testLoginWithInvalidPassword
│    - testTokenStorageAfterLogin │
│    - testLoginReturnsTokens     │
└──────────────┬──────────────────┘
               ↓
┌─────────────────────────────────┐
│ 3. ProfileFetchTest             │
│    testFetchProfile()           │
│    (Stores: roleId, tenantId)   │
└──────────────┬──────────────────┘
               ↓
┌─────────────────────────────────┐
│ 4. UserCreateTest               │
│    testCreateTenantUser()       │
│    (Uses: roleId, tenantId)     │
└──────────────┬──────────────────┘
               ↓
┌──────────────────────────────────────┐
│ 5. GetTenantsListTest                │
│    testGetTenantsList()              │
│    (Fetches and caches tenant IDs)   │
│                                      │
│    testGetTenantsWithCustomPagination│
│    (Fetches with page=1, limit=5)    │
│                                      │
│    testAccessCachedTenantIds()       │
│    (Uses cached IDs for other tests) │
└──────────────────────────────────────┘
```

---

## Expected Console Output

### Test Output:
```
>>> TENANTS LIST <<<
====================================================================================================
ID                                       | Name                           | Email           | Status
====================================================================================================
bfb5df8e-2583-48c8-b3bc-48494c626c81   | TestTanentP                    | test14@codeclouds.in    | PENDING
9e7c4720-d414-4b5f-b05a-f609fff852ca   | T LLC                          | test1@codeclouds.com    | INACTIVE
c006241a-04a8-45a3-b251-28745174b153   | DEV                            | joy.chakraborty+t3@codeclouds.com | ACTIVE
448d5a38-2893-4a18-97f3-ea3bc5b68613   | QA                             | rahul.mathur+u1@codeclouds.in    | ACTIVE
e9e56e82-e149-4965-801a-00c69e12c3ab   | Surya Tenant                   | suryatenant@gmail.com   | ACTIVE
====================================================================================================

Pagination Info:
  Total Records: 5
  Current Page:  1
  Limit:         10
  Total Pages:   1

>>> TENANT IDs AND NAMES <<<
1. Tenant ID: bfb5df8e-2583-48c8-b3bc-48494c626c81 | Name: TestTanentP | Email: test14@codeclouds.in | Status: PENDING
2. Tenant ID: 9e7c4720-d414-4b5f-b05a-f609fff852ca | Name: T LLC | Email: test1@codeclouds.com | Status: INACTIVE
3. Tenant ID: c006241a-04a8-45a3-b251-28745174b153 | Name: DEV | Email: joy.chakraborty+t3@codeclouds.com | Status: ACTIVE
4. Tenant ID: 448d5a38-2893-4a18-97f3-ea3bc5b68613 | Name: QA | Email: rahul.mathur+u1@codeclouds.in | Status: ACTIVE
5. Tenant ID: e9e56e82-e149-4965-801a-00c69e12c3ab | Name: Surya Tenant | Email: suryatenant@gmail.com | Status: ACTIVE

>>> CACHED TENANT IDs FOR LATER USE <<<
============================================================
>>> CACHED TENANT IDs <<<
============================================================
1. ID: bfb5df8e-2583-48c8-b3bc-48494c626c81 | Name: TestTanentP
2. ID: 9e7c4720-d414-4b5f-b05a-f609fff852ca | Name: T LLC
3. ID: c006241a-04a8-45a3-b251-28745174b153 | Name: DEV
4. ID: 448d5a38-2893-4a18-97f3-ea3bc5b68613 | Name: QA
5. ID: e9e56e82-e149-4965-801a-00c69e12c3ab | Name: Surya Tenant
============================================================
```

---

## How to Use Cached Tenant IDs in Other Tests

### In Any Test Class:
```java
public class MyTest extends BaseTest {
    
    @Test(dependsOnMethods = "com.postbackdaddy.tests.auth.GetTenantsListTest.testGetTenantsList")
    public void myTestUsingCachedTenantId() {
        // Get first tenant ID from cache (no API call needed!)
        String tenantId = GetTenantList.getFirstTenantId();
        String tenantName = GetTenantList.getTenantNameById(tenantId);
        
        System.out.println("Using Tenant: " + tenantName + " (" + tenantId + ")");
        
        // Now use this tenant ID in your API calls
        // Example: Create user in this tenant, update tenant, etc.
    }
}
```

### Example: Create User in Cached Tenant
```java
@Test(dependsOnMethods = "com.postbackdaddy.tests.auth.GetTenantsListTest.testGetTenantsList")
public void testCreateUserInCachedTenant() {
    String tenantId = GetTenantList.getFirstTenantId();
    String accessToken = TokenManager.getAuthToken();
    
    // Now make API call using the cached tenant ID
    CreateUserRequest userRequest = new CreateUserRequest(
        "user@example.com",
        "password@123",
        "First",
        "Last",
        "roleId-value"
    );
    
    UserService userService = new UserService();
    CreateUserResponse response = userService.createUser(userRequest, accessToken);
    // The UserService will use tenantId from TokenManager.getTenantId()
    // Or you can manually set it: TokenManager.setTenantId(tenantId);
}
```

---

## Run the Tests

```bash
# Run full test suite with tenants list
mvn clean test -DsuiteXmlFile=testng-login-runner.xml

# Run only GetTenantsListTest
mvn test -Dtest=GetTenantsListTest

# Run specific test method
mvn test -Dtest=GetTenantsListTest#testGetTenantsList
```

---

## Key Benefits

✅ **No Redundant API Calls** - Fetch once, cache, reuse
✅ **Token Reuse** - Uses token from login test
✅ **Cached IDs** - Access tenant IDs in other tests
✅ **Formatted Output** - Beautiful console display
✅ **Pagination Support** - Fetch with custom page/limit
✅ **Error Handling** - Proper exception handling and logging
✅ **Static Cache** - Data persists across test methods
✅ **Easy Integration** - Simple static method calls

---

## Test Dependency Chain

```
LoginTests.testSuccessfulLogin
    ↓
    ├─ testLoginWithInvalidEmail
    ├─ testLoginWithInvalidPassword
    ├─ testTokenStorageAfterLogin
    └─ testLoginReturnsTokens
    ↓
ProfileFetchTest.testFetchProfile
    ↓
UserCreateTest.testCreateTenantUser
    ↓
GetTenantsListTest.testGetTenantsList
    ├─ testGetTenantsWithCustomPagination
    └─ testAccessCachedTenantIds
```

---

## Summary

This implementation provides:
1. ✅ Complete GetTenantList service with pagination support
2. ✅ GetTenantsListTest with 3 test methods
3. ✅ Static cache for tenant IDs (accessible across tests)
4. ✅ Formatted console output showing all tenants
5. ✅ Proper test dependencies in XML
6. ✅ Ready-to-use cached data for other tests
7. ✅ Token reuse from login test

**All tenant IDs are now cached and ready for use in other API requests!**

