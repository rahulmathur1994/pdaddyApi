package com.postbackdaddy.api.services;

import io.restassured.response.Response;
import com.postbackdaddy.api.base.BaseSpecBuilder;
import com.postbackdaddy.api.constants.Endpoints;
import com.postbackdaddy.api.models.request.ForgetPasswordRequest;
import com.postbackdaddy.api.models.request.ResetPasswordRequest;
import com.postbackdaddy.api.models.response.ForgetPasswordResponse;
import com.postbackdaddy.api.models.response.ResetPasswordResponse;
import com.postbackdaddy.api.utils.ConfigReader;
import com.postbackdaddy.api.utils.TokenManager;

import static io.restassured.RestAssured.given;

/**
 * Password Service - Page Object Model for Password Management APIs
 * Handles Forgot Password and Reset Password operations
 */
public class PasswordService {

    private String baseURI;

    public PasswordService() {
        this.baseURI = ConfigReader.getBaseURI();
    }

    public PasswordService(String baseURI) {
        this.baseURI = baseURI;
    }

    /**
     * Request to reset password (Forgot Password)
     * @param forgetPasswordRequest Email address
     * @return Response object containing reset token
     */
    public Response forgetPassword(ForgetPasswordRequest forgetPasswordRequest) {
        Response response = given()
                .spec(BaseSpecBuilder.getRequestSpec(baseURI))
                .body(forgetPasswordRequest)
                .when()
                .post(Endpoints.FORGOT_PASSWORD)
                .then()
                .extract()
                .response();

        return response;
    }

    /**
     * Request to reset password (Forgot Password) with email
     * @param email User email address
     * @return Response object
     */
    public Response forgetPassword(String email) {
        ForgetPasswordRequest request = new ForgetPasswordRequest(email);
        return forgetPassword(request);
    }

    /**
     * Reset password with token and new password
     * @param resetPasswordRequest Reset password details
     * @return Response object
     */
    public Response resetPassword(ResetPasswordRequest resetPasswordRequest) {
        Response response = given()
                .spec(BaseSpecBuilder.getRequestSpec(baseURI))
                .body(resetPasswordRequest)
                .when()
                .post(Endpoints.RESET_PASSWORD)
                .then()
                .extract()
                .response();

        return response;
    }

    /**
     * Reset password with token and new password
     * @param token Reset token from forgot password email
     * @param newPassword New password
     * @return Response object
     */
    public Response resetPassword(String token, String newPassword) {
        ResetPasswordRequest request = new ResetPasswordRequest(token, newPassword, newPassword);
        return resetPassword(request);
    }

    /**
     * Reset password with token, new password, and confirm password
     * @param token Reset token
     * @param newPassword New password
     * @param confirmPassword Confirm password
     * @return Response object
     */
    public Response resetPassword(String token, String newPassword, String confirmPassword) {
        ResetPasswordRequest request = new ResetPasswordRequest(token, newPassword, confirmPassword);
        return resetPassword(request);
    }
}

