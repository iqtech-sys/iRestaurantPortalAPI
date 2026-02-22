package com.irestaurant.iPortalAPI.objectbox.model;

import com.irestaurant.iPortalAPI.enumerators.TableStatuses;
import com.irestaurant.iPortalAPI.enumerators.TableTypes;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import java.util.Objects;

@Entity
public class Table {

    @Id(assignable = true)
    long id;
    int seats;

    String notes;
    String tableNumber;
    String branchId;

    int status = TableStatuses.Vacant.ordinal();
    int type = TableTypes.Rounded.ordinal();

    private transient ToMany<Order> orders;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ToMany<Order> getOrders() {
        return orders;
    }

    public void setOrders(ToMany<Order> orders) {
        this.orders = orders;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 71 * hash + this.seats;
        hash = 71 * hash + Objects.hashCode(this.notes);
        hash = 71 * hash + Objects.hashCode(this.tableNumber);
        hash = 71 * hash + Objects.hashCode(this.branchId);
        hash = 71 * hash + this.status;
        hash = 71 * hash + this.type;
        hash = 71 * hash + Objects.hashCode(this.orders);
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
        final Table other = (Table) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.seats != other.seats) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (!Objects.equals(this.notes, other.notes)) {
            return false;
        }
        if (!Objects.equals(this.tableNumber, other.tableNumber)) {
            return false;
        }
        if (!Objects.equals(this.branchId, other.branchId)) {
            return false;
        }
        return Objects.equals(this.orders, other.orders);
    }

    @Override
    public String toString() {
        return "Table{" + "id=" + id + ", seats=" + seats + ", notes=" + notes + ", tableNumber=" + tableNumber
                + ", branchId=" + branchId + ", status=" + status + ", type=" + type + ", orders=" + orders + '}';
    }

}
