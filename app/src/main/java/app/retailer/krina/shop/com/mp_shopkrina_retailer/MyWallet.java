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
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
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
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
import org.json.JSONObject;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;

/**
 * Created by Krishna on 20-03-2017.
 */

public class MyWallet extends AppCompatActivity {

    TextView tvWalletText;
    TextView tvWalletTextHindi;
    TextView tvWalletAmount;
    AsyncTask<String, Void, JSONObject> myWalletAsyncTaskObj;
    Dialog mDialog;
    AnimationDrawable animation;
    String urlRemote = "";
    TextView amountTv;
    Context context;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wallet);

      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
        setSupportActionBar(toolbar);

        tvWalletText = (TextView) findViewById(R.id.my_wallet_frag_amount_txt);
        tvWalletTextHindi = (TextView) findViewById(R.id.my_wallet_frag_amount_txt2);
        tvWalletAmount = (TextView) findViewById(R.id.my_wallet_frag_amount);
        Typeface normalTypeface = FontCache.get("fonts/krdv011.ttf", MyWallet.this);
        tvWalletTextHindi.setTypeface(normalTypeface);
        tvWalletTextHindi.setText("vkidk okysV vekamUV gS");


        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new_added View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWallet.this.finish();
            }
        });

        ((ImageView) toolbar.findViewById(R.id.nav_home_icon_iv)).setOnClickListener(new_added View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWallet.this.finish();
            }
        });

        ((ImageView) toolbar.findViewById(R.id.nav_refresh_iv)).setOnClickListener(new_added View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
                if (mRetailerBean != null && !mRetailerBean.getCustomerId().equalsIgnoreCase("0")) {
                    myWalletAsyncTaskObj = new_added myWalletAsyncTask().execute(mRetailerBean.getCustomerId());
                } else {
                    Toast.makeText(MyWallet.this, "You are not logged in please login", Toast.LENGTH_SHORT).show();
                    Intent i = new_added Intent(getApplicationContext(), LoginActivity_Nav.class);
                    startActivity(i);
                    MyWallet.this.finish();
                }
            }
        });

        ((ImageView) toolbar.findViewById(R.id.my_order_more_iv)).setOnClickListener(new_added View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View menuItemView = findViewById(R.id.my_order_more_iv);
                PopupMenu popup = new_added PopupMenu(MyWallet.this, menuItemView);
                MenuInflater inflate = popup.getMenuInflater();
                inflate.inflate(R.menu.my_order_option_menu, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new_added PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_my_profile) {
                            startActivity(new_added Intent(MyWallet.this, MyProfile.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_orders) {
                            startActivity(new_added Intent(MyWallet.this, MyOrders.class));
                            return true;
                        } else if (id == R.id.action_contact_us) {
                            startActivity(new_added Intent(MyWallet.this, ActivationActivity.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_cart) {
                            startActivity(new_added Intent(MyWallet.this, CartActivity.class));
                            return true;
                        } else if (id == R.id.action_request_item) {
                            startActivity(new_added Intent(MyWallet.this, RequestBrandActivity.class));
                            return true;
                        } else if (id == R.id.action_feedback) {
                            startActivity(new_added Intent(MyWallet.this, FeedbackActivity.class));
                            return true;
                        } else if (id == R.id.action_logout) {
                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();
                            startActivity(new_added Intent(MyWallet.this, LoginActivity_Nav.class));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                finishAffinity();
                            } else {
                                MyWallet.this.finish();
                            }
                            return true;
                        } else
                            return false;
                    }
                });
            }
        });

        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        if (mRetailerBean != null && !mRetailerBean.getCustomerId().equalsIgnoreCase("0")) {
            tvWalletText.setText("Dear " + mRetailerBean.getName() + " your SK dream points is: ");
            myWalletAsyncTaskObj = new_added myWalletAsyncTask().execute(mRetailerBean.getCustomerId());
        } else {
            Toast.makeText(MyWallet.this, "You are not logged in please login", Toast.LENGTH_SHORT).show();
            Intent i = new_added Intent(getApplicationContext(), LoginActivity_Nav.class);
            startActivity(i);
            MyWallet.this.finish();
        }
        myWalletAsyncTaskObj = new_added myWalletAsyncTask().execute(mRetailerBean.getCustomerId());
*/



        context = this;

        amountTv = (TextView) findViewById(R.id.amount);

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        final RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);




        Toolbar toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
        setSupportActionBar(toolbar);


        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWallet.this.finish();
            }
        });

        ((ImageView) toolbar.findViewById(R.id.nav_home_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWallet.this.finish();
            }
        });




        urlRemote = Constant.BASE_URL_MY_WALLET+"?CustomerId="+mRetailerBean.getCustomerId();

        ((TextView) findViewById(R.id.name)).setText(""+mRetailerBean.getName());






        ((ImageView) toolbar.findViewById(R.id.nav_refresh_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callAquery();

            }


        });



        ((ImageView) toolbar.findViewById(R.id.my_order_more_iv)).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                View menuItemView = findViewById(R.id.my_order_more_iv);
                PopupMenu popup = new PopupMenu(MyWallet.this, menuItemView);
                MenuInflater inflate = popup.getMenuInflater();


//                inflate.inflate(R.menu.my_order_option_menu, popup.getMenu());
                inflate.inflate(R.menu.home, popup.getMenu());


                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_my_orders) {
                            startActivity(new Intent(MyWallet.this, MyOrders.class));
                            return true;
                        } else if (id == R.id.action_my_profile) {
                            startActivity(new Intent(MyWallet.this, MyProfile.class).putExtra("showButton", false));
                            return true;
                        }else if (id == R.id.action_my_wallet) {
                            startActivity(new Intent(MyWallet.this, MyWallet.class));
                            return true;
                        }
                        else if (id == R.id.action_my_gullak) {
                            startActivity(new Intent(MyWallet.this, MyGullakActivity.class));
                            return true;
                        }

                        else if (id == R.id.action_contact_us) {
                            startActivity(new Intent(MyWallet.this, ActivationActivity.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_cart) {
                            startActivity(new Intent(MyWallet.this, CartActivity.class));
                            return true;
                        } else if (id == R.id.action_request_item) {
                            startActivity(new Intent(MyWallet.this, RequestBrandActivity.class));
                            return true;
                        } else if (id == R.id.action_feedback) {
                            startActivity(new Intent(MyWallet.this, FeedbackActivity.class));
                            return true;


                        }





                        else if (id == R.id.action_milestone) {
                            startActivity(new Intent(MyWallet.this, MilestoneActivity.class));
                            return true;


                        }



                        else if (id == R.id.action_my_rewards) {
                            startActivity(new Intent(MyWallet.this, RewardItemActivity.class));
                            return true;


                        }



                        else if (id == R.id.action_request_service) {
                            startActivity(new Intent(MyWallet.this, RequestServiceActivity.class));
                            return true;


                        }



                        else if (id == R.id.action_my_fav) {

                            startActivity(new Intent(MyWallet.this, MyFavourite.class));

                            return true;


                        }



                        else if (id == R.id.action_mynews) {

                            startActivity(new Intent(MyWallet.this, MyNews.class));

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





                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();



                            ComplexPreferences mRetailerBeanPref2 = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);


                            mRetailerBeanPref2.clear();
                            mRetailerBeanPref2.commit();
                            ComplexPreferences mRetailerBeanPref3 = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.POPULAR_BRANDS_PREF, MODE_PRIVATE);
                            mRetailerBeanPref3.clear();
                            mRetailerBeanPref3.commit();
                            ComplexPreferences mRetailerBeanPref4 = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.SUB_SUB_CAT_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref4.clear();
                            mRetailerBeanPref4.commit();
                            ComplexPreferences mRetailerBeanPref5 = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.POPULAR_BRANDS_PREF1, MODE_PRIVATE);
                            mRetailerBeanPref5.clear();
                            mRetailerBeanPref5.commit();
                            ComplexPreferences mRetailerBeanPref6 = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.POPULAR_BRANDS_PREF2, MODE_PRIVATE);
                            mRetailerBeanPref6.clear();
                            mRetailerBeanPref6.commit();
                            ComplexPreferences mRetailerBeanPref7 = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.APP_PROMOTION_PREF, MODE_PRIVATE);
                            mRetailerBeanPref7.clear();
                            mRetailerBeanPref7.commit();
                            ComplexPreferences mRetailerBeanPref8 = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.NEWLY_ADDED_BRANDS_PREF, MODE_PRIVATE);
                            mRetailerBeanPref8.clear();
                            mRetailerBeanPref8.commit();
                            ComplexPreferences mRetailerBeanPref9 = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.ALL_TOP_ADDED_PREF, MODE_PRIVATE);
                            mRetailerBeanPref9.clear();
                            mRetailerBeanPref9.commit();
                            ComplexPreferences mRetailerBeanPref10 = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.TODAY_DHAMAKA_PREF, MODE_PRIVATE);
                            mRetailerBeanPref10.clear();
                            mRetailerBeanPref10.commit();
                            ComplexPreferences mRetailerBeanPref11 = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.EMPTY_STOCK_PREF, MODE_PRIVATE);
                            mRetailerBeanPref11.clear();
                            mRetailerBeanPref11.commit();
                            ComplexPreferences mRetailerBeanPref12 = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.BULK_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref12.clear();
                            mRetailerBeanPref12.commit();
                            ComplexPreferences mRetailerBeanPref13 = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.MOST_SELLED_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref13.clear();
                            mRetailerBeanPref13.commit();

                            clearCartData();

                            Utility.setStringSharedPreference(MyWallet.this, "ItemQJson", "");

                            Utility.setStringSharedPreference(MyWallet.this, "CompanyId", "");



//                            Utility.setStringSharedPreference(HomeActivity.this, "ItemFavJson", "");


                            SharedPreferences sharedPreferences = getSharedPreferences("dialcount", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear().commit();

                            MyWallet.this.finish();

                            startActivity(new Intent(MyWallet.this, LoginActivity_Nav.class));


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
                @SuppressLint("RestrictedApi") MenuPopupHelper menuHelper = new MenuPopupHelper(MyWallet.this, (MenuBuilder) popup.getMenu(), menuItemView);
                menuHelper.setForceShowIcon(true);
                menuHelper.show();

            }
        });

        callAquery();




    }

    /*@Override
    protected void onPause() {
        if (myWalletAsyncTaskObj != null && !myWalletAsyncTaskObj.isCancelled())
            myWalletAsyncTaskObj.cancel(true);
        super.onPause();

    }*/

    public class myWalletAsyncTask extends AsyncTask<String, Void, JSONObject> {
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(MyWallet.this);
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
            jsonObjFromurl = new HttpUrlConnectionJSONParser().getJsonObjectFromHttpUrlConnection(Constant.BASE_URL_MY_WALLET + "?CustomerId=" + params[0], null, HttpUrlConnectionJSONParser.Http.GET);
            return jsonObjFromurl;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (jsonObject != null) {
                try {
                    if (jsonObject.has("wallet")) {
                        JSONObject mWalletObj = jsonObject.getJSONObject("wallet");

                        String Id = isNullOrEmpty(mWalletObj, "Id");
                        String CustomerId = isNullOrEmpty(mWalletObj, "CustomerId");
                        String TransactionDate = isNullOrEmpty(mWalletObj, "TransactionDate");
                        String TotalAmount = isNullOrEmpty(mWalletObj, "TotalAmount");
                        String CreatedDate = isNullOrEmpty(mWalletObj, "CreatedDate");
                        String UpdatedDate = isNullOrEmpty(mWalletObj, "UpdatedDate");
                        String Deleted = isNullOrEmpty(mWalletObj, "Deleted");
                        String CreditAmount = isNullOrEmpty(mWalletObj, "CreditAmount");
                        String DebitAmount = isNullOrEmpty(mWalletObj, "DebitAmount");

                        tvWalletAmount.setText(TotalAmount);
                    } else {
                        Toast.makeText(MyWallet.this, "Wallet Info not available please try again", Toast.LENGTH_SHORT).show();
                        MyWallet.this.finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(MyWallet.this, "Server not responding properly", Toast.LENGTH_SHORT).show();
            }
            mDialog.dismiss();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            mDialog.dismiss();
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
                Log.e("MyWallet", key + " is not available which should come in response");
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }









    public void callAquery() {

        showLoading();
        new AQuery(getApplicationContext()).ajax(urlRemote, null, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {

                System.out.println("Walet json:::"+json);

                if (json == null) {


                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }

                    Toast.makeText(MyWallet.this, "Please try again", Toast.LENGTH_SHORT).show();


                } else {

                    //   Toast.makeText(MyWallet.this, ""+ json.toString(), Toast.LENGTH_SHORT).show();
                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }

                    try {

                        JSONObject  jsonObject = json.getJSONObject("wallet");

                        JSONObject rewardJson = json.getJSONObject("reward");

                        String amount = jsonObject.getString("TotalAmount");

                        amountTv.setText("Total Amount : "+amount);

                /*        ((TextView) findViewById(R.id.credit)).setText("Credit "+jsonObject.getString("CreditAmount"));


                        ((TextView) findViewById(R.id.debit)).setText("Debit "+jsonObject.getString("DebitAmount"));
*/
                        ((TextView) findViewById(R.id.total_point)).setText("Total point : "+rewardJson.getString("TotalPoint"));

                        ((TextView) findViewById(R.id.earning_point)).setText("Earning Point : "+rewardJson.getString("EarningPoint"));

                        ((TextView) findViewById(R.id.used_point)).setText("Used Point : "+rewardJson.getString("UsedPoint"));

                        ((TextView) findViewById(R.id.milestone_point)).setText("Milestone Point : "+rewardJson.getString("MilestonePoint"));




                    } catch (JSONException e) {
                        e.printStackTrace();

                        Toast.makeText(MyWallet.this, "Error Please try again", Toast.LENGTH_SHORT).show();

                    }

                    //   Toast.makeText(SkCodeActivity.this, json.toString(), Toast.LENGTH_SHORT).show();

                    //     Toast.makeText(MyWallet.this, json.toString(), Toast.LENGTH_SHORT).show();



                }


            }
        });


    }









    public void showLoading() {


        mDialog = new Dialog(MyWallet.this);
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




    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }



}