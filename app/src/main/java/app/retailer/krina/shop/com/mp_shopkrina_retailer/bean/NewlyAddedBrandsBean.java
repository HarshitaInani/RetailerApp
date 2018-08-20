package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean;

/**
 * Created by Krishna on 30-01-2017.
 */

public class NewlyAddedBrandsBean {
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

    public NewlyAddedBrandsBean(String subCategoryId, String companyId, String categoryid, String warehouseid, String categoryName, String subcategoryName, String discription, String sortOrder, String isPramotional, String createdDate, String updatedDate, String createdBy, String updateBy, String code, String logoUrl, String deleted, String isActive) {
        SubCategoryId = subCategoryId;
        CompanyId = companyId;
        Categoryid = categoryid;
        Warehouseid = warehouseid;
        CategoryName = categoryName;
        SubcategoryName = subcategoryName;
        Discription = discription;
        SortOrder = sortOrder;
        IsPramotional = isPramotional;
        CreatedDate = createdDate;
        UpdatedDate = updatedDate;
        CreatedBy = createdBy;
        UpdateBy = updateBy;
        Code = code;
        LogoUrl = logoUrl;
        Deleted = deleted;
        IsActive = isActive;
    }

    public String getSubCategoryId() {
        return SubCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        SubCategoryId = subCategoryId;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
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

    public String getSubcategoryName() {
        return SubcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        SubcategoryName = subcategoryName;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }

    public String getSortOrder() {
        return SortOrder;
    }

    public void setSortOrder(String sortOrder) {
        SortOrder = sortOrder;
    }

    public String getIsPramotional() {
        return IsPramotional;
    }

    public void setIsPramotional(String isPramotional) {
        IsPramotional = isPramotional;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getUpdateBy() {
        return UpdateBy;
    }

    public void setUpdateBy(String updateBy) {
        UpdateBy = updateBy;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getLogoUrl() {
        return LogoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        LogoUrl = logoUrl;
    }

    public String getDeleted() {
        return Deleted;
    }

    public void setDeleted(String deleted) {
        Deleted = deleted;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }
    @Override
    public String toString() {

        return LogoUrl;
    }
}
