package com.eisenstudios.game;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Connor on 31/10/2017.
 */

public class AnimationManager
{
    private Animation[] animations;
    private int animationIndex;
    public AnimationManager(Animation[] animations)
    {
        this.animations = animations;
        animationIndex = 0;
    }

    public void playAnimation(int animationIndex)
    {
        for(int i = 0; i < animations.length; i++)
        {
            if(i == animationIndex)
            {
                if(!animations[animationIndex].isPlaying())
                {
                    animations[i].play();
                }
            }
            else
            {
                animations[i].stop();
            }
        }
        this.animationIndex = animationIndex;
    }

    public void draw(Canvas canvas, Rect rectangle)
    {
        if(animations[animationIndex].isPlaying())
        {
            animations[animationIndex].draw(canvas, rectangle);
        }
    }

    public void update()
    {
        if(animations[animationIndex].isPlaying())
        {
            animations[animationIndex].update();
        }
    }
}
