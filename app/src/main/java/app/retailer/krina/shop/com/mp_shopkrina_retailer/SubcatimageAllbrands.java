package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.protinus.trupay.trupayminilib.activity.TrupayMiniLib.context;

/**
 * Created by User on 24-08-2018.
 */

public class SubcatimageAllbrands extends AppCompatActivity {
    ImageView ivItemImage;
    private TextView tvItemName;
    String name, image;
    Bundle bundle = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcatimage);
        ivItemImage = (ImageView) findViewById(R.id.item_row_item_logo_iv);
        tvItemName = (TextView) findViewById(R.id.itemlist_item_name);
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        image = bundle.getString("image");
        Picasso.with(context).load(image).resize(400, 400).into(ivItemImage);
        // Picasso.with(context).load(R.drawable.aata_and_cereals ).resize(300, 300).into( ivItemImage);
        tvItemName.setText(name);


    }
}
