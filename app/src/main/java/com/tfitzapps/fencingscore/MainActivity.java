package com.tfitzapps.fencingscore;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends WearableActivity {
    private Button btnLeft, btnRight, btnMinSwap;
    private ImageButton btnPlayPause;
    private TextView timerText;

    private Fencer left, right;
    private FencingTimer timer;

    private int timerMins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);
        btnMinSwap = findViewById(R.id.btnMinSwap);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        timerText = findViewById(R.id.timerText);

        initialize();
        // Enables Always-on
        setAmbientEnabled();
    }

    private void initialize(){
        timerMins = 3;
        timer = new FencingTimer(timerMins * 60 * 1000, timerText);
        left = new Fencer();
        right = new Fencer();

        ((RadioButton)findViewById(R.id.cardLeft)).setChecked(false);
        ((RadioButton)findViewById(R.id.cardRight)).setChecked(false);

        setScoreText();
    }

    public void increaseScore(View view) {
        switch(view.getId()){
            case R.id.btnLeft:
                left.addPoint();
                break;
            case R.id.btnRight:
                right.addPoint();
                break;
            case R.id.btnDouble:
                right.addPoint();
                left.addPoint();
                break;
        }

        setScoreText();
    }

    void setScoreText(){
        btnRight.setText(right.getScore()+"");
        btnLeft.setText(left.getScore()+"");
    }

    public void applyCard(View view) {
        RadioButton button;
        if(view instanceof RadioButton){
            button = (RadioButton) view;
            switch(button.getId()){
                case R.id.cardLeft:
                    if(left.isCarded()) right.addPoint();
                    left.setCarded();
                    break;
                case R.id.cardRight:
                    if(right.isCarded()) left.addPoint();
                    right.setCarded();
                    break;
            }
            setScoreText();
        }
    }

    public void toggleTimer(View view){
        if(timer.isRunning()){
            timer.pause();
            btnPlayPause.setImageResource(android.R.drawable.ic_media_play);
        }
        else{
            timer.resume();
            btnPlayPause.setImageResource(android.R.drawable.ic_media_pause);
        }
    }

    public void minSwap(View view){
        btnMinSwap.setText(timerMins + "M");
        timerMins = (timerMins == 1) ? 3 : 1;
        timer = new FencingTimer(timerMins * 60 * 1000, timerText);
    }

    public void reset(View view) {
        initialize();
    }
}
