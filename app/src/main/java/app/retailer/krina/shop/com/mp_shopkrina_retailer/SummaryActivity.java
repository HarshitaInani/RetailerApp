package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.utils.ComplexPreferences;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import java.util.List;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.SummaryPojo;


public class SummaryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Context context1;
    Dialog mDialog;
    TextView tvSkCode, tvtotal,tvstatus,tvno,tvcancelamount,tvcancelcount,tvdeliveredcount,tvdeliveredamount,tvpendingcount,tvpendingamount,tvtotalcount,tvtotalamount;
    AnimationDrawable animation;
    List<SummaryPojo> List = new ArrayList<SummaryPojo>();
    String skCode,status,total,order;
  double deliveredtotalamount=0,canceltotalamount=0, pendingtotalamount=0,alltotalamount;
    int totalorderOfcancel=0,totalorderofdelivered=0,totalorderofpending=0,alltotalcount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_dial_toolbar);
        setSupportActionBar(toolbar);
      TextView  title_toolbar=(TextView)findViewById(R.id.title_toolbar) ;
        title_toolbar.setText("Summary");

        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SummaryActivity.this.finish();
            }
        });

        ((ImageView) toolbar.findViewById(R.id.nav_home_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SummaryActivity.this.finish();
                startActivity(new Intent(SummaryActivity.this, HomeActivity.class));
            }
        });

       /* recyclerView = (RecyclerView) findViewById(R.id.recyeler);
        recyclerView.setLayoutManager(new_added LinearLayoutManager(context1));
*/
       // tvSkCode = (TextView) findViewById(R.id.sk);
      // tvtotal = (TextView) findViewById(R.id.tamount);
       // tvstatus = (TextView) findViewById(R.id.status);
        tvno = (TextView) findViewById(R.id.totalcount);
//for cancel order
        tvcancelcount = (TextView) findViewById(R.id.count3);
        tvcancelamount = (TextView) findViewById(R.id.cancelamount);

        //for delivered order

        tvdeliveredcount = (TextView) findViewById(R.id.deliveredcount);
        tvdeliveredamount = (TextView) findViewById(R.id.deliveredamount);

          //for pending order
        tvpendingcount = (TextView) findViewById(R.id.pendingcount);
        tvpendingamount = (TextView) findViewById(R.id.pendingamount);

// for total amount

        tvtotalcount = (TextView) findViewById(R.id.totalcount);
        tvtotalamount = (TextView) findViewById(R.id.totalamount);


        callSummaryApi();



  }

    public void callSummaryApi() {
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(SummaryActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        showLoading();
        System.out.println("data:::"+Constant.BASE_URL_SUMMARY+"day=30day&skcode="+mRetailerBean.getSkcode());
        new AQuery(SummaryActivity.this).ajax(Constant.BASE_URL_SUMMARY+"day=30day&skcode="+mRetailerBean.getSkcode(),
                null,
                JSONArray.class,
                new AjaxCallback<JSONArray>(){

                    @Override
                    public void callback(String url, JSONArray jsonArray, AjaxStatus status) {


                        System.out.println("jsonArray::"+jsonArray);
                        if (jsonArray != null) {

                            for (int i = 0; i < jsonArray.length(); i++) {

                                try {

                                    JSONObject obj = jsonArray.getJSONObject(i);

                                    //SummaryPojo feeds = new_added SummaryPojo(obj.getString("Id"),obj.getString("name"),obj.getString("createdDate"),obj.getString("TotalAmount"),
                                           // obj.getString("status"),obj.getString("Comment"),obj.getString("day"),obj.getString("monthValue"),obj.getString("year"),obj.getString("order"));

                                  //  List.add(feeds);
                                    System.out.println("resultissue::"+obj.getString("status").toString());

                                    if( obj.getString("status").toString().trim().equals("Order Canceled")||obj.getString("status").equalsIgnoreCase("Delivery Canceled"))
                                    {
                                        String price = new DecimalFormat("##.##").format((Double.parseDouble(obj.getString("TotalAmount"))));
                                        canceltotalamount+=Double.parseDouble(price);
                                        totalorderOfcancel += Integer.parseInt(obj.getString("TotalOrder"));

                                    }else if(obj.getString("status").equalsIgnoreCase("Pending")||obj.getString("status").equalsIgnoreCase("Ready to Dispatch"))
                                    {
                                        System.out.println("Status:1121:"+obj.getString("status"));
                                        String ptotal = new DecimalFormat("##.##").format((Double.parseDouble(obj.getString("TotalAmount"))));
                                        pendingtotalamount+=Double.parseDouble(ptotal);
                                        totalorderofpending += Integer.parseInt(obj.getString("TotalOrder"));

                                    }
                                 else /*if(obj.getString("status").equalsIgnoreCase("Delivered")||obj.getString("status").equalsIgnoreCase("sattled"))*/
                                    {
                                        String dtotal = new DecimalFormat("##.##").format((Double.parseDouble(obj.getString("TotalAmount"))));
                                        deliveredtotalamount+=Double.parseDouble(dtotal);
                                        totalorderofdelivered += Integer.parseInt(obj.getString("TotalOrder"));

                                    }


                                 } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                } finally {
                                   // adapter = new_added SummaryAdapter(SummaryActivity.this, List);
                                    //recyclerView.setAdapter(adapter);
                                    //Notify adapter about data changes
                                   // adapter.notifyDataSetChanged();
                                }
                            }

                            tvcancelamount.setText(String.valueOf(canceltotalamount));
                            tvcancelcount.setText(String.valueOf(totalorderOfcancel));

                            tvpendingamount.setText(String.valueOf(pendingtotalamount));
                            tvpendingcount.setText(String.valueOf(totalorderofpending));

                            tvdeliveredamount.setText(String.valueOf(deliveredtotalamount));
                            tvdeliveredcount.setText(String.valueOf(totalorderofdelivered));


                            alltotalamount=canceltotalamount+deliveredtotalamount+pendingtotalamount;
                            tvtotalamount.setText(String.valueOf(alltotalamount));

                            alltotalcount=totalorderOfcancel+totalorderofdelivered+totalorderofpending;
                            tvtotalcount.setText(String.valueOf(alltotalcount));

                            if (mDialog.isShowing()) {
                                animation.stop();
                                mDialog.dismiss();
                            }

                        } else {

                            if (mDialog.isShowing()) {
                                animation.stop();
                                mDialog.dismiss();
                            }


                            Toast.makeText(SummaryActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void showLoading() {
        mDialog = new Dialog(SummaryActivity.this);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.progress_dialog);
        ((TextView) mDialog.findViewById(R.id.progressText)).setText("Logging in please wait...");
        ImageView la = (ImageView) mDialog.findViewById(R.id.gridprogressBar);
        la.setBackgroundResource(R.drawable.custom_progress_dialog_animation);
        animation = (AnimationDrawable) la.getBackground();
        animation.start();
        mDialog.setCancelable(false);
        mDialog.show();

    }

}
