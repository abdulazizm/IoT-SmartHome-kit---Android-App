package com.FingerPointEngg.abdul.test001;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TimerFragment extends Fragment {

    static View.OnClickListener myOnClickListener;
    static Integer[] drawableArray={R.drawable.ic_lightbulb_off,R.drawable.ic_fan_off,R.drawable.ic_socket_off,R.drawable.ic_lightbulb_on,R.drawable.ic_fan_on,R.drawable.ic_socket_on};
    ButtonDetails db;
    //private static ArrayList<Integer> removedItems;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_timer,container,false);

        db = new ButtonDetails(getContext());

        //myOnClickListener = new MyOnClickListener(getActivity());

        RecyclerView recyclerView = rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ArrayList<DataModel> data = new ArrayList<DataModel>();
        String lastId = db.getLastId();
        if(lastId!=null){
            for (int id = 0; id <= Integer.parseInt(lastId); id++) {
                String name=db.getName(id);
                String offTimer=db.getOffTimer(id);
                String resId =db.getResourceId(id);
                String onTimer=db.getOnTimer(id);
                String ScheduleStart=db.getScheduleStart(id);
                String ScheduleStop = db.getScheduleStop(id);
                if(name!=null&&(!offTimer.equals("-/-/-") || !onTimer.equals("-/-/-") || !ScheduleStart.equals("-/-/-") || !ScheduleStop.equals("-/-/-"))) {
                    data.add(new DataModel(name, offTimer, id, drawableArray[Integer.parseInt(resId)], onTimer, ScheduleStart, ScheduleStop));
                }
            }
        }


        RecyclerView.Adapter adapter = new CustomAdapter(data);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

//    private static class MyOnClickListener implements View.OnClickListener {
//
//        private final Context context;
//
//        private MyOnClickListener(Context context) {
//            this.context = context;
//        }
//
//        @Override
//        public void onClick(View v) {
//            removeItem(v);
//        }
//
//       private void removeItem(View v) {
//        }
//    }

}
