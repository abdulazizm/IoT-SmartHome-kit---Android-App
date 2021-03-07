package com.FingerPointEngg.abdul.test001;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;


public class BottomNav extends AppCompatActivity implements  BottomNavigationView.OnNavigationItemSelectedListener {

    private FrameLayout frameLayout;
    Boolean islabelupdated=false;
    private Boolean exit = false;
//    String url = "http://192.168.43.50/";
    HomeFragment t = new HomeFragment();

    final Handler handler = new Handler();


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);


        frameLayout = findViewById(R.id.fragmentContainer);
        BottomNavigationView navigation = findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(this);
        ApManager apManager=new ApManager(getApplicationContext());

        //to on hotspot when only when it is off
        if(!apManager.isApOn()){
            GoToHotspotPage();
        }
        if(!Settings.System.canWrite(this)){

          apManager.showWritePermissionSettings(true);
          apManager.turnWifiApOn();

        }


//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            if (!Settings.System.canWrite(this.getApplicationContext())) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
//                intent.setData(Uri.parse("package:" + this.getPackageName()));
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//
//        }

        CreateNewWifiApNetwork();
//        if(!(apManager.getHotspotStatus())){
//            GoToHotspotPage();
//        }

        //CheckHotspotConfig();

        //to call home frag first
        load(t);
    }

    public void GoToHotspotPage() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE) ;
////            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
////            String ssid = wifiInfo.getSSID();
////            Toast.makeText(this,ssid,Toast.LENGTH_SHORT).show();
//
//            WifiManager wifimanager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//            Method[] methods = wifimanager.getClass().getDeclaredMethods();
//            //Method m: methods;
//            //WifiConfiguration config = (WifiConfiguration)m.invoke(wifimanager);
//            //Toast.makeText(this,config.SSID + config.preSharedKey,Toast.LENGTH_SHORT).show();
//            for (Method m: methods) {
//
//                if (m.getName().equals("getWifiApConfiguration")) {
//                    try {
//                        WifiConfiguration config = (WifiConfiguration)m.invoke(wifimanager);
//                        Toast.makeText(this,config.SSID + config.preSharedKey,Toast.LENGTH_SHORT).show();
//
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    } catch (InvocationTargetException e) {
//                        e.printStackTrace();
//                    }
//
//                    // here, the "config" variable holds the info, your SSID is in
//                    // config.SSID
//                }
//            }
//
//
//        }



//        Method method = mWifiManager.getClass().getDeclaredMethod("isWifiApEnabled");
//        method.setAccessible(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            //startActivity(new Intent(Settings.ACTION_APN_SETTINGS);
            //startActivityForResult(new Intent(A, 0));

            final Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            final ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.TetherSettings");
            intent.setComponent(cn);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity( intent);

//            if ((Boolean) method.invoke(mWifiManager)) {
//                Toast.makeText(this, "HotspotOn", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "HotspotOff", Toast.LENGTH_SHORT).show();
//                //Code
//
//
//            }

            Toast.makeText(this,"Set Hotspot Name and Password as \"fpe,12345678\"",Toast.LENGTH_LONG).show();
            Toast.makeText(this,"Set Hotspot Name and Password as \"fpe,12345678\"",Toast.LENGTH_LONG).show();

        //return (Boolean) method.invoke(mWifiManager);






    }}

    boolean isLocationPermissionEnable(){
        if(ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},2);
            return false;


        }
        return true;

    }

    public boolean load(Fragment fragment) {
        if (fragment == null) {
            return false;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment f = null;
        switch (menuItem.getItemId()) {

            case R.id.navigation_home:
                f = new HomeFragment();
                break;
            case R.id.navigation_timer:
                f = new TimerFragment();
                break;
            case R.id.navigation_stats:
                f = new StatsFragment();
                break;
            case R.id.navigation_settings:
                f = new SettingsFragment();
                break;


        }
        return load(f);
    }


    public void CreateNewWifiApNetwork() {

        ApManager ap = new ApManager(this.getApplicationContext());
        ap.createNewNetwork("fpe", "10100001");
    }


    @Override
    public void onBackPressed(){
        if(exit){
            finish();
        }
        else{
            Toast.makeText(this,"Press back again to Exit",Toast.LENGTH_SHORT).show();
            exit=true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit=false;
                }
            },3*1000);
        }
    }
}
