package app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.ItemList;

/**
 * Created by User on 21-12-2017.
 */

public class MyAdapter extends BaseAdapter {

    Context c;
    ArrayList<ItemList> mItemList;

    public MyAdapter(Context context, ArrayList<ItemList> mItemList) {
        super();
        this.c = context;
        this.mItemList = mItemList;

    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ItemList mItemListPojo = mItemList.get(position);
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View row = inflater.inflate(R.layout.spinner_item, parent, false);
        TextView label = (TextView) row.findViewById(R.id.moq);
        TextView price = (TextView) row.findViewById(R.id.price);
        TextView margin = (TextView) row.findViewById(R.id.margin);
        label.setText("MOQ "+mItemListPojo.getMinOrderQty());
        price.setText("RS "+ mItemListPojo.getUnitPrice());
       /* String text = "<font color=#FF4500>&#8377; " +  "  Margins: " + (new DecimalFormat("##.##").format((((Double.parseDouble(ItemList.get(ii).getPrice()) - Double.parseDouble(ItemList.get(ii).getUnitPrice())) / Double.parseDouble(ItemList.get(ii).getPrice())) * 100))) + "%";
        margin.setText(Html.fromHtml(text));*/
        String text1 =/* "<font color=#000> " + */ "  Margins: " + (new DecimalFormat("##.##").format((((Double.parseDouble(mItemListPojo.getPrice()) - Double.parseDouble(mItemListPojo.getUnitPrice())) / Double.parseDouble(mItemListPojo.getPrice())) * 100))) + "%";
        margin.setText(  Html.fromHtml(text1));
       // sub.setText(cur_obj.getSub());
      //  ImageView icon = (ImageView) row.findViewById(R.id.image);
       // icon.setImageResource(cur_obj.getImage_id());

        return row;
    }
}
