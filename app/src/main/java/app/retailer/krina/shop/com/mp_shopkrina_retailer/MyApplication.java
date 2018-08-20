package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.protinus.trupay.trupayminilib.activity.TrupayMiniLib;

/**
 * Created by user on 5/26/2017.
 */
public class MyApplication extends MultiDexApplication {
    private static Context appContext;

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        appContext = this;

       TrupayMiniLib.init(this);

    }
}
