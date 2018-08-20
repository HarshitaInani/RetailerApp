package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.app.Dialog;
import android.content.Context;
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

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.NewsAdapter;

public class MyNews extends AppCompatActivity {

    NewsAdapter adapter;
    RecyclerView recyclerView;

    Context context;

    Dialog mDialog;
    AnimationDrawable animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_news);


        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        context = this;

        recyclerView = (RecyclerView) findViewById(R.id.recyeler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ((TextView) findViewById(R.id.title_toolbar)).setText("My News");


        Toolbar toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
        setSupportActionBar(toolbar);


        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyNews.this.finish();
            }
        });



        callNewsApi();




    }



    public void callNewsApi() {

        showLoading();
        new AQuery(MyNews.this).ajax(Constant.BASE_URL_NEWS_FEED,
                null,
                JSONArray.class,
                new AjaxCallback<JSONArray>(){


                    @Override
                    public void callback(String url, JSONArray jsonArray, AjaxStatus status) {


                        if (jsonArray != null) {


                          //  Toast.makeText(MyNews.this, ""+jsonArray.toString(), Toast.LENGTH_SHORT).show();


                            adapter = new NewsAdapter(context, jsonArray, null, jsonArray.length());
                            recyclerView.setAdapter(adapter);

                            if (mDialog.isShowing()) {
                                animation.stop();
                                mDialog.dismiss();
                            }


                        } else {

                            if (mDialog.isShowing()) {
                                animation.stop();
                                mDialog.dismiss();
                            }


                            Toast.makeText(MyNews.this, "Please try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                );
    }




    public void showLoading() {


        mDialog = new Dialog(MyNews.this);
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
