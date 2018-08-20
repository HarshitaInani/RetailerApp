package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean;

/**
 * Created by User on 9/12/2017.
 */

public class NewsFeeds {

    private String imgURL, feedName, content;
    private int rating;
String SubsubCategoryid,SubsubcategoryName,Categoryid,Warehouseid,SubCategoryId,SubcategoryName;
    public NewsFeeds(String name, String imgURL, String SubsubCategoryid, String SubsubcategoryName, String Categoryid, String Warehouseid, String SubCategoryId, String SubcategoryName) {
        this.feedName = name;
        this.SubsubCategoryid = SubsubCategoryid;
        this.SubsubcategoryName = SubsubcategoryName;
        this.Categoryid = Categoryid;
        this.Warehouseid = Warehouseid;
        this.SubCategoryId = SubCategoryId;
        this.SubcategoryName = SubcategoryName;
this.imgURL=imgURL;
        this.rating = rating;




    }


    public String getSubcategoryName() {
        return SubcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        SubcategoryName = subcategoryName;
    }

    public String getContent() {
        return content;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getFeedName() {
        return feedName;
    }

    public int getRating() {
        return rating;
    }


    public String getWarehouseid() {
        return Warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        Warehouseid = warehouseid;
    }

    public String getCategoryid() {
        return Categoryid;
    }

    public void setCategoryid(String categoryid) {
        Categoryid = categoryid;
    }

    public String getSubsubcategoryName() {
        return SubsubcategoryName;
    }

    public void setSubsubcategoryName(String subsubcategoryName) {
        SubsubcategoryName = subsubcategoryName;
    }

    public String getSubsubCategoryid() {
        return SubsubCategoryid;
    }

    public void setSubsubCategoryid(String subsubCategoryid) {
        SubsubCategoryid = subsubCategoryid;
    }

    public String getSubCategoryId() {
        return SubCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        SubCategoryId = subCategoryId;
    }
}