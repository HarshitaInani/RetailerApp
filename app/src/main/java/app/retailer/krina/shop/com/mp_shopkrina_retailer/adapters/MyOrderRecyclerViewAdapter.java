package app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.MyOrderDetails;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.SummaryActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.MyOderBean;

/**
 * Created by Krishna on 29-12-2016.
 */

public class MyOrderRecyclerViewAdapter extends RecyclerView.Adapter<MyOrderRecyclerViewAdapter.ViewHolder> {
    private ArrayList<MyOderBean> myOderBeanArrayList;
    private Context context;
    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public MyOrderRecyclerViewAdapter(Context context, ArrayList<MyOderBean> myOderBeanArrayList) {

        this.myOderBeanArrayList = myOderBeanArrayList;
        this.context = context;

    }

    @Override
    public MyOrderRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_orders_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyOrderRecyclerViewAdapter.ViewHolder viewHolder, final int i) {
        try {

            System.out.println("Amount::"+myOderBeanArrayList.get(i).getTotalAmount());

            viewHolder.tvOrderNumber.setText("Order No: " + myOderBeanArrayList.get(i).getOrderId());
            viewHolder.tvAmount.setText("Amount: " + myOderBeanArrayList.get(i).getTotalAmount());
            viewHolder.tvDeliveryCharges.setText("Delivery Charges: " + myOderBeanArrayList.get(i).getDeliveryCharge());
            viewHolder.tvOrderDate.setText("Order Date: " + mSimpleDateFormat.format(sdf.parse(myOderBeanArrayList.get(i).getCreatedDate())));
            viewHolder.tvApproxDeliverDate.setText("Approx Delivery Date: " + mSimpleDateFormat.format(sdf.parse(myOderBeanArrayList.get(i).getDeliverydate())));
            viewHolder.ivOrdered.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.icn_1));
            viewHolder.ivDispached.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.icn_2));
            viewHolder.ivDelivered.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.icn_3));


       //     viewHolder.tvDpPoints.setText("DP points: "+myOderBeanArrayList.get(i).getDreamPoints());


            System.out.println("Status::;"+myOderBeanArrayList.get(i).getStatus());

            if (myOderBeanArrayList.get(i).getStatus().equalsIgnoreCase("Pending")) {
                viewHolder.ivOrdered.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.icn_1));
            }


            else if (myOderBeanArrayList.get(i).getStatus().equalsIgnoreCase("Shipped")) {
                viewHolder.ivDispached.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.icn_selecte_2));



            } else if (myOderBeanArrayList.get(i).getStatus().equalsIgnoreCase("Delivered")) {
                viewHolder.ivDispached.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.icn_selecte_2));
                viewHolder.ivDelivered.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.icn_selecte_3));
            }
            viewHolder.mRowRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, MyOrderDetails.class).putExtra("selectedPosition", i));
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return myOderBeanArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mRowRl;
        private TextView tvOrderNumber;
        private TextView tvAmount;
        private TextView tvDeliveryCharges;
        private TextView tvOrderDate;
        private TextView tvApproxDeliverDate;
        ImageView ivOrdered;
        ImageView ivDispached;
        ImageView ivDelivered;
       // TextView Summary_button;
        private TextView tvDpPoints;
        public ViewHolder(View view) {
            super(view);
            mRowRl = (LinearLayout) view.findViewById(R.id.row_rl);
            tvOrderNumber = (TextView) view.findViewById(R.id.my_order_order_no);
            tvAmount = (TextView) view.findViewById(R.id.my_order_amount);
            tvDeliveryCharges = (TextView) view.findViewById(R.id.my_order_delivery_charges);
            tvOrderDate = (TextView) view.findViewById(R.id.my_order_order_date);
            tvApproxDeliverDate = (TextView) view.findViewById(R.id.my_order_approx_deliver_date);

           // tvDpPoints = (TextView) view.findViewById(R.id.my_order_dp_points);


            ivOrdered = (ImageView) view.findViewById(R.id.my_order_iv_ordered);
            ivDispached = (ImageView) view.findViewById(R.id.my_order_iv_dispached);
            ivDelivered = (ImageView) view.findViewById(R.id.my_order_iv_delivered);
           // Summary_button = (TextView) view.findViewById(R.id.Summary_button);

        }
    }

}
