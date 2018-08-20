package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.utils.ComplexPreferences;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.SubsubBrandsAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.ViewPagerSubAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.CartItemBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.CartItemInfo;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.ItemList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;

import static com.androidquery.util.AQUtility.getContext;

public class SubsubBrands extends AppCompatActivity {
    int selectedCategoryId = -1;
    int selectedWarId = -1;

    TextView tvTotalItemPrice;
    TextView tvTotalItemQty;
    TextView tvTotalDp;
    int rowitemImageHeight;
    int rowitemImageWidth;
    private static final String TAG = "RecyclerViewExample";
    private ArrayList<ItemList> feedsList;
    private RecyclerView mRecyclerView;
    private SubsubBrandsAdapter adapter;
    private ProgressBar progressBar;
    int mp = 0, pp = 0;
    String locale;


    String hindiLanguage = " हिन्दी (भारत)";

    SharedPreferences sharedpreferences;

    String languageCheck = "e";

    LinearLayoutManager mLinearLayoutManager;

    Dialog mDialog;
    AnimationDrawable animation;
    CartItemBean mCartItem;
    TextView show_popup;
    ImageView image_ads;
    int sliderIMageHeight;
    int sliderIMageWidth;
    ArrayList<ItemList> mItemListAllValue;
    ArrayList<String> picList = new ArrayList<>();
    ViewPager viewPager;
    CirclePageIndicator circlePageIndicator;
    JSONArray jsonArraySlider;
    ViewPagerSubAdapter subadapter;
    int[] flag;
    private static int NUM_PAGES = 0;
    String catid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsub_brands2);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
         /* final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);*/
        setImagesDynamicSize();
        ImageView home=(ImageView)findViewById(R.id.home_icon_iv) ;
        image_ads=(ImageView)findViewById(R.id.image_ads) ;
        Picasso.with(this).load(Constant.BASE_URL_Images+"Advertisment/3.png").placeholder(R.drawable.adevertised).resize(sliderIMageWidth, sliderIMageHeight).error(R.drawable.adevertised).into(image_ads);
        show_popup=(TextView)findViewById(R.id.show_popup) ;

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new_added Intent(SubsubBrands.this,HomeActivity.class));
                finish();
            }
        });
        ((ImageView) toolbar.findViewById(R.id.home_more_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View menuItemView = findViewById(R.id.home_more_iv);
                PopupMenu popup = new PopupMenu(SubsubBrands.this, menuItemView);
                MenuInflater inflate = popup.getMenuInflater();
                inflate.inflate(R.menu.home, popup.getMenu());
                popup.show();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_my_orders) {
                            startActivity(new Intent(SubsubBrands.this, MyOrders.class));
                            return true;
                        } else if (id == R.id.action_my_wallet) {
                            startActivity(new Intent(SubsubBrands.this, MyWallet.class));
                            return true;
                        }   /*else if (id == R.id.action_my_dial) {
                            startActivity(new Intent(SubsubBrands.this, MyDialListActivity.class));
                            return true;
                        }*/

                        else if (id == R.id.action_my_profile) {
                            startActivity(new Intent(SubsubBrands.this, MyProfile.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_contact_us) {
                            startActivity(new Intent(SubsubBrands.this, ActivationActivity.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_cart) {
                            startActivity(new Intent(SubsubBrands.this, CartActivity.class));
                            return true;
                        } else if (id == R.id.action_request_item) {
                            startActivity(new Intent(SubsubBrands.this, RequestBrandActivity.class));
                            return true;
                        }
                        else if (id == R.id.action_feedback) {
                            startActivity(new Intent(SubsubBrands.this, FeedbackActivity.class));
                            return true;
                        }
                        else if (id == R.id.action_milestone) {
                            startActivity(new Intent(SubsubBrands.this, MilestoneActivity.class));
                            return true;
                        }
                        else if (id == R.id.action_my_rewards) {
                            startActivity(new Intent(SubsubBrands.this, RewardItemActivity.class));
                            return true;
                        }
                        else if (id == R.id.action_request_service) {
                            startActivity(new Intent(SubsubBrands.this, RequestServiceActivity.class));
                            return true;
                        }
                        else if (id == R.id.action_my_fav) {
                            startActivity(new Intent(SubsubBrands.this, MyFavourite.class));
                            return true;
                        }
                        else if (id == R.id.action_mynews) {
                            startActivity(new Intent(SubsubBrands.this, MyNews.class));
                            return true;

                        }

                        else if (id == R.id.action_my_setting) {

                            startActivity(new Intent(SubsubBrands.this, SettingActivity.class));
                            return true;

                        }


                              else if (id == R.id.action_logout) {
                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(SubsubBrands.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();

                            ComplexPreferences mRetailerBeanPref2 = ComplexPreferences.getComplexPreferences(SubsubBrands.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);


                            mRetailerBeanPref2.clear();
                            mRetailerBeanPref2.commit();

                            clearCartData();

                            Utility.setStringSharedPreference(SubsubBrands.this, "ItemQJson", "");

                            Utility.setStringSharedPreference(SubsubBrands.this, "CompanyId", "");



//                            Utility.setStringSharedPreference(HomeActivity.this, "ItemFavJson", "");


                            SharedPreferences sharedPreferences = getSharedPreferences("dialcount", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear().commit();

                            SubsubBrands.this.finish();

                            startActivity(new Intent(SubsubBrands.this, LoginActivity_Nav.class));

                            return true;
                        } else
                            return false;
                    }
                });

                MenuPopupHelper menuHelper = new MenuPopupHelper(SubsubBrands.this, (MenuBuilder) popup.getMenu(), menuItemView);
                menuHelper.setForceShowIcon(true);
                menuHelper.show();

            }
        });
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        Intent intent=getIntent();
        String ware=intent.getStringExtra("Warehouseid");
        String bname=intent.getStringExtra("SubcategoryName");
        catid=intent.getStringExtra("Categoryid");
        System.out.println("catid:::"+catid);

/*    TextView tvTotalItemPrice;
    TextView tvTotalItemQty;

    TextView tvTotalDp;*/
        tvTotalItemPrice=(TextView)findViewById(R.id.item_list_total_amount_tv) ;
        tvTotalDp=(TextView)findViewById(R.id.item_list_total_dp_tv) ;
        tvTotalItemQty=(TextView)findViewById(R.id.item_list_total_item_tv) ;
        RelativeLayout rl1=(RelativeLayout)findViewById(R.id.rl1);


        String url = Constant.BASE_URL_ITEM_LIST +"/GetAllItemByBrand?warid=1&brandName="+bname;

        System.out.println("abc:::"+url);
        new DownloadTask().execute(url);

        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(getActivity().getApplicationContext(), "To Cart Activity", Toast.LENGTH_SHORT).show();
           /*     getActivity().startActivity(new_added Intent(getActivity(), CartActivity.class));*/
                Intent send=new Intent(SubsubBrands.this,CartActivity.class);
                startActivity(send);

            }
        });
        viewPager =(ViewPager)findViewById(R.id.pager);
        circlePageIndicator =(CirclePageIndicator)findViewById(R.id.indicator);
        setupViewpager();
        callSliderApi();

        NUM_PAGES =picList.size() + 1;
        System.out.println("pic::::"+picList.size());

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


    }
    public class DownloadTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
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
            progressBar.setVisibility(View.GONE);
            if (result == 1) {
                adapter = new SubsubBrandsAdapter(SubsubBrands.this, feedsList,77, 77, tvTotalItemPrice, tvTotalItemQty, tvTotalDp,show_popup,mItemListAllValue);
                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(SubsubBrands.this, "No Item Found", Toast.LENGTH_SHORT).show();
              //  startActivity(new_added Intent(SubsubBrands.this,Allbrands.class));
                finish();
            }
        }
    }


    private void parseResult(String result) {
        System.out.println("Json:::"+result);
        try {
            JSONObject response = new JSONObject(result);
            JSONArray mItemsJsonArray = response.optJSONArray("ItemMasters");
            feedsList = new ArrayList<>();
            mItemListAllValue = new ArrayList<>();

            if (response.getString("ItemMasters").equals("[]")) {
                Toast.makeText(SubsubBrands.this, "No Item Found", Toast.LENGTH_SHORT).show();
               // startActivity(new_added Intent(SubsubBrands.this,Allbrands.class));
                finish();
            }else
            {
                for (int i = 0; i < mItemsJsonArray.length(); i++) {
                    String ItemId = mItemsJsonArray.getJSONObject(i).getString("ItemId");
                    // String UnitId = mItemsJsonArray.getJSONObject(i).getString("UnitId");
                    String Categoryid = mItemsJsonArray.getJSONObject(i).getString("Categoryid");
                    String SubCategoryId = mItemsJsonArray.getJSONObject(i).getString("SubCategoryId");
                    String SubsubCategoryid = mItemsJsonArray.getJSONObject(i).getString("SubsubCategoryid");
//Item Name

                    String itemname;




                    itemname = mItemsJsonArray.getJSONObject(i).getString("itemname");


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

//                            String PromoPoint = "0";


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

                    if(feedsList.size()==0)
                    {
                        feedsList.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, warehouseId, companyId,itemNumber,Isoffer));

                    }else
                    {
                        boolean ispresent=false;
                        for (int j = 0; j <feedsList.size() ; j++) {

                            if(feedsList.get(j).getItemNumber().equalsIgnoreCase(itemNumber))
                            {
                                ispresent=true;
                                break;
                            }
                        }
                        if(!ispresent)
                        {

                            feedsList.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, warehouseId, companyId,itemNumber,Isoffer));

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

    public CartItemBean getCartItem() {
        if (mCartItem == null) {
            ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(SubsubBrands.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0,0,0,"","");
            }
        }
        return mCartItem;
    }

    public String addItemInCartItemArrayList(String itemId, int qty, double itemUnitPrice, ItemList selectedItem, double deliveryCharges, double totalDp, String wid, String coid, double Price) {



        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(SubsubBrands.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        if (mCartItem == null) {
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0,0,0,"","");
            }
        }
        String status = "Error";
        double tempTotalPrice = 0;
        double TotalPrice = 0;
        double tempTotalQuantity = 0;
        double tempTotalDpPoint = 0;
        double saveAmount = 0;





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




                //mCartItem.getmCartItemInfos().remove(foundItemPosition);


            } else {
                mCartItem.getmCartItemInfos().add(new CartItemInfo(itemId, qty, itemUnitPrice, selectedItem, totalDp,Price,"",""));
                status = "Item Added in Cart";
            }
            for (int i = 0; i < mCartItem.getmCartItemInfos().size(); i++) {
                tempTotalPrice += mCartItem.getmCartItemInfos().get(i).getQty() * mCartItem.getmCartItemInfos().get(i).getItemUnitPrice();

                TotalPrice += mCartItem.getmCartItemInfos().get(i).getQty() * mCartItem.getmCartItemInfos().get(i).getItemPrice();

                tempTotalDpPoint += mCartItem.getmCartItemInfos().get(i).getQty() * mCartItem.getmCartItemInfos().get(i).getItemDpPoint();

                tempTotalQuantity += mCartItem.getmCartItemInfos().get(i).getQty();
            }
            mCartItem.setDeliveryCharges(deliveryCharges);
        } else {
            mCartItem.getmCartItemInfos().add(new CartItemInfo(itemId, qty, itemUnitPrice, selectedItem, totalDp,Price,"",""));
            status = "Item Added in Cart";



            tempTotalPrice += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemUnitPrice();

            TotalPrice += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemPrice();

            tempTotalDpPoint += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemDpPoint();

            tempTotalQuantity += mCartItem.getmCartItemInfos().get(0).getQty();

            mCartItem.setDeliveryCharges(deliveryCharges);
        }
        System.out.println("tempTotalPrice:::"+tempTotalPrice);
        System.out.println("TotalPrice:::"+TotalPrice);
        saveAmount =TotalPrice-tempTotalPrice;
        System.out.println("SaveAmount:::"+saveAmount);

        mCartItem.setTotalItemPrice(TotalPrice);
        mCartItem.setSavingAmount(saveAmount);
        mCartItem.setTotalPrice(tempTotalPrice);
        mCartItem.setTotalQuantity(tempTotalQuantity);
        mCartItem.setTotalDpPoints(tempTotalDpPoint);

        mCartItemArraylistPref.putObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, mCartItem);
        mCartItemArraylistPref.commit();


        //  mCartItemArraylistPref.
        return status;


    }



    public  void removeData() {

        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(SubsubBrands.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);

        // mCartItemArraylistPref.re

    }




    public void removeItemfromCart(String itemId) {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(SubsubBrands.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);


        //  Toast.makeText(HomeActivity.this, "item ID"+itemId, Toast.LENGTH_SHORT).show();


        if (mCartItem == null) {
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0,0,0,"","");
            }
        }
        String status = "Error";
        double tempTotalPrice = 0;
        double tempTotalQuantity = 0;
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

                status = "Item Updated in Cart";
                //   Toast.makeText(HomeActivity.this, "Item found"+itemId+"\n"+mCartItem.getTotalQuantity(), Toast.LENGTH_SHORT).show();
                mCartItem.getmCartItemInfos().remove(foundItemPosition);
                mCartItemArraylistPref.putObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, mCartItem);
                mCartItemArraylistPref.commit();
            }

        }
    }
    private void setImagesDynamicSize() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
       this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
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
    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(SubsubBrands.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }
    @NonNull
    public Resources getLocalizedResources(Context context, Locale desiredLocale) {
        Configuration conf = context.getResources().getConfiguration();
        conf = new Configuration(conf);
        conf.setLocale(desiredLocale);
        Context localizedContext = context.createConfigurationContext(conf);
        localizedContext.getResources().updateConfiguration(conf, null);
        return localizedContext.getResources();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // startActivity(new_added Intent(this,Allbrands.class));
        finish();
    }

    public class ComparatorOfNumericString implements Comparator<ItemList> {
        @SuppressLint("NewApi")
        @Override
        public int compare(ItemList lhs, ItemList rhs) {
            int i1 = Integer.parseInt(lhs.getMinOrderQty());
            int i2 = Integer.parseInt(rhs.getMinOrderQty());
            Log.d("Collection String::", String.valueOf(Integer.compare(i1, i2)));
            return Integer.compare(i1, i2);
        }

    }

    public boolean showPopup(int totalAmount) {

        if(totalAmount >= 3000 && totalAmount <= 4000) {
            return true;
        }else if(totalAmount >= 7000 & totalAmount <= 8000)
        {
            return true;
        }else if(totalAmount >= 11000 & totalAmount <= 12000)
        {
            return true;
        }else if(totalAmount >= 15000 & totalAmount <= 16000)
        {
            return true;
        }else if(totalAmount >= 19000 & totalAmount <= 20000)
        {
            return true;
        }else if(totalAmount >= 23000 & totalAmount <= 24000)
        {
            return true;
        }else if(totalAmount >= 27000 & totalAmount <= 28000)
        {
            return true;
        }else if(totalAmount >= 31000 & totalAmount <= 32000)
        {
            return true;
        }else if(totalAmount >= 35000 & totalAmount <= 36000)
        {
            return true;
        }else if(totalAmount >= 39000 & totalAmount <= 40000)
        {
            return true;
        }else if(totalAmount >= 43000 & totalAmount <= 44000)
        {
            return true;
        }else if(totalAmount >= 47000 & totalAmount <= 48000)
        {
            return true;
        }else{
            return false;
        }

        //return true;
    }
    public void callSliderApi() {
        new AQuery(getContext()).ajax(Constant.BASE_URL_SUB_SLIDER + "GetCategoryImageByCId?CategoryId="+catid, null, JSONArray.class, new AjaxCallback<JSONArray>()
        {
            @Override
            public void callback(String url, JSONArray json, AjaxStatus status) {
                System.out.println("slidercheck::::"+ Constant.BASE_URL_SUB_SLIDER + "GetCategoryImageByCId?CategoryId="+catid);
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
                subadapter = new ViewPagerSubAdapter(SubsubBrands.this, flag,picList);
                viewPager.setAdapter(subadapter);
                final float density = getResources().getDisplayMetrics().density;
                circlePageIndicator.setRadius(3 * density);
                circlePageIndicator.setViewPager(viewPager);
                circlePageIndicator.setFillColor(0x99FF4500);
                System.out.println("Slidesub::"+json.toString());
            }
        });

    }

}
