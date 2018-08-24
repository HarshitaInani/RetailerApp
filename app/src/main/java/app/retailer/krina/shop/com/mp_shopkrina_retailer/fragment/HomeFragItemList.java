package app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.viewpagerindicator.CirclePageIndicator;

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
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.ItemListAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.ItemListSubCatAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.ItemListSubSubCatAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.ViewPagerSubAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.BaseCatSubCatBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.CartItemBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.CartItemInfo;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.SubSubCatItemListBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.ItemList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.SubCategoriesBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.SubSubCategoriesBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.interfaces.ItemListSubCatAdapterInterface;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.interfaces.ItemListSubSubCatAdapterInterface;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Krishna on 03-01-2017.
 */

public class HomeFragItemList extends Fragment implements ItemListSubSubCatAdapterInterface, ItemListSubCatAdapterInterface {
    int selectedCategoryId = -1;
    int selectedWarId = -1;

    int rowitemImageHeight;
    int rowitemImageWidth;
    int sliderIMageHeight;
    int sliderIMageWidth;
    RecyclerView mSubCatRecyclerView;
    RecyclerView mSubSubCatRecyclerView;
    AsyncTask<String, Void, JSONObject> mGetSubSubCatItemListAsyncTask;
    TextView itemListSelectdSubSubCatTv;
    RecyclerView mItemListRecyclerView;

    AsyncTask<String, Void, ArrayList<ItemList>> mItemListAsyncTask;
    AsyncTask<String, Void, ArrayList<SubSubCategoriesBean>> mSubSubCatFilterTask;
    ArrayList<SubCategoriesBean> mSubCategoriesBeanArrayList;
    TextView tvTotalItemPrice;
    TextView tvTotalItemQty;
    TextView tvTotalDp;
    boolean showDialog = true;
    ArrayList<ItemList> mItemListArrayList = new ArrayList<>();
    ArrayList<ItemList> mItemListAllValue= new ArrayList<>();
    ItemListAdapter mItemListAdapter;
    int mp = 0, pp = 0;
    Dialog mDialog;

    AnimationDrawable animation;
    int xPoints = 2;
    ImageView imageView;
    TextView show_popup;
    ImageView image_ads;
    RelativeLayout rl1;
    ViewPager viewPager;
    CirclePageIndicator circlePageIndicator;
    JSONArray jsonArraySlider;
    ArrayList<String> picList = new ArrayList<>();
    private static int NUM_PAGES = 0;
    int currentPage = 0;
    ViewPagerSubAdapter adapter;
    int[] flag;
    ArrayList<String> price = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedCategoryId = getArguments().getInt("selectedCategoryId");
        selectedWarId = getArguments().getInt("selectedWarId");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //ItemListFragBinding itemListFragBinding = DataBindingUtil.inflate(inflater, R.layout.item_list_frag, container, false);
        // View view = itemListFragBinding.getRoot();

      View view = inflater.inflate(R.layout.item_list_frag, container, false);
        setImagesDynamicSize();

        itemListSelectdSubSubCatTv = (TextView)view.findViewById(R.id.item_list_frag_selected_tv);
        tvTotalItemPrice = (TextView)view.findViewById(R.id.item_list_total_amount_tv);
        tvTotalItemQty = (TextView)view.findViewById(R.id.item_list_total_item_tv);
        tvTotalDp = (TextView)view.findViewById(R.id.item_list_total_dp_tv);
     //   Toolbar myToolbar = (Toolbar) view.findViewById(R.id.toolbar);
      //  CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);


        show_popup=(TextView)view.findViewById(R.id.show_popup);
        imageView=(ImageView)view.findViewById(R.id.header_image);
       /* System.out.println("Image:::"+Constant.BASE_URL_Images+"Advertisment/3.png");
        Picasso.with(getActivity()).load(Constant.BASE_URL_Images+"Advertisment/3.png").placeholder(R.drawable.adevertised).resize(sliderIMageWidth, sliderIMageHeight).error(R.drawable.adevertised).into(imageView);
*/
        //  Picasso.with(getActivity()).load(Constant.BASE_URL_Images+"test/3.png").resize(300, 200).into(imageView);
        mSubCatRecyclerView=(RecyclerView)view.findViewById(R.id.sub_cat_list_frag_subcategory_rv) ;
       // mSubCatRecyclerView = itemListFragBinding.subCatListFragSubcategoryRv;
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mSubCatRecyclerView.setLayoutManager(layoutManager);

        mSubSubCatRecyclerView =(RecyclerView)view.findViewById(R.id.sub_sub_cat_list_frag_subcategory_rv);
      //  mSubSubCatRecyclerView = itemListFragBinding.subSubCatListFragSubcategoryRv;
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mSubSubCatRecyclerView.setLayoutManager(layoutManager1);
        mItemListRecyclerView =(RecyclerView)view.findViewById(R.id.item_list_rv);
        //mItemListRecyclerView = itemListFragBinding.itemListRv;
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mItemListRecyclerView.setLayoutManager(layoutManager2);
        mItemListRecyclerView.setNestedScrollingEnabled(false);
        rl1=(RelativeLayout)view.findViewById(R.id.rl1) ;
        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), CartActivity.class));
            }
        });

        mItemListAdapter = new ItemListAdapter(getContext(), mItemListArrayList, 77, 77, tvTotalItemPrice, tvTotalItemQty , tvTotalDp, show_popup,mItemListAllValue, getFragmentManager());
        mItemListRecyclerView.setAdapter(mItemListAdapter);

        viewPager =(ViewPager)view.findViewById(R.id.pager);
        circlePageIndicator =(CirclePageIndicator)view.findViewById(R.id.indicator);
        callSliderApi();
        setupViewpager();

        NUM_PAGES =picList.size() + 1;
        System.out.println("pic::::"+picList.size());
        /*final Handler handler = new Handler();
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


*/


        return view;
    }
    public void setupViewpager() {

        flag = new int[]{


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


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.CART_ITEM_ARRAYLIST_PREF, getActivity().MODE_PRIVATE);
        CartItemBean mCartItemBean = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
        if (mCartItemBean == null) {
            mCartItemBean = new CartItemBean();
        }
        ArrayList<CartItemInfo> mCartItemInfos = mCartItemBean != null ? mCartItemBean.getmCartItemInfos() : new ArrayList<CartItemInfo>();

        tvTotalItemPrice.setText("Total: " + new DecimalFormat("##.##").format(mCartItemBean.getTotalPrice()));
        tvTotalItemQty.setText("" + (int) (mCartItemBean.getTotalQuantity()));
        tvTotalDp.setText("Dp : " + new DecimalFormat("##.##").format(((HomeActivity) getActivity()).getCartItem().getTotalDpPoints()));



        ComplexPreferences mSubSubCatItem = ComplexPreferences.getComplexPreferences(getActivity(), Constant.SUB_SUB_CAT_ITEM_PREF, getActivity().MODE_PRIVATE);

        SubSubCatItemListBean mSubSubCatItemListBean = mSubSubCatItem.getObject("WarId:-" + selectedWarId + "--CatId:-" + selectedCategoryId, SubSubCatItemListBean.class);
        if (mSubSubCatItemListBean != null) {
            ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
            BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);
            if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {
                mSubCategoriesBeanArrayList = new ArrayList<>();
                ArrayList<SubCategoriesBean> mTempSubCategoriesBeanArrayList = mBaseCatSubCatBean.getmSubCategoriesBeanArrayList();
                for (int i = 0; i < mTempSubCategoriesBeanArrayList.size(); i++) {
                    if (mTempSubCategoriesBeanArrayList.get(i).getCategoryid().equalsIgnoreCase("" + selectedCategoryId)) {
                        mSubCategoriesBeanArrayList.add(mTempSubCategoriesBeanArrayList.get(i));

                        //mSubCategoriesBeanArrayList.clear();
                    }
                }
                System.out.println("mSubCategoriesBeanArrayList:::"+mSubCategoriesBeanArrayList);
                ItemListSubCatAdapter mItemListSubCatAdapter = new ItemListSubCatAdapter(getActivity(), mSubCategoriesBeanArrayList, HomeFragItemList.this);
                mSubCatRecyclerView.setAdapter(mItemListSubCatAdapter);
            } else {
                Toast.makeText(getActivity(), "Items are not available", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
            }
            showDialog = false;
            Log.d(getTag(), "Items Available");
        } else {
            showDialog = true;
            Log.d(getTag(), "Items Not Available");
        }

            if (Utils.isInternetConnected(getActivity())) {
                //    mGetSubSubCatItemListAsyncTask = new_added GetSubSubCatItemListAsyncTask().execute("" + selectedWarId, "" + selectedCategoryId);

                callAqueryForItem(""+selectedCategoryId, ""+selectedWarId);


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
        if (mGetSubSubCatItemListAsyncTask != null && !mGetSubSubCatItemListAsyncTask.isCancelled())
            mGetSubSubCatItemListAsyncTask.cancel(true);
        if (mItemListAsyncTask != null && !mItemListAsyncTask.isCancelled())
            mItemListAsyncTask.cancel(true);
        if (mSubSubCatFilterTask != null && !mSubSubCatFilterTask.isCancelled())
            mSubSubCatFilterTask.cancel(true);
        super.onPause();
    }

    @Override
    public void subcategorySelected(String selecctedSubCatId) {
        mSubSubCatFilterTask = new FilterSubSubCatAsyncTask().execute(selecctedSubCatId);
    }

    @Override
    public void subSubCategorySelected(String selecctedSuSubCatId) {
        mItemListAsyncTask = new FilterItemsAsyncTask().execute(selecctedSuSubCatId);
    }

    public class FilterSubSubCatAsyncTask extends AsyncTask<String, Void, ArrayList<SubSubCategoriesBean>> {
        /*Dialog mDialog;

        @Override
        protected void onPreExecute() {
            mDialog = new_added Dialog(getActivity());
            mDialog.getWindow().setBackgroundDrawable(new_added ColorDrawable(Color.WHITE));
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Filtering Categories list please wait...");
            mDialog.setCancelable(false);
            mDialog.show();
        }*/
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(getActivity());
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Filtering Categories list please wait...");
            ImageView la = (ImageView) mDialog.findViewById(R.id.gridprogressBar);
            la.setBackgroundResource(R.drawable.custom_progress_dialog_animation);
            animation = (AnimationDrawable) la.getBackground();
            animation.start();
            mDialog.setCancelable(false);
            mDialog.show();
        }

        @Override
        protected ArrayList<SubSubCategoriesBean> doInBackground(String... params) {
            ArrayList<SubSubCategoriesBean> mSubSubCatItemListBeanArrayList = new ArrayList<>();
            ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.SUB_SUB_CAT_ITEM_PREF, getActivity().MODE_PRIVATE);
            SubSubCatItemListBean mSubSubCatItemListBean = mBaseCatSubCatCat.getObject("WarId:-" + selectedWarId + "--CatId:-" + selectedCategoryId, SubSubCatItemListBean.class);
            ArrayList<SubSubCategoriesBean> mTempSubSubCatItemListBeanArrayList = mSubSubCatItemListBean.getmSubSubCategoriesBeen();
            System.out.println("mTempSubSubCatItemListBeanArrayList::"+mTempSubSubCatItemListBeanArrayList);
            for (int i = 0; i < mTempSubSubCatItemListBeanArrayList.size(); i++) {
                System.out.println("FirstSubSubcategoryID::"+mTempSubSubCatItemListBeanArrayList.get(i).getSubCategoryId());
               System.out.println("SecondSubSubcategoryID::"+params[0]);
                if (mTempSubSubCatItemListBeanArrayList.get(i).getSubCategoryId().equalsIgnoreCase(params[0])) {
                    mSubSubCatItemListBeanArrayList.add(mTempSubSubCatItemListBeanArrayList.get(i));

                }
            }
              return mSubSubCatItemListBeanArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<SubSubCategoriesBean> mSubSubCatItemListBeanArrayList) {
            if (mSubSubCatItemListBeanArrayList.isEmpty()) {
                Toast.makeText(getActivity(), "No Item Found under this category !" , Toast.LENGTH_SHORT).show();
            //    itemListSubSubCatAdapter.notifyDataSetChanged();

                mItemListRecyclerView.setVisibility(View.GONE);
                mSubSubCatRecyclerView.setVisibility(View.GONE);
          //  itemListSelectdSubSubCatTv.setVisibility(View.GONE);

            } else {
                mItemListRecyclerView.setVisibility(View.VISIBLE);
                mSubSubCatRecyclerView.setVisibility(View.VISIBLE);
                mSubCatRecyclerView.setVisibility(View.VISIBLE);
             // itemListSelectdSubSubCatTv.setVisibility(View.VISIBLE);
                System.out.println("mSubSubCatItemListBeanArrayList::"+mSubSubCatItemListBeanArrayList);
                ItemListSubSubCatAdapter itemListSubSubCatAdapter = new ItemListSubSubCatAdapter(getActivity(), mSubSubCatItemListBeanArrayList,/* itemListSelectdSubSubCatTv,*/ HomeFragItemList.this);
                mSubSubCatRecyclerView.setAdapter(itemListSubSubCatAdapter);


            }
            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
            }
        }
    }
    public class FilterItemsAsyncTask extends AsyncTask<String, Void, ArrayList<ItemList>> {
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(getActivity());
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Loading in please wait...");
            ImageView la = (ImageView) mDialog.findViewById(R.id.gridprogressBar);
            la.setBackgroundResource(R.drawable.custom_progress_dialog_animation);
            animation = (AnimationDrawable) la.getBackground();
            animation.start();
            mDialog.setCancelable(false);
            mDialog.show();
        }

        @Override
        protected ArrayList<ItemList> doInBackground(String... params) {
            mItemListArrayList.clear();
            mItemListAllValue.clear();
            ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.SUB_SUB_CAT_ITEM_PREF, getActivity().MODE_PRIVATE);
            SubSubCatItemListBean mSubSubCatItemListBean = mBaseCatSubCatCat.getObject("WarId:-" + selectedWarId + "--CatId:-" + selectedCategoryId, SubSubCatItemListBean.class);
            ArrayList<ItemList> mTempItemListArrayList = mSubSubCatItemListBean.getmItemLists();
            for (int i = 0; i < mTempItemListArrayList.size(); i++) {

                System.out.println("FirstSubcategoryID::"+mTempItemListArrayList.get(i).getSubsubCategoryid());
                System.out.println("SecondSubcategoryID::"+params[0]);
                if (mTempItemListArrayList.get(i).getSubsubCategoryid().equalsIgnoreCase(params[0])) {
                    mItemListArrayList.add(mTempItemListArrayList.get(i));
                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }
                    publishProgress();
                }
            }

            // New Code
            ArrayList<ItemList> mTempItemListArrayList1 = mSubSubCatItemListBean.getmItemRemoveLists();


            for (int i = 0; i < mTempItemListArrayList1.size(); i++) {

                System.out.println("FirstSubcategoryID1::"+mTempItemListArrayList1.get(i).getSubsubCategoryid());
                System.out.println("SecondSubcategoryID1::"+params[0]);
                if (mTempItemListArrayList1.get(i).getSubsubCategoryid().equalsIgnoreCase(params[0])) {
                    mItemListAllValue.add(mTempItemListArrayList1.get(i));
                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }
                    publishProgress();
                }
            }

            return mItemListArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<ItemList> mItemListArrayList) {
            if (mItemListArrayList.isEmpty()) {
                mItemListAdapter.notifyDataSetChanged();
                mItemListAdapter.notifyItemRangeInserted(0, mItemListArrayList.size());
                Toast.makeText(getActivity(), "No Item Found under this category", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    mItemListAdapter.notifyItemRangeInserted(0,  mItemListArrayList.size());
                mItemListAdapter.notifyDataSetChanged();
// changes in class by setting adapter
                mItemListRecyclerView.setAdapter(mItemListAdapter);
                }

                catch (ArrayIndexOutOfBoundsException e) {
                    getActivity().finish();
                    startActivity(new Intent(getActivity(), HomeFragItemList.class));

                }
                catch (Exception e) {
                    getActivity().finish();
                    startActivity(new Intent(getActivity(), HomeFragItemList.class));
                }
            }
            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
            }
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
           mItemListAdapter.notifyDataSetChanged();
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
            sliderIMageHeight = 219;
            sliderIMageWidth = displaymetrics.widthPixels;

        } else if (displaymetrics.widthPixels >= 720 && displaymetrics.widthPixels < 1080) {
            rowitemImageHeight = 115;
            rowitemImageWidth = 115;
            sliderIMageHeight = 348;
            sliderIMageWidth = displaymetrics.widthPixels;

        } else if (displaymetrics.widthPixels >= 1080 && displaymetrics.widthPixels < 1440) {
            rowitemImageHeight = 173;
            rowitemImageWidth = 173;
            sliderIMageHeight = 492;
            sliderIMageWidth = displaymetrics.widthPixels;

        } else if (displaymetrics.widthPixels >= 1440) {
            rowitemImageHeight = 230;
            rowitemImageWidth = 230;
            sliderIMageHeight = 656;
            sliderIMageWidth = displaymetrics.widthPixels;

        } else {
            rowitemImageHeight = 173;
            rowitemImageWidth = 173;

            sliderIMageHeight = 328;
            sliderIMageWidth = displaymetrics.widthPixels;
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
  public void callAqueryForItem(String cartId, String wardId) {

      showLoading();

      ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
      RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);


      String url = "http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/ssitem?warid=1&catid=17";


      Log.e("Ssitem", Constant.BASE_URL_ITEM_LIST + "?customerId=" + mRetailerBean.getCustomerId() + "&catid=" + cartId);
        new AQuery(getActivity()).ajax(Constant.BASE_URL_ITEM_LIST + "?customerId=" + mRetailerBean.getCustomerId() + "&catid=" + cartId, null, JSONObject.class, new AjaxCallback<JSONObject>(){

            @Override
            public void callback(String url, JSONObject jsonObject, AjaxStatus status) {


                if (jsonObject != null) {


                    Log.e("Json", jsonObject.toString());


                    try {
                        if (TextUtils.isNullOrEmpty(jsonObject.getJSONArray("ItemMasters").toString())) {
                            Toast.makeText(getActivity(), "Items not available on server", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isNullOrEmpty(jsonObject.getJSONArray("SubsubCategories").toString())) {
                            Toast.makeText(getActivity(), "Items Category are not available on server", Toast.LENGTH_SHORT).show();
                        } else {
                            ArrayList<SubSubCategoriesBean> mSubSubCategoriesBeanArrayList = new ArrayList<>();
                            ArrayList<ItemList> mItemListArrayList = new ArrayList<>();
                            ArrayList<ItemList>   mItemListAllValue=new ArrayList<>();
                            JSONArray mSubCategoriesJsonArray = jsonObject.getJSONArray("SubsubCategories");
                            for (int i = 0; i < mSubCategoriesJsonArray.length(); i++) {
                                String subSubCategoryId = isNullOrEmpty(mSubCategoriesJsonArray.getJSONObject(i), "SubsubCategoryid");
                                String subSubcategoryName = isNullOrEmpty(mSubCategoriesJsonArray.getJSONObject(i), "SubsubcategoryName");
                                String categoryid = isNullOrEmpty(mSubCategoriesJsonArray.getJSONObject(i), "Categoryid");
                                String SubCategoryId = isNullOrEmpty(mSubCategoriesJsonArray.getJSONObject(i), "SubCategoryId");
                                mSubSubCategoriesBeanArrayList.add(new SubSubCategoriesBean(subSubCategoryId, subSubcategoryName, categoryid, SubCategoryId));
                            }

                            JSONArray mItemsJsonArray = jsonObject.getJSONArray("ItemMasters");
                            for (int i = 0; i < mItemsJsonArray.length(); i++) {

                                String ItemId = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "ItemId");
                               // String UnitId = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "UnitId");
                                String Categoryid = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "Categoryid");
                                String SubCategoryId = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "SubCategoryId");
                                String SubsubCategoryid = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "SubsubCategoryid");
                                String itemname = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "itemname");
                                String HindiName = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "HindiName");
                              //  String UnitName = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "UnitName");
                                //String PurchaseUnitName = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "PurchaseUnitName");
                                String price = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "price");
                                String SellingUnitName = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "SellingUnitName");
                                String SellingSku = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "SellingSku");
                                String UnitPrice = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "UnitPrice");
                                String VATTax = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "VATTax");
                                String LogoUrl = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "LogoUrl");
                                String MinOrderQty = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "MinOrderQty");
                                String Discount = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "Discount");
                                String TotalTaxPercentage = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "TotalTaxPercentage");
                                String ItemNumber = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "ItemNumber");
                                String Isoffer = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "Isoffer");
                                String DpPoint = "";

                                String PromoPoint = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "promoPerItems");

//                            String PromoPoint = "0";


                                String MarginPoint = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "marginPoint");

                                String warehouseId = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "WarehouseId");
                                String companyId = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "CompanyId");
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

                            mItemListAllValue.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName  , DpPoint, PromoPoint, MarginPoint, warehouseId, companyId,ItemNumber,Isoffer));

                                if(mItemListArrayList.size()==0)
                                {
                                    mItemListArrayList.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname,"UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName  , DpPoint, PromoPoint, MarginPoint, warehouseId, companyId,ItemNumber,Isoffer));

                                }else
                                {
                                    boolean ispresent=false;
                                    for (int j = 0; j <mItemListArrayList.size() ; j++) {
                                        //   Log.d("click1","ItemNumber1122::"+mItemListArrayList.get(j).getItemNumber());
                                        if(mItemListArrayList.get(j).getItemNumber().equalsIgnoreCase(ItemNumber))
                                        {
                                            ispresent=true;
                                            break;
                                        }
                                    }
                                    if(!ispresent)
                                    {
                                        // System.out.println("Id:1::"+ItemId);
                                        mItemListArrayList.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName  , DpPoint, PromoPoint, MarginPoint, warehouseId, companyId,ItemNumber,Isoffer));
                                        //Log.d("click1","list show::"+mItemListArrayList);
                                        // values.add(itemname);
                                    }else
                                    {
                                        //  System.out.println("Id:2::"+ItemId);
                                        //values.add(itemname);
                                        // mItemListAllValue.add(new_added ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint,ItemNumber));
                                        // Log.d("click1","list Remove::"+values);
                                    }
                                }

                            }
                              Collections.sort(mItemListAllValue, new ComparatorOfNumericString());
                            SubSubCatItemListBean mSubSubCatItemListBean = new SubSubCatItemListBean(mSubSubCategoriesBeanArrayList, mItemListArrayList,mItemListAllValue);
                            if (getActivity() != null) {
                                ComplexPreferences mSubSubCatItem = ComplexPreferences.getComplexPreferences(getActivity(), Constant.SUB_SUB_CAT_ITEM_PREF, getActivity().MODE_PRIVATE);
                                mSubSubCatItem.putObject("WarId:-" + selectedWarId + "--CatId:-" + selectedCategoryId, mSubSubCatItemListBean);
                                mSubSubCatItem.commit();

                                ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
                                BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);
                                if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {
                                    ArrayList<SubCategoriesBean> mSubCategoriesBeanArrayList = new ArrayList<>();
                                    ArrayList<SubCategoriesBean> mTempSubCategoriesBeanArrayList = mBaseCatSubCatBean.getmSubCategoriesBeanArrayList();
                                    for (int i = 0; i < mTempSubCategoriesBeanArrayList.size(); i++) {
                                        if (mTempSubCategoriesBeanArrayList.get(i).getCategoryid().equalsIgnoreCase("" + selectedCategoryId)) {
                                            mSubCategoriesBeanArrayList.add(mTempSubCategoriesBeanArrayList.get(i));
                                        }
                                    }
                                    ItemListSubCatAdapter mItemListSubCatAdapter = new ItemListSubCatAdapter(getActivity(), mSubCategoriesBeanArrayList, HomeFragItemList.this);
                                    mSubCatRecyclerView.setAdapter(mItemListSubCatAdapter);

                                    if (mDialog.isShowing()) {
                                        animation.stop();
                                        mDialog.dismiss();
                                    }

                                } else {
                                    Toast.makeText(getActivity(), "Items are not available", Toast.LENGTH_SHORT).show();
                                    getFragmentManager().popBackStack();

                                    if (mDialog.isShowing()) {
                                        animation.stop();
                                        mDialog.dismiss();
                                    }
                                }
                            }
                        }
                    } catch (JSONException e) {

                        if (mDialog.isShowing()) {
                            animation.stop();
                            mDialog.dismiss();
                        }
                        e.printStackTrace();
                    }
                } else {
                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }

                    mItemListRecyclerView.setVisibility(View.GONE);
                    mSubSubCatRecyclerView.setVisibility(View.GONE);
                 //   itemListSelectdSubSubCatTv.setVisibility(View.GONE);
                    mSubCatRecyclerView.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Category not found...", Toast.LENGTH_SHORT).show();
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
            return Integer.compare(i1, i2);
        }
   }
    public void callSliderApi() {
        new AQuery(getContext()).ajax(Constant.BASE_URL_SUB_SLIDER + "GetCategoryImageByCId?CategoryId="+selectedCategoryId, null, JSONArray.class, new AjaxCallback<JSONArray>()
        {
            @Override
            public void callback(String url, JSONArray json, AjaxStatus status) {
                System.out.println("slidercheck::::"+ Constant.BASE_URL_SUB_SLIDER + "GetCategoryImageByCId?CategoryId="+selectedCategoryId);
                if (json == null) {
                    Toast.makeText(getContext(), "Slider : Please try again", Toast.LENGTH_SHORT).show();
                }else if(json.toString().equalsIgnoreCase("[]")){
                  picList.add(Constant.BASE_URL_Images+"Advertisment/3.png");
                }
                else {

                    try {
                        jsonArraySlider = new JSONArray(json.toString());
                        System.out.println("jsonObjectslider::::::::::"+jsonArraySlider);
                        for (int i = 0; i  < jsonArraySlider.length() ; i++) {
                            JSONObject jsonObject = jsonArraySlider.getJSONObject(i);

                            String temp = jsonObject.getString("CategoryImg");
                            temp = temp.replaceAll(" ", "%20");
                            picList.add(temp);
                            System.out.println("picListsub:::::::::::::::::::::::::::::::::::::"+picList.size());
                        }

                    }
              catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                adapter = new ViewPagerSubAdapter(getActivity(), flag,picList);
                viewPager.setAdapter(adapter);
                final float density = getResources().getDisplayMetrics().density;
                circlePageIndicator.setRadius(3 * density);
                circlePageIndicator.setViewPager(viewPager);
                circlePageIndicator.setFillColor(0x99FF4500);
                System.out.println("Slidesub::"+json.toString());
            }
        });

    }

}