package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package;

/**
 * Created by User on 09-08-2018.
 */

public class homecarebean {
    String BaseCategoryId;
    String Categoryid;
    String Warehouseid;
    String CategoryName;
    String LogoUrl;

    public homecarebean(String baseCategoryId, String categoryid, String warehouseid, String categoryName, String logoUrl) {
        BaseCategoryId = baseCategoryId;
        Categoryid = categoryid;
        Warehouseid = warehouseid;
        CategoryName = categoryName;
        LogoUrl = logoUrl;
    }

    public String getBaseCategoryId() {
        return BaseCategoryId;
    }

    public void setBaseCategoryId(String baseCategoryId) {
        BaseCategoryId = baseCategoryId;
    }

    public String getCategoryid() {
        return Categoryid;
    }

    public void setCategoryid(String categoryid) {
        Categoryid = categoryid;
    }

    public String getWarehouseid() {
        return Warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        Warehouseid = warehouseid;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getLogoUrl() {
        return LogoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        LogoUrl = logoUrl;
    }
    @Override
    public String toString() {
        return BaseCategoryId;
    }
}


