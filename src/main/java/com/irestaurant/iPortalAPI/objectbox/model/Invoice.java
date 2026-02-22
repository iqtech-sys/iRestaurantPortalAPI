package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToOne;

import java.util.Date;
import java.util.Objects;

@Entity
public class Invoice {
    
    @Id(assignable = true)
    private long id;
    
    @Unique(onConflict = io.objectbox.annotation.ConflictStrategy.REPLACE)
    private String invNum;
    
    private double amountFrom;
    
    private double amountTo;
    
    private double taxRate;
    
    private String note;
    
    private String branchId;
    
    private Date createdDate;
    
    // Relational fields (declared but not initialized - ObjectBox will handle them)
    private transient ToOne<Account> accountFrom;
    
    private transient ToOne<Account> accountTo;
    
    private transient ToOne<Entry> entry;
    
    public ToOne<Account> getAccountFrom() {
        return accountFrom;
    }
    
    public ToOne<Account> getAccountTo() {
        return accountTo;
    }
    
    public ToOne<Entry> getEntry() {
        return entry;
    }
    
    public void setAccountFrom(ToOne<Account> accountFrom) {
        this.accountFrom = accountFrom;
    }
    
    public void setAccountTo(ToOne<Account> accountTo) {
        this.accountTo = accountTo;
    }
    
    public void setEntry(ToOne<Entry> entry) {
        this.entry = entry;
    }
    
    public Invoice() {
    }
   
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
    
    public double getTaxRate() {
        return taxRate;
    }
    
    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }
    
    public String getNote() {
        return note;
    }
    
    public void setNote(String note) {
        this.note = note;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 37 * hash + Objects.hashCode(this.invNum);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.amountFrom) ^ (Double.doubleToLongBits(this.amountFrom) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.amountTo) ^ (Double.doubleToLongBits(this.amountTo) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.taxRate) ^ (Double.doubleToLongBits(this.taxRate) >>> 32));
        hash = 37 * hash + Objects.hashCode(this.note);
        hash = 37 * hash + Objects.hashCode(this.branchId);
        hash = 37 * hash + Objects.hashCode(this.createdDate);
        hash = 37 * hash + Objects.hashCode(this.accountFrom);
        hash = 37 * hash + Objects.hashCode(this.accountTo);
        hash = 37 * hash + Objects.hashCode(this.entry);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Invoice other = (Invoice) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.amountFrom) != Double.doubleToLongBits(other.amountFrom)) {
            return false;
        }
        if (Double.doubleToLongBits(this.amountTo) != Double.doubleToLongBits(other.amountTo)) {
            return false;
        }
        if (Double.doubleToLongBits(this.taxRate) != Double.doubleToLongBits(other.taxRate)) {
            return false;
        }
        if (!Objects.equals(this.invNum, other.invNum)) {
            return false;
        }
        if (!Objects.equals(this.note, other.note)) {
            return false;
        }
        if (!Objects.equals(this.branchId, other.branchId)) {
            return false;
        }
        if (!Objects.equals(this.createdDate, other.createdDate)) {
            return false;
        }
        if (!Objects.equals(this.accountFrom, other.accountFrom)) {
            return false;
        }
        if (!Objects.equals(this.accountTo, other.accountTo)) {
            return false;
        }
        return Objects.equals(this.entry, other.entry);
    }
    
    @Override
    public String toString() {
        return "Invoice{" + "id=" + id + ", invNum='" + invNum + '\'' 
                          + ", amountFrom=" + amountFrom + ", amountTo=" + amountTo 
                          + ", taxRate=" + taxRate + ", note='" + note + '\'' 
                          + ", branchId='" + branchId + '\'' + ", createdDate=" + createdDate + '}';
    } 
}
