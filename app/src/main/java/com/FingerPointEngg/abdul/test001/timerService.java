package com.FingerPointEngg.abdul.test001;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class timerService extends Service {

    ButtonDetails db;
    String name;
    CountDownTimer c;
    long l=0;
    Calendar calendar;
    SimpleDateFormat D;
    Date d;
//    boolean TimerOnMode = false;
//    boolean TimerOffMode = false;
//    boolean ScheduleMode = false;
//    boolean OnServicingScheduler = false;
    static int DestroyCount=0;
    static String lastId;

    static Boolean getTimerOnMode;
    static Boolean getTimerOffMode;
    static Boolean getCurrentState;
    static Boolean getScheduleOnOff;
    static String getOnTimer;
    static String getOffTimer;
    static String getScheduleMode;
    static String getScheduleStart;
    static String getScheduleStop;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        //Toast.makeText(this,"Start Service", Toast.LENGTH_SHORT).show();

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {

                checkRealTime();

                handler.postDelayed(this,10000);
            }
        }, 5000);

        return START_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void checkRealTime() {

        db=new ButtonDetails(getApplicationContext());
        //db.setName("service_state","true");

//        final List<Buttons> buttons=new ArrayList<>();
//        buttons.add(new Buttons(getApplicationContext(),1,"bulb_1"));
//        buttons.add(new Buttons(getApplicationContext(),2,"fan"));
//        buttons.add(new Buttons(getApplicationContext(),3,"tube_light"));
//        buttons.add(new Buttons(getApplicationContext(),4,"socket"));

        //Toast.makeText(this,"Checking", Toast.LENGTH_SHORT).show();

        calendar=Calendar.getInstance();
        D=new SimpleDateFormat("hh/mm/aa");
        d= calendar.getTime();
        String time=D.format(d);


        lastId=db.getLastId();

        for(int i=0;i<=Integer.parseInt(lastId);i++) {

            getTimerOnMode = db.getTimerOnMode(i);
            getTimerOffMode = db.getTimerOffMode(i);
            getCurrentState = db.getCurrentState(i);
            getScheduleOnOff = db.getScheduleOnOff(i);
            getOnTimer = db.getOnTimer(i);
            getOffTimer = db.getOffTimer(i);
            getScheduleMode = db.getScheduleMode(i);
            getScheduleStart = db.getScheduleStart(i);
            getScheduleStop = db.getScheduleStop(i);


            if (getTimerOnMode) {
                if (getOnTimer != null) {
                    if (time.equalsIgnoreCase(getOnTimer)) {

                        Toast.makeText(getApplicationContext(), "Switch Turned On", Toast.LENGTH_SHORT).show();
                        if (!getCurrentState) doToggle(i);
                        db.setTimerOnMode(i, false);
                        db.setOnTimer(i,"-/-/-");
                    }
                }
            }


            if (getTimerOffMode) {

                if (getOnTimer != null) {
                    if (time.equalsIgnoreCase(getOffTimer)) {
                        Toast.makeText(getApplicationContext(), "Switch Turned Off", Toast.LENGTH_SHORT).show();

                        if (getCurrentState) {
                            doToggle(i);
                        }
                        db.setTimerOffMode(i, false);
                        db.setOffTimer(i,"-/-/-");
                    }
                }
            }


            //for schedule
            if (getScheduleOnOff) {

                if (getScheduleMode != null) {
                    if (getScheduleMode.equals("SCHEDULE_ON") || getScheduleMode.equals("ON_SERVICED")) {

                        //Toast.makeText(getApplicationContext(), "vante", Toast.LENGTH_SHORT).show();
                        if (getScheduleStart != null) {
                            //Toast.makeText(getApplicationContext(), "Start ON null ila", Toast.LENGTH_SHORT).show();
                            if (time.equalsIgnoreCase(getScheduleStart)) {
                                if (!getScheduleMode.equals("ON_SERVICED")) {
                                    Toast.makeText(getApplicationContext(), "Scheduler Switch ON", Toast.LENGTH_SHORT).show();
                                    if (!getCurrentState) {
                                        doToggle(i);
                                    }
                                }
                                db.setScheduleStart(i,"-/-/-");
                                db.setScheduleMode(i, "ON_SERVICED");

                            }
                        }

                        if (getScheduleStop != null) {
                            if (time.equalsIgnoreCase(getScheduleStop)) {
                                Toast.makeText(getApplicationContext(),"Scheduler Switch Off",Toast.LENGTH_SHORT).show();

                                if (getCurrentState) {
                                    doToggle(i);
                                }
                                db.setScheduleStop(i,"-/-/-");
                                db.setScheduleMode(i, "SCHEDULE_OFF");

                            }
                        }

                    }
                }
            }
        }

        if(!getTimerOnMode&&!getTimerOffMode&&!getScheduleOnOff){

            DestroyCount++;
            if(DestroyCount>=Integer.parseInt(lastId)) {
                onDestroy();
            }

        }
//        if(!TimerOnMode&&!TimerOffMode&&!ScheduleMode&&!OnServicingScheduler){
//            DestroyCount++;
//            if(DestroyCount==5) {
//                onDestroy();
//            }
//
//        }

//                String ide=buttons.get(2).id;
//                String st=db.getName(ide)+" "+db.getTimerOnMode(ide)+" "+db.getTimerOffMode(ide)+" "+db.getCurrentState(ide);
//                Toast.makeText(getApplicationContext(),st,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    void doToggle(int i){

        db=new ButtonDetails(getApplicationContext());
        if(db.getiot(i)==0){
            String ip = db.getIP(i);
            String deviceUrl = db.getDeviceUrl(i);
            String url_rl = "http://"+ip+"/"+deviceUrl;
            //Toast.makeText(getApplicationContext(),""+url_rl,Toast.LENGTH_SHORT).show();
            SelectTask task = new SelectTask(url_rl);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else if(db.getiot(i)==1){

            IotStatusUpdateTask task = new IotStatusUpdateTask(db.getUsername(),i);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            Toast.makeText(getApplicationContext(),"IoT Switch Toggle",Toast.LENGTH_SHORT).show();
        }


    }
}
