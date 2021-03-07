package com.FingerPointEngg.abdul.test001;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class Smart_Controls extends AppCompatActivity {

    CardView parent_lock, sec_user, login_prompt;
    TextView enableDisableLogin;
    ButtonDetails db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart__controls);
        parent_lock = findViewById(R.id.parental_lock);
        sec_user = findViewById(R.id.add_sec_user);
        login_prompt = findViewById(R.id.Disable_login_prompt);

        enableDisableLogin = findViewById(R.id.EnableDisableLogin);

        db = new ButtonDetails(this);
        if(db.checkLoginPrompt())
        enableDisableLogin.setText("Disable Login Prompt");
        else
        enableDisableLogin.setText("Enable Login Prompt");

        sec_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smart_con = new Intent(getApplicationContext(),Add_secondary_user.class);
                startActivity(smart_con);
            }
        });

        login_prompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.checkLoginPrompt()){
                    db.setLoginPrompt("disable");
                    enableDisableLogin.setText("Enable Login Prompt");
                    AlertDialog.Builder builder = new AlertDialog.Builder(Smart_Controls.this);
                    builder.setCancelable(false);
                    builder.setTitle("Changes Applied Successfully");
                    builder.setMessage("Login Prompt Disabled");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.setIcon(getResources().getDrawable(R.drawable.ic_user));
                    builder.show();
                }
                else{
                    db.setLoginPrompt("enable");
                    enableDisableLogin.setText("Disable Login Prompt");
                    AlertDialog.Builder builder = new AlertDialog.Builder(Smart_Controls.this);
                    builder.setCancelable(false);
                    builder.setTitle("Changes Applied Successfully");
                    builder.setMessage("Login Prompt Enabled");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.setIcon(getResources().getDrawable(R.drawable.ic_user));
                    builder.show();

                }

            }
        });

        parent_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lock = new Intent(getApplicationContext(),Parental_Lock.class);
                startActivity(lock);
            }
        });



    }
}
