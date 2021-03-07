package com.FingerPointEngg.abdul.test001;

import android.graphics.drawable.VectorDrawable;

public class ImageItem {
    private VectorDrawable image;
    private String title;

    private int btn_id;

    public ImageItem(VectorDrawable image, String title, int btn_id) {
        super();
        this.image = image;
        this.title = title;
        this.btn_id = btn_id;
    }

    public VectorDrawable getImage() {
        return image;
    }

    public void setImage(VectorDrawable image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return btn_id;
    }

    public void setId(int btn_id) {
        this.btn_id = btn_id;
    }
}
