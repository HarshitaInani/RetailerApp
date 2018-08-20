package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package;

/**
 * Created by Krishna on 30-12-2016.
 */

public class SubCategoriesBean {
    String SubCategoryId;
    String Categoryid;
    String SubcategoryName;

    public SubCategoriesBean(String subCategoryId, String categoryid, String subcategoryName) {
        SubCategoryId = subCategoryId;
        Categoryid = categoryid;
        SubcategoryName = subcategoryName;
    }

    public String getSubCategoryId() {
        return SubCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        SubCategoryId = subCategoryId;
    }

    public String getCategoryid() {
        return Categoryid;
    }

    public void setCategoryid(String categoryid) {
        Categoryid = categoryid;
    }

    public String getSubcategoryName() {
        return SubcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        SubcategoryName = subcategoryName;
    }
}
