package com.FingerPointEngg.abdul.test001;

import android.app.Activity;
import android.os.AsyncTask;

import org.json.JSONException;


public class StatusTask extends AsyncTask<Void, Void, String> {

    private String mUrl;


    private OnDataSendToActivity dataSendToActivity;

    StatusTask(String url,Activity activity){
        dataSendToActivity = (OnDataSendToActivity)activity;
        mUrl = url;

    }

    @Override
    protected String doInBackground(Void... params) {
        return JsonHttp.makeHttpRequest(mUrl);
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            dataSendToActivity.sendData(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onPostExecute(result);
         //fragment.sendData(result);
    }
}