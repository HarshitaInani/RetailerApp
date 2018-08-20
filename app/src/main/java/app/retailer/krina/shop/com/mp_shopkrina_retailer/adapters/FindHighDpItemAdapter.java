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
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.FindHighDpPojo;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.SelledItemPojo;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment.HomeFragHighDpItemList;


/**
 * Created by Krishna on 29-12-2016.
 */

public class FindHighDpItemAdapter extends RecyclerView.Adapter<FindHighDpItemAdapter.ViewHolder> {
    private BaseCatSubCatBean mBaseCatSubCatBean;
    private ArrayList<FindHighDpPojo> mFindHighDpArrayList;
    RetailerBean mRetailerBean;

    private Context context;
    int ivHeight;
    int ivWidth;
    FragmentManager fragmentManager;

    public FindHighDpItemAdapter(Context context, BaseCatSubCatBean mBaseCatSubCatBean, int ivHeight, int ivWidth, FragmentManager fragmentManager, RetailerBean mRetailerBean, ArrayList<FindHighDpPojo> mFindHighDpArrayList) {
        this.mBaseCatSubCatBean = mBaseCatSubCatBean;
        this.context = context;
        this.ivHeight = ivHeight;
        this.ivWidth = ivWidth;
        this.fragmentManager = fragmentManager;
        this.mRetailerBean = mRetailerBean;
        this.mFindHighDpArrayList = mFindHighDpArrayList;
    }

    @Override
    public FindHighDpItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_frag_recycler_row3, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FindHighDpItemAdapter.ViewHolder viewHolder, final int i) {



         //   mPopularBrandBeenArrayList


            viewHolder.tv_android.setText(mFindHighDpArrayList.get(i).getItemname());
       // System.out.println("Image::"+Constant.BASE_URL_Images1 + mAllTopAddedItemArrayList.get(i).getItemNumber() + ".jpg");
            if (!TextUtils.isNullOrEmpty(mFindHighDpArrayList.get(i).getLogoUrl())) {
                Picasso.with(context).load(Constant.BASE_URL_Images1 + mFindHighDpArrayList.get(i).getItemNumber() + ".jpg").resize(ivWidth, ivHeight).into(viewHolder.img_android);
                Log.d("Highdpimage",Constant.BASE_URL_Images1 + mFindHighDpArrayList.get(i).getItemNumber() + ".jpg");
            }
            else
                Picasso.with(context).load(Constant.BASE_URL_Images1 + mFindHighDpArrayList.get(i).getItemNumber() + ".jpg").resize(ivWidth, ivHeight).into(viewHolder.img_android);
     viewHolder.mRowRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = Fragment.instantiate(context, HomeFragHighDpItemList.class.getName());
                    Bundle args = new Bundle();
                    args.putInt("selectedCategoryId", Integer.parseInt(mFindHighDpArrayList.get(i).getCategoryid()));
                    args.putInt("selectedSubCatId", Integer.parseInt(mFindHighDpArrayList.get(i).getSubCategoryId()));
                    args.putInt("itemId", Integer.parseInt(mFindHighDpArrayList.get(i).getItemId()));
                    args.putInt("selectedWarId", Integer.parseInt(mRetailerBean.getWarehouseid()));


                    fragment.setArguments(args);
                    fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "HomeFragItemList").commit();
                }
            });


    }

    @Override
    public int getItemCount() {
        return mFindHighDpArrayList.size();
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
