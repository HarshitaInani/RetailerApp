package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean;

import android.graphics.Bitmap;

/**
 * Created by User on 07-03-2018.
 */

public class Activitiespojo {
    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    Bitmap image;
    String DocumentName;

    public boolean isHaveImage() {
        return haveImage;
    }

    public void setHaveImage(boolean haveImage) {
        this.haveImage = haveImage;
    }

    boolean haveImage;

    public int getListItemPosition() {
        return listItemPosition;
    }

    public void setListItemPosition(int listItemPosition) {
        this.listItemPosition = listItemPosition;
    }

    int listItemPosition;


    public Activitiespojo(String documentName ) {

        this.DocumentName = documentName;

    }


    public String getDocumentName() {
        return DocumentName;
    }

    public void setDocumentName(String documentName) {
        DocumentName = documentName;
    }


}
