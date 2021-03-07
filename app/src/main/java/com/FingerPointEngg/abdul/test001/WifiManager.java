package com.FingerPointEngg.abdul.test001;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;


class WifiManagerClass {

private WifiManager.LocalOnlyHotspotReservation mReservation;
Context ctx;

 public WifiManagerClass(Context ctx) {
                this.ctx=ctx;
        }

       BottomNav nav=new BottomNav();


        @RequiresApi(api = Build.VERSION_CODES.O)
     void turnOnHotspot() {

    if (!nav.isLocationPermissionEnable()){

      }

        WifiManager manager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
     if (manager!=null){
    manager.startLocalOnlyHotspot(new WifiManager.LocalOnlyHotspotCallback() {

        @Override
        public void onStarted(WifiManager.LocalOnlyHotspotReservation reservation) {
            super.onStarted(reservation);

            mReservation = reservation;
        }

        @Override
        public void onStopped() {
            super.onStopped();
        }

        @Override
        public void onFailed(int reason) {
            super.onFailed(reason);
        }
    }, new Handler());
}}

@RequiresApi(api = Build.VERSION_CODES.O)
    void turnOffHotspot() {

    if (mReservation != null) {
        mReservation.close();
    }
}


        }

