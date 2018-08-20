package app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters;

import android.content.Context;
import android.content.SharedPreferences;
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

import app.retailer.krina.shop.com.mp_shopkrina_retailer.CartActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.Constant;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.CartItemBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;

/**
 * Created by Krishna on 03-01-2017.
 */

public class CartItemListAdapter extends RecyclerView.Adapter<CartItemListAdapter.ViewHolder> {
    private CartItemBean cartItemBean;
    private Context context;
    private int ivWidth;
    private int ivHeight;
    private TextView tvTotalItemPrice;
    private TextView tvTotalItemQty;
    private TextView tvGrandTotal;
    private TextView tvDialTotal;
    private TextView tvDeliveryCharges;

    int  dial=0;
    int  count=0;
    private TextView tvDpGrandTotal;

    boolean isFlag=false;
    int a=0,b=0,c=0,d=0,e=0,f=0,g=0,h=0,i=0,j=0,k=0;


    int deliveryCharges;

    int totalDp;

    int  gainPoint;
    String language;



    public CartItemListAdapter(Context context, CartItemBean cartItemBean, int ivWidth, int ivHeight, TextView tvTotalItemPrice, TextView tvTotalItemQty, TextView tvGrandTotal, TextView tvDeliveryCharges, int deliveryCharges , TextView tvDpGrandTotal, TextView tvDialTotal) {
        this.cartItemBean = cartItemBean;
        this.context = context;
        this.ivWidth = ivWidth;
        this.ivHeight = ivHeight;
        this.tvTotalItemPrice = tvTotalItemPrice;
        this.tvTotalItemQty = tvTotalItemQty;
        this.tvGrandTotal = tvGrandTotal;
        this.tvDialTotal = tvDialTotal;
        this.tvDeliveryCharges = tvDeliveryCharges;
        this.deliveryCharges = deliveryCharges;
        this.tvDpGrandTotal = tvDpGrandTotal;


    }

    @Override
    public CartItemListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_check_out_row, viewGroup, false);
        return new CartItemListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartItemListAdapter.ViewHolder viewHolder, final int i) {

        //  - button false
      /*  count =   Utility.getIntSharedPreferences(context,"DialCount");
        if(count==0)
        {

        }else
        {
            viewHolder.ivMinusImage.setEnabled(false);
        }*/

        viewHolder.tvItemName.setText(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemname());


      /*
        Typeface normalTypeface = FontCache.get("fonts/krdv011.ttf", context);
        viewHolder.tvItemNameHindi.setTypeface(normalTypeface);
        viewHolder.tvItemNameHindi.setText(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getHindiName());

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


        viewHolder.tvItemNameHindi.setText(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getHindiName());









        String jsonString = Utility.getStringSharedPreferences(context, "ItemFavJson");
        try {
            JSONObject jsonObject;
//                        JSONObject jsonObject = new_added JSONObject(jsonString.toString());

            if (jsonString.isEmpty()) {
                jsonObject = new JSONObject();

            } else {

                jsonObject = new JSONObject(jsonString.toString());






                if (jsonObject.has(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemId())) {

                    if (jsonObject.get(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemId()).equals("1")) {

                        //   jsonObject.put(""+itemListArrayList.get(i).getItemId(), "0");




                        viewHolder.favItem.setImageResource(R.drawable.ic_favorite_red);


                        //   Utility.setStringSharedPreference(context, "ItemFavJson" ,jsonObject.toString());

                    }

                    else if (jsonObject.get(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemId()).equals("0")) {
                        //    jsonObject.put(""+itemListArrayList.get(i).getItemId(), "1");
                        viewHolder.favItem.setImageResource(R.drawable.ic_favorite);

                        //    Utility.setStringSharedPreference(context, "ItemFavJson" ,jsonObject.toString());
                    }

                }



                if (!jsonObject.has(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemId())) {

//                    jsonObject.put(""+itemListArrayList.get(i).getItemId(), "1");

                    //      Utility.setStringSharedPreference(context, "ItemFavJson" ,jsonObject.toString());

                    viewHolder.favItem.setImageResource(R.drawable.ic_favorite);


                }
            }
        }catch (Exception e) {

        }





        if (cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getDreamPoint().isEmpty()) {

            viewHolder.tvDpPoint.setVisibility(View.GONE);

        }


        totalDp = Integer.parseInt(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getDreamPoint()) * cartItemBean.getmCartItemInfos().get(i).getQty();

        //   viewHolder.tvDpPoint.setText(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getDreamPoint());

        viewHolder.tvDpPoint.setText("Dream Point "+totalDp);





        if (!TextUtils.isNullOrEmpty(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getLogoUrl()))
            Picasso.with(context).load(Constant.BASE_URL_Images1 + cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemNumber() + ".jpg").resize(ivWidth, ivHeight).into(viewHolder.ivItemImage);

       // Picasso.with(context).load(Constant.BASE_URL_Images + "images/itemimages/" + cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getLogoUrl() + ".jpg").resize(ivWidth, ivHeight).into(viewHolder.ivItemImage);
        viewHolder.tvMoqMrp.setText("MOQ: " + cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getMinOrderQty() + " | MRP: " + new DecimalFormat("##.##").format(Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getPrice())));
        String text = "<font color=#FF4500>&#8377; " + new DecimalFormat("##.##").format(Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getUnitPrice())) + "</font>" + " | Margins: " + (new DecimalFormat("##.##").format((((Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getPrice()) - Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getUnitPrice())) / Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getPrice())) * 100))) + "%";
        viewHolder.tvRateMargins.setText(Html.fromHtml(text));

        viewHolder.ivMinusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemQuantity = Integer.parseInt(viewHolder.tvselectedItemQuantity.getText().toString());
                if (itemQuantity > 0) {
                    itemQuantity -= Integer.parseInt(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getMinOrderQty());
                    viewHolder.tvselectedItemQuantity.setText("" + itemQuantity);
                    String price = "<font color=#FF4500>&#8377; " + new DecimalFormat("##.##").format((itemQuantity * Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getUnitPrice())));
                    viewHolder.tvSelectedItemPrice.setText(Html.fromHtml(price));
                    String status = ((CartActivity) context).addItemInCartItemArrayList(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemId(), itemQuantity, Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getUnitPrice()), cartItemBean.getmCartItemInfos().get(i).getSelectedItem(), deliveryCharges  , Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getDreamPoint()) ,  cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getWarehouseId(), cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getCompanyId(),Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getPrice()) );

                    tvTotalItemPrice.setText("Total: " + new DecimalFormat("##.##").format(((CartActivity) context).getCartItem().getTotalPrice()));
                    DialSetUp((int) (((CartActivity) context).getCartItem().getTotalPrice() + deliveryCharges));
                    tvDpGrandTotal.setText(("Dream points total : " + new DecimalFormat("##.##").format(((CartActivity) context).getCartItem().getTotalDpPoints())));
                    tvTotalItemQty.setText("" + (int) ((CartActivity) context).getCartItem().getTotalQuantity());
                    totalDp = Integer.parseInt(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getDreamPoint()) * cartItemBean.getmCartItemInfos().get(i).getQty();
                    viewHolder.tvDpPoint.setText("Dream Point "+totalDp);
                    Log.i(Constant.Tag, status);
                    if (((CartActivity) context).getCartItem().getTotalPrice() >= 2000) {
                        deliveryCharges = 0;
                    } else {
                        deliveryCharges = 10;
                    }
                    tvGrandTotal.setText("Total: " + (int) (((CartActivity) context).getCartItem().getTotalPrice() + deliveryCharges));
                    tvDeliveryCharges.setText("Delivery Charges: " + deliveryCharges);
                    System.out.println("SaveAmount::;"+new DecimalFormat("##.##").format(((CartActivity) context).getCartItem().getSavingAmount()));
                }
            }
        });

        viewHolder.ivPlusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFlag=true;
                int itemQuantity = Integer.parseInt(viewHolder.tvselectedItemQuantity.getText().toString());
                itemQuantity += Integer.parseInt(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getMinOrderQty());
                viewHolder.tvselectedItemQuantity.setText("" + itemQuantity);
                String price = "<font color=#FF4500>&#8377; " + new DecimalFormat("##.##").format((itemQuantity * Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getUnitPrice())));
                viewHolder.tvSelectedItemPrice.setText(Html.fromHtml(price));
                String status = ((CartActivity) context).addItemInCartItemArrayList(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemId(), itemQuantity, Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getUnitPrice()), cartItemBean.getmCartItemInfos().get(i).getSelectedItem(), deliveryCharges  , Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getDreamPoint())  ,  cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getWarehouseId(), cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getCompanyId(), Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getPrice()) );

                tvTotalItemPrice.setText("Total: " + new DecimalFormat("##.##").format(((CartActivity) context).getCartItem().getTotalPrice()));
                DialSetUp((int) (((CartActivity) context).getCartItem().getTotalPrice() + deliveryCharges));

                tvDpGrandTotal.setText(("Dream points total : " + new DecimalFormat("##.##").format(((CartActivity) context).getCartItem().getTotalDpPoints())));



                tvTotalItemQty.setText("" + (int) ((CartActivity) context).getCartItem().getTotalQuantity());

                System.out.println("SaveAmount:: "+new DecimalFormat("##.##").format(((CartActivity) context).getCartItem().getSavingAmount()));

                totalDp = Integer.parseInt(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getDreamPoint()) * cartItemBean.getmCartItemInfos().get(i).getQty();

                viewHolder.tvDpPoint.setText("Dream Point "+totalDp);


                Log.i(Constant.Tag, status);

                if (((CartActivity) context).getCartItem().getTotalPrice() >= 2000) {
                    deliveryCharges = 0;
                } else {
                    deliveryCharges = 10;
                }
                tvGrandTotal.setText("Total: " + (int) (((CartActivity) context).getCartItem().getTotalPrice() + deliveryCharges));
                tvDeliveryCharges.setText("Delivery Charges: " + deliveryCharges);
            }
        });

        viewHolder.tvselectedItemQuantity.setText("" + cartItemBean.getmCartItemInfos().get(i).getQty());
        String price = "<font color=#FF4500>&#8377; " + new DecimalFormat("##.##").format((cartItemBean.getmCartItemInfos().get(i).getQty() * Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getUnitPrice())));
        viewHolder.tvSelectedItemPrice.setText(Html.fromHtml(price));

        tvTotalItemPrice.setText("Total: " + new DecimalFormat("##.##").format(((CartActivity) context).getCartItem().getTotalPrice()));


        tvDpGrandTotal.setText("Dream points total : " + (int) (((CartActivity) context).getCartItem().getTotalDpPoints()));



        tvTotalItemQty.setText("" + (int) ((CartActivity) context).getCartItem().getTotalQuantity());

        if (((CartActivity) context).getCartItem().getTotalPrice() >= 2000) {
            deliveryCharges = 0;
        } else {
            deliveryCharges = 10;
        }
        tvGrandTotal.setText("Total: " + (int) (((CartActivity) context).getCartItem().getTotalPrice() + deliveryCharges));
        tvDeliveryCharges.setText("Delivery Charges: " + deliveryCharges);

        DialSetUp((int) (((CartActivity) context).getCartItem().getTotalPrice() + deliveryCharges));








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



                    if (jsonObject.has(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemId())) {

                        if (jsonObject.get(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemId()).equals("1")) {

                            jsonObject.put(""+cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemId(), "0");


                            viewHolder.favItem.setImageResource(R.drawable.ic_favorite);


                            Utility.setStringSharedPreference(context, "ItemFavJson" ,jsonObject.toString());

                        }

                        else if (jsonObject.get(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemId()).equals("0")) {
                            jsonObject.put(""+cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemId(), "1");
                            viewHolder.favItem.setImageResource(R.drawable.ic_favorite_red);

                            Utility.setStringSharedPreference(context, "ItemFavJson" ,jsonObject.toString());
                        }

                    }



                    if (!jsonObject.has(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemId())) {

                        jsonObject.put(""+cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemId(), "1");




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
    private void DialSetUp(int totalAmount) {

        int dial= totalAmount/4000;

        if(dial==0)
        {
            SharedPreferences sharedPreferences = context.getSharedPreferences("dialcount", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.clear().commit();
        }
        // int amoount= totalAmount-5000;
        if(totalAmount >= 3000 & totalAmount <= 4000) {
            Toast.makeText(context,  R.string.left_free_dial, Toast.LENGTH_SHORT).show();
        }
        gainPoint =   Utility.getIntSharedPreferences(context,"GainPoint");
        System.out.println("GainPoint:::"+gainPoint);
        tvDpGrandTotal.setText("Dream points total : " + (int) (((CartActivity) context).getCartItem().getTotalDpPoints()+gainPoint));
        int  count =   Utility.getIntSharedPreferences(context,"DialCount");
        Log.e("Count::", String.valueOf(count));
        Log.e("Dial::", String.valueOf(dial));
       /* if(isFlag)
        {
            tvDialTotal.setText(String.valueOf(dial));
        }*/
        if(dial>=count)
        {
            dial=dial-count;
        }else
        {
          //  dial=count-dial;
            SharedPreferences sharedPreferences = context.getSharedPreferences("dialcount", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.clear().commit();
        }
        tvDialTotal.setText(String.valueOf(dial));

    }
    @Override
    public int getItemCount() {
        return cartItemBean.getmCartItemInfos().size();
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
