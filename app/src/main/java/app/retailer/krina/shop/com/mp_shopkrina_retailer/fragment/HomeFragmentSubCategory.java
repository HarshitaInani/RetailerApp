package app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.Amitlibs.utils.ComplexPreferences;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.Constant;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.HomeFragSubCategoryRecyclerViewAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.BaseCatSubCatBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.CategoryBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.databinding.FragmentHomeSubCategoryBinding;

/**
 * Created by Krishna on 29-12-2016.
 */

public class HomeFragmentSubCategory extends Fragment {
    int sliderIMageHeight;
    int sliderIMageWidth;
    int rowIMageHeight;
    int rowIMageWidth;
    RecyclerView mHomeFragRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    int baseCategoryId = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseCategoryId = getArguments().getInt("baseCategoryId");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentHomeSubCategoryBinding fragmentHomeSubCategoryBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home_sub_category, container, false);
        View view = fragmentHomeSubCategoryBinding.getRoot();
        setImagesDynamicSize();
      //  Picasso.with(getActivity()).load("http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/Sliderimages/slider1.jpg").placeholder(R.drawable.top_img_bg).resize(sliderIMageWidth, sliderIMageHeight).error(R.drawable.top_img_bg).into(fragmentHomeSubCategoryBinding.fragHomeTopIv);
        Picasso.with(getActivity()).load(Constant.BASE_URL_Images+"Sliderimages/slider1.jpg").placeholder(R.drawable.top_img_bg).resize(sliderIMageWidth, sliderIMageHeight).error(R.drawable.top_img_bg).into(fragmentHomeSubCategoryBinding.fragHomeTopIv);

        layoutManager = new GridLayoutManager(getActivity(), 3);
        mHomeFragRecyclerView = fragmentHomeSubCategoryBinding.cardRecyclerView;
        mHomeFragRecyclerView.setHasFixedSize(true);
        mHomeFragRecyclerView.setLayoutManager(layoutManager);
        fragmentHomeSubCategoryBinding.mywidget.setSelected(true);
        return view;
    }
  @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, getActivity().MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);

        ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
        BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);
        if (mBaseCatSubCatBean != null && (!mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty() && baseCategoryId != -1)) {
            ArrayList<CategoryBean> mCategoryBeanArrayList = new ArrayList<>();
            boolean isListFound = false;
            for (int i = 0; i < mBaseCatSubCatBean.getmCategoryBeanArrayList().size(); i++) {
                if (mBaseCatSubCatBean.getmCategoryBeanArrayList().get(i).getBaseCategoryId().equalsIgnoreCase("" + baseCategoryId)) {
                    mCategoryBeanArrayList.add(mBaseCatSubCatBean.getmCategoryBeanArrayList().get(i));
                    isListFound = true;
                }
            }
            if (isListFound) {
                HomeFragSubCategoryRecyclerViewAdapter mHomeFragSubCategoryRecyclerViewAdapter = new HomeFragSubCategoryRecyclerViewAdapter(getActivity(), mCategoryBeanArrayList, rowIMageHeight, rowIMageWidth, getFragmentManager());
                mHomeFragRecyclerView.setAdapter(mHomeFragSubCategoryRecyclerViewAdapter);
            } else {
                getActivity().getSupportFragmentManager().popBackStack();
                Toast.makeText(getActivity(), "No Item found under this category", Toast.LENGTH_SHORT).show();
            }

        } else {
            getActivity().getSupportFragmentManager().popBackStack();
            Toast.makeText(getActivity(), "No Item found under this category", Toast.LENGTH_SHORT).show();
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

}
