package com.FingerPointEngg.abdul.test001;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ApManager {
    static final String TAG = "ApManager";
    private final WifiManager mWifiManager;
    private Context context;
    //BottomNav bv;

    //boolean isHotspotOn = false;
    ApManager(Context context) {
        this.context = context;
        mWifiManager = (WifiManager) this.context.getSystemService(Context.WIFI_SERVICE);
    }

    public void showWritePermissionSettings(boolean force) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             if (force || !Settings.System.canWrite(this.context)) {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + this.context.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.context.startActivity(intent);
             }
        }

    }

    //check whether wifi hotspot on or off
    public boolean isApOn() {

        Method method = null;
        try {
            method = mWifiManager.getClass().getDeclaredMethod("isWifiApEnabled");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        method.setAccessible(true);


        try {
            return (Boolean) method.invoke(mWifiManager);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Turn wifiAp hotspot on
    public boolean turnWifiApOn() {
//        //extas
//        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.O){
//                 WifiManagerClass wifiManagerClass=new WifiManagerClass(context);
//                 wifiManagerClass.turnOnHotspot();
//        }

        WifiConfiguration wificonfiguration = null;
        try {
            Method method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            method.invoke(mWifiManager, wificonfiguration, true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Turn wifiAp hotspot off
    public boolean turnWifiApOff() {
        WifiConfiguration wificonfiguration = null;
        try {
            Method method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            method.invoke(mWifiManager, null, false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean createNewNetwork(String ssid, String password) {
        mWifiManager.setWifiEnabled(false); // turn off Wifi
        if (isApOn()) {
            //turnWifiApOff();
        } else {
            turnWifiApOn();

        }
// creating new wifi configuration
        WifiConfiguration wifiCon = new WifiConfiguration();
        wifiCon.SSID = "\""+ssid+"\"";
        wifiCon.preSharedKey = "\""+password+"\"";
        wifiCon.status = WifiConfiguration.Status.ENABLED;
        wifiCon.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        wifiCon.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        wifiCon.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        wifiCon.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wifiCon.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
        wifiCon.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        wifiCon.allowedProtocols.set(WifiConfiguration.Protocol.WPA    );
        wifiCon.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        wifiCon.allowedKeyManagement.set(4);

        try {
            Method setWifiApMethod = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            return (Boolean) setWifiApMethod.invoke(mWifiManager, wifiCon, true);  // setting and turing on android wifiap with new configrations
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
}

