package app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
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

import com.Amitlibs.utils.ComplexPreferences;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.CartActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.Constant;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.HomeActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.Utils;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.HomeFragItemListAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.HomeFragOfferListAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.BaseCatSubCatBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.OfferList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.ItemList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by gole on 03-01-2017.
 */

public class OfferFragment extends Fragment {
    int selectedCategoryId = -1;
    int selectedWarId = -1;
    int itemId = -1;
    int rowitemImageHeight=77;
    int rowitemImageWidth=77;
    RecyclerView mItemListRecyclerView;
    RelativeLayout rl1;
    TextView tvTotalItemPrice;
    TextView tvTotalItemQty;
    TextView tvTotalDp;

    HomeFragOfferListAdapter mItemListAdapter;

    int mp = 0, pp = 0;

    String locale;

    String hindiLanguage = " हिन्दी (भारत)";

    SharedPreferences sharedpreferences;

    String languageCheck = "e";

    TextView show_popup;
    Dialog mDialog;
    AnimationDrawable animation;

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
        View v = inflater.inflate(R.layout.item_list_home_frag_all, container, false);

        //setImagesDynamicSize();
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




           ArrayList<OfferList> mPopularBrandBeenArrayList = new ArrayList<>();
            Type typePopularBrandBeanArrayList1 = new TypeToken<ArrayList<OfferList>>() {}.getType();
         ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.OFFER_SELLED_ITEM_PREF, getActivity().MODE_PRIVATE);
        mPopularBrandBeenArrayList = mBaseCatSubCatCat1.getArray(Constant.OFFER_SELLED_ITEM_PREFOBJ,typePopularBrandBeanArrayList1);

        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, getActivity().MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);

        BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat1.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);

            ArrayList<ItemList> mPopularBrandBeenArrayList1 = new ArrayList<>();
        mPopularBrandBeenArrayList1.clear();



        for (int i = 0; i < mPopularBrandBeenArrayList.size(); i++) {
            System.out.println("ItemId11::"+mPopularBrandBeenArrayList.get(i).getItemId());
            System.out.println("ItemId22::"+itemId);
            if(mPopularBrandBeenArrayList.get(i).getItemId().equalsIgnoreCase(String.valueOf(itemId)))
            {
                String ItemId = mPopularBrandBeenArrayList.get(i).getItemId();
                String Cityid = mPopularBrandBeenArrayList.get(i).getCityid();
                String CityName = mPopularBrandBeenArrayList.get(i).getCityName();
                String Categoryid = mPopularBrandBeenArrayList.get(i).getCategoryid();
                String SubCategoryId = mPopularBrandBeenArrayList.get(i).getSubCategoryId();
                String SubsubCategoryid = mPopularBrandBeenArrayList.get(i).getSubsubCategoryid();
                String SubSubCode = mPopularBrandBeenArrayList.get(i).getSubSubCode();
                String WarehouseId = mPopularBrandBeenArrayList.get(i).getWarehouseId();
                String  SupplierId  = mPopularBrandBeenArrayList.get(i).getSupplierId();
                String  SUPPLIERCODES  = mPopularBrandBeenArrayList.get(i).getSUPPLIERCODES();
                String CompanyId = mPopularBrandBeenArrayList.get(i).getCompanyId();
                String CategoryName = mPopularBrandBeenArrayList.get(i).getCategoryName();
                String BaseCategoryName = mPopularBrandBeenArrayList.get(i).getBaseCategoryName();
                String SubcategoryName = mPopularBrandBeenArrayList.get(i).getSubcategoryName();
                String SubsubcategoryName = mPopularBrandBeenArrayList.get(i).getSubsubcategoryName();
                String SupplierName = mPopularBrandBeenArrayList.get(i).getSupplierName();
                String itemname = mPopularBrandBeenArrayList.get(i).getItemname();
                tvBrandName.setText(itemname);
                String SellingUnitName = mPopularBrandBeenArrayList.get(i).getSellingUnitName();
                String price = mPopularBrandBeenArrayList.get(i).getPrice();
                String LogoUrl =mPopularBrandBeenArrayList.get(i).getLogoUrl();
                String CatLogoUrl = mPopularBrandBeenArrayList.get(i).getCatLogoUrl();
                String VATTax =mPopularBrandBeenArrayList.get(i).getVATTax();
                String UnitPrice = mPopularBrandBeenArrayList.get(i).getUnitPrice();
                String Number = mPopularBrandBeenArrayList.get(i).getNumber();
                String PurchaseSku =mPopularBrandBeenArrayList.get(i).getPurchaseSku();
                String SellingSku = mPopularBrandBeenArrayList.get(i).getSellingSku();
                String PurchasePrice = mPopularBrandBeenArrayList.get(i).getPurchasePrice();
                String HindiName = mPopularBrandBeenArrayList.get(i).getHindiName();
                String marginPoint = mPopularBrandBeenArrayList.get(i).getMarginPoint();
                String NetPurchasePrice = mPopularBrandBeenArrayList.get(i).getNetPurchasePrice();
                String promoPoint = mPopularBrandBeenArrayList.get(i).getPromoPoint();
                String Discount = mPopularBrandBeenArrayList.get(i).getDiscount();
                String MinOrderQty =mPopularBrandBeenArrayList.get(i).getMinOrderQty();
                String DpPoint = mPopularBrandBeenArrayList.get(i).getDpPoint();
                String TotalTaxPercentage = mPopularBrandBeenArrayList.get(i).getTotalTaxPercentage();
                String IsOffer = mPopularBrandBeenArrayList.get(i).getIsOffer();


                mPopularBrandBeenArrayList1.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage,"", DpPoint, promoPoint, marginPoint, WarehouseId, CompanyId,Number,IsOffer));

                break;
            }


        }
        //new MostSelledItemAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mSelledItemArrayList == null ? new ArrayList<SelledItemPojo>() : mSelledItemArrayList);
      //  (Context context, BaseCatSubCatBean mBaseCatSubCatBean, int ivHeight, int ivWidth, FragmentManager fragmentManager, RetailerBean mRetailerBean, ArrayList<OfferList> mofferlist)
        mItemListAdapter = new HomeFragOfferListAdapter(getActivity(), mPopularBrandBeenArrayList1, rowitemImageWidth, rowitemImageHeight, tvTotalItemPrice, tvTotalItemQty, tvTotalDp,show_popup);
        mItemListRecyclerView.setAdapter(mItemListAdapter);


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



}