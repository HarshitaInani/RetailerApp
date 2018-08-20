package app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
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
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.PopularBrandBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment.HomeFragItemList;

/**
 * Created by Krishna on 29-12-2016.
 */

public class HomeFragRecyclerViewAdapterPopularBrands extends RecyclerView.Adapter<HomeFragRecyclerViewAdapterPopularBrands.ViewHolder> {
    private ArrayList<PopularBrandBean> mPopularBrandBeenArrayList;
    private Context context;
    int ivHeight;
    int ivWidth;
    FragmentManager fragmentManager;
    RetailerBean mRetailerBean;

    public HomeFragRecyclerViewAdapterPopularBrands(Context context, ArrayList<PopularBrandBean> mPopularBrandBeenArrayList, int ivHeight, int ivWidth, FragmentManager fragmentManager, RetailerBean mRetailerBean) {
        this.mPopularBrandBeenArrayList = mPopularBrandBeenArrayList;
        this.context = context;
        this.ivHeight = ivHeight;
        this.ivWidth = ivWidth;
        this.fragmentManager = fragmentManager;
        this.mRetailerBean = mRetailerBean;
    }

    @Override
    public HomeFragRecyclerViewAdapterPopularBrands.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_frag_recycler_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeFragRecyclerViewAdapterPopularBrands.ViewHolder viewHolder, final int i) {

        viewHolder.tv_android.setText(mPopularBrandBeenArrayList.get(i).getCategoryName());
        if (!TextUtils.isNullOrEmpty(mPopularBrandBeenArrayList.get(i).getLogoUrl()))
            Picasso.with(context).load(mPopularBrandBeenArrayList.get(i).getLogoUrl()).resize(ivWidth, ivHeight).into(viewHolder.img_android);
        else
            Picasso.with(context).load(Constant.BASE_URL_Images + "images/catimages/" + mPopularBrandBeenArrayList.get(i).getCategoryid() + ".png").resize(ivWidth, ivHeight).into(viewHolder.img_android);
        viewHolder.mRowRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = android.support.v4.app.Fragment.instantiate(context, HomeFragItemList.class.getName());
                Bundle args = new Bundle();
                args.putInt("selectedCategoryId", Integer.parseInt(mPopularBrandBeenArrayList.get(i).getCategoryid()));
                args.putInt("selectedWarId", Integer.parseInt(mRetailerBean.getWarehouseid()));
                fragment.setArguments(args);
                fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "HomeFragItemList").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPopularBrandBeenArrayList.size();
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
