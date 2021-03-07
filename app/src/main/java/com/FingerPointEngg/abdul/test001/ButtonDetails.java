package com.FingerPointEngg.abdul.test001;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.concurrent.locks.ReentrantLock;

class ButtonDetails  {
    private SQLiteDatabase db;
    private ReentrantLock lock = new ReentrantLock();
    //Context context;

    public ButtonDetails(Context ctx){

        db=ctx.openOrCreateDatabase("Buttonsfpe", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Buttonsfpe(id INT,name VARCHAR,timerOn VARCHAR,timerOff VARCHAR,scheduleStart VARCHAR,scheduleStop VARCHAR,timerMode VARCHAR,scheduleMode VARCHAR,currentState VARCHAR,timerOnMode VARCHAR,timerOffMode VARCHAR, scheduleOnOff VARCHAR, r_id VARCHAR, ip VARCHAR, device_url VARCHAR, p_user VARCHAR, p_id INT, iot INT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS users( id VARCHAR, username VARCHAR, password VARCHAR, disablelogin VARCHAR)");

    }

    void add(String id,String name,String timerOn,String timerOff,String scheduleStart,String scheduleStop,String timerMode,String scheduleMode,String currentState,String timerOnMode,String timerOffMode,String scheduleOnOff, String r_id, String ip, String device_url, String p_user, String p_id, String iot){
        if (!isExists(id)) {
            db.execSQL("INSERT INTO Buttonsfpe VALUES('"+id+"','"+name+"','"+timerOn+"','"+timerOff+"','"+scheduleStart+"','"+scheduleStop+"','"+timerMode+"','"+scheduleMode+"','"+currentState+"','"+timerOnMode+"','"+timerOffMode+"','"+scheduleOnOff+"','"+r_id+"','"+ip+"','"+device_url+"','"+p_user+"','"+p_id+"','"+iot+"');");
        }

    }

    void setName(int i,String n){
        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
        if(c.moveToFirst()){
            db.execSQL("UPDATE Buttonsfpe SET name ='"+n+"'WHERE id='"+i+"'");
        }
        c.close();
        lock.unlock();

    }
    String getName(int i){
        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
        lock.unlock();

        if(c.moveToFirst()){
            String getString = c.getString(1);
            c.close();
            return getString;
        }
        c.close();
        return null;
    }


    void setOnTimer(int i,String t){
        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
        if(c.moveToFirst()){
            db.execSQL("UPDATE Buttonsfpe SET timerOn='"+t+"'WHERE id='"+i+"'");
        }
        c.close();
        lock.unlock();
    }
    void setOffTimer(int i,String t){
        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
        if(c.moveToFirst()){
            db.execSQL("UPDATE Buttonsfpe SET timerOff='"+t+"'WHERE id='"+i+"'");
        }
        c.close();
        lock.unlock();
    }
    String getOnTimer(int i){
        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
        lock.unlock();
        if(c.moveToFirst()){
            String getString = c.getString(2);
            c.close();
            return getString;
        }
        c.close();
        return null;
    }
    String getOffTimer(int i){
        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
        lock.unlock();
        if(c.moveToFirst()){
            String getString = c.getString(3);
            c.close();
            return getString;
        }
        c.close();
        return null;
    }

    void setScheduleStart(int i,String s){
        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
        if(c.moveToFirst()){
            db.execSQL("UPDATE Buttonsfpe SET scheduleStart='"+s+"'WHERE id='"+i+"'");
        }
        c.close();
        lock.unlock();
    }
    void setScheduleStop(int i,String s){
        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
        if(c.moveToFirst()){
            db.execSQL("UPDATE Buttonsfpe SET scheduleStop='"+s+"'WHERE id='"+i+"'");
        }
        c.close();
        lock.unlock();
    }

    String getScheduleStart(int i){
        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
        lock.unlock();
        if(c.moveToFirst()){
            String getString = c.getString(4);
            c.close();
            return getString;
        }
        c.close();
        return null;
    }
    String getScheduleStop(int i){
        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
        lock.unlock();
        if(c.moveToFirst()){
            String getString = c.getString(5);
            c.close();
            return getString;
        }
        c.close();
        return null;
    }
    private boolean isExists(String i){
        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
        lock.unlock();
        Boolean isExists=c.moveToFirst();
        c.close();
        return isExists;
    }
//    void setTimerMode(String i,boolean b) {
//        lock.lock();
//        String s="false";
//        if(b){s="true";}
//        Cursor c = db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='" + i + "'", null);
//        if (c.moveToFirst()) {
//            db.execSQL("UPDATE Buttonsfpe SET timerMode='" + s + "'WHERE id='" + i + "'");
//        }
//        lock.unlock();
//    }
   void setScheduleMode (int i,String b){
       lock.lock();

            Cursor c = db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='" + i + "'", null);
            if (c.moveToFirst()) {
                db.execSQL("UPDATE Buttonsfpe SET scheduleMode='" + b + "'WHERE id='" + i + "'");
            }
            c.close();
       lock.unlock();
    }

//    boolean getTimerMode(String i){
//        lock.lock();
//        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
//        lock.unlock();
//        if(c.moveToFirst()){
//            String s=c.getString(6);
//            return s.equals("true");
//
//        }
//       return false;
//
//    }
    String getScheduleMode(int i){
        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
        lock.unlock();
        if(c.moveToFirst()){
            String getString = c.getString(7);
            c.close();
            return getString;
        }
        c.close();
        return null;

    }
    void setCurrentState (int i,boolean b){
        lock.lock();
        String s="off";
        if(b){s="on";}
        Cursor c = db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='" + i + "'", null);
        if (c.moveToFirst()) {
            db.execSQL("UPDATE Buttonsfpe SET currentState='" + s + "'WHERE id='" + i + "'");
        }
        c.close();
        lock.unlock();
    }
    boolean getCurrentState(int i){
        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
        lock.unlock();
        if(c.moveToFirst()){
            String getString = c.getString(8);
            c.close();
            return getString.equals("on");
        }
        c.close();
        return false;

    }
    void setTimerOnMode(int i,boolean b) {
        lock.lock();
        String s="off";
        if(b){s="on";}
        Cursor c = db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='" + i + "'", null);
        if (c.moveToFirst()) {
            db.execSQL("UPDATE Buttonsfpe SET timerOnMode='" + s + "'WHERE id='" + i + "'");
        }
        c.close();
        lock.unlock();
    }
    void setTimerOffMode(int i,boolean b) {
        lock.lock();
        String s="off";
        if(b){s="on";}
        Cursor c = db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='" + i + "'", null);
        if (c.moveToFirst()) {
            db.execSQL("UPDATE Buttonsfpe SET timerOffMode='" + s + "'WHERE id='" + i + "'");
        }
        c.close();
        lock.unlock();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    boolean getTimerOnMode(int i){
        lock.lock();
        try (Cursor c = db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='" + i + "'", null)) {
            lock.unlock();
            if (c.moveToFirst()) {
                String getString = c.getString(9);
                c.close();
                return getString.equals("on");
            }
            c.close();
            return false;

    }}
    boolean getTimerOffMode(int i){
        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
        lock.unlock();
        if(c.moveToFirst()){
            String getString = c.getString(10);
            c.close();
            return getString.equals("on");
        }
        c.close();
        return false;

    }

    void setScheduleOnOff(int i,boolean b) {
        lock.lock();
        String s="off";
        if(b){s="on";}
        Cursor c = db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='" + i + "'", null);
        if (c.moveToFirst()) {
            db.execSQL("UPDATE Buttonsfpe SET scheduleOnOff='" + s + "'WHERE id='" + i + "'");
        }
        c.close();
        lock.unlock();
    }

    boolean getScheduleOnOff(int i){
        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
        lock.unlock();
        if(c.moveToFirst()){
            String getString = c.getString(11);
            c.close();
            return getString.equals("on");

        }
        c.close();
        return false;

    }

    void adduser(String user, String pass){

        lock.lock();
            db.execSQL("INSERT INTO users (id,username,password,disablelogin) SELECT * FROM (SELECT '1','"+user+"','"+pass+"','enable') AS tmp WHERE NOT EXISTS(SELECT id FROM users WHERE id ='1' ) LIMIT 1;");
        lock.unlock();
    }

    boolean checkUser(String password, String username){

        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM users WHERE id='1'",null);
        lock.unlock();
        if(c.moveToFirst()){
            if(c.getString(2).equals(password)&& c.getString(1).equals(username)){
                c.close();
                return true;
            }
        }
        c.close();
        return false;

    }

    boolean checkLoginPrompt(){

        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM users WHERE id='1'",null);
        lock.unlock();
        if(c.moveToFirst()){
            if(c.getString(3).equals("enable")){
                c.close();
                return true;
            }
        }
        c.close();
        return false;

    }

    void setLoginPrompt (String value){
        lock.lock();
        Cursor c = db.rawQuery("SELECT * FROM users WHERE id='1'", null);
        if (c.moveToFirst()) {
            db.execSQL("UPDATE users SET disablelogin='" + value + "'WHERE id='1'");
        }
        c.close();
        lock.unlock();
    }

    boolean alreadyRegistered(){

        lock.lock();
        Cursor c=db.rawQuery("SELECT username FROM users WHERE id=1", null);
        lock.unlock();
        if(c.getCount()==1){
            c.close();
            return true;
        }
        else{
            c.close();
            return false;
        }


    }

    String getUsername(){

        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM users WHERE id='1'",null);
        lock.unlock();
        if(c.moveToFirst()) {

            String getString=c.getString(1);
            c.close();
            return getString;
        }
        c.close();
        return "User Name";

    }

    String getResourceId(int i){

        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
        lock.unlock();

        if(c.moveToFirst()){
            String getString=c.getString(12);
            c.close();
            return getString;
        }
        c.close();
        return null;
    }

    void setResourceId (int i,String b){
        lock.lock();
        Cursor c = db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='" + i + "'", null);
        if (c.moveToFirst()) {
            db.execSQL("UPDATE Buttonsfpe SET r_id='" + b + "'WHERE id='" + i + "'");
        }
        c.close();
        lock.unlock();
    }


    String getLastId(){

        lock.lock();
        Cursor c=db.rawQuery("SELECT MAX(id) FROM Buttonsfpe",null);
        lock.unlock();
        if(c!=null) {
            if(c.moveToFirst()){
                String getString=c.getString(0);
                c.close();
                return getString;
            }
            c.close();
            return null;
        }
        return null;
    }

    String getIP(int i){
        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
        lock.unlock();

        if(c.moveToFirst()){

            String getString=c.getString(13);
            c.close();
            return getString;
        }
        c.close();
        return null;
    }

    String getDeviceUrl(int i){
        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
        lock.unlock();

        if(c.moveToFirst()){
            String getString=c.getString(14);
            c.close();
            return getString;
        }
        c.close();
        return null;
    }

    String getPrimaryUser(int i){
        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
        lock.unlock();

        if(c.moveToFirst()){
            String getString=c.getString(15);
            c.close();
            return getString;
        }
        c.close();
        return null;
    }

//    void setPrimaryUser(int i,String n){
//        lock.lock();
//        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
//        if(c.moveToFirst()){
//            db.execSQL("UPDATE Buttonsfpe SET p_user ='"+n+"'WHERE id='"+i+"'");
//        }
//        c.close();
//        lock.unlock();
//
//    }

    String getPrimaryUserId(int i){
        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
        lock.unlock();

        if(c.moveToFirst()){
            String getString=c.getString(16);
            c.close();
            return getString;
        }
        c.close();
        return null;
    }

//    void setPrimaryUserId(int i,String n){
//        lock.lock();
//        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
//        if(c.moveToFirst()){
//            db.execSQL("UPDATE Buttonsfpe SET p_id ='"+n+"'WHERE id='"+i+"'");
//        }
//        c.close();
//        lock.unlock();
//
//    }

    Integer getiot(int i){
        lock.lock();
        Cursor c=db.rawQuery("SELECT * FROM Buttonsfpe WHERE id='"+i+"'",null);
        lock.unlock();

        if(c.moveToFirst()){
            Integer getint=c.getInt(17);
            c.close();
            return getint;
        }
        c.close();
        return null;
    }

    }