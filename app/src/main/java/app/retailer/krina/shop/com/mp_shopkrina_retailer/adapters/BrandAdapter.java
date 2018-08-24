package app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters;

import android.content.Context;
import android.content.Intent;
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
import java.util.List;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.Constant;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.SubsubBrands;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.BaseCatSubCatBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.NewsFeeds;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.PopularBrandBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment.HomeFragNewlyAddedBrandList;

/**
 * Created by User on 24-08-2018.
 */

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ViewHolder> {
    private BaseCatSubCatBean mBaseCatSubCatBean;



    private Context context;
    int ivHeight;
    int ivWidth;
    FragmentManager fragmentManager;
    private List<NewsFeeds> feedsList;

    public BrandAdapter(Context context, int ivHeight, int ivWidth, FragmentManager fragmentManager, List<NewsFeeds> feedsList) {

        this.context = context;
        this.ivHeight = ivHeight;
        this.ivWidth = ivWidth;
        this.fragmentManager = fragmentManager;

        this.feedsList = feedsList;
    }

    @Override
    public BrandAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_frag_recycler_row3, viewGroup, false);
        return new BrandAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BrandAdapter.ViewHolder viewHolder, final int i) {


        //   mPopularBrandBeenArrayList

        final String warehouseid=feedsList.get(i).getWarehouseid();
        final String subsbcategory=feedsList.get(i).getSubsubcategoryName();
        final String categoryid=feedsList.get(i).getCategoryid();



        System.out.println("Name::::" + feedsList.get(i).getSubcategoryName());
        viewHolder.tv_android.setText(feedsList.get(i).getSubcategoryName());
        if (!TextUtils.isNullOrEmpty(feedsList.get(i).getImgURL()))
            Picasso.with(context).load(feedsList.get(i).getImgURL()).resize(ivWidth, ivHeight).into(viewHolder.img_android);
            // Picasso.with(context).load(Constant.BASE_URL_Images1+ mPopularBrandBeenArrayList.get(i).getItemNumber() + ".jpg").resize(ivWidth, ivHeight).into(viewHolder.img_android);

        else
            // Picasso.with(context).load(Constant.BASE_URL_Images1 + mPopularBrandBeenArrayList.get(i).getItemNumber() + ".jpg").resize(ivWidth, ivHeight).into(viewHolder.img_android);
            Picasso.with(context).load(Constant.BASE_URL_Images + "images/catimages/" + feedsList.get(i).getCategoryid() + ".png").resize(ivWidth, ivHeight).into(viewHolder.img_android);
        viewHolder.mRowRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i=new Intent(context,SubsubBrands.class);
                i.putExtra("Warehouseid",warehouseid);
                i.putExtra("SubcategoryName",subsbcategory);
                i.putExtra("Categoryid",categoryid);

                context.startActivity(i);


            }
        });


    }

    @Override
    public int getItemCount() {
        return feedsList.size();
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
