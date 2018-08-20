package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.protinus.trupay.trupayminilib.activity.ActivityTrupayIntent;
import com.protinus.trupay.trupayminilib.utils.TrupayConstants;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.CartItemListAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.CartItemBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.CartItemInfo;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.Gullakpojo;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.ItemList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.databinding.CartActivityBinding;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.dial.DeliveryDialWheel;

/**
 * Created by Krishna on 22-01-2017.
 */

public class CartActivity extends AppCompatActivity {
    /* int rowitemImageHeight;
     int rowitemImageWidth;*/
    int rowitemImageHeight = 77;
    int rowitemImageWidth = 77;
    double total, serviceTextAmount,NetPayableamout, Entergullakamnt;
    String gullakamount;
    EditText Et1;
    private TextView tvTotalItemPrice;
    private TextView tvTotalItemQty;
    private TextView tvGrandTotal;
    private TextView tvDeliveryCharges;
    private TextView tvDpGrandTotal;
    private TextView tvDialTotal;
    private TextView tvUseDial;
    Button btnAddMoreItems;
    RecyclerView mCartFragRecyclerView;
    RelativeLayout rlCheckOut;
    int deliveryCharges = 10;
    CartItemBean mCartItem;
    AsyncTask<String, Void, JSONObject> myWalletAsyncTaskObj;
    double walletAmount = 0.0,editgullakamnt;
    double wallet1PointValue = 0.0;
    double tempwalletAmount = 0.0;
    boolean redeemClicked = false;

    Dialog mDialog;
    AnimationDrawable animation;


    String rewardPoints = "";


    double totalAmount;
    double totalDpPoints;

    double px;
    double rx;

    ProgressDialog progressDialog;

    double enterRewardPoint;


    ArrayList<String> list = new ArrayList<>();


    String skC;

    double serviceText;
    double amountToReduct = 0;
    AlertDialog customAlertDialog;
    android.support.v4.app.FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(CartActivity.this.getResources().getColor(R.color.colorPrimaryDark));
            }
        }


        // textView.setText();

        CartActivityBinding cartActivityBinding = DataBindingUtil.setContentView(this, R.layout.cart_activity);

        //  setImagesDynamicSize();

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        callChequeBounceApi();
        tvTotalItemPrice = cartActivityBinding.cartFragTotalAmountTv;
        tvTotalItemQty = cartActivityBinding.cartFragTotalItemTv;
        tvGrandTotal = cartActivityBinding.cartFragGrandTotalAmountTv;
        tvDpGrandTotal = cartActivityBinding.cartFragTotalDpPointTv;
        //      totalAmount = Double.parseDouble(cartActivityBinding.cartFragGrandTotalAmountTv.getText().toString());
        totalAmount = getCartItem().getTotalPrice() + getCartItem().getDeliveryCharges();
        tvDialTotal = (TextView) findViewById(R.id.dial_available);
        tvUseDial = (TextView) findViewById(R.id.dial_available_use);
        tvDeliveryCharges = cartActivityBinding.cartFragDeliveryChargesTv;
        btnAddMoreItems = cartActivityBinding.cartFragAddMoreItems;
        mCartFragRecyclerView = cartActivityBinding.cartFragRv;
        rlCheckOut = cartActivityBinding.cartFragCheckoutRl;
        list.add("Indore");
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(CartActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        final RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        skC = mRetailerBean.getSkcode();
        callWalletApi(mRetailerBean.getCustomerId());
        MyAsyncTasks Asynctask=new MyAsyncTasks();
        Asynctask.execute();
        btnAddMoreItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
                CartActivity.this.finish();
            }
        });

        tvUseDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvDialTotal.getText().toString().equalsIgnoreCase("0")) {
                    Toast.makeText(CartActivity.this, "No Dial Available", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(getApplicationContext(), DeliveryDialWheel.class);
                    i.putExtra("DIAL", tvDialTotal.getText().toString());
                    startActivity(i);
                }
            }
        });

        rlCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getCartItem().getTotalPrice() < 700) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setTitle("Your Order");
                    builder.setMessage(Html.fromHtml("Oh! Since your order amount is below <font color=#424242>&#8377; 700, Please add a bit more to your cart."));
                    builder.setPositiveButton("Yes! Add More Items", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            CartActivity.this.finish();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {


                    if (totalAmount < 2000) {


                        deliveryCharges = 10;


                    } else {

                        deliveryCharges = 0;

                    }


                    double tAmount = getCartItem().getTotalPrice();

                    if (tAmount < 2000) {
                        tAmount += 10;
                    }

                    totalAmount = tAmount;


                    LayoutInflater inflater = LayoutInflater.from(CartActivity.this);
                    View dialogLayout = inflater.inflate(R.layout.reward_point_popup, null);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setView(dialogLayout);

                    customAlertDialog = builder.create();


                    Button placeBtn = (Button) dialogLayout.findViewById(R.id.place_order);

                    TextView cancelBtn = (TextView) dialogLayout.findViewById(R.id.cancel);


                    TextView redeemBtn = (TextView) dialogLayout.findViewById(R.id.redeemBtn);

                    TextView dpTotal = (TextView) dialogLayout.findViewById(R.id.dppoint);

                    TextView redeemTotal = (TextView) dialogLayout.findViewById(R.id.redeempop_up_total);
                    TextView saveAmount = (TextView) dialogLayout.findViewById(R.id.saving_amount);


                    TextView pointInfoTv = (TextView) dialogLayout.findViewById(R.id.point_info);
                    TextView pointInfoTv1 = (TextView) dialogLayout.findViewById(R.id.point_info1);
                    TextView Gullakpoint = (TextView) dialogLayout.findViewById(R.id.gullakpoint);



                    Spinner cityspinner = (Spinner) dialogLayout.findViewById(R.id.city_spinner);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CartActivity.this, android.R.layout.simple_list_item_1, list);

                    cityspinner.setAdapter(adapter);

                    final TextView redeemNetTotal = (TextView) dialogLayout.findViewById(R.id.redeem_pop_up_net_amount);

                    Et1 = (EditText) dialogLayout.findViewById(R.id.Et1);
                    final EditText point = (EditText) dialogLayout.findViewById(R.id.pointEt);

                    point.setText("0");
                    Gullakpoint.setText(gullakamount);


                    if (rewardPoints.isEmpty()) {
                        rewardPoints = "0";
                    }

                    if (rewardPoints.equals("null")) {
                        rewardPoints = "0";
                    }


                    dpTotal.setText(rewardPoints);

                    redeemTotal.setText(new DecimalFormat("##.##").format(totalAmount));
                    redeemNetTotal.setText(new DecimalFormat("##.##").format(totalAmount));
                    saveAmount.setText(new DecimalFormat("##.##").format(getCartItem().getSavingAmount()));
                    pointInfoTv.setText("Note " + px +" "+ "walletpoint = Rs " + rx);
                    pointInfoTv1.setText("Note 1.0 Gullakpoint = Rs 1.0");

                    placeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                          if (!point.getText().toString().isEmpty()) {
                                Utility.setStringSharedPreference(CartActivity.this, "EnterRewardPoint", "" + enterRewardPoint);
                                Utility.setStringSharedPreference(CartActivity.this, "AmountToReduct", "" + amountToReduct);
                                if (tvDialTotal.getText().toString().equalsIgnoreCase("0")) {
                                    new AlertDialog.Builder(CartActivity.this)
                                            .setTitle("Payment")
                                            .setMessage("Please choose method for payment?")
                                            .setPositiveButton("Trupay", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    callTruePay();


                                                }

                                            })
                                            .setNegativeButton("Payment on delivery", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    customAlertDialog.dismiss();
                                                    callOrderPlaceApi();


                                                }

                                            })
                                            .show();
                                } else {

                                    Intent i = new Intent(getApplicationContext(), DeliveryDialWheel.class);
                                    i.putExtra("DIAL", tvDialTotal.getText().toString());
                                    startActivity(i);
                                }

                            } else {
                                Toast.makeText(CartActivity.this, "Please enter rewards point!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                    redeemBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(Et1.getText().toString().isEmpty() && point.getText().toString().isEmpty() ){
                                Toast.makeText(CartActivity.this, "Please enter  reward points and Gullak Points", Toast.LENGTH_SHORT).show();
                            }
                            else if (Et1.getText().toString().isEmpty()){
                                Toast.makeText(CartActivity.this, "Please enter Gullak Points", Toast.LENGTH_SHORT).show();
                            }
                            try {
                                editgullakamnt = Double.valueOf(Et1.getText().toString());
                            }
                            catch(NumberFormatException e){
                             e.printStackTrace();
                            }
                            if (Double.valueOf(gullakamount) < editgullakamnt) {
                                Et1.setText("0");
                                Toast.makeText(CartActivity.this, "You do not have enough points!", Toast.LENGTH_SHORT).show();
                            }
                            else if(editgullakamnt > Double.valueOf (redeemNetTotal.getText().toString())){
                                Toast.makeText(CartActivity.this, "Gullak points Should not be Greater than Net payable Amount", Toast.LENGTH_SHORT).show();
                                Et1.setText("0");
                            }

                           else if (!point.getText().toString().isEmpty()) {

                                if (rewardPoints.isEmpty()) {
                                    rewardPoints = "0";
                                }

                                if (rewardPoints.equals("null")) {
                                    rewardPoints = "0";
                                }

                                double currentRewardPoints = Double.parseDouble(rewardPoints);
                                enterRewardPoint = Double.parseDouble(point.getText().toString());

                                double totalAmountPopUp = Double.parseDouble(new DecimalFormat("##.##").format(totalAmount));

                                double netTotal = 0;
                                if (enterRewardPoint > currentRewardPoints) {
                                    Toast.makeText(CartActivity.this, "You do not have enough points!", Toast.LENGTH_SHORT).show();
                                    enterRewardPoint = 0;
                                    amountToReduct = 0;
                                    point.setText("0");
                                    redeemNetTotal.setText("" + new DecimalFormat("##.##").format(totalAmountPopUp));
                                } else {
                                    amountToReduct = (enterRewardPoint / px) * rx;
                                    if (amountToReduct < totalAmountPopUp ) {

//                            netTotal = totalAmountPopUp - (enterRewardPoint / 10);


                                        netTotal = totalAmountPopUp - (amountToReduct + editgullakamnt) ;


                                        redeemNetTotal.setText("" + new DecimalFormat("##.##").format(netTotal));
                                    } else {
                                        Toast.makeText(CartActivity.this, "You can not use point more than Total bill.", Toast.LENGTH_SHORT).show();
                                    }

                                }


                                //   Toast.makeText(CheckOutActivity.this, "" + point.getText().toString(), Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(CartActivity.this, "Please enter rewards point!", Toast.LENGTH_SHORT).show();
                            }




                        }
                    });

                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            enterRewardPoint = 0;
                            customAlertDialog.dismiss();


                        }
                    });

            /*    TextView indtruction = (TextView)dialogLayout.findViewById(R.id.text_instruction);
                final EditText userInput = (EditText)dialogLayout.findViewById(R.id.user_input);
                builder.setNegativeButton("Cancel", new_added DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Submit Name", new_added DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String userInputContent = userInput.getText().toString();
                        if(userInputContent.equals("")){
                            Toast.makeText(context, "You must enter a name in the field", Toast.LENGTH_LONG).show();
                            return;
                        }
                        textFromDialog.setText(userInputContent);
                    }
                });
            */


                    //  AlertDialog customAlertDialog = builder.create();
                    customAlertDialog.show();

                }
            }
        });
    }


    @Override
    public void onStart() {

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mCartFragRecyclerView.setLayoutManager(llm);
        CartItemListAdapter cartItemListAdapter = new CartItemListAdapter(CartActivity.this, getCartItem(), rowitemImageWidth, rowitemImageHeight, tvTotalItemPrice, tvTotalItemQty, tvGrandTotal, tvDeliveryCharges, deliveryCharges, tvDpGrandTotal, tvDialTotal);
        mCartFragRecyclerView.setAdapter(cartItemListAdapter);

        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(CartActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        if (mRetailerBean != null && !mRetailerBean.getCustomerId().equalsIgnoreCase("0")) {
            // myWalletAsyncTaskObj = new_added myWalletAsyncTask().execute(mRetailerBean.getCustomerId());
        } else {
            Toast.makeText(CartActivity.this, "You are not logged in please login", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), LoginActivity_Nav.class);
            startActivity(i);
            CartActivity.this.finish();
        }
        super.onStart();


    }

    public class myWalletAsyncTask extends AsyncTask<String, Void, JSONObject> {
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(CartActivity.this);
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


//            jsonObjFromurl = new_added HttpUrlConnectionJSONParser().getJsonObjectFromHttpUrlConnection(Constant.BASE_URL_MY_WALLET + "?CustomerId=" + params[0], null, HttpUrlConnectionJSONParser.Http.GET);


            jsonObjFromurl = new HttpUrlConnectionJSONParser().getJsonObjectFromHttpUrlConnection("", null, HttpUrlConnectionJSONParser.Http.GET);
//

            return jsonObjFromurl;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("wallet")) {
                    try {
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
                        walletAmount = Double.parseDouble(TotalAmount);
//                    walletAmount = 200;
                    } catch (Exception e) {
                        e.printStackTrace();
                        //  Toast.makeText(CartActivity.this, "No proper digit in wallet amount", Toast.LENGTH_SHORT).show();
                        walletAmount = 0.0;
                    }
                } else {
                    Toast.makeText(CartActivity.this, "No amount in wallet", Toast.LENGTH_SHORT).show();
                    walletAmount = 0.0;
                }
                if (jsonObject.has("conversion")) {
                    try {
                        JSONObject mWalletConversionObj = jsonObject.getJSONObject("conversion");
                        String Id = isNullOrEmpty(mWalletConversionObj, "Id");
                        double point = mWalletConversionObj.has("point") ? mWalletConversionObj.getDouble("point") : 0.0;
                        double rupee = mWalletConversionObj.has("rupee") ? mWalletConversionObj.getDouble("rupee") : 0.0;

                        wallet1PointValue = (rupee / point);
                        Log.d("CartActivity", "" + wallet1PointValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(CartActivity.this, "NOt proper value of points conversion", Toast.LENGTH_SHORT).show();
                        wallet1PointValue = 0.0;
                    }
                } else {
                    Toast.makeText(CartActivity.this, "Point conversion is not available", Toast.LENGTH_SHORT).show();
                    wallet1PointValue = 0.0;
                }
            } else {
                Toast.makeText(CartActivity.this, "Server not responding properly", Toast.LENGTH_SHORT).show();
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

    public class PlaceORderAsyncTask extends AsyncTask<JSONObject, Void, JSONObject> {
        /*Dialog mDialog;

        @Override
        protected void onPreExecute() {
            mDialog = new_added Dialog(CartActivity.this);
            mDialog.getWindow().setBackgroundDrawable(new_added ColorDrawable(Color.WHITE));
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Placing Order...");
            mDialog.setCancelable(false);
            mDialog.show();
        }*/
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(CartActivity.this);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Placing Order...");
            ImageView la = (ImageView) mDialog.findViewById(R.id.gridprogressBar);
            la.setBackgroundResource(R.drawable.custom_progress_dialog_animation);
            animation = (AnimationDrawable) la.getBackground();
            animation.start();
            mDialog.setCancelable(false);
            mDialog.show();
        }

        @Override
        protected JSONObject doInBackground(JSONObject... params) {
            JSONObject jsonObjectFromUrl = null;
            try {


                jsonObjectFromUrl = new HttpUrlConnectionJSONParser().getJsonFromHttpUrlConnectionJsonRequest_Post(Constant.BASE_URL_PLACE_ORDER, params[0]);

//                jsonObjectFromUrl = new_added HttpUrlConnectionJSONParser().getJsonFromHttpUrlConnectionJsonRequest_Post("", params[0]);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObjectFromUrl;
        }

        @Override
        protected void onPostExecute(JSONObject mJsonObject) {
            if (mJsonObject != null) {
                Toast.makeText(CartActivity.this, "Order placed successfully", Toast.LENGTH_SHORT).show();
                clearCartData();
                CartActivity.this.finish();
                startActivity(new Intent(CartActivity.this, HomeActivity.class));

            } else {
                enterRewardPoint = 0;

                Toast.makeText(CartActivity.this, "Unable to place order, please try again", Toast.LENGTH_SHORT).show();
            }
            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
            }
        }
    }

    @Override
    protected void onPause() {
        if (myWalletAsyncTaskObj != null && !myWalletAsyncTaskObj.isCancelled())
            myWalletAsyncTaskObj.cancel(true);
        super.onPause();

    }

    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(CartActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }

    public CartItemBean getCartItem() {
        if (mCartItem == null) {
            ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(CartActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0, 0, 0, "", "");
            }
        }
        return mCartItem;
    }

    public String addItemInCartItemArrayList(String itemId, int qty, double itemUnitPrice, ItemList selectedItem, double deliveryCarges, double totalDp, String warehouseId, String companyId, double Price) {


        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(CartActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        if (mCartItem == null) {
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0, 0, 0, "", "");
            }
        }

        String status = "Error";
        double tempTotalPrice = 0;
        double tempTotalQuantity = 0;
        double TotalPrice = 0;
        double saveAmount = 0;
        double tempTotalDpPoint = 0;


        if (mCartItem.getmCartItemInfos() != null && !mCartItem.getmCartItemInfos().isEmpty()) {
            boolean itemFound = false;
            int foundItemPosition = -1;
            for (int i = 0; i < mCartItem.getmCartItemInfos().size(); i++) {
                if (mCartItem.getmCartItemInfos().get(i).getItemId().equalsIgnoreCase(itemId)) {
                    itemFound = true;
                    foundItemPosition = i;
                    break;
                } else {
                    itemFound = false;
                }
            }
            if (itemFound && foundItemPosition != -1) {
                mCartItem.getmCartItemInfos().get(foundItemPosition).setQty(qty);
                mCartItem.getmCartItemInfos().get(foundItemPosition).setItemUnitPrice(itemUnitPrice);
                mCartItem.getmCartItemInfos().get(foundItemPosition).setItemPrice(Price);
                status = "Item Updated in Cart";
            } else {
                mCartItem.getmCartItemInfos().add(new CartItemInfo(itemId, qty, itemUnitPrice, selectedItem, totalDp, Price, warehouseId, companyId));
                status = "Item Added in Cart";
            }
            for (int i = 0; i < mCartItem.getmCartItemInfos().size(); i++) {
                tempTotalPrice += mCartItem.getmCartItemInfos().get(i).getQty() * mCartItem.getmCartItemInfos().get(i).getItemUnitPrice();
                TotalPrice += mCartItem.getmCartItemInfos().get(i).getQty() * mCartItem.getmCartItemInfos().get(i).getItemPrice();
                tempTotalDpPoint += mCartItem.getmCartItemInfos().get(i).getQty() * mCartItem.getmCartItemInfos().get(i).getItemDpPoint();

                tempTotalQuantity += mCartItem.getmCartItemInfos().get(i).getQty();
            }
            mCartItem.setDeliveryCharges(deliveryCarges);
        } else {
            mCartItem.getmCartItemInfos().add(new CartItemInfo(itemId, qty, itemUnitPrice, selectedItem, totalDp, Price, warehouseId, companyId));
            status = "Item Added in Cart";
            tempTotalPrice += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemUnitPrice();
            TotalPrice += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemPrice();
            tempTotalDpPoint += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemDpPoint();
            tempTotalQuantity += mCartItem.getmCartItemInfos().get(0).getQty();
            mCartItem.setDeliveryCharges(deliveryCarges);

        }

        saveAmount = TotalPrice - tempTotalPrice;
        System.out.println("SaveAmount:::" + saveAmount);
        mCartItem.setTotalPrice(tempTotalPrice);

        mCartItem.setTotalItemPrice(TotalPrice);
        mCartItem.setSavingAmount(saveAmount);
        mCartItem.setTotalQuantity(tempTotalQuantity);

        mCartItem.setTotalDpPoints(tempTotalDpPoint);


        mCartItemArraylistPref.putObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, mCartItem);
        mCartItemArraylistPref.commit();
        return status;
    }

    private void setImagesDynamicSize() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        if (displaymetrics.widthPixels >= 480 && displaymetrics.widthPixels < 720) {
            rowitemImageHeight = 77;
            rowitemImageWidth = 77;
        } else if (displaymetrics.widthPixels >= 720 && displaymetrics.widthPixels < 1080) {
            rowitemImageHeight = 115;
            rowitemImageWidth = 115;
        } else if (displaymetrics.widthPixels >= 1080 && displaymetrics.widthPixels < 1440) {
            rowitemImageHeight = 173;
            rowitemImageWidth = 173;
        } else if (displaymetrics.widthPixels >= 1440) {
            rowitemImageHeight = 230;
            rowitemImageWidth = 230;
        } else {
            rowitemImageHeight = 173;
            rowitemImageWidth = 173;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(CartActivity.this, HomeActivity.class));
        // Fragment fragment = Fragment.instantiate(CartActivity.this, HomeFragItemList.class.getName());
        // fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "HomeFragItemList").commit();

    }


    public void callWalletApi(String id) {

        showLoading();
        //   Toast.makeText(context, "Call Wallet Api called", Toast.LENGTH_SHORT).show();
        String url = Constant.BASE_URL_MY_WALLET + "?CustomerId=" + id;
        new AQuery(getApplicationContext()).ajax(url, null, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {


                if (json == null) {

                    Toast.makeText(CartActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();


                    startActivity(new Intent(CartActivity.this, HomeActivity.class));


                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }


                } else {


                    //  CartActivity.this.finish();


                    try {


                        //     Toast.makeText(CartActivity.this, "Json"+ json.toString(), Toast.LENGTH_SHORT).show();

                        JSONObject jsonObject = json.getJSONObject("conversion");


                        JSONObject jsonObjectWallet = json.getJSONObject("wallet");


                        String totalAmount = jsonObjectWallet.getString("TotalAmount");

                        Utility.setStringSharedPreference(CartActivity.this, "SkWalletAmount", "" + totalAmount);


                        double px = Double.parseDouble(jsonObject.getString("point"));
                        double rx = Double.parseDouble((jsonObject.getString("rupee")));

                        Utility.setStringSharedPreference(CartActivity.this, "px", "" + px);
                        Utility.setStringSharedPreference(CartActivity.this, "rx", "" + rx);


                        if (mDialog.isShowing()) {
                            animation.stop();
                            mDialog.dismiss();
                        }

                        setPoints();


                    } catch (JSONException e) {

                        //  Toast.makeText(CartActivity.this, "Wallet Json error"+e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();

                        if (mDialog.isShowing()) {
                            animation.stop();
                            mDialog.dismiss();
                        }


                    }

                    //   Toast.makeText(SkCodeActivity.this, json.toString(), Toast.LENGTH_SHORT).show();

                    //     Toast.makeText(MyWallet.this, json.toString(), Toast.LENGTH_SHORT).show();


                    //       Toast.makeText(SkCodeActivity.this, "", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    public void showLoading() {


        mDialog = new Dialog(CartActivity.this);
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


    public void setPoints() {
        px = Double.parseDouble(Utility.getStringSharedPreferences(CartActivity.this, "px"));
        rx = Double.parseDouble(Utility.getStringSharedPreferences(CartActivity.this, "rx"));
        rewardPoints = Utility.getStringSharedPreferences(CartActivity.this, "SkWalletAmount");

    }


    public void callTruePay() {


        if (getCartItem().getTotalPrice() < 2000) {
            total = getCartItem().getTotalPrice() + 10;
        } else {
            total = getCartItem().getTotalPrice();
        }
        serviceText = 0.01 * total;
        serviceTextAmount = serviceText + total;
        Entergullakamnt=Double.valueOf(Et1.getText().toString());
        NetPayableamout=serviceTextAmount - Entergullakamnt ;
        System.out.println("ServiceTextAmount::::" + serviceTextAmount);
        System.out.println("TotalAmount:::" + total);
        Log.d("NetPayableamout",String.valueOf(NetPayableamout));
        Intent inNext = new Intent(CartActivity.this, ActivityTrupayIntent.class);
        Bundle bundle = new Bundle();
       // bundle.putString("amount", String.valueOf(NetPayableamout));
        bundle.putString("amount", "1");
        bundle.putString("paymentMethod", "111");
       // bundle.putString("orderNo", String.valueOf(getRandom()));
        bundle.putString(TrupayConstants.SDK_MERCHANT_TXN_REF, String.valueOf(getRandom()));
        /*bundle.putString("accessToken", "6e5794ce-5747-4b63-9d47-d4be0332d2a3");*/
      //  bundle.putString("accessToken", "6a1f6117-6df9-48bc-aa4a-81adc58fd954");
        bundle.putString("accessToken", "6e5794ce-5747-4b63-9d47-d4be0332d2a3");
        bundle.putString("appName", "ShopKirana");
        bundle.putString("collectorId", "13370");
        bundle.putString("requestId", String.valueOf(getRandom()));
        inNext.putExtras(bundle);
        Log.d("TAG bunde ", bundle.toString());
        startActivityForResult(inNext, 10002);

     /*   Intent inNext = new Intent(CartActivity.this, ActivityTrupayIntent.class);
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
            Log.i("onResult", String.valueOf(data));
            Toast.makeText(CartActivity.this, "\nStatus" + bundle.getString("status")
                            + "\nStatus Descriptions" + bundle.getString("statusDesc")
                            + "\nTax amount" + bundle.getString("txnAmount")
                  /*  +bundle.getString("status")
                    +bundle.getString("status")
                    +bundle.getString("status")
                    +bundle.getString("status")*/, Toast.LENGTH_SHORT).show();


            Log.d("truepay", "\nStatus" + bundle.getString("status")
                    + "\nStatus Descriptions" + bundle.getString("statusDesc")
                    + "\nTax amount" + bundle.getString("txnAmount")
            );

            if (bundle.getString("status").equals("F")) {
                Toast.makeText(this, "Payment canceled!", Toast.LENGTH_LONG).show();
            } else if (bundle.getString("status").equals("S")) {
                Toast.makeText(this, "Payment Success!", Toast.LENGTH_LONG).show();
                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(CartActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
                if (mRetailerBean != null && !mRetailerBean.getCustomerId().equalsIgnoreCase("0")) {
                    if (mRetailerBean.getActive().equalsIgnoreCase("false")) {
                        Intent i = new Intent(getApplicationContext(), ActivationActivity.class);
                        //    dialog.dismiss();
                        startActivity(i);
                        CartActivity.this.finish();
                    } else {
                        if (Utils.isInternetConnected(CartActivity.this)) {
                            try {
                                String usedgullakamnt= Et1.getText().toString();
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
                                mRequesParamObj.put("DialEarnigPoint", 0);
                                mRequesParamObj.put("DreamPoint", cartItemBean.getTotalDpPoints());
                                mRequesParamObj.put("TotalAmount", (getCartItem().getTotalPrice()));
                                mRequesParamObj.put("Savingamount", (getCartItem().getSavingAmount()));
                                mRequesParamObj.put("deliveryCharge", cartItemBean.getTotalPrice() > 2000 ? "0" : "10");
                                mRequesParamObj.put("UsedGullakAmount", usedgullakamnt);
                                Log.d("UsedGullakAmount",usedgullakamnt);

                                //  mRequesParamObj.put("deliveryCharge", );

                                mRequesParamObj.put("Trupay", "true");
                                mRequesParamObj.put("OnlineServiceTax", serviceText);
                                mRequesParamObj.put("WalletAmount", amountToReduct);
                                mRequesParamObj.put("walletPointUsed", enterRewardPoint);
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
                             Log.d("RequesParamObj", String.valueOf(mRequesParamObj));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            new AlertDialog.Builder(CartActivity.this)
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

            } else if (bundle.getString("status").equals("P")) {
                Toast.makeText(this, "Payment Pending!", Toast.LENGTH_LONG).show();
            } else if (bundle.getString("status").equals("T")) {
                Toast.makeText(this, "Time out!", Toast.LENGTH_LONG).show();
            }

        }
    }

    public void callChequeBounceApi() {

        //http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/SalesSettlement/SearchSkcode?skcode=SK4201

        new AQuery(CartActivity.this).ajax(Constant.BASE_URL_CHEQUE_BOUNCE + "?skcode=" + skC,
                null,
                String.class,
                new AjaxCallback<String>() {

                    @Override
                    public void callback(String url, String string, AjaxStatus status) {
                        // Toast.makeText(CheckOutActivity.this, "Result : "+string, Toast.LENGTH_SHORT).show();
                        if (string =="true") {
                            new AlertDialog.Builder(CartActivity.this)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("Notice")
                                    .setMessage("Your cheque was bounced!")
                                    .setPositiveButton("Ok", null)
                                    .show();
                        }
                    }
                });
    }


    public void callOrderApi(JSONObject jsonObject) {

        showLoading();
        // Toast.makeText(this, "Json Parameter"+jsonObject, Toast.LENGTH_SHORT).show();

        Log.d("Order Parameter", jsonObject.toString());
Log.d("API77",Constant.BASE_URL_PLACE_ORDER);

        new AQuery(CartActivity.this).post(Constant.BASE_URL_PLACE_ORDER, jsonObject, String.class, new AjaxCallback<String>() {

                    @Override
                    public void callback(String url, String object, AjaxStatus status) {


                        Log.e("Result+++", object);

                        if (object.contains("true")) {

                            Toast.makeText(CartActivity.this, "Order placed successfully", Toast.LENGTH_SHORT).show();
                            clearCartData();
                            CartActivity.this.finish();
                            startActivity(new Intent(CartActivity.this, HomeActivity.class));

                        } else if (object.contains("false")) {

                            enterRewardPoint = 0;
                            Toast.makeText(CartActivity.this, "Unable to place order, please try again", Toast.LENGTH_SHORT).show();
                        } else {
                            enterRewardPoint = 0;
                            Toast.makeText(CartActivity.this, "Unable to place order, please try again", Toast.LENGTH_LONG).show();
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
        String ID;
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(CartActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        ID=mRetailerBean.getCustomerId();
        Log.d("SKID++",ID);
        if (mRetailerBean != null && !mRetailerBean.getCustomerId().equalsIgnoreCase("0")) {
            if (mRetailerBean.getActive().equalsIgnoreCase("false")) {
                Intent i = new Intent(getApplicationContext(), ActivationActivity.class);
                //    dialog.dismiss();
                startActivity(i);
                CartActivity.this.finish();
            } else {
                if (Utils.isInternetConnected(CartActivity.this)) {
                    try {
                        String usedgullakamnt= Et1.getText().toString();
                        CartItemBean cartItemBean = getCartItem();
                        String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        JSONObject mRequesParamObj = new JSONObject();
                        mRequesParamObj.put("CustomerId", mRetailerBean.getCustomerId());
                        mRequesParamObj.put("CreatedDate", currentDateandTime);
                        mRequesParamObj.put("CustomerName", mRetailerBean.getName());
                        mRequesParamObj.put("CustomerType", mRetailerBean.getCustomerType());
                        mRequesParamObj.put("Customerphonenum", mRetailerBean.getMobile());
                        mRequesParamObj.put("SalesPersonId", 0);
                        mRequesParamObj.put("DialEarnigPoint", 0);
                        mRequesParamObj.put("ShippingAddress", mRetailerBean.getShippingAddress());
                        mRequesParamObj.put("ShopName", mRetailerBean.getShopName());
                        mRequesParamObj.put("Skcode", mRetailerBean.getSkcode());
                        mRequesParamObj.put("DreamPoint", cartItemBean.getTotalDpPoints());
                        mRequesParamObj.put("TotalAmount", (getCartItem().getTotalPrice()));
                        mRequesParamObj.put("Savingamount", (getCartItem().getSavingAmount()));
                        mRequesParamObj.put("deliveryCharge", cartItemBean.getTotalPrice() > 2000 ? "0" : "10");
                        //  mRequesParamObj.put("deliveryCharge", );
                        mRequesParamObj.put("OnlineServiceTax", "0");
                        mRequesParamObj.put("WalletAmount", amountToReduct);
                        mRequesParamObj.put("walletPointUsed", enterRewardPoint);
                        mRequesParamObj.put("Trupay", "false");
                        mRequesParamObj.put("UsedGullakAmount", usedgullakamnt);
                        Log.d("UsedGullakAmount",usedgullakamnt);
                        System.out.println("HOOOOOOO" + mRequesParamObj);
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

                        customAlertDialog.dismiss();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    new AlertDialog.Builder(CartActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Error!")
                            .setMessage("Internet connection is not available")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    customAlertDialog.dismiss();

                                }

                            }).show();
                }
                //   dialog.dismiss();
            }
        } else {

            customAlertDialog.dismiss();

            Intent i = new Intent(getApplicationContext(), LoginActivity_Nav.class);
            //   dialog.dismiss();
            startActivity(i);
            CartActivity.this.finish();
        }

    }

    public class MyAsyncTasks extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(CartActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(CartActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
            RetailerBean mRetailerBean  = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
          String  id= mRetailerBean.getCustomerId();
            String apiUrl = "http://137.59.52.130:8080/api/Gullak/getgullakamount?CustId="+id;
            // implement API in background and store the response in current variable
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(apiUrl);

                    urlConnection = (HttpURLConnection) url
                            .openConnection();

                    InputStream in = urlConnection.getInputStream();

                    InputStreamReader isw = new InputStreamReader(in);

                    int data = isw.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isw.read();
                        /*System.out.print(current);*/

                    }
                    // return the data to onPostExecute method
                    return current;

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();

            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {

            Log.d("data", s.toString());
            // dismiss the progress dialog after receiving data from API
            progressDialog.dismiss();
            ArrayList<Gullakpojo> GullakpojoArrayList=new ArrayList<>();
            try {
                // JSON Parsing of data
                JSONObject oneObject= new JSONObject(s);
                /*for(int i=0;i<jsonArray.length();i++) {
                    JSONObject oneObject = jsonArray.getJSONObject(i);*/

                    gullakamount = oneObject.getString("GullakAmount");
                /*String GullakAmount=oneObject.getString("GullakAmount");*/
                    GullakpojoArrayList.add(new Gullakpojo(gullakamount));
                    // Pulling items from the array
                    /*amount.setText("Total Amount:"+gullakamount);*/
                    Log.d("VALUE", String.valueOf(GullakpojoArrayList));
               /* }*/

            } catch (JSONException e) {
                e.printStackTrace();
            }


            /*if (GullakpojoArrayList != null && !GullakpojoArrayList.isEmpty())*/


        }


    }
}
