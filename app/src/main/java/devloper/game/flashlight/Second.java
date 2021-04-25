package devloper.game.flashlight;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.security.Policy;

//TODO APPIN BASLADII APP-COMPACT ACTIVITY
public class Second extends AppCompatActivity {

//    TODO Batery statsu canli

    private BatteryReciever mBatteryReciever = new BatteryReciever();
    private IntentFilter mIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

     Camera.Parameters params;
      Camera camera;

      private MediaPlayer mediaPlayer;

    private CheckBox chcMusOf;
    MediaPlayer music;
    private TextView bateryLevel;
    private static final String TAG = "Second Activity";
    private static final int REQUEST_CODE = 1;
    private ImageView imageFlashlight;
    private ImageView volume, imageFrontLight;
    AdView mAdView;
    private static final int CAMERA_REQUEST = 50;
    private boolean flashLightStatus = false;
    private String ol = "%";
    private BroadcastReceiver bateryLen = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            bateryLevel.setText(String.valueOf(level) + ol);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        verifyPermmission();

        final boolean control = false;


        volume = findViewById(R.id.volume);


        bateryLevel = findViewById(R.id.bateryStatus);

        this.registerReceiver(this.bateryLen, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        imageFlashlight = findViewById(R.id.imageFlashlight);
        imageFrontLight = findViewById(R.id.imageFrontLight);
        music = MediaPlayer.create(this, R.raw.oppp);

        final boolean hasCameraFlash = getPackageManager().
                hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        boolean isEnabled = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;

        imageFlashlight.setEnabled(isEnabled);

//TODO Musiqin kesilmesi---kilik zamani bas veren msuqisi hadisesinin kesmesi

        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (hasCameraFlash) {

                        if (control == true) {

                            musicOn();

                        } else
                            musicOff();

                    } else {
                        Toast.makeText(Second.this, "Error Music....",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception m) {

                }
            }
        });



//      TODO   SOS METODNUNJN SET OLUNMASI



        imageFrontLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (hasCameraFlash) {
                        if (flashLightStatus)
                            flashLightFrontOff();
                        else
                            flashLightFrontOn();
                    } else {
                        Toast.makeText(Second.this, "No flash available on your device",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception c) {

                }

            }
        });


        imageFlashlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (hasCameraFlash) {
                        if (flashLightStatus)
                            flashLightOff();
                        else
                            flashLightOn();
                    } else {
                        Toast.makeText(Second.this, "No flash available on your device",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception c) {

                }
            }
        });
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }


    //    TODO ARXA KAMERANI ISMLEMSI UCUN OLAN HISSE///////......
    private void flashLightOn() {
        CameraManager cameraManager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        }

        try {
            String cameraId = "1";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cameraId = cameraManager.getCameraIdList()[0];
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cameraManager.setTorchMode(cameraId, true);
            }
            flashLightStatus = true;
            music.start();
            imageFlashlight.setImageResource(R.drawable.switch_onnn);

        } catch (CameraAccessException e) {
        }
    }

    private void flashLightOff() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cameraManager.setTorchMode(cameraId, false);
            }
            flashLightStatus = false;
            music.start();
            imageFlashlight.setImageResource(R.drawable.switch_offff);
        } catch (CameraAccessException e) {
        }
    }

    //    TODO ON CAMERANIN ACILMASI UCUN OLAN HISSE;.......
    private void flashLightFrontOn() {
        CameraManager cameraManager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        }

        try {
            String cameraId = "1";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cameraId = cameraManager.getCameraIdList()[1];
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, true);
            }
            flashLightStatus = true;
            music.start();
            imageFrontLight.setImageResource(R.drawable.switch_onnn);

        } catch (CameraAccessException e) {
        }
    }



    private void flashLightFrontOff() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[1];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cameraManager.setTorchMode(cameraId, false);
            }
            flashLightStatus = false;
            music.start();
            imageFrontLight.setImageResource(R.drawable.switch_offff);
        } catch (CameraAccessException e) {
        }
    }


//    TODO SOS METODUNNUN YAZILISi


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        verifyPermmission();
        switch (requestCode) {
            case CAMERA_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imageFlashlight.setEnabled(true);
                    imageFrontLight.setEnabled(true);
                } else {
                    Toast.makeText(Second.this, "Permission Denied for the Camera", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void verifyPermmission() {
        Log.d(TAG, "verifyPermmission: asking user of permission");
        String[] permissions = {Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getApplicationContext(),
                        permissions[0]) == PackageManager.PERMISSION_GRANTED) {

            setupViewePager();


        } else {
            ActivityCompat.requestPermissions(Second.this, permissions, REQUEST_CODE);

        }

    }


    private void setupViewePager() {


//      TODO  BURRDA ICAZE ALINIR WIEW PAGER ROL OYNAYIR


    }


//    TODO TOP OF THE NOTFICATION HISESSI....;


    public void musicOn() {

        music.start();
        volume.setImageResource(R.drawable.volume_on);

    }

    public void musicOff() {

        music.stop();
        volume.setImageResource(R.drawable.volume_off);

    }




    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBatteryReciever,mIntentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(mBatteryReciever);
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        music.release();
    }
}