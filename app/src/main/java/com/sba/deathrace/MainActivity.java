package com.sba.deathrace;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private static final long Interval = 30;
    GameView gameView;
    static int height;
    static int width;

    boolean isFirstRefresh = true;


    int backPressedTimes = 0;

    @Override
    public void onBackPressed()
    {
        backPressedTimes++;
        if(backPressedTimes == 2)
        {
            super.onBackPressed();
        }
        else
        {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                backPressedTimes = 0;
            }
        },2500);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try
        {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            height = displayMetrics.heightPixels;
            width = displayMetrics.widthPixels;

            gameView = new GameView(getApplicationContext());
            setContentView(gameView);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    handler.post(new Runnable() {
                        @Override
                        public void run()
                        {
                          //  if(GameView.isPause)
                           // {
                               // if(isFirstRefresh)
                               // {
                               //     gameView.invalidate();
                               // }

                                ///isFirstRefresh = false;
                                //onPause();
                                //return;

                           // }
                           // else
                           // {
                                    //onResume();
                                    //isFirstRefresh = true;

                           // }
                            gameView.invalidate();

                        }
                    });
                }
            },0,Interval);
        }
        catch (Exception e)
        {

        }




    }
}
