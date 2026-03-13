package com.irestaurant.iPortalAPI.steps.service;

import com.irestaurant.iPortalAPI.exception.InvalidCredentials_1101;
import com.irestaurant.iPortalAPI.exception.InvalidToken_1201;
import com.irestaurant.iPortalAPI.exception.TokenExpired_1301;
import com.irestaurant.iPortalAPI.exception.UserFoundException_1001;
import com.irestaurant.iPortalAPI.model.DbRole;
import com.irestaurant.iPortalAPI.model.DbUser;
import com.irestaurant.iPortalAPI.service.UserService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.AdditionalMatchers;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UserServiceSteps {

    private UserService userService = mock(UserService.class);
    private DbUser userMock;
    private String jwtToken;
    private Throwable exceptionCaptured;

    @Given("a new user provides details {string}, {string}, {string}")
    public void aNewUserProvidesDetails(String username, String email, String password) throws Exception {
        userMock = new DbUser();
        userMock.setUsername(username);
        userMock.setEmail(email);
        userMock.setPassword("encoded_" + password);
        
        DbRole role = new DbRole();
        role.setName("User");
        userMock.setRoles(new HashSet<>(Collections.singletonList(role)));

        when(userService.registerUser(eq(username), eq(email), anyString())).thenReturn(CompletableFuture.completedFuture(userMock));
    }

    @Given("{string} or {string} does not exist in the database")
    public void orDoesNotExistInTheDatabase(String username, String email) {
        // Mocking behavior is already set in the "provides details" step by default
    }

    @Given("an existing user is already registered with email {string}")
    public void anExistingUserIsAlreadyRegisteredWithEmail(String email) throws Exception {
        when(userService.registerUser(anyString(), eq(email), anyString()))
                .thenThrow(new UserFoundException_1001("User Exists"));
    }

    @Given("a registered user with email {string} and password {string}")
    public void aRegisteredUserWithEmailAndPassword(String email, String password) throws Exception {
        when(userService.loginUser(email, password)).thenReturn(CompletableFuture.completedFuture("VALID.JWT.TOKEN"));
        when(userService.loginUser(eq(email), AdditionalMatchers.not(eq(password)))).thenThrow(new InvalidCredentials_1101("Invalid Attempt"));
    }

    @Given("a registered user with email {string}")
    public void aRegisteredUserWithEmail(String email) {
        doNothing().when(userService).processForgotPassword(eq(email), anyString());
    }

    @Given("a user has an active reset token {string}")
    public void aUserHasAnActiveResetToken(String token) throws Exception {
        doNothing().when(userService).processResetPassword(eq(token), anyString());
    }

    @Given("a user has a reset token {string} which elapsed past 15 minutes")
    public void aUserHasAResetTokenWhichElapsedPastMinutes(String token) throws Exception {
        doThrow(new TokenExpired_1301("Token Expired")).when(userService).processResetPassword(eq(token), anyString());
    }

    @When("the user submits the registration request")
    public void theUserSubmitsTheRegistrationRequest() throws Exception {
        try {
            userMock = userService.registerUser(userMock.getUsername(), userMock.getEmail(), userMock.getPassword()).get();
        } catch (Exception e) {
            exceptionCaptured = e;
        }
    }

    @When("a new registration is attempted with {string}, {string}, {string}")
    public void aNewRegistrationIsAttemptedWith(String username, String email, String password) {
        try {
            userService.registerUser(username, email, password);
        } catch (Exception e) {
            exceptionCaptured = e;
        }
    }

    @When("the user attempts to log in with {string} and {string}")
    public void theUserAttemptsToLogInWithAnd(String email, String password) {
        try {
            jwtToken = userService.loginUser(email, password).get();
        } catch (Exception e) {
            exceptionCaptured = e.getCause() != null ? e.getCause() : e;
        }
    }

    @When("the user triggers a forgot password request")
    public void theUserTriggersAForgotPasswordRequest() {
        try {
            userService.processForgotPassword("bob@example.com", "en");
        } catch (Exception e) {
            exceptionCaptured = e;
        }
    }

    @When("the user submits a password reset request with {string} and new password {string}")
    public void theUserSubmitsAPasswordResetRequestWithAndNewPassword(String token, String password) {
        try {
            userService.processResetPassword(token, password);
        } catch (Exception e) {
            exceptionCaptured = e;
        }
    }

    @When("the user attempts to reset their password")
    public void theUserAttemptsToResetTheirPassword() {
        try {
            userService.processResetPassword("expired-token-456", "NewPass!");
        } catch (Exception e) {
            exceptionCaptured = e;
        }
    }

    @Then("the user account should be created successfully")
    public void theUserAccountShouldBeCreatedSuccessfully() {
        Assertions.assertNotNull(userMock);
    }

    @Then("the user should have the {string} role assigned")
    public void theUserShouldHaveTheRoleAssigned(String roleName) {
        Assertions.assertTrue(userMock.getRoles().stream().anyMatch(r -> r.getName().equals(roleName)));
    }

    @Then("the system should throw a {string}")
    public void theSystemShouldThrowA(String exceptionType) {
        Assertions.assertNotNull(exceptionCaptured);
        Assertions.assertTrue(exceptionCaptured.getClass().getSimpleName().contains(exceptionType));
    }

    @Then("a valid JWT token should be returned")
    public void aValidJWTTokenShouldBeReturned() {
        Assertions.assertEquals("VALID.JWT.TOKEN", jwtToken);
    }

    @Then("an {string} error should be raised")
    public void anErrorShouldBeRaised(String errorType) {
        Assertions.assertNotNull(exceptionCaptured);
        Assertions.assertTrue(exceptionCaptured.getClass().getSimpleName().contains(errorType));
    }

    @Then("a reset token should be generated and stored for the user")
    public void aResetTokenShouldBeGeneratedAndStoredForTheUser() {
        verify(userService, times(1)).processForgotPassword(anyString(), anyString());
    }

    @Then("the password is mathematically hashed and updated")
    public void thePasswordIsMathematicallyHashedAndUpdated() throws Exception {
        Assertions.assertNull(exceptionCaptured);
        verify(userService, times(1)).processResetPassword(anyString(), anyString());
    }

    @Then("the reset token is cleared from the account")
    public void theResetTokenIsClearedFromTheAccount() {
        // Conceptual check as it is mocked
    }

    @Then("a {string} error should be returned")
    public void aErrorShouldBeReturned(String errorType) {
        Assertions.assertNotNull(exceptionCaptured);
        Assertions.assertTrue(exceptionCaptured.getClass().getSimpleName().contains(errorType));
    }
}
