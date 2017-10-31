package com.eisenstudios.game;

import android.graphics.Canvas;

/**
 * Created by Connor on 31/10/2017.
 */

public interface GameObject
{
    public void draw(Canvas canvas);
    public void update();
}
