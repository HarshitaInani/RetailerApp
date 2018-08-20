package app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses;

/**
 * Created by Krishna on 02-01-2017.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by john on 7/7/16.
 */
public class RupeeTextView extends TextView {

    Context context;

    public RupeeTextView(Context context) {
        super(context);
        this.context = context;
    }

    public RupeeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    public RupeeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        String formatedString = null;
        try {
            // The comma in the format specifier does the trick
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            formatter.applyPattern("#,##,##,##,###");
            formatedString = String.format("₹" + formatter.format(Double.parseDouble(text.toString())));  // adds rupee symbol and thousand seperater
        } catch (NumberFormatException e) {
            Log.d("Rupee Class", "Rupee TextView NumberFormatException");
            e.printStackTrace();
        }
        super.setText(formatedString, type);
    }
}
