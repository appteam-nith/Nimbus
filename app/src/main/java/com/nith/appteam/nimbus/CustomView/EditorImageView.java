package com.nith.appteam.nimbus.CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by sahil on 17/2/17.
 */

public class EditorImageView extends ImageView {
    private Bitmap bitmap;
    private String absoluteUrl;

    public EditorImageView(Context context) {
        super(context);
    }

    public EditorImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditorImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getAbsoluteUrl() {
        return absoluteUrl;
    }

    public void setAbsoluteUrl(String absoluteUrl) {
        this.absoluteUrl = absoluteUrl;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
