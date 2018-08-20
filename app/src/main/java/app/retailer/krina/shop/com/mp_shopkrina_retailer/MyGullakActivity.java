package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.Amitlibs.utils.ComplexPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.Gullakpojo;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;

public class MyGullakActivity extends AppCompatActivity {
    Button b1;
    TextView  amount;
    ProgressDialog progressDialog;
    String  apiUrl;
    String gullakamount;
    String skcode;
    RetailerBean mRetailerBean;
    String id;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gullak2);
        amount = (TextView) findViewById(R.id.amount);
        b1 = (Button) findViewById(R.id.transaction_button);
        context = this;



        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyGullakActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
      mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);




        Toolbar toolbar = (Toolbar) findViewById(R.id.my_gullak_toolbar);
//        setSupportActionBar(toolbar);

        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyGullakActivity.this.finish();
            }
        });

        ((ImageView) toolbar.findViewById(R.id.nav_home_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyGullakActivity.this.finish();
            }
        });

        ((ImageView) toolbar.findViewById(R.id.nav_refresh_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyAsyncTasks Asynctask=new MyAsyncTasks();
                Asynctask.execute();
            }


        });
        ((ImageView) toolbar.findViewById(R.id.my_order_more_iv)).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                View menuItemView = findViewById(R.id.my_order_more_iv);
                PopupMenu popup = new PopupMenu(MyGullakActivity.this, menuItemView);
                MenuInflater inflate = popup.getMenuInflater();
//                inflate.inflate(R.menu.my_order_option_menu, popup.getMenu());
                inflate.inflate(R.menu.home, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_my_orders) {
                            startActivity(new Intent(MyGullakActivity.this, MyOrders.class));
                            return true;
                        } else if (id == R.id.action_my_profile) {
                            startActivity(new Intent(MyGullakActivity.this, MyProfile.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_wallet) {
                            startActivity(new Intent(MyGullakActivity.this, MyWallet.class));
                            return true;
                        } else if (id == R.id.action_my_gullak) {
                            startActivity(new Intent(MyGullakActivity.this, MyGullakActivity.class));
                            return true;
                        } else if (id == R.id.action_contact_us) {
                            startActivity(new Intent(MyGullakActivity.this, ActivationActivity.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_cart) {
                            startActivity(new Intent(MyGullakActivity.this, CartActivity.class));
                            return true;
                        } else if (id == R.id.action_request_item) {
                            startActivity(new Intent(MyGullakActivity.this, RequestBrandActivity.class));
                            return true;
                        } else if (id == R.id.action_feedback) {
                            startActivity(new Intent(MyGullakActivity.this, FeedbackActivity.class));
                            return true;


                        } else if (id == R.id.action_milestone) {
                            startActivity(new Intent(MyGullakActivity.this, MilestoneActivity.class));
                            return true;


                        } else if (id == R.id.action_my_rewards) {
                            startActivity(new Intent(MyGullakActivity.this, RewardItemActivity.class));
                            return true;


                        } else if (id == R.id.action_request_service) {
                            startActivity(new Intent(MyGullakActivity.this, RequestServiceActivity.class));
                            return true;


                        } else if (id == R.id.action_my_fav) {

                            startActivity(new Intent(MyGullakActivity.this, MyFavourite.class));

                            return true;


                        } else if (id == R.id.action_mynews) {

                            startActivity(new Intent(MyGullakActivity.this, MyNews.class));

                            return true;


                        } else if (id == R.id.action_logout) {


                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyGullakActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();
                            ComplexPreferences mRetailerBeanPref2 = ComplexPreferences.getComplexPreferences(MyGullakActivity.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);
                            mRetailerBeanPref2.clear();
                            mRetailerBeanPref2.commit();
                            ComplexPreferences mRetailerBeanPref3 = ComplexPreferences.getComplexPreferences(MyGullakActivity.this, Constant.POPULAR_BRANDS_PREF, MODE_PRIVATE);
                            mRetailerBeanPref3.clear();
                            mRetailerBeanPref3.commit();
                            ComplexPreferences mRetailerBeanPref4 = ComplexPreferences.getComplexPreferences(MyGullakActivity.this, Constant.SUB_SUB_CAT_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref4.clear();
                            mRetailerBeanPref4.commit();
                            ComplexPreferences mRetailerBeanPref5 = ComplexPreferences.getComplexPreferences(MyGullakActivity.this, Constant.POPULAR_BRANDS_PREF1, MODE_PRIVATE);
                            mRetailerBeanPref5.clear();
                            mRetailerBeanPref5.commit();
                            ComplexPreferences mRetailerBeanPref6 = ComplexPreferences.getComplexPreferences(MyGullakActivity.this, Constant.POPULAR_BRANDS_PREF2, MODE_PRIVATE);
                            mRetailerBeanPref6.clear();
                            mRetailerBeanPref6.commit();
                            ComplexPreferences mRetailerBeanPref7 = ComplexPreferences.getComplexPreferences(MyGullakActivity.this, Constant.APP_PROMOTION_PREF, MODE_PRIVATE);
                            mRetailerBeanPref7.clear();
                            mRetailerBeanPref7.commit();
                            ComplexPreferences mRetailerBeanPref8 = ComplexPreferences.getComplexPreferences(MyGullakActivity.this, Constant.NEWLY_ADDED_BRANDS_PREF, MODE_PRIVATE);
                            mRetailerBeanPref8.clear();
                            mRetailerBeanPref8.commit();
                            ComplexPreferences mRetailerBeanPref9 = ComplexPreferences.getComplexPreferences(MyGullakActivity.this, Constant.ALL_TOP_ADDED_PREF, MODE_PRIVATE);
                            mRetailerBeanPref9.clear();
                            mRetailerBeanPref9.commit();
                            ComplexPreferences mRetailerBeanPref10 = ComplexPreferences.getComplexPreferences(MyGullakActivity.this, Constant.TODAY_DHAMAKA_PREF, MODE_PRIVATE);
                            mRetailerBeanPref10.clear();
                            mRetailerBeanPref10.commit();
                            ComplexPreferences mRetailerBeanPref11 = ComplexPreferences.getComplexPreferences(MyGullakActivity.this, Constant.EMPTY_STOCK_PREF, MODE_PRIVATE);
                            mRetailerBeanPref11.clear();
                            mRetailerBeanPref11.commit();
                            ComplexPreferences mRetailerBeanPref12 = ComplexPreferences.getComplexPreferences(MyGullakActivity.this, Constant.BULK_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref12.clear();
                            mRetailerBeanPref12.commit();
                            ComplexPreferences mRetailerBeanPref13 = ComplexPreferences.getComplexPreferences(MyGullakActivity.this, Constant.MOST_SELLED_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref13.clear();
                            mRetailerBeanPref13.commit();

                            clearCartData();

                            Utility.setStringSharedPreference(MyGullakActivity.this, "ItemQJson", "");

                            Utility.setStringSharedPreference(MyGullakActivity.this, "CompanyId", "");
                            SharedPreferences sharedPreferences = getSharedPreferences("dialcount", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear().commit();

                            MyGullakActivity.this.finish();

                            startActivity(new Intent(MyGullakActivity.this, LoginActivity_Nav.class));


                            return true;
                        }

                     else
                            return false;
                }
            });
            @SuppressLint("RestrictedApi") MenuPopupHelper menuHelper = new MenuPopupHelper(MyGullakActivity.this, (MenuBuilder) popup.getMenu(), menuItemView);
                menuHelper.setForceShowIcon(true);
                menuHelper.show();

        }
    });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyGullakActivity.this, GullakTransactionActivity.class);
                startActivity(intent);
            }
        });



        MyAsyncTasks Asynctask = new MyAsyncTasks();
        Asynctask.execute();




    }






     class MyAsyncTasks extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(MyGullakActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyGullakActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
            mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
            id= mRetailerBean.getCustomerId();
            Log.d("ID77",id);
            apiUrl = "http://137.59.52.130:8080/api/Gullak/getgullakamount?CustId="+ id;
            // implement API in background and store the response in current variable
            String current = "";
            try {
                URL url;
                HttpURLConnection Con = null;
                try {
                    url = new URL(apiUrl);
                    BufferedReader bf=new BufferedReader(new InputStreamReader(Con.getInputStream()));

                    /*urlConnection = (HttpURLConnection) url .openConnection();
                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader isw = new InputStreamReader(in);*/

                    int data = bf.read();
                    while (data != -1) {
                        current += (char) data;
                        data = bf.read();
                        /*System.out.print(current);*/

                    }
                    // return the data to onPostExecute method
                    return current;

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (Con != null) {
                        Con.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();

            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {

            Log.d("data1234", s.toString());
            // dismiss the progress dialog after receiving data from API
            progressDialog.dismiss();
            ArrayList<Gullakpojo> GullakpojoArrayList=new ArrayList<>();
            try {
                // JSON Parsing of data
                JSONObject oneObject= new JSONObject(s);
                /*for(int i=0;i<oneObject.length();i++) {
                   oneObject = oneObject.getJSONObject(String.valueOf(i));*/

               gullakamount = oneObject.getString("GullakAmount");
                    skcode=oneObject.getString("SkCode");
                    Log.d("SKCODE", skcode);
                /*String GullakAmount=oneObject.getString("GullakAmount");*/
                    GullakpojoArrayList.add(new Gullakpojo(gullakamount));
                    // Pulling items from the array
                    amount.setText("Total Amount:"+gullakamount);
                    Log.d("VALUE", String.valueOf(GullakpojoArrayList));

               /* }*/

            } catch (JSONException e) {
                e.printStackTrace();
            }

            /*if (GullakpojoArrayList != null && !GullakpojoArrayList.isEmpty())*/



        }

    }
    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(MyGullakActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }
}
