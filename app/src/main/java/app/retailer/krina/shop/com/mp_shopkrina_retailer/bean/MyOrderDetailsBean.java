package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean;

/**
 * Created by Krishna on 26-01-2017.
 */

public class MyOrderDetailsBean {
    String OrderDetailsId;
    String OrderId;
    String CustomerId;
    String CustomerName;
    String City;
    String Mobile;
    String OrderDate;
    String CompanyId;
    String CityId;
    String Warehouseid;
    String WarehouseName;
    String CategoryName;
    String ItemId;
    String Itempic;
    String itemname;
    String itemcode;
    String itemNumber;
    String Barcode;
    String price;
    String UnitPrice;
    String Purchaseprice;
    String MinOrderQty;
    String MinOrderQtyPrice;
    String qty;
    String Noqty;
    String AmtWithoutTaxDisc;
    String AmtWithoutAfterTaxDisc;
    String TotalAmountAfterTaxDisc;
    String NetAmmount;
    String DiscountPercentage;
    String DiscountAmmount;
    String NetAmtAfterDis;
    String TaxPercentage;
    String TaxAmmount;
    String TotalAmt;
    String CreatedDate;
    String UpdatedDate;
    String Deleted;
    String Status;
    String SizePerUnit;
    String CurrentStock;

    public MyOrderDetailsBean(String orderDetailsId, String orderId, String customerId, String customerName, String city, String mobile, String orderDate, String companyId, String cityId, String warehouseid, String warehouseName, String categoryName, String itemId, String itempic, String itemname, String itemcode, String itemNumber, String barcode, String price, String unitPrice, String purchaseprice, String minOrderQty, String minOrderQtyPrice, String qty, String noqty, String amtWithoutTaxDisc, String amtWithoutAfterTaxDisc, String totalAmountAfterTaxDisc, String netAmmount, String discountPercentage, String discountAmmount, String netAmtAfterDis, String taxPercentage, String taxAmmount, String totalAmt, String createdDate, String updatedDate, String deleted, String status, String sizePerUnit, String currentStock) {
        OrderDetailsId = orderDetailsId;
        OrderId = orderId;
        CustomerId = customerId;
        CustomerName = customerName;
        City = city;
        Mobile = mobile;
        OrderDate = orderDate;
        CompanyId = companyId;
        CityId = cityId;
        Warehouseid = warehouseid;
        WarehouseName = warehouseName;
        CategoryName = categoryName;
        ItemId = itemId;
        Itempic = itempic;
        this.itemname = itemname;
        this.itemcode = itemcode;
        this.itemNumber = itemNumber;
        Barcode = barcode;
        this.price = price;
        UnitPrice = unitPrice;
        Purchaseprice = purchaseprice;
        MinOrderQty = minOrderQty;
        MinOrderQtyPrice = minOrderQtyPrice;
        this.qty = qty;
        Noqty = noqty;
        AmtWithoutTaxDisc = amtWithoutTaxDisc;
        AmtWithoutAfterTaxDisc = amtWithoutAfterTaxDisc;
        TotalAmountAfterTaxDisc = totalAmountAfterTaxDisc;
        NetAmmount = netAmmount;
        DiscountPercentage = discountPercentage;
        DiscountAmmount = discountAmmount;
        NetAmtAfterDis = netAmtAfterDis;
        TaxPercentage = taxPercentage;
        TaxAmmount = taxAmmount;
        TotalAmt = totalAmt;
        CreatedDate = createdDate;
        UpdatedDate = updatedDate;
        Deleted = deleted;
        Status = status;
        SizePerUnit = sizePerUnit;
        CurrentStock = currentStock;
    }

    public String getOrderDetailsId() {
        return OrderDetailsId;
    }

    public void setOrderDetailsId(String orderDetailsId) {
        OrderDetailsId = orderDetailsId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getWarehouseid() {
        return Warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        Warehouseid = warehouseid;
    }

    public String getWarehouseName() {
        return WarehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        WarehouseName = warehouseName;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getItempic() {
        return Itempic;
    }

    public void setItempic(String itempic) {
        Itempic = itempic;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getPurchaseprice() {
        return Purchaseprice;
    }

    public void setPurchaseprice(String purchaseprice) {
        Purchaseprice = purchaseprice;
    }

    public String getMinOrderQty() {
        return MinOrderQty;
    }

    public void setMinOrderQty(String minOrderQty) {
        MinOrderQty = minOrderQty;
    }

    public String getMinOrderQtyPrice() {
        return MinOrderQtyPrice;
    }

    public void setMinOrderQtyPrice(String minOrderQtyPrice) {
        MinOrderQtyPrice = minOrderQtyPrice;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getNoqty() {
        return Noqty;
    }

    public void setNoqty(String noqty) {
        Noqty = noqty;
    }

    public String getAmtWithoutTaxDisc() {
        return AmtWithoutTaxDisc;
    }

    public void setAmtWithoutTaxDisc(String amtWithoutTaxDisc) {
        AmtWithoutTaxDisc = amtWithoutTaxDisc;
    }

    public String getAmtWithoutAfterTaxDisc() {
        return AmtWithoutAfterTaxDisc;
    }

    public void setAmtWithoutAfterTaxDisc(String amtWithoutAfterTaxDisc) {
        AmtWithoutAfterTaxDisc = amtWithoutAfterTaxDisc;
    }

    public String getTotalAmountAfterTaxDisc() {
        return TotalAmountAfterTaxDisc;
    }

    public void setTotalAmountAfterTaxDisc(String totalAmountAfterTaxDisc) {
        TotalAmountAfterTaxDisc = totalAmountAfterTaxDisc;
    }

    public String getNetAmmount() {
        return NetAmmount;
    }

    public void setNetAmmount(String netAmmount) {
        NetAmmount = netAmmount;
    }

    public String getDiscountPercentage() {
        return DiscountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        DiscountPercentage = discountPercentage;
    }

    public String getDiscountAmmount() {
        return DiscountAmmount;
    }

    public void setDiscountAmmount(String discountAmmount) {
        DiscountAmmount = discountAmmount;
    }

    public String getNetAmtAfterDis() {
        return NetAmtAfterDis;
    }

    public void setNetAmtAfterDis(String netAmtAfterDis) {
        NetAmtAfterDis = netAmtAfterDis;
    }

    public String getTaxPercentage() {
        return TaxPercentage;
    }

    public void setTaxPercentage(String taxPercentage) {
        TaxPercentage = taxPercentage;
    }

    public String getTaxAmmount() {
        return TaxAmmount;
    }

    public void setTaxAmmount(String taxAmmount) {
        TaxAmmount = taxAmmount;
    }

    public String getTotalAmt() {
        return TotalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        TotalAmt = totalAmt;
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

    public String getDeleted() {
        return Deleted;
    }

    public void setDeleted(String deleted) {
        Deleted = deleted;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getSizePerUnit() {
        return SizePerUnit;
    }

    public void setSizePerUnit(String sizePerUnit) {
        SizePerUnit = sizePerUnit;
    }

    public String getCurrentStock() {
        return CurrentStock;
    }

    public void setCurrentStock(String currentStock) {
        CurrentStock = currentStock;
    }
}
