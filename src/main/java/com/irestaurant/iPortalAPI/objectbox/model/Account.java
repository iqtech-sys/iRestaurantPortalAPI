package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.ConflictStrategy;
import com.irestaurant.iPortalAPI.converter.LocalDateTimeConverter;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToMany;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Account {

    @Id(assignable = true)
    long id;

    @Unique(onConflict = ConflictStrategy.REPLACE)
    String accNumber;

    String title;

    String titleAr;

    @Convert(converter = LocalDateTimeConverter.class, dbType = Long.class)
    LocalDateTime createdDate;

    double balance;

    String branchId;

    private transient ToMany<Invoice> invoiceTos;
    // @Backlink('accountFrom')
    private transient ToMany<Invoice> invoiceFroms;
    // @Backlink('account')
    private transient ToMany<Entry> entries;

    public ToMany<Invoice> getInvoiceTos() {
        return invoiceTos;
    }

    public void setInvoiceTos(ToMany<Invoice> invoiceTos) {
        this.invoiceTos = invoiceTos;
    }

    public ToMany<Invoice> getInvoiceFroms() {
        return invoiceFroms;
    }

    public void setInvoiceFroms(ToMany<Invoice> invoiceFroms) {
        this.invoiceFroms = invoiceFroms;
    }

    public ToMany<Entry> getEntries() {
        return entries;
    }

    public void setEntries(ToMany<Entry> entries) {
        this.entries = entries;
    }

    public Account() {
    }

    public Account(long id, String title, String titleAr, String accNumber,
            LocalDateTime createdDate, double balance, String branchId) {
        this.id = id;
        this.accNumber = accNumber;
        this.title = title;
        this.titleAr = titleAr;
        this.createdDate = createdDate;
        this.balance = balance;
        this.branchId = branchId;
    }

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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
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

    @Override
    public String toString() {
        return "Account{" + "id=" + id + ", title=" + title + ", titleAr=" + titleAr + ", accNumber=" + accNumber
                + ", createdDate=" + createdDate + ", balance=" + balance + ", branchId=" + branchId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.title);
        hash = 97 * hash + Objects.hashCode(this.titleAr);
        hash = 97 * hash + Objects.hashCode(this.accNumber);
        hash = 97 * hash + Objects.hashCode(this.createdDate);
        hash = 97 * hash
                + (int) (Double.doubleToLongBits(this.balance) ^ (Double.doubleToLongBits(this.balance) >>> 32));
        hash = 97 * hash + Objects.hashCode(this.branchId);
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
        final Account other = (Account) obj;
        if (Double.doubleToLongBits(this.balance) != Double.doubleToLongBits(other.balance)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.titleAr, other.titleAr)) {
            return false;
        }
        if (!Objects.equals(this.accNumber, other.accNumber)) {
            return false;
        }
        if (!Objects.equals(this.branchId, other.branchId)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.createdDate, other.createdDate);
    }
}
