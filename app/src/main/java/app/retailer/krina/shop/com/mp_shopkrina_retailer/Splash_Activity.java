package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.splunk.mint.Mint;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.AppVersionBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.BaseCatSubCatBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;


public class Splash_Activity extends AppCompatActivity {


    AsyncTask<String, Void, JSONArray> mCheckVersionTask;
    String projectToken = "df570aea7f333f2a866014f2bc62a7b0"; // e.g.: "1ef7e30d2a58d27f4b90c42e31d6d7ad"
   // MixpanelAPI mixpanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Mint.initAndStartSession(this.getApplication(), "43f98539");
        callSliderApi();
        Utility.setSharedPreferenceBoolean(Splash_Activity.this, "APICALL", true);
       // mixpanel = MixpanelAPI.getInstance(this, projectToken);
       // Utility.setStringSharedPreference(Splash_Activity.this, "MultiLaguage", "s");

        clearCartData();

        if (Utils.isInternetConnected(Splash_Activity.this)) {
            PackageInfo pInfo = null;
            try {
                pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                String version = pInfo.versionName;
                double App_version = Double.parseDouble(version);
                boolean isCompulsory = false;
                String createdDate = "";

                ComplexPreferences mComplexPreferences = ComplexPreferences.getComplexPreferences(Splash_Activity.this, Constant.APP_VERSION_PREF, MODE_PRIVATE);
                AppVersionBean mAppVersionBean = new AppVersionBean(App_version, isCompulsory, createdDate);
                mComplexPreferences.putObject(Constant.APP_VERSION_PREF_OBJ, mAppVersionBean);
                mComplexPreferences.commit();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }


            mCheckVersionTask = new CheckVersionTask().execute();

        } else {
            Toast.makeText(Splash_Activity.this, "Internet connection is not available", Toast.LENGTH_SHORT).show();
            ContinueToHomeScreen();
        }
    }

    public void onPause() {
        super.onPause();
        if (mCheckVersionTask != null && !mCheckVersionTask.isCancelled()) {
            mCheckVersionTask.cancel(true);
        }
        finish();
    }

    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(Splash_Activity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }


    public class CheckVersionTask extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
           /* mDialog = new_added Dialog(Splash_Activity.this);
            mDialog.getWindow().setBackgroundDrawable(new_added ColorDrawable(Color.WHITE));
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Logging in please wait...");
            mDialog.setCancelable(false);
            mDialog.show();*/
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArrayFromUrl = null;
            try {
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_APP_VERSION, null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayFromUrl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if (jsonArray != null) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length() - 1);
                    System.out.println("Json Response:::"+jsonObject);
                    if (jsonObject != null) {
                        double App_version = jsonObject.getDouble("App_version");
                        boolean isCompulsory = jsonObject.getBoolean("isCompulsory");
                        String createdDate = isNullOrEmpty(jsonObject, "createdDate");

                        ComplexPreferences mComplexPreferences = ComplexPreferences.getComplexPreferences(Splash_Activity.this, Constant.APP_VERSION_PREF, MODE_PRIVATE);
                        AppVersionBean mAppVersionBean = mComplexPreferences.getObject(Constant.APP_VERSION_PREF_OBJ, AppVersionBean.class);

                        if (mAppVersionBean == null) {
                            System.out.println("Resukt::::"+mAppVersionBean);
                            mAppVersionBean = new AppVersionBean(App_version, isCompulsory, createdDate);
                            mComplexPreferences.putObject(Constant.APP_VERSION_PREF_OBJ, mAppVersionBean);
                            mComplexPreferences.commit();
                            ContinueToHomeScreen();
                        } else {
                            System.out.println("Resukt::::122"+mAppVersionBean.getApp_version());
                            if (mAppVersionBean.getApp_version()==App_version) {
                                System.out.println("Resukt::::1111"+mAppVersionBean.getApp_version());
                                ContinueToHomeScreen();
                            } else if (isCompulsory) {

                                System.out.println("Run:::::::::");
                                @SuppressLint("RestrictedApi") AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(Splash_Activity.this, android.support.v7.appcompat.R.style.Base_Theme_AppCompat_Dialog));

                                alertDialogBuilder.setTitle(getString(R.string.youAreNotUpdatedTitle));
                                alertDialogBuilder.setMessage(getString(R.string.youAreNotUpdatedMessage) + " " + App_version + getString(R.string.youAreNotUpdatedMessage1));
                                alertDialogBuilder.setCancelable(false);
                                alertDialogBuilder.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
//                                        startActivity(new_added Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=app.retailer.krina.shop.com.shopkrina_retailer")));

                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=app.retailer.krina.shop.com.shopkrina_retailer&hl=en")));
                                     //   startActivity(new_added Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=app.retailer.krina.shop.com.shopkrina_retailer&hl=en")));

                                        dialog.cancel();
                                    }
                                });
                                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        dialog.cancel();
                                        Splash_Activity.this.finish();
                                      //  ContinueToHomeScreen();

                                    }
                                });
                                alertDialogBuilder.show();
                            } else {
                                @SuppressLint("RestrictedApi") AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(Splash_Activity.this, android.support.v7.appcompat.R.style.Base_Theme_AppCompat_Dialog));

                                alertDialogBuilder.setTitle(getString(R.string.youAreNotUpdatedTitle));
                                alertDialogBuilder.setMessage(getString(R.string.youAreNotUpdatedMessage) + " " + App_version + getString(R.string.youAreNotUpdatedMessage1));
                                alertDialogBuilder.setCancelable(false);
                                alertDialogBuilder.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
//                                        startActivity(new_added Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=app.retailer.krina.shop.com.shopkrina_retailer")));

                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=app.retailer.krina.shop.com.shopkrina_retailer&hl=en")));

                                        dialog.cancel();
                                    }
                                });
                                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {

                                       // ContinueToHomeScreen();
                                        dialog.cancel();
                                        finish();
                                    }
                                });
                                alertDialogBuilder.show();
                            }
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(Splash_Activity.this, "Improper response from server", Toast.LENGTH_SHORT).show();
                ContinueToHomeScreen();
            }
        }
    }

    private void ContinueToHomeScreen() {

        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(Splash_Activity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        if (mRetailerBean != null && !mRetailerBean.getCustomerId().equalsIgnoreCase("0")) {
            if (mRetailerBean.getActive().equalsIgnoreCase("false")) {
                Intent i = new Intent(getApplicationContext(), ActivationActivity.class);
                startActivity(i);
            } else {
                ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(Splash_Activity.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);
                BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);
                if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {
                    try {
                        JSONObject props = new JSONObject();
                        props.put("AppStartedBy: ", mRetailerBean.getShopName());
                        props.put("ShopSKUCode", mRetailerBean.getSkcode());
                     //   mixpanel.track("App Started By: " + mRetailerBean.getSkcode(), props);
                    } catch (JSONException e) {
                        Log.e("ShopKirana", "Unable to add properties to JSONObject for mixpanel", e);
                    }
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(getApplicationContext(), PreHomeActivity.class);
                    startActivity(i);
                }
            }
        } else {
            Intent i = new Intent(getApplicationContext(), LoginActivity_Nav.class);
            startActivity(i);
        }
        Splash_Activity.this.finish();
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
                Log.e("LoginActivity", key + " is not available which should come in response");
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void callSliderApi() {
        new AQuery(getApplicationContext()).ajax(Constant.BASE_URL_Slider_API, null, JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray json, AjaxStatus status) {
                Log.d("Slide::",Constant.BASE_URL_Slider_API.toString());
                if (json == null) {
                    Toast.makeText(Splash_Activity.this, "Slider : Please try again", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("Slide::"+json.toString());
                    Utility.setStringSharedPreference(Splash_Activity.this, "Sliderjson", json.toString());
                }
            }
        });

    }
}
