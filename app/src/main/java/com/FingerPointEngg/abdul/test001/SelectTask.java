package com.FingerPointEngg.abdul.test001;



import android.os.AsyncTask;


public class SelectTask extends AsyncTask<Void, Void, String> {

    private String mUrl;

    SelectTask(String url){
        super();
        mUrl = url;
    }

    @Override
    protected String doInBackground(Void... params) {
        return JsonHttp.makeHttpRequest(mUrl);
    }

    @Override
    protected void onPostExecute(String result) {

    }
}
