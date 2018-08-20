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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.widget.ArrayAdapter;

import org.json.JSONObject;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;

/**
 * Created by Krishna on 18-02-2017.
 */

public class FeedbackActivity extends AppCompatActivity {
    AsyncTask<String, Void, JSONObject> mStringVoidJSONObjectAsyncTask;
    ListView feedbackListOption;
    EditText edtFeedbackComment;
    Button mSubmit;
    int rating = -2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedbackactivity);

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        feedbackListOption = (ListView) findViewById(R.id.lv_feedback);
        mSubmit = (Button) findViewById(R.id.submit_feedback);

        ((ImageView) toolbar.findViewById(R.id.my_order_detail_nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedbackActivity.this.finish();
            }
        });
        edtFeedbackComment = (EditText) findViewById(R.id.edtFeedbackComment);

        ((ImageView) toolbar.findViewById(R.id.my_order_detail_nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedbackActivity.this.finish();
            }
        });

        String[] listValues = new String[]{"Very Satisfied",
                "Satisffied",
                "Neutral",
                "Unsatisfied",
                "very Unsatisfied",
                "N/A",
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, android.R.id.text1, listValues);
        feedbackListOption.setAdapter(adapter);

        feedbackListOption.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        rating = 10;
                        break;
                    case 1:
                        rating = 8;
                        break;
                    case 2:
                        rating = 5;
                        break;
                    case 3:
                        rating = 3;
                        break;
                    case 4:
                        rating = 1;
                        break;
                    case 5:
                        rating = 0;
                        break;
                    case 6:
                        rating = -1;
                        break;
                }
            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(FeedbackActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
                mStringVoidJSONObjectAsyncTask = new RequestBrand().execute(mRetailerBean.getCustomerId(), mRetailerBean.getMobile(), mRetailerBean.getShopName(), edtFeedbackComment.getText().toString().trim(), "" + rating);
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
            mDialog = new Dialog(FeedbackActivity.this);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Submitting feedback please wait...");
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
                mJsonObject.put("Mobile", params[1]);
                mJsonObject.put("shopName", params[2]);
                mJsonObject.put("suggestions", params[3]);
                mJsonObject.put("satisfactionLevel", params[4]);

                jsonObjectFromUrl = new HttpUrlConnectionJSONParser().getJsonFromHttpUrlConnectionJsonRequest_Post(Constant.BASE_URL_FEEDBACK, mJsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObjectFromUrl;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (jsonObject != null) {
                try {
                    if (jsonObject.has("feedBackId")) {
                        Toast.makeText(FeedbackActivity.this, "Feedback submitted successfully", Toast.LENGTH_SHORT).show();
                        FeedbackActivity.this.finish();
                    } else {
                        Toast.makeText(FeedbackActivity.this, "Unable to submit feedback please try again", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mDialog.isShowing()) {
                    animation.stop();
                    mDialog.dismiss();
                }
            } else {
                Toast.makeText(FeedbackActivity.this, "Improper response from server", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
