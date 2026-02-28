package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Sync;
import io.objectbox.converter.PropertyConverter;
import io.objectbox.relation.ToOne;

import java.util.Date;

@Sync
@Entity
public class Entry {
    @Id(assignable = true)
    public long id;
    
    public double entryAmount;
    public String entryNote;
    public String branchId;
    
    //@Convert(converter = DateConverter.class, dbType = Long.class)
    public Date createdDate;
    
    public ToOne<Invoice> invoice;
    public ToOne<Account> account;
    
    public Entry() {
        this.invoice = new ToOne<>(this, Entry_.invoice);
        this.account = new ToOne<>(this, Entry_.account);
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

    public double getEntryAmount() {
        return entryAmount;
    }

    public void setEntryAmount(double entryAmount) {
        this.entryAmount = entryAmount;
    }

    public String getEntryNote() {
        return entryNote;
    }

    public void setEntryNote(String entryNote) {
        this.entryNote = entryNote;
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

    public ToOne<Invoice> getInvoice() {
        return invoice;
    }

    public void setInvoice(ToOne<Invoice> invoice) {
        this.invoice = invoice;
    }

    public ToOne<Account> getAccount() {
        return account;
    }

    public void setAccount(ToOne<Account> account) {
        this.account = account;
    }
    
    
}
