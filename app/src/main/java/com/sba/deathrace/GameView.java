package com.sba.deathrace;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class GameView extends View
{
    int canvasWidth,canvasHeight;
    Bitmap background,background2;
    Bitmap barrier,barrier3;
    Bitmap car;
    int swipe = 0;
    Bitmap controllers[] = new Bitmap[2];

    Bitmap pauseOrResume[] = new Bitmap[2];

    int carXPosition,carYPosition;
    int goLeftXPosition,goLeftYPosition;
    int goRightXPosition,goRightYPosition;

    boolean isGoLeft,isGoRight = false;
    boolean canDraw = true;
    Bitmap scaled;
    boolean isGamePlay = true;
    Intent intent;

    int barrierSpeed = 10;
    int carSpeed = 10;

    int swipeSpeed = 10;


    long score = 0;

    static boolean isDrawOver = false;



    boolean isFirstTime = true;
    boolean isBarrier4ShowUpFirstTime = true;
    boolean isBarrier3ShowUpFirstTime = true;

    Random r1,r2;
    int low,high,barrier4X,barrier4Y,barrier3X,barrier3Y;

    long startTime;
    long estimatedTime;
    Paint scorePaint = new Paint();

    long startTimeForSpeedUp;

    static boolean isPause = false;

    long pauseTime;

    public GameView(Context context)
    {
        super(context);
        background = BitmapFactory.decodeResource(getResources(),R.drawable.background);
        background2 = BitmapFactory.decodeResource(getResources(),R.drawable.background2);
        car = BitmapFactory.decodeResource(getResources(),R.drawable.car);
        controllers[0] = BitmapFactory.decodeResource(getResources(),R.drawable.goleft);
        controllers[1] = BitmapFactory.decodeResource(getResources(),R.drawable.goright);
        barrier = BitmapFactory.decodeResource(getResources(),R.drawable.barriers);
        barrier3 = BitmapFactory.decodeResource(getResources(),R.drawable.barriers3);

        r1 = new Random();
        r2 = new Random();

        scaled = Bitmap.createScaledBitmap(background, MainActivity.width,background.getHeight(), true);
        intent = new Intent(getContext(),GameOverActivity.class);
        startTime = System.currentTimeMillis();

        startTimeForSpeedUp = startTime;


        scorePaint.setColor(Color.WHITE);
        scorePaint.setAntiAlias(true);
        scorePaint.setTextSize(50);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);


        pauseOrResume[0] = BitmapFactory.decodeResource(getResources(),R.drawable.pause);
        pauseOrResume[1] = BitmapFactory.decodeResource(getResources(),R.drawable.resume);



    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        if(canDraw)
        {
            canvasWidth = getWidth();
            canvasHeight = getHeight();

            if(isFirstTime)
            {
                carXPosition = canvasWidth/2 - car.getWidth() / 2;
                carYPosition = canvasHeight - car.getHeight() - 200;
                goLeftXPosition = 60;
                goLeftYPosition = canvasHeight - car.getHeight();
                goRightXPosition = canvasWidth - controllers[1].getWidth() - 60;
                goRightYPosition = canvasHeight - car.getHeight();
                isFirstTime = false;
            }

            canvas.drawBitmap(scaled,0,swipe, null);
            canvas.drawBitmap(scaled,0,swipe - background.getHeight(), null);


            if(isPause)
            {
                canvas.drawBitmap(pauseOrResume[1],MainActivity.width / 2 - pauseOrResume[1].getWidth() / 2,30,null);

            }
            else if(!isPause)
            {
                score++;
                canvas.drawBitmap(pauseOrResume[0],MainActivity.width / 2 - pauseOrResume[1].getWidth() / 2,30,null);
            }





            if(isBarrier4ShowUpFirstTime && isGamePlay)
            {
                low = 0;
                high = canvasWidth - barrier.getWidth();
                barrier4X = r1.nextInt(high-low) + low;
                barrier3X = r1.nextInt(high-low) + low;

                barrier4Y = -200;
                barrier3Y = -700;
                isBarrier4ShowUpFirstTime = false;


            }


            if(barrier4Y > (canvasHeight + 1000))
            {
                isBarrier4ShowUpFirstTime = true;
            }
            if(barrier3Y > (canvasHeight + 1000))
            {
                isBarrier3ShowUpFirstTime = true;
            }


            if(swipe + swipeSpeed >= background.getHeight())
            {
                swipe = 0;
            }
            if(isGamePlay)
            {
                swipe = swipe + swipeSpeed;
                barrier4Y = barrier4Y + barrierSpeed;
                barrier3Y = barrier3Y + barrierSpeed;

            }
            canvas.drawBitmap(barrier,barrier4X,barrier4Y,null);
            canvas.drawBitmap(barrier3,barrier3X,barrier3Y,null);










            if(carXPosition < 0 )
            {
                carXPosition = 0;
            }
            if(carXPosition > canvasWidth - car.getWidth())
            {
                carXPosition = canvasWidth - car.getWidth();
            }



            canvas.drawBitmap(car,carXPosition,carYPosition,null);
            canvas.drawBitmap(controllers[0],goLeftXPosition,goLeftYPosition,null);
            canvas.drawBitmap(controllers[1],goRightXPosition,goRightYPosition,null);

            if(isGamePlay)
            {
                if(isGoRight)
                {
                    carXPosition = carXPosition + carSpeed;
                }
                if(isGoLeft)
                {
                    carXPosition = carXPosition - carSpeed;
                }
            }


            if(isCarHitTheBarrier(barrier4X,barrier4Y))
            {
                isDrawOver = true;
                isGamePlay = false;
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("Score",score);
                getContext().startActivity(intent);

            }
            if(isCarHitTheBarrier2(barrier3X,barrier3Y))
            {
                isDrawOver = true;
                isGamePlay = false;
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("Score",score);
                getContext().startActivity(intent);

            }

            estimatedTime = (System.currentTimeMillis() - startTime) / 60;
            canvas.drawText("" + (score) ,50,70,scorePaint);


            long elapsedTime = System.currentTimeMillis() - startTimeForSpeedUp;
            long elapsedSeconds = elapsedTime / 1000;
            if(elapsedSeconds == 5)
            {
                barrierSpeed++;
                carSpeed++;
                swipeSpeed = barrierSpeed;

                startTimeForSpeedUp = System.currentTimeMillis();
            }
        }














    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                //Check if the x and y position of the touch is inside the bitmap
                if( x > goLeftXPosition && x < goLeftXPosition + controllers[0].getWidth() && y > goLeftYPosition && y < goLeftYPosition + controllers[0].getHeight() )
                {
                    isGoLeft = true;
                    isGoRight = false;
                }
                if( x > goRightXPosition && x < goRightXPosition + controllers[1].getWidth() && y > goRightYPosition && y < goRightYPosition + controllers[1].getHeight() )
                {
                   isGoLeft = false;
                   isGoRight = true;
                }
                if( x > MainActivity.width / 2 - pauseOrResume[0].getWidth() / 2 && x < MainActivity.width / 2 - pauseOrResume[0].getWidth() / 2 + pauseOrResume[0].getWidth() && y > 30 && y < 30 + pauseOrResume[0].getHeight() )
                {
                    if(!isPause)
                    {
                        isPause = true;
                    }
                    else
                    {
                        isPause = false;
                    }

                }
                return true;
        }
        return false;
    }
    public  boolean isCarHitTheBarrier(int x, int y)
    {
        if(carXPosition - car.getWidth() / 2 < x && x < (carXPosition + car.getWidth() / 2) && carYPosition - car.getHeight() / 2 < y && y < (carYPosition +  car.getHeight() / 2))
        {
            return true;
        }
        if((carXPosition > x - barrier.getWidth() / 2) && carXPosition < x + barrier.getWidth() && carYPosition - car.getHeight() / 2 < y && y < (carYPosition +  car.getHeight() / 2))
        {
            return true;
        }
        return false;
    }
    public  boolean isCarHitTheBarrier2(int x, int y)
    {
        if(carXPosition - car.getWidth() / 2 < x && x < (carXPosition + car.getWidth() / 2) && carYPosition - car.getHeight() / 2 < y && y < (carYPosition +  car.getHeight() / 2))
        {
            return true;
        }
        if((carXPosition > x - barrier3.getWidth() / 2) && carXPosition < x + barrier3.getWidth() && carYPosition - car.getHeight() / 2 < y && y < (carYPosition +  car.getHeight() / 2))
        {
            return true;
        }
        return false;
    }






}
