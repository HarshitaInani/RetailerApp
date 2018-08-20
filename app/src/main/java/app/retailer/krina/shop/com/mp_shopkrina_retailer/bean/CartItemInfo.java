package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.ItemList;

/**
 * Created by Krishna on 20-01-2017.
 */

public class CartItemInfo {


    String itemId;
    int qty;
    double itemUnitPrice;
    double itemPrice;

    double itemDpPoint;


    ItemList selectedItem;

    String companyId;
    String warehouseId;


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public CartItemInfo(String itemId, int qty, double itemUnitPrice, ItemList selectedItem, double itemDpPoint,double itemPrice, String warehouseId, String companyId) {
        this.itemId = itemId;
        this.qty = qty;
        this.itemUnitPrice = itemUnitPrice;
        this.itemDpPoint = itemDpPoint;
        this.selectedItem = selectedItem;

        this.itemPrice = itemPrice;

        this.warehouseId = warehouseId;
        this.companyId = companyId;


    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getItemDpPoint() {
        return itemDpPoint;
    }

    public void setItemDpPoint(double itemDpPoint) {
        this.itemDpPoint = itemDpPoint;
    }



    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getItemUnitPrice() {
        return itemUnitPrice;
    }

    public void setItemUnitPrice(double itemUnitPrice) {
        this.itemUnitPrice = itemUnitPrice;
    }

    public ItemList getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(ItemList selectedItem) {
        this.selectedItem = selectedItem;
    }
}
