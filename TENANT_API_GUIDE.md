# Tenant Creation API - Complete Implementation Guide

## Overview
This document provides a complete guide to the Tenant Creation API implementation in the PostBackDaddy API automation framework.

## Architecture

### Files Created/Modified

1. **TenantService.java** (Interface)
   - Location: `src/main/java/com/postbackdaddy/api/services/TenantService.java`
   - Purpose: Defines the contract for tenant operations
   - Method: `createTenant(CreateTenantRequest, String accessToken)`

2. **TenantServiceImpl.java** (Implementation)
   - Location: `src/main/java/com/postbackdaddy/api/services/TenantServiceImpl.java`
   - Purpose: Implements tenant creation using RestAssured
   - Uses: BaseSpecBuilder, ConfigReader, RestAssured

3. **CreateTenantRequest.java** (Request Model)
   - Location: `src/main/java/com/postbackdaddy/api/models/request/CreateTenantRequest.java`
   - Fields:
     - `name` (String): Tenant name
     - `email` (String): Tenant email
     - `country_code` (String): Country code (e.g., "+1")
     - `phone_number` (String): Phone number
     - `status` (String): Tenant status (e.g., "ACTIVE")

4. **CreateTenantResponse.java** (Response Model)
   - Location: `src/main/java/com/postbackdaddy/api/models/response/CreateTenantResponse.java`
   - Main Fields:
     - `status` (boolean): Success status
     - `statusCode` (int): HTTP status code (201 for success)
     - `message` (String): Response message
     - `data` (TenantData): Tenant details
     - `error` (List): Error list
   - TenantData Fields:
     - `id`: Unique tenant identifier (UUID)
     - `name`: Tenant name
     - `email`: Tenant email
     - `phone_number`: Tenant phone
     - `country_code`: Country code
     - `status`: Tenant status
     - `created_at`: Creation timestamp
     - `updated_at`: Last update timestamp
     - `created_by`: Creator user ID
     - `updated_by`: Last updater user ID

5. **TenantCreateTests.java** (Test Class)
   - Location: `src/test/java/com/postbackdaddy/tests/auth/TenantCreateTests.java`
   - Test Methods:
     - `testSuccessfulTenantCreation()`: Tests successful tenant creation
     - `testTenantCreationWithMissingEmail()`: Tests validation
     - `testTenantCreationWithInvalidToken()`: Tests authentication
     - `testTenantCreationWithDuplicateEmail()`: Tests duplicate email handling

## API Endpoint

**Endpoint:** `https://stagingapi.postbackdaddy.com/v1/tenants`
**Method:** POST
**Authentication:** Bearer Token (Access Token)

## Request Example

```json
{
  "name": "TenantQQQ",
  "email": "test12+cccc@codeclouds.in",
  "country_code": "+1",
  "phone_number": "1234232323",
  "status": "ACTIVE"
}
```

## Response Example (Success - 201)

```json
{
  "status": true,
  "statusCode": 201,
  "message": "Tenant created successfully",
  "data": {
    "id": "59f594ab-629b-4956-90d8-8812cf05d440",
    "created_at": "2026-02-18T11:24:27.812Z",
    "updated_at": "2026-02-18T11:24:27.812Z",
    "deleted_at": null,
    "created_by": "af83b52b-1781-481c-a20b-50f3e0c6918d",
    "updated_by": "af83b52b-1781-481c-a20b-50f3e0c6918d",
    "deleted_by": null,
    "email": "test12+cccc@codeclouds.in",
    "name": "TenantQQQ",
    "phone_number": "1234232323",
    "country_code": "+1",
    "status": "ACTIVE"
  },
  "meta": null,
  "error": []
}
```

## Usage Example

### In Test Class
```java
public class MyTest extends BaseTest {
    
    private TenantService tenantService;
    
    @BeforeMethod
    public void setUp() {
        tenantService = new TenantServiceImpl();
    }
    
    @Test
    public void testTenantCreation() {
        // Create request
        CreateTenantRequest request = new CreateTenantRequest();
        request.setName("MyTenant");
        request.setEmail("tenant@example.com");
        request.setCountry_code("+1");
        request.setPhone_number("1234567890");
        request.setStatus("ACTIVE");
        
        // Get access token (from login)
        String accessToken = TokenManager.getAuthToken();
        
        // Call service
        CreateTenantResponse response = tenantService.createTenant(request, accessToken);
        
        // Verify response
        Assert.assertTrue(response.isStatus());
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertNotNull(response.getData().getId());
    }
}
```

### Key Dependencies
- **RestAssured**: For API testing and HTTP requests
- **Lombok**: For automatic getters/setters (@Data annotation)
- **Jackson**: For JSON serialization/deserialization
- **TestNG**: For test execution
- **ExtentReports**: For test reporting

## Configuration

The base URI is read from `config.properties`:
```properties
base.uri=https://stagingapi.postbackdaddy.com
api.version=v1
```

## Error Handling

The implementation handles:
1. **Missing Authentication**: Returns 401 Unauthorized
2. **Invalid Token**: Returns 403 Forbidden
3. **Validation Errors**: Returns 400 Bad Request with error details
4. **Duplicate Email**: Returns appropriate error response
5. **Server Errors**: Returns 500 Internal Server Error

## Headers Required

```
Content-Type: application/json
Authorization: Bearer {accessToken}
```

## Flow Diagram

```
TenantCreateTests
    ↓
TenantService (Interface)
    ↓
TenantServiceImpl (Implementation)
    ↓
BaseSpecBuilder.getRequestSpecWithAuth()
    ↓
RestAssured.post(/v1/tenants)
    ↓
CreateTenantResponse
```

## Testing

Run the tenant creation tests:
```bash
mvn test -Dgroups=tenant
```

Or specific test:
```bash
mvn test -Dtest=TenantCreateTests#testSuccessfulTenantCreation
```

## Notes

- The access token should be obtained from a successful login via `AuthService`
- All timestamps are in ISO 8601 format with UTC timezone
- The created_by and updated_by fields contain the user ID who performed the action
- The tenant status must be "ACTIVE" at creation time
- Email validation is enforced by the API
- Phone number validation follows international format standards

## Dependencies Tree

```
TenantCreateTests
├── TenantServiceImpl
│   ├── BaseSpecBuilder
│   ├── ConfigReader
│   ├── RestAssured
│   └── CreateTenantRequest/CreateTenantResponse
├── TokenManager
├── BaseTest (ExtentReports)
└── TestNG
```

