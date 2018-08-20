package app.retailer.krina.shop.com.mp_shopkrina_retailer.bean;

import java.util.ArrayList;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.BaseCatBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.CategoryBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.SubCategoriesBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.homecarebean;

/**
 * Created by Krishna on 30-12-2016.
 */

public class BaseCatSubCatBean {
    ArrayList<BaseCatBean> mBaseCatBeanArrayList;
    ArrayList<CategoryBean> mCategoryBeanArrayList;
    ArrayList<SubCategoriesBean> mSubCategoriesBeanArrayList;
  /*  ArrayList<homecarebean> mhomeBeanArrayList; */

    public ArrayList<BaseCatBean> getmBaseCatBeanArrayList() {
        return mBaseCatBeanArrayList;
    }

    public void setmBaseCatBeanArrayList(ArrayList<BaseCatBean> mBaseCatBeanArrayList) {
        this.mBaseCatBeanArrayList = mBaseCatBeanArrayList;
    }

    public ArrayList<CategoryBean> getmCategoryBeanArrayList() {
        return mCategoryBeanArrayList;
    }

    public void setmCategoryBeanArrayList(ArrayList<CategoryBean> mCategoryBeanArrayList) {
        this.mCategoryBeanArrayList = mCategoryBeanArrayList;
    }

    public ArrayList<SubCategoriesBean> getmSubCategoriesBeanArrayList() {
        return mSubCategoriesBeanArrayList;
    }

    public void setmSubCategoriesBeanArrayList(ArrayList<SubCategoriesBean> mSubCategoriesBeanArrayList) {
        this.mSubCategoriesBeanArrayList = mSubCategoriesBeanArrayList;
    }

   /* public ArrayList<homecarebean> getmhomeBeanArrayList() {
        return mhomeBeanArrayList;
    }

    public void setmhomeBeanArrayList(ArrayList<homecarebean> mhomeBeanArrayList) {
        this.mhomeBeanArrayList = mhomeBeanArrayList;
    }
*/


    public BaseCatSubCatBean(ArrayList<BaseCatBean> mBaseCatBeanArrayList, ArrayList<CategoryBean> mCategoryBeanArrayList, ArrayList<SubCategoriesBean> mSubCategoriesBeanArrayList/*, ArrayList<homecarebean> mhomeBeanArrayList*/) {
        this.mBaseCatBeanArrayList = mBaseCatBeanArrayList;
        this.mCategoryBeanArrayList = mCategoryBeanArrayList;
        this.mSubCategoriesBeanArrayList = mSubCategoriesBeanArrayList;
        /*this.mhomeBeanArrayList = mhomeBeanArrayList;*/
    }



}