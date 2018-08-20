package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package;

/**
 * Created by Krishna on 30-12-2016.
 */

public class BaseCatBean {
    String BaseCategoryId;
    String Warehouseid;
    String BaseCategoryName;
    String BaseCatLogoUrl;

    public BaseCatBean(String baseCategoryId, String warehouseid, String baseCategoryName, String baseCatLogoUrl) {
        BaseCategoryId = baseCategoryId;
        Warehouseid = warehouseid;
        BaseCategoryName = baseCategoryName;
        BaseCatLogoUrl = baseCatLogoUrl;
    }

    public String getBaseCategoryId() {
        return BaseCategoryId;
    }

    public void setBaseCategoryId(String baseCategoryId) {
        BaseCategoryId = baseCategoryId;
    }

    public String getWarehouseid() {
        return Warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        Warehouseid = warehouseid;
    }

    public String getBaseCategoryName() {
        return BaseCategoryName;
    }

    public void setBaseCategoryName(String baseCategoryName) {
        BaseCategoryName = baseCategoryName;
    }

    public String getBaseCatLogoUrl() {
        return BaseCatLogoUrl;
    }

    public void setBaseCatLogoUrl(String baseCatLogoUrl) {
        BaseCatLogoUrl = baseCatLogoUrl;
    }
}
