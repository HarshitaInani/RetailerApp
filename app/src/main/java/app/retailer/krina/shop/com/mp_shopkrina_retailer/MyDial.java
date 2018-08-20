package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.Amitlibs.utils.ComplexPreferences;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.MyOrderRecyclerViewAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.MyOderBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;


public class MyDial extends AppCompatActivity {

    TextView title_toolbar;
    RecyclerView recyclerView;
    Context context;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dial);
         toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
         setSupportActionBar(toolbar);
         title_toolbar=(TextView)findViewById(R.id.title_toolbar);
         title_toolbar.setText("Dial list");


        recyclerView = (RecyclerView) findViewById(R.id.recyle);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ((ImageView)findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDial.this.finish();
            }
        });



        ((ImageView)findViewById(R.id.nav_home_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDial.this.finish();
            }
        });
    ((ImageView)toolbar.findViewById(R.id.my_order_more_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View menuItemView = findViewById(R.id.my_order_more_iv);
                PopupMenu popup = new PopupMenu(MyDial.this, menuItemView);


                MenuInflater inflate = popup.getMenuInflater();
                inflate.inflate(R.menu.home, popup.getMenu());

                popup.show();


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_my_orders) {
                            startActivity(new Intent(MyDial.this, MyOrders.class));
                            return true;
                        } else if (id == R.id.action_my_wallet) {
                            startActivity(new Intent(MyDial.this, MyWallet.class));
                            return true;
                        } else if (id == R.id.action_my_profile) {
                            startActivity(new Intent(MyDial.this, MyProfile.class).putExtra("showButton", false));
                            return true;
                        }
                        else if (id == R.id.action_contact_us) {
                            startActivity(new Intent(MyDial.this, ActivationActivity.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_cart) {
                            startActivity(new Intent(MyDial.this, CartActivity.class));
                            return true;
                        } else if (id == R.id.action_request_item) {
                            startActivity(new Intent(MyDial.this, RequestBrandActivity.class));
                            return true;
                        }

                        else if (id == R.id.action_feedback) {
                            startActivity(new Intent(MyDial.this, FeedbackActivity.class));
                            return true;


                        }
                        else if (id == R.id.action_milestone) {
                            startActivity(new Intent(MyDial.this, MilestoneActivity.class));
                            return true;


                        }



                        else if (id == R.id.action_my_rewards) {
                            startActivity(new Intent(MyDial.this, RewardItemActivity.class));
                            return true;


                        }



                        else if (id == R.id.action_request_service) {
                            startActivity(new Intent(MyDial.this, RequestServiceActivity.class));
                            return true;


                        }



                        else if (id == R.id.action_my_fav) {

                            startActivity(new Intent(MyDial.this, MyFavourite.class));

                            return true;


                        }



                        else if (id == R.id.action_mynews) {

                            startActivity(new Intent(MyDial.this, MyNews.class));

                            return true;


                        }


                        else if (id == R.id.action_my_setting) {

                            startActivity(new Intent(MyDial.this, SettingActivity.class));

                            return true;


                        }


                        else if (id == R.id.action_logout) {


                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyDial.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();

                            ComplexPreferences mRetailerBeanPref2 = ComplexPreferences.getComplexPreferences(MyDial.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);
                            mRetailerBeanPref2.clear();
                            mRetailerBeanPref2.commit();

                            ComplexPreferences mRetailerBeanPref3 = ComplexPreferences.getComplexPreferences(MyDial.this, Constant.POPULAR_BRANDS_PREF, MODE_PRIVATE);
                            mRetailerBeanPref3.clear();
                            mRetailerBeanPref3.commit();
                            ComplexPreferences mRetailerBeanPref4 = ComplexPreferences.getComplexPreferences(MyDial.this, Constant.SUB_SUB_CAT_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref4.clear();
                            mRetailerBeanPref4.commit();
                            ComplexPreferences mRetailerBeanPref5 = ComplexPreferences.getComplexPreferences(MyDial.this, Constant.POPULAR_BRANDS_PREF1, MODE_PRIVATE);
                            mRetailerBeanPref5.clear();
                            mRetailerBeanPref5.commit();
                            ComplexPreferences mRetailerBeanPref6 = ComplexPreferences.getComplexPreferences(MyDial.this, Constant.POPULAR_BRANDS_PREF2, MODE_PRIVATE);
                            mRetailerBeanPref6.clear();
                            mRetailerBeanPref6.commit();
                            ComplexPreferences mRetailerBeanPref7 = ComplexPreferences.getComplexPreferences(MyDial.this, Constant.APP_PROMOTION_PREF, MODE_PRIVATE);
                            mRetailerBeanPref7.clear();
                            mRetailerBeanPref7.commit();
                            ComplexPreferences mRetailerBeanPref8 = ComplexPreferences.getComplexPreferences(MyDial.this, Constant.NEWLY_ADDED_BRANDS_PREF, MODE_PRIVATE);
                            mRetailerBeanPref8.clear();
                            mRetailerBeanPref8.commit();
                            ComplexPreferences mRetailerBeanPref9 = ComplexPreferences.getComplexPreferences(MyDial.this, Constant.ALL_TOP_ADDED_PREF, MODE_PRIVATE);
                            mRetailerBeanPref9.clear();
                            mRetailerBeanPref9.commit();
                            ComplexPreferences mRetailerBeanPref10 = ComplexPreferences.getComplexPreferences(MyDial.this, Constant.TODAY_DHAMAKA_PREF, MODE_PRIVATE);
                            mRetailerBeanPref10.clear();
                            mRetailerBeanPref10.commit();
                            ComplexPreferences mRetailerBeanPref11 = ComplexPreferences.getComplexPreferences(MyDial.this, Constant.EMPTY_STOCK_PREF, MODE_PRIVATE);
                            mRetailerBeanPref11.clear();
                            mRetailerBeanPref11.commit();
                            ComplexPreferences mRetailerBeanPref12 = ComplexPreferences.getComplexPreferences(MyDial.this, Constant.BULK_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref12.clear();
                            mRetailerBeanPref12.commit();
                            ComplexPreferences mRetailerBeanPref13 = ComplexPreferences.getComplexPreferences(MyDial.this, Constant.MOST_SELLED_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref13.clear();
                            mRetailerBeanPref13.commit();

                            clearCartData();

                            Utility.setStringSharedPreference(MyDial.this, "ItemQJson", "");

                            Utility.setStringSharedPreference(MyDial.this, "CompanyId", "");

                            SharedPreferences sharedPreferences = getSharedPreferences("dialcount", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear().commit();

//                            Utility.setStringSharedPreference(HomeActivity.this, "ItemFavJson", "");




                            MyDial.this.finish();

                            startActivity(new Intent(MyDial.this, LoginActivity_Nav.class));

                            return true;
                        } else
                            return false;
                    }
                });




                MenuPopupHelper menuHelper = new MenuPopupHelper(MyDial.this, (MenuBuilder) popup.getMenu(), menuItemView);
                menuHelper.setForceShowIcon(true);
                menuHelper.show();




            }
        });


    }
    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(MyDial.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }


}
