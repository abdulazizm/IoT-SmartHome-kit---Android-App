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

public class Add_secondary_user extends AppCompatActivity {

    Button submit_button;
    CheckBox access;
    EditText s_user;
    ProgressDialog progressDialog;
    static String s_user_f,p_user;
    ButtonDetails db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_secondary_user);

        db = new ButtonDetails(getApplicationContext());

        submit_button = findViewById(R.id.submit_sec_user);
        access = findViewById(R.id.grant_access);
        s_user = findViewById(R.id.secondary_user);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(access.isChecked()){
                    s_user_f=s_user.getText().toString();
                    if(s_user_f.length()>=4){
                        p_user=db.getUsername();
                        progressDialog = ProgressDialog.show(Add_secondary_user.this, "Wait a moment..", "Adding Secondary User", true, true);
                        AddUser add = new AddUser();
                        add.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } else{
                        Toast.makeText(getApplicationContext(),"Invalid Username",Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getApplicationContext(),"Please Grant Access",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    @SuppressLint("StaticFieldLeak")
    class AddUser extends AsyncTask<String,String,String> {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onPostExecute(String jsonRead) {
            //Toast.makeText(getApplicationContext(),jsonRead,Toast.LENGTH_SHORT).show();

            super.onPostExecute(jsonRead);
            String status="",s_user_q="";
            try {
                JSONObject json = new JSONObject(jsonRead);
                status = json.getString("status");
                s_user_q = json.getString("s_user");
                //Toast.makeText(getApplicationContext(),err,Toast.LENGTH_SHORT).show();
            } catch (JSONException e){
                e.printStackTrace();
            }

            progressDialog.dismiss();
            if(status.equals("success")&&s_user_q.equals("added")){
                Toast.makeText(getApplicationContext(),"Added Successfully",Toast.LENGTH_SHORT).show();
                finish();
            } else {
                AlertDialog.Builder builder=new AlertDialog.Builder(Add_secondary_user.this);
                builder.setCancelable(false);
                builder.setTitle("Adding Secondary User Failed");
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
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                String data = URLEncoder.encode("p_user", "UTF-8")
                        + "=" + URLEncoder.encode(p_user, "UTF-8");

                data += "&" + URLEncoder.encode("s_user", "UTF-8")
                        + "=" + URLEncoder.encode(s_user_f, "UTF-8");

                URL url = new URL("https://www.fpelabs.com/Android_App/ioT/addSecUser.php");
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
