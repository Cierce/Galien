package com.eisenstudios.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by Connor on 31/10/2017.
 */

public class EnemyManager
{
    //higher index = lower on screen = higher y value
    private ArrayList<Enemy> enemies;
    private int plrGap;
    private int enmGap;
    private int enmHeight;
    private int enmColour;
    private int score;

    private long startTime;
    private long initTime;

    public EnemyManager(int plrGap, int enmGap, int enmHeight, int enmColour)
    {
        score = 0;

        this.plrGap = plrGap;
        this.enmGap = enmGap;
        this.enmHeight = enmHeight;
        this.enmColour = enmColour;

        startTime = initTime = System.currentTimeMillis();

        enemies = new ArrayList<>();
        populateEnemies();
    }

    public boolean plrCollide(Player player)
    {
        for(Enemy enemy : enemies)
        {
            if(enemy.onHit(player))
            {
                return true;
            }
        }
        return false;
    }

    private void populateEnemies()
    {
        int curY = -5*Constants.SCREEN_HEIGHT/4;

        while(curY < 0)
        {
            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH - plrGap));
            enemies.add(new Enemy(enmHeight, enmColour, xStart, curY, plrGap));
            curY += enmHeight + enmGap;
        }
    }

    public void update()
    {
        if(startTime < Constants.INIT_TIME)
        {
            startTime = Constants.INIT_TIME;
        }
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = (float)(Math.sqrt(1+(startTime - initTime)/1000.0))*Constants.SCREEN_HEIGHT /(10000.0f);
        for(Enemy enemy : enemies)
        {
            enemy.plusY(speed * elapsedTime);
        }
        if(enemies.get(enemies.size() - 1).getRect().top >= Constants.SCREEN_HEIGHT)
        {
            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH - plrGap));
            enemies.add(0, new Enemy(enmHeight, enmColour, xStart, enemies.get(0).getRect().top - enmHeight - enmGap, plrGap));
            enemies.remove(enemies.size() - 1);
            score++;
        }
    }

    public void draw(Canvas canvas)
    {
        for(Enemy enemy : enemies)
        {
            enemy.draw(canvas);
        }

        Paint paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.MAGENTA);
        canvas.drawText("SCORE: "+score, 50, 100, paint);
    }
}
