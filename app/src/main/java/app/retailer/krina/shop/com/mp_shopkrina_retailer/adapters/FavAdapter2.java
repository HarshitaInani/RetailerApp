package app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.utils.TextUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.Constant;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.MyFavourite;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.CartItemInfo;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.basecat_subcat_cat_bean_package.ItemList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;

/**
 * Created by user on 5/4/2017.
 */

public class FavAdapter2 extends RecyclerView.Adapter<FavAdapter2.ViewHolder> {



    private ArrayList<ItemList> itemListArrayList;
    private Context context;
    private int ivWidth;
    private int ivHeight;
    private TextView tvTotalItemPrice;
    private TextView tvTotalItemQty;

    private TextView tvTotalDp;


    private double deliveryCharges = 10;


    String language;



    public FavAdapter2(Context context, ArrayList<ItemList> itemListArrayList  , TextView tvTotalItemPrice, TextView tvTotalItemQty , TextView tvTotalDp , int ivWidth, int ivHeight ) {
        this.itemListArrayList = itemListArrayList;
        this.context = context;

        this.tvTotalItemPrice = tvTotalItemPrice;
        this.tvTotalItemQty = tvTotalItemQty;


        this.tvTotalDp= tvTotalDp;

        this.ivWidth = ivWidth;
        this.ivHeight = ivHeight;



    }

    @Override
    public FavAdapter2.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_frag_brand_wise, viewGroup, false);
        return new FavAdapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavAdapter2.ViewHolder viewHolder, final int i) {

        viewHolder.tvItemName.setText(itemListArrayList.get(i).getItemname());


      /*
        Typeface normalTypeface = FontCache.get("fonts/krdv011.ttf", context);
        viewHolder.tvItemNameHindi.setTypeface(normalTypeface);
        viewHolder.tvItemNameHindi.setText(itemListArrayList.get(i).getHindiName());

*/





        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/krdv011.ttf");

        language =  Utility.getStringSharedPreferences(context, "MultiLaguage");


        if (language.equals("m")) {

            viewHolder.tvItemNameHindi.setTypeface(tf);
            viewHolder.tvItemNameHindi.setVisibility(View.VISIBLE);

        }

        if (language.equals("s")) {

            viewHolder.tvItemNameHindi.setVisibility(View.GONE);
        }


        viewHolder.tvItemNameHindi.setText(itemListArrayList.get(i).getHindiName());







//        Toast.makeText(context, "From Adapter"+ itemListArrayList.size()+"\n"+itemListArrayList.get(1).toString(), Toast.LENGTH_SHORT).show();


        String jsonString = Utility.getStringSharedPreferences(context, "ItemFavJson");
        try {
            JSONObject jsonObject;
//                        JSONObject jsonObject = new_added JSONObject(jsonString.toString());

            if (jsonString.isEmpty()) {
                jsonObject = new JSONObject();

            } else {

                jsonObject = new JSONObject(jsonString.toString());






                if (jsonObject.has(itemListArrayList.get(i).getItemId())) {

                    if (jsonObject.get(itemListArrayList.get(i).getItemId()).equals("1")) {

                        //   jsonObject.put(""+itemListArrayList.get(i).getItemId(), "0");




                        viewHolder.favItem.setImageResource(R.drawable.ic_favorite_red);


                        //   Utility.setStringSharedPreference(context, "ItemFavJson" ,jsonObject.toString());

                    }

                    else if (jsonObject.get(itemListArrayList.get(i).getItemId()).equals("0")) {
                        //    jsonObject.put(""+itemListArrayList.get(i).getItemId(), "1");
                        viewHolder.favItem.setImageResource(R.drawable.ic_favorite);

                        //    Utility.setStringSharedPreference(context, "ItemFavJson" ,jsonObject.toString());
                    }

                }



                if (!jsonObject.has(itemListArrayList.get(i).getItemId())) {

//                    jsonObject.put(""+itemListArrayList.get(i).getItemId(), "1");

                    //      Utility.setStringSharedPreference(context, "ItemFavJson" ,jsonObject.toString());

                    viewHolder.favItem.setImageResource(R.drawable.ic_favorite);


                }


            }
        }catch (Exception e) {

        }






        if (itemListArrayList.get(i).getDreamPoint().isEmpty()) {

            viewHolder.tvDpPoint.setVisibility(View.GONE);

        }


        viewHolder.tvDpPoint.setText("Dream Point "+itemListArrayList.get(i).getDreamPoint());






        if (!TextUtils.isNullOrEmpty(itemListArrayList.get(i).getLogoUrl()))
             Picasso.with(context).load(Constant.BASE_URL_Images1 + itemListArrayList.get(i).getItemNumber() + ".jpg").resize(ivWidth, ivHeight).into(viewHolder.ivItemImage);


        viewHolder.tvMoqMrp.setText("MOQ: " + itemListArrayList.get(i).getMinOrderQty() + " | MRP: " + new DecimalFormat("##.##").format ((Double.parseDouble(itemListArrayList.get(i).getPrice()))));
        String text = "<font color=#FF4500>&#8377; " + new DecimalFormat("##.##").format(Double.parseDouble(itemListArrayList.get(i).getUnitPrice())) + "</font>" + " | Margins: " + (new DecimalFormat("##.##").format((((Double.parseDouble(itemListArrayList.get(i).getPrice()) - Double.parseDouble(itemListArrayList.get(i).getUnitPrice())) / Double.parseDouble(itemListArrayList.get(i).getPrice())) * 100))) + "%";
        viewHolder.tvRateMargins.setText(Html.fromHtml(text));

        viewHolder.tvselectedItemQuantity.setText("" + 0);
        String price = "<font color=#FF4500>&#8377; " + new DecimalFormat("##.##").format((0 * Double.parseDouble(itemListArrayList.get(i).getUnitPrice())));
        viewHolder.tvSelectedItemPrice.setText(Html.fromHtml(price));

        if (((MyFavourite) context).getCartItem().getTotalPrice() < 2000) {
            deliveryCharges = 10;

          //  Toast.makeText(context, ""+deliveryCharges, Toast.LENGTH_SHORT).show();
        } else {

          //  Toast.makeText(context, ""+deliveryCharges, Toast.LENGTH_SHORT).show();
            deliveryCharges = 0;
        }



        tvTotalItemPrice.setText("Total: " + new DecimalFormat("##.##").format(((MyFavourite) context).getCartItem().getTotalPrice()));
        tvTotalItemQty.setText("" + (int) ((MyFavourite) context).getCartItem().getTotalQuantity());


        ArrayList<CartItemInfo> mCartItemInfos = ((MyFavourite) context).getCartItem().getmCartItemInfos();
        for (int j = 0; j < mCartItemInfos.size(); j++) {
            if (itemListArrayList.get(i).getItemId().equalsIgnoreCase(mCartItemInfos.get(j).getItemId())) {
                int itemQuantity = mCartItemInfos.get(j).getQty();
                if (itemQuantity > 0) {
                    viewHolder.tvselectedItemQuantity.setText("" + itemQuantity);
                    price = "<font color=#FF4500>&#8377; " + new DecimalFormat("##.##").format((itemQuantity * Double.parseDouble(itemListArrayList.get(i).getUnitPrice())));
                    viewHolder.tvSelectedItemPrice.setText(Html.fromHtml(price));

                    if (((MyFavourite) context).getCartItem().getTotalPrice() < 2000) {
                        deliveryCharges = 10;
                    } else {
                        deliveryCharges = 0;
                    }
                    tvTotalItemPrice.setText("Total: " + new DecimalFormat("##.##").format(((MyFavourite) context).getCartItem().getTotalPrice()));
                    tvTotalItemQty.setText("" + (int) ((MyFavourite) context).getCartItem().getTotalQuantity());
                }
            }
        }

        viewHolder.ivMinusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemQuantity = Integer.parseInt(viewHolder.tvselectedItemQuantity.getText().toString());
                if (itemQuantity > 0) {
                    itemQuantity -= Integer.parseInt(itemListArrayList.get(i).getMinOrderQty());
                    viewHolder.tvselectedItemQuantity.setText("" + itemQuantity);
                    String price = "<font color=#FF4500>&#8377; " + new DecimalFormat("##.##").format((itemQuantity * Double.parseDouble(itemListArrayList.get(i).getUnitPrice())));
                    viewHolder.tvSelectedItemPrice.setText(Html.fromHtml(price));

                    if (((MyFavourite) context).getCartItem().getTotalPrice() < 2000) {
                        deliveryCharges = 10;
                    } else {
                        deliveryCharges = 0;
                    }
                    String status = ((MyFavourite) context).addItemInCartItemArrayList(itemListArrayList.get(i).getItemId(), itemQuantity, Double.parseDouble(itemListArrayList.get(i).getUnitPrice()), itemListArrayList.get(i), deliveryCharges , Double.parseDouble(itemListArrayList.get(i).getDreamPoint())  ,itemListArrayList.get(i).getWarehouseId(), itemListArrayList.get(i).getCompanyId(),Double.parseDouble(itemListArrayList.get(i).getPrice()) );
                    tvTotalItemPrice.setText("Total: " + new DecimalFormat("##.##").format(((MyFavourite) context).getCartItem().getTotalPrice()));
                    tvTotalItemQty.setText("" + (int) ((MyFavourite) context).getCartItem().getTotalQuantity());


                    tvTotalDp.setText(("Dp : " + new DecimalFormat("##.##").format(((MyFavourite) context).getCartItem().getTotalDpPoints())));

                    Log.i(Constant.Tag, status);
                }
            }
        });

        viewHolder.ivPlusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemQuantity = Integer.parseInt(viewHolder.tvselectedItemQuantity.getText().toString());
                itemQuantity += Integer.parseInt(itemListArrayList.get(i).getMinOrderQty());
                viewHolder.tvselectedItemQuantity.setText("" + itemQuantity);
                String price = "<font color=#FF4500>&#8377; " + new DecimalFormat("##.##").format((itemQuantity * Double.parseDouble(itemListArrayList.get(i).getUnitPrice())));
                viewHolder.tvSelectedItemPrice.setText(Html.fromHtml(price));

                if (((MyFavourite) context).getCartItem().getTotalPrice() < 2000) {
                    deliveryCharges = 10;
                } else {
                    deliveryCharges = 0;
                }
                String status = ((MyFavourite) context).addItemInCartItemArrayList(itemListArrayList.get(i).getItemId(), itemQuantity, Double.parseDouble(itemListArrayList.get(i).getUnitPrice()), itemListArrayList.get(i), deliveryCharges , Double.parseDouble(itemListArrayList.get(i).getDreamPoint()),itemListArrayList.get(i).getWarehouseId(), itemListArrayList.get(i).getCompanyId(),Double.parseDouble(itemListArrayList.get(i).getPrice()) );
                tvTotalItemPrice.setText("Total: " + new DecimalFormat("##.##").format(((MyFavourite) context).getCartItem().getTotalPrice()));
                tvTotalItemQty.setText("" + (int) ((MyFavourite) context).getCartItem().getTotalQuantity());


                tvTotalDp.setText(("Dp : " + new DecimalFormat("##.##").format(((MyFavourite) context).getCartItem().getTotalDpPoints())));


                Log.i(Constant.Tag, status);
            }
        });




        viewHolder.favItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(context, "Clicked.."+itemListArrayList.get(i).getItemId(), Toast.LENGTH_SHORT).show();




                String jsonString = Utility.getStringSharedPreferences(context, "ItemFavJson");
                try {
                    JSONObject jsonObject;
//                        JSONObject jsonObject = new_added JSONObject(jsonString.toString());

                    if (jsonString.isEmpty()) {
                        jsonObject = new JSONObject();

                    }
                    else {
                        jsonObject = new JSONObject(jsonString.toString());

                    }

                    //jsonObject.put(""+getItemId(i), viewHolder.tvselectedItemQuantity.getText().toString());

//                    jsonObject.put(""+itemListArrayList.get(i).getItemId(), "1");

/*
                    if (jsonObject.get(itemListArrayList.get(i).getItemId()).equals("")) {
                        jsonObject.put(""+itemListArrayList.get(i).getItemId(), "0");


                    }
*/



                    if (jsonObject.has(itemListArrayList.get(i).getItemId())) {

                        if (jsonObject.get(itemListArrayList.get(i).getItemId()).equals("1")) {

                            jsonObject.put(""+itemListArrayList.get(i).getItemId(), "0");



                            viewHolder.favItem.setImageResource(R.drawable.ic_favorite);


                            Utility.setStringSharedPreference(context, "ItemFavJson" ,jsonObject.toString());

                        }

                        else if (jsonObject.get(itemListArrayList.get(i).getItemId()).equals("0")) {
                            jsonObject.put(""+itemListArrayList.get(i).getItemId(), "1");
                            viewHolder.favItem.setImageResource(R.drawable.ic_favorite_red);

                            Utility.setStringSharedPreference(context, "ItemFavJson" ,jsonObject.toString());
                        }

                    }



                    if (!jsonObject.has(itemListArrayList.get(i).getItemId())) {

                        jsonObject.put(""+itemListArrayList.get(i).getItemId(), "1");




                        Utility.setStringSharedPreference(context, "ItemFavJson" ,jsonObject.toString());

                        viewHolder.favItem.setImageResource(R.drawable.ic_favorite_red);


                    }



//                    Utility.setStringSharedPreference(context, "ItemFavJson" ,jsonObject.toString());

                } catch (JSONException e) {
                    Toast.makeText(context, "Json error"+e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


                //  Toast.makeText(context, "Json Q "+Utility.getStringSharedPreferences(context, "ItemFavJson"), Toast.LENGTH_SHORT).show();

                Log.e("fav", Utility.getStringSharedPreferences(context, "ItemFavJson"));

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemListArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivItemImage;
        private TextView tvItemName;
        private TextView tvItemNameHindi;
        private TextView tvMoqMrp;
        private TextView tvRateMargins;
        private TextView tvSelectedItemPrice;
        private TextView tvselectedItemQuantity;
        private ImageView ivMinusImage;
        private ImageView ivPlusImage;


        private TextView tvDpPoint;

        private ImageView favItem;




        public ViewHolder(View view) {
            super(view);
            ivItemImage = (ImageView) view.findViewById(R.id.item_row_item_logo_iv);
            tvItemName = (TextView) view.findViewById(R.id.itemlist_item_name);
            tvItemNameHindi = (TextView) view.findViewById(R.id.itemlist_item_name_hindi);
            tvMoqMrp = (TextView) view.findViewById(R.id.moq_mrp_tv);
            tvRateMargins = (TextView) view.findViewById(R.id.cost_margins_tv);
            tvSelectedItemPrice = (TextView) view.findViewById(R.id.item_list_row_total_cost_tv);
            tvselectedItemQuantity = (TextView) view.findViewById(R.id.item_row_quantity_tv);
            ivMinusImage = (ImageView) view.findViewById(R.id.item_row_minus_icon);
            ivPlusImage = (ImageView) view.findViewById(R.id.item_row_plus_icon);



            tvDpPoint = (TextView) view.findViewById(R.id.dp_point);

            favItem = (ImageView) view.findViewById(R.id.item_fav);


        }
    }


}
