package com.irestaurant.iPortalAPI.objectbox.model;

import com.irestaurant.iPortalAPI.converter.LocalDateTimeConverter;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Entry {

    @Id(assignable = true)
    long id;

    double entryAmount;

    String entryNote;

    String branchId;

    @Convert(converter = LocalDateTimeConverter.class, dbType = Long.class)
    LocalDateTime createdDate;

    private transient ToOne<Invoice> invoice;

    private transient ToOne<Account> account;

    public void setInvoice(ToOne<Invoice> invoice) {
        this.invoice = invoice;
    }

    public void setAccount(ToOne<Account> account) {
        this.account = account;
    }

    public ToOne<Invoice> getInvoice() {
        return invoice;
    }

    public ToOne<Account> getAccount() {
        return account;
    }

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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Entry() {
    }

    public Entry(long id, double entryAmount, String entryNote, String branchId,
            LocalDateTime createdDate) {
        this.id = id;
        this.entryAmount = entryAmount;
        this.entryNote = entryNote;
        this.branchId = branchId;
        this.createdDate = createdDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.entryAmount)
                ^ (Double.doubleToLongBits(this.entryAmount) >>> 32));
        hash = 23 * hash + Objects.hashCode(this.entryNote);
        hash = 23 * hash + Objects.hashCode(this.branchId);
        hash = 23 * hash + Objects.hashCode(this.createdDate);
        hash = 23 * hash + Objects.hashCode(this.invoice);
        hash = 23 * hash + Objects.hashCode(this.account);
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
        final Entry other = (Entry) obj;
        if (Double.doubleToLongBits(this.entryAmount) != Double.doubleToLongBits(other.entryAmount)) {
            return false;
        }
        if (!Objects.equals(this.entryNote, other.entryNote)) {
            return false;
        }
        if (!Objects.equals(this.branchId, other.branchId)) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.createdDate, other.createdDate)) {
            return false;
        }
        if (!Objects.equals(this.invoice, other.invoice)) {
            return false;
        }
        return Objects.equals(this.account, other.account);
    }

    @Override
    public String toString() {
        return "Entry{" + "id=" + id + ", entryAmount=" + entryAmount + ", entryNote=" + entryNote + ", branchId="
                + branchId + ", createdDate=" + createdDate + ", invoice=" + invoice + ", account=" + account + '}';
    }
}
