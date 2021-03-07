package com.FingerPointEngg.abdul.test001;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.VectorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class AddDevice extends AppCompatActivity {

    private Spinner spinner;
    private static final String[] paths = {  "iSmart A1", "iSmart A4", "Model A1", "Model A2", "Model A3", "Model A4", "Model A5", "Model B1", "Model B2", "Model B3", "Model B4", "Model B5"};

    private static final String[] device_url = {"bulb_1", "fan", "tube_light", "socket"};
    Button add_button;
    EditText secret_key;
    static Integer btns=0;
    static Integer btn_id=0;
    static int default_res_id=0;
    HomeFragment hf;
    ButtonDetails db;
    static String ip="192.168.43.45";
    static Long id_model;
    static String ModelName,username,idparam;
    static String SecretKey_Model;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_device);

        db = new ButtonDetails(getApplicationContext());

        spinner = findViewById(R.id.model);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        add_button = findViewById(R.id.add_button);
        secret_key = findViewById(R.id.secret_key);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SecretKey_Model=secret_key.getText().toString();
                if(SecretKey_Model.length()==10){
                    username=db.getUsername();
                    if (!(db.getLastId()==null)) btn_id = Integer.parseInt(db.getLastId());
                    id_model = spinner.getSelectedItemId();

                    if (id_model < 5 && id_model!=1) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            idparam="one";
                        }
                    } else if(id_model < 10) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            idparam="four";
                        }
                    }
                    btn_id++;
                    ModelName = spinner.getSelectedItem().toString();
                    progressDialog = ProgressDialog.show(AddDevice.this, "Wait a moment..", "Configuring your device", true, true);
                    AddDeviceTask add = new AddDeviceTask();
                    //add.execute();
                    add.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else{
                    Toast.makeText(getApplicationContext(),"Invalid Secret Key",Toast.LENGTH_SHORT).show();
                }

           }
        });

        //Set size of the popup activity
//        DisplayMetrics d=new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(d);
//        int w=d.widthPixels;
//        int h=d.heightPixels;
//        getWindow().setLayout((int)(w*0.8),(int)(h*0.4));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void add_single_switch(Integer p_id, String p_user, Integer iot){

        hf = new HomeFragment();
        db = new ButtonDetails(getApplicationContext());
        switch (ModelName){
            case "Model A1":ip="192.168.43.45"; break;
            case "Model A2":ip="192.168.43.44"; break;
            case "Model A3":ip="192.168.43.43"; break;
            case "Model A4":ip="192.168.43.42"; break;
            case "Model A5":ip="192.168.43.41"; break;
            default: Toast.makeText(getApplicationContext(),"Invalid Device Model",Toast.LENGTH_SHORT).show();
        }
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        VectorDrawable image = (VectorDrawable) getResources().getDrawable(imgs.getResourceId(default_res_id, -1));
        hf.imageItems.add(new ImageItem(image, "Switch#" + btn_id, btn_id));
        hf.gridAdapter.notifyDataSetChanged();
        db.add(""+btn_id, "Switch#" + btn_id, "-/-/-", "-/-/-", "-/-/-", "-/-/-", "false", "SCHEDULE_OFF", "off", "off", "off", "off",""+default_res_id,ip,"bulb_1",p_user,""+p_id, ""+iot);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void add_four_switch(Integer p_id, String p_user, Integer iot){

            db = new ButtonDetails(getApplicationContext());
            hf = new HomeFragment();
            if(iot==0) {
                switch (ModelName) {
                    case "Model B1":
                        ip = "192.168.43.50";
                        break;
                    case "Model B2":
                        ip = "192.168.43.51";
                        break;
                    case "Model B3":
                        ip = "192.168.43.52";
                        break;
                    case "Model B4":
                        ip = "192.168.43.53";
                        break;
                    case "Model B5":
                        ip = "192.168.43.54";
                        break;
                    default:
                        ip = "iot"+btn_id;
                }
            }
            for(btns=0;btns<4;btns++) {

                if(p_id!=0){
                    p_id++;
                }
                TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
                //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(default_res_id, -1));
                VectorDrawable image = (VectorDrawable) getResources().getDrawable(imgs.getResourceId(default_res_id, -1));
                hf.imageItems.add(new ImageItem(image, "Switch#" + btn_id, btn_id));
                db.add(""+btn_id, "Switch#" + btn_id, "-/-/-", "-/-/-", "-/-/-", "-/-/-", "false", "SCHEDULE_OFF", "off", "off", "off", "off",""+default_res_id,ip,""+device_url[btns],p_user,""+p_id, ""+iot);
                btn_id++;
        }

        hf.gridAdapter.notifyDataSetChanged();
    }

    @SuppressLint("StaticFieldLeak")
    class AddDeviceTask extends AsyncTask<String,String,String> {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onPostExecute(String jsonRead) {
             //Toast.makeText(getApplicationContext(),jsonRead,Toast.LENGTH_SHORT).show();

            super.onPostExecute(jsonRead);
            String status="",usertype="",uname="";
            int id_f=0;
            try {
                JSONObject json = new JSONObject(jsonRead);
                status = json.getString("status");
                usertype = json.getString("usertype");
                id_f = json.getInt("id");
                uname = json.getString("uname");
                //Toast.makeText(getApplicationContext(),err,Toast.LENGTH_SHORT).show();
            } catch (JSONException e){
                e.printStackTrace();
            }

            progressDialog.dismiss();
                if(status.equals("success")&&usertype.equals("primary")){
                    //Toast.makeText(getApplicationContext(),String.valueOf(id_f),Toast.LENGTH_SHORT).show();
                    if((id_f==btn_id)&&idparam.equals("one")&&id_model==0){
                        //iot one switch device selected
                        add_single_switch(0,"na",1);
                        Toast.makeText(getApplicationContext(),"One iSmart Home switch added",Toast.LENGTH_SHORT).show();

                    }
                    else if((id_f==btn_id+4)&&idparam.equals("four")&&id_model==1){
                        // iot four switch device selected
                        add_four_switch(0,"na",1);
                        Toast.makeText(getApplicationContext(),"Four iSmart Home switch added",Toast.LENGTH_SHORT).show();
                    }
                    else if((id_f==btn_id)&&idparam.equals("one")) {
                        add_single_switch(0,"na",0);
                        Toast.makeText(getApplicationContext(),"One Smart Home switch added",Toast.LENGTH_SHORT).show();
                    } else if(id_f==btn_id+4&&idparam.equals("four")) {
                        add_four_switch(0,"na",0);
                        Toast.makeText(getApplicationContext(),"Four Smart Home switch added",Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
                else if(status.equals("success")&&usertype.equals("secondary")){

                    if(idparam.equals("one")) {
                        add_single_switch(id_f,uname,0);
                        Toast.makeText(getApplicationContext(),"Added as secondary user",Toast.LENGTH_SHORT).show();
                    } else if(idparam.equals("four")) {
                        add_four_switch(id_f,uname,0);
                        Toast.makeText(getApplicationContext(),"Four switches added as secondary user",Toast.LENGTH_SHORT).show();
                    }
                    finish();
                } else if(status.equals("success")&&usertype.equals("primary-reconfigure")){
                    //Toast.makeText(getApplicationContext(),String.valueOf(id_f),Toast.LENGTH_SHORT).show();
                    if(idparam.equals("one")&&id_model==0){
                        //iot one switch device selected
                        btn_id = id_f;
                        add_single_switch(0,"na",1);
                        Toast.makeText(getApplicationContext(),"One iSmart Home switch added",Toast.LENGTH_SHORT).show();

                    }
                    else if(idparam.equals("four")&&id_model==1){
                        // iot four switch device selected
                        btn_id = id_f;
                        add_four_switch(0,"na",1);
                        Toast.makeText(getApplicationContext(),"Four iSmart Home switch added",Toast.LENGTH_SHORT).show();
                    }
                    else if(idparam.equals("one")) {
                        btn_id = id_f;
                        add_single_switch(0,"na",0);
                        Toast.makeText(getApplicationContext(),"One Smart Home switch added",Toast.LENGTH_SHORT).show();
                    } else if(idparam.equals("four")) {
                        btn_id = id_f;
                        add_four_switch(0,"na",0);
                        Toast.makeText(getApplicationContext(),"Four Smart Home switch added",Toast.LENGTH_SHORT).show();
                    }
                    finish();
                } else {
                        AlertDialog.Builder builder=new AlertDialog.Builder(AddDevice.this);
                        builder.setCancelable(false);
                        builder.setTitle("Adding Device Failed");
                        builder.setMessage("Due to Connection Failure/Wrong credentials provided. Please Retry later.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                finish();
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
                String data = URLEncoder.encode("uname", "UTF-8")
                        + "=" + URLEncoder.encode(username, "UTF-8");
                //Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                data += "&" + URLEncoder.encode("id", "UTF-8")
                        + "=" + URLEncoder.encode(String.valueOf(btn_id), "UTF-8");

                data += "&" + URLEncoder.encode("secret", "UTF-8")
                        + "=" + URLEncoder.encode(SecretKey_Model, "UTF-8");

                data += "&" + URLEncoder.encode("deviceType", "UTF-8")
                        + "=" + URLEncoder.encode(idparam, "UTF-8");

                URL url = new URL("https://www.fpelabs.com/Android_App/ioT/add_new_device_db.php");
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
                return "{status:failed}";
            }

        }
        }

}
