package app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.Gullakpojo;

/**
 * Created by User on 13-07-2018.
 */

          public class GullaktransactionAdapter extends RecyclerView.Adapter <GullaktransactionAdapter.ViewHolder>{
    private ArrayList<Gullakpojo> GullakpojoArrayList;
    private Context context;
    private LayoutInflater inflater;

    public GullaktransactionAdapter(Context context, ArrayList<Gullakpojo> GullakpojoArrayList){
        this.context = context;
        this.GullakpojoArrayList = GullakpojoArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public GullaktransactionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = inflater.inflate(R.layout.transactionrecycleview, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v); // pass the view to View Holder
        return vh;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(GullaktransactionAdapter.ViewHolder holder, final int position) {

        final Gullakpojo Pojo = GullakpojoArrayList.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date=null;
        try {
      date =sdf.parse(Pojo.getCreatedDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputPattern = "dd/MM/yyyy";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
     String   str = outputFormat.format(date);
        holder.t1.setText(str);
        System.out.println("ID::"+Pojo.getId());
        holder.t2.setText(Pojo.getAmountIn());
        holder.t3.setText(Pojo.getAmountOut());
        holder.t4.setText(Pojo.getGullakAmount());



    }
    @Override
    public int getItemCount() {
return GullakpojoArrayList.size();
    }
public class ViewHolder extends RecyclerView.ViewHolder {
    TextView t1,t2,t3,t4,t5;// init the item view's
    public ViewHolder(View itemView) {
        super(itemView);
        // get the reference of item view's
        t1 = (TextView) itemView.findViewById(R.id.t1);
        t2 = (TextView) itemView.findViewById(R.id.t2);
        t3 = (TextView) itemView.findViewById(R.id.t3);
        t4=(TextView) itemView.findViewById(R.id.t4);
        t5=(TextView) itemView.findViewById(R.id.t5);
    }
}
}