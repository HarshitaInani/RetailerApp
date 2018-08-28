package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package;

import java.util.ArrayList;
import java.util.List;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.NewsFeeds;

/**
 * Created by User on 28-08-2018.
 */

public class BrandListPojo {

    List<NewsFeeds> brands=new ArrayList<>();

    public List<NewsFeeds> getBrands() {
        return brands;
    }
    public void setBrands(List<NewsFeeds> brands) {
        this.brands = brands;
    }


}
