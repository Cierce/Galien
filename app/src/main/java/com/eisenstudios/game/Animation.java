package com.eisenstudios.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Connor on 31/10/2017.
 */

public class Animation
{
    private Bitmap[] frames;
    private int frameIndex;
    private float frameTime;
    private float lastFrame;
    private boolean isPlaying;

    public Animation(Bitmap[] frames, float animationTime)
    {
        this.frames = frames;
        this.frameIndex = 0;
        frameTime = animationTime/frames.length;
        lastFrame = System.currentTimeMillis();
        isPlaying = false;
    }

    public boolean isPlaying()
    {
        return isPlaying;
    }

    public void play()
    {
        frameIndex = 0;
        lastFrame = System.currentTimeMillis();
        isPlaying = true;
    }

    public void stop()
    {
        isPlaying = false;
    }

    public void update()
    {
        if(!isPlaying)
        {
            return;
        }
        if(System.currentTimeMillis() - lastFrame > frameTime*1000)
        {
            frameIndex++;
            frameIndex = frameIndex >= frames.length ? 0 : frameIndex;
            lastFrame = System.currentTimeMillis();
        }
    }

    public void draw(Canvas canvas, Rect destination)
    {
        if(!isPlaying)
        {
            return;
        }
        scaleRectangle(destination);
        canvas.drawBitmap(frames[frameIndex], null, destination, new Paint());
    }

    private void scaleRectangle(Rect rectangle)
    {
        float whRatio = (float)(frames[frameIndex].getWidth())/frames[frameIndex].getHeight();
        if(rectangle.width() > rectangle.height())
        {
            rectangle.left = rectangle.right - (int)(rectangle.height()* whRatio);
        }
        else
        {
            rectangle.top = rectangle.bottom - (int)(rectangle.width()* (1/whRatio));
        }
    }
}
