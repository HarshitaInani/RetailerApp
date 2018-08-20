package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package;

/**
 * Created by Krishna on 03-01-2017.
 */

public class ItemList {
    String ItemId;
    String UnitId;
    String Categoryid;
    String SubCategoryId;
    String SubsubCategoryid;
    String itemname;
    String UnitName;
    String PurchaseUnitName;
    String price;
    String SellingUnitName;
    String SellingSku;
    String UnitPrice;
    String VATTax;
    String LogoUrl;
    String MinOrderQty;
    String Discount;
    String TotalTaxPercentage;
    String HindiName;


    String dreamPoint;
    String promoPoint;
    String marginPoint;


    String warehouseId;
    String companyId;
    String itemNumber;
    String isoffer;



    public ItemList(String itemId, String unitId, String categoryid, String subCategoryId, String subsubCategoryid, String itemname, String unitName, String purchaseUnitName, String price, String sellingUnitName, String sellingSku, String unitPrice, String VATTax, String logoUrl, String minOrderQty, String discount, String totalTaxPercentage, String HindiName , String dreamPoint , String promoPoint, String marginPoint, String warehouseId, String companyId, String itemNumber,String isoffer) {
        ItemId = itemId;
        UnitId = unitId;
        Categoryid = categoryid;
        SubCategoryId = subCategoryId;
        SubsubCategoryid = subsubCategoryid;
        this.itemname = itemname;
        UnitName = unitName;
        PurchaseUnitName = purchaseUnitName;
        this.price = price;
        SellingUnitName = sellingUnitName;
        SellingSku = sellingSku;
        UnitPrice = unitPrice;
        this.VATTax = VATTax;
        LogoUrl = logoUrl;
        MinOrderQty = minOrderQty;
        Discount = discount;
        TotalTaxPercentage = totalTaxPercentage;
        this.HindiName = HindiName;


        this.dreamPoint = dreamPoint;

        this.promoPoint = promoPoint;
        this.marginPoint = marginPoint;


        this.warehouseId = warehouseId;
        this.companyId = companyId;
        this.itemNumber = itemNumber;
        this.isoffer = isoffer;


    }

    public String getIsoffer() {
        return isoffer;
    }

    public void setIsoffer(String isoffer) {
        this.isoffer = isoffer;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getHindiName() {
        return HindiName;
    }

    public void setHindiName(String hindiName) {
        HindiName = hindiName;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getUnitId() {
        return UnitId;
    }

    public void setUnitId(String unitId) {
        UnitId = unitId;
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

    public String getSubsubCategoryid() {
        return SubsubCategoryid;
    }



    public String getDreamPoint() {
        return dreamPoint;
    }

    public void setDreamPoint(String dreamPoint) {
        this.dreamPoint = dreamPoint;
    }

    public String getPromoPoint() {
        return promoPoint;
    }

    public void setPromoPoint(String promoPoint) {
        this.promoPoint = promoPoint;
    }

    public String getMarginPoint() {
        return marginPoint;
    }

    public void setMarginPoint(String marginPoint) {
        this.marginPoint = marginPoint;
    }





    public void setSubsubCategoryid(String subsubCategoryid) {
        SubsubCategoryid = subsubCategoryid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public String getPurchaseUnitName() {
        return PurchaseUnitName;
    }

    public void setPurchaseUnitName(String purchaseUnitName) {
        PurchaseUnitName = purchaseUnitName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSellingUnitName() {
        return SellingUnitName;
    }

    public void setSellingUnitName(String sellingUnitName) {
        SellingUnitName = sellingUnitName;
    }

    public String getSellingSku() {
        return SellingSku;
    }

    public void setSellingSku(String sellingSku) {
        SellingSku = sellingSku;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getVATTax() {
        return VATTax;
    }

    public void setVATTax(String VATTax) {
        this.VATTax = VATTax;
    }

    public String getLogoUrl() {
        return LogoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        LogoUrl = logoUrl;
    }

    public String getMinOrderQty() {
        return MinOrderQty;
    }

    public void setMinOrderQty(String minOrderQty) {
        MinOrderQty = minOrderQty;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getTotalTaxPercentage() {
        return TotalTaxPercentage;
    }

    public void setTotalTaxPercentage(String totalTaxPercentage) {
        TotalTaxPercentage = totalTaxPercentage;

    }
    public String toString()
    {
      //  return MinOrderQty+" - Price "+UnitPrice;
        return LogoUrl;
    }



}
