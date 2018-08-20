package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.GullaktransactionAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.Gullakpojo;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;



public class GullakTransactionActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    String apiUrl = "http://137.59.52.130:8080/api/Gullak/gethistoryByDate";
    RecyclerView recycleview;
    String amountin,amountout,gullakamount,status,date;
    Button GetData;
    TextView StartDate, EndDate,t6;
    boolean flag=true;
    String sStartDate, sEndDate;
    RequestQueue queue;
    String time,time1;
    String CustomerId;
    RetailerBean mRetailerBean;
    String id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gullak_transaction);
        StartDate =(TextView)findViewById(R.id.start_date);
        EndDate =(TextView)findViewById(R.id.end_date);
        t6 =(TextView)findViewById(R.id.t6);
        GetData =(Button)findViewById(R.id.get_data);
        ImageView start=(ImageView)findViewById(R.id.startImage);
        ImageView end=(ImageView)findViewById(R.id.endImage);
        progressDialog = new ProgressDialog(GullakTransactionActivity.this);
      recycleview = (RecyclerView)findViewById(R.id.transactionrecycleview);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycleview.setLayoutManager(llm);
        MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
        myAsyncTasks.execute(apiUrl);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartDate();
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EndDate();
            }
        });
        GetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = false;
                /*if (StartDate.getText().toString().equals("") || EndDate.getText().toString().equals("")){
                    Toast.makeText(GullakTransactionActivity.this, "Please Enter star date and end date", Toast.LENGTH_SHORT).show();
                }*/
                if (StartDate.getText().toString().equals("")) {
                    Toast.makeText(GullakTransactionActivity.this, "Please Enter start date", Toast.LENGTH_SHORT).show();
                }
               else if (EndDate.getText().toString().equals("")) {
                    Toast.makeText(GullakTransactionActivity.this, "Please Enter end date", Toast.LENGTH_SHORT).show();
                }

             /*   progressDialog = new ProgressDialog(GullakTransactionActivity.this);
                progressDialog.setMessage("Please Wait");
                progressDialog.setCancelable(false);
                progressDialog.show();*/

                //   progressDialog.dismiss();


                else {

                    MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                    myAsyncTasks.execute();
                }
            }
        });

    }





    public class MyAsyncTasks extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            sStartDate = StartDate.getText().toString().trim();
            sEndDate = EndDate.getText().toString().trim();
            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(GullakTransactionActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
            mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
            CustomerId= mRetailerBean.getCustomerId();

            // implement API in background and store the response in current variable
          String apiUrl1;
            if(flag){

                apiUrl1= "http://137.59.52.130:8080/api/Gullak/gethistoryLastTen?id="+ CustomerId;

                    }
            else{

                apiUrl1 = apiUrl+"?id="+CustomerId+"&FromDate="+sStartDate+"&ToDate="+sEndDate;
                Log.d("URL+++",apiUrl1);
                Log.d("ID++",CustomerId);

            }




            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(apiUrl1);

                    urlConnection = (HttpURLConnection) url
                            .openConnection();

                    InputStream in = urlConnection.getInputStream();

                    InputStreamReader isw = new InputStreamReader(in);

                    int data = isw.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isw.read();
                      //  System.out.print(current+"hiii");
                        /*Log.d("API",current);*/

                    }
                    // return the data to onPostExecute method
                    return current;

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();

            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {

            Log.d("data", s.toString());
            // dismiss the progress dialog after receiving data from API

                    progressDialog.dismiss();
            ArrayList<Gullakpojo> GullakpojoArrayList=new ArrayList<>();
            try {
                // JSON Parsing of data
                JSONArray jsonArray = new JSONArray(s);


      for(int i=0;i<jsonArray.length();i++)
      {
    JSONObject oneObject = jsonArray.getJSONObject(i);
          amountin = oneObject.getString("AmountIn");
          amountout = oneObject.getString("AmountOut");
          gullakamount = oneObject.getString("GullakAmount");
          status=oneObject.getString("GullakRequestStatus");
t6.setText("GullakStatus:"+status);
          Log.d("AmountIn",amountin);
          Log.d("AmountOut",amountout);
          Log.d("GullakAmount",gullakamount);
          Log.d("STATUS",status);
          date=oneObject.getString("CreatedDate");
         // id = oneObject.getString("Id");
    GullakpojoArrayList.add(new Gullakpojo(amountin,amountout,gullakamount,status,date));
}
                // Pulling items from the array


            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("APIII", String.valueOf(GullakpojoArrayList));
            if (GullakpojoArrayList != null && !GullakpojoArrayList.isEmpty())

                try {
                    GullaktransactionAdapter Gullak = new GullaktransactionAdapter(GullakTransactionActivity.this, GullakpojoArrayList);
                    /*Log.d("APIIII", String.valueOf((GullakpojoArrayList)));*/
                    recycleview.setAdapter(Gullak);
                    Gullak.notifyDataSetChanged();
                } catch (IndexOutOfBoundsException e) {
                   // startActivity(new Intent(GullakTransactionActivity.this, HomeActivity.class));

                    System.out.println("Exeption:::"+e.toString());
                } catch (Exception e) {
                   // startActivity(new Intent(GullakTransactionActivity.this, HomeActivity.class));
                    e.printStackTrace();
                    System.out.println("Exeption1:::"+e.toString());
                }

        }

    }
    private void EndDate() {

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        System.out.println("the selected " + mDay);
        DatePickerDialog dialog = new DatePickerDialog(GullakTransactionActivity.this,
                new mDateSetListener1(), mYear, mMonth, mDay);
        dialog.show();
    }

    private void StartDate() {



        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        System.out.println("the selected " + mDay);
        DatePickerDialog dialog = new DatePickerDialog(GullakTransactionActivity.this,
                new mDateSetListener(), mYear, mMonth, mDay);
        dialog.show();

    }
    class mDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // getCalender();
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            mMonth=mMonth + 1;

            String monthString = String.valueOf(mMonth);
            System.out.println(monthString);
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            time= formatter.format(date);
            if (monthString.length() == 1) {
                monthString = "0" + monthString;

            }
            String mDayString = String.valueOf(mDay);
            System.out.println(mDayString);
            if (mDayString.length() == 1) {
                mDayString = "0" + mDayString;

            }
            StartDate.setText(new StringBuilder()
                    // Month is 0 based so add 1
                    /*.append(mMonth + 1).append("/").append(mDay).append("/")
                    .append(mYear).append(" "));*/
                    .append(mYear).append("-").append(monthString).append("-").append(mDayString).append("T").append(time));
            System.out.println(StartDate.getText().toString());


        }
    }

    class mDateSetListener1 implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // getCalender();
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            mMonth=mMonth + 1;
            String monthString = String.valueOf(mMonth);
            System.out.println(monthString);
            if (monthString.length() == 1) {
                monthString = "0" + monthString;
            }
            String mDayString = String.valueOf(mDay);
            System.out.println(mDayString);
            if (mDayString.length() == 1) {
                mDayString = "0" + mDayString;

            }
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
          time= formatter.format(date);
            System.out.println(formatter.format(date));
            EndDate.setText(new StringBuilder()
                    // Month is 0 based so add 1
                    /*.append(mMonth + 1).append("/").append(mDay).append("/")
                    .append(mYear).append(" "));*/
                    .append(mYear).append("-").append(monthString).append("-").append(mDayString).append("T").append(time));
            System.out.println(StartDate.getText().toString());

        }
    }



}
