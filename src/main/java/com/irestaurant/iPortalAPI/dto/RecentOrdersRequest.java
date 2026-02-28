package com.irestaurant.iPortalAPI.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * Payload DTO for the /orders.getRecent STOMP endpoint.
 * The client sends this to request a paginated, branch-filtered list of recent
 * orders.
 */
public class RecentOrdersRequest {

    @JsonProperty("branchName")
    private String branchName; // optional — null/empty means all branches

    @JsonProperty("limit")
    @Min(1)
    @Max(1000)
    private int limit = 20; // sensible default

    public RecentOrdersRequest() {
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
