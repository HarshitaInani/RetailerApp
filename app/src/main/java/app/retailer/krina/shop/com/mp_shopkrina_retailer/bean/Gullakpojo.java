package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean;

import android.util.Log;

/**
 * Created by User on 13-07-2018.
 */

public class Gullakpojo {
   String id;
 String CustomerId;
   String SkCode;
   String AmountIn;
String AmountOut;
String GullakAmount;
String GullakRequestStatus;
String CreatedDate;
    String CreatedUserId;
String CreatedBy;
String ApprovedUserId;
String ApprovedBy;
    String IsActive;
    String IsDeleted;
    String OderId_GullakRequestid;


    public Gullakpojo( String amountin,String amountout, String gullakamount,String status,String date) {
        this.AmountIn = amountin;
        this.AmountOut=amountout;
        this.GullakAmount=gullakamount;
        this. GullakRequestStatus=status;
        this.CreatedDate=date;


        Log.d("amountin",AmountIn);
    }
    public Gullakpojo( String gullakamount) {

        this.GullakAmount=gullakamount;
        Log.d("gullakamount+++++",GullakAmount);
    }
    public Gullakpojo(String gullakamount,String skcode){
    this.GullakAmount=gullakamount;
    this.SkCode=skcode;
}



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;

    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getSkCode() {
        return SkCode;
    }

    public void setSkCode(String skCode) {
        SkCode = skCode;
    }

    public String getAmountIn() {
        return AmountIn;
    }

    public void setAmountIn(String amountIn) {
        AmountIn = amountIn;
    }

    public String getAmountOut() {
        return AmountOut;
    }

    public void setAmountOut(String amountOut) {
        AmountOut = amountOut;
    }

    public String getGullakAmount() {
        return GullakAmount;
    }

    public void setGullakAmount(String gullakAmount) {
        GullakAmount = gullakAmount;
    }

    public String getGullakRequestStatus() {
        return GullakRequestStatus;
    }

    public void setGullakRequestStatus(String gullakRequestStatus) {
        GullakRequestStatus = gullakRequestStatus;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getCreatedUserId() {
        return CreatedUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        CreatedUserId = createdUserId;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getApprovedUserId() {
        return ApprovedUserId;
    }

    public void setApprovedUserId(String approvedUserId) {
        ApprovedUserId = approvedUserId;
    }

    public String getApprovedBy() {
        return ApprovedBy;
    }

    public void setApprovedBy(String approvedBy) {
        ApprovedBy = approvedBy;
    }

    public String isActive() {
        return IsActive;
    }

    public void setActive(String active) {
        IsActive = active;
    }

    public String isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(String deleted) {
        IsDeleted = deleted;
    }

    public String getOderId_GullakRequestid() {
        return OderId_GullakRequestid;
    }

    public void setOderId_GullakRequestid(String oderId_GullakRequestid) {
        OderId_GullakRequestid = oderId_GullakRequestid;
    }

    @Override
    public String toString() {
        return id;
    }
}
