package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.FavAdapter2;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.CartItemBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.CartItemInfo;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.ItemList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;

public class MyFavourite extends AppCompatActivity {



    Context context;
    JSONObject jsonFav;


    int rowitemImageHeight=77;
    int rowitemImageWidth=77;


    JSONArray jsonArray = new JSONArray();

    JSONObject jsonObjectMain = new JSONObject();



    ArrayList<ItemList> mItemListArrayList = new ArrayList<>();


    FavAdapter2 mSearchFragAdapter;

    RecyclerView mItemListRecyclerView;

    int mp = 0, pp = 0;


    AlertDialog mAlertDialog;
    ProgressBar mProgressBar;



    CartItemBean mCartItem;


    TextView tvTotalItemPrice;
    TextView tvTotalItemQty;


    TextView tvTotalDp;



    Dialog mDialog;
    AnimationDrawable animation;

    int Xpoints = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favourite);



        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        context = this;
        String json = Utility.getStringSharedPreferences(context, "ItemFavJson");

       // setImagesDynamicSize();


        ((TextView) findViewById(R.id.title_toolbar)).setText("My Favorite");


        Toolbar toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
        setSupportActionBar(toolbar);


        ((RelativeLayout) findViewById(R.id.rl1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyFavourite.this, CartActivity.class));
            }
        });

        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFavourite.this.finish();
            }
        });





        mItemListRecyclerView = (RecyclerView) findViewById(R.id.frag_search_rv);

        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(MyFavourite.this, LinearLayoutManager.VERTICAL, false);
        mItemListRecyclerView.setLayoutManager(layoutManager2);



        tvTotalItemPrice = (TextView) findViewById(R.id.frag_search_total_amount_tv);
        tvTotalItemQty = (TextView) findViewById(R.id.frag_search_total_item_tv);




        tvTotalDp = (TextView) findViewById(R.id.frag_search_total_dp_tv);



        tvTotalItemPrice.setText("Total: " + new DecimalFormat("##.##").format(getCartItem().getTotalPrice()));
        tvTotalItemQty.setText("" + (int) getCartItem().getTotalQuantity());


        tvTotalDp.setText("Dp : "+ new DecimalFormat("##.##").format(getCartItem().getTotalDpPoints()));


        showLoading();


        //MyF cartActivityBinding = DataBindingUtil.setContentView(this, R.layout.cart_activity);



      /*  mProgressBar = searchFragBinding.searchFragSearchBar;
        mProgressBar.setVisibility(View.INVISIBLE);
        mItemListRecyclerView = searchFragBinding.fragSearchRv;
        LinearLayoutManager layoutManager2
                = new_added LinearLayoutManager(MyFavourite.this, LinearLayoutManager.VERTICAL, false);
        mItemListRecyclerView.setLayoutManager(layoutManager2);

        mSearchFragAdapter = new_added SearchFragListAdapter(MyFavourite.this,

                mItemListArrayList,
                rowitemImageWidth,
                rowitemImageHeight,
                tvTotalItemPrice,
                tvTotalItemQty ,
                tvTotalDp


        );


        mItemListRecyclerView.setAdapter(mSearchFragAdapter);
*/



        try {

            JSONObject jsonObject = new JSONObject(json.toString());
            for (int i =0; i < jsonObject.length(); i++) {

             //   Toast.makeText(context, "", Toast.LENGTH_SHORT).show();

                jsonObject.names().get(i);

               if( jsonObject.get(jsonObject.names().get(i).toString()).equals("1")) {

               //    jsonArray.put(  jsonObject.get(jsonObject.names().get(i).toString())  );


                 //  jsonObject.remove()
               }





              //  Toast.makeText(context, ""+jsonObject.names().get(i), Toast.LENGTH_SHORT).show();

                jsonFav = new JSONObject();



            }


         //   Toast.makeText(context, "Array : "+jsonArray.toString(), Toast.LENGTH_SHORT).show();

            /*Set keys = jsonObject.keys();
            Iterator a = keys.iterator();
            while(a.hasNext()) {
                String key = (String)a.next();
                // loop to get the dynamic key
                String value = (String)jsonObject.get(key);
                System.out.print("key : "+key);
                System.out.println(" value :"+value);
            }
*/



            Iterator<?> keys = jsonObject.keys();

            while( keys.hasNext() ) {
                String key = (String) keys.next();
                System.out.println("Key: " + key);
                System.out.println("Value: " + jsonObject.get(key));


              //  Toast.makeText(context, key+ "key \nvalue "+  jsonObject.get(key), Toast.LENGTH_SHORT).show();

                if (jsonObject.get(key).equals("1")) {
                    JSONObject jsonObject1 = new JSONObject();

                    jsonObject1.put("ItemId", Integer.parseInt(key) );
                    jsonArray.put(jsonObject1);
                }

            }


            // Toast.makeText(context, "Array : "+jsonArray.toString(), Toast.LENGTH_SHORT).show();

            jsonObjectMain.put("items", jsonArray);

          //  ((TextView) findViewById(R.id.txt)).setText(jsonObjectMain.toString());


            Log.e("fav", jsonObjectMain.toString());


            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
            }


            callMyfavApi();

        } catch (JSONException e) {
            e.printStackTrace();

            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
            }

        }


       // callMyfavApi();
    }


    public void callMyfavApi() {

        JSONArray jsonArray1 = new JSONArray();

        showLoading();


        //http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/Customers/favourite

        new AQuery(MyFavourite.this).post(Constant.BASE_URL_MY_FAV,
                jsonObjectMain
                , JSONArray.class,
                new AjaxCallback<JSONArray>(){


                    @Override
                    public void callback(String url, JSONArray jsonArrayFromurl, AjaxStatus status) {

                        System.out.println("Response:::"+jsonArrayFromurl);
                        if (jsonArrayFromurl != null) {
                         //   Toast.makeText(MyFavourite.this, ""+jsonArrayFromurl.toString(), Toast.LENGTH_SHORT).show();


                            if (mDialog.isShowing()) {
                                animation.stop();
                                mDialog.dismiss();
                            }

                            try{



                                mItemListArrayList.clear();
                                if (jsonArrayFromurl.length() > 0) {
                                    for (int i = 0; i < jsonArrayFromurl.length(); i++) {
                                        String ItemId = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "ItemId");
                                        String UnitId = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "UnitId");
                                        String Categoryid = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "Categoryid");
                                        String SubCategoryId = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "SubCategoryId");
                                        String SubsubCategoryid = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "SubsubCategoryid");
                                        String itemname = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "itemname");
                                        String itemNumber = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "ItemNumber");
                                        String UnitName = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "UnitName");
                                        String PurchaseUnitName = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "PurchaseUnitName");
                                        String price = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "price");
                                        String SellingUnitName = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "SellingUnitName");
                                        String SellingSku = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "SellingSku");
                                        String UnitPrice = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "UnitPrice");
                                        String VATTax = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "VATTax");
                                        String LogoUrl = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "LogoUrl");
                                        String MinOrderQty = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "MinOrderQty");
                                        String Discount = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "Discount");
                                        String TotalTaxPercentage = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "TotalTaxPercentage");

                                        String HindiName = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "HindiName");



                                        String DpPoint = "0";

//                            String PromoPoint = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "promoPoint");
                                        String PromoPoint = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "promoPerItems");

//                            String PromoPoint = "0";



                                        String MarginPoint = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "marginPoint");


//                            String MarginPoint = "0";


                                        String warehouseId = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "WarehouseId");
                                        String companyId = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "CompanyId");
                                        String Isoffer = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "Isoffer");


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
                                            pp = Integer.parseInt(PromoPoint) * Xpoints;

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
                                            mp = Integer.parseInt(MarginPoint) * Xpoints;

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

                                        //mItemListArrayList.get(i).



                                        mItemListArrayList.add(new ItemList(ItemId, UnitId, Categoryid, SubCategoryId, SubsubCategoryid, itemname, UnitName, PurchaseUnitName, price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, warehouseId, companyId,itemNumber,Isoffer));
                                        //    publishProgress();




                                        if (!mItemListArrayList.isEmpty()) {
                                            // mSearchFragAdapter.
                                           // mSearchFragAdapter.notifyDataSetChanged();


                                        //    Toast.makeText(MyFavourite.this, "list have data", Toast.LENGTH_SHORT).show();

                                            mSearchFragAdapter = new FavAdapter2(MyFavourite.this,mItemListArrayList, tvTotalItemPrice, tvTotalItemQty, tvTotalDp , rowitemImageWidth, rowitemImageHeight);
                                            mItemListRecyclerView.setAdapter(mSearchFragAdapter);


                                            if (mDialog.isShowing()) {
                                                animation.stop();
                                                mDialog.dismiss();
                                            }

                                            //   mProgressBar.setVisibility(View.INVISIBLE);


                                            //   mSearchFragAdapter.notifyItemRangeChanged(0,2);

                     /*   mSearchFragAdapter.notifyItemChanged(0);
                        mSearchFragAdapter.notifyItemChanged(1);
                        mSearchFragAdapter.notifyItemChanged(2);
                        mSearchFragAdapter.notifyItemChanged(3);
                        mSearchFragAdapter.notifyItemChanged(4);
                     */
                        /* = new_added SearchFragListAdapter(getActivity(), mItemListArrayList, rowitemImageWidth, rowitemImageHeight, tvTotalItemPrice, tvTotalItemQty);
                        mItemListRecyclerView.setAdapter(mSearchFragAdapter);*/



                                        } else {

                                            if (mDialog.isShowing()) {
                                                animation.stop();
                                                mDialog.dismiss();
                                            }

                                            //   mProgressBar.setVisibility(View.INVISIBLE);

                                            Toast.makeText(MyFavourite.this, "Items are not available", Toast.LENGTH_SHORT).show();
                                            getFragmentManager().popBackStack();
                                        }






                                    }
                                }








                            } catch (Exception e) {

                                if (mDialog.isShowing()) {
                                    animation.stop();
                                    mDialog.dismiss();
                                }


                                Toast.makeText(MyFavourite.this, "Json exception "+e.toString(), Toast.LENGTH_SHORT).show();


//                                mProgressBar.setVisibility(View.INVISIBLE);


                            //    Toast.makeText(MyFavourite.this, "Json exception "+e.toString(), Toast.LENGTH_SHORT).show();


                            }










                        }

                        else {

                            if (mDialog.isShowing()) {
                                animation.stop();
                                mDialog.dismiss();
                            }

                            Toast.makeText(MyFavourite.this, "Please try again!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

     /*   new_added AQuery(MyFavourite.this).ajax("http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/Customers/favourite",
                jsonArray1.toString()
                , JSONArray.class,
                new_added AjaxCallback<JSONArray>(){

                });
*/
/*

        new_added AQuery(MyFavourite.this).ajax("http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/Customers/favourite", jsonArray1, JSONArray.class, new_added AjaxCallback<JSONArray>() {

            @Override
            public void callback(String url, JSONArray json, AjaxStatus status) {
                if(json != null){
                    //successful ajax call, show status code and json content

                }else{
                    //ajax error, show error code


               //     Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();


                }            }
        });

*/




    }





    public  void callVolley() {






    //    JsonArrayRequest jsonArrayRequest = new_added JsonArrayRequest("", )

/*
        JsonArrayRequest request = new_added JsonArrayRequest("http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/Customers/favourite"
                , jsonArray
                , new_added Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response) {


                ((TextView) findViewById(R.id.txt)).setText(response.toString());
                //Here success response
            }
        }, new_added Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Here error response

                // Toast.makeText(this, "Volley  error ", Toast.LENGTH_SHORT).show();


              //  Toast.makeText(MyFavourite.this, "Volley  error "+ error.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        queue.add(request);

*/

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




    public CartItemBean getCartItem() {
        if (mCartItem == null) {
            ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(MyFavourite.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0,0,0, "", "");
            }
        }
        return mCartItem;
    }





    public String addItemInCartItemArrayList(String itemId, int qty, double itemUnitPrice, ItemList selectedItem, double deliveryCharges , double totalDp , String warehouseId, String companyId,double Price) {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(MyFavourite.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        if (mCartItem == null) {
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0,0,0, "", "");
            }
        }
        String status = "Error";
        double tempTotalPrice = 0;
        double tempTotalQuantity = 0;
        double TotalPrice = 0;
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
            } else {
                mCartItem.getmCartItemInfos().add(new CartItemInfo(itemId, qty, itemUnitPrice, selectedItem, totalDp ,Price , warehouseId, companyId));
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
            mCartItem.getmCartItemInfos().add(new CartItemInfo(itemId, qty, itemUnitPrice, selectedItem , totalDp ,Price , warehouseId, companyId));
            status = "Item Added in Cart";
            tempTotalPrice += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemUnitPrice();
            TotalPrice += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemPrice();
            tempTotalDpPoint += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemDpPoint();


            tempTotalQuantity += mCartItem.getmCartItemInfos().get(0).getQty();

            mCartItem.setDeliveryCharges(deliveryCharges);
        }
        saveAmount =TotalPrice-tempTotalPrice;
        System.out.println("SaveAmount:::"+saveAmount);
        mCartItem.setSavingAmount(saveAmount);
        mCartItem.setTotalPrice(tempTotalPrice);
        mCartItem.setTotalQuantity(tempTotalQuantity);
        mCartItem.setTotalItemPrice(TotalPrice);
        mCartItem.setTotalDpPoints(tempTotalDpPoint);


        mCartItemArraylistPref.putObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, mCartItem);
        mCartItemArraylistPref.commit();
        return status;
    }



    private void setImagesDynamicSize() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        if (displaymetrics.widthPixels >= 480 && displaymetrics.widthPixels < 720) {
            rowitemImageHeight = 77;
            rowitemImageWidth = 77;
        } else if (displaymetrics.widthPixels >= 720 && displaymetrics.widthPixels < 1080) {
            rowitemImageHeight = 115;
            rowitemImageWidth = 115;
        } else if (displaymetrics.widthPixels >= 1080 && displaymetrics.widthPixels < 1440) {
            rowitemImageHeight = 173;
            rowitemImageWidth = 173;
        } else if (displaymetrics.widthPixels >= 1440) {
            rowitemImageHeight = 230;
            rowitemImageWidth = 230;
        } else {
            rowitemImageHeight = 173;
            rowitemImageWidth = 173;
        }
    }


    public void showLoading() {


        mDialog = new Dialog(MyFavourite.this);
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




}
