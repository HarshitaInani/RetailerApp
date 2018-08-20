package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean;

/**
 * Created by User on 11-08-2018.
 */

public class shopbycompanybean {
    String SubCategoryId;
    String CompanyId;
    String Categoryid;
    String Warehouseid;
    String CategoryName;
    String SubcategoryName;
    String Discription;
    String SortOrder;
    String IsPramotional;
    String CreatedDate;
    String UpdatedDate;
    String CreatedBy;
    String UpdateBy;
    String Code;
    String LogoUrl;
    String Deleted;
    String IsActive;
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

    public String getLogoUrl() {
        return LogoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        LogoUrl = logoUrl;
    }



    public shopbycompanybean(String Categoryid, String subCategoryId, String SubsubcategoryName, String LogoUrl) {
       this. SubCategoryId = subCategoryId;
        this. SubcategoryName = SubsubcategoryName;
        this. LogoUrl = LogoUrl;
        this. Categoryid=Categoryid;

    }
@Override
    public  String toString() {
     return    SubcategoryName;
    }
}
