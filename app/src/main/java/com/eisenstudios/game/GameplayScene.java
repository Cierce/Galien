package com.eisenstudios.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by Connor on 31/10/2017.
 */

public class GameplayScene implements Scene
{
    private Player player;
    private Point plrPoint;
    private EnemyManager enemyManager;
    private boolean isPlrMoving;
    private boolean isGameOver;
    private long gmeOvrTime;
    private Rect rectangle;

    private OrientationData orientationData;
    private long frameTime;

    public GameplayScene()
    {
        player = new Player(new Rect(100, 100, 200, 200), Color.rgb(0, 0, 255));
        plrPoint = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player.update(plrPoint);
        rectangle = new Rect();
        isPlrMoving = false;
        isGameOver = false;
        enemyManager = new EnemyManager(200, 350, 75, Color.BLACK);
        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);
        player.draw(canvas);
        enemyManager.draw(canvas);

        if(isGameOver)
        {
            Paint gmeOverPaint = new Paint();
            gmeOverPaint.setTextSize(100);
            gmeOverPaint.setColor(Color.MAGENTA);

            drawGameOver(canvas, gmeOverPaint, "GAME OVER");
        }
    }

    @Override
    public void terminate()
    {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void getTouch(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if (!isGameOver && player.getBounds().contains((int) event.getX(), (int) event.getY()))
                {
                    isPlrMoving = true;
                }
                if (isGameOver && System.currentTimeMillis() - gmeOvrTime >= 2000)
                {
                    isGameOver = false;
                    restartGameState();
                    orientationData.newGame();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isGameOver && isPlrMoving)
                {
                    plrPoint.set((int) event.getX(), (int) event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                isPlrMoving = false;
                break;
        }
    }

    public boolean restartGameState()
    {
        plrPoint = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player.update(plrPoint);
        enemyManager = new EnemyManager(200, 350, 75, Color.BLACK);
        isPlrMoving = false;
        return true;
    }

    public void update()
    {
        if (!isGameOver)
        {
            if(frameTime < Constants.INIT_TIME)
            {
                frameTime = Constants.INIT_TIME;
            }

            int elapsedTime = (int)(System.currentTimeMillis() - frameTime);
            frameTime = System.currentTimeMillis();

            if(orientationData.getOrientation() != null && orientationData.getStartOrientation() != null)
            {
                float pitch = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1];
                float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];

                float xSpeed = 2 * roll * Constants.SCREEN_WIDTH/1000f;
                float ySpeed = pitch * Constants.SCREEN_HEIGHT/1000f;

                plrPoint.x += Math.abs(xSpeed*elapsedTime) > 5 ? xSpeed*elapsedTime : 0;
                plrPoint.y -= Math.abs(ySpeed*elapsedTime) > 5 ? ySpeed*elapsedTime : 0;
            }

            if(plrPoint.x < 0)
            {
                plrPoint.x = 0;
            }
            else if(plrPoint.x > Constants.SCREEN_WIDTH)
            {
                plrPoint.x = Constants.SCREEN_WIDTH;
            }

            if(plrPoint.y < 0)
            {
                plrPoint.y = Constants.SCREEN_HEIGHT;
            }
            else if(plrPoint.y > Constants.SCREEN_HEIGHT)
            {
                plrPoint.y = Constants.SCREEN_HEIGHT;
            }

            player.update(plrPoint);
            enemyManager.update();
            if (enemyManager.plrCollide(player))
            {
                isGameOver = true;
                gmeOvrTime = System.currentTimeMillis();
            }
        }
    }

    private void drawGameOver(Canvas canvas, Paint paint, String txt)
    {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(rectangle);
        int clpWidth = rectangle.width();
        int clpHeight = rectangle.height();
        paint.getTextBounds(txt, 0, txt.length(), rectangle);
        float xCord = clpWidth / 2f - rectangle.width() / 2f - rectangle.left;
        float yCord = clpHeight / 2f + rectangle.height() / 2f - rectangle.bottom;
        canvas.drawText(txt, xCord, yCord, paint);
    }
}
