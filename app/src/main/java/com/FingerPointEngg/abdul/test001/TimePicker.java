package com.FingerPointEngg.abdul.test001;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TimePicker extends AppCompatActivity {
    NumberPicker n1,n2,n3,n4;
    String[] ampm=new String[2];
    Button setOn,setOff;
    Calendar c;
    SimpleDateFormat D;
    Date d;
    ButtonDetails db;
    boolean ServicesRunning=false;
    private static ArrayList<MyData> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        n1= findViewById(R.id.hours);
        n2= findViewById(R.id.minutes);

        n4= findViewById(R.id.ampm);
        setOn=findViewById(R.id.button_setOn);
        setOff=findViewById(R.id.button_setOff);
        ampm[0]="AM";ampm[1]="PM";
        db=new ButtonDetails(getApplicationContext());
        data = new ArrayList<MyData>();

//        data.add(new MyData(
//                "New Name",
//                "xsjcxda",
//                12,
//                1
//        ));

        //data.remove(1);
        n1.setMinValue(1);
        n1.setMaxValue(12);
        n2.setMinValue(00);
        n2.setMaxValue(59);

        n4.setMinValue(0);
        n4.setMaxValue(1);
        n4.setDisplayedValues(ampm);
        NumberPicker.Formatter f=new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d",value);
            }
        };
        n1.setFormatter(f);
        n2.setFormatter(f);

        n4.setFormatter(f);


        Intent i=getIntent();
        final int id=getIntent().getIntExtra("id",-1);
        final DecimalFormat format=new DecimalFormat("00");

        setOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hour= format.format(n1.getValue());
                String mins=format.format(n2.getValue());
                String f=ampm[n4.getValue()];
                if(!ServicesRunning) {
                    ServicesRunning=true;
                    Intent i = new Intent(TimePicker.this, timerService.class);
                    startService(i);
                }

                c=Calendar.getInstance();
                Toast.makeText(getApplicationContext(),hour+"/"+mins+"/"+f,Toast.LENGTH_SHORT).show();

                db.setOnTimer(id,hour+"/"+mins+"/"+f);
                db.setTimerOnMode(id,true);

//                data.add(new MyData(
//                        "New Name",
//                        "xsjcxda",
//                        R.drawable.ic_lightbulb_on,
//                        1
//                ));

            }
        });
        setOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hour= format.format(n1.getValue());
                String mins=format.format(n2.getValue());
                //String sec=String.valueOf(n3.getValue());
                String f=ampm[n4.getValue()];

                if(!ServicesRunning) {
                    ServicesRunning=true;
                    Intent i = new Intent(TimePicker.this, timerService.class);
                    startService(i);
                }
                Toast.makeText(getApplicationContext(),hour+"/"+mins+"/"+f,Toast.LENGTH_SHORT).show();

                db.setOffTimer(id,hour+"/"+mins+"/"+f);
                db.setTimerOffMode(id,true);
         }
        });


        DisplayMetrics d=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(d);
        int w=d.widthPixels;
        int h=d.heightPixels;
        getWindow().setLayout((int)(w*0.8),(int)(h*0.5));


    }


}
