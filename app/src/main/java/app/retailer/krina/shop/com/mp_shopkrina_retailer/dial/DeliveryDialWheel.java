package app.retailer.krina.shop.com.mp_shopkrina_retailer.dial;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.utils.ComplexPreferences;
import com.android.volley.RequestQueue;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.protinus.trupay.trupayminilib.activity.ActivityTrupayIntent;


import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.ActivationActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.Constant;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.HomeActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.LoginActivity_Nav;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.Utils;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.CartItemBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.CartItemInfo;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.dial.library.LuckyItem;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.dial.library.LuckyWheelView;


public class DeliveryDialWheel extends AppCompatActivity {
    List<LuckyItem> data = new ArrayList<>();
    private Context mContext;
    private Activity mActivity;
     int GetPoint=0;
    private RelativeLayout mRelativeLayout;
    private Button mButton;
    Dialog mDialog;
    AnimationDrawable animation;
    private PopupWindow mPopupWindow;
     LuckyWheelView luckyWheelView;
    String dial,OrderId,Point;
    RequestQueue queue;

    int piCount=0;
    int pjCount=0;
    int pkCount=0;
    int plCount=0;
    int pmCount=0;
double serviceText;
    int count=0;
    int dial1=0;
    static final int[] DIAL = {1, 1, 1, 1, 2, 2, 3, 3, 4, 5 };
   // int deliveryCharges = 10;
    CartItemBean mCartItem;
    double amountToReduct = 0;
    double enterRewardPoint;
    Button btnPlay,btnSkip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* //No status bar on screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_dial_wheel);


        amountToReduct = Double.parseDouble(Utility.getStringSharedPreferences(DeliveryDialWheel.this, "AmountToReduct"));
        enterRewardPoint = Double.parseDouble(Utility.getStringSharedPreferences(DeliveryDialWheel.this, "EnterRewardPoint"));

        Intent intent = getIntent();
        dial = intent.getStringExtra("DIAL");
        dial1= Integer.parseInt(dial);

        TextView mtext=(TextView)findViewById(R.id.text) ;
        mtext.setText("बधाई हो ! आपको मिले है "+dial1+" फ्री डायल ");
        mContext = getApplicationContext();

        // Get the activity
        mActivity = DeliveryDialWheel.this;

        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);
       luckyWheelView = (LuckyWheelView) findViewById(R.id.luckyWheel);

        LuckyItem luckyItem1 = new LuckyItem();
        luckyItem1.text = "40";
        luckyItem1.icon = R.drawable.prize;
        luckyItem1.color = Color.parseColor("#8F1BDF");
        data.add(luckyItem1);

        LuckyItem luckyItem2 = new LuckyItem();
        luckyItem2.text = "80";
        luckyItem2.icon = R.drawable.prize;
        luckyItem2.color = Color.parseColor("#00C9F9");
        data.add(luckyItem2);

        LuckyItem luckyItem3 = new LuckyItem();
        luckyItem3.text = "120";
        luckyItem3.icon = R.drawable.prize;
        luckyItem3.color = Color.parseColor("#FF8800");
        data.add(luckyItem3);

        //////////////////
        LuckyItem luckyItem4 = new LuckyItem();
        luckyItem4.text = "160";
        luckyItem4.icon = R.drawable.prize;
        luckyItem4.color = Color.parseColor("#FF3552");
        data.add(luckyItem4);

        LuckyItem luckyItem5 = new LuckyItem();
        luckyItem5.text = "200";
        luckyItem5.icon = R.drawable.prize;
        luckyItem5.color = Color.parseColor("#EFCEB9");
        data.add(luckyItem5);


        luckyWheelView.setData(data);
        luckyWheelView.setRound(getRandomRound());

        btnPlay=(Button)findViewById(R.id.play);
        btnSkip=(Button)findViewById(R.id.skip);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Dial1::"+dial1);
                if(dial1==0)
                {
                    Toast.makeText(mContext, "Your dial has end", Toast.LENGTH_SHORT).show();
                }else
                {
                    Random random = new Random();
                    int index = getRandomIndex();

                    luckyWheelView.startLuckyWheelWithTargetIndex(index);
                }
                btnPlay.setEnabled(false);
                btnSkip.setEnabled(false);
            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          ErrorPopup();

            }
        });
        luckyWheelView.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {
                String number = null;
                System.out.println("Index::"+index);
                if(index==0)
                {
                    number="40";
                    loadPhoto(R.drawable.sk_icon,number);
                }else if(index==1)
                {
                    number="40";
                    loadPhoto(R.drawable.sk_icon,number);
                }
                else if(index==2)
                {
                    number="80";
                    loadPhoto(R.drawable.sk_icon,number);
                }
                else if(index==3)
                {
                    number="120";
                    loadPhoto(R.drawable.sk_icon,number);
                }else if(index==4)
                {
                    number="160";
                    loadPhoto(R.drawable.sk_icon,number);
                }else if(index==5)
                {
                    number="200";
                    loadPhoto(R.drawable.sk_icon,number);
                }
              //  Toast.makeText(getApplicationContext(), number, Toast.LENGTH_SHORT).show();
              // callPostDialPoint();

            }
        });
    }

    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(DeliveryDialWheel.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }


    private int getRandomIndex() {
        Random rand = new Random();
        return rand.nextInt(data.size() - 1) + 0;
    }

    private int getRandomRound() {
        Random rand = new Random();
        return rand.nextInt(10) + 15;
    }

    private void loadPhoto(int img,String number) {

        dial1=dial1-1;
        int  gainPoint =   Utility.getIntSharedPreferences(DeliveryDialWheel.this,"GainPoint");

        System.out.println("gainPoint1::"+gainPoint);
        System.out.println("gainPoint2::"+GetPoint);
        System.out.println("gainPoint3::"+number);

       // GetPoint=gainPoint+GetPoint+Integer.parseInt(number);
        GetPoint=gainPoint+ Integer.parseInt(number);

        System.out.println("gainPoint Final::"+GetPoint);
        final Dialog dialog = new Dialog(DeliveryDialWheel.this);
        dialog.setContentView(R.layout.custom);
        dialog.setCancelable(false);
        Button dialogButton = (Button) dialog.findViewById(R.id.ok);
        TextView tvPoint = (TextView) dialog.findViewById(R.id.text);
        String numberColor = " <i><font color=#000000>Congratulation you get </font> </i><font color=#0000FF>"+number+"</font> <i><font color=#000000> free point</font></i>";
        // tvPoint.setText("You Got "+ Html.fromHtml(numberColor)+" Point");
        tvPoint.setText(Html.fromHtml(numberColor));
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Utility.setIntSharedPreference(DeliveryDialWheel.this, "GainPoint", GetPoint);
                if(dial1==0)
                {
                    new AlertDialog.Builder(DeliveryDialWheel.this)
                            .setTitle("Payment")
                            .setMessage("Please choose method for payment?")
                            .setPositiveButton("Trupay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //finish();

                                    //  SkCodeActivity.this.finish();
                                    //    startActivity(new_added Intent(SkCodeActivity.this, DaysBidActivity.class));

                                    callTruePay();


                                }

                            })
                            .setNegativeButton("Payment on delivery",  new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    callOrderPlaceApi();


                                }

                            })
                            .show();

                }else{
                    dialog.dismiss();
                }
                count =   Utility.getIntSharedPreferences(DeliveryDialWheel.this,"DialCount");
                System.out.println("Count::"+count);
                count++;
                Utility.setIntSharedPreference(DeliveryDialWheel.this, "DialCount", count);
             //  callPostDialPoint();

                btnPlay.setEnabled(true);
                btnSkip.setEnabled(true);
            }
        });

        dialog.show();
    }


    public void showLoading() {
        mDialog = new Dialog(DeliveryDialWheel.this);
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
    public void onBackPressed() {
       // startActivity(new_added Intent(DeliveryDialWheel.this ,CartActivity.class));
        //DeliveryDialWheel.this.finish();
        ErrorPopup();
    }

    private void ErrorPopup() {
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryDialWheel.this);
            builder.setTitle("Place Order");
            builder.setMessage("Are you sure, want to place order without remaining dial use?");
            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    new AlertDialog.Builder(DeliveryDialWheel.this)
                            .setTitle("Payment")
                            .setMessage("Please choose method for payment?")
                            .setPositiveButton("Trupay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //finish();

                                    //  SkCodeActivity.this.finish();
                                    //    startActivity(new_added Intent(SkCodeActivity.this, DaysBidActivity.class));

                                    callTruePay();


                                }

                            })
                            .setNegativeButton("Payment on delivery",  new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                    callOrderPlaceApi();


                                }

                            })
                            .show();

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void callTruePay() {


        double total,serviceTextAmount;

        if(getCartItem().getTotalPrice() < 2000) {

            total = getCartItem().getTotalPrice() + 10;
        } else {
            total = getCartItem().getTotalPrice();

        }
        serviceText=0.01*total;
        serviceTextAmount=serviceText+total;
        System.out.println("ServiceTextAmount::::"+serviceTextAmount);

        Intent inNext = new Intent(DeliveryDialWheel.this,ActivityTrupayIntent.class);
        Bundle bundle = new Bundle();
        bundle.putString("amount", ""+total);
        bundle.putString("paymentMethod", "111");
        bundle.putString("orderNo", String.valueOf(getRandom()));
        bundle.putString("accessToken", "6e5794ce-5747-4b63-9d47-d4be0332d2a3");
        bundle.putString("appName", "ShopKirana");
        bundle.putString("collectorId", "13370");
        bundle.putString("requestId", String.valueOf(getRandom()));
        inNext.putExtras(bundle);
        Log.i("TAG bunde ", bundle.toString());
        startActivityForResult(inNext, 10002);


       /* Intent inNext = new Intent(DeliveryDialWheel.this, ActivityTrupayIntent.class);
        Bundle bundle = new Bundle();
        bundle.putString("amount", ""+total);
        //   bundle.putString("amount", "1");
        bundle.putString("merchantTransactionRefNumber", String.valueOf(getRandom()));
        bundle.putString("accessToken", "6e5794ce-5747-4b63-9d47-d4be0332d2a3");
//Secret key -> 123456
        //      edc4a7e2-73aa-4b97-a804-6b66c2f8d9c3

        bundle.putString("appName", "ShopKrina");
        inNext.putExtras(bundle);
        Log.i("TAG bunde ", bundle.toString());
        startActivityForResult(inNext, 10002);*/

    }



    private int getRandom() {
        Random rn = new Random();
        return rn.nextInt(10000) + 1;
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("onResult-ActivityMain", String.valueOf(requestCode));
        if (requestCode == 10002 && data != null && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();


            Toast.makeText(DeliveryDialWheel.this, "\nStatus" +bundle.getString("status")
                            +"\nStatus Descriptions" +bundle.getString("statusDesc")
                            +"\nTax amount" +bundle.getString("txnAmount")
                  /*  +bundle.getString("status")
                    +bundle.getString("status")
                    +bundle.getString("status")
                    +bundle.getString("status")*/, Toast.LENGTH_LONG).show();


            Log.e("truepay",
                    "\nStatus" +bundle.getString("status")
                            +"\nStatus Descriptions" +bundle.getString("statusDesc")
                            +"\nTax amount" +bundle.getString("txnAmount")
            );


            if (bundle.getString("status").equals("F")) {
                Toast.makeText(this, "Payment canceled!", Toast.LENGTH_LONG).show();
            }

            else if(bundle.getString("status").equals("S")) {
                Toast.makeText(this, "Payment Success!", Toast.LENGTH_LONG).show();
                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(DeliveryDialWheel.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
                int   gainPoint =   Utility.getIntSharedPreferences(DeliveryDialWheel.this,"GainPoint");
                if (mRetailerBean != null && !mRetailerBean.getCustomerId().equalsIgnoreCase("0")) {
                    if (mRetailerBean.getActive().equalsIgnoreCase("false")) {
                        Intent i = new Intent(getApplicationContext(), ActivationActivity.class);
                        //    dialog.dismiss();
                        startActivity(i);
                        DeliveryDialWheel.this.finish();
                    } else {
                        if (Utils.isInternetConnected(DeliveryDialWheel.this)) {
                            try {
                                CartItemBean cartItemBean = getCartItem();
                                String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                                JSONObject mRequesParamObj = new JSONObject();
                                mRequesParamObj.put("CreatedDate", currentDateandTime);
                                mRequesParamObj.put("CustomerName", mRetailerBean.getName());
                                mRequesParamObj.put("CustomerType", mRetailerBean.getCustomerType());
                                mRequesParamObj.put("Customerphonenum", mRetailerBean.getMobile());
                                mRequesParamObj.put("SalesPersonId", 0);
                                mRequesParamObj.put("ShippingAddress", mRetailerBean.getShippingAddress());
                                mRequesParamObj.put("ShopName", mRetailerBean.getShopName());
                                mRequesParamObj.put("Skcode", mRetailerBean.getSkcode());
                                mRequesParamObj.put("DialEarnigPoint", gainPoint);
                                mRequesParamObj.put("DreamPoint", cartItemBean.getTotalDpPoints());
                                mRequesParamObj.put("TotalAmount", (getCartItem().getTotalPrice()));
                                mRequesParamObj.put("Savingamount", (getCartItem().getSavingAmount()));
                                mRequesParamObj.put("deliveryCharge", cartItemBean.getTotalPrice() > 2000 ? "0" : "10");
                                //  mRequesParamObj.put("deliveryCharge", );
                                mRequesParamObj.put("Trupay", "true");
                                mRequesParamObj.put("WalletAmount", amountToReduct);
                                mRequesParamObj.put("walletPointUsed", enterRewardPoint);
                                mRequesParamObj.put("OnlineServiceTax", serviceText);




                                JSONArray mItemArray = new JSONArray();
                                for (int i = 0; i < cartItemBean.getmCartItemInfos().size(); i++) {
                                    JSONObject mItemObj = new JSONObject();
                                    mItemObj.put("ItemId", cartItemBean.getmCartItemInfos().get(i).getItemId());
                                    mItemObj.put("qty", cartItemBean.getmCartItemInfos().get(i).getQty());


                                    mItemObj.put("WarehouseId", cartItemBean.getmCartItemInfos().get(i).getWarehouseId());
                                    mItemObj.put("CompanyId", cartItemBean.getmCartItemInfos().get(i).getCompanyId());


                                    mItemArray.put(mItemObj);
                                }
                                mRequesParamObj.put("itemDetails", mItemArray);


                                //     new_added PlaceORderAsyncTask().execute(mRequesParamObj);


                                callOrderApi(mRequesParamObj);

                                // customAlertDialog.dismiss();


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            new AlertDialog.Builder(DeliveryDialWheel.this)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("Error!")
                                    .setMessage("Internet connection is not available")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {


                                            //  customAlertDialog.dismiss();

                                        }

                                    }).show();
                        }
                        //   dialog.dismiss();
                    }
                }
                // startActivity(new_added Intent(CartActivity.this, HomeActivity.class));

            }

            else if(bundle.getString("status").equals("P")) {
                Toast.makeText(this, "Payment Pending!", Toast.LENGTH_LONG).show();
            }

            else if(bundle.getString("status").equals("T")) {
                Toast.makeText(this, "Time out!", Toast.LENGTH_LONG).show();
            }

        }
    }
    public CartItemBean getCartItem() {
        if (mCartItem == null) {
            ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(DeliveryDialWheel.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0,0,0,"", "");
            }
        }
        return mCartItem;
    }

    public void callOrderApi(JSONObject jsonObject) {

        showLoading();
        // Toast.makeText(this, "Json Parameter"+jsonObject, Toast.LENGTH_SHORT).show();

        Log.e("Order Parameter", jsonObject.toString());


        new AQuery(DeliveryDialWheel.this).post(Constant.BASE_URL_PLACE_ORDER,
                jsonObject,
                String.class,
                new AjaxCallback<String>(){

                    @Override
                    public void callback(String url, String object, AjaxStatus status) {


                        Log.e("Result", object.toString());

                        if (object.contains("true")) {

                            Toast.makeText(DeliveryDialWheel.this, "Order placed successfully", Toast.LENGTH_SHORT).show();
                            clearCartData();
                            DeliveryDialWheel.this.finish();
                            startActivity(new Intent(DeliveryDialWheel.this, HomeActivity.class));

                        } else if (object.contains("false")){

                            enterRewardPoint = 0;
                            Toast.makeText(DeliveryDialWheel.this, "Unable to place order, please try again", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            enterRewardPoint = 0;
                            Toast.makeText(DeliveryDialWheel.this, "Unable to place order, please try again", Toast.LENGTH_LONG).show();
                        }
                        if (mDialog.isShowing()) {
                            animation.stop();
                            mDialog.dismiss();
                        }


                    }
                }
        );

    }

    private void callOrderPlaceApi() {
        int   gainPoint =   Utility.getIntSharedPreferences(DeliveryDialWheel.this,"GainPoint");
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(DeliveryDialWheel.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        if (mRetailerBean != null && !mRetailerBean.getCustomerId().equalsIgnoreCase("0")) {
            if (mRetailerBean.getActive().equalsIgnoreCase("false")) {
                Intent i = new Intent(getApplicationContext(), ActivationActivity.class);
                //    dialog.dismiss();
                startActivity(i);
                DeliveryDialWheel.this.finish();
            } else {
                if (Utils.isInternetConnected(DeliveryDialWheel.this)) {
                    try {
                        CartItemBean cartItemBean = getCartItem();
                        String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        JSONObject mRequesParamObj = new JSONObject();
                        mRequesParamObj.put("CreatedDate", currentDateandTime);
                        mRequesParamObj.put("CustomerName", mRetailerBean.getName());
                        mRequesParamObj.put("CustomerType", mRetailerBean.getCustomerType());
                        mRequesParamObj.put("Customerphonenum", mRetailerBean.getMobile());
                        mRequesParamObj.put("SalesPersonId", 0);
                        mRequesParamObj.put("ShippingAddress", mRetailerBean.getShippingAddress());
                        mRequesParamObj.put("ShopName", mRetailerBean.getShopName());
                        mRequesParamObj.put("Skcode", mRetailerBean.getSkcode());
                        mRequesParamObj.put("DreamPoint", cartItemBean.getTotalDpPoints());
                        mRequesParamObj.put("DialEarnigPoint", gainPoint);
                        mRequesParamObj.put("TotalAmount", (getCartItem().getTotalPrice()));
                        mRequesParamObj.put("Savingamount", (getCartItem().getSavingAmount()));

                        mRequesParamObj.put("deliveryCharge", cartItemBean.getTotalPrice() > 2000 ? "0" : "10");

                        //  mRequesParamObj.put("deliveryCharge", );

                        mRequesParamObj.put("OnlineServiceTax", "0");


                        mRequesParamObj.put("WalletAmount", amountToReduct);



                        mRequesParamObj.put("walletPointUsed", enterRewardPoint);
                        mRequesParamObj.put("Trupay", "false");




                        JSONArray mItemArray = new JSONArray();
                        for (int i = 0; i < cartItemBean.getmCartItemInfos().size(); i++) {
                            JSONObject mItemObj = new JSONObject();
                            mItemObj.put("ItemId", cartItemBean.getmCartItemInfos().get(i).getItemId());
                            mItemObj.put("qty", cartItemBean.getmCartItemInfos().get(i).getQty());


                            mItemObj.put("WarehouseId", cartItemBean.getmCartItemInfos().get(i).getWarehouseId());
                            mItemObj.put("CompanyId", cartItemBean.getmCartItemInfos().get(i).getCompanyId());


                            mItemArray.put(mItemObj);
                        }
                        mRequesParamObj.put("itemDetails", mItemArray);


                        //     new_added PlaceORderAsyncTask().execute(mRequesParamObj);


                        callOrderApi(mRequesParamObj);

                       // customAlertDialog.dismiss();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    new AlertDialog.Builder(DeliveryDialWheel.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Error!")
                            .setMessage("Internet connection is not available")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                       dialog.dismiss();

                                }

                            }).show();
                }
                //   dialog.dismiss();
            }
        } else {

           // customAlertDialog.dismiss();

            Intent i = new Intent(getApplicationContext(), LoginActivity_Nav.class);
            //   dialog.dismiss();
            startActivity(i);
            DeliveryDialWheel.this.finish();
        }

    }

}
