package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import com.irestaurant.iPortalAPI.enumerators.TableStatuses;
import com.irestaurant.iPortalAPI.enumerators.TableTypes;
import io.objectbox.annotation.Sync;

@Sync
@Entity
public class Table {

    @Id(assignable = true)
    public long id;

    public long seats;
    public String notes;
    public String tableNumber;
    public String branchId;

    public long status = TableStatuses.Vacant.ordinal();
    public long type = TableTypes.Rounded.ordinal();

    @Backlink(to = "table")
    public ToMany<Order> orders;

    public Table() {
       this.orders = new ToMany<>(this, Table_.orders);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSeats() {
        return seats;
    }

    public void setSeats(long seats) {
        this.seats = seats;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public ToMany<Order> getOrders() {
        return orders;
    }

    public void setOrders(ToMany<Order> orders) {
        this.orders = orders;
    }
    
    
}
