package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.*;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters.PlaceAutocompleteAdapter;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.BaseCatSubCatBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.RetailerBean;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses.Utility;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.databinding.MyProfileBinding;


/**
 * Created by Krishna on 30-01-2017.
 */

public class MyProfile extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    public static final String TAG = "My_Profile_Activity";
    MyProfileBinding myProfileBinding;
    protected GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter mAdapter;
    private static final LatLngBounds BOUNDS_GREATER_INDIA = new LatLngBounds(new LatLng(23.63936, 68.14712), new LatLng(28.20453, 97.34466));
    Button updateProfileBtn;
    private static final int CAMERA_REQUEST = 1;
    private static final int GALLERY_REQUST = 2;
    ImageView imageView;
    EditText profile_name;
    EditText profile_pass;
    EditText profile_email;
    EditText profile_shopName;
    EditText profile_dob;
    EditText profile_Gst;
    TextView choose_image;
    //Image request code
    String uploadFilePath;
    boolean isProfileImage = false;
    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 1;
    //Bitmap to get image from gallery
    private Bitmap bitmap;
    //Uri to store the image uri
    private Uri filePath;
    String fname;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(MyProfile.this.getResources().getColor(R.color.colorPrimaryDark));
            }
        }
        if( ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 1);
            }
        }
        setContentView(R.layout.my_profile);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        myProfileBinding = DataBindingUtil.setContentView(this, R.layout.my_profile);
       // updateProfileBtn=(Button)findViewById(R.id.updateProfileBtn) ;
        imageView=(ImageView) findViewById(R.id.uploadreg) ;
        profile_name=(EditText)findViewById(R.id.my_profile_edt_name) ;
        profile_pass=(EditText)findViewById(R.id.my_profile_edt_pass) ;
        profile_email=(EditText)findViewById(R.id.my_profile_edt_email) ;
        profile_shopName=(EditText)findViewById(R.id.text11) ;
        profile_dob=(EditText)findViewById(R.id.text12) ;
        profile_Gst=(EditText)findViewById(R.id.gst) ;
        choose_image=(TextView)findViewById(R.id.choose_image) ;
        ComplexPreferences mRetailerBeanPref1 = ComplexPreferences.getComplexPreferences(MyProfile.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean1 = mRetailerBeanPref1.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        Picasso.with(MyProfile.this).load(Constant.BASE_URL_Images+"UploadedImages/"+mRetailerBean1.getCustomerId()+".jpg").placeholder(R.drawable.top_img_bg).resize(30, 30).error(R.drawable.top_img_bg).into(imageView);
        System.out.println("Image::"+Constant.BASE_URL_Images+"UploadedImages/"+mRetailerBean1.getCustomerId()+".jpg");



        choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showFileChooser();

                UploadImage();
            }
        });
        //Requesting storage permission
        requestStoragePermission();
      /*  updateProfileBtn.setOnClickListener(new_added View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        //System.out.println("Fname::"+fname);
        if (fname==null)
        {
            fname=mRetailerBean1.getCustomerId()+".jpg";
        }
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();
 /*       myProfileBinding.myProfileEdtBillingAdd.setOnItemClickListener(new_added AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final AutocompletePrediction item = mAdapter.getItem(position);
                final String placeId = item.getPlaceId();
                final CharSequence primaryText = item.getPrimaryText(null);

                Log.i(TAG, "Autocomplete item selected: " + primaryText);

                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(new_added ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(@NonNull PlaceBuffer places) {
                        if (!places.getStatus().isSuccess()) {
                            // Request did not complete successfully
                            Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                            places.release();
                            return;
                        }
                        // Get the Place object from the buffer.
                        final Place place = places.get(0);
                        Log.i(TAG, "Place details received: " + place.getName());

                        places.release();
                    }
                });
                Log.i(TAG, "Called getPlaceById to get Place details for"+ placeId);
            }
        });*/
       // mAdapter = new_added PlaceAutocompleteAdapter(this, mGoogleApiClient, BOUNDS_GREATER_INDIA,null);
      //  myProfileBinding.myProfileEdtBillingAdd.setAdapter(mAdapter);
/*
        myProfileBinding.myProfileEdtShippingAdd.setOnItemClickListener(new_added AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final AutocompletePrediction item = mAdapter.getItem(position);
                final String placeId = item.getPlaceId();
                final CharSequence primaryText = item.getPrimaryText(null);

                Log.i(TAG, "Autocomplete item selected: " + primaryText);

                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(new_added ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(@NonNull PlaceBuffer places) {
                        if (!places.getStatus().isSuccess()) {
                            // Request did not complete successfully
                            Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                            places.release();
                            return;
                        }
                        // Get the Place object from the buffer.
                        final Place place = places.get(0);
                        Log.i(TAG, "Place details received: " + place.getName());

                        places.release();
                    }
                });
                Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
            }
        });
        myProfileBinding.myProfileEdtShippingAdd.setAdapter(mAdapter);*/


        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyProfile.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        if (mRetailerBean != null && !mRetailerBean.getCustomerId().equalsIgnoreCase("0")) {
            myProfileBinding.myProfileEdtName.setText("" + mRetailerBean.getName());
            myProfileBinding.myProfileEdtMobile.setText("" + mRetailerBean.getMobile());
            myProfileBinding.myProfileEdtPass.setText("" + mRetailerBean.getPassword());
            myProfileBinding.myProfileEdtEmail.setText("" + mRetailerBean.getEmailid());
            myProfileBinding.myProfileEdtBillingAdd.setText("" + mRetailerBean.getBillingAddress());
            myProfileBinding.myProfileEdtShippingAdd.setText("" + mRetailerBean.getShippingAddress());
            myProfileBinding.myProfileEdtLandmark.setText("" + mRetailerBean.getLandMark());
            myProfileBinding.myProfileEdtPincode.setText("" + mRetailerBean.getZipCode());
            myProfileBinding.text11.setText("" + mRetailerBean.getShopName());
            myProfileBinding.gst.setText("" + mRetailerBean.getRefNo());
            myProfileBinding.text12.setText("" + mRetailerBean.getDob());



        } else {
            Toast.makeText(this, "You are not logged in please login first", Toast.LENGTH_SHORT).show();
            MyProfile.this.finish();
        }


        ((CheckBox) findViewById(R.id.addressCb)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String billingAddress =  ((EditText) findViewById(R.id.my_profile_edt_billing_add)).getText().toString();



                    ((EditText) findViewById(R.id.my_profile_edt_shipping_add)).setText(billingAddress);


                }


            }
        });


        ((Button) findViewById(R.id.updateProfileBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String image = getStringImage(bitmap);


                System.out.println("DecodeString:::"+image);


                uploadImage();
            }
        });





  }

    private void UploadImage() {

        final CharSequence[] options = {"Take Photo","Choose from Library","Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MyProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                } else if (options[item].equals("Choose from Library")) {
                    Intent i = new Intent(
                            Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, GALLERY_REQUST);
                }

                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Updating Profile...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, Constant.BASE_URL_SIGNUP_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();



                        CallApiResponse(s);
                       // startActivity(new_added Intent(MyProfile.this,HomeActivity.class));
                        System.out.println("Fname::"+fname+":::uploadFilePath::"+uploadFilePath);
                        if(uploadFilePath==null){

                        }else{
                           File file = new File(uploadFilePath);
                            file.delete();
                        }

                        //Showing toast message of the response
                        Toast.makeText(MyProfile.this, "Update successfully done" , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(MyProfile.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyProfile.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
               // String image = getStringImage(bitmap);
               // System.out.println("Image::"+image);
                //Getting Image Name
                String name = profile_name.getText().toString().trim();
                String pass = profile_pass.getText().toString().trim();
                String email = profile_email.getText().toString().trim();
                String dob = profile_dob.getText().toString().trim();
                String shopname = profile_shopName.getText().toString().trim();
                String gst = profile_Gst.getText().toString().trim();

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters

                params.put("Mobile", mRetailerBean.getMobile());
                params.put("Name", name);
                params.put("ShopName", shopname);
                params.put("RefNo", gst);
                params.put("Password", pass);
                params.put("DOB", dob);
                params.put("Emailid", email);
                params.put("UploadRegistration", fname);

                System.out.println("params::"+params);
                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void CallApiResponse(String s) {
        System.out.println("Response::"+s);
        try {
            JSONObject jsonObject=new JSONObject(s.toString());
            String customerId = jsonObject.getString("CustomerId");
            String customerCategoryId = isNullOrEmpty(jsonObject, "CustomerCategoryId");
            String Skcode = isNullOrEmpty(jsonObject, "Skcode");
            String ShopName = isNullOrEmpty(jsonObject, "ShopName");
            String dob = isNullOrEmpty(jsonObject, "DOB");
            String Warehouseid = isNullOrEmpty(jsonObject, "Warehouseid");
            System.out.println("WID:::"+Warehouseid);
            String Mobile = isNullOrEmpty(jsonObject, "Mobile");
            String WarehouseName = isNullOrEmpty(jsonObject, "WarehouseName");
            String Name = isNullOrEmpty(jsonObject, "Name");
            String Password = isNullOrEmpty(jsonObject, "Password");
            String Description = isNullOrEmpty(jsonObject, "Description");
            String CustomerType = isNullOrEmpty(jsonObject, "CustomerType");
            String CustomerCategoryName = isNullOrEmpty(jsonObject, "CustomerCategoryName");
            String CompanyId = isNullOrEmpty(jsonObject, "CompanyId");
            String BillingAddress = isNullOrEmpty(jsonObject, "BillingAddress");
            String TypeOfBuissness = isNullOrEmpty(jsonObject, "TypeOfBuissness");
            String ShippingAddress = isNullOrEmpty(jsonObject, "ShippingAddress");
            String LandMark = isNullOrEmpty(jsonObject, "LandMark");
            String Country = isNullOrEmpty(jsonObject, "Country");
            String State = isNullOrEmpty(jsonObject, "State");
            String Cityid = isNullOrEmpty(jsonObject, "Cityid");
            String City = isNullOrEmpty(jsonObject, "City");
            String ZipCode = isNullOrEmpty(jsonObject, "ZipCode");
            String BAGPSCoordinates = isNullOrEmpty(jsonObject, "BAGPSCoordinates");
            String SAGPSCoordinates = isNullOrEmpty(jsonObject, "SAGPSCoordinates");
            String RefNo = isNullOrEmpty(jsonObject, "RefNo");
            String OfficePhone = isNullOrEmpty(jsonObject, "OfficePhone");
            String Emailid = isNullOrEmpty(jsonObject, "Emailid");
            String Familymember = isNullOrEmpty(jsonObject, "Familymember");
            String CreatedBy = isNullOrEmpty(jsonObject, "CreatedBy");
            String LastModifiedBy = isNullOrEmpty(jsonObject, "LastModifiedBy");
            String CreatedDate = isNullOrEmpty(jsonObject, "CreatedDate");
            String UpdatedDate = isNullOrEmpty(jsonObject, "UpdatedDate");
            String imei = isNullOrEmpty(jsonObject, "imei");
            String MonthlyTurnOver = isNullOrEmpty(jsonObject, "MonthlyTurnOver");
            String ExecutiveId = isNullOrEmpty(jsonObject, "ExecutiveId");
            String SizeOfShop = isNullOrEmpty(jsonObject, "SizeOfShop");
            String Rating = isNullOrEmpty(jsonObject, "Rating");
            String ClusterId = isNullOrEmpty(jsonObject, "ClusterId");
            String Deleted = isNullOrEmpty(jsonObject, "Deleted");
            String Active = isNullOrEmpty(jsonObject, "Active");
            String GroupNotification = isNullOrEmpty(jsonObject, "GroupNotification");
            String Notifications = isNullOrEmpty(jsonObject, "Notifications");
            String Exception = isNullOrEmpty(jsonObject, "Exception");
            String DivisionId = isNullOrEmpty(jsonObject, "DivisionId");
            String Rewardspoints = isNullOrEmpty(jsonObject, "Rewardspoints");

                RetailerBean mRetailerBean = new RetailerBean(customerId, customerCategoryId, Skcode, ShopName, Warehouseid, Mobile, WarehouseName, Name, Password, Description, CustomerType, CustomerCategoryName, CompanyId, BillingAddress, TypeOfBuissness, ShippingAddress, LandMark, Country, State, Cityid, City, ZipCode, BAGPSCoordinates, SAGPSCoordinates, RefNo, OfficePhone, Emailid, Familymember, CreatedBy, LastModifiedBy, CreatedDate, UpdatedDate, imei, MonthlyTurnOver, ExecutiveId, SizeOfShop, Rating, ClusterId, Deleted, Active, GroupNotification, Notifications, Exception, DivisionId, Rewardspoints,dob);

                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyProfile.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                mRetailerBeanPref.putObject(Constant.RETAILER_BEAN_PREF_OBJ, mRetailerBean);
                mRetailerBeanPref.commit();

                ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(MyProfile.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);
                BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);


                Utility.setStringSharedPreference(MyProfile.this, "CName", Name);
                Utility.setStringSharedPreference(MyProfile.this, "CId", customerId);

                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);

            System.out.println("Cust::"+customerId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
     //   bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    //method to show file chooser
    private void showFileChooser() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, GALLERY_REQUST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyProfile.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
            RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
            fname = mRetailerBean.getCustomerId() + ".jpg";
            //String partFilename = "UploadImage";
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            File path = new File(Environment.getExternalStorageDirectory()+ "/ShopKirana");
            if (!path.exists()) path.mkdirs();
            uploadFilePath = String.format(Environment.getExternalStorageDirectory()+ "/ShopKirana/"+fname);
            System.out.println("Image upload::"+uploadFilePath);
            try {

                Uri selectedImage = Uri.parse(uploadFilePath);
                File file = new File(uploadFilePath);
                String path1 = file.getAbsolutePath();

                FileOutputStream outStream = new FileOutputStream(file);

                photo.compress(Bitmap.CompressFormat.JPEG, 50, outStream);

                outStream.flush();
                outStream.close();

                if (path1 != null) {
                    if (path1.startsWith("content")) {
                        Bitmap bitmap;
                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                        bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bitmapOptions);

                    } else {
                        bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    }
                }
                if (bitmap != null) {
                    isProfileImage = true;
                    imageView.setImageBitmap(bitmap);

                    Toast.makeText(this, "Captured", Toast.LENGTH_LONG).show();
                    uploadMultipart();

                } else {
                    Toast.makeText(this,
                            "Failed to Capture the picture. kindly Try Again:",
                            Toast.LENGTH_LONG).show();}
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (requestCode == GALLERY_REQUST && resultCode == RESULT_OK && null != data) {

            Uri selectedImageUri = data.getData();
            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = managedQuery(selectedImageUri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();

            String selectedImagePath = cursor.getString(column_index);

            Bitmap bm;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(selectedImagePath, options);
            final int REQUIRED_SIZE = 200;
            int scale = 1;
            while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE) scale *= 2;
            options.inSampleSize = scale;
            options.inJustDecodeBounds = false;
            bm = BitmapFactory.decodeFile(selectedImagePath, options);
            uploadFilePath = SavedImages(bm);
           imageView.setImageBitmap(bm);
            isProfileImage = true;
            if (isProfileImage){

                uploadMultipart();

            }
            System.out.println("Image Upload Gallery::"+uploadFilePath);
        }
    }
    // Code from StudyKloud
    public String SavedImages(Bitmap bm) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(Environment.getExternalStorageDirectory()+ "/ShopKirana");
        myDir.mkdirs();

        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyProfile.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
         fname = mRetailerBean.getCustomerId() + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            //    isChosen = true;
            //profileImg = file.getName();

            uploadFilePath = root + "/ShopKirana/" + fname;
            //   new_added UploadImageTask().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root + "/ShopKirana/" + fname;

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(this,
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }
    public void uploadMultipart() {

        //String path = getPath(filePath);


        System.out.println("Path:::"+uploadFilePath);
        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, Constant.UPLOAD_URL)
                    .addFileToUpload(uploadFilePath, "file") //Adding file
                    .addParameter("name", "image") //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

//it gives some error that is the reason i have commented it
       /*if (requestCode == CAMERA_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Now user should be able to use camera
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            }
            else {
                // Your app will not have this permission. Turn off all functions
                // that require this permission or it will force close like your
                // original question
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }*/


        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
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
                Log.e("LoginActivity", key + " is not available which should come in response");
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
