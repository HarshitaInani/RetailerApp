package app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.utils.TextUtils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.CartActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.Constant;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.HomeActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.SubsubBrands;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.Utils;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.FindHighDpItemAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.HomeFragItemListAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.HomeFragNewlyAddedAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.SubsubBrandsAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.BaseCatSubCatBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.FindHighDpPojo;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.ItemList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by gole on 03-01-2017.
 */

public class HomeFragNewlyAddedBrandList extends Fragment {
  //  int selectedCategoryId = -1;
  //  int selectedWarId = -1;
   // int itemId = -1;
    int rowitemImageHeight=77;
    int rowitemImageWidth=77;
    String bname;
    RecyclerView mItemListRecyclerView;
    RelativeLayout rl1;
    TextView tvTotalItemPrice;
    TextView tvTotalItemQty;
    TextView tvTotalDp;
    TextView tvBrandName;

    HomeFragItemListAdapter mItemListAdapter;

    int mp = 0, pp = 0;
    private static final String TAG = "NewlyAddedItemDetails";
    String locale;

    String hindiLanguage = " हिन्दी (भारत)";

    SharedPreferences sharedpreferences;

    String languageCheck = "e";

    TextView show_popup;
    Dialog mDialog;
    AnimationDrawable animation;

    AsyncTask<String, Void, JSONArray> getNewlyAddedBrandTask;
    HomeFragNewlyAddedAdapter adapter;
    private ArrayList<ItemList> mNewlyAddeddArrayList;
    ArrayList<ItemList> mItemListAllValue;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // selectedCategoryId = getArguments().getInt("selectedCategoryId");
        //selectedWarId = getArguments().getInt("selectedWarId");
     //   itemId = getArguments().getInt("itemId");
        bname = getArguments().getString("SubcategoryName");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.item_list_home_frag_all, container, false);

       // setImagesDynamicSize();
        //   getActivity().getApplicationContext();
        locale = getActivity().getApplicationContext().getResources().getConfiguration().locale.getDisplayName();

        Log.e("language", locale);

        sharedpreferences = getActivity().getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);

        languageCheck = sharedpreferences.getString("language", "");

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mItemListRecyclerView = (RecyclerView) v.findViewById(R.id.item_list_rv);
        mItemListRecyclerView.setNestedScrollingEnabled(false);
        mItemListRecyclerView.setHasFixedSize(true);
        mItemListRecyclerView.setLayoutManager(linearLayoutManager);
        mItemListRecyclerView.setAdapter(mItemListAdapter);

        show_popup=(TextView)v.findViewById(R.id.show_popup) ;
        rl1=(RelativeLayout)v.findViewById(R.id.rl1);
        tvTotalItemPrice=(TextView)v.findViewById(R.id.item_list_total_amount_tv);
        tvTotalItemQty=(TextView)v.findViewById(R.id.item_list_total_item_tv);
        tvTotalDp=(TextView)v.findViewById(R.id.item_list_total_dp_tv);
        tvBrandName=(TextView)v.findViewById(R.id.brand_name);
        tvBrandName.setText(bname);



        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getActivity().startActivity(new Intent(getActivity(), CartActivity.class));
            }
        });


    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTotalItemPrice.setText("Total: " + new DecimalFormat("##.##").format(((HomeActivity) getActivity()).getCartItem().getTotalPrice()));
        tvTotalItemQty.setText("" + (int) ((HomeActivity) getActivity()).getCartItem().getTotalQuantity());
        tvTotalDp.setText("Dp : " + new DecimalFormat("##.##").format(((HomeActivity) getActivity()).getCartItem().getTotalDpPoints()));

        if (Utils.isInternetConnected(getActivity())) {

           /* getNewlyAddedBrandTask =*/ new GetNewlyAddedBrandItem().execute();

        } else {
            new AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error!")
                    .setMessage("Internet connection is not available")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }

                    })
                    //.setNegativeButton("No", null)
                    .show();
        }
    }





    private void setImagesDynamicSize() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
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




    public void showLoading() {
        mDialog = new Dialog(getActivity());
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



    public class GetNewlyAddedBrandItem extends AsyncTask<String, Void, Integer> {
        @Override
        protected void onPreExecute() {
           showLoading();
        }
        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            String url1=Constant.BASE_URL_NEWLY_ADDED_BRAND_ITEM+"/GetAllItemByBrand?warid=1&brandName="+bname;
            String url2=url1.replaceAll(" ", "%20");
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(url2);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();
                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1;
                } else {
                    result = 0;
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result;
        }
        @Override
        protected void onPostExecute(Integer result) {

            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
            }
            if (result == 1) {
                adapter = new HomeFragNewlyAddedAdapter(getActivity(), mNewlyAddeddArrayList,rowitemImageWidth, rowitemImageHeight, tvTotalItemPrice, tvTotalItemQty, tvTotalDp,show_popup,mItemListAllValue,getFragmentManager());
                mItemListRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getActivity(), "No Item Found", Toast.LENGTH_SHORT).show();
                //  startActivity(new_added Intent(SubsubBrands.this,Allbrands.class));
              //  finish();
              //  getActivity().finish();
            }
        }
    }


    private void parseResult(String result) {
        System.out.println("Json11:::"+result);
        try {
            JSONObject response = new JSONObject(result);
            JSONArray mItemsJsonArray = response.optJSONArray("ItemMasters");
            mNewlyAddeddArrayList = new ArrayList<>();
            mItemListAllValue = new ArrayList<>();

            if (response.getString("ItemMasters").equals("[]")) {
                Toast.makeText(getActivity(), "No Item Found", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }else
            {
                for (int i = 0; i < mItemsJsonArray.length(); i++) {

                    String ItemId = mItemsJsonArray.getJSONObject(i).getString("ItemId");
                    String Categoryid = mItemsJsonArray.getJSONObject(i).getString("Categoryid");
                    String SubCategoryId = mItemsJsonArray.getJSONObject(i).getString("SubCategoryId");
                    String SubsubCategoryid = mItemsJsonArray.getJSONObject(i).getString("SubsubCategoryid");
                    String itemname = mItemsJsonArray.getJSONObject(i).getString("itemname");
                    String price = mItemsJsonArray.getJSONObject(i).getString("price");
                    String SellingUnitName = mItemsJsonArray.getJSONObject(i).getString("SellingUnitName");
                    String SellingSku = mItemsJsonArray.getJSONObject(i).getString("SellingSku");
                    String UnitPrice = mItemsJsonArray.getJSONObject(i).getString("UnitPrice");
                    String VATTax = mItemsJsonArray.getJSONObject(i).getString("VATTax");
                    String LogoUrl = mItemsJsonArray.getJSONObject(i).getString("LogoUrl");
                    String MinOrderQty = mItemsJsonArray.getJSONObject(i).getString("MinOrderQty");
                    String Discount = mItemsJsonArray.getJSONObject(i).getString("Discount");
                    String TotalTaxPercentage = mItemsJsonArray.getJSONObject(i).getString("TotalTaxPercentage");
                    String itemNumber = mItemsJsonArray.getJSONObject(i).getString("ItemNumber");
                    String HindiName = mItemsJsonArray.getJSONObject(i).getString("HindiName");
                    String DpPoint = "";
                    String PromoPoint = mItemsJsonArray.getJSONObject(i).getString("promoPerItems");
                    String MarginPoint = mItemsJsonArray.getJSONObject(i).getString("marginPoint");
                    String warehouseId = mItemsJsonArray.getJSONObject(i).getString("WarehouseId");
                    String companyId = mItemsJsonArray.getJSONObject(i).getString("CompanyId");
                    String Isoffer = mItemsJsonArray.getJSONObject(i).getString("Isoffer");


                    if (PromoPoint.trim().equals("null")) {
                        pp = 0;

                    }


                    if (PromoPoint.isEmpty()) {
                        pp = 0;

                    } else if (PromoPoint.trim().equals("null")) {
                        pp = 0;

                    } else {
                        pp = Integer.parseInt(PromoPoint);

                    }


                    if (MarginPoint.trim().equals("null")) {

                        mp = 0;
                    }


                    if (MarginPoint.isEmpty()) {

                        mp = 0;
                    } else if (MarginPoint.trim().equals("null")) {

                        mp = 0;
                    } else {
                        mp = Integer.parseInt(MarginPoint);

                    }

                    if (pp > 0 && mp > 0) {
                        int pp_mp = pp + mp;

                        DpPoint = "" + pp_mp;
                    } else if (mp > 0) {
                        DpPoint = "" + mp;
                    } else if (pp > 0) {
                        DpPoint = "" + pp;
                    } else {
                        DpPoint = "0";
                    }


                    mItemListAllValue.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, warehouseId, companyId,itemNumber,Isoffer));
                    if(mNewlyAddeddArrayList.size()==0)
                    {
                        mNewlyAddeddArrayList.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, warehouseId, companyId,itemNumber,Isoffer));

                    }else
                    {
                        boolean ispresent=false;
                        for (int j = 0; j <mNewlyAddeddArrayList.size() ; j++) {

                            if(mNewlyAddeddArrayList.get(j).getItemNumber().equalsIgnoreCase(itemNumber))
                            {
                                ispresent=true;
                                break;
                            }
                        }
                        if(!ispresent)
                        {

                            mNewlyAddeddArrayList.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, warehouseId, companyId,itemNumber,Isoffer));

                        }else
                        {

                        }
                    }



                }

                Collections.sort(mItemListAllValue, new ComparatorOfNumericString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
                Log.e("HomeFragApi", key + " is not available which should come in response");
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public class ComparatorOfNumericString implements Comparator<ItemList> {
        @SuppressLint("NewApi")
        @Override
        public int compare(ItemList lhs, ItemList rhs) {
            int i1 = Integer.parseInt(lhs.getMinOrderQty());
            int i2 = Integer.parseInt(rhs.getMinOrderQty());
            //Log.d("Collection String::", String.valueOf(Integer.compare(i1, i2)));
            return Integer.compare(i1, i2);
        }

    }
}