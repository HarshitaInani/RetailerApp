package app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.CartActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.Constant;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.HomeActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.Utils;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.SearchFragListAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.ItemList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.databinding.SearchFragBinding;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Krishna on 03-01-2017.
 */

public class SearchFragItemList extends Fragment {
    int rowitemImageHeight=77;
    int rowitemImageWidth=77;


    RecyclerView mItemListRecyclerView;
    AsyncTask<String, Void, JSONArray> mSearchItemAsyncTask;

    TextView tvTotalItemPrice;
    TextView tvTotalItemQty;


    TextView tvTotalDp;



    EditText edtSearchEditText,edtSearchBarCodeEditText;
    LinearLayout fragbarcodeler,ll1;
    AlertDialog mAlertDialog;
    ProgressBar mProgressBar;


    ArrayList<ItemList> mItemListArrayList = new ArrayList<>();
    SearchFragListAdapter mSearchFragAdapter;


    int mp = 0, pp = 0;

    int xPoints = 2;

    TextView show_popup;
    TextView tvSearchBarcode;
    ArrayList<ItemList> mItemListAllValue= new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        SearchFragBinding searchFragBinding = DataBindingUtil.inflate(inflater, R.layout.search_frag, container, false);

       // SearchFragBinding searchFragBinding = DataBindingUtil.inflate(inflater, R.layout.search_frag, container, false);


        View view = searchFragBinding.getRoot();
        //setImagesDynamicSize();

        tvTotalItemPrice = searchFragBinding.fragSearchTotalAmountTv;
        tvTotalItemQty = searchFragBinding.fragSearchTotalItemTv;


        tvTotalDp = searchFragBinding.fragSearchTotalDpTv;
        tvSearchBarcode = searchFragBinding.searchBarcode;

        show_popup=(TextView)view.findViewById(R.id.show_popup) ;
        fragbarcodeler=(LinearLayout)view.findViewById(R.id.frag_search_barcode_ler) ;
        ll1=(LinearLayout)view.findViewById(R.id.ll1) ;

        edtSearchEditText = searchFragBinding.fragSearchEdt;
        edtSearchBarCodeEditText = searchFragBinding.fragSearchBarcode;



        mProgressBar = searchFragBinding.searchFragSearchBar;
        mProgressBar.setVisibility(View.INVISIBLE);
        mItemListRecyclerView = searchFragBinding.fragSearchRv;
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mItemListRecyclerView.setLayoutManager(layoutManager2);

        mSearchFragAdapter = new SearchFragListAdapter(getActivity(), mItemListArrayList, rowitemImageWidth, rowitemImageHeight, tvTotalItemPrice, tvTotalItemQty , tvTotalDp,show_popup,mItemListAllValue);
        mItemListRecyclerView.setAdapter(mSearchFragAdapter);


        callAqueryForSearch("1");


        searchFragBinding.rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), CartActivity.class));
            }
        });

        return view;



    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

              /*  if(i==0)
                {
                    fragbarcodeler.setVisibility(View.VISIBLE);
                }*/
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchString = editable.toString();
                if (searchString.length() > 2) {
                    if (mSearchItemAsyncTask != null && !mSearchItemAsyncTask.isCancelled()) {
                        mSearchItemAsyncTask.cancel(true);
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                    //fragbarcodeler.setVisibility(View.GONE);
                    if (Utils.isInternetConnected(getActivity())) {

                        //mSearchItemAsyncTask = new_added searchItemAsyncTask().execute(searchString);

                        callAqueryForSearch(searchString);

                    } else {
                        if (mAlertDialog != null && mAlertDialog.isShowing())
                            mAlertDialog.dismiss();
                        mAlertDialog = new AlertDialog.Builder(getActivity())
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Error!")
                                .setCancelable(true)
                                .setMessage("Internet connection is not available")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
                    }
                }
            }
        });
        edtSearchBarCodeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

/*
                System.out.println("i::"+i);
                if(i==0)
                {
                    ll1.setVisibility(View.VISIBLE);
                }*/
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchString = editable.toString();
             /*   if (searchString.length() > 2) {
                    ll1.setVisibility(View.GONE);
                }*/

            }
        });
        tvSearchBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  edtSearchEditText.setVisibility(edtSearchEditText.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                String searchBarcode=edtSearchBarCodeEditText.getText().toString();
                if (Utils.isInternetConnected(getActivity())) {
                    //mSearchItemAsyncTask = new_added searchItemAsyncTask().execute(searchString);
                    callAqueryForSearchBarCode(searchBarcode,"3");



                } else {
                    if (mAlertDialog != null && mAlertDialog.isShowing())
                        mAlertDialog.dismiss();
                    mAlertDialog = new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Error!")
                            .setCancelable(true)
                            .setMessage("Internet connection is not available")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                }
            }
        });

        tvTotalItemPrice.setText("Total: " + new DecimalFormat("##.##").format(((HomeActivity) getActivity()).getCartItem().getTotalPrice()));
        tvTotalItemQty.setText("" + (int) ((HomeActivity) getActivity()).getCartItem().getTotalQuantity());


        tvTotalDp.setText("Dp : "+ new DecimalFormat("##.##").format(((HomeActivity) getActivity()).getCartItem().getTotalDpPoints()));


    }

    @Override
    public void onPause() {
        if (mSearchItemAsyncTask != null && !mSearchItemAsyncTask.isCancelled())
            mSearchItemAsyncTask.cancel(true);
        super.onPause();
    }


    public class searchItemAsyncTask extends AsyncTask<String, Void, JSONArray> {
        Dialog mDialog;

        @Override
        protected void onPreExecute() {
         /*   mDialog = new_added Dialog(getActivity());
            mDialog.getWindow().setBackgroundDrawable(new_added ColorDrawable(Color.WHITE));
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Loading items please wait...");
            mDialog.setCancelable(false);
            mDialog.show();*/
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArrayFromurl = null;
            try {
                jsonArrayFromurl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_ITEM_MASTER + "?itemname=" + params[0] + "&x=city", null, HttpUrlConnectionJSONParser.Http.GET);
                if (jsonArrayFromurl != null) {
                    mItemListArrayList.clear();
                    mItemListAllValue.clear();
                    if (jsonArrayFromurl.length() > 0) {
                        for (int i = 0; i < jsonArrayFromurl.length(); i++) {
                            String ItemId = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "ItemId");
                            String UnitId = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "UnitId");
                            String Categoryid = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "Categoryid");
                            String SubCategoryId = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "SubCategoryId");
                            String SubsubCategoryid = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "SubsubCategoryid");
                            String itemname = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "itemname");
                            String HindiName = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "HindiName");
                            String UnitName = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "UnitName");
                            String PurchaseUnitName = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "PurchaseUnitName");
                            String price = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "price");
                            String SellingUnitName = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "SellingUnitName");
                            String SellingSku = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "SellingSku");
                            String UnitPrice = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "UnitPrice");
                            String VATTax = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "VATTax");
                            String LogoUrl = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "LogoUrl");
                            String MinOrderQty = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "MinOrderQty");
                            String Discount = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "Discount");
                            String TotalTaxPercentage = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "TotalTaxPercentage");
                            String itemNumber = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "Number");



                            String DpPoint = "0";

//                            String PromoPoint = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "promoPoint");
                            String PromoPoint = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "promoPerItems");

//                            String PromoPoint = "0";



                            String MarginPoint = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "marginPoint");



                            String warehouseId = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "WarehouseId");
                            String companyId = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "CompanyId");
                            String Isoffer = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "Isoffer");

//                            String MarginPoint = "0";


                            if(PromoPoint.trim().equals("null")) {
                                pp = 0;

                            }


                            if(PromoPoint.isEmpty()) {
                                pp = 0;

                            }


                            else if(PromoPoint.trim().equals("null")) {
                                pp = 0;

                            }

                            else {
                            //    pp = Integer.parseInt(PromoPoint) * xPoints;

                                pp = Integer.parseInt(PromoPoint);

                            }


                            if(MarginPoint.trim().equals("null")) {

                                mp = 0;
                            }


                            if(MarginPoint.isEmpty()) {

                                mp = 0;
                            }

                            else if(MarginPoint.trim().equals("null")) {

                                mp = 0;
                            }

                            else {
                                mp = Integer.parseInt(MarginPoint) * xPoints;

                            }
                            if(pp > 0 && mp > 0) {
                                int pp_mp = pp + mp;

                                DpPoint = ""+pp_mp;
                            }
                            else if (mp > 0) {
                                DpPoint = ""+mp;
                            }
                            else if (pp > 0) {
                                DpPoint = ""+pp;
                            }

                            else {
                                DpPoint = "0";
                            }

                            mItemListArrayList.add(new ItemList(ItemId, UnitId, Categoryid, SubCategoryId, SubsubCategoryid, itemname, UnitName, PurchaseUnitName, price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, warehouseId, companyId,itemNumber,Isoffer));
                            publishProgress();
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Server not responding properly", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayFromurl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if (jsonArray != null && jsonArray.length() > 0) {
                if (getActivity() != null) {
                    if (!mItemListArrayList.isEmpty()) {
                        mSearchFragAdapter.notifyDataSetChanged();/* = new_added SearchFragListAdapter(getActivity(), mItemListArrayList, rowitemImageWidth, rowitemImageHeight, tvTotalItemPrice, tvTotalItemQty);
                        mItemListRecyclerView.setAdapter(mSearchFragAdapter);*/
                    } else {
                        Toast.makeText(getActivity(), "Items are not available", Toast.LENGTH_SHORT).show();
                        getFragmentManager().popBackStack();
                    }
                }
            } else {
                Toast.makeText(getActivity(), "Improper response from server", Toast.LENGTH_SHORT).show();
            }
            mProgressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            if (getActivity() != null) {
                if (mItemListArrayList != null && !mItemListArrayList.isEmpty()) {
                    mSearchFragAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Items are not available", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                }
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
                Log.e("HomeFragItemListFrag", key + " is not available which should come in response");
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
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

   public void callAqueryForSearch(String item) {


        mProgressBar.setVisibility(View.VISIBLE);


        //http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/itemMaster?itemname=garner&x=city

        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        final RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);

              String CustomerId=mRetailerBean.getCustomerId();
        String url;
       // String url = Constant.BASE_URL_ITEM_MASTER + "?itemname=" + item + "&x=city";
        if(item.equalsIgnoreCase("1"))
        {
             url = Constant.BASE_URL_BRAND_WISE_PROMOTION;
        }else{
           url = Constant.BASE_URL_ITEM_SEARCH + "?itemname=" + item + "&CustomerId="+CustomerId;
       }

        System.out.println(url);
        new AQuery(getActivity()).ajax(url, null, JSONArray.class, new AjaxCallback<JSONArray>(){


            @Override
            public void callback(String url, JSONArray jsonArrayFromurl, AjaxStatus status) {
                Log.e("onResponse Search null", jsonArrayFromurl.toString());

                if(jsonArrayFromurl.toString().equalsIgnoreCase("[]"))
                {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "Items are not available", Toast.LENGTH_SHORT).show();
                }else if (jsonArrayFromurl != null) {


                    //   Toast.makeText(getActivity(), ""+jsonArrayFromurl.toString(), Toast.LENGTH_SHORT).show();
                    try{
                        mItemListArrayList.clear();
                        mItemListAllValue.clear();
                        if (jsonArrayFromurl.length() > 0) {
                            for (int i = 0; i < jsonArrayFromurl.length(); i++) {
                                String ItemId = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "ItemId");
                               // String UnitId = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "UnitId");
                                String Categoryid = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "Categoryid");
                                String SubCategoryId = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "SubCategoryId");
                                String SubsubCategoryid = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "SubsubCategoryid");
                                String itemname = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "itemname");
                              //  String UnitName = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "UnitName");
                                String PurchaseUnitName = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "PurchaseUnitName");
                                String price = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "price");
                                String SellingUnitName = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "SellingUnitName");
                                String SellingSku = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "SellingSku");
                                String UnitPrice = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "UnitPrice");
                                String VATTax = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "VATTax");
                                String LogoUrl = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "LogoUrl");
                                String MinOrderQty = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "MinOrderQty");
                                String Discount = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "Discount");
                                String TotalTaxPercentage = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "TotalTaxPercentage");
                                String itemNumber = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "Number");
                                String HindiName = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "HindiName");
                                String DpPoint = "0";

//                            String PromoPoint = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "promoPoint");
                                String PromoPoint = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "promoPerItems");

//                            String PromoPoint = "0";



                                String MarginPoint = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "marginPoint");




//                            String MarginPoint = "0";



                                String warehouseId = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "WarehouseId");
                                String companyId = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "CompanyId");
                                String Isoffer = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "IsOffer");


                                if(PromoPoint.trim().equals("null")) {
                                    pp = 0;

                                }


                                if(PromoPoint.isEmpty()) {
                                    pp = 0;

                                }


                                else if(PromoPoint.trim().equals("null")) {
                                    pp = 0;

                                }

                                else {
                                    pp = Integer.parseInt(PromoPoint);

                                }


                                if(MarginPoint.trim().equals("null")) {

                                    mp = 0;
                                }


                                if(MarginPoint.isEmpty()) {

                                    mp = 0;
                                }

                                else if(MarginPoint.trim().equals("null")) {

                                    mp = 0;
                                }

                                else {
                                    mp = Integer.parseInt(MarginPoint)  * xPoints;

                                }

                                if(pp > 0 && mp > 0) {
                                    int pp_mp = pp + mp;

                                    DpPoint = ""+pp_mp;
                                }
                                else if (mp > 0) {
                                    DpPoint = ""+mp;
                                }
                                else if (pp > 0) {
                                    DpPoint = ""+pp;
                                }

                                else {
                                    DpPoint = "0";
                                }

                                //mItemListArrayList.get(i).



                               // mItemListArrayList.add(new ItemList(ItemId, UnitId, Categoryid, SubCategoryId, SubsubCategoryid, itemname, UnitName, PurchaseUnitName, price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, warehouseId, companyId,itemNumber));
                                //    publishProgress();



                                mItemListAllValue.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", PurchaseUnitName, price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, warehouseId, companyId,itemNumber,Isoffer));

                                if(mItemListArrayList.size()==0)
                                {
                                   mItemListArrayList.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", PurchaseUnitName, price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, warehouseId, companyId,itemNumber,Isoffer));
                                }else
                                {
                                    boolean ispresent=false;
                                    for (int j = 0; j <mItemListArrayList.size() ; j++) {
                                        //   Log.d("click1","ItemNumber1122::"+mItemListArrayList.get(j).getItemNumber());
                                        if(mItemListArrayList.get(j).getItemNumber().equalsIgnoreCase(itemNumber))
                                        {
                                            ispresent=true;
                                            break;
                                        }
                                    }
                                    if(!ispresent)
                                    {
                                        // System.out.println("Id:1::"+ItemId);
                                        mItemListArrayList.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", PurchaseUnitName, price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, warehouseId, companyId,itemNumber,Isoffer));                                        //Log.d("click1","list show::"+mItemListArrayList);
                                        // values.add(itemname);
                                    }else
                                    {
                                        //  System.out.println("Id:2::"+ItemId);
                                        //values.add(itemname);
                                        // mItemListAllValue.add(new_added ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint,ItemNumber));
                                        // Log.d("click1","list Remove::"+values);
                                    }
                                }








                                if (!mItemListArrayList.isEmpty()) {
                                    // mSearchFragAdapter.
                                    mSearchFragAdapter.notifyDataSetChanged();
                                    mItemListRecyclerView.setAdapter(mSearchFragAdapter);

                                    mProgressBar.setVisibility(View.INVISIBLE);


                                    //   mSearchFragAdapter.notifyItemRangeChanged(0,2);
                     /*   mSearchFragAdapter.notifyItemChanged(0);
                        mSearchFragAdapter.notifyItemChanged(1);
                        mSearchFragAdapter.notifyItemChanged(2);
                        mSearchFragAdapter.notifyItemChanged(3);
                        mSearchFragAdapter.notifyItemChanged(4);
                     */
                        /* = new_added SearchFragListAdapter(getActivity(), mItemListArrayList, rowitemImageWidth, rowitemImageHeight, tvTotalItemPrice, tvTotalItemQty);
                        mItemListRecyclerView.setAdapter(mSearchFragAdapter);*/



                                } else {

                                    mProgressBar.setVisibility(View.INVISIBLE);

                                    Toast.makeText(getActivity(), "Items are not available", Toast.LENGTH_SHORT).show();
                                    getFragmentManager().popBackStack();
                                }
                        }


                            Collections.sort(mItemListAllValue, new ComparatorOfNumericString());
                        }








                    } catch (Exception e) {

                        mProgressBar.setVisibility(View.INVISIBLE);


                    }



                }

                else {

                    mProgressBar.setVisibility(View.INVISIBLE);

                    Toast.makeText(getActivity(), "Item not found!", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }


    public void callAqueryForSearchBarCode(String item,String valid) {


        mProgressBar.setVisibility(View.VISIBLE);


        //http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/itemMaster?itemname=garner&x=city

        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        final RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);

        String CustomerId=mRetailerBean.getCustomerId();
        String url;

            url = Constant.BASE_URL_ITEM_SEARCH + "/GetItemByBarcode?Barcode="+item ;



        System.out.println(url);
        new AQuery(getActivity()).ajax(url, null, String.class, new AjaxCallback<String>(){


            @Override
            public void callback(String url, String jsonArrayFromurl, AjaxStatus status) {
                Log.e("onResponse  Barcode", jsonArrayFromurl.toString());

                if(jsonArrayFromurl.toString().equalsIgnoreCase("null"))
                {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "Items are not available", Toast.LENGTH_SHORT).show();
                }else if (jsonArrayFromurl != null) {


                    //  Toast.makeText(getActivity(), ""+jsonArrayFromurl.toString(), Toast.LENGTH_SHORT).show();
                    try{
                        mItemListArrayList.clear();
                        mItemListAllValue.clear();
                        JSONObject jsonObject =new JSONObject(jsonArrayFromurl);
                        if (jsonArrayFromurl.length() > 0) {
                            //String ItemId = jsonObject.getString("ItemId");
                                String ItemId = isNullOrEmpty(jsonObject, "ItemId");
                              //  String UnitId = isNullOrEmpty(jsonObject, "UnitId");

                                String Categoryid = isNullOrEmpty(jsonObject, "Categoryid");
                                String SubCategoryId = isNullOrEmpty(jsonObject, "SubCategoryId");
                                String SubsubCategoryid = isNullOrEmpty(jsonObject, "SubsubCategoryid");
                                String itemname = isNullOrEmpty(jsonObject, "itemname");
                              //  String UnitName = isNullOrEmpty(jsonObject, "UnitName");
                                String PurchaseUnitName = isNullOrEmpty(jsonObject, "PurchaseUnitName");
                                String price = isNullOrEmpty(jsonObject, "price");
                                String SellingUnitName = isNullOrEmpty(jsonObject, "SellingUnitName");
                                String SellingSku = isNullOrEmpty(jsonObject, "SellingSku");
                                String UnitPrice = isNullOrEmpty(jsonObject, "UnitPrice");
                                String VATTax = isNullOrEmpty(jsonObject, "VATTax");
                                String LogoUrl = isNullOrEmpty(jsonObject, "LogoUrl");
                                String MinOrderQty = isNullOrEmpty(jsonObject, "MinOrderQty");
                                String Discount = isNullOrEmpty(jsonObject, "Discount");
                                String TotalTaxPercentage = isNullOrEmpty(jsonObject, "TotalTaxPercentage");
                                String itemNumber = isNullOrEmpty(jsonObject, "Number");
                                String HindiName = isNullOrEmpty(jsonObject, "HindiName");
                                String DpPoint = "0";

//                            String PromoPoint = isNullOrEmpty(jsonArrayFromurl.getJSONObject(i), "promoPoint");
                                String PromoPoint = isNullOrEmpty(jsonObject, "promoPerItems");

//                            String PromoPoint = "0";



                                String MarginPoint = isNullOrEmpty(jsonObject, "marginPoint");




//                            String MarginPoint = "0";



                                String warehouseId = isNullOrEmpty(jsonObject, "WarehouseId");
                                String companyId = isNullOrEmpty(jsonObject, "CompanyId");
                                String Isoffer = isNullOrEmpty(jsonObject, "IsOffer");


                                if(PromoPoint.trim().equals("null")) {
                                    pp = 0;

                                }


                                if(PromoPoint.isEmpty()) {
                                    pp = 0;

                                }


                                else if(PromoPoint.trim().equals("null")) {
                                    pp = 0;

                                }

                                else {
                                    pp = Integer.parseInt(PromoPoint);

                                }


                                if(MarginPoint.trim().equals("null")) {

                                    mp = 0;
                                }


                                if(MarginPoint.isEmpty()) {

                                    mp = 0;
                                }

                                else if(MarginPoint.trim().equals("null")) {

                                    mp = 0;
                                }

                                else {
                                    mp = Integer.parseInt(MarginPoint)  * xPoints;

                                }

                                if(pp > 0 && mp > 0) {
                                    int pp_mp = pp + mp;

                                    DpPoint = ""+pp_mp;
                                }
                                else if (mp > 0) {
                                    DpPoint = ""+mp;
                                }
                                else if (pp > 0) {
                                    DpPoint = ""+pp;
                                }

                                else {
                                    DpPoint = "0";
                                }
                                //mItemListArrayList.get(i).



                                // mItemListArrayList.add(new ItemList(ItemId, UnitId, Categoryid, SubCategoryId, SubsubCategoryid, itemname, UnitName, PurchaseUnitName, price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, warehouseId, companyId,itemNumber));
                                //    publishProgress();



                                mItemListAllValue.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", PurchaseUnitName, price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, warehouseId, companyId,itemNumber,Isoffer));

                                if(mItemListArrayList.size()==0)
                                {
                                    mItemListArrayList.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", PurchaseUnitName, price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, warehouseId, companyId,itemNumber,Isoffer));
                                }else
                                {
                                    boolean ispresent=false;
                                    for (int j = 0; j <mItemListArrayList.size() ; j++) {
                                        //   Log.d("click1","ItemNumber1122::"+mItemListArrayList.get(j).getItemNumber());
                                        if(mItemListArrayList.get(j).getItemNumber().equalsIgnoreCase(itemNumber))
                                        {
                                            ispresent=true;
                                            break;
                                        }
                                    }
                                    if(!ispresent)
                                    {
                                        // System.out.println("Id:1::"+ItemId);
                                        mItemListArrayList.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", PurchaseUnitName, price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, warehouseId, companyId,itemNumber,Isoffer));                                        //Log.d("click1","list show::"+mItemListArrayList);
                                        // values.add(itemname);
                                    }else
                                    {
                                        //  System.out.println("Id:2::"+ItemId);
                                        //values.add(itemname);
                                        // mItemListAllValue.add(new_added ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint,ItemNumber));
                                        // Log.d("click1","list Remove::"+values);
                                    }
                                }







                                if (!mItemListArrayList.isEmpty()) {
                                    // mSearchFragAdapter.
                                    mSearchFragAdapter.notifyDataSetChanged();
                                    mItemListRecyclerView.setAdapter(mSearchFragAdapter);

                                    mProgressBar.setVisibility(View.INVISIBLE);


                                    //   mSearchFragAdapter.notifyItemRangeChanged(0,2);
                     /*   mSearchFragAdapter.notifyItemChanged(0);
                        mSearchFragAdapter.notifyItemChanged(1);
                        mSearchFragAdapter.notifyItemChanged(2);
                        mSearchFragAdapter.notifyItemChanged(3);
                        mSearchFragAdapter.notifyItemChanged(4);
                     */
                        /* = new_added SearchFragListAdapter(getActivity(), mItemListArrayList, rowitemImageWidth, rowitemImageHeight, tvTotalItemPrice, tvTotalItemQty);
                        mItemListRecyclerView.setAdapter(mSearchFragAdapter);*/



                                } else {

                                    mProgressBar.setVisibility(View.INVISIBLE);

                                    Toast.makeText(getActivity(), "Items are not available", Toast.LENGTH_SHORT).show();
                                    getFragmentManager().popBackStack();
                                }



                            Collections.sort(mItemListAllValue, new ComparatorOfNumericString());
                        }








                    } catch (Exception e) {

                        mProgressBar.setVisibility(View.INVISIBLE);


                    }



                }

                else {

                    mProgressBar.setVisibility(View.INVISIBLE);

                    Toast.makeText(getActivity(), "Item not found!", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }



    public class ComparatorOfNumericString implements Comparator<ItemList> {
        @SuppressLint("NewApi")
        @Override
        public int compare(ItemList lhs, ItemList rhs) {
            int i1 = Integer.parseInt(lhs.getMinOrderQty());
            int i2 = Integer.parseInt(rhs.getMinOrderQty());
          //  Log.d("Collection String::", String.valueOf(Integer.compare(i1, i2)));
            return Integer.compare(i1, i2);
        }

    }


}