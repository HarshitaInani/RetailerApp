package app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.utils.ComplexPreferences;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.CartActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.Constant;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.HomeActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.RewardItemActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by user on 4/7/2017.
 */

public class RewardItemAdapter extends RecyclerView.Adapter<RewardItemAdapter.ViewHolder> {

    private JSONArray jsonArray;
    private JSONArray jsonArrayQ;

    private Context context;
    AnimationDrawable animation;
    int count;
    Dialog mDialog;
    ArrayList<String> itemNameList = new ArrayList<>();
    ArrayList<String> itemDesList = new ArrayList<>();

    ArrayList<String> itemIdList = new ArrayList<>();

    ArrayList<String> itemPointList = new ArrayList<>();


    ArrayList<String> itemImageUrl = new ArrayList<>();

    ArrayList<String> isActiveList = new ArrayList<>();
    ArrayList<String> isDeletedList = new ArrayList<>();

    ArrayList<Integer> quantityList = new ArrayList<>();

    ArrayList<Integer> jsonquantityList = new ArrayList<>();


//    LinkedList<Integer> quantityList = new_added LinkedList<>();

    //JSONObject qJson = new_added JSONObject();







    JSONObject jsonObject = new JSONObject();

    JSONObject jsonObjectQ = new JSONObject();


    int quantityCount = 1;


    public RewardItemAdapter(Context context, JSONArray jsonArray, JSONArray jsonArrayQ, int count) {

        this.context = context;
        this.jsonArray = jsonArray;
        this.count = count;
        this.jsonArrayQ = jsonArrayQ;


    }

    @Override
    public RewardItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reward_items, viewGroup, false);
        return new RewardItemAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final RewardItemAdapter.ViewHolder viewHolder, final int i) {

        //  Toast.makeText(context, "Adapter Array"+jsonArray.toString(), Toast.LENGTH_SHORT).show();


      //  viewHolder.setIsRecyclable(false);

        try {
            for (int j = 0; j < jsonArray.length(); j++) {

                jsonObject = jsonArray.getJSONObject(j);


                itemNameList.add(jsonObject.getString("rItem"));
                itemDesList.add(jsonObject.getString("Description"));
                itemImageUrl.add(jsonObject.getString("ImageUrl"));

                isActiveList.add(jsonObject.getString("IsActive"));
                isDeletedList.add(jsonObject.getString("IsDeleted"));

                itemIdList.add(jsonObject.getString("rItemId"));

                itemPointList.add(jsonObject.getString("rPoint"));

                quantityList.add(0);

                /*nameList.add(jsonObject.getString("Name"));

                skCodeList.add(jsonObject.getString("Skcode"));

                //BeatNumber

                entryList.add(jsonObject.getString("BeatNumber"));

                daysList.add(jsonObject.getString("Day"));

                rewardsPointList.add(jsonObject.getString("Rewardspoints"));
*/

                //Toast.makeText(context, ""+jsonObject.getString("Name"), Toast.LENGTH_SHORT).show();
                //viewHolder.tvName.setText(jsonObject.getString("Name"));


            }

        } catch (Exception e) {

        }


        if (isActiveList.get(i).equals("false")) {

            viewHolder.currentLinearLayout.setVisibility(View.GONE);
            viewHolder.currentLinearLayout.setMinimumHeight(0);

            viewHolder.cardView.setVisibility(View.GONE);
         //   viewHolder.currentLinearLayout.set

        }

        if (isDeletedList.get(i).equals("true")) {

            viewHolder.currentLinearLayout.setVisibility(View.GONE);

            viewHolder.currentLinearLayout.setMinimumHeight(0);

            viewHolder.cardView.setVisibility(View.GONE);
        }


        viewHolder.tvItemName.setText(itemNameList.get(i));
        viewHolder.tvDes.setText(itemDesList.get(i));

//        viewHolder.tvPointValue.setText(itemPointList.get(i));

        viewHolder.tvPointValue.setText("Points : "+itemPointList.get(i));




       // viewHolder.tvQty.setText("1");


  //      Quantity
//        viewHolder.tvQty.setText(quantityList.get(i).toString());
        //    viewHolder.tvQty.setText(jsonquantityList.get(i).toString());

        try {
            viewHolder.tvQty.setText(jsonArrayQ.getJSONObject(i).getString("quantity"));
        } catch (JSONException e) {
            e.printStackTrace();
        };



        String temp = itemImageUrl.get(i).trim();
        temp = temp.replaceAll(" ", "%20");


        Picasso.with(context).load(temp).placeholder(R.drawable.top_img_bg).error(R.drawable.top_img_bg).into(viewHolder.itemImage);


        viewHolder.buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.buyBtn.setEnabled(false);
                showLoading();
              //  ((RewardItemActivity)context).setPointValue();

               // Toast.makeText(context, "Q"+quantityList.get(i)+"\n"+itemNameList.get(i), Toast.LENGTH_SHORT).show();


             //   Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();


                try {
                    Toast.makeText(context, "Q"+jsonArrayQ.getJSONObject(i).getString("quantity")+"\n"+itemNameList.get(i), Toast.LENGTH_SHORT);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject params = new JSONObject();
                JSONArray quantityArray = new JSONArray();
                JSONObject arrayObject = new JSONObject();
                try {

                    ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(context, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                    final RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);




//                    params.put("Skcode", Utility.getStringSharedPreferences(context, "BeatSkCode"));
//                    params.put("CustomerId", Utility.getStringSharedPreferences(context, "BeatSkCodeCId"));


                    params.put("Skcode", mRetailerBean.getSkcode());
                    params.put("CustomerId", mRetailerBean.getCustomerId());


                    params.put("WalletPoint", ""+Integer.parseInt(itemPointList.get(i))
                            *
                            Integer.parseInt(jsonArrayQ.getJSONObject(i).getString("quantity"))

                    );



                    arrayObject.put("ItemId", itemIdList.get(i));
                    arrayObject.put("OrderQty", jsonArrayQ.getJSONObject(i).getString("quantity"));


                    quantityArray.put(arrayObject);

                    params.put("DreamItemDetails", quantityArray);




                Log.e("Reward params",params.toString());
            //    Toast.makeText(context, params.toString(), Toast.LENGTH_SHORT).show();




                if(Integer.parseInt(jsonArrayQ.getJSONObject(i).getString("quantity"))
                        >
                        0
                        ) {
                    if (Double.parseDouble(Utility.getStringSharedPreferences(context, "WalletTotal"))
                            >
                            Integer.parseInt(itemPointList.get(i)) * Integer.parseInt(jsonArrayQ.getJSONObject(i).getString("quantity"))
                            )

                    {

                        new AQuery(context).post(Constant.BASE_URL_BUT_REWARD_ITEMS, params, JSONObject.class, new AjaxCallback<JSONObject>() {

                            @Override
                            public void callback(String url, JSONObject json, AjaxStatus status) {

                                if (json.toString() != null) {
                                    if (mDialog.isShowing()) {
                                        animation.stop();
                                        mDialog.dismiss();
                                    }

                                    Toast.makeText(context, "Order success", Toast.LENGTH_SHORT).show();

                                    context.startActivity(new Intent(context, HomeActivity.class));
                                    ((RewardItemActivity) context).finish();

                                } else {

                                    if (mDialog.isShowing()) {
                                        animation.stop();
                                        mDialog.dismiss();
                                    }
                                    Toast.makeText(context, "Please try again!", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });


                    } else {
                        Toast.makeText(context, "You do not have enough amount!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Please select quantity!", Toast.LENGTH_SHORT).show();
                }




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });




        viewHolder.plusBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                JSONObject jsonObject = new JSONObject();



                    try {

                        int q = Integer.parseInt(jsonArrayQ.getJSONObject(i).getString("quantity"));
                        ++q;

                        jsonObject = jsonArrayQ.getJSONObject(i);
                        jsonObject.put("quantity", q );
                        jsonArrayQ.put(i, jsonObject);

                        viewHolder.tvQty.setText("" + jsonArrayQ.getJSONObject(i).getString("quantity"));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //   quantityList.add(i, q);
//                    viewHolder.tvQty.setText("" + q);

                    // viewHolder.tvQty.setText("" + quantityList.get(i));




                    //  notifyDataSetChanged();




            }

        });



        viewHolder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                  /*      int q = quantityList.get(i);
                        --q;

                        quantityList.add(i, q);

                        viewHolder.tvQty.setText("" + quantityList.get(i));
                      // notifyDataSetChanged();
*/

                try {

                    if (Integer.parseInt(jsonArrayQ.getJSONObject(i).getString("quantity")) > 0) {


                        int q = Integer.parseInt(jsonArrayQ.getJSONObject(i).getString("quantity"));
                        --q;

                        jsonObject = jsonArrayQ.getJSONObject(i);
                        jsonObject.put("quantity", q);
                        jsonArrayQ.put(i, jsonObject);

                        viewHolder.tvQty.setText("" + jsonArrayQ.getJSONObject(i).getString("quantity"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }





            }
        });








//          viewHolder.tvName.setText("Get name");


/*
        viewHolder.tvName.setText(nameList.get(i));


        viewHolder.tvSkcode.setText(skCodeList.get(i));

        //   viewHolder.tvEntry.setText("Get Entry");

        viewHolder.tvEntry.setText(entryList.get(i));


        viewHolder.tvDay.setText(daysList.get(i));

        viewHolder.itemView.setOnClickListener(new_added View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sk = skCodeList.get(i).toString();
                String rp = rewardsPointList.get(i).toString();
                //   Toast.makeText(context, ""+skCodeList.get(i), Toast.LENGTH_SHORT).show();

                Intent i = new_added Intent(context, BeatOrderActivity.class);

                i.putExtra("MyBeatSkCode", sk);

                i.putExtra("MyRewardsPoint", rp);
                //   i.putExtra("", "");
                // context.startActivity(new_added Intent(context, BeatOrderActivity.class));

                context.startActivity(i);

                ((Activity)context).finish();

            }
        });
*/







     /*   try {

        } catch (IndexOutOfBoundsException e) {
            context.startActivity(new_added Intent(context, HomeActivity.class));
        }
   */


    }

    @Override
    public int getItemCount() {


//        return itemListArrayList.size();

        return count;

    }



    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvItemName, tvDes, tvQty, tvPointValue;
        ImageView itemImage;
        Button buyBtn;

        ImageView plusBtn, minusBtn;

        LinearLayout currentLinearLayout;

        CardView cardView;
        public ViewHolder(View view) {
            super(view);

            tvItemName = (TextView) view.findViewById(R.id.reward_item_name);
            tvDes= (TextView) view.findViewById(R.id.reward_item_des);
            tvQty = (TextView) view.findViewById(R.id.item_row_quantity_tv);
            tvPointValue = (TextView) view.findViewById(R.id.reward_item_value);

            itemImage = (ImageView) view.findViewById(R.id.reward_image);

            buyBtn = (Button) view.findViewById(R.id.reward_buy_btn);

            currentLinearLayout = (LinearLayout) view.findViewById(R.id.reward_item_view);

            plusBtn = (ImageView) view.findViewById(R.id.item_row_plus_icon);

            minusBtn = (ImageView) view.findViewById(R.id.item_row_minus_icon);


            cardView= (CardView) view.findViewById(R.id.cardView);
        }



    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void showLoading() {


        mDialog = new Dialog(context);
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
