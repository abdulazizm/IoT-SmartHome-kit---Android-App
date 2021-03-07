package com.FingerPointEngg.abdul.test001;

public class DataModel {

    String name;
    String offtimer;
    int id_;
    int image;
    String ontimer;
    String scheontimer;
    String scheofftimer;

    public DataModel(String name, String offtimer, int id_, int image, String ontimer, String scheontimer, String scheofftimer) {
        this.name = name;
        this.offtimer = offtimer;
        this.id_ = id_;
        this.image=image;
        this.ontimer=ontimer;
        this.scheontimer=scheontimer;
        this.scheofftimer=scheofftimer;
    }

    public String getName() {

        return name;
    }

    public String getOfftimer() {

        return offtimer;
    }

    public int getImage() {

        return image;
    }

    public int getId() {

        return id_;
    }

    public String getonTimer() {

        return ontimer;
    }

    public String getScheontimer() {

        return scheontimer;
    }

    public String getScheofftimer() {

        return scheofftimer;
    }
}
