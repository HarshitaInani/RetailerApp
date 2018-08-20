package app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters;

/**
 * Created by User on 9/12/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


import java.util.List;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.PromotionPojo;


/**
 * Created by AndroidNovice on 6/5/2016.
 */
public class AppPromotionAdapter extends RecyclerView.Adapter<AppPromotionAdapter.MyViewHolder> {


    int ivHeight;
    int ivWidth;
    private List<PromotionPojo> promotionList;
    private Context context;
    private LayoutInflater inflater;

    public AppPromotionAdapter(Context context, List<PromotionPojo> promotionList, int ivHeight, int ivWidth) {
        this.ivHeight = ivHeight;
        this.ivWidth = ivWidth;
        this.context = context;
        this.promotionList = promotionList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = inflater.inflate(R.layout.adapter_app_promotion, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final PromotionPojo promotionPojo = promotionList.get(position);

        String Imageurl=promotionPojo.getLogoUrl().replaceAll(" ", "%20");
      // System.out.println("ImageUrl:::"+Imageurl);
        holder.title.setText(promotionPojo.getName());
        Picasso.with(context).load(Imageurl).resize(ivHeight, ivWidth).into(holder.ivItemImage);



    holder.mRowRl.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      /*  Intent i=new_added Intent(context,SubsubBrands.class);
        i.putExtra("Warehouseid",feeds.getWarehouseid());
        i.putExtra("SubcategoryName",feeds.getSubsubcategoryName());
        System.out.println("SubBrand::::"+feeds.getSubsubcategoryName());
        context.startActivity(i);*/
    }
});

    }

    @Override
    public int getItemCount() {
        return promotionList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivItemImage;
        private TextView content, title;
        private LinearLayout mRowRl;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_android);
            mRowRl = (LinearLayout) itemView.findViewById(R.id.home_frag_row);
            ivItemImage = (ImageView) itemView.findViewById(R.id.img_android);


        }
    }

}