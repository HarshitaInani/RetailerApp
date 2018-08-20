package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.Amitlibs.utils.ComplexPreferences;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Iterator;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;

public class RequestServiceActivity extends AppCompatActivity {

    CheckBox panCb, tinCb,FssaiCb, itrCb;
    JSONObject jsonObject = new JSONObject();
    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObjectMain = new JSONObject();
    RetailerBean mRetailerBean;
    Dialog mDialog;
    AnimationDrawable animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);


        panCb = (CheckBox) findViewById(R.id.panCb);
        tinCb = (CheckBox) findViewById(R.id.tinCb);
        FssaiCb = (CheckBox) findViewById(R.id.fssaciCb);
        itrCb  = (CheckBox) findViewById(R.id.itrCb);


        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);


        ((TextView) findViewById(R.id.title_toolbar)).setText("Request Service");


        Toolbar toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
        setSupportActionBar(toolbar);


        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestServiceActivity.this.finish();
            }
        });


        if (!Utility.isConnectingToInternet(RequestServiceActivity.this)){
            Toast.makeText(this, "Please check Internet connection", Toast.LENGTH_SHORT).show();
        }

        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(RequestServiceActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
         mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);



        panCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    try {
                        jsonObject.put("pan", "1");



/*
                        JSONObject jsonArrayObject = new_added JSONObject();

                        jsonArrayObject.put("Item", "pan");


                        jsonArray.put(jsonArrayObject);
*/


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                 /*   try {
                        jsonObject.put("pan", "0");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
*/

                    jsonObject.remove("pan");

                }
            }
        });


        tinCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    try {
                        jsonObject.put("tin", "1");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                    jsonObject.remove("tin");


/*
                    try {
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
*/


                }
            }
        });



        FssaiCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    try {
                        jsonObject.put("fssai", "1");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                /*    try {
                        jsonObject.put("fssai", "0");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
*/
                    jsonObject.remove("fssai");

                }
            }
        });



        itrCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    try {
                        jsonObject.put("itr", "1");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                /*    try {
                        jsonObject.put("fssai", "0");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
*/
                    jsonObject.remove("itr");

                }
            }
        });




        ((Button) findViewById(R.id.submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!panCb.isChecked() &&
                        !itrCb.isChecked() &&
                        !FssaiCb.isChecked() &&
                        !tinCb.isChecked()) {
                    Toast.makeText(RequestServiceActivity.this, "Please select an option!", Toast.LENGTH_SHORT).show();

                }

                else if(!Utility.isConnectingToInternet(RequestServiceActivity.this)) {
                    Toast.makeText(RequestServiceActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();
                }

                else {

                    Log.e("request", jsonObject.toString());
                  //  Toast.makeText(RequestServiceActivity.this, "" + jsonObject.toString(), Toast.LENGTH_SHORT).show();

                    sendData(jsonObject);

                }
            }
        });



    }



    public void sendData(JSONObject json) {


        jsonObjectMain = new JSONObject();

        jsonArray = new JSONArray();



        showLoading();

        try {

            JSONObject jsonObject = new JSONObject(json.toString());
            for (int i =0; i < jsonObject.length(); i++) {

                //   Toast.makeText(context, "", Toast.LENGTH_SHORT).show();

                jsonObject.names().get(i);

                if( jsonObject.get(jsonObject.names().get(i).toString()).equals("1")) {

                    //    jsonArray.put(  jsonObject.get(jsonObject.names().get(i).toString())  );


                    //  jsonObject.remove()
                }





                //  Toast.makeText(context, ""+jsonObject.names().get(i), Toast.LENGTH_SHORT).show();

             //   jsonFav = new_added JSONObject();



            }


            //   Toast.makeText(context, "Array : "+jsonArray.toString(), Toast.LENGTH_SHORT).show();

            /*Set keys = jsonObject.keys();
            Iterator a = keys.iterator();
            while(a.hasNext()) {
                String key = (String)a.next();
                // loop to get the dynamic key
                String value = (String)jsonObject.get(key);
                System.out.print("key : "+key);
                System.out.println(" value :"+value);
            }
*/



            Iterator<?> keys = jsonObject.keys();

            while( keys.hasNext() ) {
                String key = (String) keys.next();
                System.out.println("Key: " + key);
                System.out.println("Value: " + jsonObject.get(key));


                //  Toast.makeText(context, key+ "key \nvalue "+  jsonObject.get(key), Toast.LENGTH_SHORT).show();

                if (jsonObject.get(key).equals("1")) {
                    JSONObject jsonObject1 = new JSONObject();

//                    jsonObject1.put("ItemId", Integer.parseInt(key) );

                    jsonObject1.put("Item", key.toString() );

                    jsonArray.put(jsonObject1);
                }

            }


            // Toast.makeText(context, "Array : "+jsonArray.toString(), Toast.LENGTH_SHORT).show();

            jsonObjectMain.put("items", jsonArray);
            jsonObjectMain.put("Skcode", mRetailerBean.getSkcode());
            jsonObjectMain.put("WarehouseId", mRetailerBean.getWarehouseid());
            jsonObjectMain.put("PeopleID", mRetailerBean.getCustomerId());
            jsonObjectMain.put("PeopleName", mRetailerBean.getName());


            //  ((TextView) findViewById(R.id.txt)).setText(jsonObjectMain.toString());


            Log.e("fav", jsonObjectMain.toString());

          //  Toast.makeText(this, "Last object"+jsonObjectMain.toString(), Toast.LENGTH_SHORT).show();




            if(!Utility.isConnectingToInternet(RequestServiceActivity.this)) {

                if (mDialog.isShowing()) {
                    animation.stop();
                    mDialog.dismiss();
                }

                Toast.makeText(RequestServiceActivity.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
            }


else {

                new AQuery(RequestServiceActivity.this).post(Constant.BASE_URL_REQUEST_SERVICE,
                        jsonObjectMain,
                        String.class,
                        new AjaxCallback<String>() {


                            @Override
                            public void callback(String url, String s, AjaxStatus status) {
                                //Toast.makeText(RequestServiceActivity.this, "" + s, Toast.LENGTH_SHORT).show();

                                if (s.equals("true")) {

                                    if (mDialog.isShowing()) {
                                        animation.stop();
                                        mDialog.dismiss();
                                    }

                                    Toast.makeText(RequestServiceActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    RequestServiceActivity.this.finish();
                                    startActivity(new Intent(RequestServiceActivity.this, HomeActivity.class));
                                } else {
                                    Toast.makeText(RequestServiceActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();


                                    if (mDialog.isShowing()) {
                                        animation.stop();
                                        mDialog.dismiss();
                                    }


                                }
                            }
                        });


            }
           // callMyfavApi();

        } catch (JSONException e) {
            e.printStackTrace();


        }





    }




    public void showLoading() {


        mDialog = new Dialog(RequestServiceActivity.this);
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



}
