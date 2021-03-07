package com.FingerPointEngg.abdul.test001;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class StatsFragment extends Fragment implements Cloneable {
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    int i=1;
    ProgressBar p;
//    TableLayout tableLayout;
    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats,null);
//        View v=inflater.inflate(R.layout.fragment_stats,null);
//        p=v.findViewById(R.id.pro);
//        tableLayout=v.findViewById(R.id.table);
//        p.setProgress(i);
//
//        final ProgressBar r=new ProgressBar(getContext());
//
//        final ProgressBar r1=new ProgressBar(getContext());
//        final ProgressBar r2=new ProgressBar(getContext());
//        TableRow row=new TableRow(getContext());
//
//        row.addView(r);row.addView(r1);row.addView(r2);
//        tableLayout.addView(row);
//        FloatingActionButton fab=v.findViewById(R.id.add);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getContext(),ModuleSelector.class);
//                startActivity(intent);
//            }
//        });
//
//        p.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(),"clicked..",Toast.LENGTH_SHORT).show();
//                i=i+20;
//                p.setProgress(i);
//            }
//        });

//        return v;

    }
}
