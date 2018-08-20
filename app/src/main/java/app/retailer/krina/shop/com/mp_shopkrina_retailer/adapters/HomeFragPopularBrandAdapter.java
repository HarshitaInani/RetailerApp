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
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.BaseCatSubCatBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.PopularBrandBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment.HomeFragItemList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment.HomeFragNewlyAddedBrandList;


/**
 * Created by Krishna on 29-12-2016.
 */

public class HomeFragPopularBrandAdapter extends RecyclerView.Adapter<HomeFragPopularBrandAdapter.ViewHolder> {
    private BaseCatSubCatBean mBaseCatSubCatBean;
    private ArrayList<PopularBrandBean> mPopularBrandBeenArrayList;
    RetailerBean mRetailerBean;

    private Context context;
    int ivHeight;
    int ivWidth;
    FragmentManager fragmentManager;

    public HomeFragPopularBrandAdapter(Context context, BaseCatSubCatBean mBaseCatSubCatBean, int ivHeight, int ivWidth, FragmentManager fragmentManager, RetailerBean mRetailerBean, ArrayList<PopularBrandBean> mPopularBrandBeenArrayList) {
        this.mBaseCatSubCatBean = mBaseCatSubCatBean;
        this.context = context;
        this.ivHeight = ivHeight;
        this.ivWidth = ivWidth;
        this.fragmentManager = fragmentManager;
        this.mRetailerBean = mRetailerBean;
        this.mPopularBrandBeenArrayList = mPopularBrandBeenArrayList;
    }

    @Override
    public HomeFragPopularBrandAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_frag_recycler_row3, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeFragPopularBrandAdapter.ViewHolder viewHolder, final int i) {



         //   mPopularBrandBeenArrayList

        System.out.println("Name::::"+mPopularBrandBeenArrayList.get(i).getSubcategoryName());
            viewHolder.tv_android.setText(mPopularBrandBeenArrayList.get(i).getSubcategoryName());
            if (!TextUtils.isNullOrEmpty(mPopularBrandBeenArrayList.get(i).getLogoUrl()))
               Picasso.with(context).load(mPopularBrandBeenArrayList.get(i ).getLogoUrl()).resize(ivWidth, ivHeight).into(viewHolder.img_android);
               // Picasso.with(context).load(Constant.BASE_URL_Images1+ mPopularBrandBeenArrayList.get(i).getItemNumber() + ".jpg").resize(ivWidth, ivHeight).into(viewHolder.img_android);

            else
               // Picasso.with(context).load(Constant.BASE_URL_Images1 + mPopularBrandBeenArrayList.get(i).getItemNumber() + ".jpg").resize(ivWidth, ivHeight).into(viewHolder.img_android);
                Picasso.with(context).load(Constant.BASE_URL_Images + "images/catimages/" + mPopularBrandBeenArrayList.get(i ).getCategoryid() + ".png").resize(ivWidth, ivHeight).into(viewHolder.img_android);
            viewHolder.mRowRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = Fragment.instantiate(context, HomeFragNewlyAddedBrandList.class.getName());
                    Bundle args = new Bundle();
                    //args.putInt("baseCategoryId", Integer.parseInt(mPopularBrandBeenArrayList.get(i - mBaseCatSubCatBean.getmBaseCatBeanArrayList().size()).getCategoryid()));
                    args.putInt("selectedCategoryId", Integer.parseInt(mPopularBrandBeenArrayList.get(i).getCategoryid()));
                    args.putInt("selectedSubCatId", Integer.parseInt(mPopularBrandBeenArrayList.get(i).getSubCategoryId()));
                    args.putInt("selectedWarId", Integer.parseInt(mRetailerBean.getWarehouseid()));
                    args.putString("SubcategoryName",mPopularBrandBeenArrayList.get(i).getSubcategoryName());

                    fragment.setArguments(args);
                    fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "HomeFragItemList").commit();
                }
            });






    }

    @Override
    public int getItemCount() {
        return mPopularBrandBeenArrayList.size();
    }

  /*  @Override
    public int getItemCount() {
        return (mBaseCatSubCatBean.getmBaseCatBeanArrayList().size() + mPopularBrandBeenArrayList.size());
    }*/

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
