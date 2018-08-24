package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.Amitlibs.utils.ComplexPreferences;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.HomeActivityNavExpandableListAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.CartItemBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.CartItemInfo;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.BaseCatBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.CategoryBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.ItemList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment.HomeFragItemList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment.HomeFragment;
//import app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment.NotificationFrag;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment.SearchFragItemList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FrameLayout mFrameLayout;
    Fragment fragment;
    android.support.v4.app.FragmentManager fragmentManager;

    NavigationView navigationView;
    ExpandableListView mExpandableListView;
    TextView tvshop;
    CartItemBean mCartItem;

    public static boolean checkIntent = false;
    public boolean shouldCallCatMenuApi = true;
    ImageView toolBarSearchIv;
    RelativeLayout notificationIcon;
    int lastExpandedPosition = -1;

    public ArrayList<BaseCatBean> listDataHeaderGlobal;
    public HashMap<BaseCatBean, ArrayList<CategoryBean>> listDataChildGlobal;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //initialization of toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//open Side hamburger
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ((ImageView) toolbar.findViewById(R.id.nav_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });

// intialization
        ImageView allbrands=(ImageView)toolbar.findViewById(R.id.Allbrands);
        ImageView barCode=(ImageView)toolbar.findViewById(R.id.barcode_scan);
        allbrands.setColorFilter(HomeActivity.this.getResources().getColor(R.color.mycolor));
        barCode.setColorFilter(HomeActivity.this.getResources().getColor(R.color.mycolor));
        allbrands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(HomeActivity.this,Allbrands.class);
                startActivity(i);
            }
        });
        // click on bar code scanner
        barCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(HomeActivity.this,BarcodeScanItem.class);
                startActivity(i);
            }
        });

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        // click on home icon
        ((ImageView) toolbar.findViewById(R.id.home_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // popVisibleFragment();
                fragment = Fragment.instantiate(HomeActivity.this, HomeFragment.class.getName());
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "HomeFragment").commit();
                toolBarSearchIv.setVisibility(View.VISIBLE);
                notificationIcon.setVisibility(View.VISIBLE);
            }

    });
        toolBarSearchIv = ((ImageView) toolbar.findViewById(R.id.nav_search_iv));
// click on search icon
        ((ImageView) toolbar.findViewById(R.id.nav_search_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // popVisibleFragment();
                fragment = Fragment.instantiate(HomeActivity.this, SearchFragItemList.class.getName());
                fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "SearchFragItemList").commit();




            }
        });
        // click on notification Icon
        notificationIcon = ((RelativeLayout) toolbar.findViewById(R.id.nav_notification_iv));
        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // popVisibleFragment();
               /*fragment = Fragment.instantiate(HomeActivity.this, NotificationFrag.class.getName());
                fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment,"NotificationFrag").commit();
                ((RelativeLayout) toolbar.findViewById(R.id.nav_notification_iv)).setVisibility(View.GONE);*/
               Intent intent =new Intent(HomeActivity.this,NotiFicationActivity.class);
               startActivity(intent);
            }
        });
// click on Menu icon
        ((ImageView) toolbar.findViewById(R.id.home_more_iv)).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                View menuItemView = findViewById(R.id.home_more_iv);
                PopupMenu popup = new PopupMenu(HomeActivity.this, menuItemView);


                MenuInflater inflate = popup.getMenuInflater();
                inflate.inflate(R.menu.home, popup.getMenu());
                popup.show();


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_my_orders) {
                            startActivity(new Intent(HomeActivity.this, MyOrders.class));
                            return true;
                        } else if (id == R.id.action_my_wallet) {
                            startActivity(new Intent(HomeActivity.this, MyWallet.class));
                            return true;
                        }
                        else if (id == R.id.action_my_gullak) {
                            startActivity(new Intent(HomeActivity.this, MyGullakActivity.class));
                            return true;
                        }
                        /*else if (id == R.id.action_my_dial) {
                           startActivity(new Intent(HomeActivity.this, MyDialListActivity.class));
                            return true;
                        }*/

                        else if (id == R.id.action_my_profile) {
                            startActivity(new Intent(HomeActivity.this, MyProfile.class).putExtra("showButton", false));
                            return true;
                        }
                     /* else if (id == R.id.action_activities) {
                            startActivity(new Intent(HomeActivity.this, ActivitiesActivity.class).putExtra("showButton", false));
                            return true;
                        }
*/

                     /*   else if (id == R.id.action_my_dial) {
                            startActivity(new_added Intent(HomeActivity.this, MyDial.class).putExtra("showButton", false));
                            return true;
                        }*/
                        else if (id == R.id.action_contact_us) {
                            startActivity(new Intent(HomeActivity.this, ActivationActivity.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_cart) {
                            startActivity(new Intent(HomeActivity.this, CartActivity.class));
                            return true;
                        } else if (id == R.id.action_request_item) {
                            startActivity(new Intent(HomeActivity.this, RequestBrandActivity.class));
                            return true;
                        }

                        else if (id == R.id.action_feedback) {
                            startActivity(new Intent(HomeActivity.this, FeedbackActivity.class));
                            return true;


                        }
                        else if (id == R.id.action_milestone) {
                            startActivity(new Intent(HomeActivity.this, MilestoneActivity.class));
                            return true;


                        }



                        else if (id == R.id.action_my_rewards) {
                            startActivity(new Intent(HomeActivity.this, RewardItemActivity.class));
                            return true;


                        }



                        else if (id == R.id.action_request_service) {
                            startActivity(new Intent(HomeActivity.this, RequestServiceActivity.class));
                            return true;


                        }



                        else if (id == R.id.action_my_fav) {

                            startActivity(new Intent(HomeActivity.this, MyFavourite.class));

                            return true;


                        }



                        else if (id == R.id.action_mynews) {

                            startActivity(new Intent(HomeActivity.this, MyNews.class));

                            return true;


                        }


                        else if (id == R.id.action_my_setting) {

                            startActivity(new Intent(HomeActivity.this, SettingActivity.class));

                            return true;


                        }


                        else if (id == R.id.action_logout) {


                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();
                            ComplexPreferences mRetailerBeanPref2 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);
                            mRetailerBeanPref2.clear();
                            mRetailerBeanPref2.commit();
                            ComplexPreferences mRetailerBeanPref3 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.POPULAR_BRANDS_PREF, MODE_PRIVATE);
                            mRetailerBeanPref3.clear();
                            mRetailerBeanPref3.commit();
                            ComplexPreferences mRetailerBeanPref4 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.SUB_SUB_CAT_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref4.clear();
                            mRetailerBeanPref4.commit();
                            ComplexPreferences mRetailerBeanPref5 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.POPULAR_BRANDS_PREF1, MODE_PRIVATE);
                            mRetailerBeanPref5.clear();
                            mRetailerBeanPref5.commit();
                            ComplexPreferences mRetailerBeanPref6 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.POPULAR_BRANDS_PREF2, MODE_PRIVATE);
                            mRetailerBeanPref6.clear();
                            mRetailerBeanPref6.commit();
                            ComplexPreferences mRetailerBeanPref7 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.APP_PROMOTION_PREF, MODE_PRIVATE);
                            mRetailerBeanPref7.clear();
                            mRetailerBeanPref7.commit();
                            ComplexPreferences mRetailerBeanPref8 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.NEWLY_ADDED_BRANDS_PREF, MODE_PRIVATE);
                            mRetailerBeanPref8.clear();
                            mRetailerBeanPref8.commit();
                            ComplexPreferences mRetailerBeanPref9 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.ALL_TOP_ADDED_PREF, MODE_PRIVATE);
                            mRetailerBeanPref9.clear();
                            mRetailerBeanPref9.commit();
                            ComplexPreferences mRetailerBeanPref10 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.TODAY_DHAMAKA_PREF, MODE_PRIVATE);
                            mRetailerBeanPref10.clear();
                            mRetailerBeanPref10.commit();
                            ComplexPreferences mRetailerBeanPref11 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.EMPTY_STOCK_PREF, MODE_PRIVATE);
                            mRetailerBeanPref11.clear();
                            mRetailerBeanPref11.commit();
                            ComplexPreferences mRetailerBeanPref12 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.BULK_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref12.clear();
                            mRetailerBeanPref12.commit();
                            ComplexPreferences mRetailerBeanPref13 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.MOST_SELLED_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref13.clear();
                            mRetailerBeanPref13.commit();

                            clearCartData();

                            Utility.setStringSharedPreference(HomeActivity.this, "ItemQJson", "");

                            Utility.setStringSharedPreference(HomeActivity.this, "CompanyId", "");


//                            Utility.setStringSharedPreference(HomeActivity.this, "ItemFavJson", "");

                            SharedPreferences sharedPreferences = getSharedPreferences("dialcount", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear().commit();

                            HomeActivity.this.finish();

                            startActivity(new Intent(HomeActivity.this, LoginActivity_Nav.class));

                            return true;
                        } else
                            return false;
                    }
                });




                MenuPopupHelper menuHelper = new MenuPopupHelper(HomeActivity.this, (MenuBuilder) popup.getMenu(), menuItemView);
                menuHelper.setForceShowIcon(true);
                menuHelper.show();




            }
        });
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);

//initialization
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        mExpandableListView = (ExpandableListView) findViewById(R.id.navigationmenu);
        tvshop=(TextView)header.findViewById(R.id.Shopname) ;
       tvshop.setText( mRetailerBean.getShopName());
        System.out.println ("Shopname"+mRetailerBean.getShopName());
        mFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        fragmentManager = HomeActivity.this.getSupportFragmentManager();
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
//Navigation view
    public void setupNavigationView(ArrayList<BaseCatBean> listDataHeader, final HashMap<BaseCatBean, ArrayList<CategoryBean>> listDataChild) {
        if (navigationView != null) {
            HomeActivityNavExpandableListAdapter mMenuAdapter = new HomeActivityNavExpandableListAdapter(this, listDataHeader, listDataChild, mExpandableListView);
            // setting list adapter
            mExpandableListView.setAdapter(mMenuAdapter);
            mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    if (lastExpandedPosition != -1

                            && groupPosition != lastExpandedPosition) {

                        mExpandableListView.collapseGroup(lastExpandedPosition);

                    }
                    lastExpandedPosition = groupPosition;
                }
            });

            mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
//                    Toast.makeText(HomeActivity.this, "Child Click listner clicked", Toast.LENGTH_SHORT).show();
                    if (listDataHeaderGlobal != null && listDataChildGlobal != null) {
                        BaseCatBean mSelectedBaseCatBean = listDataHeaderGlobal.get(i);
                        ArrayList<CategoryBean> mSelectedCategoryBeanArrayList = listDataChildGlobal.get(mSelectedBaseCatBean);
                        CategoryBean mSelectCategoryBean = mSelectedCategoryBeanArrayList.get(i1);
                        Fragment fragment = Fragment.instantiate(HomeActivity.this, HomeFragItemList.class.getName());
                        Bundle args = new Bundle();
                        args.putInt("selectedCategoryId", Integer.parseInt(mSelectCategoryBean.getCategoryid()));

//                        args.putInt("selectedWarId", Integer.parseInt(mSelectCategoryBean.getWarehouseid()));

                        args.putInt("selectedWarId", 1);

                        fragment.setArguments(args);

                        Fragment myFragment = getVisibleFragment();
                        if (myFragment.getTag().equalsIgnoreCase("HomeFragment")) {

                            fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "HomeFragItemList").commit();
                        }
                        else
                            {
                            fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "HomeFragItemList").commit();
                        }
                    }
                    else {
                        Toast.makeText(HomeActivity.this, "Unable to process please try again", Toast.LENGTH_SHORT).show();
                    }
                    if (drawer != null)
                        drawer.closeDrawer(Gravity.LEFT);
                    return true;
                }
            });

            mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                    //Toast.makeText(HomeActivity.this, "Group Click listner clicked", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }
    }
//on satrt Activity
    @Override
    protected void onStart() {
        super.onStart();
        fragment = Fragment.instantiate(this, HomeFragment.class.getName());
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "HomeFragment").commit();

    }
// on Backpressed Activity
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment myFragment = getVisibleFragment();
            if (myFragment != null && myFragment.getTag().equalsIgnoreCase("HomeFragment")) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Closing Activity")
                        .setMessage("Closing Application will clear the cart items...Click yes to continue")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                Utility.setStringSharedPreference(HomeActivity.this, "ItemQJson", "");

                                Utility.setStringSharedPreference(HomeActivity.this, "CompanyId", "");


                                finish();
                                finishAffinity();




                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            } /*else if(myFragment != null && myFragment.getTag().equalsIgnoreCase("HomeFragItemList")){}*/ else {

                notificationIcon.setVisibility(View.VISIBLE);
                toolBarSearchIv.setVisibility(View.VISIBLE);
                super.onBackPressed();

            }

        }
    }
    //on Visibility
    public void shoeToolbarIcon(){
        notificationIcon.setVisibility(View.VISIBLE);
        toolBarSearchIv.setVisibility(View.VISIBLE);
    }

    public Fragment getVisibleFragment() {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    public void popVisibleFragment() {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragmentManager.popBackStack();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//save Cart item data in Complex preference
    public CartItemBean getCartItem() {
        if (mCartItem == null) {
            ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0,0,0, "", "");
            }
        }
        return mCartItem;
    }

    public String addItemInCartItemArrayList(String itemId, int qty, double itemUnitPrice, ItemList selectedItem, double deliveryCharges , double totalDp, String warehouseId, String companyId,double Price) {

        System.out.println("itemId::"+itemId);


        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        if (mCartItem == null) {
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0,0,0, "", "");
            }
        }
        String status = "Error";
        double tempTotalPrice = 0;
        double tempTotalQuantity = 0;
        double TotalPrice = 0;
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
            } else {


                mCartItem.getmCartItemInfos().add(new CartItemInfo(itemId, qty, itemUnitPrice, selectedItem, totalDp,Price, warehouseId, companyId));


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
            mCartItem.getmCartItemInfos().add(new CartItemInfo(itemId, qty, itemUnitPrice, selectedItem , totalDp,Price, warehouseId, companyId));
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

        mCartItem.setTotalPrice(tempTotalPrice);
        mCartItem.setSavingAmount(saveAmount);
        mCartItem.setTotalItemPrice(TotalPrice);
        mCartItem.setTotalQuantity(tempTotalQuantity);

        mCartItem.setTotalDpPoints(tempTotalDpPoint);


        mCartItemArraylistPref.putObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, mCartItem);
        mCartItemArraylistPref.commit();
        return status;
    }

    public  void removeData() {

        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);

        // mCartItemArraylistPref.re


    }


    public void removeItemfromCart(String itemId) {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);


        //  Toast.makeText(HomeActivity.this, "item ID"+itemId, Toast.LENGTH_SHORT).show();


        if (mCartItem == null) {
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0,0,0, "", "");
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
                //    mCartItem.getmCartItemInfos().get(foundItemPosition).setQty(qty);
                //  mCartItem.getmCartItemInfos().get(foundItemPosition).setItemUnitPrice(itemUnitPrice);
                status = "Item Updated in Cart";
                //   Toast.makeText(HomeActivity.this, "Item found"+itemId+"\n"+mCartItem.getTotalQuantity(), Toast.LENGTH_SHORT).show();
                mCartItem.getmCartItemInfos().remove(foundItemPosition);
                mCartItemArraylistPref.putObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, mCartItem);
                mCartItemArraylistPref.commit();




            }



/*
            else {
                mCartItem.getmCartItemInfos().add(new_added CartItemInfo(itemId, qty, itemUnitPrice, selectedItem, totalDp));
                status = "Item Added in Cart";
            }
            for (int i = 0; i < mCartItem.getmCartItemInfos().size(); i++) {
                tempTotalPrice += mCartItem.getmCartItemInfos().get(i).getQty() * mCartItem.getmCartItemInfos().get(i).getItemUnitPrice();

                tempTotalDpPoint += mCartItem.getmCartItemInfos().get(i).getQty() * mCartItem.getmCartItemInfos().get(i).getItemDpPoint();

                tempTotalQuantity += mCartItem.getmCartItemInfos().get(i).getQty();
            }
            mCartItem.setDeliveryCharges(deliveryCharges);
        } else {
            mCartItem.getmCartItemInfos().add(new_added CartItemInfo(itemId, qty, itemUnitPrice, selectedItem, totalDp));
            status = "Item Added in Cart";

            tempTotalPrice += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemUnitPrice();

            tempTotalDpPoint += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemDpPoint();



            tempTotalQuantity += mCartItem.getmCartItemInfos().get(0).getQty();

            mCartItem.setDeliveryCharges(deliveryCharges);
        }


        mCartItem.setTotalPrice(tempTotalPrice);
        mCartItem.setTotalQuantity(tempTotalQuantity);

        mCartItem.setTotalDpPoints(tempTotalDpPoint);

        mCartItemArraylistPref.putObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, mCartItem);
        mCartItemArraylistPref.commit();
*/
            //  mCartItemArraylistPref.


        }
    }

    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
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


}
