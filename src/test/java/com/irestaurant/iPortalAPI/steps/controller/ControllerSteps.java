package com.irestaurant.iPortalAPI.steps.controller;

import com.irestaurant.iPortalAPI.controller.AccountController;
import com.irestaurant.iPortalAPI.controller.AuthController;
import com.irestaurant.iPortalAPI.dto.*;
import com.irestaurant.iPortalAPI.model.DbUser;
import com.irestaurant.iPortalAPI.service.AccountService;
import com.irestaurant.iPortalAPI.service.UserService;
import com.irestaurant.iPortalAPI.util.JwtUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ControllerSteps {

    @Mock
    private UserService userService;
    @Mock
    private AccountService accountService;
    @Mock
    private SimpMessagingTemplate messagingTemplate;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private SimpMessageHeaderAccessor headerAccessor;
    @Mock
    private Principal principal;

    @InjectMocks
    private AuthController authController;
    @InjectMocks
    private AccountController accountController;

    private AuthResponse lastResponse;
    private LoginRequest loginReq;
    private RegisterRequest registerReq;

    public ControllerSteps() {
        MockitoAnnotations.openMocks(this);
        when(headerAccessor.getSessionId()).thenReturn("session-123");
    }

    @Given("a valid login payload for {string}")
    public void aValidLoginPayloadFor(String email) throws Exception {
        loginReq = new LoginRequest();
        loginReq.setEmail(email);
        loginReq.setPassword("password");
        when(userService.loginUser(anyString(), anyString())).thenReturn(CompletableFuture.completedFuture("MOCK_JWT"));
        when(userService.loadUserByEmail(anyString())).thenReturn(mock(org.springframework.security.core.userdetails.User.class));
    }

    @Given("a valid registration payload for {string}")
    public void aValidRegistrationPayloadFor(String username) throws Exception {
        registerReq = new RegisterRequest();
        registerReq.setUsername(username);
        registerReq.setEmail(username + "@example.com");
        registerReq.setPassword("pass");
        DbUser mockUser = new DbUser();
        mockUser.setUsername(username);
        mockUser.setRoles(Collections.emptySet());
        when(userService.registerUser(anyString(), anyString(), anyString())).thenReturn(CompletableFuture.completedFuture(mockUser));
    }

    @Given("a valid JWT token in the session headers")
    public void aValidJWTTokenInTheSessionHeaders() {
        when(headerAccessor.getFirstNativeHeader("Authorization")).thenReturn("Bearer MOCK_JWT");
        when(jwtUtil.pureJWT(anyString())).thenReturn("MOCK_JWT");
        when(jwtUtil.extractEmail(anyString())).thenReturn("user@example.com");
    }

    @Given("a request for sales gadgets for branch {string}")
    public void aRequestForSalesGadgetsForBranch(String branch) {
        when(accountService.getExpenses(any(), eq(branch), any(), any())).thenReturn(100.0);
        when(accountService.getProfit(any(), eq(branch), any(), any())).thenReturn(200.0);
        when(accountService.getRevenue(any(), eq(branch), any(), any())).thenReturn(300.0);
    }

    @Given("a request for performance heatmap for branch {string}")
    public void aRequestForPerformanceHeatmapForBranch(String branch) {
        when(accountService.getPerformanceHeatmap(any(), eq(branch), any(), any())).thenReturn(Collections.emptyList());
    }

    @When("the login message is sent to {string}")
    public void theLoginMessageIsSentTo(String destination) {
        authController.login(loginReq, headerAccessor);
    }

    @When("the registration message is sent to {string}")
    public void theRegistrationMessageIsSentTo(String destination) {
        authController.register(registerReq, headerAccessor, principal);
    }

    @When("the request is sent to {string}")
    public void theRequestIsSentTo(String destination) {
        if (destination.contains("salesGadgets")) {
            SalesGadgetsRequest req = new SalesGadgetsRequest();
            req.setBranchName("MainBranch");
            accountController.getSalesGadgets(req, headerAccessor);
        } else if (destination.contains("performanceHeatmap")) {
            PerformanceHeatmapRequest req = new PerformanceHeatmapRequest();
            req.setBranchName("MainBranch");
            accountController.getPerformanceHeatmap(req, headerAccessor);
        }
    }

    @Then("the user should receive a successful AuthResponse on {string} containing a JWT")
    public void theUserShouldReceiveASuccessfulAuthResponseOnContainingAJWT(String queue) {
        ArgumentCaptor<AuthResponse> captor = ArgumentCaptor.forClass(AuthResponse.class);
        verify(messagingTemplate).convertAndSendToUser(eq("session-123"), eq(queue), captor.capture());
        Assertions.assertEquals("1", captor.getValue().getCode());
        Assertions.assertEquals("MOCK_JWT", captor.getValue().getToken());
    }

    @Then("the user should receive a successful AuthResponse on {string}")
    public void theUserShouldReceiveASuccessfulAuthResponseOn(String queue) {
        verify(messagingTemplate).convertAndSend(eq(queue), any(AuthResponse.class));
    }

    @Then("the response should contain valid expense, profit, and revenue data on {string}")
    public void theResponseShouldContainValidExpenseProfitAndRevenueDataOn(String queue) {
        ArgumentCaptor<AuthResponse> captor = ArgumentCaptor.forClass(AuthResponse.class);
        verify(messagingTemplate).convertAndSendToUser(eq("session-123"), eq(queue), captor.capture());
        SalesGadgetsDTO data = (SalesGadgetsDTO) captor.getValue().getData();
        Assertions.assertEquals(100.0, data.getExpenses());
    }

    @Then("the response should contain a list of heatmap data on {string}")
    public void theResponseShouldContainAListOfHeatmapDataOn(String queue) {
        verify(messagingTemplate).convertAndSendToUser(eq("session-123"), eq(queue), any(AuthResponse.class));
    }
}
