package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;

public class SettingActivity extends AppCompatActivity {


    SharedPreferences sharedpreferences;


    CheckBox checkBox;
    Context context;

    String multiLanguage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);



        checkBox = (CheckBox) findViewById(R.id.checkbox_hindi);

        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


        context = this;

        multiLanguage = Utility.getStringSharedPreferences(context, "MultiLaguage");

        ((TextView) findViewById(R.id.title_toolbar)).setText("Setting");

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
        setSupportActionBar(toolbar);


        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingActivity.this.finish();
            }
        });

       /* ((ImageView) toolbar.findViewById(R.id.nav_home_icon_iv)).setOnClickListener(new_added View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingActivity.this.finish();
            }
        });
*/



     /*   if (sharedpreferences.getString("language", "").equals("h")) {
            checkBox.setChecked(true);
        }
*/
        if (multiLanguage.equals("m")) {
            checkBox.setChecked(true);
        }



        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putString("language", "h");

                    editor.commit();

                    sharedpreferences = getSharedPreferences("MyPrefs",
                            Context.MODE_PRIVATE);



                    Utility.setStringSharedPreference(context, "MultiLaguage", "m");
                    //    Toast.makeText(SettingActivity.this, sharedpreferences.getString("language", ""), Toast.LENGTH_SHORT).show();


                } else {

                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putString("language", "e");
                    editor.commit();

                    Utility.setStringSharedPreference(context, "MultiLaguage", "s");

                }
            }
        });


        ((Button) findViewById(R.id.back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, HomeActivity.class));
                SettingActivity.this.finish();
            }
        });


//        String token = FirebaseInstanceId.getInstance().getToken();
////
//        Toast.makeText(SettingActivity.this, ""+token, Toast.LENGTH_SHORT).show();



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SettingActivity.this, HomeActivity.class));
        SettingActivity.this.finish();


    }


}
