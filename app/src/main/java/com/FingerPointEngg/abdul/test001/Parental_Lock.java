package com.FingerPointEngg.abdul.test001;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.System.out;

public class Parental_Lock extends AppCompatActivity {

    Button submit_button;
    CheckBox access;
    EditText secret_lock;
    ProgressDialog progressDialog;
    static String secret_g,p_user;
    ButtonDetails db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parental__lock);

        db = new ButtonDetails(getApplicationContext());

        submit_button = findViewById(R.id.submit_lock);
        access = findViewById(R.id.grant_lock_access);
        secret_lock = findViewById(R.id.secret_to_lock);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(access.isChecked()){
                    secret_g=secret_lock.getText().toString();
                    if(secret_g.length()==10){
                        p_user=db.getUsername();
                        progressDialog = ProgressDialog.show(Parental_Lock.this, "Wait a moment..", "Requesting Parental Lock", true, true);
                        Lock_Device_Query reset = new Lock_Device_Query();
                        reset.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } else{
                        Toast.makeText(getApplicationContext(),"Invalid SecretKey",Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getApplicationContext(),"Agree to lock/unlock your device",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }



    @SuppressLint("StaticFieldLeak")
    class Lock_Device_Query extends AsyncTask<String,String,String> {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onPostExecute(String jsonRead) {
            //Toast.makeText(Reset_Device.this,jsonRead,Toast.LENGTH_SHORT).show();

            super.onPostExecute(jsonRead);
            String status="",state="";
            try {
                JSONObject json = new JSONObject(jsonRead);
                status = json.getString("status");
                state = json.getString("state");
                //Toast.makeText(getApplicationContext(),err,Toast.LENGTH_SHORT).show();
            } catch (JSONException e){
                e.printStackTrace();
            }

            progressDialog.dismiss();
            if(status.equals("success")&&state.equals("true")){
                Toast.makeText(Parental_Lock.this,"Parental Lock Enabled Successfully",Toast.LENGTH_SHORT).show();
                finish();
            } else if(status.equals("success")&&state.equals("false")){
                Toast.makeText(Parental_Lock.this,"Parental Lock Disabled Successfully",Toast.LENGTH_SHORT).show();
                finish();
            } else {
                AlertDialog.Builder builder=new AlertDialog.Builder(Parental_Lock.this);
                builder.setCancelable(false);
                builder.setTitle("Reset Failed");
                builder.setMessage("Due to Connection Failure/Wrong credentials provided. Please Retry later.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setIcon(getResources().getDrawable(R.drawable.ic_add ));
                builder.show();
            }

        }


        @Override
        protected String doInBackground(String... strings) {

            try {
                String data = URLEncoder.encode("uname", "UTF-8")
                        + "=" + URLEncoder.encode(p_user, "UTF-8");

                data += "&" + URLEncoder.encode("secret", "UTF-8")
                        + "=" + URLEncoder.encode(secret_g, "UTF-8");

                URL url = new URL("https://www.fpelabs.com/Android_App/ioT/setParentalControl.php");
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
                return "{\"status\":\"failed\"}";
            }

        }
    }
}
