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
import android.widget.Toast;

import com.Amitlibs.utils.TextUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.Constant;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.BaseCatSubCatBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.PopularBrandBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment.HomeFragItemList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment.HomeFragmentSubCategory;

/**
 * Created by Krishna on 29-12-2016.
 */

public class HomeFragRecyclerViewAdapter extends RecyclerView.Adapter<HomeFragRecyclerViewAdapter.ViewHolder> {
    private BaseCatSubCatBean mBaseCatSubCatBean;
    private ArrayList<PopularBrandBean> mPopularBrandBeenArrayList;
    RetailerBean mRetailerBean;

    private Context context;
    int ivHeight;
    int ivWidth;
    FragmentManager fragmentManager;

    public HomeFragRecyclerViewAdapter(Context context, BaseCatSubCatBean mBaseCatSubCatBean, int ivHeight, int ivWidth, FragmentManager fragmentManager, RetailerBean mRetailerBean, ArrayList<PopularBrandBean> mPopularBrandBeenArrayList) {
        this.mBaseCatSubCatBean = mBaseCatSubCatBean;
        this.context = context;
        this.ivHeight = ivHeight;
        this.ivWidth = ivWidth;
        this.fragmentManager = fragmentManager;
        this.mRetailerBean = mRetailerBean;
        this.mPopularBrandBeenArrayList = mPopularBrandBeenArrayList;
    }

    @Override
    public HomeFragRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_frag_recycler_row2, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeFragRecyclerViewAdapter.ViewHolder viewHolder, final int i) {

        /*

        if (i < mPopularBrandBeenArrayList.size()) {
            viewHolder.tv_android.setText(mPopularBrandBeenArrayList.get(i).getSubcategoryName());
            if (!TextUtils.isNullOrEmpty(mPopularBrandBeenArrayList.get(i).getLogoUrl()))
                Picasso.with(context).load(mPopularBrandBeenArrayList.get(i).getLogoUrl()).resize(ivWidth, ivHeight).into(viewHolder.img_android);
            else
                Picasso.with(context).load(Constant.BASE_URL_Images + "images/catimages/" + mPopularBrandBeenArrayList.get(i).getCategoryid() + ".png").resize(ivWidth, ivHeight).into(viewHolder.img_android);
            viewHolder.mRowRl.setOnClickListener(new_added View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment fragment = android.support.v4.app.Fragment.instantiate(context, HomeFragItemList.class.getName());
                    Bundle args = new_added Bundle();
                    args.putInt("selectedCategoryId", Integer.parseInt(mPopularBrandBeenArrayList.get(i).getCategoryid()));
                    args.putInt("selectedSubCatId", Integer.parseInt(mPopularBrandBeenArrayList.get(i).getSubCategoryId()));
                    args.putInt("selectedWarId", Integer.parseInt(mRetailerBean.getWarehouseid()));
                    fragment.setArguments(args);
                    fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "HomeFragItemList").commit();
                }
            });
        } else {
            viewHolder.tv_android.setText(mBaseCatSubCatBean.getmBaseCatBeanArrayList().get(i - mPopularBrandBeenArrayList.size()).getBaseCategoryName());
            if (!TextUtils.isNullOrEmpty(mBaseCatSubCatBean.getmBaseCatBeanArrayList().get(i - mPopularBrandBeenArrayList.size()).getBaseCatLogoUrl()))
                Picasso.with(context).load(mBaseCatSubCatBean.getmBaseCatBeanArrayList().get(i - mPopularBrandBeenArrayList.size()).getBaseCatLogoUrl()).resize(ivWidth, ivHeight).into(viewHolder.img_android);
            else
                Picasso.with(context).load(Constant.BASE_URL_Images + "images/basecatimages/" + mBaseCatSubCatBean.getmBaseCatBeanArrayList().get(i - mPopularBrandBeenArrayList.size()).getBaseCategoryId() + ".png").resize(ivWidth, ivHeight).into(viewHolder.img_android);
            viewHolder.mRowRl.setOnClickListener(new_added View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = android.support.v4.app.Fragment.instantiate(context, HomeFragmentSubCategory.class.getName());
                    Bundle args = new_added Bundle();
                    args.putInt("baseCategoryId", Integer.parseInt(mBaseCatSubCatBean.getmBaseCatBeanArrayList().get(i - mPopularBrandBeenArrayList.size()).getBaseCategoryId()));
                    fragment.setArguments(args);
                    fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "HomeFragmentSubCategory").commit();
                }
            });
        }


        */


        if (i < mBaseCatSubCatBean.getmBaseCatBeanArrayList().size()) {
            System.out.println("ivWidth::"+ivWidth);
            System.out.println("ivHeight::"+ivHeight);
            System.out.println("URLBaseCat::"+(mBaseCatSubCatBean.getmBaseCatBeanArrayList().get(i).getBaseCatLogoUrl()));
            System.out.println("URLPNG::"+(Constant.BASE_URL_Images + "images/basecatimages/" + mBaseCatSubCatBean.getmBaseCatBeanArrayList().get(i).getBaseCategoryId()+".png"));
            viewHolder.tv_android.setText(mBaseCatSubCatBean.getmBaseCatBeanArrayList().get(i).getBaseCategoryName());

            if (!TextUtils.isNullOrEmpty(mBaseCatSubCatBean.getmBaseCatBeanArrayList().get(i).getBaseCatLogoUrl()))
                Picasso.with(context).load(mBaseCatSubCatBean.getmBaseCatBeanArrayList().get(i).getBaseCatLogoUrl()).resize(ivWidth, ivHeight).into(viewHolder.img_android);
            else
                Picasso.with(context).load(Constant.BASE_URL_Images + "images/basecatimages/" + mBaseCatSubCatBean.getmBaseCatBeanArrayList().get(i).getBaseCategoryId()+".png").resize(ivWidth, ivHeight).into(viewHolder.img_android);
                 viewHolder.mRowRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = android.support.v4.app.Fragment.instantiate(context, HomeFragmentSubCategory.class.getName());
                    Bundle args = new Bundle();
                    args.putInt("baseCategoryId", Integer.parseInt(mBaseCatSubCatBean.getmBaseCatBeanArrayList().get(i).getBaseCategoryId()));
                /*    Toast.makeText(context, ""+mBaseCatSubCatBean.getmBaseCatBeanArrayList().get(i).getBaseCategoryId()
                            +"\n"+mBaseCatSubCatBean.getmBaseCatBeanArrayList().get(i).getBaseCategoryName()+
                            "\n"+mBaseCatSubCatBean.getmBaseCatBeanArrayList().get(i).getBaseCatLogoUrl(), Toast.LENGTH_SHORT).show();
*/
                  /*  args.putInt("selectedSubCatId", Integer.parseInt(mBaseCatSubCatBean.getmBaseCatBeanArrayList().get(i).getSubCategoryId()));
                    args.putInt("selectedWarId", Integer.parseInt(mRetailerBean.getWarehouseid()));
                 */
                    fragment.setArguments(args);
                    fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "HomeFragmentSubCategory").commit();
                }
            });

        }/*else {

            //   mPopularBrandBeenArrayList

            viewHolder.tv_android.setText(mPopularBrandBeenArrayList.get(i - mBaseCatSubCatBean.getmBaseCatBeanArrayList().size()).getSubcategoryName());
            if (!TextUtils.isNullOrEmpty(mPopularBrandBeenArrayList.get(i - mBaseCatSubCatBean.getmBaseCatBeanArrayList().size()).getLogoUrl()))
                Picasso.with(context).load(mPopularBrandBeenArrayList.get(i - mBaseCatSubCatBean.getmBaseCatBeanArrayList().size()).getLogoUrl()).resize(ivWidth, ivHeight).into(viewHolder.img_android);
            else
                Picasso.with(context).load(Constant.BASE_URL_Images + "images/catimages/" + mPopularBrandBeenArrayList.get(i - mBaseCatSubCatBean.getmBaseCatBeanArrayList().size()).getCategoryid() + ".png").resize(ivWidth, ivHeight).into(viewHolder.img_android);
            viewHolder.mRowRl.setOnClickListener(new_added View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Fragment fragment = android.support.v4.app.Fragment.instantiate(context, HomeFragItemList.class.getName());


                    Bundle args = new_added Bundle();
                    //args.putInt("baseCategoryId", Integer.parseInt(mPopularBrandBeenArrayList.get(i - mBaseCatSubCatBean.getmBaseCatBeanArrayList().size()).getCategoryid()));


                    System.out.println("WID:::"+mRetailerBean.getWarehouseid());
                    if (mRetailerBean.getWarehouseid().equalsIgnoreCase(""))
                    {
                        Toast.makeText(context, "Not in mapped", Toast.LENGTH_SHORT).show();
                    }else {
                        args.putInt("selectedCategoryId", Integer.parseInt(mPopularBrandBeenArrayList.get(i  - mBaseCatSubCatBean.getmBaseCatBeanArrayList().size()).getCategoryid()));
                        args.putInt("selectedSubCatId", Integer.parseInt(mPopularBrandBeenArrayList.get(i - mBaseCatSubCatBean.getmBaseCatBeanArrayList().size()).getSubCategoryId()));
                        args.putInt("selectedWarId", Integer.parseInt(mRetailerBean.getWarehouseid()));

                        fragment.setArguments(args);
                        fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "HomeFragItemList").commit();
                    }
                }
            });

        }*/
    }

    @Override
    public int getItemCount() {
        return (mBaseCatSubCatBean.getmBaseCatBeanArrayList().size() + mPopularBrandBeenArrayList.size());
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
