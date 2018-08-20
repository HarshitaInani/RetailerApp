package app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.Constant;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;


/**
 * Created by abc on 5/25/2016.
 */
public class ViewPagerItemAdapter extends PagerAdapter {
    Context context;

    ArrayList<String> priceList;

    LayoutInflater inflater;

    private String paymentAmount = "100";
    int[] flag;
    int getP;

    public static final int PAYPAL_REQUEST_CODE = 123;
    ArrayList<String> picList = new ArrayList<>();


    public ViewPagerItemAdapter(Context context, int[] flag, ArrayList<String> picList) {
        this.context = context;
       this.picList=picList;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        return flag.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((CardView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        ImageView imgflag;
        TextView priceTv;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.view_pager_demo, container, false);

        getP = position;
        imgflag = (ImageView) itemView.findViewById(R.id.cat_img);
        imgflag.setImageResource(flag[position]);
     //   Picasso.with(context).load(Constant.BASE_SLIDER_IMAGE + picList.get(position)).placeholder(R.drawable.top_img_bg).error(R.drawable.grocerry).into(imgflag);
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((CardView) object);

    }




}
