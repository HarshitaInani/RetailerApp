package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.ScanBarCodeAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.SubsubBrandsAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.CartItemBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.CartItemInfo;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.ItemList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment.SearchFragItemList;

public class BarcodeScanItem extends AppCompatActivity {
    int selectedCategoryId = -1;
    int selectedWarId = -1;

    TextView tvTotalItemPrice;
    TextView tvTotalItemQty;
    TextView tvTotalDp;
    int rowitemImageHeight=77;
    int rowitemImageWidth=77;
    private static final String TAG = "RecyclerViewExample";
    private ArrayList<ItemList> feedsList;
    private RecyclerView mRecyclerView;
    private ScanBarCodeAdapter mScanBarCodeAdapter;

    int mp = 0, pp = 0;

    int xPoints = 2;    String locale;

    String hindiLanguage = " हिन्दी (भारत)";

    SharedPreferences sharedpreferences;

    String languageCheck = "e";

    LinearLayoutManager mLinearLayoutManager;

    Dialog mDialog;
    AnimationDrawable animation;
    CartItemBean mCartItem;
    TextView show_popup;
    ImageView image_ads;
    int sliderIMageHeight;
    int sliderIMageWidth;
    ArrayList<ItemList> mItemListAllValue= new ArrayList<>();
    ArrayList<ItemList> mItemListArrayList = new ArrayList<>();
    AlertDialog mAlertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code_item);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setImagesDynamicSize();
        ImageView home=(ImageView)findViewById(R.id.home_icon_iv) ;
        image_ads=(ImageView)findViewById(R.id.image_ads) ;
        show_popup=(TextView)findViewById(R.id.show_popup) ;
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new_added Intent(SubsubBrands.this,HomeActivity.class));
                finish();
            }
        });
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        tvTotalItemPrice=(TextView)findViewById(R.id.item_list_total_amount_tv) ;
        tvTotalDp=(TextView)findViewById(R.id.item_list_total_dp_tv) ;
        tvTotalItemQty=(TextView)findViewById(R.id.item_list_total_item_tv) ;
        RelativeLayout rl1=(RelativeLayout)findViewById(R.id.rl1);

         rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send=new Intent(BarcodeScanItem.this,CartActivity.class);
                startActivity(send);

            }
        });
        if (savedInstanceState != null) {
            Log.e("Stage", "1");

        }else
        {

            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setOrientationLocked(false);
            integrator.initiateScan();
        }

        Log.e("Stage", "2");
    }





    public void callAqueryForSearchBarCode(String item) {


        showLoading();

        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        final RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);

        String CustomerId=mRetailerBean.getCustomerId();
        String url;

        url = Constant.BASE_URL_ITEM_SEARCH + "/GetItemByBarcode?Barcode="+item ;



        System.out.println(url);
        new AQuery(this).ajax(url, null, String.class, new AjaxCallback<String>(){


            @Override
            public void callback(String url, String jsonArrayFromurl, AjaxStatus status) {
                Log.e("onResponse  Barcode", jsonArrayFromurl.toString());

                if(jsonArrayFromurl.toString().equalsIgnoreCase("null"))
                {
                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }
                    Toast.makeText(BarcodeScanItem.this, "Items are not available", Toast.LENGTH_SHORT).show();
                }else if (jsonArrayFromurl != null) {


                    //  Toast.makeText(getActivity(), ""+jsonArrayFromurl.toString(), Toast.LENGTH_SHORT).show();
                    try{
                        mItemListArrayList.clear();
                        mItemListAllValue.clear();
                        JSONObject jsonObject =new JSONObject(jsonArrayFromurl);
                        if (jsonArrayFromurl.length() > 0) {

                            String ItemId = isNullOrEmpty(jsonObject, "ItemId");
                            String UnitId = isNullOrEmpty(jsonObject, "UnitId");
                            String Categoryid = isNullOrEmpty(jsonObject, "Categoryid");
                            String SubCategoryId = isNullOrEmpty(jsonObject, "SubCategoryId");
                            String SubsubCategoryid = isNullOrEmpty(jsonObject, "SubsubCategoryid");
                            String itemname = isNullOrEmpty(jsonObject, "itemname");
                            String UnitName = isNullOrEmpty(jsonObject, "UnitName");
                            String PurchaseUnitName = isNullOrEmpty(jsonObject, "PurchaseUnitName");
                            String price = isNullOrEmpty(jsonObject, "price");
                            String SellingUnitName = isNullOrEmpty(jsonObject, "SellingUnitName");
                            String SellingSku = isNullOrEmpty(jsonObject, "SellingSku");
                            String UnitPrice = isNullOrEmpty(jsonObject, "UnitPrice");
                            String VATTax = isNullOrEmpty(jsonObject, "VATTax");
                            String LogoUrl = isNullOrEmpty(jsonObject, "LogoUrl");
                            String MinOrderQty = isNullOrEmpty(jsonObject, "MinOrderQty");
                            String Discount = isNullOrEmpty(jsonObject, "Discount");
                            String TotalTaxPercentage = isNullOrEmpty(jsonObject, "TotalTaxPercentage");
                            String itemNumber = isNullOrEmpty(jsonObject, "Number");
                            String HindiName = isNullOrEmpty(jsonObject, "HindiName");
                            String DpPoint = "0";

//                            String PromoPoint = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "promoPoint");
                            String PromoPoint = isNullOrEmpty(jsonObject, "promoPerItems");

//                            String PromoPoint = "0";



                            String MarginPoint = isNullOrEmpty(jsonObject, "marginPoint");

//                            String MarginPoint = "0";



                            String warehouseId = isNullOrEmpty(jsonObject, "WarehouseId");
                            String companyId = isNullOrEmpty(jsonObject, "CompanyId");
                            String isoffer = isNullOrEmpty(jsonObject, "Isoffer");


                            if(PromoPoint.trim().equals("null")) {
                                pp = 0;

                            }


                            if(PromoPoint.isEmpty()) {
                                pp = 0;

                            }


                            else if(PromoPoint.trim().equals("null")) {
                                pp = 0;

                            }

                            else {
                                pp = Integer.parseInt(PromoPoint);

                            }


                            if(MarginPoint.trim().equals("null")) {

                                mp = 0;
                            }


                            if(MarginPoint.isEmpty()) {

                                mp = 0;
                            }

                            else if(MarginPoint.trim().equals("null")) {

                                mp = 0;
                            }

                            else {
                                mp = Integer.parseInt(MarginPoint)  * xPoints;

                            }

                            if(pp > 0 && mp > 0) {
                                int pp_mp = pp + mp;

                                DpPoint = ""+pp_mp;
                            }
                            else if (mp > 0) {
                                DpPoint = ""+mp;
                            }
                            else if (pp > 0) {
                                DpPoint = ""+pp;
                            }

                            else {
                                DpPoint = "0";
                            }



                            mItemListAllValue.add(new ItemList(ItemId, UnitId, Categoryid, SubCategoryId, SubsubCategoryid, itemname, UnitName, PurchaseUnitName, price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, warehouseId, companyId,itemNumber,isoffer));

                            if(mItemListArrayList.size()==0)
                            {
                                mItemListArrayList.add(new ItemList(ItemId, UnitId, Categoryid, SubCategoryId, SubsubCategoryid, itemname, UnitName, PurchaseUnitName, price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, warehouseId, companyId,itemNumber,isoffer));
                            }else
                            {
                                boolean ispresent=false;
                                for (int j = 0; j <mItemListArrayList.size() ; j++) {
                                    //   Log.d("click1","ItemNumber1122::"+mItemListArrayList.get(j).getItemNumber());
                                    if(mItemListArrayList.get(j).getItemNumber().equalsIgnoreCase(itemNumber))
                                    {
                                        ispresent=true;
                                        break;
                                    }
                                }
                                if(!ispresent)
                                {
                                    // System.out.println("Id:1::"+ItemId);
                                    mItemListArrayList.add(new ItemList(ItemId, UnitId, Categoryid, SubCategoryId, SubsubCategoryid, itemname, UnitName, PurchaseUnitName, price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, warehouseId, companyId,itemNumber,isoffer));                                        //Log.d("click1","list show::"+mItemListArrayList);
                                    // values.add(itemname);
                                }else
                                {
                                    //  System.out.println("Id:2::"+ItemId);
                                    //values.add(itemname);
                                    // mItemListAllValue.add(new_added ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint,ItemNumber));
                                    // Log.d("click1","list Remove::"+values);
                                }
                            }







                            if (!mItemListArrayList.isEmpty()) {
                                // mSearchFragAdapter.
                                mScanBarCodeAdapter = new ScanBarCodeAdapter(BarcodeScanItem.this, mItemListArrayList,rowitemImageWidth, rowitemImageHeight, tvTotalItemPrice, tvTotalItemQty, tvTotalDp,show_popup,mItemListAllValue);

                                mRecyclerView.setAdapter(mScanBarCodeAdapter);
                                mScanBarCodeAdapter.notifyDataSetChanged();





                            } else {


                                Toast.makeText(BarcodeScanItem.this, "Items are not available", Toast.LENGTH_SHORT).show();
                                getFragmentManager().popBackStack();
                            }



                            Collections.sort(mItemListAllValue, new ComparatorOfNumericString());
                        }








                    } catch (Exception e) {




                    }
                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }


                }

                else {



                    Toast.makeText(BarcodeScanItem.this, "Item not found!", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    public CartItemBean getCartItem() {
        if (mCartItem == null) {
            ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(BarcodeScanItem.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0,0,0,"","");
            }
        }
        return mCartItem;
    }

    public String addItemInCartItemArrayList(String itemId, int qty, double itemUnitPrice, ItemList selectedItem, double deliveryCharges, double totalDp, String wid, String coid,double Price) {



        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(BarcodeScanItem.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        if (mCartItem == null) {
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0,0,0,"","");
            }
        }
        String status = "Error";
        double tempTotalPrice = 0;
        double TotalPrice = 0;
        double tempTotalQuantity = 0;
        double tempTotalDpPoint = 0;
        double saveAmount = 0;





        if (mCartItem.getmCartItemInfos() != null && !mCartItem.getmCartItemInfos().isEmpty()) {
            boolean itemFound = false;
            int foundItemPosition = -1;
            for (int i = 0; i < mCartItem.getmCartItemInfos().size(); i++) {
                if (mCartItem.getmCartItemInfos().get(i).getItemId().equalsIgnoreCase(itemId)) {
                    itemFound = true;
                    foundItemPosition = i;
                    break;
                } else {
                    itemFound = false;
                }
            }
            if (itemFound && foundItemPosition != -1) {
                mCartItem.getmCartItemInfos().get(foundItemPosition).setQty(qty);
                mCartItem.getmCartItemInfos().get(foundItemPosition).setItemUnitPrice(itemUnitPrice);
                mCartItem.getmCartItemInfos().get(foundItemPosition).setItemPrice(Price);
                status = "Item Updated in Cart";




                //mCartItem.getmCartItemInfos().remove(foundItemPosition);


            } else {
                mCartItem.getmCartItemInfos().add(new CartItemInfo(itemId, qty, itemUnitPrice, selectedItem, totalDp,Price,"",""));
                status = "Item Added in Cart";
            }
            for (int i = 0; i < mCartItem.getmCartItemInfos().size(); i++) {
                tempTotalPrice += mCartItem.getmCartItemInfos().get(i).getQty() * mCartItem.getmCartItemInfos().get(i).getItemUnitPrice();

                TotalPrice += mCartItem.getmCartItemInfos().get(i).getQty() * mCartItem.getmCartItemInfos().get(i).getItemPrice();

                tempTotalDpPoint += mCartItem.getmCartItemInfos().get(i).getQty() * mCartItem.getmCartItemInfos().get(i).getItemDpPoint();

                tempTotalQuantity += mCartItem.getmCartItemInfos().get(i).getQty();
            }
            mCartItem.setDeliveryCharges(deliveryCharges);
        } else {
            mCartItem.getmCartItemInfos().add(new CartItemInfo(itemId, qty, itemUnitPrice, selectedItem, totalDp,Price,"",""));
            status = "Item Added in Cart";



            tempTotalPrice += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemUnitPrice();

            TotalPrice += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemPrice();

            tempTotalDpPoint += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemDpPoint();

            tempTotalQuantity += mCartItem.getmCartItemInfos().get(0).getQty();

            mCartItem.setDeliveryCharges(deliveryCharges);
        }
        System.out.println("tempTotalPrice:::"+tempTotalPrice);
        System.out.println("TotalPrice:::"+TotalPrice);
        saveAmount =TotalPrice-tempTotalPrice;
        System.out.println("SaveAmount:::"+saveAmount);

        mCartItem.setTotalItemPrice(TotalPrice);
        mCartItem.setSavingAmount(saveAmount);
        mCartItem.setTotalPrice(tempTotalPrice);
        mCartItem.setTotalQuantity(tempTotalQuantity);
        mCartItem.setTotalDpPoints(tempTotalDpPoint);

        mCartItemArraylistPref.putObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, mCartItem);
        mCartItemArraylistPref.commit();


        //  mCartItemArraylistPref.
        return status;


    }


    private void setImagesDynamicSize() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
       this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        if (displaymetrics.widthPixels >= 480 && displaymetrics.widthPixels < 720) {
            rowitemImageHeight = 77;
            rowitemImageWidth = 77;
            sliderIMageHeight = 219;
            sliderIMageWidth = displaymetrics.widthPixels;

        } else if (displaymetrics.widthPixels >= 720 && displaymetrics.widthPixels < 1080) {
            rowitemImageHeight = 115;
            rowitemImageWidth = 115;

            sliderIMageHeight = 348;
            sliderIMageWidth = displaymetrics.widthPixels;
        } else if (displaymetrics.widthPixels >= 1080 && displaymetrics.widthPixels < 1440) {
            rowitemImageHeight = 173;
            rowitemImageWidth = 173;

            sliderIMageHeight = 492;
            sliderIMageWidth = displaymetrics.widthPixels;
        } else if (displaymetrics.widthPixels >= 1440) {
            rowitemImageHeight = 230;
            rowitemImageWidth = 230;

            sliderIMageHeight = 656;
            sliderIMageWidth = displaymetrics.widthPixels;
        } else {
            rowitemImageHeight = 173;
            rowitemImageWidth = 173;

            sliderIMageHeight = 328;
            sliderIMageWidth = displaymetrics.widthPixels;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // startActivity(new_added Intent(this,Allbrands.class));
        finish();
    }

    public class ComparatorOfNumericString implements Comparator<ItemList> {
        @SuppressLint("NewApi")
        @Override
        public int compare(ItemList lhs, ItemList rhs) {
            int i1 = Integer.parseInt(lhs.getMinOrderQty());
            int i2 = Integer.parseInt(rhs.getMinOrderQty());
            Log.d("Collection String::", String.valueOf(Integer.compare(i1, i2)));
            return Integer.compare(i1, i2);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("QRCodeScanActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                finish();
            } else {


                Log.d("QRCodeScanActivity", "Scanned");



                String   Scan=result.getContents();
               //  Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

                if (Utils.isInternetConnected(BarcodeScanItem.this)) {

                    callAqueryForSearchBarCode(Scan);



                } else {
                    if (mAlertDialog != null && mAlertDialog.isShowing())
                        mAlertDialog.dismiss();
                    mAlertDialog = new AlertDialog.Builder(BarcodeScanItem.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Error!")
                            .setCancelable(true)
                            .setMessage("Internet connection is not available")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                }

                // scanResults.setText(Scan);
            }
        } else {
            Log.e("Stage", "3");
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void showLoading() {


        mDialog = new Dialog(BarcodeScanItem.this);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.progress_dialog);
        ((TextView) mDialog.findViewById(R.id.progressText)).setText("Logging in please wait...");
        ImageView la = (ImageView) mDialog.findViewById(R.id.gridprogressBar);
        la.setBackgroundResource(R.drawable.custom_progress_dialog_animation);
        animation = (AnimationDrawable) la.getBackground();
        animation.start();
        mDialog.setCancelable(false);
        mDialog.show();

    }
    private String isNullOrEmpty(JSONObject mJsonObject, String key) throws JSONException {
        try {
            if (mJsonObject.has(key)) {
                if (TextUtils.isNullOrEmpty(mJsonObject.getString(key))) {
                    return "";
                } else {
                    return mJsonObject.getString(key);
                }
            } else {
                Log.e("HomeFragItemListFrag", key + " is not available which should come in response");
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public boolean showPopup(int totalAmount) {

        if(totalAmount >= 3000 && totalAmount <= 4000) {
            return true;
        }else if(totalAmount >= 7000 & totalAmount <= 8000)
        {
            return true;
        }else if(totalAmount >= 11000 & totalAmount <= 12000)
        {
            return true;
        }else if(totalAmount >= 15000 & totalAmount <= 16000)
        {
            return true;
        }else if(totalAmount >= 19000 & totalAmount <= 20000)
        {
            return true;
        }else if(totalAmount >= 23000 & totalAmount <= 24000)
        {
            return true;
        }else if(totalAmount >= 27000 & totalAmount <= 28000)
        {
            return true;
        }else if(totalAmount >= 31000 & totalAmount <= 32000)
        {
            return true;
        }else if(totalAmount >= 35000 & totalAmount <= 36000)
        {
            return true;
        }else if(totalAmount >= 39000 & totalAmount <= 40000)
        {
            return true;
        }else if(totalAmount >= 43000 & totalAmount <= 44000)
        {
            return true;
        }else if(totalAmount >= 47000 & totalAmount <= 48000)
        {
            return true;
        }else{
            return false;
        }

        //return true;
    }

}
