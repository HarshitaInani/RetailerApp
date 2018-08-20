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
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.NewlyAddedBrandsBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.PopularBrandBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment.HomeFragItemList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment.HomeFragNewlyAddedBrandList;


/**
 * Created by Krishna on 29-12-2016.
 */

public class NewlyAddedBrandsAdapter extends RecyclerView.Adapter<NewlyAddedBrandsAdapter.ViewHolder> {
    private BaseCatSubCatBean mBaseCatSubCatBean;
    private ArrayList<NewlyAddedBrandsBean> mNewlyAddedBrandsList;
    RetailerBean mRetailerBean;

    private Context context;
    int ivHeight;
    int ivWidth;
    FragmentManager fragmentManager;

    public NewlyAddedBrandsAdapter(Context context, BaseCatSubCatBean mBaseCatSubCatBean, int ivHeight, int ivWidth, FragmentManager fragmentManager, RetailerBean mRetailerBean, ArrayList<NewlyAddedBrandsBean> mNewlyAddedBrandsList) {
        this.mBaseCatSubCatBean = mBaseCatSubCatBean;
        this.context = context;
        this.ivHeight = ivHeight;
        this.ivWidth = ivWidth;
        this.fragmentManager = fragmentManager;
        this.mRetailerBean = mRetailerBean;
        this.mNewlyAddedBrandsList = mNewlyAddedBrandsList;
    }

    @Override
    public NewlyAddedBrandsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_frag_recycler_row3, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewlyAddedBrandsAdapter.ViewHolder viewHolder, final int i) {


            viewHolder.tv_android.setText(mNewlyAddedBrandsList.get(i).getSubcategoryName());
            if (!TextUtils.isNullOrEmpty(mNewlyAddedBrandsList.get(i).getLogoUrl()))
               Picasso.with(context).load(mNewlyAddedBrandsList.get(i ).getLogoUrl()).resize(ivWidth, ivHeight).into(viewHolder.img_android);

            else
                Picasso.with(context).load(Constant.BASE_URL_Images + "images/catimages/" + mNewlyAddedBrandsList.get(i ).getCategoryid() + ".png").resize(ivWidth, ivHeight).into(viewHolder.img_android);
            viewHolder.mRowRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = Fragment.instantiate(context, HomeFragNewlyAddedBrandList.class.getName());
                    Bundle args = new Bundle();
                    //args.putInt("selectedCategoryId", Integer.parseInt(mNewlyAddedBrandsList.get(i).getCategoryid()));
                  //  args.putInt("selectedSubCatId", Integer.parseInt(mNewlyAddedBrandsList.get(i).getSubCategoryId()));
                   // args.putInt("itemId", Integer.parseInt(mNewlyAddedBrandsList.get(i).getSubCategoryId()));
                    args.putString("SubcategoryName",mNewlyAddedBrandsList.get(i).getSubcategoryName());
                    fragment.setArguments(args);
                    fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "HomeFragItemList").commit();
                }
            });


    }

    @Override
    public int getItemCount() {
        return mNewlyAddedBrandsList.size();
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
