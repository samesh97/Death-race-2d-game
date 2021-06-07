package com.sba.deathrace;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    ImageView play;
    Button highScore;
    long score;
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
        setContentView(R.layout.activity_first);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        play = (ImageView) findViewById(R.id.play);
        highScore = (Button) findViewById(R.id.highScore);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        score = preferences.getLong("Score", 0);



        highScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                if(score != 0)
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(FirstActivity.this);
                    dialog.setTitle("High Score");
                    dialog.setMessage("Score : "+score);
                    dialog.setIcon(R.drawable.highscore);
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
                else
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(FirstActivity.this);
                    dialog.setTitle("High Score");
                    dialog.setMessage( "Score : " + "0");
                    dialog.setIcon(R.drawable.highscore);
                    dialog.setCancelable(false);

                    dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }



            }
        });



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();

            }
        });
    }
}
