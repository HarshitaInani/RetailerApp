package app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.ActivitiesActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.AppBarFlexiImageScrollsActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.SubsubBrands;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.Activitiespojo;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.NewsFeeds;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.indexingscroll.StringMatcher;



public class CustomListAdapter extends ArrayAdapter <NewsFeeds>implements SectionIndexer {

    private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    Context context;
    Typeface face,face2;
    private List<NewsFeeds> feedsList;
    public CustomListAdapter(Context context, int resourceId,
                             List<NewsFeeds> items) {
        super(context, resourceId, items);
        this.context = context;
        this.feedsList = items;
       // face = Typeface.createFromAsset(context.getAssets(), "fonts/LSANSD.TTF");
       // face2= Typeface.createFromAsset(context.getAssets(),"fonts/verdana.ttf");
    }



    public int getPositionForSection(int section) {
        // If there is no item for current section, previous section will be selected
        for (int i = section; i >= 0; i--) {
            for (int j = 0; j < getCount(); j++) {
                if (i == 0) {
                    // For numeric section
                    for (int k = 0; k <= 9; k++) {
                        if (StringMatcher.match(String.valueOf(feedsList.get(j).getFeedName().charAt(0)), String.valueOf(k)))
                            return j;
                    }
                } else {
                    if (StringMatcher.match(String.valueOf(feedsList.get(j).getFeedName().charAt(0)), String.valueOf(mSections.charAt(i))))
                        return j;
                }
            }
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int position)
    {

        return 0;
    }

    @Override
    public Object[] getSections() {
        String[] sections = new String[mSections.length()];
        for (int i = 0; i < mSections.length(); i++)
            sections[i] = String.valueOf(mSections.charAt(i));
        return sections;
    }



    /*private view holder class*/
    private class ViewHolder {

        private ImageView ivItemImage;
        private TextView content, title;
        private NetworkImageView imageview;
        private ProgressBar ratingbar;
        private LinearLayout mRowRl;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

       final NewsFeeds rowItemn = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.singleitem_recyclerview, null);
            holder = new ViewHolder();



            holder.title = (TextView) convertView.findViewById(R.id.title_view);
            holder.mRowRl = (LinearLayout) convertView.findViewById(R.id.home_frag_row);
            holder.ivItemImage = (ImageView) convertView.findViewById(R.id.item_row_item_logo);


            convertView.setTag(holder);
        } else

            holder = (ViewHolder) convertView.getTag();

        holder.title.setText(rowItemn.getFeedName());
        Picasso.with(context).load(rowItemn.getImgURL()).resize(50, 50).into(holder.ivItemImage);

        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.parseColor("#ffffff"));

        } else {
            convertView.setBackgroundColor(Color.parseColor("#fafafa"));
        }
        holder.mRowRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,SubsubBrands.class);
                i.putExtra("Warehouseid",rowItemn.getWarehouseid());
                i.putExtra("SubcategoryName",rowItemn.getSubsubcategoryName());
                i.putExtra("Categoryid",rowItemn.getCategoryid());
                System.out.println("SubBrand::::"+rowItemn.getSubsubcategoryName());
                context.startActivity(i);
            }
        });

        return convertView;







    }

}