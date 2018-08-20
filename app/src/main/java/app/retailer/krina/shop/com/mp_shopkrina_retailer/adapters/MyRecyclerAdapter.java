package app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters;

/**
 * Created by User on 9/12/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.Constant;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.NetworkController;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.SubsubBrands;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.NewsFeeds;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;

/**
 * Created by AndroidNovice on 6/5/2016.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    ArrayList<String> SubCategoryId;
    ArrayList<String> SubsubCategoryid;
    ArrayList<String> Categoryid;
    RetailerBean mRetailerBean;



    private List<NewsFeeds> feedsList;
    private Context context;
    private LayoutInflater inflater;

    public MyRecyclerAdapter(Context context, List<NewsFeeds> feedsList) {

        this.context = context;

        this.context = context;
        this.feedsList = feedsList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = inflater.inflate(R.layout.singleitem_recyclerview, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final NewsFeeds feeds = feedsList.get(position);
        //Pass the values of feeds object to Views
        holder.title.setText(feeds.getFeedName());
        System.out.println("URL::"+feeds.getImgURL());
        Picasso.with(context).load(feeds.getImgURL()).resize(50, 50).into(holder.ivItemImage);

       // holder.imageview.setImageUrl(Constant.BASE_URL_Images+"images/itemimages/"+feeds.getImgURL(), NetworkController.getInstance(context).getImageLoader());
         //HomeFragmentSubCategory

        Categoryid=new ArrayList<String>();
        SubCategoryId=new ArrayList<String>();
        SubsubCategoryid=new ArrayList<String>();


    holder.mRowRl.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i=new Intent(context,SubsubBrands.class);
        i.putExtra("Warehouseid",feeds.getWarehouseid());
        i.putExtra("SubcategoryName",feeds.getSubsubcategoryName());
        System.out.println("SubBrand::::"+feeds.getSubsubcategoryName());
        context.startActivity(i);
    }
});

    }

    @Override
    public int getItemCount() {
        return feedsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivItemImage;
        private TextView content, title;
        private NetworkImageView imageview;
        private ProgressBar ratingbar;
        private LinearLayout mRowRl;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_view);
            // Volley's NetworkImageView which will load Image from URL
            //imageview = (NetworkImageView) itemView.findViewById(R.id.thumbnail);
            mRowRl = (LinearLayout) itemView.findViewById(R.id.home_frag_row);
            ivItemImage = (ImageView) itemView.findViewById(R.id.item_row_item_logo);


        }
    }

}