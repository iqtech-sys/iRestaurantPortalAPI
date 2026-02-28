package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.ConflictStrategy;
import com.irestaurant.iPortalAPI.converter.LocalDateTimeConverter;
import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Sync;
import io.objectbox.annotation.Unique;
import io.objectbox.converter.PropertyConverter;
import io.objectbox.relation.ToMany;
import java.time.LocalDateTime;
import java.util.Date;

@Sync
@Entity
public class Category {

    @Id(assignable = true)
    long id;

    @Unique(onConflict = ConflictStrategy.REPLACE)
    String title;

    String description;

    //@Convert(converter = LocalDateTimeConverter.class, dbType = Long.class)
    Date createdDate;

    byte[] image;
    String branchId;

    @Backlink(to = "category")
    private ToMany<Product> products;

    public Category() {
        this.products = new ToMany<>(this, Category_.products);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
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
    
    
}