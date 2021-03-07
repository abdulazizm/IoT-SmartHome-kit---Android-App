package com.FingerPointEngg.abdul.test001;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.System.out;

class IotStatusUpdateTask extends AsyncTask<String,String,String> {

    //p-provided, g-get
    private String uname_p;
    private Integer id_p;

    IotStatusUpdateTask(String uname_g, Integer id_g) {
        uname_p = uname_g;
        id_p = id_g;
    }

    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute("success");
        //Toast.makeText(getContext(),result,Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            String data = URLEncoder.encode("uname", "UTF-8")
                    + "=" + URLEncoder.encode(uname_p, "UTF-8");

            data += "&" + URLEncoder.encode("id", "UTF-8")
                    + "=" + URLEncoder.encode(""+id_p, "UTF-8");

            URL url = new URL("https://www.fpelabs.com/Android_App/ioT/toggleState.php");
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept-Encoding", "identity");
            urlConnection.setDoOutput(true);

            OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
            writer.write(data);
            writer.flush();
            writer.close();
            // Get the server response
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String sb = reader.readLine();
            reader.close();
            urlConnection.disconnect();
            return sb;
        } catch (Exception e) {
            out.println(e.getMessage());
        }
        return "failed";
    }
}
