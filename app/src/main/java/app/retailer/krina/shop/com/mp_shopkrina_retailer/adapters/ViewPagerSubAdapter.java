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

import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;


/**
 * Created by abc on 5/25/2016.
 */
public class ViewPagerSubAdapter extends PagerAdapter {
    Context context;
    int[] flag;
    ArrayList<String> priceList;

    LayoutInflater inflater;

    private String paymentAmount = "100";

    int getP;

    public static final int PAYPAL_REQUEST_CODE = 123;
    ArrayList<String> picList = new ArrayList<>();


    public ViewPagerSubAdapter(Context context, int[] flag, ArrayList<String> picList) {
        this.context = context;
        this.flag = flag;
/*        priceList = price;*/
       this.picList=picList;
    }

    @Override
    public int getCount() {
        return picList.size();
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
        System.out.println("piclistshow::::"+picList.get(position));
     //   priceTv = (TextView)  itemView.findViewById(R.id.price);


        // Capture position and set to the ImageView
       // imgflag.setImageResource(flag[position]);
      //  System.out.println("piclistttt:::"+ picList.get(position));
        ((ViewPager) container).addView(itemView);
        if(picList.size()==0){

        }else if(picList.get(position).equalsIgnoreCase("")){
            System.out.println("condition if");
            Picasso.with(context).load(R.drawable.grocerry2).error(R.drawable.grocerry).into(imgflag);
        }

        else {
            System.out.println("condition else");
            Picasso.with(context).load( picList.get(position)).placeholder(R.drawable.grocerry2).error(R.drawable.grocerry).into(imgflag);
        }



        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((CardView) object);

    }




}
