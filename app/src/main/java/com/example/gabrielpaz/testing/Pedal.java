package com.example.gabrielpaz.testing;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Gabriel Paz on 11/22/2015.
 */
public class Pedal extends ImageView {
    public int h;
    public int w;
    float x = 0.0f;
    float y = 0.0f;
    boolean moving = false;
    public Pedal(Context context) {
        super(context);
    }

}
