package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.ConflictStrategy;
import com.irestaurant.iPortalAPI.converter.LocalDateTimeConverter;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToMany;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Category {

    @Id(assignable = true)
    long id;

    @Unique(onConflict = ConflictStrategy.REPLACE)
    String title;

    String description;

    @Convert(converter = LocalDateTimeConverter.class, dbType = Long.class)
    LocalDateTime createdDate;

    byte[] image;
    String branchId;

    // @Backlink('category')
    private transient ToMany<Product> products;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public ToMany<Product> getProducts() {
        return products;
    }

    public void setProducts(ToMany<Product> products) {
        this.products = products;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 97 * hash + Objects.hashCode(this.title);
        hash = 97 * hash + Objects.hashCode(this.description);
        hash = 97 * hash + Objects.hashCode(this.createdDate);
        hash = 97 * hash + Arrays.hashCode(this.image);
        hash = 97 * hash + Objects.hashCode(this.branchId);
        hash = 97 * hash + Objects.hashCode(this.products);
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
        final Category other = (Category) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.branchId, other.branchId)) {
            return false;
        }
        if (!Objects.equals(this.createdDate, other.createdDate)) {
            return false;
        }
        if (!Arrays.equals(this.image, other.image)) {
            return false;
        }
        return Objects.equals(this.products, other.products);
    }

    @Override
    public String toString() {
        return "Category{" + "id=" + id + ", title=" + title + ", description=" + description + ", createdDate="
                + createdDate + ", image=" + image + ", branchId=" + branchId + ", products=" + products + '}';
    }

}
