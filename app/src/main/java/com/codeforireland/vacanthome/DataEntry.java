package com.codeforireland.vacanthome;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


public class DataEntry extends AppCompatActivity {

    private Spinner propertyType;
    private EditText houseNo;
    private EditText streetName;
    private EditText townName;
    private Spinner county;
    private EditText eircode;
    private TextView geoLocateText;
    private Button geoLocate;
    private Button library;
    private Button takePhoto;
    private Spinner vacantTime;
    private ToggleButton saleRentSign;
    private ToggleButton grassOvergrown;
    private ToggleButton roofDamaged;
    private ToggleButton windowsBoarded;
    private ToggleButton paintPeeling;
    private ToggleButton propertyActivity;
    private ToggleButton wasteBins;
    private ToggleButton accessToProperty;
    private Spinner aboutYou;
    private EditText message;
    private CheckBox canContact;
    private EditText editName;
    private EditText editEmail;
    private EditText editPhone;
    private ToggleButton tandc;
    private String image64;
    private Button buttonSubmit;
    private ImageView imageView;
    private String lat = "";
    private String lon = "";

    
    private static String urlAddress = "put-your-api-endpoint-here";

    private GoogleApiClient client;

    protected LocationManager locationManager;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_entry);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        locationManager = (LocationManager)
        getSystemService(Context.LOCATION_SERVICE);

        propertyType = (Spinner) findViewById(R.id.propertyType);
        houseNo = (EditText) findViewById(R.id.houseNo);
        streetName = (EditText) findViewById(R.id.streetName);
        townName = (EditText) findViewById(R.id.townName);
        county = (Spinner) findViewById(R.id.county);
        eircode = (EditText) findViewById(R.id.eircode);
        geoLocateText = (TextView) findViewById(R.id.geoLocateText);
        geoLocate = (Button) findViewById(R.id.geoLocate);
        takePhoto = (Button) findViewById(R.id.takePhoto);
        library = (Button) findViewById(R.id.library);
        vacantTime = (Spinner) findViewById(R.id.vacantTime);
        saleRentSign = (ToggleButton) findViewById(R.id.saleRentSign);
        grassOvergrown = (ToggleButton) findViewById(R.id.grassOvergrown);
        roofDamaged = (ToggleButton) findViewById(R.id.roofDamaged);
        windowsBoarded = (ToggleButton) findViewById(R.id.windowsBoarded);
        paintPeeling = (ToggleButton) findViewById(R.id.paintPeeling);
        propertyActivity = (ToggleButton) findViewById(R.id.propertyActivity);
        wasteBins = (ToggleButton) findViewById(R.id.wasteBins);
        accessToProperty = (ToggleButton) findViewById(R.id.accessToProperty);
        aboutYou = (Spinner) findViewById(R.id.aboutYou);
        //message = (EditText) findViewById(R.id.message);
        //canContact = (CheckBox) findViewById(R.id.canContact);
        //editName = (EditText) findViewById(R.id.editName);
        //editEmail = (EditText) findViewById(R.id.editEmail);
        //editPhone = (EditText) findViewById(R.id.editPhone);
        tandc = (ToggleButton) findViewById(R.id.tandc);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        imageView = (ImageView) findViewById(R.id.picture);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                takePhoto();
            }
        });
        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                pickImage();
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                buttonSubmit.getBackground().setAlpha(128);

                postData();
            }
        });


        if (ContextCompat.checkSelfPermission(DataEntry.this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(DataEntry.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(DataEntry.this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 2);
        }


        geoLocate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (ContextCompat.checkSelfPermission(DataEntry.this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(DataEntry.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                                PackageManager.PERMISSION_GRANTED) {
                    LocationListener locationListener = new MyLocationListener();
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);


                    double _lat = Math.round(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude() * 100000d) / 100000d;
                    double _lon = Math.round(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude() * 100000d) / 100000d;

                    DecimalFormat df = new DecimalFormat("#.#####");

                    lat = String.valueOf(df.format(_lat));
                    lon = String.valueOf(df.format(_lon));

                    geoLocateText.setText("Geolocation: "+lat+", "+lon);

                }

            }
        });



    }

    protected void postData() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                OutputStream os = null;
                InputStream resp = null;
                HttpURLConnection conn = null;
                try {
                    //constants
                    URL url = new URL(urlAddress);
                    JSONObject json = new JSONObject();
                    json.put("propertyType", propertyType.getSelectedItem().toString());
                    json.put("houseNo", houseNo.getText());
                    json.put("streetName", streetName.getText());
                    json.put("townName", townName.getText());
                    json.put("county", county.getSelectedItem().toString());
                    json.put("eircode", eircode.getText());
                    json.put("geoLocateText", geoLocateText.getText());
                    json.put("vacantTime", vacantTime.getSelectedItem().toString());
                    json.put("saleRentSign", saleRentSign.isChecked());
                    json.put("grassOvergrown", grassOvergrown.isChecked());
                    json.put("roofDamaged", roofDamaged.isChecked());
                    json.put("windowsBoarded", windowsBoarded.isChecked());
                    json.put("paintPeeling", paintPeeling.isChecked());
                    json.put("propertyActivity", propertyActivity.isChecked());
                    json.put("wasteBins", wasteBins.isChecked());
                    json.put("accessToProperty", accessToProperty.isChecked());
                    json.put("aboutYou", aboutYou.getSelectedItem().toString());
                    json.put("message", message.getText());
                    json.put("canContact", canContact.isChecked());
                    json.put("editName", editName.getText());
                    json.put("editEmail", editEmail.getText());
                    json.put("editPhone", editPhone.getText());
                    json.put("tandc", tandc.isChecked());
                    json.put("image", image64);
                    String message = json.toString();

                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout( 10000 /*milliseconds*/ );
                    conn.setConnectTimeout( 150000 /* milliseconds */ );
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setFixedLengthStreamingMode(message.getBytes().length);

                    //make some HTTP header nicety
                    conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                    conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                    //open
                    conn.connect();

                    //setup send
                    os = new BufferedOutputStream(conn.getOutputStream());
                    os.write(message.getBytes());
                    //clean up
                    os.flush();

                    //response
                    resp = conn.getInputStream();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    //clean up
                    try {
                        if  (os != null) {
                            os.close();
                        }

                        if  (resp != null) {
                            resp.close();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    conn.disconnect();
                    thankYouMessage();

                }

            }
        }).start();
    }
    protected void thankYouMessage() {
        Intent i = new Intent();
        setResult(RESULT_OK, i);

        finish();
    }
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }
    private static final int TAKE_PICTURE = 1;
    private static final int SELECT_PICTURE = 2;
    private Uri imageUri;

    public void takePhoto() {
        verifyStoragePermissions(DataEntry.this);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(),  "vacant-home-"+sdf.format(timestamp)+".jpg");
        //Uri img = Uri.fromFile(photo);

        Uri img = FileProvider.getUriForFile(this, "com.codeforireland.vacanthome.provider", photo);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, img);
        imageUri = img;
        startActivityForResult(intent, TAKE_PICTURE);
    }
    public void pickImage() {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, SELECT_PICTURE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    setImage(imageUri);
                }
            case SELECT_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        setImage(data.getData());
                    }
                }
        }
    }

    private void setImage(Uri selectedImage) {
        getContentResolver().notifyChange(selectedImage, null);
        ContentResolver cr = getContentResolver();
        Bitmap bitmap;
        try {
            bitmap = android.provider.MediaStore.Images.Media
                    .getBitmap(cr, selectedImage);
            imageView.setImageBitmap(bitmap);
            image64 = encodeTobase64(bitmap);
            System.out.println(image64);
        } catch (Exception e) {
            Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                    .show();
            System.out.println("Camera"+ e.toString());
        }
    }
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
        return imageEncoded;
    }

    protected void onStop() {
        super.onStop();
        buttonSubmit.getBackground().setAlpha(255);
    }
}