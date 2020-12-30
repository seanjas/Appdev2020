package com.example.selection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class LoopTimer extends AppCompatActivity {
    TextView lblTimer,lblStartTime,lblEnd,lblElapse;
    Button btnStart,btnStop;
    Timer timer;
    long startTime,millis,buffer,updateTime;
    Handler myHandler =new Handler();
    boolean hasStarted =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_timer);

        lblTimer=findViewById(R.id.lblTimer);
        lblStartTime=findViewById(R.id.lblStartTime);
        lblEnd=findViewById(R.id.lblEnd);
        lblElapse=findViewById(R.id.lblElapse);
        btnStart=findViewById(R.id.btnStart);
        btnStop=findViewById(R.id.btnStop);

        btnStart.setOnClickListener(v-> {
            startTime = SystemClock.uptimeMillis();
            myHandler.postDelayed(runningTime , 0);
            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
        });
        btnStop.setOnClickListener(v->{
            hasStarted=false;
            buffer  += millis;
            lblEnd.setText(getDisplayTime(buffer));
            lblElapse.setText(getDisplayTime(millis));
            myHandler.removeCallbacks(runningTime);
            btnStop.setEnabled(false);
            btnStart.setEnabled(true);
            lblTimer.setText("00:00:00");
        });
    }
    private Runnable runningTime = new Runnable() {
        @Override
        public void run() {
            millis = SystemClock.uptimeMillis() - startTime;
            if (!hasStarted){
                hasStarted=true;
                lblStartTime.setText(getDisplayTime(buffer));
            }
            updateTime = buffer + millis;
            lblTimer.setText(getDisplayTime(updateTime));
            myHandler.postDelayed(this,0);

        }
    };
    private String getDisplayTime(long time){
        int sec= (int) (time/1000);
        int min = sec /60;
        sec = sec % 60;
        int milliseconds = (int) (time % 1000);
        return String.format("%02d:%02d:%02d",min,sec,milliseconds);
    }
}