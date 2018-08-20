package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean;

import java.util.ArrayList;

/**
 * Created by Krishna on 26-01-2017.
 */

public class MyOderBean {
    String OrderId;
    String CompanyId;
    String SalesPersonId;
    String SalesPerson;
    String CustomerId;
    String CustomerName;
    String Skcode;
    String ShopName;
    String Status;
    String invoice_no;
    String CustomerCategoryId;
    String CustomerCategoryName;
    String CustomerType;
    String Customerphonenum;
    String BillingAddress;
    String ShippingAddress;
    String TotalAmount;
    String GrossAmount;
    String DiscountAmount;
    String TaxAmount;
    String CityId;
    String Warehouseid;
    String WarehouseName;
    String active;
    String CreatedDate;
    String Deliverydate;
    String UpdatedDate;
    String Deleted;
    String ReDispatchCount;
    String DivisionId;
    String ReasonCancle;
    String Cluster;
    String deliveryCharge;
    ArrayList<MyOrderDetailsBean> myOrderDetailsBeanArrayList;

    public MyOderBean(String orderId, String companyId, String salesPersonId, String salesPerson, String customerId, String customerName, String skcode, String shopName, String status, String invoice_no, String customerCategoryId, String customerCategoryName, String customerType, String customerphonenum, String billingAddress, String shippingAddress, String totalAmount, String grossAmount, String discountAmount, String taxAmount, String cityId, String warehouseid, String warehouseName, String active, String createdDate, String deliverydate, String updatedDate, String deleted, String reDispatchCount, String divisionId, String reasonCancle, String cluster, String deliveryCharge, ArrayList<MyOrderDetailsBean> myOrderDetailsBeanArrayList) {
        OrderId = orderId;
        CompanyId = companyId;
        SalesPersonId = salesPersonId;
        SalesPerson = salesPerson;
        CustomerId = customerId;
        CustomerName = customerName;
        Skcode = skcode;
        ShopName = shopName;
        Status = status;
        this.invoice_no = invoice_no;
        CustomerCategoryId = customerCategoryId;
        CustomerCategoryName = customerCategoryName;
        CustomerType = customerType;
        Customerphonenum = customerphonenum;
        BillingAddress = billingAddress;
        ShippingAddress = shippingAddress;
        TotalAmount = totalAmount;
        GrossAmount = grossAmount;
        DiscountAmount = discountAmount;
        TaxAmount = taxAmount;
        CityId = cityId;
        Warehouseid = warehouseid;
        WarehouseName = warehouseName;
        this.active = active;
        CreatedDate = createdDate;
        Deliverydate = deliverydate;
        UpdatedDate = updatedDate;
        Deleted = deleted;
        ReDispatchCount = reDispatchCount;
        DivisionId = divisionId;
        ReasonCancle = reasonCancle;
        Cluster = cluster;
        this.deliveryCharge = deliveryCharge;
        this.myOrderDetailsBeanArrayList = myOrderDetailsBeanArrayList;
    }

    public String getCustomerCategoryId() {
        return CustomerCategoryId;
    }

    public void setCustomerCategoryId(String customerCategoryId) {
        CustomerCategoryId = customerCategoryId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getSalesPersonId() {
        return SalesPersonId;
    }

    public void setSalesPersonId(String salesPersonId) {
        SalesPersonId = salesPersonId;
    }

    public String getSalesPerson() {
        return SalesPerson;
    }

    public void setSalesPerson(String salesPerson) {
        SalesPerson = salesPerson;
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

    public String getSkcode() {
        return Skcode;
    }

    public void setSkcode(String skcode) {
        Skcode = skcode;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public String getCustomerCategoryName() {
        return CustomerCategoryName;
    }

    public void setCustomerCategoryName(String customerCategoryName) {
        CustomerCategoryName = customerCategoryName;
    }

    public String getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(String customerType) {
        CustomerType = customerType;
    }

    public String getCustomerphonenum() {
        return Customerphonenum;
    }

    public void setCustomerphonenum(String customerphonenum) {
        Customerphonenum = customerphonenum;
    }

    public String getBillingAddress() {
        return BillingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        BillingAddress = billingAddress;
    }

    public String getShippingAddress() {
        return ShippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        ShippingAddress = shippingAddress;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getGrossAmount() {
        return GrossAmount;
    }

    public void setGrossAmount(String grossAmount) {
        GrossAmount = grossAmount;
    }

    public String getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        DiscountAmount = discountAmount;
    }

    public String getTaxAmount() {
        return TaxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        TaxAmount = taxAmount;
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

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getDeliverydate() {
        return Deliverydate;
    }

    public void setDeliverydate(String deliverydate) {
        Deliverydate = deliverydate;
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

    public String getReDispatchCount() {
        return ReDispatchCount;
    }

    public void setReDispatchCount(String reDispatchCount) {
        ReDispatchCount = reDispatchCount;
    }

    public String getDivisionId() {
        return DivisionId;
    }

    public void setDivisionId(String divisionId) {
        DivisionId = divisionId;
    }

    public String getReasonCancle() {
        return ReasonCancle;
    }

    public void setReasonCancle(String reasonCancle) {
        ReasonCancle = reasonCancle;
    }

    public String getCluster() {
        return Cluster;
    }

    public void setCluster(String cluster) {
        Cluster = cluster;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public ArrayList<MyOrderDetailsBean> getMyOrderDetailsBeanArrayList() {
        return myOrderDetailsBeanArrayList;
    }

    public void setMyOrderDetailsBeanArrayList(ArrayList<MyOrderDetailsBean> myOrderDetailsBeanArrayList) {
        this.myOrderDetailsBeanArrayList = myOrderDetailsBeanArrayList;
    }
}
