package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONException;
import org.json.JSONObject;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.PlaceAutocompleteAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.databinding.ActivityRegistrationBinding;

/**
 * Created by Krishna on 12/20/2016.
 */

public class RegistrationActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {



    AsyncTask<String, Void, JSONObject> mRegistrationTask;
    public static final String TAG = "Registration Activity";

    ActivityRegistrationBinding mActivityRegistrationBinding;

    protected GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter mAdapter;

    private static final LatLngBounds BOUNDS_GREATER_INDIA = new LatLngBounds(new LatLng(23.63936, 68.14712), new LatLng(28.20453, 97.34466));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        mActivityRegistrationBinding = DataBindingUtil.setContentView(this, R.layout.activity_registration);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();
        mActivityRegistrationBinding.activityRegistrationEdtLocality.setOnItemClickListener(mAutocompleteClickListener);
        mAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, BOUNDS_GREATER_INDIA, null);
        mActivityRegistrationBinding.activityRegistrationEdtLocality.setAdapter(mAdapter);

        mActivityRegistrationBinding.activityRegistrationBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String location = mActivityRegistrationBinding.activityRegistrationEdtLocality.getText().toString().trim();
                if (TextUtils.isNullOrEmpty(mActivityRegistrationBinding.activityRegistrationEdtMobile.getText().toString().trim())) {

                    mActivityRegistrationBinding.inputLayoutMob.setError("Enter mobile number");

                    if (!TextUtils.isNullOrEmpty(mActivityRegistrationBinding.activityRegistrationEdtLocality.getText().toString().trim())) {

                        mActivityRegistrationBinding.inputLayoutSelectLocality.setError(null);

                    }



                } else if (!TextUtils.isValidMobileNo(mActivityRegistrationBinding.activityRegistrationEdtMobile.getText().toString().trim())) {

                    mActivityRegistrationBinding.inputLayoutMob.setError("Enter valid mobile number");

                    if (!TextUtils.isNullOrEmpty(mActivityRegistrationBinding.activityRegistrationEdtLocality.getText().toString().trim())) {

                        mActivityRegistrationBinding.inputLayoutSelectLocality.setError(null);
                    }

                }



                else if (TextUtils.isNullOrEmpty(mActivityRegistrationBinding.activityRegistrationEdtPass.getText().toString().trim())) {

                    mActivityRegistrationBinding.inputLayoutPass.setError("Enter password");


                    mActivityRegistrationBinding.inputLayoutMob.setError(null);


                    if (!TextUtils.isNullOrEmpty(mActivityRegistrationBinding.activityRegistrationEdtLocality.getText().toString().trim())) {

                        mActivityRegistrationBinding.inputLayoutSelectLocality.setError(null);
                    }

                }



                else if (mActivityRegistrationBinding.activityRegistrationEdtPass.getText().toString().trim().length() < 6) {

                    mActivityRegistrationBinding.inputLayoutPass.setError("Please use more than 6 characters!");


                    mActivityRegistrationBinding.inputLayoutMob.setError(null);


                    if (!TextUtils.isNullOrEmpty(mActivityRegistrationBinding.activityRegistrationEdtLocality.getText().toString().trim())) {

                        mActivityRegistrationBinding.inputLayoutSelectLocality.setError(null);
                    }



                }





                else if (TextUtils.isNullOrEmpty(mActivityRegistrationBinding.activityRegistrationEdtLocality.getText().toString().trim())) {

                    mActivityRegistrationBinding.inputLayoutSelectLocality.setError("Enter location");



                    mActivityRegistrationBinding.inputLayoutPass.setError(null);
                    mActivityRegistrationBinding.inputLayoutMob.setError(null);


                }


                else if(!Utility.isConnectingToInternet(RegistrationActivity.this)) {

                    Toast.makeText(RegistrationActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();


                }


                //Indore, Madhya Pradesh, India

                // || !location.toLowerCase().trim().equals("indore")
              /*  else if(!location.equalsIgnoreCase("Indore, Madhya Pradesh, India")
                        &&

                        !location.equalsIgnoreCase("indore")
                        ) {
                    Toast.makeText(RegistrationActivity.this, "Services coming soon in this city", Toast.LENGTH_SHORT).show();
                }
              */

                else if(!location.toLowerCase().contains("indore")) {
                    Toast.makeText(RegistrationActivity.this, "Services coming soon in this city", Toast.LENGTH_SHORT).show();
                }

             /*   else if(!location.equalsIgnoreCase("indore")) {
                    Toast.makeText(RegistrationActivity.this, "Services coming soon in this city", Toast.LENGTH_SHORT).show();
                }
*/


                else {
                    String mobile = mActivityRegistrationBinding.activityRegistrationEdtMobile.getText().toString().trim();
                    String password = mActivityRegistrationBinding.activityRegistrationEdtPass.getText().toString().trim();

// Calling registration api
                         mRegistrationTask = new RegistrationTask().execute(location, location, location, mobile, password);


//                    Toast.makeText(RegistrationActivity.this, mobile+"\n"+password+"\n"+location, Toast.LENGTH_SHORT).show();



                    Log.e("location", location);



                }
            }
        });


    }

    @Override
    protected void onPause() {
        if (mRegistrationTask != null && !mRegistrationTask.isCancelled()) {
            mRegistrationTask.cancel(true);
        }
        super.onPause();
    }

    public class RegistrationTask extends AsyncTask<String, Void, JSONObject> {
        /*Dialog mDialog;

        @Override
        protected void onPreExecute() {
            mDialog = new_added Dialog(RegistrationActivity.this);
            mDialog.getWindow().setBackgroundDrawable(new_added ColorDrawable(Color.WHITE));
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Registering please wait...");
            mDialog.setCancelable(false);
            mDialog.show();
        }*/
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(RegistrationActivity.this);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Requesting Brand please wait...");
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
                mRequestParamJson.put("Address", params[0]);
                mRequestParamJson.put("BAGPSCoordinates", params[1]);
                mRequestParamJson.put("BillingAddress", params[2]);
                mRequestParamJson.put("Mobile", params[3]);
                mRequestParamJson.put("Password", params[4]);
                System.out.println("regcheck:::"+mRequestParamJson);

                jsonObjectFromUrl = new HttpUrlConnectionJSONParser().getJsonFromHttpUrlConnectionJsonRequest_Post(Constant.BASE_URL_SIGNUP, mRequestParamJson);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObjectFromUrl;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            System.out.println("jsonObject:::"+jsonObject);
            if (jsonObject != null) {
                try {
                    if (TextUtils.isNullOrEmpty(jsonObject.getString("CustomerId"))) {
                        Toast.makeText(RegistrationActivity.this, "Improper response from server", Toast.LENGTH_SHORT).show();
                    } else if ((jsonObject.getString("CustomerId").toString().equalsIgnoreCase("0"))) {
                        Toast.makeText(RegistrationActivity.this, "Improper response from server", Toast.LENGTH_SHORT).show();
                    } else {
                        String customerId = jsonObject.getString("CustomerId");
                        String customerCategoryId = isNullOrEmpty(jsonObject.getString("CustomerCategoryId"));
                        String Skcode = isNullOrEmpty(jsonObject.getString("Skcode"));
                        String ShopName = isNullOrEmpty(jsonObject.getString("ShopName"));
                        String dob = isNullOrEmpty(jsonObject.getString("DOB"));
                        String Warehouseid = isNullOrEmpty(jsonObject.getString("Warehouseid"));
                        String Mobile = isNullOrEmpty(jsonObject.getString("Mobile"));
                        String WarehouseName = isNullOrEmpty(jsonObject.getString("WarehouseName"));
                        String Name = isNullOrEmpty(jsonObject.getString("Name"));
                        String Password = isNullOrEmpty(jsonObject.getString("Password"));
                        String Description = isNullOrEmpty(jsonObject.getString("Description"));
                        String CustomerType = isNullOrEmpty(jsonObject.getString("CustomerType"));
                        String CustomerCategoryName = isNullOrEmpty(jsonObject.getString("CustomerCategoryName"));
                        String CompanyId = isNullOrEmpty(jsonObject.getString("CompanyId"));
                        String BillingAddress = isNullOrEmpty(jsonObject.getString("BillingAddress"));
                        String TypeOfBuissness = isNullOrEmpty(jsonObject.getString("TypeOfBuissness"));
                        String ShippingAddress = isNullOrEmpty(jsonObject.getString("ShippingAddress"));
                        String LandMark = isNullOrEmpty(jsonObject.getString("LandMark"));
                        String Country = isNullOrEmpty(jsonObject.getString("Country"));
                        String State = isNullOrEmpty(jsonObject.getString("State"));
                        String Cityid = isNullOrEmpty(jsonObject.getString("Cityid"));
                        String City = isNullOrEmpty(jsonObject.getString("City"));
                        String ZipCode = isNullOrEmpty(jsonObject.getString("ZipCode"));
                        String BAGPSCoordinates = isNullOrEmpty(jsonObject.getString("BAGPSCoordinates"));
                        String SAGPSCoordinates = isNullOrEmpty(jsonObject.getString("SAGPSCoordinates"));
                        String RefNo = isNullOrEmpty(jsonObject.getString("RefNo"));
                        String OfficePhone = isNullOrEmpty(jsonObject.getString("OfficePhone"));
                        String Emailid = isNullOrEmpty(jsonObject.getString("Emailid"));
                        String Familymember = isNullOrEmpty(jsonObject.getString("Familymember"));
                        String CreatedBy = isNullOrEmpty(jsonObject.getString("CreatedBy"));
                        String LastModifiedBy = isNullOrEmpty(jsonObject.getString("LastModifiedBy"));
                        String CreatedDate = isNullOrEmpty(jsonObject.getString("CreatedDate"));
                        String UpdatedDate = isNullOrEmpty(jsonObject.getString("UpdatedDate"));
                        String imei = isNullOrEmpty(jsonObject.getString("imei"));
                        String MonthlyTurnOver = isNullOrEmpty(jsonObject.getString("MonthlyTurnOver"));
                        String ExecutiveId = isNullOrEmpty(jsonObject.getString("ExecutiveId"));
                        String SizeOfShop = isNullOrEmpty(jsonObject.getString("SizeOfShop"));
                        String Rating = isNullOrEmpty(jsonObject.getString("Rating"));
                        String ClusterId = isNullOrEmpty(jsonObject.getString("ClusterId"));
                        String Deleted = isNullOrEmpty(jsonObject.getString("Deleted"));
                        String Active = isNullOrEmpty(jsonObject.getString("Active"));
                        String GroupNotification = isNullOrEmpty(jsonObject.getString("GroupNotification"));
                        String Notifications = isNullOrEmpty(jsonObject.getString("Notifications"));
                        String Exception = isNullOrEmpty(jsonObject.getString("Exception"));
                        String DivisionId = isNullOrEmpty(jsonObject.getString("DivisionId"));
                        String Rewardspoints = isNullOrEmpty(jsonObject.getString("Rewardspoints"));

                        Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                        if (Active.equalsIgnoreCase("true")) {
                            RetailerBean mRetailerBean = new RetailerBean(customerId, customerCategoryId, Skcode, ShopName, Warehouseid, Mobile, WarehouseName, Name, Password, Description, CustomerType, CustomerCategoryName, CompanyId, BillingAddress, TypeOfBuissness, ShippingAddress, LandMark, Country, State, Cityid, City, ZipCode, BAGPSCoordinates, SAGPSCoordinates, RefNo, OfficePhone, Emailid, Familymember, CreatedBy, LastModifiedBy, CreatedDate, UpdatedDate, imei, MonthlyTurnOver, ExecutiveId, SizeOfShop, Rating, ClusterId, Deleted, Active, GroupNotification, Notifications, Exception, DivisionId, Rewardspoints,dob);

                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(RegistrationActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.putObject(Constant.RETAILER_BEAN_PREF_OBJ, mRetailerBean);
                            mRetailerBeanPref.commit();

                            startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
                            RegistrationActivity.this.finish();
                        } else {
                            startActivity(new Intent(RegistrationActivity.this, ActivationActivity.class));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(RegistrationActivity.this, "Improper response from server", Toast.LENGTH_SHORT).show();
            }
            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
            }
        }
    }

    private String isNullOrEmpty(String value) throws JSONException {
        if (TextUtils.isNullOrEmpty(value)) {
            return "";
        } else {
            return value;
        }
    }


    /**
     * Listener that handles selections from suggestions from the AutoCompleteTextView that
     * displays Place suggestions.
     * Gets the place id of the selected item and issues a request to the Places Geo Data API
     * to retrieve more details about the place.
     *
     * @see com.google.android.gms.location.places.GeoDataApi#getPlaceById(com.google.android.gms.common.api.GoogleApiClient,
     * String...)
     */
    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            Log.i(TAG, "Autocomplete item selected: " + primaryText);

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
             details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

            Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
        }
    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);

            /*// Format details of the place for display and show it in a TextView.
            mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                    place.getId(), place.getAddress(), place.getPhoneNumber(),
                    place.getWebsiteUri()));

            // Display the third party attributions if set.
            final CharSequence thirdPartyAttribution = places.getAttributions();
            if (thirdPartyAttribution == null) {
                mPlaceDetailsAttribution.setVisibility(View.GONE);
            } else {
                mPlaceDetailsAttribution.setVisibility(View.VISIBLE);
                mPlaceDetailsAttribution.setText(Html.fromHtml(thirdPartyAttribution.toString()));
            }*/

            Log.i(TAG, "Place details received: " + place.getName());

            places.release();
        }
    };

    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }

    /**
     * Called when the Activity could not connect to Google Play services and the auto manager
     * could resolve the error automatically.
     * In this case the API is not available and notify the user.
     *
     * @param connectionResult can be inspected to determine the cause of the failure
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(this,
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }



}