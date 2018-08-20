package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.CustomImageAdapter;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.Activitiespojo;


public class ActivitiesActivity extends AppCompatActivity {
    String[] personNameList;
    ListView listView;
    CustomImageAdapter adapter;
    ArrayList<Activitiespojo> actvitylist = new ArrayList<Activitiespojo>();
    String[] Showlist;
    Dialog mDialog;
    AnimationDrawable animation;
    int position;
    String imageTempName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        new GetList().execute();
        listView = (ListView) findViewById(R.id.listview);
        actvitylist= new ArrayList<>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Activitiespojo pojo = actvitylist.get(position);

                Intent intent = new Intent(ActivitiesActivity.this, UnderSelectActivity.class);
                intent.putExtra("Name",pojo.getDocumentName());
                startActivity(intent);
            }
        });

    }
 public class GetList extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            showLoading();
            //  progressBar.setVisibility(View.VISIBLE);
  }
   @Override
        protected JSONArray doInBackground(String... params) {

            JSONArray jsonArrayFromUrl = null;
         /*   ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(ActivitiesActivity.this, Constant.RETAILER_BEAN_PREF, getApplication().MODE_PRIVATE);
            RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);*/
            String url=Constant.BASE_URL_DOCUMENT;
            System.out.println("documenturl::::"+url);

            try {
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(url, null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayFromUrl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            ArrayList<Activitiespojo> mItemListArrayList = new ArrayList<>();

            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i  < jsonArray.length() ; i++) {

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                       String documentname = jsonObject.getString("DocumentName");
                        mItemListArrayList.add(new Activitiespojo(documentname));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
    }

                adapter = new CustomImageAdapter(ActivitiesActivity.this, mItemListArrayList);
                listView.setAdapter(adapter);
            }
            mDialog.dismiss();
        }
    }
    public void showLoading() {
        mDialog = new Dialog(ActivitiesActivity.this);
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
    public void captureImage(int pos, String imageName) {
        position = pos;
        imageTempName = imageName;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,100);
    }


    private void onCaptureImageResult(Intent data) {

        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");

        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
        Uri tempUri = getImageUri(getApplicationContext(), imageBitmap, imageTempName);
        String picturePath = getRealPathFromURI(tempUri);
        adapter.setImageInItem(position, imageBitmap, picturePath);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == 100) {
                onCaptureImageResult(data);
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage, String imageName) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, imageName, null);
        return Uri.parse(path);

    }
    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }



}
