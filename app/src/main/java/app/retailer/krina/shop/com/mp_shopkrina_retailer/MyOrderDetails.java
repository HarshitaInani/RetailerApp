package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.utils.ComplexPreferences;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.MyOrderDetailRecyclerViewAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.MyOderBean;

/**
 * Created by Krishna on 27-01-2017.
 */

public class MyOrderDetails extends AppCompatActivity {

    RecyclerView mMyOrderDetailsRecyclerView;
    int selectedPosition;
    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_orders_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        mMyOrderDetailsRecyclerView = (RecyclerView) findViewById(R.id.my_order_detail_rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mMyOrderDetailsRecyclerView.setLayoutManager(llm);

        selectedPosition = getIntent().getIntExtra("selectedPosition", -1);
        if (selectedPosition == -1) {
            Toast.makeText(this, "Selected Item position not found", Toast.LENGTH_SHORT).show();
            MyOrderDetails.this.finish();
        }
        ((ImageView) toolbar.findViewById(R.id.my_order_detail_nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyOrderDetails.this.finish();
            }
        });

        ((ImageView) toolbar.findViewById(R.id.my_order_detail_home_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyOrderDetails.this.finish();
            }
        });


    }

    @Override
    protected void onStart() {
        ComplexPreferences mMyOrderPref = ComplexPreferences.getComplexPreferences(MyOrderDetails.this, Constant.MY_ORDER_PREF, MODE_PRIVATE);
        Type typeMyOrderArrayList = new TypeToken<ArrayList<MyOderBean>>() {
        }.getType();
        ArrayList<MyOderBean> myOderBeanArrayList = mMyOrderPref.getArray(Constant.MY_ORDER_PREF_OBJ, typeMyOrderArrayList);
        if (myOderBeanArrayList != null && myOderBeanArrayList.size() > selectedPosition) {

            ((TextView) findViewById(R.id.my_order_detail_order_no)).setText(myOderBeanArrayList.get(selectedPosition).getOrderId());
            try {
                ((TextView) findViewById(R.id.my_order_detail_date)).setText(mSimpleDateFormat.format(sdf.parse(myOderBeanArrayList.get(selectedPosition).getCreatedDate())));
            } catch (Exception e) {
                e.printStackTrace();
            }
            ((TextView) findViewById(R.id.my_order_detail_name)).setText(myOderBeanArrayList.get(selectedPosition).getCustomerName());
            ((TextView) findViewById(R.id.my_order_detils_billing_address)).setText(myOderBeanArrayList.get(selectedPosition).getBillingAddress());
            ((TextView) findViewById(R.id.my_order_detail_shipping_address)).setText(myOderBeanArrayList.get(selectedPosition).getShippingAddress());
            ((TextView) findViewById(R.id.content_my_order_grand_total_amount_tv)).setText("Total Amount: " + myOderBeanArrayList.get(selectedPosition).getTotalAmount());

            MyOrderDetailRecyclerViewAdapter myOrderDetailRecyclerViewAdapter = new MyOrderDetailRecyclerViewAdapter(MyOrderDetails.this, myOderBeanArrayList.get(selectedPosition).getMyOrderDetailsBeanArrayList());
            mMyOrderDetailsRecyclerView.setAdapter(myOrderDetailRecyclerViewAdapter);
        } else {
            Toast.makeText(this, "No Item Found", Toast.LENGTH_SHORT).show();
            MyOrderDetails.this.finish();
        }
        super.onStart();
    }
}