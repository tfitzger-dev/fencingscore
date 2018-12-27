package com.tfitzapps.fencingscore;

import android.os.CountDownTimer;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by fitzt on 10/26/2018.
 */
public class FencingTimer {
    private long msStart;

    private long msRemaining;
    private boolean running;
    private CountDownTimer timer;
    private TextView textView;

    public FencingTimer(long msStart, TextView textView){
        this.msStart = msStart;
        this.msRemaining = msStart;
        this.textView = textView;
        this.textView.setText(this.toString());
    }

    public void start(){
        msRemaining = msStart;
        running = true;
        startTimer();
    }

    public void resume(){
        running = true;
        startTimer();
    }

    public void pause(){
        timer.cancel();
        running = false;
    }

    public void stop(){
        timer.cancel();
        running = false;
    }

    private void startTimer(){
        timer = new CountDownTimer(msRemaining, 1000) {
            @Override
            public void onTick(long msLeft) {
                msRemaining = msLeft;
                //textView.setText(String.format("%02d:%02d", getMinutes(), getSeconds()));
                textView.setText(FencingTimer.this.toString());
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }

    public long getMinutes(){
        return (msRemaining / 1000) / 60;
    }

    public long getSeconds(){
        return (msRemaining / 1000) % 60;
    }

    @Override
    public String toString(){
        return String.format("%02d:%02d", getMinutes(), getSeconds());
    }

    public boolean isRunning() {
        return running;
    }

    public long getMsStart() {
        return msStart;
    }
}
