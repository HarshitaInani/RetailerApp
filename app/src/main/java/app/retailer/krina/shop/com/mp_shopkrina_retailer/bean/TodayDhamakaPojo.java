package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean;

/**
 * Created by User on 9/12/2017.
 */

public class TodayDhamakaPojo {

    String Id;
    String Name;
    String Discription;
    String LogoUrl;
    public TodayDhamakaPojo(String id, String name, String discription, String logoUrl) {
        this.Id = id;
        this.Name = name;
        this.Discription = discription;
        this.LogoUrl = logoUrl;

    }

    public String getLogoUrl() {
        return LogoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        LogoUrl = logoUrl;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }

public String toString(){
        return LogoUrl;
    }
}