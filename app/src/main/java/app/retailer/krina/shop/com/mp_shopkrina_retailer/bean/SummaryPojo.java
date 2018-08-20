package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean;

/**
 * Created by User on 9/12/2017.
 */

public class SummaryPojo {

    private String id, name, createdDate,TotalAmount,status,Comment,day,month,year,TotalOrder;

    public SummaryPojo(String id, String name, String createdDate, String TotalAmount, String status, String Comment, String day, String month, String year, String  TotalOrder) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.TotalAmount = TotalAmount;
        this.status = status;
        this.Comment = Comment;
        this.day = day;
         this.month=month;
        this.year = year;
        this.TotalOrder = TotalOrder;




    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public String getTotalOrder() {
        return year;
    }

    public void setTotalOrder(String order) {
        this.TotalOrder = order;
    }
}