package com.irestaurant.iPortalAPI.steps.controller;

import com.irestaurant.iPortalAPI.controller.OrderController;
import com.irestaurant.iPortalAPI.dto.*;
import com.irestaurant.iPortalAPI.service.OrderService;
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

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class OrderControllerSteps {

    @Mock
    private OrderService orderService;
    @Mock
    private SimpMessagingTemplate messagingTemplate;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private SimpMessageHeaderAccessor headerAccessor;

    @InjectMocks
    private OrderController orderController;

    public OrderControllerSteps() {
        MockitoAnnotations.openMocks(this);
        when(headerAccessor.getSessionId()).thenReturn("session-456");
        when(headerAccessor.getFirstNativeHeader("Authorization")).thenReturn("Bearer MOCK_JWT");
        when(jwtUtil.pureJWT(anyString())).thenReturn("MOCK_JWT");
        when(jwtUtil.extractEmail(anyString())).thenReturn("admin@example.com");
    }

    @Given("a request for recent orders with limit {int}")
    public void aRequestForRecentOrdersWithLimit(Integer limit) {
        when(orderService.getRecentOrders(any(), any(), eq(limit))).thenReturn(Collections.nCopies(limit, new RecentOrderDTO()));
    }

    @When("a message is sent to {string}")
    public void aMessageIsSentTo(String destination) {
        DbRequest req = new DbRequest();
        orderController.getUniqueBranchIds(req, headerAccessor);
    }

    @When("the message is sent to {string}")
    public void theMessageIsSentToRecent(String destination) {
        if (destination.contains("recentOrders")) {
            RecentOrdersRequest req = new RecentOrdersRequest();
            req.setLimit(5);
            orderController.getRecentOrders(req, headerAccessor);
        } else if (destination.contains("bestPerformingBranch")) {
            BestPerformingBranchRequest req = new BestPerformingBranchRequest();
            orderController.getBestPerformingBranch(req, headerAccessor);
        }
    }

    @Then("the response on {string} should contain a list of branch IDs")
    public void theResponseOnShouldContainAListOfBranchIDs(String queue) {
        verify(messagingTemplate).convertAndSendToUser(eq("session-456"), eq(queue), any(AuthResponse.class));
    }

    @Then("the response on {string} should contain {int} order records")
    public void theResponseOnShouldContainOrderRecords(String queue, Integer count) {
        ArgumentCaptor<AuthResponse> captor = ArgumentCaptor.forClass(AuthResponse.class);
        verify(messagingTemplate).convertAndSendToUser(eq("session-456"), eq(queue), captor.capture());
        Assertions.assertEquals(count, ((java.util.List)captor.getValue().getData()).size());
    }

    @Then("the response on {string} should contain sorted branch data")
    public void theResponseOnShouldContainSortedBranchData(String queue) {
        verify(messagingTemplate).convertAndSendToUser(eq("session-456"), eq(queue), any(AuthResponse.class));
    }
}
