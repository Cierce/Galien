package com.eisenstudios.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Connor on 31/10/2017.
 */

public class SceneManager
{
    private ArrayList<Scene> scenes = new ArrayList<>();
    public static int ACTIVE_SCENE;

    public SceneManager()
    {
        ACTIVE_SCENE = 0;
        scenes.add(new GameplayScene());
    }

    public void setScene(int activeScene)
    {
        this.ACTIVE_SCENE = activeScene;
    }

    public void draw(Canvas canvas)
    {
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }

     public void update()
     {
         scenes.get(ACTIVE_SCENE).update();
     }

     public void getTouch(MotionEvent event)
     {
        scenes.get(ACTIVE_SCENE).getTouch(event);
     }
}
