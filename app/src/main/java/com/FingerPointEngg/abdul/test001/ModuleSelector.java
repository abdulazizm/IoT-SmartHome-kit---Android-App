package com.FingerPointEngg.abdul.test001;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ModuleSelector extends AppCompatActivity  {
Spinner s1,s2;
Button b;
List<String>switches=new ArrayList<>();
List<String>modelsA=new ArrayList<>();
List<String>modelsB=new ArrayList<>();
List<String>selected=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_selector);
        s1=findViewById(R.id.sw);
        s2=findViewById(R.id.models);
        b=findViewById(R.id.set);
        switches.add("ONE SWITCH");
        switches.add("FOUR SWITCH");

        modelsA.add("A1");modelsA.add("A2");modelsA.add("A3");modelsA.add("A4");modelsA.add("A5");
        modelsB.add("B1");modelsB.add("B2");modelsB.add("B3");modelsB.add("B4");modelsB.add("B5");
        ArrayAdapter<String> switchAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, switches);
        switchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(switchAdapter);
        s1.setSelection(0);

        final ArrayAdapter<String> AAdapter= new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, modelsA);
        AAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(AAdapter);
        s2.setSelection(0);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item=switches.get(position);
                if(item.equals("ONE SWITCH")){

                    s2.setAdapter(AAdapter);
                    selected=modelsA;
                }
                else if(item.equals("FOUR SWITCH")){
                    ArrayAdapter<String> BAdapter= new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, modelsB);
                    BAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    s2.setAdapter(BAdapter);
                    selected=modelsB;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),switches.get(s1.getSelectedItemPosition())+" "+selected.get(s2.getSelectedItemPosition()),Toast.LENGTH_SHORT).show();
            }
        });


        DisplayMetrics d=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(d);
        int w=d.widthPixels;
        int h=d.heightPixels;
        getWindow().setLayout((int)(w*0.9),(int)(h*0.3));





    }
}
