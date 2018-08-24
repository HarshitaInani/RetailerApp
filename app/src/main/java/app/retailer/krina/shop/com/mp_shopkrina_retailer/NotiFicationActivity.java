package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.NotificationFragListAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.NotificationListBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;
//import app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment.NotificationFrag;

/**
 * Created by User on 17-08-2018.
 */

public class NotiFicationActivity extends AppCompatActivity {
    int rowitemImageHeight;
    int rowitemImageWidth;


    RecyclerView mNotificaitonRecyclerView;
    AsyncTask<String, Void, JSONObject> mNotificationAsyncTask;

    AlertDialog mAlertDialog;
    ArrayList<NotificationListBean> mNotificationListArrayList = new ArrayList<>();
    NotificationFragListAdapter mNotificationFragAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_frag);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        mNotificaitonRecyclerView=(RecyclerView)findViewById(R.id.frag_notification_rv);
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(NotiFicationActivity.this, LinearLayoutManager.VERTICAL, false);
        mNotificaitonRecyclerView.setLayoutManager(layoutManager2);

        mNotificationFragAdapter = new NotificationFragListAdapter(NotiFicationActivity.this, mNotificationListArrayList);
        mNotificaitonRecyclerView.setAdapter(mNotificationFragAdapter);
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(NotiFicationActivity.this, Constant.RETAILER_BEAN_PREF, NotiFicationActivity.this.MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        mNotificationAsyncTask = new NotificationListAsyncTask().execute(mRetailerBean.getCustomerId());


        ImageView home=(ImageView)findViewById(R.id.home_icon_iv) ;
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NotiFicationActivity.this,HomeActivity.class));
            }
        });

        ((ImageView) toolbar.findViewById(R.id.home_more_iv)).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                View menuItemView = findViewById(R.id.home_more_iv);
                PopupMenu popup = new PopupMenu(NotiFicationActivity.this, menuItemView);


                MenuInflater inflate = popup.getMenuInflater();
                inflate.inflate(R.menu.home, popup.getMenu());
                popup.show();


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_my_orders) {
                            startActivity(new Intent(NotiFicationActivity.this, MyOrders.class));
                            return true;
                        } else if (id == R.id.action_my_wallet) {
                            startActivity(new Intent(NotiFicationActivity.this, MyWallet.class));
                            return true;
                        }
                        else if (id == R.id.action_my_gullak) {
                            startActivity(new Intent(NotiFicationActivity.this, MyGullakActivity.class));
                            return true;
                        }

                        else if (id == R.id.action_my_profile) {
                            startActivity(new Intent(NotiFicationActivity.this, MyProfile.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_contact_us) {
                            startActivity(new Intent(NotiFicationActivity.this, ActivationActivity.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_cart) {
                            startActivity(new Intent(NotiFicationActivity.this, CartActivity.class));
                            return true;
                        } else if (id == R.id.action_request_item) {
                            startActivity(new Intent(NotiFicationActivity.this, RequestBrandActivity.class));
                            return true;
                        }

                        else if (id == R.id.action_feedback) {
                            startActivity(new Intent(NotiFicationActivity.this, FeedbackActivity.class));
                            return true;

                        }

                        else if (id == R.id.action_milestone) {
                            startActivity(new Intent(NotiFicationActivity.this, MilestoneActivity.class));
                            return true;

                        }

                        else if (id == R.id.action_my_rewards) {
                            startActivity(new Intent(NotiFicationActivity.this, RewardItemActivity.class));
                            return true;

                        }

                        else if (id == R.id.action_request_service) {
                            startActivity(new Intent(NotiFicationActivity.this, RequestServiceActivity.class));
                            return true;


                        }
                        else if (id == R.id.action_my_fav) {

                            startActivity(new Intent(NotiFicationActivity.this, MyFavourite.class));

                            return true;


                        }



                        else if (id == R.id.action_mynews) {

                            startActivity(new Intent(NotiFicationActivity.this, MyNews.class));

                            return true;


                        }
                        else if (id == R.id.action_my_setting) {

                            startActivity(new Intent(NotiFicationActivity.this, SettingActivity.class));

                            return true;
                        }


                        else if (id == R.id.action_logout) {
                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(NotiFicationActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();

                            ComplexPreferences mRetailerBeanPref2 = ComplexPreferences.getComplexPreferences(NotiFicationActivity.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);
                            mRetailerBeanPref2.clear();
                            mRetailerBeanPref2.commit();

                            ComplexPreferences mRetailerBeanPref3 = ComplexPreferences.getComplexPreferences(NotiFicationActivity.this, Constant.POPULAR_BRANDS_PREF, MODE_PRIVATE);
                            mRetailerBeanPref3.clear();
                            mRetailerBeanPref3.commit();

                            ComplexPreferences mRetailerBeanPref4 = ComplexPreferences.getComplexPreferences(NotiFicationActivity.this, Constant.SUB_SUB_CAT_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref4.clear();
                            mRetailerBeanPref4.commit();

                            ComplexPreferences mRetailerBeanPref5 = ComplexPreferences.getComplexPreferences(NotiFicationActivity.this, Constant.POPULAR_BRANDS_PREF1, MODE_PRIVATE);
                            mRetailerBeanPref5.clear();
                            mRetailerBeanPref5.commit();

                            ComplexPreferences mRetailerBeanPref6 = ComplexPreferences.getComplexPreferences(NotiFicationActivity.this, Constant.POPULAR_BRANDS_PREF2, MODE_PRIVATE);
                            mRetailerBeanPref6.clear();
                            mRetailerBeanPref6.commit();

                            ComplexPreferences mRetailerBeanPref7 = ComplexPreferences.getComplexPreferences(NotiFicationActivity.this, Constant.APP_PROMOTION_PREF, MODE_PRIVATE);
                            mRetailerBeanPref7.clear();
                            mRetailerBeanPref7.commit();

                            ComplexPreferences mRetailerBeanPref8 = ComplexPreferences.getComplexPreferences(NotiFicationActivity.this, Constant.NEWLY_ADDED_BRANDS_PREF, MODE_PRIVATE);
                            mRetailerBeanPref8.clear();
                            mRetailerBeanPref8.commit();

                            ComplexPreferences mRetailerBeanPref9 = ComplexPreferences.getComplexPreferences(NotiFicationActivity.this, Constant.ALL_TOP_ADDED_PREF, MODE_PRIVATE);
                            mRetailerBeanPref9.clear();
                            mRetailerBeanPref9.commit();

                            ComplexPreferences mRetailerBeanPref10 = ComplexPreferences.getComplexPreferences(NotiFicationActivity.this, Constant.TODAY_DHAMAKA_PREF, MODE_PRIVATE);
                            mRetailerBeanPref10.clear();
                            mRetailerBeanPref10.commit();

                            ComplexPreferences mRetailerBeanPref11 = ComplexPreferences.getComplexPreferences(NotiFicationActivity.this, Constant.EMPTY_STOCK_PREF, MODE_PRIVATE);
                            mRetailerBeanPref11.clear();
                            mRetailerBeanPref11.commit();

                            ComplexPreferences mRetailerBeanPref12 = ComplexPreferences.getComplexPreferences(NotiFicationActivity.this, Constant.BULK_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref12.clear();
                            mRetailerBeanPref12.commit();

                            ComplexPreferences mRetailerBeanPref13 = ComplexPreferences.getComplexPreferences(NotiFicationActivity.this, Constant.MOST_SELLED_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref13.clear();
                            mRetailerBeanPref13.commit();

                            clearCartData();

                            Utility.setStringSharedPreference(NotiFicationActivity.this, "ItemQJson", "");
                            Utility.setStringSharedPreference(NotiFicationActivity.this, "CompanyId", "");

//                            Utility.setStringSharedPreference(HomeActivity.this, "ItemFavJson", "");

                            SharedPreferences sharedPreferences = getSharedPreferences("dialcount", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear().commit();


                            NotiFicationActivity.this.finish();

                            startActivity(new Intent(NotiFicationActivity.this, LoginActivity_Nav.class));

                            return true;
                        } else
                            return false;
                    }
                });

                @SuppressLint("RestrictedApi") MenuPopupHelper menuHelper = new MenuPopupHelper(NotiFicationActivity.this, (MenuBuilder) popup.getMenu(), menuItemView);
                menuHelper.setForceShowIcon(true);
                menuHelper.show();
            }
        });


    }
    @Override
    public void onPause() {
        if (mNotificationAsyncTask != null && !mNotificationAsyncTask.isCancelled())
            mNotificationAsyncTask.cancel(true);
        super.onPause();
    }

    public class NotificationListAsyncTask extends AsyncTask<String, Void, JSONObject> {
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(NotiFicationActivity.this);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.progress_dialog);
            ImageView la = (ImageView) mDialog.findViewById(R.id.gridprogressBar);
            la.setBackgroundResource(R.drawable.custom_progress_dialog_animation);
            animation = (AnimationDrawable) la.getBackground();
            animation.start();
            mDialog.setCancelable(false);
            mDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObjFromurl = null;
            try {
                jsonObjFromurl = new HttpUrlConnectionJSONParser().getJsonObjectFromHttpUrlConnection(Constant.BASE_URL_NOTIFICATION + "?list=" + 50 + "&page=1" + "&customerid=" + params[0], null, HttpUrlConnectionJSONParser.Http.GET);
                if (jsonObjFromurl != null) {
                    mNotificationListArrayList.clear();
                    JSONArray mJsonArray = jsonObjFromurl.getJSONArray("notificationmaster");
                    if (mJsonArray.length() > 0) {
                        for (int i = 0; i < mJsonArray.length(); i++) {
                            String Id = isNullOrEmpty(mJsonArray.getJSONObject(i), "Id");
                            String title = isNullOrEmpty(mJsonArray.getJSONObject(i), "title");
                            String Message = isNullOrEmpty(mJsonArray.getJSONObject(i), "Message");
                            String ImageUrl = isNullOrEmpty(mJsonArray.getJSONObject(i), "ImageUrl");
                            String NotificationTime = isNullOrEmpty(mJsonArray.getJSONObject(i), "NotificationTime");
                            boolean Deleted = mJsonArray.getJSONObject(i).getBoolean("Deleted");

                            mNotificationListArrayList.add(new NotificationListBean(Id, title, Message, ImageUrl, NotificationTime, Deleted));
                            publishProgress();
                        }
                    }
                } else {
                    Toast.makeText(NotiFicationActivity.this, "Server not responding properly", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObjFromurl;
        }

        @Override
        protected void onPostExecute(JSONObject jsonArray) {
            Log.d("Notifiication", String.valueOf(mNotificationListArrayList));
            if (mNotificationListArrayList != null && jsonArray.length() > 0) {
                if (NotiFicationActivity.this!= null) {
                    if (!mNotificationListArrayList.isEmpty()) {
                        mNotificationFragAdapter.notifyDataSetChanged();/* = new_added SearchFragListAdapter(getActivity(), mItemListArrayList, rowitemImageWidth, rowitemImageHeight, tvTotalItemPrice, tvTotalItemQty);
                        mItemListRecyclerView.setAdapter(mSearchFragAdapter);*/
                    } else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NotiFicationActivity.this);
                        alertDialogBuilder.setMessage("No Notification available");
                        alertDialogBuilder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        ((new HomeActivity()) ).shoeToolbarIcon();
                                        getFragmentManager().popBackStack();

                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }
                }
            } else {
                Toast.makeText(NotiFicationActivity.this, "Improper response from server", Toast.LENGTH_SHORT).show();
            }
            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            if (NotiFicationActivity.this != null) {
                if (mNotificationListArrayList != null && !mNotificationListArrayList.isEmpty()) {
                    mNotificationFragAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(NotiFicationActivity.this, "Items are not available", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                }
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
                Log.e("HomeFragItemListFrag", key + " is not available which should come in response");
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(NotiFicationActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }

}