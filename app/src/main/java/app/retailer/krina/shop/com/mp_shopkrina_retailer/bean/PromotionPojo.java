package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean;

import android.util.Log;

import java.net.IDN;

import static android.os.Build.ID;

/**
 * Created by User on 9/12/2017.
 */

public class PromotionPojo {

    String Id;
    String Name;
    String Discription;
    String LogoUrl;
    public PromotionPojo(String id, String name, String discription, String logoUrl) {
        this.Id = id;
        this.Name = name;
        this.Discription = discription;
        this.LogoUrl = logoUrl;
        Log.d("appid",Id);
        Log.d("appid",Name);
        Log.d("appid",Name);
        Log.d("appid",Name);

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

    @Override
    public String toString() {

        return LogoUrl;
    }
}