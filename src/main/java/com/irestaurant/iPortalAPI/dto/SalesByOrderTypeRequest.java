package com.irestaurant.iPortalAPI.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.Date;

public class SalesByOrderTypeRequest {

    @JsonProperty("branchName")
    private String branchName; // optional — null/empty means all branches

    @JsonProperty("startDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonProperty("endDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;

    @JsonProperty("topX")
    @Min(1)
    @Max(1000)
    private int topX = 10; // sensible default

    public SalesByOrderTypeRequest() {
    }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public int getTopX() { return topX; }
    public void setTopX(int topX) { this.topX = topX; }
}
