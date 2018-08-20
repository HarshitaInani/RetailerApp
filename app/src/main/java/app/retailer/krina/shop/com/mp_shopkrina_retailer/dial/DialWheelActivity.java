package app.retailer.krina.shop.com.mp_shopkrina_retailer.dial;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.Amitlibs.utils.ComplexPreferences;
import com.android.volley.RequestQueue;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.Constant;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.HomeActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.dial.library.LuckyItem;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.dial.library.LuckyWheelView;


public class DialWheelActivity extends AppCompatActivity {
    List<LuckyItem> data = new ArrayList<>();
    private Context mContext;
    private Activity mActivity;

    private RelativeLayout mRelativeLayout;
    private Button mButton;
    Dialog mDialog;
    AnimationDrawable animation;
    private PopupWindow mPopupWindow;
     LuckyWheelView luckyWheelView;
    String Id,OrderId,Point;
    RequestQueue queue;
    Button btnPlay,btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  //No status bar on screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_dial_wheel);



        TextView mtext=(TextView)findViewById(R.id.text) ;
        mtext.setText("बधाई हो ! आपको मिले है "+1+" फ्री डायल ");

        Intent intent = getIntent();
         Id = intent.getStringExtra("Id");
         OrderId = intent.getStringExtra("OrderId");
         Point = intent.getStringExtra("Point");
        System.out.println("OrderId:::"+OrderId);
        // Get the application context
        mContext = getApplicationContext();

        // Get the activity
        mActivity = DialWheelActivity.this;

        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);
       luckyWheelView = (LuckyWheelView) findViewById(R.id.luckyWheel);

        LuckyItem luckyItem1 = new LuckyItem();
        luckyItem1.text = "10";
        luckyItem1.icon = R.drawable.prize;
        luckyItem1.color = Color.parseColor("#8F1BDF");
        data.add(luckyItem1);

        LuckyItem luckyItem2 = new LuckyItem();
        luckyItem2.text = "20";
        luckyItem2.icon = R.drawable.prize;
        luckyItem2.color = Color.parseColor("#00C9F9");
        data.add(luckyItem2);

        LuckyItem luckyItem3 = new LuckyItem();
        luckyItem3.text = "30";
        luckyItem3.icon = R.drawable.prize;
        luckyItem3.color = Color.parseColor("#FF8800");
        data.add(luckyItem3);

        //////////////////
        LuckyItem luckyItem4 = new LuckyItem();
        luckyItem4.text = "40";
        luckyItem4.icon = R.drawable.prize;
        luckyItem4.color = Color.parseColor("#FF3552");
        data.add(luckyItem4);

        LuckyItem luckyItem5 = new LuckyItem();
        luckyItem5.text = "50";
        luckyItem5.icon = R.drawable.prize;
        luckyItem5.color = Color.parseColor("#EFCEB9");
        data.add(luckyItem5);

        LuckyItem luckyItem6 = new LuckyItem();
        luckyItem6.text = "60";
        luckyItem6.icon = R.drawable.prize;
        luckyItem6.color = Color.parseColor("#8F1BDF");
        data.add(luckyItem6);
        //////////////////

        //////////////////////
        LuckyItem luckyItem7 = new LuckyItem();
        luckyItem7.text = "70";
        luckyItem7.icon = R.drawable.prize;
        luckyItem7.color = Color.parseColor("#00C9F9");
        data.add(luckyItem7);

        LuckyItem luckyItem8 = new LuckyItem();
        luckyItem8.text = "80";
        luckyItem8.icon = R.drawable.prize;
        luckyItem8.color = Color.parseColor("#FF8800");
        data.add(luckyItem8);


        LuckyItem luckyItem9 = new LuckyItem();
        luckyItem9.text = "90";
        luckyItem9.icon = R.drawable.prize;
        luckyItem9.color = Color.parseColor("#FF3552");
        data.add(luckyItem9);
        ////////////////////////

        LuckyItem luckyItem10 = new LuckyItem();
        luckyItem10.text = "100";
        luckyItem10.icon = R.drawable.prize;
        luckyItem10.color = Color.parseColor("#EFCEB9");
        data.add(luckyItem10);

        LuckyItem luckyItem11 = new LuckyItem();
        luckyItem11.text = "200";
        luckyItem11.icon = R.drawable.prize;
        luckyItem11.color = Color.parseColor("#8F1BDF");
        data.add(luckyItem11);

        LuckyItem luckyItem12 = new LuckyItem();
        luckyItem12.text = "300";
        luckyItem12.icon = R.drawable.prize;
        luckyItem12.color = Color.parseColor("#00C9F9");
        data.add(luckyItem12);

        /////////////////////

        luckyWheelView.setData(data);
        luckyWheelView.setRound(getRandomRound());

        btnPlay=(Button)findViewById(R.id.play);
        btnSkip=(Button)findViewById(R.id.skip);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAqueryDialExpired();
                btnPlay.setEnabled(false);
                btnSkip.setEnabled(false);
            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();

            }
        });
        luckyWheelView.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {
                String number = null;
                System.out.println("Index::"+index);
                if(index==0)
                {
                    number="10";
                    loadPhoto(R.drawable.sk_icon,number);

                }else if(index==1)
                {
                    number="10";
                    loadPhoto(R.drawable.sk_icon,number);

                }

                else if(index==2)
                {
                    number="20";
                    loadPhoto(R.drawable.sk_icon,number);
                }
                else if(index==3)
                {
                    number="30";
                    loadPhoto(R.drawable.sk_icon,number);
                }else if(index==4)
                {
                    number="40";
                    loadPhoto(R.drawable.sk_icon,number);
                }else if(index==5)
                {
                    number="50";
                    loadPhoto(R.drawable.sk_icon,number);
                }else if(index==6)
                {
                    number="60";
                    loadPhoto(R.drawable.sk_icon,number);
                }else if(index==7)
                {
                    number="70";
                    loadPhoto(R.drawable.sk_icon,number);
                }else if(index==8)
                {
                    number="80";
                    loadPhoto(R.drawable.sk_icon,number);
                }else if(index==9)
                {
                    number="90";
                    loadPhoto(R.drawable.sk_icon,number);
                }else if(index==10)
                {
                    number="100";
                    loadPhoto(R.drawable.sk_icon,number);
                }
              //  Toast.makeText(getApplicationContext(), number, Toast.LENGTH_SHORT).show();

              // callPostDialPoint();

            }
        });
    }



    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(DialWheelActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }

    private void callPostDialPoint() {
        showLoading();
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(DialWheelActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        JSONObject jsonObject2 = new JSONObject();

        try {

            jsonObject2.put("Id", Id);

        } catch (Exception e) {

        }

       String url=Constant.BASE_URL+"DialPoint?Id="+Id;
        new AQuery(getApplicationContext()).put(url, jsonObject2, JSONObject.class, new AjaxCallback<JSONObject>() {


            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {

                System.out.println("Json::"+json.toString());
                if (json == null) {

                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }

                    Toast.makeText(DialWheelActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DialWheelActivity.this, "Get point add to your wallet", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DialWheelActivity.this, HomeActivity.class));
                    DialWheelActivity.this.finish();


                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }


                    // Toast.makeText(BeatOrderActivity.this, json.toString(), Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


    private int getRandomIndex() {
        Random rand = new Random();
        return rand.nextInt(data.size() - 1) + 0;
    }

    private int getRandomRound() {
        Random rand = new Random();
        return rand.nextInt(10) + 15;
    }

    private void loadPhoto(int img,String number) {
        final Dialog dialog = new Dialog(DialWheelActivity.this);
        dialog.setContentView(R.layout.custom);
        dialog.setCancelable(false);
        Button dialogButton = (Button) dialog.findViewById(R.id.ok);
        TextView tvPoint = (TextView) dialog.findViewById(R.id.text);
        String numberColor = " <i><font color=#000000>Congratulation you get </font> </i><font color=#0000FF>"+number+"</font> <i><font color=#000000> free point</font></i>";
        // tvPoint.setText("You Got "+ Html.fromHtml(numberColor)+" Point");
        tvPoint.setText(Html.fromHtml(numberColor));
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the popup window
               callPostDialPoint();
                btnPlay.setEnabled(true);
                btnSkip.setEnabled(true);
                dialog.dismiss();
            }
        });
        dialog.show();

    }


    public void callAqueryDialExpired() {

        String url=Constant.BASE_URL+"DialPoint?Id="+Id;
        System.out.println("UrlDial::"+url);
        showLoading();
        new AQuery(getApplicationContext()).ajax(url, null, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {


                System.out.println("JsonData"+json);
                if (json==null) {

                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }

                    Toast.makeText(DialWheelActivity.this, "Please Try again", Toast.LENGTH_SHORT).show();


                } else {

                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }


                    try {
                        JSONObject  jsonObject = new JSONObject(json.toString());
                        String Message = jsonObject.getString("Message");
                        if(Message.trim().equalsIgnoreCase("Dial Available"))
                        {
                            System.out.println("Point::"+Point);
                            int point=Integer.parseInt(Point)/10;
                          //  double point= Double.parseDouble(Point)/10;
                            System.out.println("Point Indexing::"+point);
                            int index =  point;
                            luckyWheelView.startLuckyWheelWithTargetIndex(index);

                        }else{
                            Toast.makeText(DialWheelActivity.this, "सॉरी , आपका डायल उपयोग न करने की वजह से expire  हो गया हे !!", Toast.LENGTH_SHORT).show();

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


            }
        });


    }

    public void showLoading() {
        mDialog = new Dialog(DialWheelActivity.this);
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

  /*  @Override
    public void onBackPressed() {
        startActivity(new_added Intent(DialWheelActivity.this ,BeatOrderActivity.class));
        DialWheelActivity.this.finish();
    }
*/
}
