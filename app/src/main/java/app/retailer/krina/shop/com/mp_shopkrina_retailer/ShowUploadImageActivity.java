package app.retailer.krina.shop.com.mp_shopkrina_retailer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class ShowUploadImageActivity extends AppCompatActivity {
Button uploadimage;
    private static final int CAMERA_REQUEST = 1;
    private static final int GALLERY_REQUST = 2;
    String uploadFilePath;
    Bitmap bitmap;
    boolean isProfileImage = false;
    ImageView upload_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_upload_image);
        uploadimage=(Button) findViewById(R.id.uploadimage);
        upload_image=(ImageView)findViewById(R.id.upload_image);

        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload_image.setVisibility(View.VISIBLE);
                UploadImage();
     }
        });

    }
    private void UploadImage() {

        final CharSequence[] options = {"Take Photo","Choose from Library","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ShowUploadImageActivity.this);
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            String partFilename = "UploadImage";
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            File path = new File(Environment.getExternalStorageDirectory()+ "/Voterapp");
            if (!path.exists()) path.mkdirs();
            uploadFilePath = String.format(Environment.getExternalStorageDirectory()+ "/Voterapp/"+partFilename+".jpg");
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

                        bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),
                                bitmapOptions);

                    } else {
                        bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    }
                }
                if (bitmap != null) {
                    isProfileImage = true;
                    upload_image.setImageBitmap(bitmap);
                    Toast.makeText(this, "Captured", Toast.LENGTH_LONG)
                            .show();


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
            Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                    null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();

            String selectedImagePath = cursor.getString(column_index);

            Bitmap bm;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(selectedImagePath, options);
            final int REQUIRED_SIZE = 200;
            int scale = 1;
            while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                    && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;
            options.inSampleSize = scale;
            options.inJustDecodeBounds = false;
            bm = BitmapFactory.decodeFile(selectedImagePath, options);
            uploadFilePath = SavedImages(bm);
            upload_image.setImageBitmap(bm);
            isProfileImage = true;

            System.out.println("Image Upload Gallery::"+uploadFilePath);
        }
    }
    // Code from StudyKloud
    public String SavedImages(Bitmap bm) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(Environment.getExternalStorageDirectory()+ "/Voterapp");
        myDir.mkdirs();

        String random = generateRandom(16) + System.currentTimeMillis();
        String fname = "golesir" + ".jpg";
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

            uploadFilePath = root + "/Voterapp/" + fname;
            //   new UploadImageTask().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return root + "/Voterapp/" + fname;

    }
    public String generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return new String(digits);
    }
}
