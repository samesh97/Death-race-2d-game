package com.sba.deathrace;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    ImageView gameover;
    TextView score;
    long marks;
    Button goToMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        gameover = (ImageView) findViewById(R.id.gameover);
        score = (TextView) findViewById(R.id.score);

        Intent intent = getIntent();
        marks =  intent.getLongExtra("Score",0);


        goToMain = (Button) findViewById(R.id.goToMain);

        goToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                GameView.isDrawOver = false;
                Intent intent = new Intent(getApplicationContext(),FirstActivity.class);
                startActivity(intent);
                finish();
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        long currentScore = preferences.getLong("Score", 0);


        if(currentScore != 0)
        {
            if(marks > currentScore)
            {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putLong("Score",marks);
                editor.apply();
            }
        }
        else
        {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong("Score",marks);
            editor.apply();
        }



        score.setText("Your Score : " + marks);

        gameover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();

            }
        });


    }
}
