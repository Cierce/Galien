package com.eisenstudios.game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Connor on 31/10/2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    private MainThread thread;
    private SceneManager scnManager;

    public GamePanel(Context context)
    {
        super(context);
        getHolder().addCallback(this);
        Constants.CURRENT_CONTEXT = context;
        thread = new MainThread(getHolder(), this);
        scnManager = new SceneManager();

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int widfth, int height)
    {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        thread = new MainThread(getHolder(), this);
        Constants.INIT_TIME = System.currentTimeMillis();
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        while (retry)
        {
            try
            {
                thread.setRunning(false);
                thread.start();
            } catch (Exception exp)
            {
                retry = false;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        scnManager.getTouch(event);
        return true;
        //return super.onTouchEvent(event);
    }

    public void update()
    {
        scnManager.update();
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        scnManager.draw(canvas);
    }
}
