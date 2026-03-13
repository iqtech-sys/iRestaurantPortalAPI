package com.irestaurant.iPortalAPI.dto;

public class StandardComplianceMetricsDTO {

    private String branchId;
    private long averageOrdersTime;
    private boolean compliance;

    public StandardComplianceMetricsDTO() {
    }

    public StandardComplianceMetricsDTO(String branchId, long averageOrdersTime, boolean compliance) {
        this.branchId = branchId;
        this.averageOrdersTime = averageOrdersTime;
        this.compliance = compliance;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public long getAverageOrdersTime() {
        return averageOrdersTime;
    }

    public void setAverageOrdersTime(long averageOrdersTime) {
        this.averageOrdersTime = averageOrdersTime;
    }

    public boolean isCompliance() {
        return compliance;
    }

    public void setCompliance(boolean compliance) {
        this.compliance = compliance;
    }
}
