package com.eisenstudios.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Connor on 31/10/2017.
 */

public class Player implements GameObject
{
    private Rect plr;
    private int colour;

    private Animation stoodStill;
    private Animation walkRight;
    private Animation walkLeft;
    private AnimationManager animeManager;

    public Player(Rect rect, int colour)
    {
        this.plr = rect;
        this.colour = colour;

        BitmapFactory bitFactory = new BitmapFactory();
        Bitmap idle = bitFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.galien);
        Bitmap rightOne = bitFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.galien_walk1);
        Bitmap rightTwo = bitFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.galien_walk2);

        stoodStill = new Animation(new Bitmap[]{idle}, 2);
        walkRight = new Animation(new Bitmap[]{rightOne, rightTwo}, 0.5f);

        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);
        Bitmap leftOne = Bitmap.createBitmap(rightOne, 0, 0, rightOne.getWidth(), rightOne.getHeight(), matrix, false);
        Bitmap leftTwo = Bitmap.createBitmap(rightTwo, 0, 0, rightTwo.getWidth(), rightTwo.getHeight(), matrix, false);
        walkLeft = new Animation(new Bitmap[]{leftOne, leftTwo}, 0.5f);

        animeManager = new AnimationManager(new Animation[]{stoodStill, walkRight, walkLeft});
    }

    @Override
    public void draw(Canvas canvas)
    {
        animeManager.draw(canvas, plr);
    }

    @Override
    public void update()
    {
        animeManager.update();
    }

    public void update(Point point)
    {
        float plrOldLeft = plr.left;
        plr.set(point.x - plr.width()/2, point.y - plr.height()/2,  point.x + plr.width()/2,  point.y + plr.height()/2);
        int state = 0;
        if(plr.left - plrOldLeft > 5)
        {
            state = 1;
        }
        else if (plr.left - plrOldLeft < -5)
        {
            state = 2;
        }
        animeManager.playAnimation(state);
        animeManager.update();
    }

    public Rect getBounds()
    {
        return plr;
    }
}
