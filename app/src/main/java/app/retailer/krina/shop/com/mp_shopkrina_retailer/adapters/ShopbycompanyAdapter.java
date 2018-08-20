package app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.shopbycompanybean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment.HomeFragNewlyAddedBrandList;

/**
 * Created by User on 11-08-2018.
 */

public class ShopbycompanyAdapter extends RecyclerView.Adapter<ShopbycompanyAdapter.ViewHolder> {

    private  ArrayList<shopbycompanybean> mshopcompany;
    private Context context;
    int ivHeight;
    int ivWidth;
    FragmentManager fragmentManager;



    public ShopbycompanyAdapter(Context context, int rowIMageHeight, int rowIMageWidth, FragmentManager fragmentManager, ArrayList<shopbycompanybean> mshopcompany) {

        this.context = context;
        this.ivHeight = rowIMageHeight;
        this.ivWidth = rowIMageWidth;
        this.fragmentManager = fragmentManager;
        this.mshopcompany=mshopcompany;



    }

    @Override
    public ShopbycompanyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_frag_recycler_row3, parent, false);
        return new ShopbycompanyAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ShopbycompanyAdapter.ViewHolder holder,  final int i) {
        holder.tv_android.setText(mshopcompany.get(i).getSubcategoryName());
        if (!TextUtils.isNullOrEmpty(mshopcompany.get(i).getLogoUrl())) {
            Picasso.with(context).load(mshopcompany.get(i).getLogoUrl()).resize(ivHeight, ivWidth).into(holder.img_android);
        }
        else {
            Picasso.with(context).load(Constant.BASE_URL_Images + "images/catimages/" + mshopcompany.get(i).getCategoryid() + ".png").resize(ivWidth, ivHeight).into(holder.img_android);
        }

    holder.mRowRl.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Fragment fragment = Fragment.instantiate(context, HomeFragNewlyAddedBrandList.class.getName());
            Bundle args = new Bundle();
            //args.putInt("baseCategoryId", Integer.parseInt(mPopularBrandBeenArrayList.get(i - mBaseCatSubCatBean.getmBaseCatBeanArrayList().size()).getCategoryid()));
            args.putInt("selectedCategoryId", Integer.parseInt(mshopcompany.get(i).getCategoryid()));
            args.putInt("selectedSubCatId", Integer.parseInt(mshopcompany.get(i).getSubCategoryId()));
            /*args.putInt("selectedWarId", Integer.parseInt(mshopcompany.getWarehouseid()));*/
            args.putString("SubcategoryName",mshopcompany.get(i).getSubcategoryName());

            fragment.setArguments(args);
            fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "HomeFragItemList").commit();

        }
    });
    }


    @Override
    public int getItemCount() {
        return 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_android;
        private ImageView img_android;
        private RelativeLayout mRowRl;


        public ViewHolder(View itemView) {
            super(itemView);
            mRowRl = (RelativeLayout) itemView.findViewById(R.id.home_frag_row);
            tv_android = (TextView) itemView.findViewById(R.id.tv_android);
            img_android = (ImageView) itemView.findViewById(R.id.img_android);
        }
    }
}
