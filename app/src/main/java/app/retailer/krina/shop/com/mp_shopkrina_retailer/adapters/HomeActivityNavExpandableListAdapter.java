package app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.Amitlibs.utils.TextUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.Constant;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.BaseCatBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.CategoryBean;


/**
 * Created by Krishna on 05-01-2017.
 */

public class HomeActivityNavExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<BaseCatBean> mListDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<BaseCatBean, ArrayList<CategoryBean>> mListDataChild;
    ExpandableListView expandList;
    ImageView imageView;

    public HomeActivityNavExpandableListAdapter(Context context, List<BaseCatBean> listDataHeader, HashMap<BaseCatBean, ArrayList<CategoryBean>> listChildData, ExpandableListView mView) {
        this.mContext = context;
        this.mListDataHeader = listDataHeader;
        this.mListDataChild = listChildData;
        this.expandList = mView;
    }

    @Override
    public int getGroupCount()
    {
        int i = mListDataHeader.size();
        Log.d("GROUPCOUNT", String.valueOf(i));
        return this.mListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int childCount = 0;
//        if (groupPosition != 2) {
        childCount = this.mListDataChild.get(this.mListDataHeader.get(groupPosition))
                .size();
//        }
        Log.d("childCount", String.valueOf(childCount));
        return childCount;
    }

    @Override
    public Object getGroup(int groupPosition) {
        Log.d("groupPosition", String.valueOf(groupPosition));
        return this.mListDataHeader.get(groupPosition);

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Log.d("CHILD", mListDataChild.get(this.mListDataHeader.get(groupPosition))
                .get(childPosition).toString());
        return this.mListDataChild.get(this.mListDataHeader.get(groupPosition))
                .get(childPosition);

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        BaseCatBean headerTitle = (BaseCatBean) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.home_activity_nav_item_menu, null);
        }
        /*LinearLayout mNavMenuHeader = (LinearLayout) convertView.findViewById(R.id.item_ll);
        mNavMenuHeader.setOnClickListener(new_added View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Menu Action need to implement", Toast.LENGTH_SHORT).show();
            }
        });*/
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.menu);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle.getBaseCategoryName());

        imageView=(ImageView)convertView.findViewById(R.id.img);
        //  imageView.setImageResource(R.drawable.nav_icon);


        if(headerTitle.getBaseCategoryName().equals("Groceries"))
        {
            imageView.setImageResource(R.drawable.groceries);

        }
        else if(headerTitle.getBaseCategoryName().equals("Homecare"))
        {

            imageView.setImageResource(R.drawable.homecare);

        }
        else  if(headerTitle.getBaseCategoryName().equals("Personal care"))
        {

            imageView.setImageResource(R.drawable.personalcare);

        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final CategoryBean childObject = (CategoryBean) getChild(groupPosition, childPosition);

        if (convertView == null)
        {

            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.home_activity_nav_item_sub_menu, null);

        }
        /*LinearLayout mSubMenuRow = (LinearLayout) convertView.findViewById(R.id.child_ll);
        mSubMenuRow.setOnClickListener(new_added View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Sub Menu Action need to implement", Toast.LENGTH_SHORT).show();
            }
        });*/
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.submenu);
        txtListChild.setText(childObject.getCategoryName());
        ImageView mImageView = (ImageView) convertView.findViewById(R.id.home_activity_nav_menu_sub_item_imageView);
        if (!TextUtils.isNullOrEmpty(childObject.getLogoUrl())) {
            Picasso.with(mContext).load(childObject.getLogoUrl()).resize(70, 70).into(mImageView);
        }
        else {

            Picasso.with(mContext).load(Constant.BASE_URL_Images + "images/catimages/" + childObject.getCategoryid() + ".png").resize(50, 50).into(mImageView);

        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
