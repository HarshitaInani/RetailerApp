package app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.MyOrderDetailsBean;

/**
 * Created by Krishna on 29-12-2016.
 */

public class MyOrderDetailRecyclerViewAdapter extends RecyclerView.Adapter<MyOrderDetailRecyclerViewAdapter.ViewHolder> {
    private ArrayList<MyOrderDetailsBean> myOderDetailsBeanArrayList;
    private Context context;

    public MyOrderDetailRecyclerViewAdapter(Context context, ArrayList<MyOrderDetailsBean> myOderDetailsBeanArrayList) {
        this.myOderDetailsBeanArrayList = myOderDetailsBeanArrayList;
        this.context = context;
    }

    @Override
    public MyOrderDetailRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_orders_detail_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyOrderDetailRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        try {

            viewHolder.tvItemName.setText(myOderDetailsBeanArrayList.get(i).getItemname());
            viewHolder.tvQuantity.setText(myOderDetailsBeanArrayList.get(i).getQty());
            viewHolder.tvPrice.setText(myOderDetailsBeanArrayList.get(i).getUnitPrice());
//            viewHolder.tvOrderDate.setText("Order Date: " + mSimpleDateFormat.format(sdf.parse(myOderBeanArrayList.get(i).getCreatedDate())));
//            viewHolder.tvApproxDeliverDate.setText("Approx Delivery Date: " + mSimpleDateFormat.format(sdf.parse(myOderBeanArrayList.get(i).getDeliverydate())));
//            viewHolder.tvOderStatus.setText("Status: " + myOderBeanArrayList.get(i).getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return myOderDetailsBeanArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemName;
        private TextView tvQuantity;
        private TextView tvPrice;

        public ViewHolder(View view) {
            super(view);
            tvItemName = (TextView) view.findViewById(R.id.my_order_Detail_item_name);
            tvQuantity = (TextView) view.findViewById(R.id.my_order_detail_quantity);
            tvPrice = (TextView) view.findViewById(R.id.my_order_detail_price);
        }
    }

}
