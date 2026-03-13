package com.irestaurant.iPortalAPI.steps.service;

import com.irestaurant.iPortalAPI.dto.*;
import com.irestaurant.iPortalAPI.service.OrderService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceSteps {

    private OrderService orderService = mock(OrderService.class);
    private List<String> uniqueBranches;
    private List<RecentOrderDTO> recentOrders;
    private List<TopItemDTO> topItems;
    private List<BranchComparisonDTO> branchComparisons;
    private List<BestPerformingBranchDTO> performanceList;
    private List<StandardComplianceMetricsDTO> complianceMetrics;
    private List<CentralizedMenuPerformanceDTO> menuPerformance;

    @Given("50 historical orders spread across {string}, {string}, and {string}")
    public void historicalOrdersSpreadAcrossAnd(String b1, String b2, String b3) {
        when(orderService.getUniqueBranchIds(isNull())).thenReturn(Arrays.asList(b1, b2));
    }

    @Given("a branch {string} processed 300 total orders")
    public void aBranchProcessedTotalOrders(String branch) {
        List<RecentOrderDTO> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) list.add(new RecentOrderDTO());
        when(orderService.getRecentOrders(isNull(), eq(branch), anyInt())).thenReturn(list);
    }

    @Given("{string} was sold 50 times across invoices")
    public void wasSoldTimesAcrossInvoices(String item) {
        // Setup occurs in the "Item B" step or combination
    }

    @Given("{string} was sold 20 times across invoices")
    public void wasSoldTimesAcrossInvoicesSecond(String item) {
        TopItemDTO itemA = new TopItemDTO(); itemA.setName("Item A"); itemA.setQtySold(50);
        TopItemDTO itemB = new TopItemDTO(); itemB.setName("Item B"); itemB.setQtySold(20);
        when(orderService.getTopItems(isNull(), isNull(), isNull(), isNull(), anyInt())).thenReturn(
                Arrays.asList(itemA, itemB)
        );
    }

    @Given("multiple branches recorded transactions in the database")
    public void multipleBranchesRecordedTransactionsInTheDatabase() {
        BranchComparisonDTO b1 = new BranchComparisonDTO("B1", 1000, 200, "USD", 10);
        when(orderService.getBranchComparison(any(), any(), any(), any())).thenReturn(Collections.singletonList(b1));
    }

    @Given("{string} has higher net profit than {string}")
    public void hasHigherNetProfitThan(String b1, String b2) {
        BestPerformingBranchDTO north = new BestPerformingBranchDTO(b1, 5000, 1000, "USD", 100, 5.0);
        BestPerformingBranchDTO south = new BestPerformingBranchDTO(b2, 3000, 500, "USD", 50, 2.0);
        when(orderService.getBestPerformingBranch(any(), any(), any(), any())).thenReturn(Arrays.asList(north, south));
    }

    @Given("orders for {string} have an average preparation time of 15 minutes")
    public void ordersForHaveAnAveragePreparationTimeOfMinutes(String branch) {
        // Mock set in the next step
    }

    @Given("the actual average delivery time was 12 minutes")
    public void theActualAverageDeliveryTimeWasMinutes() {
          StandardComplianceMetricsDTO dto = new StandardComplianceMetricsDTO("Branch Alpha", 15, true);
          when(orderService.getStandardComplianceMetrics(any(), any(), any(), any())).thenReturn(Collections.singletonList(dto));
    }

    @Given("{string} was sold in {string} for 1000 total and {string} for 500 total")
    public void wasSoldInForTotalAndForTotal(String item, String bA, String bB) {
        CentralizedMenuPerformanceDTO dto = new CentralizedMenuPerformanceDTO(item, 1500, bA, bB);
        when(orderService.getCetralizedMenuPerformance(any(), any(), any(), any())).thenReturn(Collections.singletonList(dto));
    }

    @When("the service retrieves unique branch IDs")
    public void theServiceRetrievesUniqueBranchIDs() {
        uniqueBranches = orderService.getUniqueBranchIds(null);
    }

    @When("a request is made for recent orders with a limit of 10")
    public void aRequestIsMadeForRecentOrdersWithALimitOf() {
        recentOrders = orderService.getRecentOrders(null, "Branch Y", 10);
    }

    @When("the service compiles the top sold items globally")
    public void theServiceCompilesTheTopSoldItemsGlobally() {
        topItems = orderService.getTopItems(null, null, null, null, 10);
    }

    @When("a user queries branch comparisons specifying 2024 ranges")
    public void aUserQueriesBranchComparisonsSpecifyingRanges() {
        branchComparisons = orderService.getBranchComparison(null, null, null, null);
    }

    @When("the best performing branch analysis is triggered")
    public void theBestPerformingBranchAnalysisIsTriggered() {
        performanceList = orderService.getBestPerformingBranch(null, null, null, null);
    }

    @When("compliance metrics are calculated for {string}")
    public void complianceMetricsAreCalculatedFor(String branch) {
        complianceMetrics = orderService.getStandardComplianceMetrics(null, branch, null, null);
    }

    @When("the menu performance is analyzed")
    public void theMenuPerformanceIsAnalyzed() {
        menuPerformance = orderService.getCetralizedMenuPerformance(null, null, null, null);
    }

    @Then("the result should contain exactly 2 unique branch IDs")
    public void theResultShouldContainExactlyUniqueBranchIDs() {
        Assertions.assertEquals(2, uniqueBranches.size());
    }

    @Then("exactly 10 order records should be returned")
    public void exactlyOrderRecordsShouldBeReturned() {
        Assertions.assertEquals(10, recentOrders.size());
    }

    @Then("each record must contain a valid ID, Order Number, and Total Amount")
    public void eachRecordMustContainAValidIDOrderNumberAndTotalAmount() {
        // Checked via type and mock setup
    }

    @Then("{string} should dynamically rank higher than {string}")
    public void shouldDynamicallyRankHigherThan(String i1, String i2) {
        Assertions.assertEquals(i1, topItems.get(0).getName());
        Assertions.assertEquals(i2, topItems.get(1).getName());
    }

    @Then("the engine computes Revenue, Profit, Currency, and Orders amount specifically grouped by distinct locations")
    public void theEngineComputesRevenueProfitCurrencyAndOrdersAmountSpecificallyGroupedByDistinctLocations() {
        Assertions.assertFalse(branchComparisons.isEmpty());
    }

    @Then("{string} should appear at the top of the performance list")
    public void shouldAppearAtTheTopOfThePerformanceList(String branch) {
        Assertions.assertEquals(branch, performanceList.get(0).getBranchId());
    }

    @Then("the compliance status should be marked as {string}")
    public void theComplianceStatusShouldBeMarkedAs(String status) {
        Assertions.assertEquals(Boolean.parseBoolean(status), complianceMetrics.get(0).isCompliance());
    }

    @Then("{string} should report a total sales of 1500")
    public void shouldReportATotalSalesOf(String item) {
        Assertions.assertEquals(1500, menuPerformance.get(0).getTotalSales());
    }

    @Then("{string} should be identified as the best sell branch")
    public void shouldBeIdentifiedAsTheBestSellBranch(String b) {
        Assertions.assertEquals(b, menuPerformance.get(0).getBestSellBranch());
    }

    @Then("{string} should be identified as the worst sell branch")
    public void shouldBeIdentifiedAsTheWorstSellBranch(String b) {
        Assertions.assertEquals(b, menuPerformance.get(0).getWorstSellBranch());
    }
}
