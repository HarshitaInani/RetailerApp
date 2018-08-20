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
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.OfferList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment.OfferFragment;


/**
 * Created by Krishna on 29-12-2016.
 */

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {
    private BaseCatSubCatBean mBaseCatSubCatBean;
    private ArrayList<OfferList> mofferlist;
    RetailerBean mRetailerBean;

    private Context context;
    int ivHeight;
    int ivWidth;
    FragmentManager fragmentManager;

    public OfferAdapter(Context context, BaseCatSubCatBean mBaseCatSubCatBean, int ivWidth, int ivHeight, FragmentManager fragmentManager, RetailerBean mRetailerBean, ArrayList<OfferList> mofferlist) {
        this.mBaseCatSubCatBean = mBaseCatSubCatBean;
        this.context = context;
        this.ivHeight = ivHeight;
        this.ivWidth = ivWidth;
        this.fragmentManager = fragmentManager;
        this.mRetailerBean = mRetailerBean;
        this.mofferlist = mofferlist;
    }

    @Override
    public OfferAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_frag_recycler_row3, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OfferAdapter.ViewHolder viewHolder, final int i) {


        System.out.println("mofferlist2121:::"+mofferlist.get(i).getItemname());
         //   mPopularBrandBeenArrayList


            viewHolder.tv_android.setText(mofferlist.get(i).getItemname());

       // System.out.println("Image::"+Constant.BASE_URL_Images1 + "itemimages/" + mAllTopAddedItemArrayList.get(i).getItemNumber() + ".jpg");
            if (!TextUtils.isNullOrEmpty(mofferlist.get(i).getOfferLogoUrl()))

                Picasso.with(context).load( mofferlist.get(i).getOfferLogoUrl() ).resize(ivWidth, ivHeight).into(viewHolder.img_android);

            else
               // Picasso.with(context).load(Constant.BASE_URL_Images1 + mofferlist.get(i).getNumber() + ".jpg").resize(ivWidth, ivHeight).into(viewHolder.img_android);
                Picasso.with(context).load( mofferlist.get(i).getOfferLogoUrl()).resize(ivWidth, ivHeight).into(viewHolder.img_android);
     viewHolder.mRowRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = Fragment.instantiate(context, OfferFragment.class.getName());
                Bundle args = new Bundle();
                args.putInt("selectedCategoryId", Integer.parseInt(mofferlist.get(i).getCategoryid()));
                args.putInt("selectedSubCatId", Integer.parseInt(mofferlist.get(i).getSubCategoryId()));
                args.putInt("itemId", Integer.parseInt(mofferlist.get(i).getItemId()));
           //     args.putInt("selectedWarId", Integer.parseInt(mRetailerBean.getWarehouseid()));


                fragment.setArguments(args);
                fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "HomeFragItemList").commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mofferlist.size();
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
