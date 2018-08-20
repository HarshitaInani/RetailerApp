package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.MyOrderRecyclerViewAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.MyOderBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.MyOrderDetailsBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;

/**
 * Created by Krishna on 25-01-2017.
 */

public class MyOrders extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    boolean isItemListAvail = false;
    RecyclerView mMyOrderRecyclerView;
    AsyncTask<String, Void, JSONArray> mgetMyOrderAsyncTask;
TextView summary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_orders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);


      /*  HashMap map = new_added HashMap();
        map.put("test","test");

        ComplexPreferences mComplexPreferences = ComplexPreferences.getComplexPreferences(MyOrders.this,"testhe",MODE_PRIVATE);
        mComplexPreferences.putObject("asd",map);
*/


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.my_order_swipe_refresh_layout);
        mMyOrderRecyclerView = (RecyclerView) findViewById(R.id.my_order_rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mMyOrderRecyclerView.setLayoutManager(llm);


        swipeRefreshLayout.setRefreshing( false );
        swipeRefreshLayout.setEnabled( false );

      /*swipeRefreshLayout.post(new_added Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                Toast.makeText(MyOrders.this, "Please write code to refresh", Toast.LENGTH_SHORT).show();
            }
        });*/
summary=(TextView)findViewById(R.id.Summary_button) ;
        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyOrders.this, SummaryActivity.class);
                startActivity(intent);                }
        });

        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyOrders.this.finish();
            }
        });

        ((ImageView) toolbar.findViewById(R.id.nav_home_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyOrders.this.finish();
            }
        });

        ((ImageView) toolbar.findViewById(R.id.nav_refresh_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
                if (mRetailerBean != null && !mRetailerBean.getCustomerId().equalsIgnoreCase("0")) {
                    ComplexPreferences mMyOrderPref = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.MY_ORDER_PREF, MODE_PRIVATE);
                    Type typeMyOrderArrayList = new TypeToken<ArrayList<MyOderBean>>() {
                    }.getType();
                    ArrayList<MyOderBean> myOderBeanArrayList = mMyOrderPref.getArray(Constant.MY_ORDER_PREF_OBJ, typeMyOrderArrayList);
                    if (myOderBeanArrayList != null) {
                        MyOrderRecyclerViewAdapter myOrderRecyclerViewAdapter = new MyOrderRecyclerViewAdapter(MyOrders.this, myOderBeanArrayList);
                        mMyOrderRecyclerView.setAdapter(myOrderRecyclerViewAdapter);
                        isItemListAvail = true;
                        mgetMyOrderAsyncTask = new GetMyOrderAsyncTask().execute(mRetailerBean.getMobile());
                    } else {
                        mgetMyOrderAsyncTask = new GetMyOrderAsyncTask().execute(mRetailerBean.getMobile());
                        isItemListAvail = false;
                    }
                } else {
                    Intent i = new Intent(getApplicationContext(), LoginActivity_Nav.class);
                    startActivity(i);
                    MyOrders.this.finish();
                }
            }
        });

       /* ((ImageView) toolbar.findViewById(R.id.my_order_more_iv)).setOnClickListener(new_added View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View menuItemView = findViewById(R.id.my_order_more_iv);
                PopupMenu popup = new_added PopupMenu(MyOrders.this, menuItemView);
                MenuInflater inflate = popup.getMenuInflater();
                inflate.inflate(R.menu.my_order_option_menu, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new_added PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_my_profile) {
                            startActivity(new_added Intent(MyOrders.this, MyProfile.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_contact_us) {
                            startActivity(new_added Intent(MyOrders.this, ActivationActivity.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_cart) {
                            startActivity(new_added Intent(MyOrders.this, CartActivity.class));
                            return true;
                        } else if (id == R.id.action_request_item) {
                            startActivity(new_added Intent(MyOrders.this, RequestBrandActivity.class));
                            return true;
                        } else if (id == R.id.action_feedback) {
                            startActivity(new_added Intent(MyOrders.this, FeedbackActivity.class));
                            return true;
                        }







                        else if (id == R.id.action_milestone) {
                            startActivity(new_added Intent(MyOrders.this, MilestoneActivity.class));
                            return true;


                        }



                        else if (id == R.id.action_my_rewards) {
                            startActivity(new_added Intent(MyOrders.this, RewardItemActivity.class));
                            return true;


                        }



                        else if (id == R.id.action_request_service) {
                            startActivity(new_added Intent(MyOrders.this, RequestServiceActivity.class));
                            return true;


                        }



                        else if (id == R.id.action_my_fav) {

                            startActivity(new_added Intent(MyOrders.this, MyFavourite.class));

                            return true;


                        }



                        else if (id == R.id.action_mynews) {

                            startActivity(new_added Intent(MyOrders.this, MyNews.class));

                            return true;


                        }





                        else if (id == R.id.action_logout) {


                            *//*

                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();
                            startActivity(new_added Intent(MyOrders.this, LoginActivity_Nav.class));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                finishAffinity();
                            } else {
                                MyOrders.this.finish();
                            }

                            *//*


                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();



                            ComplexPreferences mRetailerBeanPref2 = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);


                            mRetailerBeanPref2.clear();
                            mRetailerBeanPref2.commit();

                            clearCartData();

                            Utility.setStringSharedPreference(MyOrders.this, "ItemQJson", "");

                            Utility.setStringSharedPreference(MyOrders.this, "CompanyId", "");



//                            Utility.setStringSharedPreference(HomeActivity.this, "ItemFavJson", "");




                            MyOrders.this.finish();

                            startActivity(new_added Intent(MyOrders.this, LoginActivity_Nav.class));




                            return true;


                        } else
                            return false;
                    }
                });


                MenuPopupHelper menuHelper = new_added MenuPopupHelper(MyOrders.this, (MenuBuilder) popup.getMenu(), menuItemView);
                menuHelper.setForceShowIcon(true);
                menuHelper.show();

            }
        });*/

        ((ImageView) toolbar.findViewById(R.id.my_order_more_iv)).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                View menuItemView = findViewById(R.id.my_order_more_iv);
                PopupMenu popup = new PopupMenu(MyOrders.this, menuItemView);
                MenuInflater inflate = popup.getMenuInflater();


//                inflate.inflate(R.menu.my_order_option_menu, popup.getMenu());
                inflate.inflate(R.menu.home, popup.getMenu());


                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_my_orders) {
                            startActivity(new Intent(MyOrders.this, MyOrders.class));
                            return true;
                        } else if (id == R.id.action_my_profile) {
                            startActivity(new Intent(MyOrders.this, MyProfile.class).putExtra("showButton", false));
                            return true;
                        }else if (id == R.id.action_my_wallet) {
                            startActivity(new Intent(MyOrders.this, MyWallet.class));
                            return true;
                        }
                      /*  else if (id == R.id.action_my_dial) {
                            startActivity(new Intent(MyOrders.this, MyDialListActivity.class));
                            return true;
                        }
*/                       else if (id == R.id.action_my_gullak) {
                            startActivity(new Intent(MyOrders.this, MyGullakActivity.class));
                            return true;
                        }
                        else if (id == R.id.action_contact_us) {
                            startActivity(new Intent(MyOrders.this, ActivationActivity.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_cart) {
                            startActivity(new Intent(MyOrders.this, CartActivity.class));
                            return true;
                        } else if (id == R.id.action_request_item) {
                            startActivity(new Intent(MyOrders.this, RequestBrandActivity.class));
                            return true;
                        } else if (id == R.id.action_feedback) {
                            startActivity(new Intent(MyOrders.this, FeedbackActivity.class));
                            return true;


                        }





                        else if (id == R.id.action_milestone) {
                            startActivity(new Intent(MyOrders.this, MilestoneActivity.class));
                            return true;


                        }



                        else if (id == R.id.action_my_rewards) {
                            startActivity(new Intent(MyOrders.this, RewardItemActivity.class));
                            return true;


                        }



                        else if (id == R.id.action_request_service) {
                            startActivity(new Intent(MyOrders.this, RequestServiceActivity.class));
                            return true;


                        }



                        else if (id == R.id.action_my_fav) {

                            startActivity(new Intent(MyOrders.this, MyFavourite.class));

                            return true;


                        }



                        else if (id == R.id.action_mynews) {

                            startActivity(new Intent(MyOrders.this, MyNews.class));

                            return true;


                        }





                        else if (id == R.id.action_logout) {

                            /*

                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();
                            startActivity(new_added Intent(MyWallet.this, LoginActivity_Nav.class));
                            MyWallet.this.finish();

                            */





                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();

                            ComplexPreferences mRetailerBeanPref2 = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);
                            mRetailerBeanPref2.clear();
                            mRetailerBeanPref2.commit();
                            ComplexPreferences mRetailerBeanPref3 = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.POPULAR_BRANDS_PREF, MODE_PRIVATE);
                            mRetailerBeanPref3.clear();
                            mRetailerBeanPref3.commit();
                            ComplexPreferences mRetailerBeanPref4 = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.SUB_SUB_CAT_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref4.clear();
                            mRetailerBeanPref4.commit();
                            ComplexPreferences mRetailerBeanPref5 = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.POPULAR_BRANDS_PREF1, MODE_PRIVATE);
                            mRetailerBeanPref5.clear();
                            mRetailerBeanPref5.commit();
                            ComplexPreferences mRetailerBeanPref6 = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.POPULAR_BRANDS_PREF2, MODE_PRIVATE);
                            mRetailerBeanPref6.clear();
                            mRetailerBeanPref6.commit();
                            ComplexPreferences mRetailerBeanPref7 = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.APP_PROMOTION_PREF, MODE_PRIVATE);
                            mRetailerBeanPref7.clear();
                            mRetailerBeanPref7.commit();
                            ComplexPreferences mRetailerBeanPref8 = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.NEWLY_ADDED_BRANDS_PREF, MODE_PRIVATE);
                            mRetailerBeanPref8.clear();
                            mRetailerBeanPref8.commit();
                            ComplexPreferences mRetailerBeanPref9 = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.ALL_TOP_ADDED_PREF, MODE_PRIVATE);
                            mRetailerBeanPref9.clear();
                            mRetailerBeanPref9.commit();
                            ComplexPreferences mRetailerBeanPref10 = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.TODAY_DHAMAKA_PREF, MODE_PRIVATE);
                            mRetailerBeanPref10.clear();
                            mRetailerBeanPref10.commit();
                            ComplexPreferences mRetailerBeanPref11 = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.EMPTY_STOCK_PREF, MODE_PRIVATE);
                            mRetailerBeanPref11.clear();
                            mRetailerBeanPref11.commit();
                            ComplexPreferences mRetailerBeanPref12 = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.BULK_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref12.clear();
                            mRetailerBeanPref12.commit();
                            ComplexPreferences mRetailerBeanPref13 = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.MOST_SELLED_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref13.clear();
                            mRetailerBeanPref13.commit();

                            clearCartData();

                            Utility.setStringSharedPreference(MyOrders.this, "ItemQJson", "");

                            Utility.setStringSharedPreference(MyOrders.this, "CompanyId", "");



//                            Utility.setStringSharedPreference(HomeActivity.this, "ItemFavJson", "");


                            SharedPreferences sharedPreferences = getSharedPreferences("dialcount", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear().commit();

                            MyOrders.this.finish();

                            startActivity(new Intent(MyOrders.this, LoginActivity_Nav.class));


                            return true;
                        }

                    /*    else if (id == R.id.setting) {
                            startActivity(new_added Intent(MyWallet.this, SettingActivity.class));
                            //   HomeActivity.this.finish();
                            return true;
                        }



                        else if (id == R.id.mysales) {

//                            startActivity(new_added Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new_added Intent(MyWallet.this, MySalesActivity.class));

                            //    HomeActivity.this.finish();
                            return true;
                        }


                        else if (id == R.id.mybid) {

//                            startActivity(new_added Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new_added Intent(MyWallet.this, DaysBidActivity.class));

                            //     HomeActivity.this.finish();

                            //HomeActivity.this.finish();
                            return true;
                        }

                        else if (id == R.id.redeem_point) {

//                            startActivity(new_added Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new_added Intent(MyWallet.this, ReedemPointActivity.class));

                            //           HomeActivity.this.finish();
                            return true;
                        }

                        else if (id == R.id.reward_point_menu) {

//                            startActivity(new_added Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new_added Intent(MyWallet.this, RewardItemActivity.class));

                            //     HomeActivity.this.finish();
                            return true;
                        }
*/



                        else
                            return false;
                    }
                });



                @SuppressLint("RestrictedApi") MenuPopupHelper menuHelper = new MenuPopupHelper(MyOrders.this, (MenuBuilder) popup.getMenu(), menuItemView);
                menuHelper.setForceShowIcon(true);
                menuHelper.show();

            }
        });
    }

    @Override
    protected void onStart() {
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        if (mRetailerBean != null && !mRetailerBean.getCustomerId().equalsIgnoreCase("0")) {
            ComplexPreferences mMyOrderPref = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.MY_ORDER_PREF, MODE_PRIVATE);
            Type typeMyOrderArrayList = new TypeToken<ArrayList<MyOderBean>>() {
            }.getType();
            ArrayList<MyOderBean> myOderBeanArrayList = mMyOrderPref.getArray(Constant.MY_ORDER_PREF_OBJ, typeMyOrderArrayList);
            if (myOderBeanArrayList != null) {
                MyOrderRecyclerViewAdapter myOrderRecyclerViewAdapter = new MyOrderRecyclerViewAdapter(MyOrders.this, myOderBeanArrayList);
                mMyOrderRecyclerView.setAdapter(myOrderRecyclerViewAdapter);
                isItemListAvail = true;
                mgetMyOrderAsyncTask = new GetMyOrderAsyncTask().execute(mRetailerBean.getMobile());
            } else {
                mgetMyOrderAsyncTask = new GetMyOrderAsyncTask().execute(mRetailerBean.getMobile());
                isItemListAvail = false;
            }
        } else {
            Intent i = new Intent(getApplicationContext(), LoginActivity_Nav.class);
            startActivity(i);
            MyOrders.this.finish();
        }
        super.onStart();
    }



    @Override
    protected void onPause() {
        if (mgetMyOrderAsyncTask != null && !mgetMyOrderAsyncTask.isCancelled())
            mgetMyOrderAsyncTask.cancel(true);
        super.onPause();
    }

    public class GetMyOrderAsyncTask extends AsyncTask<String, Void, JSONArray> {
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(MyOrders.this);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Getting order detail please wait...");
            ImageView la = (ImageView) mDialog.findViewById(R.id.gridprogressBar);
            la.setBackgroundResource(R.drawable.custom_progress_dialog_animation);
            animation = (AnimationDrawable) la.getBackground();
            animation.start();
            mDialog.setCancelable(false);
            mDialog.show();
        }


        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArrayObjectFromUrl = null;
            try {
                jsonArrayObjectFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_MY_ORDERS + "?Mobile=" + params[0], null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayObjectFromUrl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {


            System.out.println("Order Json::"+jsonArray);
            if (jsonArray != null) {
                try {
                    ArrayList<MyOderBean> myOderBeanArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject mJsonObject = jsonArray.getJSONObject(i);
                        JSONArray mJsonArrayOrderDetails = mJsonObject.getJSONArray("orderDetails");
                        ArrayList<MyOrderDetailsBean> myOrderDetailsBeanArrayList = new ArrayList<>();
                        for (int j = 0; j < mJsonArrayOrderDetails.length(); j++) {
                            JSONObject jsonObjectOrderDetail = mJsonArrayOrderDetails.getJSONObject(j);
                            String OrderDetailsId = isNullOrEmpty(jsonObjectOrderDetail, "OrderDetailsId");
                            String OrderId = isNullOrEmpty(jsonObjectOrderDetail, "OrderId");
                            String CustomerId = isNullOrEmpty(jsonObjectOrderDetail, "CustomerId");
                            String CustomerName = isNullOrEmpty(jsonObjectOrderDetail, "CustomerName");
                            String City = isNullOrEmpty(jsonObjectOrderDetail, "City");
                            String Mobile = isNullOrEmpty(jsonObjectOrderDetail, "Mobile");
                            String OrderDate = isNullOrEmpty(jsonObjectOrderDetail, "OrderDate");
                            String CompanyId = isNullOrEmpty(jsonObjectOrderDetail, "CompanyId");
                            String CityId = isNullOrEmpty(jsonObjectOrderDetail, "CityId");
                            String Warehouseid = isNullOrEmpty(jsonObjectOrderDetail, "Warehouseid");
                            String WarehouseName = isNullOrEmpty(jsonObjectOrderDetail, "WarehouseName");
                            String CategoryName = isNullOrEmpty(jsonObjectOrderDetail, "CategoryName");
                            String ItemId = isNullOrEmpty(jsonObjectOrderDetail, "ItemId");
                            String Itempic = isNullOrEmpty(jsonObjectOrderDetail, "Itempic");
                            String itemname = isNullOrEmpty(jsonObjectOrderDetail, "itemname");
                            String itemcode = isNullOrEmpty(jsonObjectOrderDetail, "itemcode");
                            String itemNumber = isNullOrEmpty(jsonObjectOrderDetail, "itemNumber");
                            String Barcode = isNullOrEmpty(jsonObjectOrderDetail, "Barcode");
                            String price = isNullOrEmpty(jsonObjectOrderDetail, "price");
                            String UnitPrice = isNullOrEmpty(jsonObjectOrderDetail, "UnitPrice");
                            String Purchaseprice = isNullOrEmpty(jsonObjectOrderDetail, "Purchaseprice");
                            String MinOrderQty = isNullOrEmpty(jsonObjectOrderDetail, "MinOrderQty");
                            String MinOrderQtyPrice = isNullOrEmpty(jsonObjectOrderDetail, "MinOrderQtyPrice");
                            String qty = isNullOrEmpty(jsonObjectOrderDetail, "qty");
                            String Noqty = isNullOrEmpty(jsonObjectOrderDetail, "Noqty");
                            String AmtWithoutTaxDisc = isNullOrEmpty(jsonObjectOrderDetail, "AmtWithoutTaxDisc");
                            String AmtWithoutAfterTaxDisc = isNullOrEmpty(jsonObjectOrderDetail, "AmtWithoutAfterTaxDisc");
                            String TotalAmountAfterTaxDisc = isNullOrEmpty(jsonObjectOrderDetail, "TotalAmountAfterTaxDisc");
                            String NetAmmount = isNullOrEmpty(jsonObjectOrderDetail, "NetAmmount");
                            String DiscountPercentage = isNullOrEmpty(jsonObjectOrderDetail, "DiscountPercentage");
                            String DiscountAmmount = isNullOrEmpty(jsonObjectOrderDetail, "DiscountAmmount");
                            String NetAmtAfterDis = isNullOrEmpty(jsonObjectOrderDetail, "NetAmtAfterDis");
                            String TaxPercentage = isNullOrEmpty(jsonObjectOrderDetail, "TaxPercentage");
                            String TaxAmmount = isNullOrEmpty(jsonObjectOrderDetail, "TaxAmmount");
                            String TotalAmt = isNullOrEmpty(jsonObjectOrderDetail, "TotalAmt");
                            String CreatedDate = isNullOrEmpty(jsonObjectOrderDetail, "CreatedDate");
                            String UpdatedDate = isNullOrEmpty(jsonObjectOrderDetail, "UpdatedDate");
                            String Deleted = isNullOrEmpty(jsonObjectOrderDetail, "Deleted");
                            String Status = isNullOrEmpty(jsonObjectOrderDetail, "Status");
                            String SizePerUnit = isNullOrEmpty(jsonObjectOrderDetail, "SizePerUnit");
                            String CurrentStock = isNullOrEmpty(jsonObjectOrderDetail, "CurrentStock");

                            MyOrderDetailsBean myOrderDetailsBean = new MyOrderDetailsBean(OrderDetailsId, OrderId, CustomerId, CustomerName, City, Mobile, OrderDate, CompanyId, CityId, Warehouseid, WarehouseName, CategoryName, ItemId, Itempic, itemname, itemcode, itemNumber, Barcode, price, UnitPrice, Purchaseprice, MinOrderQty, MinOrderQtyPrice, qty, Noqty, AmtWithoutTaxDisc, AmtWithoutAfterTaxDisc, TotalAmountAfterTaxDisc, NetAmmount, DiscountPercentage, DiscountAmmount, NetAmtAfterDis, TaxPercentage, TaxAmmount, TotalAmt, CreatedDate, UpdatedDate, Deleted, Status, SizePerUnit, CurrentStock);
                            myOrderDetailsBeanArrayList.add(myOrderDetailsBean);
                        }
                        String OrderId = isNullOrEmpty(mJsonObject, "OrderId");
                        String CompanyId = isNullOrEmpty(mJsonObject, "CompanyId");
                        String SalesPersonId = isNullOrEmpty(mJsonObject, "SalesPersonId");
                        String SalesPerson = isNullOrEmpty(mJsonObject, "SalesPerson");
                        String CustomerId = isNullOrEmpty(mJsonObject, "CustomerId");
                        String CustomerName = isNullOrEmpty(mJsonObject, "CustomerName");
                        String Skcode = isNullOrEmpty(mJsonObject, "Skcode");
                        String ShopName = isNullOrEmpty(mJsonObject, "ShopName");
                        String Status = isNullOrEmpty(mJsonObject, "Status");
                        String invoice_no = isNullOrEmpty(mJsonObject, "invoice_no");
                        String CustomerCategoryId = isNullOrEmpty(mJsonObject, "CustomerCategoryId");
                        String CustomerCategoryName = isNullOrEmpty(mJsonObject, "CustomerCategoryName");
                        String CustomerType = isNullOrEmpty(mJsonObject, "CustomerType");
                        String Customerphonenum = isNullOrEmpty(mJsonObject, "Customerphonenum");
                        String BillingAddress = isNullOrEmpty(mJsonObject, "BillingAddress");
                        String ShippingAddress = isNullOrEmpty(mJsonObject, "ShippingAddress");
                        String TotalAmount = isNullOrEmpty(mJsonObject, "TotalAmount");
                        String GrossAmount = isNullOrEmpty(mJsonObject, "GrossAmount");
                        String DiscountAmount = isNullOrEmpty(mJsonObject, "DiscountAmount");
                        String TaxAmount = isNullOrEmpty(mJsonObject, "TaxAmount");
                        String CityId = isNullOrEmpty(mJsonObject, "CityId");
                        String Warehouseid = isNullOrEmpty(mJsonObject, "Warehouseid");
                        String WarehouseName = isNullOrEmpty(mJsonObject, "WarehouseName");
                        String active = isNullOrEmpty(mJsonObject, "active");
                        String CreatedDate = isNullOrEmpty(mJsonObject, "CreatedDate");
                        String Deliverydate = isNullOrEmpty(mJsonObject, "Deliverydate");
                        String UpdatedDate = isNullOrEmpty(mJsonObject, "UpdatedDate");
                        String Deleted = isNullOrEmpty(mJsonObject, "Deleted");
                        String ReDispatchCount = isNullOrEmpty(mJsonObject, "ReDispatchCount");
                        String DivisionId = isNullOrEmpty(mJsonObject, "DivisionId");
                        String ReasonCancle = isNullOrEmpty(mJsonObject, "ReasonCancle");
                        String Cluster = isNullOrEmpty(mJsonObject, "Cluster");
                        String deliveryCharge = isNullOrEmpty(mJsonObject, "deliveryCharge");

                        MyOderBean myOderBean = new MyOderBean(OrderId, CompanyId, SalesPersonId, SalesPerson, CustomerId, CustomerName, Skcode, ShopName, Status, invoice_no, CustomerCategoryId, CustomerCategoryName, CustomerType, Customerphonenum, BillingAddress, ShippingAddress, TotalAmount, GrossAmount, DiscountAmount, TaxAmount, CityId, Warehouseid, WarehouseName, active, CreatedDate, Deliverydate, UpdatedDate, Deleted, ReDispatchCount, DivisionId, ReasonCancle, Cluster, deliveryCharge, myOrderDetailsBeanArrayList);
                        myOderBeanArrayList.add(myOderBean);
                    }
                    Collections.reverse(myOderBeanArrayList);
                    ComplexPreferences mMyOrderPref = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.MY_ORDER_PREF, MODE_PRIVATE);
                    mMyOrderPref.putObject(Constant.MY_ORDER_PREF_OBJ, myOderBeanArrayList);
                    mMyOrderPref.commit();
                    MyOrderRecyclerViewAdapter myOrderRecyclerViewAdapter = new MyOrderRecyclerViewAdapter(MyOrders.this, myOderBeanArrayList);
                    mMyOrderRecyclerView.setAdapter(myOrderRecyclerViewAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(MyOrders.this, "Improper response from server", Toast.LENGTH_SHORT).show();
            }
            swipeRefreshLayout.setRefreshing(false);
            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
            }
        }
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
                Log.e("MyOrder", key + " is not available which should come in response");
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }




    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }


}
