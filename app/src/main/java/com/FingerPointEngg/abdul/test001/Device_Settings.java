package com.FingerPointEngg.abdul.test001;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class Device_Settings extends AppCompatActivity {

    CardView card_reset_device;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device__settings);

        card_reset_device = findViewById(R.id.card_reset_device);

        card_reset_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reset_device = new Intent(getApplicationContext(),Reset_Device.class);
                startActivity(reset_device);
            }
        });

    }
}
