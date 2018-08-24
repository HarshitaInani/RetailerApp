package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.Amitlibs.utils.ComplexPreferences;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.CustomListAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.NewsFeeds;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.indexingscroll.IndexableListView;

public class Allbrands extends AppCompatActivity {

    RequestQueue queue;

    RecyclerView recyclerView;
    CustomListAdapter adapter;

    private IndexableListView mListView;
    List<NewsFeeds> feedsList = new ArrayList<NewsFeeds>();
   // MyRecyclerAdapter adapter;
ProgressBar progressBar;
    android.support.v4.app.Fragment fragment;
    android.support.v4.app.FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allbrands);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView home=(ImageView)findViewById(R.id.home_icon_iv) ;
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Allbrands.this,HomeActivity.class));
            }
        });

        ((ImageView) toolbar.findViewById(R.id.home_more_iv)).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                View menuItemView = findViewById(R.id.home_more_iv);
                PopupMenu popup = new PopupMenu(Allbrands.this, menuItemView);


                MenuInflater inflate = popup.getMenuInflater();
                inflate.inflate(R.menu.home, popup.getMenu());
                popup.show();


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_my_orders) {
                            startActivity(new Intent(Allbrands.this, MyOrders.class));
                            return true;
                        } else if (id == R.id.action_my_wallet) {
                            startActivity(new Intent(Allbrands.this, MyWallet.class));
                            return true;
                        }
                       /* else if (id == R.id.action_my_dial) {
                            startActivity(new Intent(Allbrands.this, MyDialListActivity.class));
                            return true;
                        }*/
                        else if (id == R.id.action_my_profile) {
                            startActivity(new Intent(Allbrands.this, MyProfile.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_contact_us) {
                            startActivity(new Intent(Allbrands.this, ActivationActivity.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_cart) {
                            startActivity(new Intent(Allbrands.this, CartActivity.class));
                            return true;
                        } else if (id == R.id.action_request_item) {
                            startActivity(new Intent(Allbrands.this, RequestBrandActivity.class));
                            return true;
                        }

                        else if (id == R.id.action_feedback) {
                            startActivity(new Intent(Allbrands.this, FeedbackActivity.class));
                            return true;

                        }

                        else if (id == R.id.action_milestone) {
                            startActivity(new Intent(Allbrands.this, MilestoneActivity.class));
                            return true;

                        }

                        else if (id == R.id.action_my_rewards) {
                            startActivity(new Intent(Allbrands.this, RewardItemActivity.class));
                            return true;

                        }

                        else if (id == R.id.action_request_service) {
                            startActivity(new Intent(Allbrands.this, RequestServiceActivity.class));
                            return true;


                        }
                        else if (id == R.id.action_my_fav) {

                            startActivity(new Intent(Allbrands.this, MyFavourite.class));

                            return true;


                        }



                        else if (id == R.id.action_mynews) {

                            startActivity(new Intent(Allbrands.this, MyNews.class));

                            return true;


                        }
                        else if (id == R.id.action_my_setting) {

                            startActivity(new Intent(Allbrands.this, SettingActivity.class));

                            return true;
 }


                        else if (id == R.id.action_logout) {
                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(Allbrands.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();

                            ComplexPreferences mRetailerBeanPref2 = ComplexPreferences.getComplexPreferences(Allbrands.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);
                            mRetailerBeanPref2.clear();
                            mRetailerBeanPref2.commit();

                            ComplexPreferences mRetailerBeanPref3 = ComplexPreferences.getComplexPreferences(Allbrands.this, Constant.POPULAR_BRANDS_PREF, MODE_PRIVATE);
                            mRetailerBeanPref3.clear();
                            mRetailerBeanPref3.commit();

                            ComplexPreferences mRetailerBeanPref4 = ComplexPreferences.getComplexPreferences(Allbrands.this, Constant.SUB_SUB_CAT_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref4.clear();
                            mRetailerBeanPref4.commit();

                            ComplexPreferences mRetailerBeanPref5 = ComplexPreferences.getComplexPreferences(Allbrands.this, Constant.POPULAR_BRANDS_PREF1, MODE_PRIVATE);
                            mRetailerBeanPref5.clear();
                            mRetailerBeanPref5.commit();

                            ComplexPreferences mRetailerBeanPref6 = ComplexPreferences.getComplexPreferences(Allbrands.this, Constant.POPULAR_BRANDS_PREF2, MODE_PRIVATE);
                            mRetailerBeanPref6.clear();
                            mRetailerBeanPref6.commit();

                            ComplexPreferences mRetailerBeanPref7 = ComplexPreferences.getComplexPreferences(Allbrands.this, Constant.APP_PROMOTION_PREF, MODE_PRIVATE);
                            mRetailerBeanPref7.clear();
                            mRetailerBeanPref7.commit();

                            ComplexPreferences mRetailerBeanPref8 = ComplexPreferences.getComplexPreferences(Allbrands.this, Constant.NEWLY_ADDED_BRANDS_PREF, MODE_PRIVATE);
                            mRetailerBeanPref8.clear();
                            mRetailerBeanPref8.commit();

                            ComplexPreferences mRetailerBeanPref9 = ComplexPreferences.getComplexPreferences(Allbrands.this, Constant.ALL_TOP_ADDED_PREF, MODE_PRIVATE);
                            mRetailerBeanPref9.clear();
                            mRetailerBeanPref9.commit();

                            ComplexPreferences mRetailerBeanPref10 = ComplexPreferences.getComplexPreferences(Allbrands.this, Constant.TODAY_DHAMAKA_PREF, MODE_PRIVATE);
                            mRetailerBeanPref10.clear();
                            mRetailerBeanPref10.commit();

                            ComplexPreferences mRetailerBeanPref11 = ComplexPreferences.getComplexPreferences(Allbrands.this, Constant.EMPTY_STOCK_PREF, MODE_PRIVATE);
                            mRetailerBeanPref11.clear();
                            mRetailerBeanPref11.commit();

                            ComplexPreferences mRetailerBeanPref12 = ComplexPreferences.getComplexPreferences(Allbrands.this, Constant.BULK_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref12.clear();
                            mRetailerBeanPref12.commit();

                            ComplexPreferences mRetailerBeanPref13 = ComplexPreferences.getComplexPreferences(Allbrands.this, Constant.MOST_SELLED_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref13.clear();
                            mRetailerBeanPref13.commit();

                            clearCartData();

                            Utility.setStringSharedPreference(Allbrands.this, "ItemQJson", "");
                            Utility.setStringSharedPreference(Allbrands.this, "CompanyId", "");

//                            Utility.setStringSharedPreference(HomeActivity.this, "ItemFavJson", "");

                            SharedPreferences sharedPreferences = getSharedPreferences("dialcount", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear().commit();


                            Allbrands.this.finish();

                            startActivity(new Intent(Allbrands.this, LoginActivity_Nav.class));

                            return true;
                        } else
                            return false;
                    }
                });

                MenuPopupHelper menuHelper = new MenuPopupHelper(Allbrands.this, (MenuBuilder) popup.getMenu(), menuItemView);
                menuHelper.setForceShowIcon(true);
                menuHelper.show();
       }
        });
      /*  ((ImageView) toolbar.findViewById(R.id.nav_search_iv)).setOnClickListener(new_added View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // popVisibleFragment();
                fragment = android.support.v4.app.Fragment.instantiate(Allbrands.this, SearchFragItemList.class.getName());
                fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "SearchFragItemList").commit();

                //           ((ImageView) toolbar.findViewById(R.id.nav_search_iv)).setVisibility(View.GONE);


            }
        });*/
        //Initialize RecyclerView
        //  public static String BASE_URL = "http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/";
        //String url = "http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/SubsubCategory/GetAllBrand";
       /* recyclerView = (RecyclerView) findViewById(R.id.recyclerview);*/

       // adapter = new_added MyRecyclerAdapter(this, feedsList);
      adapter = new CustomListAdapter(this, R.layout.singleitem_recyclerview, feedsList);
        mListView = (IndexableListView) findViewById(R.id.listview);
        mListView.setAdapter(adapter);
        mListView.setFastScrollEnabled(true);
        /*recyclerView.setLayoutManager(new_added LinearLayoutManager(this));
     *//*   recyclerView.setFastScrollEnabled(true);*//*
        recyclerView.setAdapter(adapter);*/
        //Getting Instance of Volley Request Queue
        queue = NetworkController.getInstance(this).getRequestQueue();
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        //Volley's inbuilt class to make Json array request
        JsonArrayRequest newsReq = new JsonArrayRequest(Constant.BASE_URL+"SubsubCategory/GetAllBrand", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                Log.d("Json:::",response.toString());
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject obj = response.getJSONObject(i);
                        NewsFeeds feeds = new NewsFeeds(obj.getString("SubsubcategoryName"),obj.getString("LogoUrl"),obj.getString("SubsubCategoryid"),obj.getString("SubsubcategoryName"),obj.getString("Categoryid"),"1",obj.getString("SubCategoryId"),obj.getString("SubcategoryName"));
                        Collections.sort(feedsList, new Comparator<NewsFeeds>() {
                            @Override
                            public int compare(NewsFeeds lhs, NewsFeeds rhs) {
                                return lhs.getFeedName().compareTo(rhs.getFeedName());
                            }
                        });
                        feedsList.add(feeds);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    } finally {
                        //Notify adapter about data changes
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        });
        //Adding JsonArrayRequest to Request Queue
        queue.add(newsReq);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,HomeActivity.class));
    }

    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(Allbrands.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }


}