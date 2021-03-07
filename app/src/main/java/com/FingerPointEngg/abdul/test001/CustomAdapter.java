package com.FingerPointEngg.abdul.test001;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<DataModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewOffTimer;
        ImageView imageViewIcon;
        TextView textViewOnTimer;
        TextView textViewScheOnTimer;
        TextView textViewScheOffTimer;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewOffTimer = (TextView) itemView.findViewById(R.id.offTimerLabel);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
            this.textViewOnTimer = (TextView) itemView.findViewById(R.id.onTimerLabel);
            this.textViewScheOnTimer = (TextView) itemView.findViewById(R.id.schedulerOnLabel);
            this.textViewScheOffTimer = (TextView) itemView.findViewById(R.id.schedulerOffLabel);
        }
    }

    public CustomAdapter(ArrayList<DataModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

        view.setOnClickListener(TimerFragment.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewOffTimer = holder.textViewOffTimer;
        ImageView imageView = holder.imageViewIcon;
        TextView ontimer = holder.textViewOnTimer;
        TextView scheOnTimer = holder.textViewScheOnTimer;
        TextView scheOffTimer = holder.textViewScheOffTimer;

        textViewName.setText(dataSet.get(listPosition).getName());
        textViewOffTimer.setText(dataSet.get(listPosition).getOfftimer());
        imageView.setImageResource(dataSet.get(listPosition).getImage());
        ontimer.setText(dataSet.get(listPosition).getonTimer());
        scheOnTimer.setText(dataSet.get(listPosition).getScheontimer());
        scheOffTimer.setText(dataSet.get(listPosition).getScheofftimer());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
