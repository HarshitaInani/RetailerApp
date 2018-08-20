package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package;

/**
 * Created by Krishna on 30-12-2016.
 */

public class SubSubCategoriesBean {
    String SubSubCategoryId;
    String SubSubcategoryName;
    String Categoryid;
    String SubCategoryId;

    public SubSubCategoriesBean(String subSubCategoryId, String subSubcategoryName, String categoryid, String subCategoryId) {
        SubSubCategoryId = subSubCategoryId;
        SubSubcategoryName = subSubcategoryName;
        Categoryid = categoryid;
        SubCategoryId = subCategoryId;
    }

    public String getSubSubCategoryId() {
        return SubSubCategoryId;
    }

    public void setSubSubCategoryId(String subSubCategoryId) {
        SubSubCategoryId = subSubCategoryId;
    }

    public String getSubSubcategoryName() {
        return SubSubcategoryName;
    }

    public void setSubSubcategoryName(String subSubcategoryName) {
        SubSubcategoryName = subSubcategoryName;
    }

    public String getCategoryid() {
        return Categoryid;
    }

    public void setCategoryid(String categoryid) {
        Categoryid = categoryid;
    }

    public String getSubCategoryId() {
        return SubCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        SubCategoryId = subCategoryId;
    }

    @Override
    public String toString() {
        return SubSubcategoryName+""+SubCategoryId;
    }
}
