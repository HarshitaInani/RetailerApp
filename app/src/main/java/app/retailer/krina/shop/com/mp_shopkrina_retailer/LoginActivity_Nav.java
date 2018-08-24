package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.BaseCatSubCatBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;

public class LoginActivity_Nav extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText etMobileNumber, etPass;
    TextInputLayout tilMobileNo, tilPass;
    Button btnLogin, btnSignUp, btnForgetPass;
    AsyncTask<String, Void, JSONObject> mLoginTask;
    AsyncTask<String, Void, JSONObject> mForgetPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__nav);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setupTitelBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        drawer.setEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        getIds();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mobileNo = etMobileNumber.getText().toString().trim();
                String pass = etPass.getText().toString().trim();

                if (TextUtils.isNullOrEmpty(mobileNo)) {
                    tilMobileNo.setError("Enter mobile number");
                }
                else if (!TextUtils.isValidMobileNo(mobileNo)) {

                    tilMobileNo.setError("Invalid mobile number");
                }


                else if (TextUtils.isNullOrEmpty(pass)) {
                    tilPass.setError("Password should not be empty");
                    tilMobileNo.setError(null);

                }

                else if(!Utility.isConnectingToInternet(LoginActivity_Nav.this)) {
                    Toast.makeText(LoginActivity_Nav.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();
                }



                else {
                    ComplexPreferences mComplexPreferences = ComplexPreferences.getComplexPreferences(LoginActivity_Nav.this, Constant.FCM_KEY_PREF, MODE_PRIVATE);
                    String fcmToken = mComplexPreferences.getObject(Constant.FCM_KEY_PREF_OBJ, String.class);
                    mLoginTask = new LoginTask().execute(mobileNo, pass, fcmToken == null ? "" : fcmToken);
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity_Nav.this, RegistrationActivity.class));
            }
        });
        btnForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity_Nav.this);
                builder.setTitle("Enter Mobile Number");

                final EditText input = new EditText(LoginActivity_Nav.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_PHONE);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (TextUtils.isValidMobileNo(input.getText().toString().trim()))
                            mForgetPass = new ForgetPassTask().execute(input.getText().toString().trim());
                        else {
                            input.setError("Enter Valid Mobile Number");
                            Toast.makeText(LoginActivity_Nav.this, "Enter valid mobile number", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
   /*     if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
   */


        finishAffinity();

    }

    @Override
    protected void onPause() {
        if (mLoginTask != null && !mLoginTask.isCancelled()) {
            mLoginTask.cancel(true);
        }
        if (mForgetPass != null && !mForgetPass.isCancelled()) {
            mForgetPass.cancel(true);
        }
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_activity__nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        int id = item.getItemId();

        if (id == R.id.rateUs) {

        } else if (id == R.id.my_offer) {

        } else if (id == R.id.rewards_point) {

        } else if (id == R.id.terms_conditions) {}
        Toast.makeText(LoginActivity_Nav.this, "Coming soon..", Toast.LENGTH_SHORT).show();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupTitelBar(Toolbar toolbar) {
        toolbar.setTitle("Sign In");
    }

    private void getIds() {
        btnLogin = (Button) findViewById(R.id.activity_login_btn_signin);
        btnSignUp = (Button) findViewById(R.id.activity_login_btn_signup);
        btnForgetPass = (Button) findViewById(R.id.activity_login_btn_forget_pass);
        etMobileNumber = (EditText) findViewById(R.id.activity_login_edt_mobile);
        etPass = (EditText) findViewById(R.id.activity_login_edt_pass);
        tilMobileNo = (TextInputLayout) findViewById(R.id.input_layout_mob);
        tilPass = (TextInputLayout) findViewById(R.id.input_layout_pass);



       // etMobileNumber.setText("9826755872");
      //  etPass.setText("123456");

    }

    public class ForgetPassTask extends AsyncTask<String, Void, JSONObject> {
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(LoginActivity_Nav.this);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Requesting for password please wait...");
            ImageView la = (ImageView) mDialog.findViewById(R.id.gridprogressBar);
            la.setBackgroundResource(R.drawable.custom_progress_dialog_animation);
            animation = (AnimationDrawable) la.getBackground();
            animation.start();
            mDialog.setCancelable(false);
            mDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObjectFromUrl = null;
            try {
                jsonObjectFromUrl = new HttpUrlConnectionJSONParser().getJsonObjectFromHttpUrlConnection(Constant.BASE_URL_FORGETPASS + "?Mobile=" + params[0], null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObjectFromUrl;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            new AlertDialog.Builder(LoginActivity_Nav.this)
                    .setTitle("Forget Password")
                    .setMessage("Forget password request sent successfully, you will receive your password on your mobile number")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(R.mipmap.ic_launcher)
                    .show();
            /*if (jsonObject != null) {

            } else {
                Toast.makeText(LoginActivity_Nav.this, "Improper response from server", Toast.LENGTH_SHORT).show();
            }*/
            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
            }
        }
    }

    public class LoginTask extends AsyncTask<String, Void, JSONObject> {
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(LoginActivity_Nav.this);
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

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObjectFromUrl = null;
            try {
                JSONObject mRequestParamJson = new JSONObject();
                mRequestParamJson.put("Mobile", params[0]);
                mRequestParamJson.put("Password", params[1]);
                mRequestParamJson.put("fcmId", params[2]);

                jsonObjectFromUrl = new HttpUrlConnectionJSONParser().getJsonFromHttpUrlConnectionJsonRequest_Post(Constant.BASE_URL_LOGIN, mRequestParamJson);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObjectFromUrl;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            Log.d("Login Response::", jsonObject.toString()+"");
            if (jsonObject != null) {
                try {
                    if (TextUtils.isNullOrEmpty(jsonObject.getString("CustomerId"))) {
                        Toast.makeText(LoginActivity_Nav.this, "Wrong id or password", Toast.LENGTH_SHORT).show();
                    } else if ((jsonObject.getString("CustomerId").toString().equalsIgnoreCase("0"))) {
                        Toast.makeText(LoginActivity_Nav.this, "Wrong id or password", Toast.LENGTH_SHORT).show();
                    } else {
                        String customerId = jsonObject.getString("CustomerId");
                        String customerCategoryId = isNullOrEmpty(jsonObject, "CustomerCategoryId");
                        String Skcode = isNullOrEmpty(jsonObject, "Skcode");
                        String ShopName = isNullOrEmpty(jsonObject, "ShopName");
                        String dob = isNullOrEmpty(jsonObject, "DOB");
                        String Warehouseid = isNullOrEmpty(jsonObject, "Warehouseid");
                        System.out.println("WID:::"+Warehouseid);
                        String Mobile = isNullOrEmpty(jsonObject, "Mobile");
                        String WarehouseName = isNullOrEmpty(jsonObject, "WarehouseName");
                        String Name = isNullOrEmpty(jsonObject, "Name");
                        String Password = isNullOrEmpty(jsonObject, "Password");
                        String Description = isNullOrEmpty(jsonObject, "Description");
                        String CustomerType = isNullOrEmpty(jsonObject, "CustomerType");
                        String CustomerCategoryName = isNullOrEmpty(jsonObject, "CustomerCategoryName");
                        String CompanyId = isNullOrEmpty(jsonObject, "CompanyId");
                        String BillingAddress = isNullOrEmpty(jsonObject, "BillingAddress");
                        String TypeOfBuissness = isNullOrEmpty(jsonObject, "TypeOfBuissness");
                        String ShippingAddress = isNullOrEmpty(jsonObject, "ShippingAddress");
                        String LandMark = isNullOrEmpty(jsonObject, "LandMark");
                        String Country = isNullOrEmpty(jsonObject, "Country");
                        String State = isNullOrEmpty(jsonObject, "State");
                        String Cityid = isNullOrEmpty(jsonObject, "Cityid");
                        String City = isNullOrEmpty(jsonObject, "City");
                        String ZipCode = isNullOrEmpty(jsonObject, "ZipCode");
                        String BAGPSCoordinates = isNullOrEmpty(jsonObject, "BAGPSCoordinates");
                        String SAGPSCoordinates = isNullOrEmpty(jsonObject, "SAGPSCoordinates");
                        String RefNo = isNullOrEmpty(jsonObject, "RefNo");
                        String OfficePhone = isNullOrEmpty(jsonObject, "OfficePhone");
                        String Emailid = isNullOrEmpty(jsonObject, "Emailid");
                        String Familymember = isNullOrEmpty(jsonObject, "Familymember");
                        String CreatedBy = isNullOrEmpty(jsonObject, "CreatedBy");
                        String LastModifiedBy = isNullOrEmpty(jsonObject, "LastModifiedBy");
                        String CreatedDate = isNullOrEmpty(jsonObject, "CreatedDate");
                        String UpdatedDate = isNullOrEmpty(jsonObject, "UpdatedDate");
                        String imei = isNullOrEmpty(jsonObject, "imei");
                        String MonthlyTurnOver = isNullOrEmpty(jsonObject, "MonthlyTurnOver");
                        String ExecutiveId = isNullOrEmpty(jsonObject, "ExecutiveId");
                        String SizeOfShop = isNullOrEmpty(jsonObject, "SizeOfShop");
                        String Rating = isNullOrEmpty(jsonObject, "Rating");
                        String ClusterId = isNullOrEmpty(jsonObject, "ClusterId");
                        String Deleted = isNullOrEmpty(jsonObject, "Deleted");
                        String Active = isNullOrEmpty(jsonObject, "Active");
                        String GroupNotification = isNullOrEmpty(jsonObject, "GroupNotification");
                        String Notifications = isNullOrEmpty(jsonObject, "Notifications");
                        String Exception = isNullOrEmpty(jsonObject, "Exception");
                        String DivisionId = isNullOrEmpty(jsonObject, "DivisionId");
                        String Rewardspoints = isNullOrEmpty(jsonObject, "Rewardspoints");
                        if (Active.equalsIgnoreCase("true")) {

                            Utility.setStringSharedPreference(LoginActivity_Nav.this, "MultiLaguage", "m");

                            RetailerBean mRetailerBean = new RetailerBean(customerId, customerCategoryId, Skcode, ShopName, Warehouseid, Mobile, WarehouseName, Name, Password, Description, CustomerType, CustomerCategoryName, CompanyId, BillingAddress, TypeOfBuissness, ShippingAddress, LandMark, Country, State, Cityid, City, ZipCode, BAGPSCoordinates, SAGPSCoordinates, RefNo, OfficePhone, Emailid, Familymember, CreatedBy, LastModifiedBy, CreatedDate, UpdatedDate, imei, MonthlyTurnOver, ExecutiveId, SizeOfShop, Rating, ClusterId, Deleted, Active, GroupNotification, Notifications, Exception, DivisionId, Rewardspoints,dob);

                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(LoginActivity_Nav.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.putObject(Constant.RETAILER_BEAN_PREF_OBJ, mRetailerBean);
                            mRetailerBeanPref.commit();

                            ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(LoginActivity_Nav.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);
                            BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);

                            Utility.setSharedPreferenceBoolean(LoginActivity_Nav.this, "APICALL", true);
                            Utility.setStringSharedPreference(LoginActivity_Nav.this, "CName", Name);
                            Utility.setStringSharedPreference(LoginActivity_Nav.this, "CId", customerId);


                            if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {
                                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(i);
                            } else {
                                startActivity(new Intent(LoginActivity_Nav.this, PreHomeActivity.class));
                                LoginActivity_Nav.this.finish();
                            }
                        } else {
                            startActivity(new Intent(LoginActivity_Nav.this, ActivationActivity.class));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(LoginActivity_Nav.this, "Improper response from server", Toast.LENGTH_SHORT).show();
            }
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
                Log.e("LoginActivity", key + " is not available which should come in response");
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
