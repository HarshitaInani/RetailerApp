package app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.Constant;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.HomeActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.Utils;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.AppPromotionAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.BulkItemAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.EmptyStockAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.ExeclusiveBrandsAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.FindHighDpItemAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.GroceryAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.HomeFragAllTopAddedAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.HomeFragBrandWiseAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.HomeFragPopularBrandAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.HomeFragRecyclerViewAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.MostSelledItemAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.NewlyAddedBrandsAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.OfferAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.ShopbycompanyAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.TodayDhamakaAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.ViewPagerAdapter2;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.homecareadapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.personalcareadapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.BaseCatSubCatBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.BulkItemPojo;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.EmptyStockPojo;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.ExeclusiveBrandsBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.FindHighDpPojo;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.NewlyAddedBrandsBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.OfferList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.PopularBrandBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.PromotionPojo;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.SelledItemPojo;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.TodayDhamakaPojo;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.AllTopAddedItemList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.BaseCatBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.CategoryBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.ItemList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.SubCategoriesBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.shopbycompanybean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.databinding.FragmentHomeBinding;

import static android.content.Intent.getIntent;


/**
 * Created by Krishna on 29-12-2016.
 */

public class HomeFragment extends Fragment {
    //Declarations
    int sliderIMageHeight;
    int sliderIMageWidth=100;
    int rowIMageHeight=100;
    int rowIMageWidth;
    boolean isItemListAvail = false;
    int mp = 0, pp = 0;
    boolean isPopularBrandItemListAvail = false;
    RecyclerView  offerRecycler;
    RecyclerView shopCardRecyclerView;
    RecyclerView mpersonalcareRecyclerView;
    RecyclerView mHomecareRecyclerView;
    RecyclerView  mGroceryRecyclerView;
    RecyclerView mHomeFragRecyclerView;
    RecyclerView mHomeFragRecyclerView1;
    RecyclerView mHomeFragRecyclerView2;
    RecyclerView mNewlyAddedRecyclerView;
    RecyclerView mAllTopAddedRecyclerView;
    RecyclerView mAppPromotionRecycler;
    RecyclerView mTodayDhamakaRecycler;
    RecyclerView mEmptyStockRecycler;
    RecyclerView mBulkItemRecycler;
    RecyclerView mMostSelledRecycler;
    RecyclerView mFindHighDpRecycler;
    RecyclerView mExeclusiveRecycler;
    LinearLayoutManager layoutManager123,layoutManager,layoutManager1,layoutManager2,layoutManager3,layoutManager4,layoutManager5,layoutManager6,layoutManager7,layoutManager8,layoutManager9,layoutManager10,layoutManager11,layoutManager12;
    AsyncTask<String, Void, JSONArray> getOfferesAsyncTask;
    AsyncTask<String, Void, JSONObject> getCategorySubCategoryAsyncTask;
    AsyncTask<String, Void, JSONArray> getPopularBrandTask;
    AsyncTask<String, Void, JSONArray> getBrandwisepramotionTask;
    AsyncTask<String, Void, JSONArray> getAppPramotionTask;
    AsyncTask<String, Void, JSONArray> getNewlyAddedBrandsTask;
    AsyncTask<String, Void, JSONArray> getAllTopAddedItemTask;
    AsyncTask<String, Void, JSONArray> getTodayDhamakaTask;
    AsyncTask<String, Void, JSONArray> getEmptyStockTask;
    AsyncTask<String, Void, JSONArray> getBulkItemTask;
    AsyncTask<String, Void, JSONArray> getMostSelectedItemTask;
    AsyncTask<String, Void, JSONArray> getFindItemDpTask;
    AsyncTask<String, Void, JSONArray> getExclusiveBrand;
    AsyncTask<String, Void, JSONArray> ShopbyCompany;
    JSONArray jsonArraySlider;
    ArrayList<String> picList = new ArrayList<>();
    int currentPage = 0;
    private static int NUM_PAGES = 0;
    ViewPagerAdapter2 adapter;
    CirclePageIndicator circlePageIndicator;
    ArrayList<String> price = new ArrayList<String>();
    ViewPager viewPager;
    int[] flag;
    int condition=0;
    int value=0;
    NestedScrollView mNestedScrollView;
    ProgressBar progressBar;
    OfferAdapter mofferAdapter;
    Button btn;
    LinearLayout  homecare,personalcare,grocery,l1,lerPromotion,lerNewlyAdded,lerTopAdded,lerTodayDhamaka,lerEmptyStock,lerBulkItem,lerMostSalld,lerFindItemHighDp,lerPopularItem,lerExclusiveBrand,lerOffer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //intiliazation
        FragmentHomeBinding fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        final View view = fragmentHomeBinding.getRoot();
        //setImagesDynamicSize();

        Picasso.with(getActivity()).load(Constant.BASE_URL_SLIDER).placeholder(R.drawable.top_img_bg).resize(sliderIMageWidth, sliderIMageHeight).error(R.drawable.top_img_bg).into(fragmentHomeBinding.fragHomeTopIv);
        view.findViewById(R.id.mywidget).setSelected(true);
        mNestedScrollView=(NestedScrollView)view.findViewById(R.id.nestedScroll) ;
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        grocery = (LinearLayout)view.findViewById(R.id.grocery);
        homecare = (LinearLayout)view.findViewById(R.id.homecare);
        personalcare = (LinearLayout)view.findViewById(R.id.personalcare);

          btn=(Button)view.findViewById(R.id.btn);
          btn.setOnClickListener(new View.OnClickListener() {
            @Override
              public void onClick(View v) {
                mNestedScrollView.fullScroll(mNestedScrollView.FOCUS_UP);
            }
        });

        l1=fragmentHomeBinding.l1;
        lerOffer=fragmentHomeBinding.lerOffer;
        lerPromotion=fragmentHomeBinding.lerPromotion;
        lerNewlyAdded=fragmentHomeBinding.lerNewlyAdded;
        lerTopAdded=fragmentHomeBinding.lerTopAdded;
        lerTodayDhamaka=fragmentHomeBinding.lerTodayDhamaka;
        lerEmptyStock=fragmentHomeBinding.lerEmptyStock;
        lerBulkItem=fragmentHomeBinding.lerBulkItem;
        lerMostSalld=fragmentHomeBinding.lerMostSalld;
        lerFindItemHighDp=fragmentHomeBinding.lerFindItemHighDp;
        lerPopularItem=fragmentHomeBinding.lerPopularItem;
        lerExclusiveBrand=fragmentHomeBinding.lerExclusiveBrand;

        shopCardRecyclerView = fragmentHomeBinding.shopCardRecyclerView;
        layoutManager123 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        shopCardRecyclerView.setLayoutManager(layoutManager123);

        offerRecycler = fragmentHomeBinding.offerRecycler;
        layoutManager12 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        offerRecycler.setLayoutManager(layoutManager12);

        offerRecycler.setHasFixedSize(false);
        offerRecycler.setNestedScrollingEnabled(false);

        mHomeFragRecyclerView = fragmentHomeBinding.cardRecyclerView;
         layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mHomeFragRecyclerView.setLayoutManager(layoutManager);

        mHomeFragRecyclerView1 = fragmentHomeBinding.cardRecyclerView1;
         layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mHomeFragRecyclerView1.setLayoutManager(layoutManager1);

        mGroceryRecyclerView = fragmentHomeBinding.cardRecyclerView0;
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mGroceryRecyclerView.setLayoutManager(layoutManager);

        mHomecareRecyclerView = fragmentHomeBinding.cardRecyclerView01;
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mHomecareRecyclerView.setLayoutManager(layoutManager);


        mpersonalcareRecyclerView = fragmentHomeBinding.cardRecyclerViewpersonolcare;
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mpersonalcareRecyclerView.setLayoutManager(layoutManager);

        mHomeFragRecyclerView2 = fragmentHomeBinding.cardRecyclerView2;
         layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mHomeFragRecyclerView2.setLayoutManager(layoutManager2);

        mAppPromotionRecycler = fragmentHomeBinding.appPromotionRecycler;
        layoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mAppPromotionRecycler.setLayoutManager(layoutManager3);


        mNewlyAddedRecyclerView = fragmentHomeBinding.newlyAddedRecycler;
        layoutManager4 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mNewlyAddedRecyclerView.setLayoutManager(layoutManager4);


        mAllTopAddedRecyclerView = fragmentHomeBinding.allTopAddedRecycler;
        layoutManager5 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mAllTopAddedRecyclerView.setLayoutManager(layoutManager5);


        mTodayDhamakaRecycler = fragmentHomeBinding.todayDhamakaRecycler;
        layoutManager6 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mTodayDhamakaRecycler.setLayoutManager(layoutManager6);

        mEmptyStockRecycler = fragmentHomeBinding.emptyStockRecycler;
        layoutManager7 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mEmptyStockRecycler.setLayoutManager(layoutManager7);


        mBulkItemRecycler = fragmentHomeBinding.bulkItemRecycler;
        layoutManager8 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mBulkItemRecycler.setLayoutManager(layoutManager8);


        mMostSelledRecycler = fragmentHomeBinding.mostSelledItem;
        layoutManager9 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mMostSelledRecycler.setLayoutManager(layoutManager9);


        mFindHighDpRecycler = fragmentHomeBinding.findItemHighDp;
        layoutManager10 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mFindHighDpRecycler.setLayoutManager(layoutManager10);


        mExeclusiveRecycler = fragmentHomeBinding.exclusiveBrandRv;
        layoutManager11 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mExeclusiveRecycler.setLayoutManager(layoutManager11);


        shopCardRecyclerView.setHasFixedSize(false);
        mpersonalcareRecyclerView.setHasFixedSize(false);
        mHomecareRecyclerView.setHasFixedSize(false);
        mGroceryRecyclerView.setHasFixedSize(false);
        mHomeFragRecyclerView.setHasFixedSize(false);
        mHomeFragRecyclerView1.setHasFixedSize(false);
        mHomeFragRecyclerView2.setHasFixedSize(false);
        mAppPromotionRecycler.setHasFixedSize(false);
        mNewlyAddedRecyclerView.setHasFixedSize(false);
        mAllTopAddedRecyclerView.setHasFixedSize(false);
        mTodayDhamakaRecycler.setHasFixedSize(false);
        mEmptyStockRecycler.setHasFixedSize(false);
        mBulkItemRecycler.setHasFixedSize(false);
        mMostSelledRecycler.setHasFixedSize(false);
        mFindHighDpRecycler.setHasFixedSize(false);
        mExeclusiveRecycler.setHasFixedSize(false);



        shopCardRecyclerView.setNestedScrollingEnabled(false);
        mpersonalcareRecyclerView.setNestedScrollingEnabled(false);
        mHomecareRecyclerView.setNestedScrollingEnabled(false);
        mGroceryRecyclerView.setNestedScrollingEnabled(false);
        mHomeFragRecyclerView.setNestedScrollingEnabled(false);
        mHomeFragRecyclerView1.setNestedScrollingEnabled(false);
        mHomeFragRecyclerView2.setNestedScrollingEnabled(false);
        mAppPromotionRecycler.setNestedScrollingEnabled(false);
        mNewlyAddedRecyclerView.setNestedScrollingEnabled(false);
        mAllTopAddedRecyclerView.setNestedScrollingEnabled(false);
        mTodayDhamakaRecycler.setNestedScrollingEnabled(false);
        mEmptyStockRecycler.setNestedScrollingEnabled(false);
        mBulkItemRecycler.setNestedScrollingEnabled(false);
        mMostSelledRecycler.setNestedScrollingEnabled(false);
        mFindHighDpRecycler.setNestedScrollingEnabled(false);
        mExeclusiveRecycler.setNestedScrollingEnabled(false);


        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//check condition to call Api on Scrollview
                if(v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY > oldScrollY) {
                        int visibleItemCount = layoutManager.getChildCount();
                        int totalItemCount = layoutManager.getItemCount();
                        int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                        Log.d("totalItemCount", String.valueOf(totalItemCount));
                        Log.d("pastVisiblesItems", String.valueOf(pastVisiblesItems));
                       Log.d("visibleItemCount", String.valueOf(visibleItemCount));
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount-9) {

                            if (condition == 1) {

                                 if(value==0){

                                     Log.d("AppPromotion","AppPromotion");
                                    value=1;

                                     getAppPramotionTask = new AppPromotion().execute();

                                }
                                else if (value==1) {
                                     Log.d("NewlyAddedBrands","NewlyAddedBrands");
                                    value=2;
                                     getNewlyAddedBrandsTask = new NewlyAddedBrands().execute();

                                }
                                else if(value==2){
                                     Log.d("AllTopAddedItem","AllTopAddedItem");
                                    value=3;
                                     getAllTopAddedItemTask = new AllTopAddedItem().execute();
                                }
                                else if(value==3){
                                     Log.d("TodayDhamaka","TodayDhamaka");
                                    value=4;
                                     getTodayDhamakaTask = new GetTodayDhamaka().execute();
                                }
                                else if(value==4){
                                     Log.d("EmptyStockItem","EmptyStockItem");
                                    value=5;
                                     getEmptyStockTask = new GetEmptyStockItem().execute();
                                }
                                else if(value==5){
                                     Log.d("BulkItem","BulkItem");
                                    value=6;
                                     getBulkItemTask = new GetBulkItem().execute();
                                }
                                else if(value==6){
                                     Log.d("MostSelledItem","MostSelledItem");
                                    value=7;
                                     getMostSelectedItemTask = new GetMostSelledItem().execute();
                                }
                                else if(value==7){
                                     Log.d("FindItemHighDP","FindItemHighDP");
                                    value=8;
                                     getFindItemDpTask = new GetFindItemHighDP().execute();
                                }
                                else if(value==8){
                                     Log.d("Exclusive","Exclusive");
                                    value=9;
                                     getExclusiveBrand = new GetExeclusiveBrand().execute();
                                }

                            } else {
                                Log.d("NoApiCall::","NoApiCall");
                            }
                        }
                    }
                }
            }
        });

        viewPager = fragmentHomeBinding.pager;
        circlePageIndicator = fragmentHomeBinding.indicator;
        try {
            jsonArraySlider = new JSONArray(Utility.getStringSharedPreferences(getActivity(), "Sliderjson").toString());
            System.out.println("jsonArraySlider:::::::::::::::::::::::::::::::::::::"+jsonArraySlider);

            for (int i = 0; i  < jsonArraySlider.length() ; i++) {


                JSONObject jsonObject = jsonArraySlider.getJSONObject(i);

                String temp = jsonObject.getString("Pic");
                temp = temp.replaceAll(" ", "%20");

                picList.add(temp);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        NUM_PAGES =picList.size() + 1;
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES -1 ) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);

            }
        };

        Timer swipeTimer = new Timer();// This will create a new_added Thread

        swipeTimer.schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4000, 4000);



        setupViewpager();

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // this is Complex preference in which data of Retailer bean pojo class is stored
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, getActivity().MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        setupViewpager();

        ComplexPreferences mBaseCatSubCatCat11 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
        BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat11.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);

        if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {
            isItemListAvail = true;
            //Declaration of all the Array list
            ArrayList<CategoryBean> mgrocerycategoryBeanArrayList1 = new ArrayList<>();
            mgrocerycategoryBeanArrayList1.clear();
            ArrayList<CategoryBean> mhomecarecategoryBeanArrayList1 = new ArrayList<>();
            mhomecarecategoryBeanArrayList1.clear();
            ArrayList<CategoryBean> mpersonalcaregoryBeanArrayList1 = new ArrayList<>();
            mpersonalcaregoryBeanArrayList1.clear();
            ArrayList<OfferList> mPopularBrandBeenArrayList3 = new ArrayList<>();
            mPopularBrandBeenArrayList3.clear();
            Type typePopularBrandBeanArrayList3 = new TypeToken<ArrayList<OfferList>>() {}.getType();
            ArrayList<PopularBrandBean> mPopularBrandBeenArrayList = new ArrayList<>();
            ArrayList<ItemList> mPopularBrandBeenArrayList1 = new ArrayList<>();
            ArrayList<PopularBrandBean> mPopularBrandBeenArrayList2 = new ArrayList<>();

            ArrayList<ExeclusiveBrandsBean> mExeclusiveBrandsBeanList = new ArrayList<>();
            mExeclusiveBrandsBeanList.clear();

            ArrayList<PromotionPojo> mAppPromotionArrayList = new ArrayList<>();
            mAppPromotionArrayList.clear();

            ArrayList<NewlyAddedBrandsBean> mNewlyAddedBrandsBeanList = new ArrayList<>();
            mNewlyAddedBrandsBeanList.clear();

            ArrayList<AllTopAddedItemList> mAllTopAddedItemBeanList = new ArrayList<>();
            mAllTopAddedItemBeanList.clear();

            ArrayList<TodayDhamakaPojo> mTodayDhamakaBeanList = new ArrayList<>();
            mTodayDhamakaBeanList.clear();

            ArrayList<EmptyStockPojo> mEmptyStockBeanList = new ArrayList<>();
            mEmptyStockBeanList.clear();

            ArrayList<BulkItemPojo> mBulkItemBeanList = new ArrayList<>();
            mBulkItemBeanList.clear();

            ArrayList<SelledItemPojo> mSelledItemBeanList = new ArrayList<>();
            mSelledItemBeanList.clear();

            ArrayList<FindHighDpPojo> mFindHighDpBeanList = new ArrayList<>();
            mFindHighDpBeanList.clear();


            Type typemExeclusiveBrandsBeanArrayList = new TypeToken<ArrayList<ExeclusiveBrandsBean>>() {}.getType();
            Type typeAppPromotionBeanArrayList = new TypeToken<ArrayList<PromotionPojo>>() {}.getType();
            Type typeNewlyAddedBrandsBeanArrayList = new TypeToken<ArrayList<NewlyAddedBrandsBean>>() {}.getType();
            Type typeAllTopAddedItemBeanArrayList = new TypeToken<ArrayList<AllTopAddedItemList>>() {}.getType();
            Type typemTodayDhamakaBeanArrayList = new TypeToken<ArrayList<TodayDhamakaPojo>>() {}.getType();
            Type typemEmptyStockBeanArrayList = new TypeToken<ArrayList<EmptyStockPojo>>() {}.getType();
            Type typemBulkItemBeanArrayList = new TypeToken<ArrayList<BulkItemPojo>>() {}.getType();
            Type typemSelledItemBeanArrayList = new TypeToken<ArrayList<SelledItemPojo>>() {}.getType();
            Type typemFindHighDpBeanArrayList = new TypeToken<ArrayList<FindHighDpPojo>>() {}.getType();


            Type typePopularBrandBeanArrayList1 = new TypeToken<ArrayList<ItemList>>() {}.getType();
            Type typePopularBrandBeanArrayList2 = new TypeToken<ArrayList<PopularBrandBean>>() {}.getType();
            Type typemgroceryArrayList = new TypeToken< ArrayList<CategoryBean>>() {}.getType();
            Type typemhomecareArrayList = new TypeToken< ArrayList<CategoryBean>>() {}.getType();
            Type typempersonalcareArrayList = new TypeToken< ArrayList<CategoryBean>>() {}.getType();
            Type typePopularBrandBeanArrayList = new TypeToken<ArrayList<PopularBrandBean>>()
            {
            }.getType();


            //declaration of All the Complexpreference used in this Activity
            ComplexPreferences   mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.POPULAR_BRANDS_PREF, getActivity().MODE_PRIVATE);
            ComplexPreferences   mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.POPULAR_BRANDS_PREF1, getActivity().MODE_PRIVATE);
            ComplexPreferences   mBaseCatSubCatCat2 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.POPULAR_BRANDS_PREF2, getActivity().MODE_PRIVATE);
            mPopularBrandBeenArrayList = mBaseCatSubCatCat.getArray(Constant.POPULAR_BRANDS_PREFOBJ, typePopularBrandBeanArrayList);
            mPopularBrandBeenArrayList1 = mBaseCatSubCatCat1.getArray(Constant.POPULAR_BRANDS_PREFOBJ1, typePopularBrandBeanArrayList1);
            mPopularBrandBeenArrayList2 = mBaseCatSubCatCat2.getArray(Constant.POPULAR_BRANDS_PREFOBJ2,typePopularBrandBeanArrayList2);


            ComplexPreferences mBaseCatSubCatCat8 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.EXECLUSIVE_BRANDS_PREF, getActivity().MODE_PRIVATE);
            mExeclusiveBrandsBeanList = mBaseCatSubCatCat8.getArray(Constant.EXECLUSIVE_BRANDS_PREFOBJ, typemExeclusiveBrandsBeanArrayList);


            ComplexPreferences mBaseAPP_PROMOTION_PREF = ComplexPreferences.getComplexPreferences(getActivity(), Constant.APP_PROMOTION_PREF, getActivity().MODE_PRIVATE);
            mAppPromotionArrayList = mBaseAPP_PROMOTION_PREF.getArray(Constant.APP_PROMOTION_PREFOBJ, typeAppPromotionBeanArrayList);


            ComplexPreferences mBaseNEWLY_ADDED_BRANDS_PREF = ComplexPreferences.getComplexPreferences(getActivity(), Constant.NEWLY_ADDED_BRANDS_PREF, getActivity().MODE_PRIVATE);
            mNewlyAddedBrandsBeanList = mBaseNEWLY_ADDED_BRANDS_PREF.getArray(Constant.NEWLY_ADDED_BRANDS_PREFOBJ, typeNewlyAddedBrandsBeanArrayList);

            ComplexPreferences mBaseCatALL_TOP_ADDED_PREF = ComplexPreferences.getComplexPreferences(getActivity(), Constant.ALL_TOP_ADDED_PREF, getActivity().MODE_PRIVATE);
            mAllTopAddedItemBeanList = mBaseCatALL_TOP_ADDED_PREF.getArray(Constant.ALL_TOP_ADDED_PREFOBJ, typeAllTopAddedItemBeanArrayList);

            ComplexPreferences mBaseCatSubCatCat3 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.TODAY_DHAMAKA_PREF, getActivity().MODE_PRIVATE);
            mTodayDhamakaBeanList = mBaseCatSubCatCat3.getArray(Constant.TODAY_DHAMAKA_PREFOBJ, typemTodayDhamakaBeanArrayList);

            ComplexPreferences mBaseCatSubCatCat4 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.EMPTY_STOCK_PREF, getActivity().MODE_PRIVATE);
            mEmptyStockBeanList = mBaseCatSubCatCat4.getArray(Constant.EMPTY_STOCK_PREFOBJ, typemEmptyStockBeanArrayList);

            ComplexPreferences mBaseCatSubCatCat5 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BULK_ITEM_PREF, getActivity().MODE_PRIVATE);
            mBulkItemBeanList = mBaseCatSubCatCat5.getArray(Constant.BULK_ITEM_PREFOBJ, typemBulkItemBeanArrayList);

            ComplexPreferences mBaseCatSubCatCat6 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.MOST_SELLED_ITEM_PREF, getActivity().MODE_PRIVATE);
            mSelledItemBeanList = mBaseCatSubCatCat6.getArray(Constant.MOST_SELLED_ITEM_PREFOBJ, typemSelledItemBeanArrayList);

            ComplexPreferences mBaseCatSubCatCat7 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.FIND_HIGH_DP_PREF, getActivity().MODE_PRIVATE);
            mFindHighDpBeanList = mBaseCatSubCatCat7.getArray(Constant.FIND_HIGH_DP_PREFOBJ, typemFindHighDpBeanArrayList);

            ComplexPreferences grocerycategory = ComplexPreferences.getComplexPreferences(getActivity(), Constant.GROCERY_ITEM_PREF, getActivity().MODE_PRIVATE);
            mgrocerycategoryBeanArrayList1=grocerycategory.getArray(Constant.GROCERY_ITEM_PREFOBJ,typemgroceryArrayList);


            ComplexPreferences homecarecategory = ComplexPreferences.getComplexPreferences(getActivity(), Constant.HOMECARE_ITEM_PREF, getActivity().MODE_PRIVATE);
            mhomecarecategoryBeanArrayList1=homecarecategory.getArray(Constant.HOMECARE_ITEM_PREFOBJ,typemhomecareArrayList);


            ComplexPreferences personalcarecategory = ComplexPreferences.getComplexPreferences(getActivity(), Constant.PERSONAL_ITEM_PREF, getActivity().MODE_PRIVATE);
            mpersonalcaregoryBeanArrayList1=personalcarecategory.getArray(Constant.PERSONAL_ITEM_PREFOBJ,typempersonalcareArrayList);


            ComplexPreferences  mBaseOFFER_SELLED_ITEM_PREF= ComplexPreferences.getComplexPreferences(getActivity(), Constant.OFFER_SELLED_ITEM_PREF, getActivity().MODE_PRIVATE);
            mPopularBrandBeenArrayList3 = mBaseOFFER_SELLED_ITEM_PREF.getArray(Constant.OFFER_SELLED_ITEM_PREFOBJ,typePopularBrandBeanArrayList3);
                isPopularBrandItemListAvail = true;
           // if (mPopularBrandBeenArrayList != null && !mPopularBrandBeenArrayList.isEmpty()) {
                HomeFragRecyclerViewAdapter mHomeFragRecyclerViewAdapter = new HomeFragRecyclerViewAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mPopularBrandBeenArrayList == null ? new ArrayList<PopularBrandBean>() : mPopularBrandBeenArrayList);
                mHomeFragRecyclerView.setAdapter(mHomeFragRecyclerViewAdapter);
           // }
//Set adapter in recycleView if data is Comes From API

            if (mExeclusiveBrandsBeanList != null && !mExeclusiveBrandsBeanList.isEmpty()) {
                Log.d("BrandsBean", String.valueOf(mExeclusiveBrandsBeanList));
                lerExclusiveBrand.setVisibility(View.VISIBLE);
                ExeclusiveBrandsAdapter mExeclusiveBrandsAdapter = new ExeclusiveBrandsAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mExeclusiveBrandsBeanList == null ? new ArrayList<ExeclusiveBrandsBean>() : mExeclusiveBrandsBeanList);
                mExeclusiveRecycler.setAdapter(mExeclusiveBrandsAdapter);
            }
            if (mAppPromotionArrayList != null && !mAppPromotionArrayList.isEmpty())
            { Log.d("AppPromotion", String.valueOf(mAppPromotionArrayList));
                lerPromotion.setVisibility(View.VISIBLE);
                AppPromotionAdapter mAppPromotionAdapter = new AppPromotionAdapter(getActivity(), mAppPromotionArrayList, rowIMageHeight, rowIMageWidth);
                mAppPromotionRecycler.setAdapter(mAppPromotionAdapter);
            }
            if (mNewlyAddedBrandsBeanList != null && !mNewlyAddedBrandsBeanList.isEmpty()) {
                Log.d("NewlyAdded", String.valueOf(mNewlyAddedBrandsBeanList));
                lerNewlyAdded.setVisibility(View.VISIBLE);
                NewlyAddedBrandsAdapter mNewlyAddedBrandsAdapter = new NewlyAddedBrandsAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mNewlyAddedBrandsBeanList == null ? new ArrayList<NewlyAddedBrandsBean>() : mNewlyAddedBrandsBeanList);
                mNewlyAddedRecyclerView.setAdapter(mNewlyAddedBrandsAdapter);
            }
            if (mAllTopAddedItemBeanList != null && !mAllTopAddedItemBeanList.isEmpty()) {
                Log.d("TopAdded", String.valueOf(mAllTopAddedItemBeanList));
                lerTopAdded.setVisibility(View.VISIBLE);
                HomeFragAllTopAddedAdapter mHomeFragAllTopAddedAdapter = new HomeFragAllTopAddedAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mAllTopAddedItemBeanList == null ? new ArrayList<AllTopAddedItemList>() : mAllTopAddedItemBeanList);
                mAllTopAddedRecyclerView.setAdapter(mHomeFragAllTopAddedAdapter);
            }
            if (mTodayDhamakaBeanList != null && !mTodayDhamakaBeanList.isEmpty()) {
                Log.d("TodayDhamaka", String.valueOf(mTodayDhamakaBeanList));
                lerTodayDhamaka.setVisibility(View.VISIBLE);
                TodayDhamakaAdapter mTodayDhamakaAdapter = new TodayDhamakaAdapter(getActivity(), mTodayDhamakaBeanList, rowIMageHeight, rowIMageWidth);
                mTodayDhamakaRecycler.setAdapter(mTodayDhamakaAdapter);
            }
            if (mEmptyStockBeanList != null && !mEmptyStockBeanList.isEmpty()) {
                Log.d("EmptyStock", String.valueOf(mEmptyStockBeanList));
                lerEmptyStock.setVisibility(View.VISIBLE);
                EmptyStockAdapter mEmptyStockAdapter = new EmptyStockAdapter(getActivity(),mEmptyStockBeanList, rowIMageHeight, rowIMageWidth);
                mEmptyStockRecycler.setAdapter(mEmptyStockAdapter);
            }
            if (mBulkItemBeanList != null && !mBulkItemBeanList.isEmpty()) {
                Log.d("BulkItem", String.valueOf(mBulkItemBeanList));
                lerBulkItem.setVisibility(View.VISIBLE);
                BulkItemAdapter mBulkItemAdapter = new BulkItemAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mBulkItemBeanList == null ? new ArrayList<BulkItemPojo>() : mBulkItemBeanList);
                mBulkItemRecycler.setAdapter(mBulkItemAdapter);
            }
            if (mSelledItemBeanList != null && !mSelledItemBeanList.isEmpty()) {
                Log.d("SelledItem", String.valueOf(mSelledItemBeanList));
                lerMostSalld.setVisibility(View.VISIBLE);
                MostSelledItemAdapter mMostSelledItemAdapter = new MostSelledItemAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mSelledItemBeanList == null ? new ArrayList<SelledItemPojo>() : mSelledItemBeanList);
                mMostSelledRecycler.setAdapter(mMostSelledItemAdapter);
            }
            if (mFindHighDpBeanList != null && !mFindHighDpBeanList.isEmpty()) {
                Log.d("FindHighDp", String.valueOf(mFindHighDpBeanList));
                lerFindItemHighDp.setVisibility(View.VISIBLE);
                FindHighDpItemAdapter mFindHighDpItemAdapter = new FindHighDpItemAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mFindHighDpBeanList == null ? new ArrayList<FindHighDpPojo>() : mFindHighDpBeanList);
                mFindHighDpRecycler.setAdapter(mFindHighDpItemAdapter);
            }

            if(mgrocerycategoryBeanArrayList1!= null && !mgrocerycategoryBeanArrayList1.isEmpty()){
                Log.d("grocery1", String.valueOf(mgrocerycategoryBeanArrayList1));
                grocery.setVisibility(View.VISIBLE);
                GroceryAdapter groceryAdapter = new GroceryAdapter(getActivity(), mBaseCatSubCatBean,mgrocerycategoryBeanArrayList1, rowIMageHeight, rowIMageWidth, getFragmentManager());
                mGroceryRecyclerView.setAdapter(groceryAdapter);
            }
            if(mhomecarecategoryBeanArrayList1!= null && !mhomecarecategoryBeanArrayList1.isEmpty()){
                Log.d("grocery2", String.valueOf(mhomecarecategoryBeanArrayList1));
                homecare.setVisibility(View.VISIBLE);
                homecareadapter homecareAdapter = new homecareadapter(getActivity(), mBaseCatSubCatBean,mhomecarecategoryBeanArrayList1, rowIMageHeight, rowIMageWidth, getFragmentManager());
                mHomecareRecyclerView.setAdapter(homecareAdapter);
            }
            if(mpersonalcaregoryBeanArrayList1!= null && !mpersonalcaregoryBeanArrayList1.isEmpty()){
                Log.d("grocery3", String.valueOf(mpersonalcaregoryBeanArrayList1));
                personalcare.setVisibility(View.VISIBLE);
                personalcareadapter personalcareAdapter = new personalcareadapter(getActivity(), mBaseCatSubCatBean,mpersonalcaregoryBeanArrayList1, rowIMageHeight, rowIMageWidth, getFragmentManager());
                mpersonalcareRecyclerView.setAdapter(personalcareAdapter);
            }

            if (mPopularBrandBeenArrayList1 != null && !mPopularBrandBeenArrayList1.isEmpty()) {
                Log.d("PopularBrand", String.valueOf(mPopularBrandBeenArrayList1));
                lerPopularItem.setVisibility(View.VISIBLE);
                HomeFragBrandWiseAdapter mHomeFragRecyclerViewAdapter1 = new HomeFragBrandWiseAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mPopularBrandBeenArrayList1 == null ? new ArrayList<ItemList>() : mPopularBrandBeenArrayList1);
                mHomeFragRecyclerView1.setAdapter(mHomeFragRecyclerViewAdapter1);
            }
            if (mPopularBrandBeenArrayList2 != null && !mPopularBrandBeenArrayList2.isEmpty()) {
                Log.d("PopularBrand2", String.valueOf(mPopularBrandBeenArrayList2));
                l1.setVisibility(View.VISIBLE);
                HomeFragPopularBrandAdapter mHomeFragPopularBrandAdapter2 = new HomeFragPopularBrandAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mPopularBrandBeenArrayList2 == null ? new ArrayList<PopularBrandBean>() : mPopularBrandBeenArrayList2);
                mHomeFragRecyclerView2.setAdapter(mHomeFragPopularBrandAdapter2);
            }
            if (mPopularBrandBeenArrayList3 != null && !mPopularBrandBeenArrayList3.isEmpty()) {
                Log.d("mPopularBrandBeen",String.valueOf(mPopularBrandBeenArrayList3));
                mofferAdapter = new OfferAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mPopularBrandBeenArrayList3 == null ? new ArrayList<OfferList>() : mPopularBrandBeenArrayList3);
                offerRecycler.setAdapter(mofferAdapter);
                lerOffer.setVisibility(View.VISIBLE);

            }
        } else {
            isItemListAvail = false;
        }
       boolean ApiCall =   Utility.getSharedPreferencesBoolean(getActivity(),"APICALL");
        System.out.println("ApiCall::::" + ApiCall);
        if (((HomeActivity) getActivity()).shouldCallCatMenuApi) {
            if (Utils.isInternetConnected(getActivity())) {
                if (ApiCall) {
                    Utility.setSharedPreferenceBoolean(getActivity(), "APICALL", false);
                    condition=1;
                 getCategorySubCategoryAsyncTask = new GetCategorySubCategory().execute(mRetailerBean.getWarehouseid());
                    getOfferesAsyncTask = new GetOffers().execute();
                }
            } else {
                Toast.makeText(getActivity(), "Internet connection is not available", Toast.LENGTH_SHORT).show();
            }
            ((HomeActivity) getActivity()).shouldCallCatMenuApi = false;
        }
        setNavDrawer();
    }

    private void setNavDrawer() {
        ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
        BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);
        if (mBaseCatSubCatBean != null) {
            ArrayList<BaseCatBean> listDataHeader = mBaseCatSubCatBean.getmBaseCatBeanArrayList();
            HashMap<BaseCatBean, ArrayList<CategoryBean>> listDataChild = new HashMap();
            for (int i = 0; i < listDataHeader.size(); i++) {
                ArrayList<CategoryBean> listDataChildArray = new ArrayList<>();
                for (int j = 0; j < mBaseCatSubCatBean.getmCategoryBeanArrayList().size(); j++) {
                    if (listDataHeader.get(i).getBaseCategoryId().equalsIgnoreCase(mBaseCatSubCatBean.getmCategoryBeanArrayList().get(j).getBaseCategoryId())) {
                        listDataChildArray.add(mBaseCatSubCatBean.getmCategoryBeanArrayList().get(j));
                    }
                }
                listDataChild.put(listDataHeader.get(i), listDataChildArray);
            }
            ((HomeActivity) getActivity()).listDataHeaderGlobal = listDataHeader;
            ((HomeActivity) getActivity()).listDataChildGlobal = listDataChild;
            ((HomeActivity) getActivity()).setupNavigationView(listDataHeader, listDataChild);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getCategorySubCategoryAsyncTask != null && !getCategorySubCategoryAsyncTask.isCancelled()) {
            getCategorySubCategoryAsyncTask.cancel(true);
        }
        if (getPopularBrandTask != null && !getPopularBrandTask.isCancelled()) {
            getPopularBrandTask.cancel(true);
        }
    }
//API call of All Categories Item
    public class GetCategorySubCategory extends AsyncTask<String, Void, JSONObject> {
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
          /*  mDialog = new_added Dialog(getActivity());
            mDialog.getWindow().setBackgroundDrawable(new_added ColorDrawable(Color.TRANSPARENT));
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.progress_dialog);
            ImageView la = (ImageView) mDialog.findViewById(R.id.gridprogressBar);
            la.setBackgroundResource(R.drawable.custom_progress_dialog_animation);
            animation = (AnimationDrawable) la.getBackground();
            animation.start();
            mDialog.setCancelable(false);
            if (isItemListAvail) {
                ((TextView) mDialog.findViewById(R.id.progressText)).setText("Refreshing item list please wait...");

            } else {
                ((TextView) mDialog.findViewById(R.id.progressText)).setText("Loading items please wait...");
                mDialog.setCancelable(false);
                mDialog.show();
            }*/
        }
//URL connection
        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObjectFromUrl = null;

            System.out.println("Url::"+ Constant.BASE_URL_ITEM_MASTER + "?warehouseid=" + params[0]);
            try {
                jsonObjectFromUrl = new HttpUrlConnectionJSONParser().getJsonObjectFromHttpUrlConnection(Constant.BASE_URL_ITEM_MASTER + "?warehouseid=" + params[0], null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObjectFromUrl;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            ArrayList<CategoryBean> mpersonalcaregoryBeanArrayList1 = new ArrayList<>();
            ArrayList<CategoryBean> mhomecarecategoryBeanArrayList1 = new ArrayList<>();
            ArrayList<CategoryBean> mgrocerycategoryBeanArrayList1 = new ArrayList<>();
            ArrayList<BaseCatBean> mBaseCatBeanArrayList = new ArrayList<>();
            ArrayList<CategoryBean> mCategoryBeanArrayList = new ArrayList<>();
            ArrayList<SubCategoriesBean> mSubCategoriesBeanArrayList = new ArrayList<>();
//json parsing
            if (jsonObject != null) {
                try {
                    if (TextUtils.isNullOrEmpty(jsonObject.getJSONArray("Basecats").toString())) {
                        Toast.makeText(getActivity(), "BaseCategories not available on server", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isNullOrEmpty(jsonObject.getJSONArray("Categories").toString())) {
                        Toast.makeText(getActivity(), "Categories not available on server", Toast.LENGTH_SHORT).show();
                    } else {


                        JSONArray mBaseCategoryJsonArray = jsonObject.getJSONArray("Basecats");
                        for (int i = 0; i < mBaseCategoryJsonArray.length(); i++) {
                            String baseCategoryId = isNullOrEmpty(mBaseCategoryJsonArray.getJSONObject(i), "BaseCategoryId");
                            String warehouseid ="1";
                            String baseCategoryName = isNullOrEmpty(mBaseCategoryJsonArray.getJSONObject(i), "BaseCategoryName");
                            String logoUrl = isNullOrEmpty(mBaseCategoryJsonArray.getJSONObject(i), "LogoUrl");
                            mBaseCatBeanArrayList.add(new BaseCatBean(baseCategoryId, warehouseid, baseCategoryName, logoUrl));
                        }
                        JSONArray mCategoriesJsonArray = jsonObject.getJSONArray("Categories");
                        for (int i = 0; i < mCategoriesJsonArray.length(); i++) {
                            String baseCategoryId = isNullOrEmpty(mCategoriesJsonArray.getJSONObject(i), "BaseCategoryId");
                            String categoryid = isNullOrEmpty(mCategoriesJsonArray.getJSONObject(i), "Categoryid");
                            String warehouseid ="1";
                            String categoryName = mCategoriesJsonArray.getJSONObject(i).getString("CategoryName");
                            String logoUrl = isNullOrEmpty(mCategoriesJsonArray.getJSONObject(i), "LogoUrl");
                            mCategoryBeanArrayList.add(new CategoryBean(baseCategoryId, categoryid, warehouseid, categoryName, logoUrl));
                        }
                        JSONArray mSubCategoriesJsonArray = jsonObject.getJSONArray("SubCategories");
                        for (int i = 0; i < mSubCategoriesJsonArray.length(); i++) {
                            String subCategoryId = isNullOrEmpty(mSubCategoriesJsonArray.getJSONObject(i), "SubCategoryId");
                            String categoryid = isNullOrEmpty(mSubCategoriesJsonArray.getJSONObject(i), "Categoryid");
                            String subcategoryName = isNullOrEmpty(mSubCategoriesJsonArray.getJSONObject(i), "SubcategoryName");
                            mSubCategoriesBeanArrayList.add(new SubCategoriesBean(subCategoryId, categoryid, subcategoryName));
                        }
                        BaseCatSubCatBean mBaseCatSubCatBean = new BaseCatSubCatBean(mBaseCatBeanArrayList, mCategoryBeanArrayList, mSubCategoriesBeanArrayList);

                        if (getActivity() != null) {
                            ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
                            mBaseCatSubCatCat.putObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, mBaseCatSubCatBean);
                            mBaseCatSubCatCat.commit();


                            ArrayList<PopularBrandBean> mPopularBrandBeenArrayList;
                            Type typePopularBrandBeanArrayList = new TypeToken<ArrayList<PopularBrandBean>>() {
                            }.getType();
                            mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.POPULAR_BRANDS_PREF, getActivity().MODE_PRIVATE);
                            mPopularBrandBeenArrayList = mBaseCatSubCatCat.getArray(Constant.POPULAR_BRANDS_PREFOBJ, typePopularBrandBeanArrayList);
                            if (mPopularBrandBeenArrayList != null && !mPopularBrandBeenArrayList.isEmpty())
                                isPopularBrandItemListAvail = true;
                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, getActivity().MODE_PRIVATE);
                            RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);


                            HomeFragRecyclerViewAdapter mHomeFragRecyclerViewAdapter = new HomeFragRecyclerViewAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mPopularBrandBeenArrayList == null ? new ArrayList<PopularBrandBean>() : mPopularBrandBeenArrayList);
                            mHomeFragRecyclerView.setAdapter(mHomeFragRecyclerViewAdapter);

                            ArrayList<BaseCatBean> listDataHeader = mBaseCatBeanArrayList;
                            HashMap<BaseCatBean, ArrayList<CategoryBean>> listDataChild = new HashMap();
                            for (int i = 0; i < listDataHeader.size(); i++) {
                                ArrayList<CategoryBean> listDataChildArray = new ArrayList<>();
                                for (int j = 0; j < mCategoryBeanArrayList.size(); j++) {
                                    if (listDataHeader.get(i).getBaseCategoryId().equalsIgnoreCase(mCategoryBeanArrayList.get(j).getBaseCategoryId())) {
                                        listDataChildArray.add(mCategoryBeanArrayList.get(j));
                                    }
                                }
                                listDataChild.put(listDataHeader.get(i), listDataChildArray);
                            }
                            ((HomeActivity) getActivity()).listDataHeaderGlobal = listDataHeader;
                            ((HomeActivity) getActivity()).listDataChildGlobal = listDataChild;
                            ((HomeActivity) getActivity()).setupNavigationView(listDataHeader, listDataChild);

                            if (Utils.isInternetConnected(getActivity())) {
                                getPopularBrandTask = new GetPopularBrands().execute(mRetailerBean.getWarehouseid());
                            } else {
                                Toast.makeText(getActivity(), "Internet connection is not available", Toast.LENGTH_SHORT).show();
                            }
                            //grocery category

                            if (mBaseCatSubCatBean != null && (!mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty())) {
                                grocery.setVisibility(View.VISIBLE);
                                boolean isListFound = false;
                                for (int i = 0; i < mBaseCatSubCatBean.getmCategoryBeanArrayList().size(); i++) {
                                    if (mBaseCatSubCatBean.getmCategoryBeanArrayList().get(i).getBaseCategoryId().equalsIgnoreCase("" + 1)) {
                                        mgrocerycategoryBeanArrayList1.add(mBaseCatSubCatBean.getmCategoryBeanArrayList().get(i));
                                        isListFound = true;

                                    }
                                }
                                if (isListFound) {
                                    try {
                                        Log.d("grocery11", String.valueOf(mgrocerycategoryBeanArrayList1));
                                        ComplexPreferences grocerycategory = ComplexPreferences.getComplexPreferences(getActivity(), Constant.GROCERY_ITEM_PREF, getActivity().MODE_PRIVATE);
                                        grocerycategory.putObject(Constant.GROCERY_ITEM_PREFOBJ, mgrocerycategoryBeanArrayList1);
                                        grocerycategory.commit();
                                        GroceryAdapter groceryAdapter = new GroceryAdapter(getActivity(), mBaseCatSubCatBean,mgrocerycategoryBeanArrayList1, rowIMageHeight, rowIMageWidth, getFragmentManager());
                                        mGroceryRecyclerView.setAdapter(groceryAdapter);
                                        groceryAdapter.notifyDataSetChanged();
                                        Log.e("isListFound", String.valueOf(isListFound));
                                    }
                                    catch(IndexOutOfBoundsException e){
                                        e.printStackTrace();
                                    }
                                } else {
                                    //getActivity().getSupportFragmentManager().popBackStack();
                                    Toast.makeText(getActivity(), "No Item found under this category", Toast.LENGTH_SHORT).show();
                                }

                            }
                            // Homecare category
                          if (mBaseCatSubCatBean != null && (!mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty())) {
                                homecare.setVisibility(View.VISIBLE);

                                boolean isListFound = false;
                                for (int i = 0; i < mBaseCatSubCatBean.getmCategoryBeanArrayList().size(); i++) {
                                    if (mBaseCatSubCatBean.getmCategoryBeanArrayList().get(i).getBaseCategoryId().equalsIgnoreCase("" + 2)) {
                                        mhomecarecategoryBeanArrayList1.add(mBaseCatSubCatBean.getmCategoryBeanArrayList().get(i));
                                        isListFound = true;

                                    }
                                }
                                if (isListFound) {
                                    try {
                                        Log.d("grocery22", String.valueOf(mhomecarecategoryBeanArrayList1));
                                        ComplexPreferences homecare = ComplexPreferences.getComplexPreferences(getActivity(), Constant.HOMECARE_ITEM_PREF, getActivity().MODE_PRIVATE);
                                        homecare.putObject(Constant.HOMECARE_ITEM_PREFOBJ, mhomecarecategoryBeanArrayList1);
                                        homecare.commit();
                                        homecareadapter homecareAdapter = new homecareadapter(getActivity(), mBaseCatSubCatBean,mhomecarecategoryBeanArrayList1, rowIMageHeight, rowIMageWidth, getFragmentManager());
                                        mHomecareRecyclerView.setAdapter(homecareAdapter);
                                        homecareAdapter.notifyDataSetChanged();
                                        Log.e("isListFound", String.valueOf(isListFound));
                                    }
                                    catch(IndexOutOfBoundsException e){
                                        e.printStackTrace();
                                    }
                                } else {
                                    //getActivity().getSupportFragmentManager().popBackStack();
                                    Toast.makeText(getActivity(), "No Item found under this category", Toast.LENGTH_SHORT).show();
                                }
                            }
                            // API call of Personal care
                       if (mBaseCatSubCatBean != null && (!mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty())) {
                                personalcare.setVisibility(View.VISIBLE);
                                boolean isListFound = false;
                                for (int i = 0; i < mBaseCatSubCatBean.getmCategoryBeanArrayList().size(); i++) {
                                    if (mBaseCatSubCatBean.getmCategoryBeanArrayList().get(i).getBaseCategoryId().equalsIgnoreCase("" + 3)) {
                                        mpersonalcaregoryBeanArrayList1.add(mBaseCatSubCatBean.getmCategoryBeanArrayList().get(i));
                                        isListFound = true;

                                    }
                                }
                                if (isListFound) {
                                    try {
                                        Log.d("grocery33", String.valueOf(mpersonalcaregoryBeanArrayList1));
                                        ComplexPreferences personalcare = ComplexPreferences.getComplexPreferences(getActivity(), Constant.PERSONAL_ITEM_PREF, getActivity().MODE_PRIVATE);
                                        personalcare.putObject(Constant.PERSONAL_ITEM_PREFOBJ, mpersonalcaregoryBeanArrayList1);
                                        personalcare.commit();
                                        personalcareadapter personalcareAdapter = new personalcareadapter(getActivity(), mBaseCatSubCatBean,mpersonalcaregoryBeanArrayList1, rowIMageHeight, rowIMageWidth, getFragmentManager());
                                        mpersonalcareRecyclerView.setAdapter(personalcareAdapter);
                                        personalcareAdapter.notifyDataSetChanged();
                                        Log.e("isListFound", String.valueOf(isListFound));
                                    }
                                    catch(IndexOutOfBoundsException e){
                                        e.printStackTrace();
                                    }
                                } else {
                                    //getActivity().getSupportFragmentManager().popBackStack();
                                    Toast.makeText(getActivity(), "No Item found under this category", Toast.LENGTH_SHORT).show();
                                }

                            }

                            else {
                                getActivity().getSupportFragmentManager().popBackStack();
                                Toast.makeText(getActivity(), "No Item found under this category", Toast.LENGTH_SHORT).show();
                            }



                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // clear all the Complex preference
                BaseCatSubCatBean mBaseCatSubCatBean = new BaseCatSubCatBean(mBaseCatBeanArrayList, mCategoryBeanArrayList, mSubCategoriesBeanArrayList);
                ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat.putObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, mBaseCatSubCatBean);
                mBaseCatSubCatCat.commit();
                mBaseCatSubCatCat.clear();
                ComplexPreferences grocerycategory = ComplexPreferences.getComplexPreferences(getActivity(), Constant.GROCERY_ITEM_PREF, getActivity().MODE_PRIVATE);
                grocerycategory.putObject(Constant.GROCERY_ITEM_PREFOBJ, mgrocerycategoryBeanArrayList1);
                grocerycategory.commit();
                 grocerycategory.clear();
                ComplexPreferences homecare = ComplexPreferences.getComplexPreferences(getActivity(), Constant.HOMECARE_ITEM_PREF, getActivity().MODE_PRIVATE);
                homecare.putObject(Constant.HOMECARE_ITEM_PREFOBJ, mhomecarecategoryBeanArrayList1);
                homecare.commit();
                homecare.clear();
                ComplexPreferences personalcare = ComplexPreferences.getComplexPreferences(getActivity(), Constant.PERSONAL_ITEM_PREF, getActivity().MODE_PRIVATE);
                personalcare.putObject(Constant.PERSONAL_ITEM_PREFOBJ, mpersonalcaregoryBeanArrayList1);
                personalcare.commit();
               personalcare.clear();
                Toast.makeText(getActivity(), "Improper response from server", Toast.LENGTH_SHORT).show();

            }

          /*  if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
            }*/
        }
    }

  /* public class ShopbyCompany extends  AsyncTask<String ,Void,JSONArray> {
       Dialog mDialog;
       AnimationDrawable animation;

       @Override
       protected void onPreExecute() {
           super.onPreExecute();
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

       @Override
       protected JSONArray doInBackground(String... params) {
           JSONArray jsonArrayFromUrl = null;
           try {
               jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_SHOPBYCMPNY + "?subcategoryid=" +7, null, HttpUrlConnectionJSONParser.Http.GET);
           } catch (Exception e) {
               e.printStackTrace();
           }
           Log.d("jsoncheckshop", String.valueOf(jsonArrayFromUrl));
           return jsonArrayFromUrl;
       }

       @Override
       protected void onPostExecute(JSONArray jsonArray) {
           super.onPostExecute(jsonArray);
           ArrayList<shopbycompanybean> mshopcompany = new ArrayList<>();
           mshopcompany.clear();

           if (jsonArray != null && jsonArray.length() > 0) {
               for (int i = 0; i < jsonArray.length(); i++) {
                   Log.d("jsoncheckshop11", String.valueOf(jsonArray.length()));
                   try {
                       JSONObject jsonObject = jsonArray.getJSONObject(i);
                       String SubsubCategoryid = isNullOrEmpty(jsonObject, "SubsubCategoryid");
                       String SubsubcategoryName = isNullOrEmpty(jsonObject, "SubsubcategoryName");
                       String LogoUrl = isNullOrEmpty(jsonObject, "LogoUrl");
                       String Categoryid = isNullOrEmpty(jsonObject, "Categoryid");
                       mshopcompany.add(new shopbycompanybean(Categoryid,SubsubCategoryid, SubsubcategoryName, LogoUrl ));
                   }

                   catch (Exception e) {
                       e.printStackTrace();
                   }
               }
               if (getActivity() != null) {

                   ComplexPreferences shopbycompanypref = ComplexPreferences.getComplexPreferences((getActivity()), Constant.ShopBY_PREF, getActivity().MODE_PRIVATE);
                   shopbycompanypref.putObject(Constant.ShopBY_PREF_OBJ, mshopcompany);
                   shopbycompanypref.commit();

                   Type typeshopbyBeanArrayList = new TypeToken<ArrayList<shopbycompanybean>>() {
                   }.getType();

                   mshopcompany = shopbycompanypref.getArray(Constant.ShopBY_PREF_OBJ, typeshopbyBeanArrayList);

                   if (mshopcompany != null && !mshopcompany.isEmpty()) {
                       Log.d("mshopcompany", String.valueOf(mshopcompany));
                       try {
                           ShopbycompanyAdapter Adapter = new ShopbycompanyAdapter(getActivity(), rowIMageHeight, rowIMageWidth, getFragmentManager(), mshopcompany == null ? new ArrayList<shopbycompanybean>() : mshopcompany);
                           shopCardRecyclerView.setAdapter(Adapter);
                       } catch (IndexOutOfBoundsException e) {
                           startActivity(new Intent(getActivity(), HomeActivity.class));
                       } catch (Exception e) {
                           startActivity(new Intent(getActivity(), HomeActivity.class));
                       }

                   }

               }
           } else {

               Toast.makeText(getActivity(), "Improper response from server", Toast.LENGTH_SHORT).show();

               ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.ShopBY_PREF, getActivity().MODE_PRIVATE);
               mBaseCatSubCatCat.putObject(Constant.ShopBY_PREF_OBJ, mshopcompany);
               mBaseCatSubCatCat.commit();
               mBaseCatSubCatCat.clear();

           }


           if (mDialog.isShowing())

           {
               animation.stop();
               mDialog.dismiss();
           }
       }
   }*/
//API Call of popular brands
    public class GetPopularBrands extends AsyncTask<String, Void, JSONArray> {
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
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

        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArrayFromUrl = null;
            try {
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_BRAND_PROMOTION + "?warehouseid=" + params[0], null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("jsoncheck+++", String.valueOf(Constant.BASE_URL_BRAND_PROMOTION));
            return jsonArrayFromUrl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            Log.d("jsoncheck+++", String.valueOf(jsonArray));

            ArrayList<PopularBrandBean> mPopularBrandBeenArrayList = new ArrayList<>();
            mPopularBrandBeenArrayList.clear();
            if (jsonArray != null && jsonArray.length() > 0) {


                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject mJsonObject = jsonArray.getJSONObject(i);
                        String SubCategoryId = isNullOrEmpty(mJsonObject, "SubCategoryId");
                      //  String CompanyId = isNullOrEmpty(mJsonObject, "CompanyId");
                        String Categoryid = isNullOrEmpty(mJsonObject, "Categoryid");
                        String Warehouseid =  "1";
                        String CategoryName = isNullOrEmpty(mJsonObject, "CategoryName");
                        String SubcategoryName = isNullOrEmpty(mJsonObject, "SubsubcategoryName");
                        String Discription ="Description";
                        String SortOrder = isNullOrEmpty(mJsonObject, "SortOrder");
                        String IsPramotional = isNullOrEmpty(mJsonObject, "IsPramotional");
                        String CreatedDate = isNullOrEmpty(mJsonObject, "CreatedDate");
                        String UpdatedDate = isNullOrEmpty(mJsonObject, "UpdatedDate");
                        String CreatedBy = isNullOrEmpty(mJsonObject, "CreatedBy");
                        String UpdateBy = isNullOrEmpty(mJsonObject, "UpdateBy");
                        String Code = isNullOrEmpty(mJsonObject, "SubCategoryId");
                        String LogoUrl = isNullOrEmpty(mJsonObject, "LogoUrl");
                        String Deleted = isNullOrEmpty(mJsonObject, "UpdateBy");
                        String IsActive = isNullOrEmpty(mJsonObject, "UpdateBy");
                        PopularBrandBean mPopularBrandBean = new PopularBrandBean(SubCategoryId, Categoryid, Categoryid, Warehouseid, CategoryName, SubcategoryName, Discription, SortOrder, IsPramotional, CreatedDate, UpdatedDate, CreatedBy, UpdateBy, Code, LogoUrl, Deleted, IsActive);
                        mPopularBrandBeenArrayList.add(mPopularBrandBean);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (getActivity() != null) {


                    ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.POPULAR_BRANDS_PREF2, getActivity().MODE_PRIVATE);
                    mBaseCatSubCatCat.putObject(Constant.POPULAR_BRANDS_PREFOBJ2, mPopularBrandBeenArrayList);
                    mBaseCatSubCatCat.commit();


                    ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, getActivity().MODE_PRIVATE);
                    RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);

                    mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
                    BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);
                    if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {
                        isItemListAvail = true;

                        Type typePopularBrandBeanArrayList = new TypeToken<ArrayList<PopularBrandBean>>() {
                        }.getType();

                        mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.POPULAR_BRANDS_PREF2, getActivity().MODE_PRIVATE);
                        mPopularBrandBeenArrayList = mBaseCatSubCatCat.getArray(Constant.POPULAR_BRANDS_PREFOBJ2, typePopularBrandBeanArrayList);
                        mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.POPULAR_BRANDS_PREF, getActivity().MODE_PRIVATE);
                       // mPopularBrandBeenArrayList = mBaseCatSubCatCat.getArray(Constant.POPULAR_BRANDS_PREFOBJ, typePopularBrandBeanArrayList);
                        if (mPopularBrandBeenArrayList != null && !mPopularBrandBeenArrayList.isEmpty())
                            isPopularBrandItemListAvail = true;
                        try {
                            HomeFragPopularBrandAdapter mHomeFragRecyclerViewAdapter = new HomeFragPopularBrandAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mPopularBrandBeenArrayList == null ? new ArrayList<PopularBrandBean>() : mPopularBrandBeenArrayList);
                            mHomeFragRecyclerView2.setAdapter(mHomeFragRecyclerViewAdapter);
                        } catch (IndexOutOfBoundsException e) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        } catch (Exception e) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        }
                    }

                    if (Utils.isInternetConnected(getActivity())) {


                        getBrandwisepramotionTask = new GetBrandWisePromotion().execute();


                    } else {
                        Toast.makeText(getActivity(), "Internet connection is not available", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            else {
                Toast.makeText(getActivity(), "Improper response from server", Toast.LENGTH_SHORT).show();
                ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.POPULAR_BRANDS_PREF2, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat.putObject(Constant.POPULAR_BRANDS_PREFOBJ2, mPopularBrandBeenArrayList);
                mBaseCatSubCatCat.commit();
                mBaseCatSubCatCat.clear();
            }
            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
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
                Log.e("HomeFragApi", key + " is not available which should come in response");
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void setImagesDynamicSize() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        if (displaymetrics.widthPixels >= 480 && displaymetrics.widthPixels < 720) {
            sliderIMageHeight = 219;
            sliderIMageWidth = displaymetrics.widthPixels;

            /*rowIMageHeight = 219;
            rowIMageWidth = 230;*/

            rowIMageHeight = 93;
            rowIMageWidth = 116;
        } else if (displaymetrics.widthPixels >= 720 && displaymetrics.widthPixels < 1080) {
            sliderIMageHeight = 348;
            sliderIMageWidth = displaymetrics.widthPixels;

            /*rowIMageHeight = 329;
            rowIMageWidth = 346;*/

            rowIMageHeight = 140;
            rowIMageWidth = 175;
        } else if (displaymetrics.widthPixels >= 1080 && displaymetrics.widthPixels < 1440) {
            sliderIMageHeight = 492;
            sliderIMageWidth = displaymetrics.widthPixels;

            /*rowIMageHeight = 518;
            rowIMageWidth = 494;*/

            rowIMageHeight = 210;
            rowIMageWidth = 263;

        } else if (displaymetrics.widthPixels >= 1440) {
            sliderIMageHeight = 656;
            sliderIMageWidth = displaymetrics.widthPixels;

            /*rowIMageHeight = 619;
            rowIMageWidth = 658;*/

            rowIMageHeight = 279;
            rowIMageWidth = 349;
        } else {
            sliderIMageHeight = 328;
            sliderIMageWidth = displaymetrics.widthPixels;

            /*rowIMageHeight = 229;
            rowIMageWidth = 246;*/

            rowIMageHeight = 140;
            rowIMageWidth = 175;
        }
    }


    public void setupViewpager() {

        flag = new int[]{

/*
                R.drawable.bag2,
                R.drawable.sb9,
                R.drawable.teen19,
                R.drawable.clg15,
                R.drawable.sb9,
                R.drawable.sb10,
                R.drawable.wb10,
                R.drawable.sb12,
                R.drawable.teen4,
                R.drawable.teen5,


                R.drawable.clg5,
                R.drawable.sb18,
                R.drawable.sb10,
                R.drawable.teen16,
                R.drawable.wb4,
                R.drawable.wb5
*/



                R.drawable.grocerry,
//                        R.drawable.vp2,
                R.drawable.grocerry2,
//                        R.drawable.vp4,
//                        R.drawable.vp5,
                R.drawable.grocerry3,


        };

        price.add("19.99");
        price.add("15.50");
        price.add("14.60");
        price.add("14.50");
        price.add("14.50");






        adapter = new ViewPagerAdapter2(getActivity(), flag, price,picList);
        viewPager.setAdapter(adapter);
        final float density = getResources().getDisplayMetrics().density;
        circlePageIndicator.setRadius(3 * density);
//        circlePageIndicator.setFillColor(0x8800BCD4);
        circlePageIndicator.setFillColor(0x99FF4500);
        //circlePageIndicator.setFillColor(0x99FFFFFF);
        circlePageIndicator.setViewPager(viewPager);
    }

//API call of GetBrandWisePromotion
    public class GetBrandWisePromotion extends AsyncTask<String, Void, JSONArray> {

        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
           /* mDialog = new_added Dialog(getActivity());
            mDialog.getWindow().setBackgroundDrawable(new_added ColorDrawable(Color.TRANSPARENT));
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Logging in please wait...");
            ImageView la = (ImageView) mDialog.findViewById(R.id.gridprogressBar);
            la.setBackgroundResource(R.drawable.custom_progress_dialog_animation);
            animation = (AnimationDrawable) la.getBackground();
            animation.start();
            mDialog.setCancelable(false);
            mDialog.show();*/

                progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArrayFromUrl = null;
            try {
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_BRAND_WISE_PROMOTION, null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayFromUrl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            Log.d("jsoncheck111", String.valueOf(jsonArray));
            ArrayList<ItemList> mPopularBrandBeenArrayList1 = new ArrayList<>();
            mPopularBrandBeenArrayList1.clear();
           if (jsonArray != null && jsonArray.length() > 0) {
                lerPopularItem.setVisibility(View.VISIBLE);

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject mJsonObject = jsonArray.getJSONObject(i);
                        String ItemId = isNullOrEmpty(mJsonObject, "ItemId");
                        String Categoryid = isNullOrEmpty(mJsonObject, "Categoryid");
                        String SubCategoryId = isNullOrEmpty(mJsonObject, "SubCategoryId");
                        String SubsubCategoryid = isNullOrEmpty(mJsonObject, "SubsubCategoryid");
                        String itemname = isNullOrEmpty(mJsonObject, "itemname");
                        String price = isNullOrEmpty(mJsonObject, "price");
                        String SellingUnitName = isNullOrEmpty(mJsonObject, "SellingUnitName");
                        String ItemNumber = isNullOrEmpty(mJsonObject, "Number");
                        String SellingSku = isNullOrEmpty(mJsonObject, "SellingSku");
                        String SubcategoryName = isNullOrEmpty(mJsonObject, "SubcategoryName");
                        String UnitPrice = isNullOrEmpty(mJsonObject, "UnitPrice");
                        String VATTax = isNullOrEmpty(mJsonObject, "VATTax");
                        String LogoUrl = isNullOrEmpty(mJsonObject, "LogoUrl");
                        String MinOrderQty = isNullOrEmpty(mJsonObject, "MinOrderQty");
                        String Discount = isNullOrEmpty(mJsonObject, "Discount");
                        String TotalTaxPercentage = isNullOrEmpty(mJsonObject, "TotalTaxPercentage");
                        String HindiName = isNullOrEmpty(mJsonObject, "HindiName");
                        String DpPoint = "";
                        String PromoPoint = isNullOrEmpty(mJsonObject, "promoPerItems");
                        String MarginPoint = isNullOrEmpty(mJsonObject, "marginPoint");
                        String WarehouseId = isNullOrEmpty(mJsonObject, "WarehouseId");
                        String CompanyId = isNullOrEmpty(mJsonObject, "CompanyId");
                        String Isoffer = isNullOrEmpty(mJsonObject, "IsOffer");


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

                        mPopularBrandBeenArrayList1.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, SubcategoryName, "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, WarehouseId, CompanyId, ItemNumber,Isoffer));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.POPULAR_BRANDS_PREF1, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat1.putObject(Constant.POPULAR_BRANDS_PREFOBJ1, mPopularBrandBeenArrayList1);
                mBaseCatSubCatCat1.commit();
                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, getActivity().MODE_PRIVATE);
                RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
                mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
                BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat1.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);
                if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {
                    if (mPopularBrandBeenArrayList1 != null && !mPopularBrandBeenArrayList1.isEmpty())
                    try {
                        HomeFragBrandWiseAdapter mHomeFragRecyclerViewAdapter1 = new HomeFragBrandWiseAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mPopularBrandBeenArrayList1 == null ? new ArrayList<ItemList>() : mPopularBrandBeenArrayList1);
                        mHomeFragRecyclerView1.setAdapter(mHomeFragRecyclerViewAdapter1);
                    } catch (IndexOutOfBoundsException e) {
                        startActivity(new Intent(getActivity(), HomeActivity.class));
                    } catch (Exception e) {
                        startActivity(new Intent(getActivity(), HomeActivity.class));
                    }

                }

            }

            else
                {
               ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.POPULAR_BRANDS_PREF1, getActivity().MODE_PRIVATE);
               mBaseCatSubCatCat1.putObject(Constant.POPULAR_BRANDS_PREFOBJ1, mPopularBrandBeenArrayList1);
               mBaseCatSubCatCat1.commit();
               mBaseCatSubCatCat1.clear();

            }
            progressBar.setVisibility(View.GONE);
        }
    }


    //API call of AppPromotion
    public class AppPromotion extends AsyncTask<String, Void, JSONArray> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArrayFromUrl = null;
            try {
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_APP_PROMOTION, null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayFromUrl;
        }
        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            ArrayList<PromotionPojo> mAppPromotionList = new ArrayList<>();
            mAppPromotionList.clear();
            Log.d("jsoncheck333", String.valueOf(jsonArray));
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject mJsonObject = jsonArray.getJSONObject(i);
                        String Id = isNullOrEmpty(mJsonObject, "Id");
                        String Name = isNullOrEmpty(mJsonObject, "Name");
                        String Discription = isNullOrEmpty(mJsonObject, "Discription");
                        String CreatedDate = isNullOrEmpty(mJsonObject, "CreatedDate");
                        String UpdatedDate = isNullOrEmpty(mJsonObject, "UpdatedDate");
                        String LogoUrl = isNullOrEmpty(mJsonObject, "LogoUrl");
                        String Deleted = isNullOrEmpty(mJsonObject, "Deleted");
                        String IsActive = isNullOrEmpty(mJsonObject, "IsActive");
                        mAppPromotionList.add(new PromotionPojo(Id, Name, Discription, LogoUrl));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.APP_PROMOTION_PREF, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat1.putObject(Constant.APP_PROMOTION_PREFOBJ, mAppPromotionList);
                mBaseCatSubCatCat1.commit();
                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, getActivity().MODE_PRIVATE);
                RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
                mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
                BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat1.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);
                if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {
                    if (mAppPromotionList != null && !mAppPromotionList.isEmpty())
                    try {
                        AppPromotionAdapter mAppPromotionAdapter = new AppPromotionAdapter(getActivity(), mAppPromotionList, rowIMageHeight, rowIMageWidth);
                        mAppPromotionRecycler.setAdapter(mAppPromotionAdapter);
                    } catch (IndexOutOfBoundsException e) {
                        startActivity(new Intent(getActivity(), HomeActivity.class));
                    } catch (Exception e) {
                        startActivity(new Intent(getActivity(), HomeActivity.class));
                    }
                }
            } else {
                Toast.makeText(getActivity(), "Improper response from server", Toast.LENGTH_SHORT).show();
                Log.d("APP_PROMOTION_PREFOBJ", String.valueOf(mAppPromotionList));
                ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.APP_PROMOTION_PREF, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat1.putObject(Constant.APP_PROMOTION_PREFOBJ, mAppPromotionList);
                mBaseCatSubCatCat1.commit();
                mBaseCatSubCatCat1.clear();
            }
            progressBar.setVisibility(View.GONE);
        }
    }
    //API call of NewlyAddedBrands
    public class NewlyAddedBrands extends AsyncTask<String, Void, JSONArray> {
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArrayFromUrl = null;
            try {
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_NEWLY_ADDED_BRANDS, null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayFromUrl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            ArrayList<NewlyAddedBrandsBean> mNewlyAddedBrandsBeanList = new ArrayList<>();
            mNewlyAddedBrandsBeanList.clear();
            Log.d("jsoncheck444", String.valueOf(jsonArray));
            if (jsonArray != null && jsonArray.length() > 0) {
                lerNewlyAdded.setVisibility(View.VISIBLE);

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject mJsonObject = jsonArray.getJSONObject(i);
                        String SubCategoryId = isNullOrEmpty(mJsonObject, "SubCategoryId");
                        //  String CompanyId = isNullOrEmpty(mJsonObject, "CompanyId");
                        String Categoryid = isNullOrEmpty(mJsonObject, "Categoryid");
                        // String Warehouseid = isNullOrEmpty(mJsonObject, "Warehouseid");
                        String CategoryName = isNullOrEmpty(mJsonObject, "CategoryName");
                        String SubcategoryName = isNullOrEmpty(mJsonObject, "SubcategoryName");
                        //  String Discription = isNullOrEmpty(mJsonObject, "Discription");
                        String SortOrder = isNullOrEmpty(mJsonObject, "SortOrder");
                        String IsPramotional = isNullOrEmpty(mJsonObject, "IsPramotional");
                        String CreatedDate = isNullOrEmpty(mJsonObject, "CreatedDate");
                        String UpdatedDate = isNullOrEmpty(mJsonObject, "UpdatedDate");
                        String CreatedBy = isNullOrEmpty(mJsonObject, "CreatedBy");
                        String UpdateBy = isNullOrEmpty(mJsonObject, "UpdateBy");
                        String Code = isNullOrEmpty(mJsonObject, "SubCategoryId");
                        String LogoUrl = isNullOrEmpty(mJsonObject, "LogoUrl");
                        String Deleted = isNullOrEmpty(mJsonObject, "UpdateBy");
                        String IsActive = isNullOrEmpty(mJsonObject, "UpdateBy");
                        NewlyAddedBrandsBean mNewlyAddedBrandsBean = new NewlyAddedBrandsBean(SubCategoryId, "CompanyId", Categoryid, "Warehouseid", CategoryName, SubcategoryName, "Discription", SortOrder, IsPramotional, CreatedDate, UpdatedDate, CreatedBy, UpdateBy, Code, LogoUrl, Deleted, IsActive);
                        mNewlyAddedBrandsBeanList.add(mNewlyAddedBrandsBean);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (getActivity() != null) {
                    ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.NEWLY_ADDED_BRANDS_PREF, getActivity().MODE_PRIVATE);
                    mBaseCatSubCatCat.putObject(Constant.NEWLY_ADDED_BRANDS_PREFOBJ, mNewlyAddedBrandsBeanList);
                    mBaseCatSubCatCat.commit();


                    ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, getActivity().MODE_PRIVATE);
                    RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);

                    mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
                    BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);
                    if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {
                        if (mNewlyAddedBrandsBeanList != null && !mNewlyAddedBrandsBeanList.isEmpty())

                            try {
                                NewlyAddedBrandsAdapter mNewlyAddedBrandsAdapter = new NewlyAddedBrandsAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mNewlyAddedBrandsBeanList == null ? new ArrayList<NewlyAddedBrandsBean>() : mNewlyAddedBrandsBeanList);
                                mNewlyAddedRecyclerView.setAdapter(mNewlyAddedBrandsAdapter);
                            } catch (IndexOutOfBoundsException e) {
                                startActivity(new Intent(getActivity(), HomeActivity.class));
                            } catch (Exception e) {
                                startActivity(new Intent(getActivity(), HomeActivity.class));
                            }
                    }
                }
            }
            else {
                Toast.makeText(getActivity(), "Improper response from server", Toast.LENGTH_SHORT).show();
                ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.NEWLY_ADDED_BRANDS_PREF, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat.putObject(Constant.NEWLY_ADDED_BRANDS_PREFOBJ, mNewlyAddedBrandsBeanList);
                mBaseCatSubCatCat.commit();
                mBaseCatSubCatCat.clear();
            }
            progressBar.setVisibility(View.GONE);
        }
    }
//Api call of AllTopAddedItem

    public class AllTopAddedItem extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);

        }


        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArrayFromUrl = null;
            try {
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_ALL_TOP_ADDED_ITEM, null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayFromUrl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            Log.d("jsoncheck555", String.valueOf(jsonArray));
            ArrayList<AllTopAddedItemList> mAllTopAddedItemArrayList = new ArrayList<>();
            mAllTopAddedItemArrayList.clear();

            if (jsonArray != null && jsonArray.length() > 0) {
                lerTopAdded.setVisibility(View.VISIBLE);

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject mJsonObject = jsonArray.getJSONObject(i);
                        String ItemId = isNullOrEmpty(mJsonObject, "ItemId");
                        String Categoryid = isNullOrEmpty(mJsonObject, "Categoryid");
                        String SubCategoryId = isNullOrEmpty(mJsonObject, "SubCategoryId");
                        String SubsubCategoryid = isNullOrEmpty(mJsonObject, "SubsubCategoryid");
                        String itemname = isNullOrEmpty(mJsonObject, "itemname");
                        String price = isNullOrEmpty(mJsonObject, "price");
                        String SellingUnitName = isNullOrEmpty(mJsonObject, "SellingUnitName");
                        String ItemNumber = isNullOrEmpty(mJsonObject, "Number");
                        String SellingSku = isNullOrEmpty(mJsonObject, "SellingSku");
                        String SubcategoryName = isNullOrEmpty(mJsonObject, "SubcategoryName");
                        String UnitPrice = isNullOrEmpty(mJsonObject, "UnitPrice");
                        String VATTax = isNullOrEmpty(mJsonObject, "VATTax");
                        String LogoUrl = isNullOrEmpty(mJsonObject, "LogoUrl");
                        String MinOrderQty = isNullOrEmpty(mJsonObject, "MinOrderQty");
                        String Discount = isNullOrEmpty(mJsonObject, "Discount");
                        String TotalTaxPercentage = isNullOrEmpty(mJsonObject, "TotalTaxPercentage");
                        String HindiName = isNullOrEmpty(mJsonObject, "HindiName");
                        String DpPoint = "";
                        String PromoPoint = isNullOrEmpty(mJsonObject, "promoPerItems");
                        String MarginPoint = isNullOrEmpty(mJsonObject, "marginPoint");
                        String WarehouseId = isNullOrEmpty(mJsonObject, "WarehouseId");
                        String CompanyId = isNullOrEmpty(mJsonObject, "CompanyId");
                        String IsOffer = isNullOrEmpty(mJsonObject, "IsOffer");


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

                        mAllTopAddedItemArrayList.add(new AllTopAddedItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, SubcategoryName, "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, WarehouseId, CompanyId, ItemNumber,IsOffer));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.ALL_TOP_ADDED_PREF, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat1.putObject(Constant.ALL_TOP_ADDED_PREFOBJ, mAllTopAddedItemArrayList);
                mBaseCatSubCatCat1.commit();
                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, getActivity().MODE_PRIVATE);
                RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
                mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
                BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat1.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);

                if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {
                    if (mAllTopAddedItemArrayList != null && !mAllTopAddedItemArrayList.isEmpty())
                        try {
                            HomeFragAllTopAddedAdapter mHomeFragAllTopAddedAdapter = new HomeFragAllTopAddedAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mAllTopAddedItemArrayList == null ? new ArrayList<AllTopAddedItemList>() : mAllTopAddedItemArrayList);
                            mAllTopAddedRecyclerView.setAdapter(mHomeFragAllTopAddedAdapter);
                            mHomeFragAllTopAddedAdapter.notifyDataSetChanged();
                        } catch (IndexOutOfBoundsException e) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        } catch (Exception e) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        }
                }
            } else {

                ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.ALL_TOP_ADDED_PREF, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat1.putObject(Constant.ALL_TOP_ADDED_PREFOBJ, mAllTopAddedItemArrayList);
                mBaseCatSubCatCat1.commit();
                mBaseCatSubCatCat1.clear();

            }

            progressBar.setVisibility(View.GONE);
        }

    }
// API call of Today dhamaka
    public class GetTodayDhamaka extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArrayFromUrl = null;
            try {
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_TODAY_DHAMAKA, null, HttpUrlConnectionJSONParser.Http.GET);
            }

            catch (Exception e) {
                e.printStackTrace();
            }

            return jsonArrayFromUrl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            ArrayList<TodayDhamakaPojo> mTodayDhamakaList = new ArrayList<>();
            mTodayDhamakaList.clear();

            Log.d("jsoncheck666", String.valueOf(jsonArray));
            if (jsonArray != null && jsonArray.length() > 0) {
                lerTodayDhamaka.setVisibility(View.VISIBLE);

                for (int i = 0; i < jsonArray.length(); i++) {

                    try {
                        JSONObject mJsonObject = jsonArray.getJSONObject(i);
                        String Id = isNullOrEmpty(mJsonObject, "Id");
                        String Name = isNullOrEmpty(mJsonObject, "Name");
                        String Discription = isNullOrEmpty(mJsonObject, "Discription");
                        String CreatedDate = isNullOrEmpty(mJsonObject, "CreatedDate");
                        String UpdatedDate = isNullOrEmpty(mJsonObject, "UpdatedDate");
                        String LogoUrl = isNullOrEmpty(mJsonObject, "LogoUrl");
                        String Deleted = isNullOrEmpty(mJsonObject, "Deleted");
                        String IsActive = isNullOrEmpty(mJsonObject, "IsActive");


                        mTodayDhamakaList.add(new TodayDhamakaPojo(Id, Name, Discription, LogoUrl));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.TODAY_DHAMAKA_PREF, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat1.putObject(Constant.TODAY_DHAMAKA_PREFOBJ, mTodayDhamakaList);
                mBaseCatSubCatCat1.commit();
                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, getActivity().MODE_PRIVATE);
                RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
                mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
                BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat1.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);

                if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {

                    if (mTodayDhamakaList != null && !mTodayDhamakaList.isEmpty())
                        try {
                            TodayDhamakaAdapter mTodayDhamakaAdapter = new TodayDhamakaAdapter(getActivity(), mTodayDhamakaList, rowIMageHeight, rowIMageWidth);
                            mTodayDhamakaRecycler.setAdapter(mTodayDhamakaAdapter);
                        } catch (IndexOutOfBoundsException e) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        } catch (Exception e) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));}
                }
            }
            else
                {
                ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.TODAY_DHAMAKA_PREF, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat1.putObject(Constant.TODAY_DHAMAKA_PREFOBJ, mTodayDhamakaList);
                mBaseCatSubCatCat1.commit();
                mBaseCatSubCatCat1.clear();
            }
            progressBar.setVisibility(View.GONE);
        }

    }
    //API call GetEmptyStockItem
    public class GetEmptyStockItem extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArrayFromUrl = null;
            try {
                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, getActivity().MODE_PRIVATE);
                RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_EMPTY_STOCK+"?CompanyId="+mRetailerBean.getCompanyId()+"&Warehouse_id="+mRetailerBean.getWarehouseid(), null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayFromUrl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            ArrayList<EmptyStockPojo> mEmptyStockList = new ArrayList<>();
            mEmptyStockList.clear();
            //    "LogoUrl":"http://ec2-54-200-35-172.us-west-2.compute.amazonaws.com/../../UploadedLogos/images (1).jpg","Deleted":false,"IsActive":true}]
            Log.d("jsoncheck777", String.valueOf(jsonArray));
            if (jsonArray != null && jsonArray.length() > 0) {
                lerEmptyStock.setVisibility(View.VISIBLE);

                for (int i = 0; i < jsonArray.length(); i++) {

                    try {

                        JSONObject mJsonObject = jsonArray.getJSONObject(i);
                        String StockId = isNullOrEmpty(mJsonObject, "StockId");
                        String CompanyId = isNullOrEmpty(mJsonObject, "CompanyId");
                        String WarehouseId = isNullOrEmpty(mJsonObject, "WarehouseId");
                        String WarehouseName = isNullOrEmpty(mJsonObject, "WarehouseName");
                        String SellingSku = isNullOrEmpty(mJsonObject, "SellingSku");
                        String ItemId = isNullOrEmpty(mJsonObject, "ItemId");
                        String ItemNumber = isNullOrEmpty(mJsonObject, "ItemNumber");
                        String ItemName = isNullOrEmpty(mJsonObject, "ItemName");
                        String CurrentInventory = isNullOrEmpty(mJsonObject, "CurrentInventory");
                        String ManualReason = isNullOrEmpty(mJsonObject, "ManualReason");
                        mEmptyStockList.add(new EmptyStockPojo(StockId, CompanyId, WarehouseId, WarehouseName,SellingSku,ItemId,ItemName,ItemNumber,CurrentInventory,ManualReason));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.EMPTY_STOCK_PREF, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat1.putObject(Constant.EMPTY_STOCK_PREFOBJ, mEmptyStockList);
                mBaseCatSubCatCat1.commit();
                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, getActivity().MODE_PRIVATE);
                RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
                mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
                BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat1.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);
                if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {
                    if (mEmptyStockList != null && !mEmptyStockList.isEmpty())
                        try {
                            EmptyStockAdapter mEmptyStockAdapter = new EmptyStockAdapter(getActivity(), mEmptyStockList, rowIMageHeight, rowIMageWidth);
                            mEmptyStockRecycler.setAdapter(mEmptyStockAdapter);
                        } catch (IndexOutOfBoundsException e) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        } catch (Exception e) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        }
                }

            } else {
                ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.EMPTY_STOCK_PREF, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat1.putObject(Constant.EMPTY_STOCK_PREFOBJ, mEmptyStockList);
                mBaseCatSubCatCat1.commit();
                mBaseCatSubCatCat1.clear();
            }
            progressBar.setVisibility(View.GONE);
        }
    }
//API call of Bulk item
    public class GetBulkItem extends AsyncTask<String, Void, JSONArray> {



        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);

        }


        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArrayFromUrl = null;
            try {
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_BULK_ITEM, null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayFromUrl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            ArrayList<BulkItemPojo> mBulkItemArrayList = new ArrayList<>();
            mBulkItemArrayList.clear();
            Log.d("jsoncheck888", String.valueOf(jsonArray));
            if (jsonArray != null && jsonArray.length() > 0) {
                lerBulkItem.setVisibility(View.VISIBLE);

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject mJsonObject = jsonArray.getJSONObject(i);
                        String ItemId = isNullOrEmpty(mJsonObject, "ItemId");
                        String Categoryid = isNullOrEmpty(mJsonObject, "Categoryid");
                        String SubCategoryId = isNullOrEmpty(mJsonObject, "SubCategoryId");
                        String SubsubCategoryid = isNullOrEmpty(mJsonObject, "SubsubCategoryid");
                        String itemname = isNullOrEmpty(mJsonObject, "itemname");
                        String price = isNullOrEmpty(mJsonObject, "price");
                        String SellingUnitName = isNullOrEmpty(mJsonObject, "SellingUnitName");
                        String ItemNumber = isNullOrEmpty(mJsonObject, "Number");
                        String SellingSku = isNullOrEmpty(mJsonObject, "SellingSku");
                        String SubcategoryName = isNullOrEmpty(mJsonObject, "SubcategoryName");
                        String UnitPrice = isNullOrEmpty(mJsonObject, "UnitPrice");
                        String VATTax = isNullOrEmpty(mJsonObject, "VATTax");
                        String LogoUrl = isNullOrEmpty(mJsonObject, "LogoUrl");
                        String MinOrderQty = isNullOrEmpty(mJsonObject, "MinOrderQty");
                        String Discount = isNullOrEmpty(mJsonObject, "Discount");
                        String TotalTaxPercentage = isNullOrEmpty(mJsonObject, "TotalTaxPercentage");
                        String HindiName = isNullOrEmpty(mJsonObject, "HindiName");
                        String DpPoint = "";
                        String PromoPoint = isNullOrEmpty(mJsonObject, "promoPerItems");
                        String MarginPoint = isNullOrEmpty(mJsonObject, "marginPoint");
                        String WarehouseId = isNullOrEmpty(mJsonObject, "WarehouseId");
                        String CompanyId = isNullOrEmpty(mJsonObject, "CompanyId");
                        String IsOffer = isNullOrEmpty(mJsonObject, "IsOffer");

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

                        mBulkItemArrayList.add(new BulkItemPojo(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, SubcategoryName, "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, WarehouseId, CompanyId, ItemNumber, IsOffer));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BULK_ITEM_PREF, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat1.putObject(Constant.BULK_ITEM_PREFOBJ, mBulkItemArrayList);
                mBaseCatSubCatCat1.commit();
                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, getActivity().MODE_PRIVATE);
                RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
                mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
                BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat1.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);

                if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {
                    if (mBulkItemArrayList != null && !mBulkItemArrayList.isEmpty())
                        try {
                            BulkItemAdapter mBulkItemAdapter = new BulkItemAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mBulkItemArrayList == null ? new ArrayList<BulkItemPojo>() : mBulkItemArrayList);
                            mBulkItemRecycler.setAdapter(mBulkItemAdapter);
                            mBulkItemAdapter.notifyDataSetChanged();
                        } catch (IndexOutOfBoundsException e) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        } catch (Exception e) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        }
                }

            }
            else {

                ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BULK_ITEM_PREF, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat1.putObject(Constant.BULK_ITEM_PREFOBJ, mBulkItemArrayList);
                mBaseCatSubCatCat1.commit();
                mBaseCatSubCatCat1.clear();
            }

            progressBar.setVisibility(View.GONE);
        }

    }
//API call of MostSelledItem
    public class GetMostSelledItem extends AsyncTask<String, Void, JSONArray> {


        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArrayFromUrl = null;
            try {
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_MOST_SELLED_ITEM, null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayFromUrl;
        }


        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            ArrayList<SelledItemPojo> mSelledItemArrayList = new ArrayList<>();
            mSelledItemArrayList.clear();
            Log.d("jsoncheck999", String.valueOf(jsonArray));
            if (jsonArray != null && jsonArray.length() > 0) {
                lerMostSalld.setVisibility(View.VISIBLE);

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject mJsonObject = jsonArray.getJSONObject(i);
                        String ItemId = isNullOrEmpty(mJsonObject, "ItemId");
                        String Categoryid = isNullOrEmpty(mJsonObject, "Categoryid");
                        String SubCategoryId = isNullOrEmpty(mJsonObject, "SubCategoryId");
                        String SubsubCategoryid = isNullOrEmpty(mJsonObject, "SubsubCategoryid");
                        String itemname = isNullOrEmpty(mJsonObject, "itemname");
                        String price = isNullOrEmpty(mJsonObject, "price");
                        String SellingUnitName = isNullOrEmpty(mJsonObject, "SellingUnitName");
                        String ItemNumber = isNullOrEmpty(mJsonObject, "Number");
                        String SellingSku = isNullOrEmpty(mJsonObject, "SellingSku");
                        String SubcategoryName = isNullOrEmpty(mJsonObject, "SubcategoryName");
                        String UnitPrice = isNullOrEmpty(mJsonObject, "UnitPrice");
                        String VATTax = isNullOrEmpty(mJsonObject, "VATTax");
                        String LogoUrl = isNullOrEmpty(mJsonObject, "LogoUrl");
                        String MinOrderQty = isNullOrEmpty(mJsonObject, "MinOrderQty");
                        String Discount = isNullOrEmpty(mJsonObject, "Discount");
                        String TotalTaxPercentage = isNullOrEmpty(mJsonObject, "TotalTaxPercentage");
                        String HindiName = isNullOrEmpty(mJsonObject, "HindiName");
                        String DpPoint = "";
                        String PromoPoint = isNullOrEmpty(mJsonObject, "promoPerItems");
                        String MarginPoint = isNullOrEmpty(mJsonObject, "marginPoint");
                        String WarehouseId = isNullOrEmpty(mJsonObject, "WarehouseId");
                        String CompanyId = isNullOrEmpty(mJsonObject, "CompanyId");
                        String IsOffer = isNullOrEmpty(mJsonObject, "IsOffer");


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

                        mSelledItemArrayList.add(new SelledItemPojo(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, SubcategoryName, "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, WarehouseId, CompanyId, ItemNumber,IsOffer));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.MOST_SELLED_ITEM_PREF, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat1.putObject(Constant.MOST_SELLED_ITEM_PREFOBJ, mSelledItemArrayList);
                mBaseCatSubCatCat1.commit();
                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, getActivity().MODE_PRIVATE);
                RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
                mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
                BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat1.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);

                if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {
                    if (mSelledItemArrayList != null && !mSelledItemArrayList.isEmpty())
                        try {
                            MostSelledItemAdapter mMostSelledItemAdapter = new MostSelledItemAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mSelledItemArrayList == null ? new ArrayList<SelledItemPojo>() : mSelledItemArrayList);
                            mMostSelledRecycler.setAdapter(mMostSelledItemAdapter);
                            mMostSelledItemAdapter.notifyDataSetChanged();
                        } catch (IndexOutOfBoundsException e) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        } catch (Exception e) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        }
                }
            } else {

                ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.MOST_SELLED_ITEM_PREF, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat1.putObject(Constant.MOST_SELLED_ITEM_PREFOBJ, mSelledItemArrayList);
                mBaseCatSubCatCat1.commit();
                mBaseCatSubCatCat1.clear();
            }

            progressBar.setVisibility(View.GONE);
        }

    }
    //API Call of GetFindItemHighDP
    public class GetFindItemHighDP extends AsyncTask<String, Void, JSONArray> {



        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);

        }


        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArrayFromUrl = null;
            try {
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_FIND_ITEM_HIGH_DP, null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayFromUrl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            ArrayList<FindHighDpPojo> mFindHighDpArrayList = new ArrayList<>();
            mFindHighDpArrayList.clear();
            Log.d("jsoncheck101", String.valueOf(jsonArray));
            if (jsonArray != null && jsonArray.length() > 0) {
                lerFindItemHighDp.setVisibility(View.VISIBLE);

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject mJsonObject = jsonArray.getJSONObject(i);
                        String ItemId = isNullOrEmpty(mJsonObject, "ItemId");
                        String Categoryid = isNullOrEmpty(mJsonObject, "Categoryid");
                        String SubCategoryId = isNullOrEmpty(mJsonObject, "SubCategoryId");
                        String SubsubCategoryid = isNullOrEmpty(mJsonObject, "SubsubCategoryid");
                        String itemname = isNullOrEmpty(mJsonObject, "itemname");
                        String price = isNullOrEmpty(mJsonObject, "price");
                        String SellingUnitName = isNullOrEmpty(mJsonObject, "SellingUnitName");
                        String ItemNumber = isNullOrEmpty(mJsonObject, "Number");
                        String SellingSku = isNullOrEmpty(mJsonObject, "SellingSku");
                        String SubcategoryName = isNullOrEmpty(mJsonObject, "SubcategoryName");
                        String UnitPrice = isNullOrEmpty(mJsonObject, "UnitPrice");
                        String VATTax = isNullOrEmpty(mJsonObject, "VATTax");
                        String LogoUrl = isNullOrEmpty(mJsonObject, "LogoUrl");
                        String MinOrderQty = isNullOrEmpty(mJsonObject, "MinOrderQty");
                        String Discount = isNullOrEmpty(mJsonObject, "Discount");
                        String TotalTaxPercentage = isNullOrEmpty(mJsonObject, "TotalTaxPercentage");
                        String HindiName = isNullOrEmpty(mJsonObject, "HindiName");
                        String DpPoint = "";
                        String PromoPoint = isNullOrEmpty(mJsonObject, "promoPerItems");
                        String MarginPoint = isNullOrEmpty(mJsonObject, "marginPoint");
                        String WarehouseId = isNullOrEmpty(mJsonObject, "WarehouseId");
                        String CompanyId = isNullOrEmpty(mJsonObject, "CompanyId");
                        String IsOffer = isNullOrEmpty(mJsonObject, "IsOffer");

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

                        mFindHighDpArrayList.add(new FindHighDpPojo(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, SubcategoryName, "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, WarehouseId, CompanyId, ItemNumber,IsOffer));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.FIND_HIGH_DP_PREF, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat1.putObject(Constant.FIND_HIGH_DP_PREFOBJ, mFindHighDpArrayList);
                mBaseCatSubCatCat1.commit();
                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, getActivity().MODE_PRIVATE);
                RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
                mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
                BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat1.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);

                if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {
                    if (mFindHighDpArrayList != null && !mFindHighDpArrayList.isEmpty())
                        try {
                            FindHighDpItemAdapter mFindHighDpItemAdapter = new FindHighDpItemAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mFindHighDpArrayList == null ? new ArrayList<FindHighDpPojo>() : mFindHighDpArrayList);
                            mFindHighDpRecycler.setAdapter(mFindHighDpItemAdapter);
                            mFindHighDpItemAdapter.notifyDataSetChanged();
                        } catch (IndexOutOfBoundsException e) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        } catch (Exception e) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        }
                }
            } else {
                ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.FIND_HIGH_DP_PREF, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat1.putObject(Constant.FIND_HIGH_DP_PREFOBJ, mFindHighDpArrayList);
                mBaseCatSubCatCat1.commit();
                mBaseCatSubCatCat1.clear();

            }

            progressBar.setVisibility(View.GONE);
        }

    }


    public class GetExeclusiveBrand extends AsyncTask<String, Void, JSONArray> {
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArrayFromUrl = null;
            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, getActivity().MODE_PRIVATE);
            RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
            try {
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_EXECLUSIVE_BRAND, null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayFromUrl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            ArrayList<ExeclusiveBrandsBean> mExeclusiveBrandsBeanList = new ArrayList<>();
            mExeclusiveBrandsBeanList.clear();
            System.out.println("jsoncheck222"+jsonArray);
            if (jsonArray != null && jsonArray.length() > 0) {
                lerNewlyAdded.setVisibility(View.VISIBLE);

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject mJsonObject = jsonArray.getJSONObject(i);
                        String SubCategoryId = isNullOrEmpty(mJsonObject, "SubCategoryId");
                        //  String CompanyId = isNullOrEmpty(mJsonObject, "CompanyId");
                        String Categoryid = isNullOrEmpty(mJsonObject, "Categoryid");
                        // String Warehouseid = isNullOrEmpty(mJsonObject, "Warehouseid");
                        String CategoryName = isNullOrEmpty(mJsonObject, "CategoryName");
                        String SubcategoryName = isNullOrEmpty(mJsonObject, "SubsubcategoryName");
                        //  String Discription = isNullOrEmpty(mJsonObject, "Discription");
                        String SortOrder = isNullOrEmpty(mJsonObject, "SortOrder");
                        String IsPramotional = isNullOrEmpty(mJsonObject, "IsPramotional");
                        String CreatedDate = isNullOrEmpty(mJsonObject, "CreatedDate");
                        String UpdatedDate = isNullOrEmpty(mJsonObject, "UpdatedDate");
                        String CreatedBy = isNullOrEmpty(mJsonObject, "CreatedBy");
                        String UpdateBy = isNullOrEmpty(mJsonObject, "UpdateBy");
                        String Code = isNullOrEmpty(mJsonObject, "SubCategoryId");
                        String LogoUrl = isNullOrEmpty(mJsonObject, "LogoUrl");
                        String Deleted = isNullOrEmpty(mJsonObject, "UpdateBy");
                        String IsActive = isNullOrEmpty(mJsonObject, "UpdateBy");
                        ExeclusiveBrandsBean mExeclusiveBrandsBean = new ExeclusiveBrandsBean(SubCategoryId, "CompanyId", Categoryid, "Warehouseid", CategoryName, SubcategoryName, "Discription", SortOrder, IsPramotional, CreatedDate, UpdatedDate, CreatedBy, UpdateBy, Code, LogoUrl, Deleted, IsActive);
                        mExeclusiveBrandsBeanList.add(mExeclusiveBrandsBean);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (getActivity() != null) {


                    ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.EXECLUSIVE_BRANDS_PREF, getActivity().MODE_PRIVATE);
                    mBaseCatSubCatCat.putObject(Constant.EXECLUSIVE_BRANDS_PREFOBJ, mExeclusiveBrandsBeanList);
                    mBaseCatSubCatCat.commit();

                    ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, getActivity().MODE_PRIVATE);
                    RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);

                    mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
                    BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);
                    if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {
                        if (mExeclusiveBrandsBeanList != null && !mExeclusiveBrandsBeanList.isEmpty())

                            try {
                                ExeclusiveBrandsAdapter mExeclusiveBrandsAdapter = new ExeclusiveBrandsAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mExeclusiveBrandsBeanList == null ? new ArrayList<ExeclusiveBrandsBean>() : mExeclusiveBrandsBeanList);
                                mExeclusiveRecycler.setAdapter(mExeclusiveBrandsAdapter);
                            } catch (IndexOutOfBoundsException e) {
                                startActivity(new Intent(getActivity(), HomeActivity.class));
                             } catch (Exception e) {
                                startActivity(new Intent(getActivity(), HomeActivity.class));
                            }
                    }

                }

            } else {
                Toast.makeText(getActivity(), "Improper response from server", Toast.LENGTH_SHORT).show();
                ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.EXECLUSIVE_BRANDS_PREF, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat.putObject(Constant.EXECLUSIVE_BRANDS_PREFOBJ, mExeclusiveBrandsBeanList);
                mBaseCatSubCatCat.commit();
                mBaseCatSubCatCat.clear();
            }
            progressBar.setVisibility(View.GONE);
        }
    }

    public class GetOffers extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArrayFromUrl = null;
            try {
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_OFFER_ITEM, null, HttpUrlConnectionJSONParser.Http.GET);
                System.out.println("offerapi"+Constant.BASE_URL_OFFER_ITEM);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayFromUrl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            ArrayList<OfferList> mOfferList = new ArrayList<>();
            ArrayList<OfferList> mOfferListAll = new ArrayList<>();
            mOfferList.clear();
            System.out.println("jsoncheck:::::::"+jsonArray);
            if (jsonArray != null && jsonArray.length() > 0) {
                lerOffer.setVisibility(View.VISIBLE);

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject mJsonObject = jsonArray.getJSONObject(i);
                        String ItemId = isNullOrEmpty(mJsonObject, "ItemId");
                        String Cityid = isNullOrEmpty(mJsonObject, "Cityid");
                        String CityName = isNullOrEmpty(mJsonObject, "CityName");
                        String Categoryid = isNullOrEmpty(mJsonObject, "Categoryid");
                        String SubCategoryId = isNullOrEmpty(mJsonObject, "SubCategoryId");
                        String SubsubCategoryid = isNullOrEmpty(mJsonObject, "SubsubCategoryid");
                        String SubSubCode = isNullOrEmpty(mJsonObject, "SubSubCode");
                        String WarehouseId = isNullOrEmpty(mJsonObject, "WarehouseId");
                        String  SupplierId  = isNullOrEmpty(mJsonObject, " SupplierId ");
                        String  SUPPLIERCODES  = isNullOrEmpty(mJsonObject, " SUPPLIERCODES ");
                        String CompanyId = isNullOrEmpty(mJsonObject, "CompanyId");
                        String CategoryName = isNullOrEmpty(mJsonObject, "CategoryName");
                        String BaseCategoryName = isNullOrEmpty(mJsonObject, "BaseCategoryName");
                        String SubcategoryName = isNullOrEmpty(mJsonObject, "SubcategoryName");
                        String SubsubcategoryName = isNullOrEmpty(mJsonObject, "SubsubcategoryName");
                        String SupplierName = isNullOrEmpty(mJsonObject, "SupplierName");
                        String itemname = isNullOrEmpty(mJsonObject, "itemname");
                        String SellingUnitName = isNullOrEmpty(mJsonObject, "SellingUnitName");
                        String price = isNullOrEmpty(mJsonObject, "price");
                        String LogoUrl = isNullOrEmpty(mJsonObject, "LogoUrl");
                        String CatLogoUrl = isNullOrEmpty(mJsonObject, "CatLogoUrl");
                        String VATTax = isNullOrEmpty(mJsonObject, "VATTax");
                        String UnitPrice = isNullOrEmpty(mJsonObject, "UnitPrice");
                        String Number = isNullOrEmpty(mJsonObject, "Number");
                        String PurchaseSku = isNullOrEmpty(mJsonObject, "PurchaseSku");
                        String SellingSku = isNullOrEmpty(mJsonObject, "SellingSku");
                        String PurchasePrice = isNullOrEmpty(mJsonObject, "PurchasePrice");
                        String HindiName = isNullOrEmpty(mJsonObject, "HindiName");
                        String marginPoint = isNullOrEmpty(mJsonObject, "marginPoint");
                        String NetPurchasePrice = isNullOrEmpty(mJsonObject, "NetPurchasePrice");
                        String promoPoint = isNullOrEmpty(mJsonObject, "promoPoint");
                        String Discount = isNullOrEmpty(mJsonObject, "Discount");
                        String MinOrderQty = isNullOrEmpty(mJsonObject, "MinOrderQty");
                        String DpPoint = "";
                        String TotalTaxPercentage = isNullOrEmpty(mJsonObject, "TotalTaxPercentage");
                        String IsOffer = isNullOrEmpty(mJsonObject, "IsOffer");
                        String OfferLogoUrl = isNullOrEmpty(mJsonObject, "OfferLogoUrl");

                        if (promoPoint.trim().equals("null")) {
                            pp = 0;

                        }

                        if (promoPoint.isEmpty()) {
                            pp = 0;

                        } else if (promoPoint.trim().equals("null")) {
                            pp = 0;

                        } else {
                            pp = Integer.parseInt(promoPoint);

                        }

                        if (marginPoint.trim().equals("null")) {

                            mp = 0;
                        }


                        if (marginPoint.isEmpty()) {

                            mp = 0;
                        } else if (marginPoint.trim().equals("null")) {

                            mp = 0;
                        } else {
                            mp = Integer.parseInt(marginPoint);

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

                        mOfferListAll.add(new OfferList(ItemId, Cityid, CityName, Categoryid, SubCategoryId, SubsubCategoryid, SubSubCode, WarehouseId, SupplierId, SUPPLIERCODES, CompanyId, CategoryName, BaseCategoryName, SubcategoryName, SubsubcategoryName, SupplierName, itemname,  SellingUnitName,price,LogoUrl,CatLogoUrl,VATTax,UnitPrice,Number,PurchaseSku,SellingSku,PurchasePrice,HindiName,marginPoint,NetPurchasePrice,promoPoint,Discount,MinOrderQty,DpPoint,TotalTaxPercentage,IsOffer,OfferLogoUrl));
                        Log.d("OFFERAPI+++",String.valueOf(mOfferListAll));
                        if(mOfferList.size()==0)
                        {
                            mOfferList.add(new OfferList(ItemId, Cityid, CityName, Categoryid, SubCategoryId, SubsubCategoryid, SubSubCode, WarehouseId, SupplierId, SUPPLIERCODES, CompanyId, CategoryName, BaseCategoryName, SubcategoryName, SubsubcategoryName, SupplierName, itemname,  SellingUnitName,price,LogoUrl,CatLogoUrl,VATTax,UnitPrice,Number,PurchaseSku,SellingSku,PurchasePrice,HindiName,marginPoint,NetPurchasePrice,promoPoint,Discount,MinOrderQty,DpPoint,TotalTaxPercentage,IsOffer,OfferLogoUrl));
                            Log.d("OFFERAPI++",String.valueOf(mOfferList));
                        }else
                        {
                            boolean ispresent=false;
                            for (int j = 0; j <mOfferList.size() ; j++) {
                                //   Log.d("click1","ItemNumber1122::"+mItemListArrayList.get(j).getItemNumber());
                                if(mOfferList.get(j).getNumber().equalsIgnoreCase(Number))
                                {Log.d("OFFERAPI+",String.valueOf(mOfferList));
                                    ispresent=true;
                                    break;
                                }
                            }
                            if(!ispresent)
                            {
                                // System.out.println("Id:1::"+ItemId);
                                mOfferList.add(new OfferList(ItemId, Cityid, CityName, Categoryid, SubCategoryId, SubsubCategoryid, SubSubCode, WarehouseId, SupplierId, SUPPLIERCODES, CompanyId, CategoryName, BaseCategoryName, SubcategoryName, SubsubcategoryName, SupplierName, itemname,  SellingUnitName,price,LogoUrl,CatLogoUrl,VATTax,UnitPrice,Number,PurchaseSku,SellingSku,PurchasePrice,HindiName,marginPoint,NetPurchasePrice,promoPoint,Discount,MinOrderQty,DpPoint,TotalTaxPercentage,IsOffer,OfferLogoUrl));

                            }else
                            {

                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("mOfferList1212"+mOfferList);
                ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.OFFER_SELLED_ITEM_PREF, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat1.putObject(Constant.OFFER_SELLED_ITEM_PREFOBJ, mOfferList);
                mBaseCatSubCatCat1.commit();


                ComplexPreferences mBaseCatSubCatCat2 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.OFFER_ALL_ITEM_PREF, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat2.putObject(Constant.OFFER_ALL_ITEM_PREFOBJ, mOfferListAll);
                mBaseCatSubCatCat2.commit();

                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, getActivity().MODE_PRIVATE);
                RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);

                mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
                BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat1.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);

                if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {
                    if (mOfferList != null && !mOfferList.isEmpty())
                        try {
                            mofferAdapter = new OfferAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mOfferList == null ? new ArrayList<OfferList>() : mOfferList);
                            offerRecycler.setAdapter(mofferAdapter);
                            mofferAdapter.notifyDataSetChanged();

                        } catch (IndexOutOfBoundsException e) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        } catch (Exception e) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        }
                }
            } else {

                ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.OFFER_SELLED_ITEM_PREF, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat1.putObject(Constant.OFFER_SELLED_ITEM_PREFOBJ, mOfferList);
                mBaseCatSubCatCat1.commit();
                mBaseCatSubCatCat1.clear();
                ComplexPreferences mBaseCatSubCatCat2 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.OFFER_ALL_ITEM_PREF, getActivity().MODE_PRIVATE);
                mBaseCatSubCatCat2.putObject(Constant.OFFER_ALL_ITEM_PREFOBJ, mOfferListAll);
                mBaseCatSubCatCat2.commit();
                mBaseCatSubCatCat2.clear();


            }

            progressBar.setVisibility(View.GONE);
        }

    }
}
