package com.FingerPointEngg.abdul.test001;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SettingsFragment extends Fragment  {


    CardView card_profile,card_device,card_logout,card_control,card_help;
    ButtonDetails db;
    TextView user_name,user_devices;

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        card_profile = v.findViewById(R.id.card_profile);
        card_device = v.findViewById(R.id.card_device);
        card_logout = v.findViewById(R.id.card_logout);
        card_control = v.findViewById(R.id.card_control);
        card_help = v.findViewById(R.id.card_help);
        user_name = v.findViewById(R.id.user_name);
        user_devices = v.findViewById(R.id.user_devices);

        db=new ButtonDetails(getContext());
        //String user=db.getUsername();
        user_name.setText(db.getUsername());
        if(db.getLastId()!=null){
            user_devices.setText(db.getLastId()+" Smart Device Configured");
        }

        card_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent smart_con = new Intent(getContext(),Smart_Controls.class);
                startActivity(smart_con);
            }
        });

        card_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent device_con = new Intent(getContext(),Device_Settings.class);
                startActivity(device_con);
            }
        });

        card_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                //builder.setCancelable(false);
                builder.setTitle("Logout");
                builder.setMessage("You can't control device until you login again. Are you sure to logout ? ");
                builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.setLoginPrompt("enable");
                        dialog.cancel();
                        Intent logout = new Intent(getContext(), LoginActivity.class);
                        startActivity(logout);
                    }
                });
                builder.setIcon(getResources().getDrawable(R.drawable.ic_add ));
                builder.show();

            }
        });
        return v;
    }

}
