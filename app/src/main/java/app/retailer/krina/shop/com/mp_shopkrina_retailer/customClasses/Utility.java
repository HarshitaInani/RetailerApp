package app.retailer.krina.shop.com.mp_shopkrina_retailer.customClasses;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;*/


public class Utility {

	public static Context appContext;
	private static String PREFERENCE="relybee";
	private static String DIALCOUNT="dialcount";
	private static int MAX_IMAGE_DIMENSION = 720;

	// for username string preferences
	public static void setStringSharedPreference(Context context, String name,String value) {

		appContext = context;
		SharedPreferences settings = context.getSharedPreferences(PREFERENCE, 0);
		SharedPreferences.Editor editor = settings.edit();
		// editor.clear();
		editor.putString(name, value);
		editor.commit();

	}

	public static boolean emailValidator(String email)
	{

		Pattern pattern;
		Matcher matcher;
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();

	}


	public static void setIntSharedPreference(Context context, String name, int value) {

		appContext = context;
		SharedPreferences settings = context.getSharedPreferences(DIALCOUNT, 0);
		SharedPreferences.Editor editor = settings.edit();
		// editor.clear();
		editor.putInt(name, value);
		editor.commit();

	}

	public static void showSettingsAlert(final Context context){

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

		// Setting Dialog Title
		alertDialog.setTitle("GPS is settings");

		// Setting Dialog Message
		alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

		// Setting Icon to Dialog
		//alertDialog.setIcon(R.drawable.delete);

		// On pressing Settings button
		alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				context.startActivity(intent);
			}
		});

		// on pressing cancel button
		alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		// Showing Alert Message
		alertDialog.show();

	}


/*
	public static boolean isLocationOn(final Context context){

		LocationManager locationManager = (LocationManager) appContext.getSystemService(Context.LOCATION_SERVICE);
		boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (isGPSEnabled) {
			return false;
		}else{
			if(Build.VERSION.SDK_INT > 18 ){
	//			final Dialog dd=MyDialog.iPhone("Allow to access your current location", context);
	//			((MyTextView)dd.findViewById(R.id.yes)).setOnClickListener(new_added View.OnClickListener() {
					@Override
					public void onClick(View v) {
	//					dd.dismiss();
						context.startActivity(new_added Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
					}
				});
			}
			return true;
		}

	}
*/

	public static String comparetwodates(String setdate){


		try{
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = formatter.parse(setdate);
			int month=calendar.get(Calendar.MONTH)+1;
			String str2 = ""+calendar.get(Calendar.DAY_OF_MONTH)+"/"+(month)+"/"+(calendar.get(Calendar.YEAR));
			Date date2 = formatter.parse(str2);

			if (date2.after(date1))
			{
//                datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
				return "Past Date Selected";
//                System.out.println("date2 is Greater than my date1");
			}
		}catch (ParseException e1){
			e1.printStackTrace();
		}
		return setdate;
	}


	public static String getStringSharedPreferences(Context context, String name) {
		SharedPreferences settings = context.getSharedPreferences(PREFERENCE, 0);
		return settings.getString(name, "");
	}

	public static int getIntSharedPreferences(Context context, String name) {
		SharedPreferences settings = context.getSharedPreferences(DIALCOUNT, 0);
		return settings.getInt(name, 0);
	}
	
	
	// for username string preferences
	public static void setIntegerSharedPreference(Context context, String name,	int value) {
		appContext = context;
		SharedPreferences settings = context.getSharedPreferences(PREFERENCE, 0);
		SharedPreferences.Editor editor = settings.edit();
		// editor.clear();
		editor.putInt(name, value);
		editor.commit();
	}
	
	//Drawable
		public static void setDrawableSharedPreference(Context context, String name,int value) {
			appContext = context;
			SharedPreferences settings = context.getSharedPreferences(PREFERENCE, 0);
			SharedPreferences.Editor editor = settings.edit();
			// editor.clear();
			editor.putInt(name, value);
			editor.commit();
		}

	
	public static int getIntegerSharedPreferences(Context context, String name) {
		SharedPreferences settings = context.getSharedPreferences(PREFERENCE, 0);
		return settings.getInt(name, 0);
	}

	public static void setSharedPreferenceBoolean(Context context, String name,	boolean value) {
		appContext = context;
		SharedPreferences settings = context.getSharedPreferences(PREFERENCE, 0);
		SharedPreferences.Editor editor = settings.edit();
		// editor.clear();
		editor.putBoolean(name, value);
		editor.commit();
	}

	public static boolean getSharedPreferencesBoolean(Context context,String name) {
		SharedPreferences settings = context.getSharedPreferences(PREFERENCE, 0);
		return settings.getBoolean(name, false);
	}

	public static Bitmap decodeSampledBitmapFromResource(String path,int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,	reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	/*public static Drawable getImageFromURL(String photoDomain) {
		Drawable drawable = null;
		try {
			DefaultHttpClient httpClient = new_added DefaultHttpClient();
			HttpGet request = new_added HttpGet(photoDomain.trim());
			HttpResponse response = httpClient.execute(request);
			InputStream is = response.getEntity().getContent();
			drawable = Drawable.createFromStream(is, "src");
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return drawable;
	}

	public static String postParamsAndfindJSON(String url,
			ArrayList<NameValuePair> params) {
		String result = "";

		System.out.println("URL comes in jsonparser class is:  " + url);
		try {
			int TIMEOUT_MILLISEC = 100000; // = 10 seconds
			HttpParams httpParams = new_added BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,
					TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);

			HttpClient httpClient = new_added DefaultHttpClient();
			HttpPost httpPost = new_added HttpPost(url);
			httpPost.setEntity(new_added UrlEncodedFormEntity(params));
			// httpGet.setURI(new_added URI(url));

			HttpResponse httpResponse = httpClient.execute(httpPost);
			InputStream is = httpResponse.getEntity().getContent();
			BufferedReader reader = new_added BufferedReader(new_added InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new_added StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");

			}

			is.close();
			result = sb.toString();

		} catch (Exception e) {
			System.out.println("exception in jsonparser class ........");
			e.printStackTrace();
			return null;
		}
		return result;
	}

*/	public static Bitmap DownloadImageDirect(String imageUrl) {
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String encodeTobase64(Bitmap image) {
		Bitmap immagex = image;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

		// String imageEncoded = Base64Coder.encodeTobase64(image);

		// Log.d("LOOK", imageEncoded);
		return imageEncoded;
	}

	public static void alertBoxShow(Context context, int msg) {
		// set dialog for user's current location set for searching theaters.
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle("Alert");
		dialog.setMessage(msg);
		dialog.setPositiveButton(" Ok ", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialogInterface.cancel();

			}
		});
		dialog.setCancelable(false);
		dialog.show();
	}

    public static void ShowStringAlertWithMessage(Context context, String alerttitle,
                                                  String locationvalidation) {
 		// Assign the alert builder , this can not be assign in Click events
 		AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
 		builder.setCancelable(false);
 		builder.setMessage(locationvalidation);
 		builder.setTitle(alerttitle);
 		// Set behavior of negative button
 		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

 			@Override
			public void onClick(DialogInterface dialog, int which) {
 				// Cancel the dialog
 				dialog.cancel();
 			}
 		});
 		AlertDialog alert = builder.create();
 		alert.show();
 	}
    
    /*public static void ShowStringAlert2WithMessage(final Context context, int alerttitle,
 			int locationvalidation) {
 		// Assign the alert builder , this can not be assign in Click events
 		AlertDialog.Builder builder = new_added AlertDialog.Builder(context,AlertDialog.THEME_HOLO_LIGHT);
 		builder.setCancelable(false);
 		builder.setMessage(locationvalidation);
 		builder.setTitle(alerttitle);
 		// Set behavior of negative button
 		builder.setPositiveButton("GET STARTED", new_added DialogInterface.OnClickListener() {

 			@Override
			public void onClick(DialogInterface dialog, int which) {
 				// Cancel the dialog
 				dialog.cancel();
 				//((HomeActivity) context).moveToNextActivity("GET STARTED");
 				//Intent intent=new_added Intent(context,GetStartedActivity.class);
 			}
 		});
 		builder.setNegativeButton("FAQ", new_added DialogInterface.OnClickListener() {

 			@Override
			public void onClick(DialogInterface dialog, int which) {
 				// Cancel the dialog
 				dialog.cancel();
 				//((HomeActivity) context).moveToNextActivity("FAQ");
 			}
 		});
 		
 		AlertDialog alert = builder.create();
 		alert.show();
 	}*/
	
/*
	public static String findJSONFromUrl(String url) {
		String result = "";

		System.out.println("URL comes in jsonparser class is:  " + url);
		try {
			int TIMEOUT_MILLISEC = 100000; // = 10 seconds
			HttpParams httpParams = new_added BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,
					TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpClient httpClient = new_added DefaultHttpClient();
			HttpGet httpGet = new_added HttpGet(url);
			// httpGet.setURI(new_added URI(url));

			HttpResponse httpResponse = httpClient.execute(httpGet);
			InputStream is = httpResponse.getEntity().getContent();
			BufferedReader reader = new_added BufferedReader(new_added InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new_added StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");

			}

			is.close();
			result = sb.toString();
			System.out.println("result  in jsonparser class ........" + result);

		} catch (Exception e) {
			System.out.println("exception in jsonparser class ........");
			e.printStackTrace();
			return null;
		}
		return result;
	}
*/

	public static Bitmap getBitmap(String url) {
		Bitmap imageBitmap = null;
		try {
			URL aURL = new URL(url);
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			try {
				imageBitmap = BitmapFactory
						.decodeStream(new FlushedInputStream(is));
			} catch (OutOfMemoryError error) {
				error.printStackTrace();
				System.out.println("exception in get bitma putility");
			}

			bis.close();
			is.close();
			final int IMAGE_MAX_SIZE = 50;
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			int scale = 1;
			while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) > IMAGE_MAX_SIZE) {
				scale++;
			}
			if (scale > 1) {
				scale--;
				// scale to max possible inSampleSize that still yields an image
				// larger than target
				o = new BitmapFactory.Options();
				o.inSampleSize = scale;
				// b = BitmapFactory.decodeStream(in, null, o);

				// resize to desired dimensions
				int height = imageBitmap.getHeight();
				int width = imageBitmap.getWidth();

				double y = Math.sqrt(IMAGE_MAX_SIZE
						/ (((double) width) / height));
				double x = (y / height) * width;

				Bitmap scaledBitmap = Bitmap.createScaledBitmap(imageBitmap,
						(int) x, (int) y, true);
				imageBitmap.recycle();
				imageBitmap = scaledBitmap;

				System.gc();
			} else {
				// b = BitmapFactory.decodeStream(in);
			}

		} catch (OutOfMemoryError error) {
			error.printStackTrace();
			System.out.println("exception in get bitma putility");
		} catch (Exception e) {
			System.out.println("exception in get bitma putility");
			e.printStackTrace();
		}
		return imageBitmap;
	}

	static class FlushedInputStream extends FilterInputStream {
		public FlushedInputStream(InputStream inputStream) {
			super(inputStream);
		}
	}

	public static byte[] scaleImage(Context context, Uri photoUri)
			throws IOException {
		InputStream is = context.getContentResolver().openInputStream(photoUri);
		BitmapFactory.Options dbo = new BitmapFactory.Options();
		dbo.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(is, null, dbo);
		is.close();

		int rotatedWidth, rotatedHeight;
		int orientation = 0;// getOrientation(context, photoUri);

		if (orientation == 90 || orientation == 270) {
			rotatedWidth = dbo.outHeight;
			rotatedHeight = dbo.outWidth;
		} else {
			rotatedWidth = dbo.outWidth;
			rotatedHeight = dbo.outHeight;
		}

		Bitmap srcBitmap;
		is = context.getContentResolver().openInputStream(photoUri);
		if (rotatedWidth > MAX_IMAGE_DIMENSION
				|| rotatedHeight > MAX_IMAGE_DIMENSION) {
			float widthRatio = ((float) rotatedWidth)
					/ ((float) MAX_IMAGE_DIMENSION);
			float heightRatio = ((float) rotatedHeight)
					/ ((float) MAX_IMAGE_DIMENSION);
			float maxRatio = Math.max(widthRatio, heightRatio);

			// Create the bitmap from file
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = (int) maxRatio;
			srcBitmap = BitmapFactory.decodeStream(is, null, options);
		} else {
			srcBitmap = BitmapFactory.decodeStream(is);
		}
		is.close();

		/*
		 * if the orientation is not 0 (or -1, which means we don't know), we
		 * have to do a rotation.
		 */
		if (orientation > 0) {
			Matrix matrix = new Matrix();
			matrix.postRotate(orientation);

			srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0,
					srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		/*
		 * if (type.equals("image/png")) {
		 * srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); } else if
		 * (type.equals("image/jpg") || type.equals("image/jpeg")) {
		 * srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); }
		 */
		byte[] bMapArray = baos.toByteArray();
		baos.close();
		return bMapArray;
	}

	static int mMaxWidth, mMaxHeight;

	@SuppressWarnings("deprecation")
	public static Bitmap loadResizedImage(Context mContext, final File imageFile) {
		WindowManager windowManager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		mMaxWidth = display.getWidth();
		mMaxHeight = display.getHeight();

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
		int scale = calculateInSampleSize(options, mMaxWidth, mMaxHeight);
		while (options.outWidth / scale > mMaxWidth
				|| options.outHeight / scale > mMaxHeight) {
			scale++;
		}
		Bitmap bitmap = null;
		Bitmap scaledBitmap = null;
		if (scale > 1) {
			try {
				scale--;
				options = new BitmapFactory.Options();
				options.inSampleSize = scale;
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				options.inPurgeable = true;
				options.inTempStorage = new byte[16 * 100];
				bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(),
						options);
				if (bitmap == null) {
					return null;
				}

				// resize to desired dimensions
				int width = bitmap.getWidth();
				int height = bitmap.getHeight();
				double newWidth;
				double newHeight;
				if ((double) width / mMaxWidth < (double) height / mMaxHeight) {
					newHeight = mMaxHeight;
					newWidth = (newHeight / height) * width;
				} else {
					newWidth = mMaxWidth;
					newHeight = (newWidth / width) * height;
				}

				scaledBitmap = Bitmap.createScaledBitmap(bitmap,
						Math.round((float) newWidth),
						Math.round((float) newHeight), true);
				bitmap.recycle();
				bitmap = scaledBitmap;
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
				bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
			}
			System.gc();
		} else {
			bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
		}

		return rotateImage(bitmap, imageFile);
	}

	public static Bitmap rotateImage(final Bitmap bitmap,
			final File fileWithExifInfo) {
		if (bitmap == null) {
			return null;
		}
		Bitmap rotatedBitmap = bitmap;
		int orientation = 0;
		try {
			orientation = getImageOrientation(fileWithExifInfo
					.getAbsolutePath());
			if (orientation != 0) {
				Matrix matrix = new Matrix();
				matrix.postRotate(orientation, (float) bitmap.getWidth() / 2,
						(float) bitmap.getHeight() / 2);
				rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
						bitmap.getWidth(), bitmap.getHeight(), matrix, true);
				bitmap.recycle();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rotatedBitmap;
	}

	public static int getImageOrientation(final String file) throws IOException {
		ExifInterface exif = new ExifInterface(file);
		int orientation = exif
				.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
		switch (orientation) {
		case ExifInterface.ORIENTATION_NORMAL:
			return 0;
		case ExifInterface.ORIENTATION_ROTATE_90:
			return 90;
		case ExifInterface.ORIENTATION_ROTATE_180:
			return 180;
		case ExifInterface.ORIENTATION_ROTATE_270:
			return 270;
		default:
			return 0;
		}
	}
	public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
             
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              //Read byte from input stream
                 
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
               
              //Write byte from output stream
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
	static Locale myLocale;
	public static void setLocale(String lang,Context context) {
		 
	    myLocale = new Locale(lang);
	    Resources res = context.getResources();
	    DisplayMetrics dm = res.getDisplayMetrics();
	    Configuration conf = res.getConfiguration();
	    conf.locale = myLocale;
	    res.updateConfiguration(conf, dm);

	}
    public static boolean isConnectingToInternet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
/*    public static String multiPart(String url,MultipartEntity entity) {
        String result="";
        HttpClient httpclient;

        try {
            httpclient = new_added DefaultHttpClient();
            HttpPost httppost = new_added HttpPost(url);

            httppost.setEntity(entity);
            System.out.println("cvsc" + httppost);
            HttpResponse response = httpclient.execute(httppost);
            result = EntityUtils.toString(response.getEntity());
            System.out.println("Given Result to photo  " + result);

        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;

    }*/




	public static String getDate(String dateTime) {


		String inputPattern = "yyyy-MM-dd HH:mm:ss";

//        String outputPattern = "dd/MM/yyyy h:mm a";

		String outputPattern = "dd/MM/yyyy";


		SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
		SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

		Date date = null;
		String str = null;

		try {
			date = inputFormat.parse(dateTime);
			str = outputFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}


		return  str;
	}


	public static String getTime(String dateTime) {


		String inputPattern = "yyyy-MM-dd HH:mm:ss";

//        String outputPattern = "dd/MM/yyyy h:mm a";

//		String outputPattern = "dd/MM/yyyy";

		String outputPattern = "HH:mm:ss";


		SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
		SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

		Date date = null;
		String str = null;

		try {
			date = inputFormat.parse(dateTime);
			str = outputFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}


		return  str;
	}



}// final class ends here

