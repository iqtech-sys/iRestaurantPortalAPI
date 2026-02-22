package com.irestaurant.iPortalAPI.objectbox.model;

import com.irestaurant.iPortalAPI.converter.LocalDateTimeConverter;
import com.irestaurant.iPortalAPI.enumerators.NotificationTypes;
import com.irestaurant.iPortalAPI.enumerators.Usecases;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Notification {

    @Id(assignable = true)
    long id;

    String title;
    String subtitle;
    String branchId;

    int badgeCount;
    boolean canReply;

    @Convert(converter = LocalDateTimeConverter.class, dbType = Long.class)
    LocalDateTime creationDate = LocalDateTime.now();

    int type = NotificationTypes.None.ordinal();
    int usecase = Usecases.None.ordinal();

    private transient ToOne<User> user;
    // @Backlink('notification') // Links back from notification message to
    // notification.
    private transient ToMany<NotificationMessage> messages;

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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public int getBadgeCount() {
        return badgeCount;
    }

    public void setBadgeCount(int badgeCount) {
        this.badgeCount = badgeCount;
    }

    public boolean isCanReply() {
        return canReply;
    }

    public void setCanReply(boolean canReply) {
        this.canReply = canReply;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUsecase() {
        return usecase;
    }

    public void setUsecase(int usecase) {
        this.usecase = usecase;
    }

    public ToOne<User> getUser() {
        return user;
    }

    public void setUser(ToOne<User> user) {
        this.user = user;
    }

    public ToMany<NotificationMessage> getMessages() {
        return messages;
    }

    public void setMessages(ToMany<NotificationMessage> messages) {
        this.messages = messages;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 37 * hash + Objects.hashCode(this.title);
        hash = 37 * hash + Objects.hashCode(this.subtitle);
        hash = 37 * hash + Objects.hashCode(this.branchId);
        hash = 37 * hash + this.badgeCount;
        hash = 37 * hash + (this.canReply ? 1 : 0);
        hash = 37 * hash + Objects.hashCode(this.creationDate);
        hash = 37 * hash + this.type;
        hash = 37 * hash + this.usecase;
        hash = 37 * hash + Objects.hashCode(this.user);
        hash = 37 * hash + Objects.hashCode(this.messages);
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
        final Notification other = (Notification) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.badgeCount != other.badgeCount) {
            return false;
        }
        if (this.canReply != other.canReply) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (this.usecase != other.usecase) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.subtitle, other.subtitle)) {
            return false;
        }
        if (!Objects.equals(this.branchId, other.branchId)) {
            return false;
        }
        if (!Objects.equals(this.creationDate, other.creationDate)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return Objects.equals(this.messages, other.messages);
    }

    @Override
    public String toString() {
        return "Notification{" + "id=" + id + ", title=" + title + ", subtitle=" + subtitle + ", branchId=" + branchId
                + ", badgeCount=" + badgeCount + ", canReply=" + canReply + ", creationDate=" + creationDate + ", type="
                + type + ", usecase=" + usecase + ", user=" + user + ", messages=" + messages + '}';
    }

}
