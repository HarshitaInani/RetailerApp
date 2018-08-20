package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.BaseCatSubCatBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.BaseCatBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.CategoryBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.SubCategoriesBean;

/**
 * Created by Krishna on 29-01-2017.
 */

public class PreHomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pre_home_activity);
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(PreHomeActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        new GetCategorySubCategory().execute(mRetailerBean.getWarehouseid());
    }

    public class GetCategorySubCategory extends AsyncTask<String, Void, JSONObject> {
        Dialog mDialog;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(PreHomeActivity.this);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            mDialog.setContentView(R.layout.progress_dialog);
            /*if (isItemListAvail) {
                ((TextView) mDialog.findViewById(R.id.progressText)).setText("Refreshing item list please wait...");

            } else {
                ((TextView) mDialog.findViewById(R.id.progressText)).setText("Loading items please wait...");
                mDialog.setCancelable(false);
                mDialog.show();
            }*/
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObjectFromUrl = null;
            try {
                jsonObjectFromUrl = new HttpUrlConnectionJSONParser().getJsonObjectFromHttpUrlConnection(Constant.BASE_URL_ITEM_MASTER + "?warehouseid=" + params[0], null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObjectFromUrl;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (jsonObject != null) {
                try {
                    if (TextUtils.isNullOrEmpty(jsonObject.getJSONArray("Basecats").toString())) {
                        Toast.makeText(PreHomeActivity.this, "BaseCategories not available on server", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isNullOrEmpty(jsonObject.getJSONArray("Categories").toString())) {
                        Toast.makeText(PreHomeActivity.this, "Categories not available on server", Toast.LENGTH_SHORT).show();
                    } else {
                        ArrayList<BaseCatBean> mBaseCatBeanArrayList = new ArrayList<>();
                        ArrayList<CategoryBean> mCategoryBeanArrayList = new ArrayList<>();
                        ArrayList<SubCategoriesBean> mSubCategoriesBeanArrayList = new ArrayList<>();

                        JSONArray mBaseCategoryJsonArray = jsonObject.getJSONArray("Basecats");
                        for (int i = 0; i < mBaseCategoryJsonArray.length(); i++) {
                            String baseCategoryId = isNullOrEmpty(mBaseCategoryJsonArray.getJSONObject(i), "BaseCategoryId");
                            String warehouseid = isNullOrEmpty(mBaseCategoryJsonArray.getJSONObject(i), "Warehouseid");
                            String baseCategoryName = isNullOrEmpty(mBaseCategoryJsonArray.getJSONObject(i), "BaseCategoryName");
                            String logoUrl = isNullOrEmpty(mBaseCategoryJsonArray.getJSONObject(i), "LogoUrl");
                            mBaseCatBeanArrayList.add(new BaseCatBean(baseCategoryId, warehouseid, baseCategoryName, logoUrl));
                        }
                        JSONArray mCategoriesJsonArray = jsonObject.getJSONArray("Categories");
                        for (int i = 0; i < mCategoriesJsonArray.length(); i++) {
                            String baseCategoryId = isNullOrEmpty(mCategoriesJsonArray.getJSONObject(i), "BaseCategoryId");
                            String categoryid = isNullOrEmpty(mCategoriesJsonArray.getJSONObject(i), "Categoryid");
                            String warehouseid = isNullOrEmpty(mCategoriesJsonArray.getJSONObject(i), "Warehouseid");
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

                        if (mBaseCatSubCatBean != null) {
                            ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(PreHomeActivity.this, Constant.BASECAT_CAT_SUBCAT_PREF, PreHomeActivity.this.MODE_PRIVATE);
                            mBaseCatSubCatCat.putObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, mBaseCatSubCatBean);
                            mBaseCatSubCatCat.commit();

                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(i);
                            PreHomeActivity.this.finish();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(PreHomeActivity.this, "Server not responding properly", Toast.LENGTH_SHORT).show();
            }
            if (mDialog.isShowing()) mDialog.dismiss();
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
}
