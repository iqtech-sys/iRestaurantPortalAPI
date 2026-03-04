package com.irestaurant.iPortalAPI.objectbox.model;

import com.irestaurant.iPortalAPI.util.Constants;
import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Sync;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToOne;
import java.util.Date;

@Sync
@Entity
public class Invoice {

    @Id(assignable = true)
    long id;

    @Unique(onConflict = ConflictStrategy.REPLACE)
    String invNum;

    double amountFrom;
    double amountTo;
    String note;
    double taxRate;
    String branchId;

    Date createdDate;

    public ToOne<Account> accountFrom;
    public ToOne<Account> accountTo;
    public ToOne<Entry> entry;

    public Invoice() {
        this.accountFrom = new ToOne<>(this, Invoice_.accountFrom);
        this.accountTo = new ToOne<>(this, Invoice_.accountTo);
        this.entry = new ToOne<>(this, Invoice_.entry);
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

    public String getInvNum() {
        return invNum;
    }

    public void setInvNum(String invNum) {
        this.invNum = invNum;
    }

    public double getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(double amountFrom) {
        this.amountFrom = amountFrom;
    }

    public double getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(double amountTo) {
        this.amountTo = amountTo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public ToOne<Account> getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(ToOne<Account> accountFrom) {
        this.accountFrom = accountFrom;
    }

    public ToOne<Account> getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(ToOne<Account> accountTo) {
        this.accountTo = accountTo;
    }

    public ToOne<Entry> getEntry() {
        return entry;
    }

    public void setEntry(ToOne<Entry> entry) {
        this.entry = entry;
    }
}
