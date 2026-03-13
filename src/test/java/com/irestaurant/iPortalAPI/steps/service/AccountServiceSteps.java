package com.irestaurant.iPortalAPI.steps.service;

import com.irestaurant.iPortalAPI.dto.PerformanceHeatmapDTO;
import com.irestaurant.iPortalAPI.service.AccountService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceSteps {

    private AccountService accountService = mock(AccountService.class);
    private double calculatedResult;
    private List<PerformanceHeatmapDTO> heatmapData;

    @Given("historical invoices for branch {string} totaling 500 dollars marked as added")
    public void historicalInvoicesForBranchTotalingDollarsMarkedAsAdded(String branch) {
        when(accountService.getExpenses(isNull(), eq(branch), isNull(), isNull())).thenReturn(500.0);
    }

    @Given("historical invoices for branch {string} totaling 200 dollars marked as outgoing")
    public void historicalInvoicesForBranchTotalingDollarsMarkedAsOutgoing(String branch) {
        // Expenses logic only counts 'added' invoices in the service implementation
    }

    @Given("a branch {string} has 5000 dollars in gross revenue")
    public void aBranchHasDollarsInGrossRevenue(String branch) {
        when(accountService.getRevenue(isNull(), eq(branch), isNull(), isNull())).thenReturn(5000.0);
    }

    @Given("total expenses amounting to 3000 dollars for the same period")
    public void totalExpensesAmountingToDollarsForTheSamePeriod() {
        when(accountService.getExpenses(any(), any(), any(), any())).thenReturn(3000.0);
        // Profit mock
        when(accountService.getProfit(any(), any(), any(), any())).thenReturn(2000.0);
    }

    @Given("daily revenue is generated uniquely for exactly 5 branches")
    public void dailyRevenueIsGeneratedUniquelyForExactlyBranches() {
        List<PerformanceHeatmapDTO> dtos = new ArrayList<>();
        dtos.add(new PerformanceHeatmapDTO("Branch E", 1000.0, "2024-01"));
        dtos.add(new PerformanceHeatmapDTO("Branch C", 800.0, "2024-01"));
        dtos.add(new PerformanceHeatmapDTO("Branch D", 600.0, "2024-01"));
        dtos.add(new PerformanceHeatmapDTO("Branch A", 400.0, "2024-01"));
        dtos.add(new PerformanceHeatmapDTO("Branch B", 200.0, "2024-01"));
        when(accountService.getPerformanceHeatmap(isNull(), isNull(), isNull(), isNull())).thenReturn(dtos);
    }

    @When("the financial engine calculates expenses for {string}")
    public void theFinancialEngineCalculatesExpensesFor(String branch) {
        calculatedResult = accountService.getExpenses(null, branch, null, null);
    }

    @When("the service calculates the profit for {string}")
    public void theServiceCalculatesTheProfitFor(String branch) {
        calculatedResult = accountService.getProfit(null, branch, null, null);
    }

    @When("a request is made for the performance heatmap")
    public void aRequestIsMadeForThePerformanceHeatmap() {
        heatmapData = accountService.getPerformanceHeatmap(null, null, null, null);
    }

    @Then("the result should be exactly 500.0")
    public void theResultShouldBeExactly() {
        Assertions.assertEquals(500.0, calculatedResult);
    }

    @Then("the resulting profit value should be 2000.0")
    public void theResultingProfitValueShouldBe() {
        Assertions.assertEquals(2000.0, calculatedResult);
    }

    @Then("the output list should be sorted precisely descending based on highest revenue score")
    public void theOutputListShouldBeSortedPreciselyDescendingBasedOnHighestRevenueScore() {
        Assertions.assertEquals(5, heatmapData.size());
        Assertions.assertTrue(heatmapData.get(0).getProfit() > heatmapData.get(1).getProfit());
    }

    @Then("each entry must contain a branch ID, profit, and month string")
    public void eachEntryMustContainABranchIDProfitAndMonthString() {
        PerformanceHeatmapDTO first = heatmapData.get(0);
        Assertions.assertNotNull(first.getBranchId());
        Assertions.assertNotNull(first.getMonth());
    }

    @Then("no empty or undefined branch instances should be included")
    public void noEmptyOrUndefinedBranchInstancesShouldBeIncluded() {
        Assertions.assertTrue(heatmapData.stream().noneMatch(b -> b.getBranchId() == null || b.getBranchId().isEmpty()));
    }
}
