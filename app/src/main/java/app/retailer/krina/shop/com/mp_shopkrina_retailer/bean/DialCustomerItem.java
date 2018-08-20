package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean;

/**
 * Created by user on 10/27/2017.
 */

public class DialCustomerItem {
    String Id;
    String CustomerId;
    String OrderId;
    String OrderAmount;
    String UsedUnused;
    String CreatedDate;
    String CustomerName;
    String ShopName;
    String Skcode;
    String BillingAddress;
    String Mobile;
    String Point;

    public DialCustomerItem(String id, String customerName, String shopName, String skcode, String mobile,String point) {
        this.Id = id;
        this.CustomerName = customerName;
        this.ShopName = shopName;
        this.Skcode = skcode;
        this.Mobile = mobile;
        this.Point = point;
    }
  /*  public DialCustomerItem(String id, String customerId, String orderId, String orderAmount, String point, String usedUnused, String createdDate, String customerName, String shopName, String skcode, String billingAddress) {
        this.Id = id;
        this.CustomerId = customerId;
        this.OrderId = orderId;
        this.OrderAmount = orderAmount;
        this.CreatedDate = createdDate;
        this.Point = point;
        this.UsedUnused = usedUnused;
        this.CustomerName = customerName;
        this.ShopName = shopName;
        this.Skcode = skcode;
        this.BillingAddress = billingAddress;

    }
 }*/

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getCustomerName() {
      return CustomerName;
  }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getSkcode() {
        return Skcode;
    }

    public void setSkcode(String skcode) {
        Skcode = skcode;
    }

    public String getBillingAddress() {
        return BillingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        BillingAddress = billingAddress;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        OrderAmount = orderAmount;
    }

    public String getPoint() {
        return Point;
    }

    public void setPoint(String point) {
        Point = point;
    }

    public String getUsedUnused() {
        return UsedUnused;
    }

    public void setUsedUnused(String usedUnused) {
        UsedUnused = usedUnused;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;

    }
    public String toString()
    {
        return Skcode;
    }
}
