package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Sync;
import io.objectbox.annotation.Unique;
import io.objectbox.converter.PropertyConverter;
import io.objectbox.relation.ToMany;

import java.util.Date;

@Sync
@Entity
public class Account {
    @Id(assignable = true)
    public long id;
    
    public String title;
    public String titleAr;
    
    @Unique(onConflict = ConflictStrategy.REPLACE)
    public String accNumber;
    
    //@Convert(converter = DateConverter.class, dbType = Long.class)
    public Date createdDate;
    
    public double balance;
    public String branchId;

    @Backlink(to = "account")
    public ToMany<Entry> entries;
    
    @Backlink(to = "accountFrom")
    public ToMany<Invoice> invoiceFrom;
    
    @Backlink(to = "accountTo")
    public ToMany<Invoice> invoiceTo;

    public Account() {
        this.entries = new ToMany<>(this, Account_.entries);
        this.invoiceFrom = new ToMany<>(this, Account_.invoiceFrom);
        this.invoiceTo = new ToMany<>(this, Account_.invoiceTo);
    }

//    public static class DateConverter implements PropertyConverter<Date, Long> {
//        @Override
//        public Long convertToDatabaseValue(Date entityProperty) {
//            return entityProperty != null ? entityProperty.getTime() : null;
//        }
//
//        @Override
//        public Date convertToEntityProperty(Long databaseValue) {
//            return databaseValue != null ? new Date(databaseValue) : null;
//        }
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleAr() {
        return titleAr;
    }

    public void setTitleAr(String titleAr) {
        this.titleAr = titleAr;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public ToMany<Entry> getEntries() {
        return entries;
    }

    public void setEntries(ToMany<Entry> entries) {
        this.entries = entries;
    }

    public ToMany<Invoice> getInvoiceFrom() {
        return invoiceFrom;
    }

    public void setInvoiceFrom(ToMany<Invoice> invoiceFrom) {
        this.invoiceFrom = invoiceFrom;
    }

    public ToMany<Invoice> getInvoiceTo() {
        return invoiceTo;
    }

    public void setInvoiceTo(ToMany<Invoice> invoiceTo) {
        this.invoiceTo = invoiceTo;
    }
    
    
}