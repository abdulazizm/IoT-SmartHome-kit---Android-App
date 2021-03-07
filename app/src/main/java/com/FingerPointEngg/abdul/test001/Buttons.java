package com.FingerPointEngg.abdul.test001;



import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;


class Buttons{
    int id;
    private ButtonDetails bd;
    private String urlSpec;

    Buttons(Context ctx,int id,String urlSpec){
        bd=new ButtonDetails(ctx);
        this.id=id;
        this.urlSpec=urlSpec;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    boolean getTimerOnMode(){
        return bd.getTimerOnMode(id);
    }


    boolean getTimerOffMode(){
        return bd.getTimerOffMode(id);
    }

    boolean getScheduleOnOff(){ return bd.getScheduleOnOff(id); }

    String getScheduleMode(){
        return bd.getScheduleMode(id);
    }

    String getOnTime(){
        return bd.getOnTimer(id);
    }

    String getOffTime(){
        return bd.getOffTimer(id);
    }

    String getStartTime(){
        return bd.getScheduleStart(id);
    }

    String getStopTime(){
        return bd.getScheduleStop(id);
    }

    void doToggle(int i){

        String ip = "192.168.43.50";
        String deviceName = "";
        String url_rl = "http://"+ip+"/"+deviceName;
        SelectTask task = new SelectTask(url_rl);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);;
    }

    boolean getCurrentState(){
        return bd.getCurrentState(id);
    }


}
