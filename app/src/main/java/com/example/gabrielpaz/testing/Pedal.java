package com.example.gabrielpaz.testing;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Gabriel Paz on 11/22/2015.
 */
public class Pedal extends ImageView implements View.OnTouchListener{
    public int h;
    public int w;
    float x = 0.0f;
    float y = 0.0f;
    boolean moving = false;
    public Pedal(Context context) {
        super(context);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                moving = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if(moving)
                {
                    x = event.getRawX() - this.getWidth()/2;
                    y = event.getRawY() - this.getHeight()/2;
                    this.setX(x);
                    this.setY(y);
                }
                break;
            case MotionEvent.ACTION_UP:
                moving = false;
                break;
        }
        return true;
    }
}
