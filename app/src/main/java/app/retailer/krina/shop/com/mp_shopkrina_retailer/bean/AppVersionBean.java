package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean;

/**
 * Created by Krishna on 17-02-2017.
 */

public class AppVersionBean {
    double App_version;
    boolean isCompulsory;
    String createdDate;

    public AppVersionBean(double app_version, boolean isCompulsory, String createdDate) {
        this.createdDate = createdDate;
        this.isCompulsory = isCompulsory;
        App_version = app_version;
    }

    public double getApp_version() {
        return App_version;
    }

    public void setApp_version(double app_version) {
        App_version = app_version;
    }

    public boolean isCompulsory() {
        return isCompulsory;
    }

    public void setCompulsory(boolean compulsory) {
        isCompulsory = compulsory;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
