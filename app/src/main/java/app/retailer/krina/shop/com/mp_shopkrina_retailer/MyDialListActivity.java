package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.utils.ComplexPreferences;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.DialListAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.DialCustomerItem;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;


public class MyDialListActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
   // private RecyclerView.Adapter adapter;
    private ProgressDialog progressDialog;
    TextView title_toolbar;
    Dialog mDialog;
    AnimationDrawable animation;
    SearchView editsearch;
    DialListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dial_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_dial_toolbar);
        setSupportActionBar(toolbar);
        title_toolbar=(TextView)findViewById(R.id.title_toolbar) ;
        title_toolbar.setText("My Dial");

        //Finally initializing our adapter
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = (RecyclerView)findViewById(R.id.top_brand_list);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        if(isNetworkAvailable())
        {
            callAqueryDialAvailable();
        }else
        {
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show();
        }

        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialListActivity.this.finish();
               // startActivity(new_added Intent(MyDialListActivity.this ,BeatOrderActivity.class));

            }
        });


        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialListActivity.this.finish();
            }
        });

        ((ImageView) toolbar.findViewById(R.id.nav_home_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialListActivity.this.finish();
                startActivity(new Intent(MyDialListActivity.this, HomeActivity.class));
            }
        });





        // Locate the EditText in listview_main.xml
        editsearch = (SearchView) findViewById(R.id.search);

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void callAqueryDialAvailable() {
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyDialListActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        String url=Constant.BASE_URL+"DialPoint/CustomerDeliveryDial?CustomerId="+mRetailerBean.getCustomerId();
      // String url=Constant.BASE_URL+"DialPoint/SalesmanDial?SalesManId=8";
        System.out.println("UrlDial::"+url);
        showLoading();
        new AQuery(getApplicationContext()).ajax(url, null, JSONArray.class, new AjaxCallback<JSONArray>() {

            @Override
            public void callback(String url, JSONArray json, AjaxStatus status) {


                System.out.println("JsonData"+json);
                if (json.toString().equalsIgnoreCase("[]")) {

                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }

                    Toast.makeText(MyDialListActivity.this, "No dial available", Toast.LENGTH_SHORT).show();


                } else {
                    ArrayList<DialCustomerItem> dialItemArrayList = new ArrayList<>();
                    ArrayList<DialCustomerItem> AllItemArrayList = new ArrayList<>();
                    //    Toast.makeText(BeatOrderActivity.this, ""+ json.toString(), Toast.LENGTH_SHORT).show();
                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }

                    try {
                        for (int i = 0; i < json.length(); i++) {
                            JSONObject mJsonObject = json.getJSONObject(i);
                            String id= mJsonObject.getString("Id");
                            String customerId= mJsonObject.getString("CustomerId");
                            String Mobile= mJsonObject.getString("Mobile");
                            String orderAmount= mJsonObject.getString("OrderAmount");
                            String point= mJsonObject.getString("point");
                            String usedUnused= mJsonObject.getString("UsedUnused");
                            String createdDate= mJsonObject.getString("CreatedDate");
                           // String CustomerName= mJsonObject.getString("CustomerName");
                            String ShopName= mJsonObject.getString("ShopName");
                            String Skcode= mJsonObject.getString("Skcode");
//                            String BillingAddress= mJsonObject.getString("BillingAddress");
                           // dialItemArrayList.add(new_added DialCustomerItem(id,customerId,orderId,orderAmount,String.valueOf(point),usedUnused,createdDate,CustomerName,ShopName,Skcode,BillingAddress));
                            AllItemArrayList.add(new DialCustomerItem(id,"ss",ShopName,Skcode,Mobile,point));
                            if(dialItemArrayList.size()==0)
                            {
                                dialItemArrayList.add(new DialCustomerItem(id,"ss",ShopName,Skcode,Mobile,point));

                            }else
                            {
                                boolean ispresent=false;
                                for (int j = 0; j <dialItemArrayList.size() ; j++) {
                                    if(dialItemArrayList.get(j).getSkcode().equalsIgnoreCase(Skcode))
                                    {
                                        ispresent=true;
                                        break;
                                    }
                                }
                                if(!ispresent)
                                {
                                    dialItemArrayList.add(new DialCustomerItem(id,"ss",ShopName,Skcode,Mobile,point));

                                }else
                                {

                                }
                            }
                        }
                        System.out.println("dialItemArrayList::"+dialItemArrayList.size());

                        adapter = new DialListAdapter(AllItemArrayList,dialItemArrayList, MyDialListActivity.this);
                        recyclerView.setAdapter(adapter);
                        editsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {

                                String text = newText;

                                //  adapter.filter(text);
                                adapter.getFilter().filter(newText);
                                return true;
                                //adapter.getFilter().filter(newText);
                                //return false;

                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }
    public void showLoading() {
        mDialog = new Dialog(MyDialListActivity.this);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.progress_dialog);
        ((TextView) mDialog.findViewById(R.id.progressText)).setText("Logging in please wait...");
        ImageView la = (ImageView) mDialog.findViewById(R.id.gridprogressBar);
        la.setBackgroundResource(R.drawable.custom_progress_dialog_animation);
        animation = (AnimationDrawable) la.getBackground();
        animation.start();
        mDialog.setCancelable(true);
        mDialog.show();

    }
    @Override
    public void onBackPressed() {
        //  super.onBackPressed()
        MyDialListActivity.this.finish();
       // startActivity(new_added Intent(MyDialListActivity.this ,BeatOrderActivity.class));

    }
    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(MyDialListActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }
}
