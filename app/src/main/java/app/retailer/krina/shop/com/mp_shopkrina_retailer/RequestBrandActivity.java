package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;

import org.json.JSONObject;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;

/**
 * Created by Krishna on 18-02-2017.
 */

public class RequestBrandActivity extends AppCompatActivity {
    AsyncTask<String, Void, JSONObject> mStringVoidJSONObjectAsyncTask;
    EditText mBrandName;
    Button mSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requestbrandactivity);


        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBrandName = (EditText) findViewById(R.id.brand_name);
        mSubmit = (Button) findViewById(R.id.submit);
        ((ImageView) toolbar.findViewById(R.id.my_order_detail_nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestBrandActivity.this.finish();
            }
        });

        ((ImageView) toolbar.findViewById(R.id.my_order_detail_home_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestBrandActivity.this.finish();
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBrandName.getText().toString().toString().isEmpty()) {
                    mBrandName.setError("Enter Brand Name");
                } else {
                    ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(RequestBrandActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                    RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
                    mStringVoidJSONObjectAsyncTask = new RequestBrand().execute(mRetailerBean.getCustomerId(), mRetailerBean.getMobile(), mBrandName.getText().toString().trim());
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mStringVoidJSONObjectAsyncTask != null && !mStringVoidJSONObjectAsyncTask.isCancelled()) {
            mStringVoidJSONObjectAsyncTask.cancel(true);
        }
    }

    public class RequestBrand extends AsyncTask<String, Void, JSONObject> {
        /*Dialog mDialog;

        @Override
        protected void onPreExecute() {
            mDialog = new_added Dialog(RequestBrandActivity.this);
            mDialog.getWindow().setBackgroundDrawable(new_added ColorDrawable(Color.WHITE));
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Requesting Brand please wait...");
            mDialog.setCancelable(false);
            mDialog.show();
        }*/
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(RequestBrandActivity.this);
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
                JSONObject mJsonObject = new JSONObject();
                mJsonObject.put("customerId", params[0]);
                mJsonObject.put("customerMobile", params[1]);
                mJsonObject.put("requestedBrand", params[2]);

                jsonObjectFromUrl = new HttpUrlConnectionJSONParser().getJsonFromHttpUrlConnectionJsonRequest_Post(Constant.BASE_URL_REQUEST_BRAND, mJsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObjectFromUrl;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (jsonObject != null) {
                try {
                    if (jsonObject.has("reqId")) {
                        Toast.makeText(RequestBrandActivity.this, "Request placed successfully", Toast.LENGTH_SHORT).show();
                        RequestBrandActivity.this.finish();
                    } else {
                        Toast.makeText(RequestBrandActivity.this, "Unable to place request pleasse try again", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mDialog.isShowing()) {
                    animation.stop();
                    mDialog.dismiss();
                }
            } else {
                Toast.makeText(RequestBrandActivity.this, "Improper response from server", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
