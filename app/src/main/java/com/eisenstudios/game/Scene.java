package com.eisenstudios.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Connor on 31/10/2017.
 */

public interface Scene
{
    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void getTouch(MotionEvent event);
}
