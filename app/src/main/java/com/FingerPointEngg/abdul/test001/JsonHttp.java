package com.FingerPointEngg.abdul.test001;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


class JsonHttp {

    static String makeHttpRequest(String url){
        String strResult = "";

        try{
            URL u = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) u.openConnection();
            strResult = readStream(con.getInputStream());
        }catch (Exception e){
            e.printStackTrace();
        }

        return strResult;
    }

    private static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();

        try{
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = reader.readLine()) != null){
                sb.append(line).append("\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

}
