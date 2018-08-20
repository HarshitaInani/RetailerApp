package app.retailer.krina.shop.com.mp_shopkrina_retailer.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.Constant;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.HomeActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.NotificationFragListAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.NotificationListBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.databinding.NotificationFragBinding;

/**
 * Created by Krishna on 03-01-2017.
 */

public class NotificationFrag extends Fragment {



    int rowitemImageHeight;
    int rowitemImageWidth;


    RecyclerView mNotificaitonRecyclerView;
    AsyncTask<String, Void, JSONObject> mNotificationAsyncTask;

    AlertDialog mAlertDialog;
    ArrayList<NotificationListBean> mNotificationListArrayList = new ArrayList<>();
    NotificationFragListAdapter mNotificationFragAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        NotificationFragBinding notificationFragBinding = DataBindingUtil.inflate(
                inflater, R.layout.notification_frag, container, false);
        View view = notificationFragBinding.getRoot();

        mNotificaitonRecyclerView = notificationFragBinding.fragNotificationRv;
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mNotificaitonRecyclerView.setLayoutManager(layoutManager2);

        mNotificationFragAdapter = new NotificationFragListAdapter(getActivity(), mNotificationListArrayList);
        mNotificaitonRecyclerView.setAdapter(mNotificationFragAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, getActivity().MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        mNotificationAsyncTask = new NotificationListAsyncTask().execute(mRetailerBean.getCustomerId());
    }

    @Override
    public void onPause() {
        if (mNotificationAsyncTask != null && !mNotificationAsyncTask.isCancelled())
            mNotificationAsyncTask.cancel(true);
        super.onPause();
    }




    public class NotificationListAsyncTask extends AsyncTask<String, Void, JSONObject> {
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(getActivity());
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.progress_dialog);
            ImageView la = (ImageView) mDialog.findViewById(R.id.gridprogressBar);
            la.setBackgroundResource(R.drawable.custom_progress_dialog_animation);
            animation = (AnimationDrawable) la.getBackground();
            animation.start();
            mDialog.setCancelable(false);
            mDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObjFromurl = null;
            try {
                jsonObjFromurl = new HttpUrlConnectionJSONParser().getJsonObjectFromHttpUrlConnection(Constant.BASE_URL_NOTIFICATION + "?list=" + 50 + "&page=1" + "&customerid=" + params[0], null, HttpUrlConnectionJSONParser.Http.GET);
                if (jsonObjFromurl != null) {
                    mNotificationListArrayList.clear();
                    JSONArray mJsonArray = jsonObjFromurl.getJSONArray("notificationmaster");
                    if (mJsonArray.length() > 0) {
                        for (int i = 0; i < mJsonArray.length(); i++) {
                            String Id = isNullOrEmpty(mJsonArray.getJSONObject(i), "Id");
                            String title = isNullOrEmpty(mJsonArray.getJSONObject(i), "title");
                            String Message = isNullOrEmpty(mJsonArray.getJSONObject(i), "Message");
                            String ImageUrl = isNullOrEmpty(mJsonArray.getJSONObject(i), "ImageUrl");
                            String NotificationTime = isNullOrEmpty(mJsonArray.getJSONObject(i), "NotificationTime");
                            boolean Deleted = mJsonArray.getJSONObject(i).getBoolean("Deleted");

                            mNotificationListArrayList.add(new NotificationListBean(Id, title, Message, ImageUrl, NotificationTime, Deleted));
                            publishProgress();
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Server not responding properly", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObjFromurl;
        }

        @Override
        protected void onPostExecute(JSONObject jsonArray) {
            Log.d("Notifiication", String.valueOf(mNotificationListArrayList));
            if (mNotificationListArrayList != null && jsonArray.length() > 0) {
                if (getActivity() != null) {
                    if (!mNotificationListArrayList.isEmpty()) {
                        mNotificationFragAdapter.notifyDataSetChanged();/* = new_added SearchFragListAdapter(getActivity(), mItemListArrayList, rowitemImageWidth, rowitemImageHeight, tvTotalItemPrice, tvTotalItemQty);
                        mItemListRecyclerView.setAdapter(mSearchFragAdapter);*/
                    } else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setMessage("No Notification available");
                        alertDialogBuilder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        ((HomeActivity) getActivity()).shoeToolbarIcon();
                                        getFragmentManager().popBackStack();

                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }
                }
            } else {
                Toast.makeText(getActivity(), "Improper response from server", Toast.LENGTH_SHORT).show();
            }
            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            if (getActivity() != null) {
                if (mNotificationListArrayList != null && !mNotificationListArrayList.isEmpty()) {
                    mNotificationFragAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Items are not available", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                }
            }
        }
    }

    private String isNullOrEmpty(JSONObject mJsonObject, String key) throws JSONException {
        try {
            if (mJsonObject.has(key)) {
                if (TextUtils.isNullOrEmpty(mJsonObject.getString(key))) {
                    return "";
                } else {
                    return mJsonObject.getString(key);
                }
            } else {
                Log.e("HomeFragItemListFrag", key + " is not available which should come in response");
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }



}