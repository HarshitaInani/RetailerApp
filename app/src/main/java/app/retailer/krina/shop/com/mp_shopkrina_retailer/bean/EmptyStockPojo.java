package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean;

/**
 * Created by Krishna on 03-01-2017.
 */

public class EmptyStockPojo {

    String StockId;
    String CompanyId;
    String WarehouseId;
    String WarehouseName;
    String SellingSku;
    String ItemId;
    String ItemName;
    String ItemNumber;
    String CurrentInventory;
    String ManualReason;





    public EmptyStockPojo(String StockId,String companyId, String wareHouseId,String WarehouseName,String SellingSku, String ItemId,String name, String ItemNumber, String CurrentInventory, String ManualReason) {
        this.StockId = StockId;
        this.CompanyId = companyId;
        this.WarehouseId = wareHouseId;
        this.WarehouseName = WarehouseName;
        this.SellingSku = SellingSku;
        this.ItemId = ItemId;
        this.ItemName = name;
        this.ItemNumber = ItemNumber;
        this.CurrentInventory = CurrentInventory;
        this.ManualReason = ManualReason;

    }

    public String getStockId() {
        return StockId;
    }

    public void setStockId(String stockId) {
        StockId = stockId;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getWarehouseId() {
        return WarehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        WarehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return WarehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        WarehouseName = warehouseName;
    }

    public String getSellingSku() {
        return SellingSku;
    }

    public void setSellingSku(String sellingSku) {
        SellingSku = sellingSku;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemNumber() {
        return ItemNumber;
    }

    public void setItemNumber(String itemNumber) {
        ItemNumber = itemNumber;
    }

    public String getCurrentInventory() {
        return CurrentInventory;
    }

    public void setCurrentInventory(String currentInventory) {
        CurrentInventory = currentInventory;
    }

    public String getManualReason() {
        return ManualReason;
    }

    public void setManualReason(String manualReason) {
        ManualReason = manualReason;
    }

    public String toString()
    {
        return ItemName;
    }

}
