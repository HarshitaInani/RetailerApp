package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MilestoneActivity extends AppCompatActivity {




    JSONArray jsonArray1;


    ArrayList<String> totalList = new ArrayList<>();
    ArrayList<String> dateList = new ArrayList<>();

    ArrayList<String> valueList = new ArrayList<>();

    SimpleDateFormat format2;
    Date newDate2;

    BarChart barChart;

    Legend l;

    String id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milestone);


        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        setGraph();

        ((TextView) findViewById(R.id.title_toolbar)).setText("Milestone");


        Toolbar toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
        setSupportActionBar(toolbar);


        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MilestoneActivity.this.finish();
            }
        });





    }


    public void setGraph() {
        new AQuery(getApplicationContext()).ajax(Constant.BASE_URL+"pointConversion/milestone", null, JSONArray.class, new AjaxCallback<JSONArray>() {


            @Override
            public void callback(String url, JSONArray jsonArray, AjaxStatus status) {
                //   Toast.makeText(LoginActivity_Nav.this, "Aquery call"+status.getError()+status.getMessage(), Toast.LENGTH_SHORT).show();

                if (jsonArray == null) {
                    Toast.makeText(MilestoneActivity.this, "Json is null " + status.getError(), Toast.LENGTH_SHORT).show();


                } else {


                    //      Toast.makeText(ReedemPointActivity.this, "Json Array "+jsonArray.toString(), Toast.LENGTH_SHORT).show();


                    try {

                        totalList = new ArrayList<String>();
                        valueList = new ArrayList<>();


                        jsonArray1 = new JSONArray(jsonArray.toString());
                        JSONObject jsonObject = new JSONObject();



                        for (int i = 0; i< jsonArray1.length(); i++) {



                            jsonObject = jsonArray1.getJSONObject(i);


                            totalList.add(jsonObject.getString("mPoint"));

                            valueList.add(jsonObject.getString("rPoint"));







                        }

                        getMonthData(totalList, valueList);

//                               getMonthData(valueList, totalList);


                    } catch (Exception e) {

                        Log.e("Json error Beat Order", e.toString() );
                        Toast.makeText(MilestoneActivity.this, "Error json "+e.toString(), Toast.LENGTH_LONG).show();

                    }

                }
            }


        });


    }



    public void getMonthData(ArrayList totalList, ArrayList dateList) {

        barChart = (BarChart) findViewById(R.id.chart);

       /* l = barChart.getLegend();

        l.setTextSize(24f);
        l.setEnabled(false);
*/
        //  lineChart.set

        //  lineChart.setDescription("Amount");

        //   lineChart.setTouchEnabled(false);

        barChart.setDescription("");
        ArrayList<BarEntry> entries = new ArrayList<>();


        for (int i =0; i < totalList.size(); i++) {


            //  double value = Double.parseDouble(totalList.get(i).toString());

            float value2 = Float.parseFloat(totalList.get(i).toString());

            entries.add(new BarEntry(value2, i));



        }


        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false);






        BarDataSet dataset = new BarDataSet(entries,"");

        dataset.setValueTextSize(12f);


        ArrayList<String> labels = dateList;

        BarData data = new BarData(labels, dataset);

        // dataset.setColors(ColorTemplate.COLORFUL_COLORS); //




        barChart.setData(data);
        barChart.animateY(2000);
        //  LineData data = new_added LineData(labels, dataset);

        //   dataset.setColors(ColorTemplate.PASTEL_COLORS);
//
        // dataset.setDrawCubic(true);

        //  dataset.setDrawFilled(true);


        // dataset.setValueTextSize(10);



        //   lineChart.setData(data);

        //   lineChart.animateY(2000);






    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();

        // startActivity(new_added Intent(context, DaysBidActivity.class));

        MilestoneActivity.this.finish();
    }













}
