package com.tfitzapps.fencingscore;

import android.os.Build;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by fitzt on 10/26/2018.
 */
public class FencingTimer {
    private final Vibrator vibrator;
    private long msStart;

    private long msRemaining;
    private boolean running;
    private CountDownTimer timer;
    private TextView textView;

    public FencingTimer(long msStart, TextView textView, Vibrator vibrator){
        this.msStart = msStart;
        this.msRemaining = msStart;
        this.textView = textView;
        this.textView.setText(this.toString());
        this.vibrator = vibrator;
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

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onFinish() {
                long[] vibrationPattern = {0, 500, 50, 300, 50, 500};
                vibrator.vibrate(VibrationEffect.createWaveform(vibrationPattern, -1));
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
