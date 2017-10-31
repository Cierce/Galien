package com.eisenstudios.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Connor on 31/10/2017.
 */

public class Enemy implements GameObject
{
    private Rect enm;
    private int colour;
    private Rect enmTwo;

    public Enemy(int enmHeight, int colour, int startX, int startY, int plrGap)
    {
        this.colour = colour;
        enm = new Rect(0, startY, startX, startY + enmHeight);
        enmTwo = new Rect(startX + plrGap, startY, Constants.SCREEN_WIDTH, startY + enmHeight);
    }

    public boolean onHit(Player player)
    {
        return Rect.intersects(enm, player.getBounds()) || Rect.intersects(enmTwo, player.getBounds());
    }

    @Override
    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(colour);
        canvas.drawRect(enm, paint);
        canvas.drawRect(enmTwo, paint);
    }

    @Override
    public void update()
    {

    }

    public Rect getRect()
    {
        return enm;
    }

    public void plusY(float y)
    {
        enm.top += y;
        enm.bottom += y;
        enmTwo.top += y;
        enmTwo.bottom += y;
    }
}
