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
public class ViewPagerAdapter2 extends PagerAdapter {
    Context context;
    int[] flag;
    ArrayList<String> priceList;

    LayoutInflater inflater;

    private String paymentAmount = "100";

    int getP;

    public static final int PAYPAL_REQUEST_CODE = 123;
    ArrayList<String> picList = new ArrayList<>();


    public ViewPagerAdapter2(Context context, int[] flag, ArrayList<String> price, ArrayList<String> picList) {
        this.context = context;
        this.flag = flag;
/*        priceList = price;*/
       this.picList=picList;
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

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        View itemView = inflater.inflate(R.layout.main_card_recycle2, container, false);

        View itemView = inflater.inflate(R.layout.view_pager_demo, container, false);


        getP = position;
//        paymentAmount = priceList.get(position);
        // Locate the ImageView in viewpager_item.xml
        imgflag = (ImageView) itemView.findViewById(R.id.cat_img);
     //   priceTv = (TextView)  itemView.findViewById(R.id.price);


        // Capture position and set to the ImageView
        imgflag.setImageResource(flag[position]);
       // priceTv.setText("Price \n$ "+priceList.get(position));
     System.out.println("picasso::::::::::::::::::::::::::::::::::::::"+ Constant.BASE_URL_SLIDER + picList.get(position));
        try {
            Picasso.with(context).load(Constant.BASE_URL_SLIDER + picList.get(position)).placeholder(R.drawable.top_img_bg).error(R.drawable.grocerry).into(imgflag);

        }catch (Exception e){

        }

        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((CardView) object);

    }




}
