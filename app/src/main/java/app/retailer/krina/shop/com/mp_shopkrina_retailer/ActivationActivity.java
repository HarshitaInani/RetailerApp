package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Krishna on 12/27/2016.
 */

public class ActivationActivity extends AppCompatActivity {
    Button mButton;
    boolean showButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activation_activity);

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);


        mButton = ((Button) findViewById(R.id.activity_activation_btn_signin));
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // ActivationActivity.this.finish();
                startActivity(new Intent(ActivationActivity.this, LoginActivity_Nav.class));
            }
        });
        if (getIntent().getBooleanExtra("showButton", true)) {
            mButton.setVisibility(View.VISIBLE);
        } else {
            mButton.setVisibility(View.GONE);
        }
    }
}
