package app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.SubSubCategoriesBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.interfaces.ItemListSubSubCatAdapterInterface;

/**
 * Created by Krishna on 03-01-2017.
 */

public class ItemListSubSubCatAdapter extends RecyclerView.Adapter<ItemListSubSubCatAdapter.ViewHolder> {
    private ArrayList<SubSubCategoriesBean> mSubSubCategoriesBeanArrayList;
    private Context context;
    private TextView itemListSelectdSubSubCatTv;
    private TextView lastitemListSelectdSubSubCatTv;
    private ItemListSubSubCatAdapterInterface mItemListSubSubCatAdapterInterface;

    public ItemListSubSubCatAdapter(Context context, ArrayList<SubSubCategoriesBean> mSubSubCategoriesBeanArrayList, TextView itemListSelectdSubSubCatTv, ItemListSubSubCatAdapterInterface mItemListSubSubCatAdapterInterface) {
        this.mSubSubCategoriesBeanArrayList = mSubSubCategoriesBeanArrayList;
        this.context = context;
        this.itemListSelectdSubSubCatTv = itemListSelectdSubSubCatTv;
        this.mItemListSubSubCatAdapterInterface = mItemListSubSubCatAdapterInterface;
    }

    @Override
    public ItemListSubSubCatAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sub_sub_cat_adapter_row, viewGroup, false);
        return new ItemListSubSubCatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemListSubSubCatAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.tv_sub_sub_cat_adpt_tv.setText(mSubSubCategoriesBeanArrayList.get(i).getSubSubcategoryName());
        if (lastitemListSelectdSubSubCatTv == null) {
            viewHolder.tv_sub_sub_cat_adpt_tv.setSelected(true);
            lastitemListSelectdSubSubCatTv = viewHolder.tv_sub_sub_cat_adpt_tv;
            itemListSelectdSubSubCatTv.setText(mSubSubCategoriesBeanArrayList.get(i).getSubSubcategoryName());
            mItemListSubSubCatAdapterInterface.subSubCategorySelected(mSubSubCategoriesBeanArrayList.get(i).getSubSubCategoryId());
        }
        viewHolder.tv_sub_sub_cat_adpt_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastitemListSelectdSubSubCatTv != null)
                    lastitemListSelectdSubSubCatTv.setSelected(false);
                viewHolder.tv_sub_sub_cat_adpt_tv.setSelected(true);
                lastitemListSelectdSubSubCatTv = viewHolder.tv_sub_sub_cat_adpt_tv;
                itemListSelectdSubSubCatTv.setText(mSubSubCategoriesBeanArrayList.get(i).getSubSubcategoryName());
                mItemListSubSubCatAdapterInterface.subSubCategorySelected(mSubSubCategoriesBeanArrayList.get(i).getSubSubCategoryId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSubSubCategoriesBeanArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_sub_sub_cat_adpt_tv;

        public ViewHolder(View view) {
            super(view);
            tv_sub_sub_cat_adpt_tv = (TextView) view.findViewById(R.id.sub_sub_cat_adpt_tv);
        }
    }
}
