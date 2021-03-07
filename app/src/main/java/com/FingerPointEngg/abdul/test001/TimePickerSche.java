package com.FingerPointEngg.abdul.test001;

import android.annotation.SuppressLint;
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
import java.util.Calendar;
import java.util.Date;

public class TimePickerSche extends AppCompatActivity {
    NumberPicker n1,n2,n3,n4;
    String[] ampm=new String[2];
    Button start,stop;
    Calendar c;
    SimpleDateFormat D;
    Date d;
    ButtonDetails db;
    TimePicker Timerpicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_picker);
        n1= findViewById(R.id.hours);
        n2= findViewById(R.id.minutes);

        n4= findViewById(R.id.ampm);
        start=findViewById(R.id.button_setStart);
        stop=findViewById(R.id.button_setStop);
        ampm[0]="AM";ampm[1]="PM";
        db=new ButtonDetails(getApplicationContext());
        Timerpicker = new TimePicker();

        n1.setMinValue(1);
        n1.setMaxValue(12);
        n2.setMinValue(00);
        n2.setMaxValue(59);

        n4.setMinValue(0);
        n4.setMaxValue(1);
        n4.setDisplayedValues(ampm);
        NumberPicker.Formatter f=new NumberPicker.Formatter() {
            @SuppressLint("DefaultLocale")
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

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hour= format.format(n1.getValue());
                String mins=format.format(n2.getValue());
                String f=ampm[n4.getValue()];

                if(!Timerpicker.ServicesRunning) {
                    Intent i = new Intent(TimePickerSche.this, timerService.class);
                    startService(i);
                    Timerpicker.ServicesRunning=false;
                }
                Toast.makeText(getApplicationContext(),hour+"/"+mins+"/"+f,Toast.LENGTH_SHORT).show();
                c=Calendar.getInstance();
                db.setScheduleStart(id,hour+"/"+mins+"/"+f);
                db.setScheduleMode(id,"SCHEDULE_ON");
                start.setBackgroundColor(getResources().getColor(R.color.red));


            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hour= format.format(n1.getValue());
                String mins=format.format(n2.getValue());
                //String sec=String.valueOf(n3.getValue());
                String f=ampm[n4.getValue()];

                if(!Timerpicker.ServicesRunning) {
                    Intent i = new Intent(TimePickerSche.this, timerService.class);
                    startService(i);
                    Timerpicker.ServicesRunning=false;
                }

                Toast.makeText(getApplicationContext(),hour+"/"+mins+"/"+f,Toast.LENGTH_SHORT).show();

                db.setScheduleStop(id,hour+"/"+mins+"/"+f);
                //db.setScheduleMode(id,"SHEDULE_ON");
                stop.setBackgroundColor(getResources().getColor(R.color.red));
         }
        });


        DisplayMetrics d=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(d);
        int w=d.widthPixels;
        int h=d.heightPixels;
        getWindow().setLayout((int)(w*0.8),(int)(h*0.5));


    }


}
