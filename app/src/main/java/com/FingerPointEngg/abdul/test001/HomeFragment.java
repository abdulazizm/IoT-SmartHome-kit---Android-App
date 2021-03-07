package com.FingerPointEngg.abdul.test001;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.VectorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.System.out;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private GridView gridView;
    static public GridViewAdapter gridAdapter;
    FloatingActionButton addDevice;
    static ArrayList imageItems;
    ButtonDetails db;
    SwipeRefreshLayout swipeLayout;
    Integer id_;
    String uname;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.fragment_home, null);

        gridView = view.findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(getContext(), R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);

        swipeLayout = view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.Blue));

        final Vibrator vib= (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        updateStatus();
        iotStatusUpdate();

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        addDevice = view.findViewById(R.id.add_device);

        addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent add = new Intent(getContext(),AddDevice.class);
                startActivity(add);
            }
        });
        uname = db.getUsername();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);

                vib.vibrate(50);

                id_ = item.getId();
                if (db.getiot(id_) == 0) {
                    String ip = db.getIP(id_);
                    String device_url = db.getDeviceUrl(id_);
                    String url = "http://" + ip + "/" + device_url;
                    //Toast.makeText(getContext(), "" + url, Toast.LENGTH_SHORT).show();
                    switch (db.getResourceId(id_)) {
                        case "0":
                            db.setResourceId(id_, "3");
                            break;
                        case "1":
                            db.setResourceId(id_, "4");
                            break;
                        case "2":
                            db.setResourceId(id_, "5");
                            break;
                        case "3":
                            db.setResourceId(id_, "0");
                            break;
                        case "4":
                            db.setResourceId(id_, "1");
                            break;
                        case "5":
                            db.setResourceId(id_, "2");
                            break;
                    }

                    SelectTask task = new SelectTask(url);
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    updateStatus();
                    //reload();

                } else if ((db.getiot(id_) == 1)) {

                    switch (db.getResourceId(id_)) {
                        case "0":
                            db.setResourceId(id_, "3");
                            break;
                        case "1":
                            db.setResourceId(id_, "4");
                            break;
                        case "2":
                            db.setResourceId(id_, "5");
                            break;
                        case "3":
                            db.setResourceId(id_, "0");
                            break;
                        case "4":
                            db.setResourceId(id_, "1");
                            break;
                        case "5":
                            db.setResourceId(id_, "2");
                            break;
                    }

                    IotStatusUpdateTask task = new IotStatusUpdateTask(uname,id_);
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    iotStatusUpdate();

                    //reload();
                }
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                //Create intent
                Intent intent = new Intent(getContext(), ButtonSettings.class);
                intent.putExtra("id",item.getId());
                startActivity(intent);

                return true;
            }
        });

//        //timer for status and names
//
//        final Handler handler = new Handler();
//
//        handler.postDelayed(new Runnable() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void run() {
//
//                updateStatus();
//                iotStatusUpdate();
//                handler.postDelayed(this,1000);
//
//
//            }
//        }, 1000);



        return view;
    }


    /**
     * Prepare some dummy data for gridview
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ArrayList<ImageItem> getData() {
        db = new ButtonDetails(getContext());
        imageItems = new ArrayList<>();
        String lastID=db.getLastId();
        if(lastID!= null) {
            TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
            for (int i = 0; i <= Integer.parseInt(lastID); i++) {
                if (db.getResourceId(i) != null) {
                    VectorDrawable image = (VectorDrawable) getResources().getDrawable(imgs.getResourceId(Integer.parseInt(db.getResourceId(i)), -1));
                    imageItems.add(new ImageItem(image, db.getName(i), i));
                }
            }
        }
        return imageItems;
    }


    @Override
    public void onRefresh() {

        updateStatus();
        iotStatusUpdate();

    }

    public void reload() {

        GridViewAdapter gridViewAdapter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            gridViewAdapter = new GridViewAdapter(getContext(), R.layout.grid_item_layout, getData());
        }
        gridView.setAdapter(gridViewAdapter);
        swipeLayout.setRefreshing(false);

    }

    private void iotStatusUpdate(){

        if(db.getLastId()!=null) {
            for (Integer id_update = 1; id_update <= Integer.parseInt(db.getLastId()); id_update++) {
                if (db.getiot(id_update) == 1) {
                    IotStatusGetTask task = new IotStatusGetTask(uname,id_update);
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
//                String ip = db.getIP(id_update);
//                String ip2 = db.getIP(id_update+1);
//                if(ip != null && ip.equals(ip2)){
//                    id_update=id_update+3;
//                }
            }
        }

    }
    private void updateStatus() {

        if(db.getLastId()!=null) {
            for (int id_update = 1; id_update <= Integer.parseInt(db.getLastId()); id_update++) {
                if (db.getiot(id_update) == 0) {
                    String ip = db.getIP(id_update);
                    if (ip != null) {
                        String url = "http://" + ip + "/status";
                        StatusTask2 task = new StatusTask2(url, id_update);
                        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                    String ip2 = db.getIP(id_update+1);
                    if(ip != null && ip.equals(ip2)){
                        id_update=id_update+3;
                    }

                }
            }
        }

    }

    @SuppressLint("StaticFieldLeak")
    public class StatusTask2 extends AsyncTask<Void, Void, String> {

        private String mUrl;
        private Integer id_;

        StatusTask2(String url, Integer id) {
            mUrl = url;
            id_ = id;
        }

        @Override
        protected String doInBackground(Void... params) {

            String jsonparse = JsonHttp.makeHttpRequest(mUrl);

            try {

                JSONObject json = new JSONObject(jsonparse);

                String device = db.getDeviceUrl(id_);

                if(device!=null){
                    switch (device) {

                        case "bulb_1": {
                            String bulb_1 = json.getString("bl");
                            if (bulb_1 != null) {
                                if (bulb_1.equals("1")) updateResIdAndCurrentStateTrue();
                                else updateResIdAndCurrentStateFalse();
                            }
                            break;
                        }
                        case "fan": {
                            String fan_1 = json.getString("fl");
                            if (fan_1 != null) {
                                if (fan_1.equals("1")) updateResIdAndCurrentStateTrue();
                                else updateResIdAndCurrentStateFalse();
                            }
                            break;
                        }

                        case "tube_light": {
                            String tube_1 = json.getString("tl");
                            if (tube_1 != null) {
                                if (tube_1.equals("1")) updateResIdAndCurrentStateTrue();
                                else updateResIdAndCurrentStateFalse();
                            }
                            break;
                        }

                        case "socket": {
                            String sock_1 = json.getString("sl");

                            if (sock_1 != null) {
                                if (sock_1.equals("1")) updateResIdAndCurrentStateTrue();
                                else updateResIdAndCurrentStateFalse();
                            }
                            break;
                        }

                    }
                }
                return jsonparse;
            } catch (Exception e) {
                e.printStackTrace();
            }


            return "failed";
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String result) {

            //Toast.makeText(getContext(),result,Toast.LENGTH_SHORT).show();
            reload();
            super.onPostExecute(result);

        }

        private void updateResIdAndCurrentStateTrue(){
            db.setCurrentState(id_, true);
            //on
            switch (db.getResourceId(id_)) {
                case "0":
                    db.setResourceId(id_, "3");
                    break;
                case "1":
                    db.setResourceId(id_, "4");
                    break;
                case "2":
                    db.setResourceId(id_, "5");
                    break;
            }
        }

        private void updateResIdAndCurrentStateFalse(){
            db.setCurrentState(id_, false);
            //off
            switch (db.getResourceId(id_)) {
                case "3":
                    db.setResourceId(id_, "0");
                    break;
                case "4":
                    db.setResourceId(id_, "1");
                    break;
                case "5":
                    db.setResourceId(id_, "2");
                    break;
            }
        }

//        private void updateButtonStatus(String jsonStrings) {
//
//        }
    }

    class IotStatusGetTask extends AsyncTask<String,String,String> {

        //p-provided, g-get
        private String uname_p;
        private Integer id_p;

        IotStatusGetTask(String uname_g, Integer id_g) {
            uname_p = uname_g;
            id_p = id_g;
        }


        @Override
        protected void onPostExecute(String jsonRead) {

            super.onPostExecute(jsonRead);

            String status="", state="";
            try {
                JSONObject json = new JSONObject(jsonRead);
                status = json.getString("status");
                state = json.getString("data");
            } catch (JSONException e){
                e.printStackTrace();
            }


            if(status.equals("success")){

                if(state.equals("0")){
                    updateResIdAndCurrentStateFalse();
                }else if(state.equals("1")){
                    updateResIdAndCurrentStateTrue();
                }
            }
            reload();
        }

        private void updateResIdAndCurrentStateTrue(){
            db.setCurrentState(id_p, true);
            switch (db.getResourceId(id_p)) {
                case "0":
                    db.setResourceId(id_p, "3");
                    break;
                case "1":
                    db.setResourceId(id_p, "4");
                    break;
                case "2":
                    db.setResourceId(id_p, "5");
                    break;
            }
        }

        private void updateResIdAndCurrentStateFalse(){
            db.setCurrentState(id_p, false);
            switch (db.getResourceId(id_p)) {
                case "3":
                    db.setResourceId(id_p, "0");
                    break;
                case "4":
                    db.setResourceId(id_p, "1");
                    break;
                case "5":
                    db.setResourceId(id_p, "2");
                    break;
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                //https://www.fpelabs.com/Android_App/ioT/get_db.php?uname=aziz&id=2
                String data = URLEncoder.encode("uname", "UTF-8")
                        + "=" + URLEncoder.encode(uname_p, "UTF-8");

                data += "&" + URLEncoder.encode("id", "UTF-8")
                        + "=" + URLEncoder.encode(""+id_p, "UTF-8");

                URL url = new URL("https://www.fpelabs.com/Android_App/ioT/get_db.php");
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Accept-Encoding", "identity");
                urlConnection.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                writer.write(data);
                writer.flush();
                writer.close();

                // Get the server response
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line;
                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line).append("\n");
                }
                reader.close();
                return sb.toString();

            } catch (Exception e) {
                out.println(e.getMessage());
            }
            return "{'status':'failed'}";
        }
    }

}
