package app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment;

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

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.CartActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.Constant;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.HomeActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.Utils;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.ItemListSubSubCatAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.PopularItemListAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.ItemList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.SubSubCategoriesBean;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by gole on 03-01-2017.
 */

public class HomeFragItemPopularItemList extends Fragment {
    int selectedCategoryId = -1;
    int selectedWarId = -1;
    int itemId = -1;
    int rowitemImageHeight=77;
    int rowitemImageWidth=77;


    RecyclerView mItemListRecyclerView;
    RelativeLayout rl1;
    AsyncTask<String, Void, ArrayList<ItemList>> mItemListAsyncTask;
    AsyncTask<String, Void, ArrayList<SubSubCategoriesBean>> mSubSubCatFilterTask;

    TextView tvTotalItemPrice;
    TextView tvTotalItemQty;

    TextView tvTotalDp;


    boolean showDialog = true;
    ArrayList<ItemList> mItemListArrayList = new ArrayList<>();


    PopularItemListAdapter mItemListAdapter;
    ItemListSubSubCatAdapter itemListSubSubCatAdapter;


    int mp = 0, pp = 0;

    String locale;

    String hindiLanguage = " हिन्दी (भारत)";

    SharedPreferences sharedpreferences;

    String languageCheck = "e";


    TextView show_popup;
    Dialog mDialog;
    AnimationDrawable animation;
    ArrayList<ItemList> mItemListAllValue= new ArrayList<>();
    TextView tvBrandName;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedCategoryId = getArguments().getInt("selectedCategoryId");
        selectedWarId = getArguments().getInt("selectedWarId");
        itemId = getArguments().getInt("itemId");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.item_list_frag_brand_item, container, false);

        //setImagesDynamicSize();
        //   getActivity().getApplicationContext();
        locale = getActivity().getApplicationContext().getResources().getConfiguration().locale.getDisplayName();

        Log.e("language", locale);

        sharedpreferences = getActivity().getApplicationContext().getSharedPreferences("MyPrefs",
                MODE_PRIVATE);

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
        tvBrandName=(TextView)v.findViewById(R.id.item_list_frag_selected_tv);

        ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);


            ArrayList<ItemList> mPopularBrandBeenArrayList1 = new ArrayList<>();
            Type typePopularBrandBeanArrayList1 = new TypeToken<ArrayList<ItemList>>() {}.getType();
            mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.POPULAR_BRANDS_PREF1, getActivity().MODE_PRIVATE);

        mPopularBrandBeenArrayList1 = mBaseCatSubCatCat1.getArray(Constant.POPULAR_BRANDS_PREFOBJ1,typePopularBrandBeanArrayList1);
        System.out.println("mPopularBrandBeenArrayList1::::"+mPopularBrandBeenArrayList1);
        ArrayList<ItemList> mPopularBrandBeenArrayList = new ArrayList<>();

        for (int i = 0; i < mPopularBrandBeenArrayList1.size(); i++) {

            System.out.println("ItemId11::"+mPopularBrandBeenArrayList1.get(i).getItemId());
            System.out.println("ItemId22::"+itemId);
            if(mPopularBrandBeenArrayList1.get(i).getItemId().equalsIgnoreCase(String.valueOf(itemId)))
            {

              String  ItemId=mPopularBrandBeenArrayList1.get(i).getItemId();
              String  Categoryid=mPopularBrandBeenArrayList1.get(i).getCategoryid();
              String  SubCategoryId=mPopularBrandBeenArrayList1.get(i).getSubCategoryId();
              String  SubsubCategoryid=mPopularBrandBeenArrayList1.get(i).getSubsubCategoryid();
              String  itemname=mPopularBrandBeenArrayList1.get(i).getItemname();
                tvBrandName.setText(itemname);
              String  price=mPopularBrandBeenArrayList1.get(i).getPrice();
              String  SellingUnitName=mPopularBrandBeenArrayList1.get(i).getSellingUnitName();
              String  SellingSku=mPopularBrandBeenArrayList1.get(i).getSellingSku();
              String  UnitPrice=mPopularBrandBeenArrayList1.get(i).getUnitPrice();
              String  VATTax=mPopularBrandBeenArrayList1.get(i).getVATTax();
              String  LogoUrl=mPopularBrandBeenArrayList1.get(i).getLogoUrl();
              String  MinOrderQty=mPopularBrandBeenArrayList1.get(i).getMinOrderQty();
              String  Discount=mPopularBrandBeenArrayList1.get(i).getDiscount();
              String  TotalTaxPercentage=mPopularBrandBeenArrayList1.get(i).getTotalTaxPercentage();
              //String  HindiName=mPopularBrandBeenArrayList1.get(i).getItemHindiname();
              String  DpPoint=mPopularBrandBeenArrayList1.get(i).getDreamPoint();
              String  PromoPoint=mPopularBrandBeenArrayList1.get(i).getPromoPoint();
              String  MarginPoint=mPopularBrandBeenArrayList1.get(i).getMarginPoint();
              String  WarehouseId=mPopularBrandBeenArrayList1.get(i).getWarehouseId();
              String  CompanyId=mPopularBrandBeenArrayList1.get(i).getCompanyId();
              String  ItemNumber=mPopularBrandBeenArrayList1.get(i).getItemNumber();
              String  isoffer=mPopularBrandBeenArrayList1.get(i).getIsoffer();

                mPopularBrandBeenArrayList.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage,"", DpPoint, PromoPoint, MarginPoint, WarehouseId, CompanyId,ItemNumber,isoffer));


                break;
            }


        }

        mItemListAdapter = new PopularItemListAdapter(getActivity(), mPopularBrandBeenArrayList, rowitemImageWidth, rowitemImageHeight, tvTotalItemPrice, tvTotalItemQty, tvTotalDp,show_popup);
        mItemListRecyclerView.setAdapter(mItemListAdapter);

         /*   try {
                System.out.println("Rungg:::"+mItemListAllValue);
                mItemListAdapter = new_added PopularItemListAdapter(getActivity(), mItemListAllValue, rowitemImageWidth, rowitemImageHeight, tvTotalItemPrice, tvTotalItemQty, tvTotalDp);
                mItemListRecyclerView.setAdapter(mItemListAdapter);
            }catch (IndexOutOfBoundsException e) {
                startActivity(new_added Intent(getActivity(), HomeActivity.class));
            } catch (Exception e) {
                startActivity(new_added Intent(getActivity(), HomeActivity.class));
            }*/

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  Toast.makeText(getActivity().getApplicationContext(), "To Cart Activity", Toast.LENGTH_SHORT).show();
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



          // new_added GetBrandWisePromotion().execute();


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

    @Override
    public void onPause() {

        if (mItemListAsyncTask != null && !mItemListAsyncTask.isCancelled())
            mItemListAsyncTask.cancel(true);
        if (mSubSubCatFilterTask != null && !mSubSubCatFilterTask.isCancelled())
            mSubSubCatFilterTask.cancel(true);
        super.onPause();
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



}