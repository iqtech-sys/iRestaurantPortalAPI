package com.irestaurant.iPortalAPI.service;

import com.irestaurant.iPortalAPI.dto.PerformanceHeatmapDTO;
import com.irestaurant.iPortalAPI.objectbox.model.Invoice;
import com.irestaurant.iPortalAPI.objectbox.model.Invoice_;
import com.irestaurant.iPortalAPI.util.AccountUtil;
import com.irestaurant.iPortalAPI.util.SyncManager;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.QueryBuilder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    public double getExpenses(String email, String branch, Date startDate, Date endDate) {
        BoxStore store = SyncManager.init(email);
        Box<Invoice> invoiceBox = store.boxFor(Invoice.class);
        QueryBuilder<Invoice> invoiceQb = invoiceBox.query();
        if (startDate != null && endDate != null) {
            invoiceQb = invoiceQb.between(Invoice_.createdDate, endDate, endDate);
        }
        if (branch != null && !branch.isBlank()) {
            invoiceQb = invoiceQb.equal(Invoice_.branchId, branch, QueryBuilder.StringOrder.CASE_INSENSITIVE);
        }
        invoiceQb = invoiceQb.filter(invoice -> AccountUtil.isAddedInvoice(invoice.getInvNum()));
        return invoiceQb.build().property(Invoice_.amountTo).sumDouble();
    }

    public double getProfit(String email, String branch, Date startDate, Date endDate) {
        //
        BoxStore store = SyncManager.init(email);
        Box<Invoice> invoiceBox = store.boxFor(Invoice.class);
        QueryBuilder<Invoice> invoiceQb = invoiceBox.query();
        if (startDate != null && endDate != null) {
            invoiceQb.between(Invoice_.createdDate, endDate, endDate);
        }
        if (branch != null && !branch.isBlank()) {
            invoiceQb.equal(Invoice_.branchId, branch, io.objectbox.query.QueryBuilder.StringOrder.CASE_INSENSITIVE);
        }
        double revenue = getRevenue(email, branch, startDate, endDate);
        double expenses = getExpenses(email, branch, startDate, endDate);
        double profie = revenue - expenses;
        return profie;
    }

    public double getRevenue(String email, String branch, Date startDate, Date endDate) {
        BoxStore store = SyncManager.init(email);
        Box<Invoice> invoiceBox = store.boxFor(Invoice.class);
        QueryBuilder<Invoice> invoiceQb = invoiceBox.query();
        if (startDate != null && endDate != null) {
            invoiceQb.between(Invoice_.createdDate, endDate, endDate);
        }
        if (branch != null && !branch.isBlank()) {
            invoiceQb.equal(Invoice_.branchId, branch, io.objectbox.query.QueryBuilder.StringOrder.CASE_INSENSITIVE);
        }
        return invoiceQb.build().property(Invoice_.amountTo).sumDouble();
        // double revenue = 0.0;
        // var allInvoices = invoiceQb.build().find();
        // for (Invoice invoice : allInvoices) {
        // double taxRate = invoice.getTaxRate();
        // double subTotal = invoice.getAmountTo();
        // double tax = AccountUtil.calculateTax(subTotal, taxRate);
        // double totalAmount = AccountUtil.calculateTotal(subTotal, tax);
        // revenue += totalAmount;
        // }
        // return revenue;
    }

    public List<PerformanceHeatmapDTO> getPerformanceHeatmap(String email, String branch, Date startDate, Date endDate) {
        BoxStore store = SyncManager.init(email);
        Box<Invoice> invoiceBox = store.boxFor(Invoice.class);
        QueryBuilder<Invoice> invoiceQb = invoiceBox.query();

        if (startDate != null && endDate != null) {
            invoiceQb = invoiceQb.between(Invoice_.createdDate, startDate, endDate);
        }
        if (branch != null && !branch.isBlank()) {
            invoiceQb = invoiceQb.equal(Invoice_.branchId, branch, QueryBuilder.StringOrder.CASE_INSENSITIVE);
        }

        List<Invoice> allInvoices = invoiceQb.build().find();
        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
        Map<String, Map<String, Double>> branchMonthProfit = new HashMap<>();

        for (Invoice invoice : allInvoices) {
            String bId = invoice.getBranchId() != null ? invoice.getBranchId() : "Unknown";
            String monthString = "Unknown";
            if (invoice.getCreatedDate() != null) {
                monthString = monthFormat.format(invoice.getCreatedDate());
            }

            double amount = invoice.getAmountTo();
            String invNum = invoice.getInvNum() != null ? invoice.getInvNum() : "";
            boolean isExpense = AccountUtil.isAddedInvoice(invNum);

            double profitContribution = amount - (isExpense ? amount : 0.0);

            branchMonthProfit.computeIfAbsent(bId, k -> new HashMap<>())
                    .merge(monthString, profitContribution, Double::sum);
        }

        List<PerformanceHeatmapDTO> result = new ArrayList<>();
        for (Map.Entry<String, Map<String, Double>> branchEntry : branchMonthProfit.entrySet()) {
            String bId = branchEntry.getKey();
            for (Map.Entry<String, Double> monthEntry : branchEntry.getValue().entrySet()) {
                double profit = AccountUtil.round(monthEntry.getValue());
                result.add(new PerformanceHeatmapDTO(bId, profit, monthEntry.getKey()));
            }
        }

        return result;
    }
}
