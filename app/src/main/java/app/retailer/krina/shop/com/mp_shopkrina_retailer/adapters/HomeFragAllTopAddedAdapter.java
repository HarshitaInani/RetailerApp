package app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Amitlibs.utils.TextUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.Constant;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.BaseCatSubCatBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.AllTopAddedItemList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.ItemList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment.HomeFragAllTopAddedItemList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment.HomeFragItemPopularItemList;


/**
 * Created by Krishna on 29-12-2016.
 */

public class HomeFragAllTopAddedAdapter extends RecyclerView.Adapter<HomeFragAllTopAddedAdapter.ViewHolder> {
    private BaseCatSubCatBean mBaseCatSubCatBean;
    private ArrayList<AllTopAddedItemList> mAllTopAddedItemArrayList;
    RetailerBean mRetailerBean;
    private Context context;
    int ivHeight;
    int ivWidth;
    FragmentManager fragmentManager;
    public HomeFragAllTopAddedAdapter(Context context, BaseCatSubCatBean mBaseCatSubCatBean, int ivHeight, int ivWidth, FragmentManager fragmentManager, RetailerBean mRetailerBean, ArrayList<AllTopAddedItemList> mAllTopAddedItemArrayList) {
        this.mBaseCatSubCatBean = mBaseCatSubCatBean;
        this.context = context;
        this.ivHeight = ivHeight;
        this.ivWidth = ivWidth;
        this.fragmentManager = fragmentManager;
        this.mRetailerBean = mRetailerBean;
        this.mAllTopAddedItemArrayList = mAllTopAddedItemArrayList;
    }

    @Override
    public HomeFragAllTopAddedAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_frag_recycler_row3, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeFragAllTopAddedAdapter.ViewHolder viewHolder, final int i) {


            viewHolder.tv_android.setText(mAllTopAddedItemArrayList.get(i).getItemname());

            if (!TextUtils.isNullOrEmpty(mAllTopAddedItemArrayList.get(i).getLogoUrl())) {

                Picasso.with(context).load(Constant.BASE_URL_Images1 + mAllTopAddedItemArrayList.get(i).getItemNumber() + ".jpg").resize(ivWidth, ivHeight).into(viewHolder.img_android);
                Log.d("Topadded", Constant.BASE_URL_Images1 + mAllTopAddedItemArrayList.get(i).getItemNumber()+ ".jpg");
            }
            else
                Picasso.with(context).load(Constant.BASE_URL_Images1 + mAllTopAddedItemArrayList.get(i).getItemNumber() + ".jpg").resize(ivWidth, ivHeight).into(viewHolder.img_android);
     viewHolder.mRowRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = Fragment.instantiate(context, HomeFragAllTopAddedItemList.class.getName());
                    Bundle args = new Bundle();
                    args.putInt("selectedCategoryId", Integer.parseInt(mAllTopAddedItemArrayList.get(i).getCategoryid()));
                    args.putInt("selectedSubCatId", Integer.parseInt(mAllTopAddedItemArrayList.get(i).getSubCategoryId()));
                    args.putInt("itemId", Integer.parseInt(mAllTopAddedItemArrayList.get(i).getItemId()));
                    args.putInt("selectedWarId", Integer.parseInt(mRetailerBean.getWarehouseid()));


                    fragment.setArguments(args);
                    fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "HomeFragItemList").commit();
                }
            });


    }

    @Override
    public int getItemCount() {
        return mAllTopAddedItemArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_android;
        private ImageView img_android;
        private RelativeLayout mRowRl;

        public ViewHolder(View view) {
            super(view);
            mRowRl = (RelativeLayout) view.findViewById(R.id.home_frag_row);
            tv_android = (TextView) view.findViewById(R.id.tv_android);
            img_android = (ImageView) view.findViewById(R.id.img_android);
        }
    }

}
