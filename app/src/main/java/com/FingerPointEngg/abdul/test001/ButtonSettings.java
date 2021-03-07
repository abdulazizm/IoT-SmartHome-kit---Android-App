package com.FingerPointEngg.abdul.test001;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ButtonSettings extends AppCompatActivity {
    Button name,timer,schedule;
    Switch scheOnOff;
    EditText nv;
    static int id;
    ButtonDetails db;
    HomeFragment hf;
    TextView time,sche;
    ImageButton b_bulb,b_socket,b_fan;
    //Context context;
    static boolean OnOffVisible=false;
    static String resourceId;
    //boolean isLabelUpdated=false;
//    boolean islabelupdated;
//    BottomNav nav;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_settings);
        name = findViewById(R.id.name_set);
        timer = findViewById(R.id.timer_window);
        schedule = findViewById(R.id.shedule_window);
        nv = findViewById(R.id.name_value);
        scheOnOff = findViewById(R.id.switch2);
        b_bulb = findViewById(R.id.bulb_button);
        b_fan = findViewById(R.id.fan_button);
        b_socket = findViewById(R.id.socket_button);

        db = new ButtonDetails(getApplicationContext());

        //final Intent intent = getIntent();
        id = getIntent().getIntExtra("id",-1);
        //Toast.makeText(getApplicationContext(),""+id,Toast.LENGTH_SHORT).show();

        resourceId=db.getResourceId(id);
        switch (resourceId){
            case "0":
            case "3":{
                b_bulb.setImageDrawable(getResources().getDrawable(R.drawable.ic_lightbulb_on));
                b_fan.setImageDrawable(getResources().getDrawable(R.drawable.ic_fan_off));
                b_socket.setImageDrawable(getResources().getDrawable(R.drawable.ic_socket_off));
                break;
            }
            case "1":
            case "4":{
                b_bulb.setImageDrawable(getResources().getDrawable(R.drawable.ic_lightbulb_off));
                b_fan.setImageDrawable(getResources().getDrawable(R.drawable.ic_fan_on));
                b_socket.setImageDrawable(getResources().getDrawable(R.drawable.ic_socket_off));
                break;
            }
            case "2":
            case "5":{
                b_bulb.setImageDrawable(getResources().getDrawable(R.drawable.ic_lightbulb_off));
                b_fan.setImageDrawable(getResources().getDrawable(R.drawable.ic_fan_off));
                b_socket.setImageDrawable(getResources().getDrawable(R.drawable.ic_socket_on));
                break;
            }

        }

        b_bulb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(resourceId.equals("0") || resourceId.equals("3"))){
                    b_bulb.setImageDrawable(getResources().getDrawable(R.drawable.ic_lightbulb_on));
                    b_fan.setImageDrawable(getResources().getDrawable(R.drawable.ic_fan_off));
                    b_socket.setImageDrawable(getResources().getDrawable(R.drawable.ic_socket_off));
                    db.setResourceId(id,"0");
                    resourceId="0";
                }
            }
        });

        b_fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(resourceId.equals("1") || resourceId.equals("4"))){
                    b_bulb.setImageDrawable(getResources().getDrawable(R.drawable.ic_lightbulb_off));
                    b_fan.setImageDrawable(getResources().getDrawable(R.drawable.ic_fan_on));
                    b_socket.setImageDrawable(getResources().getDrawable(R.drawable.ic_socket_off));
                    db.setResourceId(id,"1");
                    resourceId="1";
                }
            }
        });

        b_socket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(resourceId.equals("2") || resourceId.equals("5"))){
                    b_bulb.setImageDrawable(getResources().getDrawable(R.drawable.ic_lightbulb_off));
                    b_fan.setImageDrawable(getResources().getDrawable(R.drawable.ic_fan_off));
                    b_socket.setImageDrawable(getResources().getDrawable(R.drawable.ic_socket_on));
                    db.setResourceId(id,"2");
                    resourceId="2";
                }
            }
        });

        nv.setText(db.getName(id));

        time = findViewById(R.id.time);
//        time.setText("ON TIME " + db.getOnTimer(id) + " " + "OFF TIME " + db.getOffTimer(id));
//
        sche = findViewById(R.id.sche);
//        sche.setText("START " + db.getScheduleStart(id) + " STOP " + db.getScheduleStop(id));


        Boolean getScheduleOn= db.getScheduleOnOff(id);
        // Checks when user enters the respective button Setting page (For Schedule On Off implementation)
        if(getScheduleOn==true) {
            scheOnOff.toggle();
            OnOffVisible=true;
            schedule.setVisibility(View.VISIBLE);
            sche.setVisibility(View.VISIBLE);
            //scheOnOffInvisible=false;
        } else if(getScheduleOn==false)
        {
            OnOffVisible=false;
            schedule.setVisibility(View.INVISIBLE);
            sche.setVisibility(View.INVISIBLE);
        }

        // On button click execution
        scheOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OnOffVisible==true){
                    schedule.setVisibility(View.INVISIBLE);
                    sche.setVisibility(View.INVISIBLE);
                    db.setScheduleOnOff(id, false);
                    OnOffVisible=false;
                }
                else if(OnOffVisible==false){
                    schedule.setVisibility(View.VISIBLE);
                    sche.setVisibility(View.VISIBLE);
                    db.setScheduleOnOff(id, true);
                    OnOffVisible=true;
                }
            }
        });
        name.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (nv.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Type a name", Toast.LENGTH_SHORT).show();
                } else {

                    hf = new HomeFragment();
                    //hf.updatelabels();
                    //Toast.makeText(getApplicationContext(), ""+id, Toast.LENGTH_SHORT).show();
                    String name=nv.getText().toString();
                    db.setName(id, name);
//                    TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
//                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(0, -1));
//                    hf.imageItems.add(new ImageItem(bitmap, "Button#" + 158, 110));
                    //hf.gridAdapter.notifyDataSetChanged();
                    //hf.gridView.invalidateViews();
                    finish();

                }

            }
        });
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(ButtonSettings.this, timerService.class);
//                startService(i);
                Intent t = new Intent(getApplicationContext(), TimePicker.class);
                t.putExtra("id", id);
                startActivity(t);
            }
        });
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent t = new Intent(getApplicationContext(), TimePickerSche.class);
                t.putExtra("id", id);
                startActivity(t);
            }
        });

        //Set size of the popup activity
        DisplayMetrics d=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(d);
        int w=d.widthPixels;
        int h=d.heightPixels;
        getWindow().setLayout((int)(w*0.9),(int)(h*0.7));

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onPostResume() {
        time.setText("ON TIME "+db.getOnTimer(id)+" "+"OFF TIME "+db.getOffTimer(id));
        sche.setText("START "+db.getScheduleStart(id)+" STOP "+db.getScheduleStop(id));
        super.onPostResume();
    }
}
