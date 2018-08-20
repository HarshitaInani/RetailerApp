package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean;

/**
 * Created by Krishna on 25-03-2017.
 */

public class NotificationListBean {
    String id;
    String title;
    String Message;
    String ImageUrl;
    String NotificationTime;
    boolean Deleted;

    public NotificationListBean(String id, String title, String message, String imageUrl, String notificationTime, boolean deleted) {
        this.id = id;
        this.title = title;
        Message = message;
        ImageUrl = imageUrl;
        NotificationTime = notificationTime;
        Deleted = deleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getNotificationTime() {
        return NotificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        NotificationTime = notificationTime;
    }

    public boolean isDeleted() {
        return Deleted;
    }

    public void setDeleted(boolean deleted) {
        Deleted = deleted;
    }
}
