
package com.yahoo.raczkowskir.moveup30;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public int counter = 100;
    public int preTimerCounter = 3;
    public int counterStartValue = 100;

    Button buttonStart, buttonStop, buttonClear;
    TextView textView;
    EditText editTextMin, editTextSec;
    Boolean isStopped, isCleared;
    boolean isFirstStart = true;
    public ChainedCountDownTimer timer1;
    public ChainedCountDownTimer timer2;
    ConstraintLayout constraintLayout;
    MediaPlayer mp, mp_finish;
    Context context = this;

    public void protectValue(EditText editText, int valueOfTiemr) {
        String contentofEditTextSec = String.valueOf(editText.getText());
        if ( contentofEditTextSec.length() > 2
                || Integer.parseInt(contentofEditTextSec) > valueOfTiemr ) {
            editText.setText(String.valueOf(valueOfTiemr));
        }
        Log.d("Raki_3", String.valueOf("protectValue"));
    }

    public int getTimer(EditText aEditTextMin, EditText aEditTextSec, TextView aTextView) {
        Log.d("Raki_4", String.valueOf("getTimer"));
        int editTextMin = Integer.parseInt(String.valueOf(aEditTextMin.getText()));
        int editTextSec = Integer.parseInt(String.valueOf(aEditTextSec.getText()));
        int fullTimer = (editTextMin*60) + editTextSec;
        Log.d("Raki", "editTextMin " + editTextMin);
        Log.d("Raki", "editTextSec " + editTextSec);
        Log.d("Raki", "Full timer " + fullTimer);


        counter = fullTimer;
        counterStartValue = fullTimer;

        if (isFirstStart) {
            aTextView.setText(String.valueOf(fullTimer));
            isFirstStart = false;
        }

        return fullTimer;
    }

    public Boolean startPreTimer(TextView aTextView) {
        timer1 = new ChainedCountDownTimer((4000), 1000){

            public void onTick(long millisUntilFinished){
                try {
                    if (mp.isPlaying()) {
                        mp.stop();
                        mp.release();
                        mp = MediaPlayer.create(context, R.raw.alarm_clock);
                    } mp.start();
                } catch(Exception e) { e.printStackTrace();
                }

                aTextView.setText(String.valueOf(preTimerCounter));
                preTimerCounter--;
                Log.d("Raki_1", String.valueOf(preTimerCounter));
            }
            public  void onFinish(){
                //alarm_clock_finish

                try {
                    if (mp_finish.isPlaying()) {
                        mp_finish.stop();
                        mp_finish.release();
                        mp_finish = MediaPlayer.create(context, R.raw.alarm_clock_finish);
                    } mp_finish.start();
                } catch(Exception e) { e.printStackTrace();
                }

                constraintLayout.setBackgroundColor(Color.rgb(76, 175, 80));
                Log.d("Raki_2", String.valueOf(preTimerCounter));
                aTextView.setText(String.valueOf(0));
                preTimerCounter = 3;
            }
        };
        return true;
    }

    public void manageTimer(TextView aTextView) {
        Log.d("Raki_5", String.valueOf("manageTimer"));
        isStopped = false;
        isCleared = false;

        String currentTimer = String.valueOf(textView.getText());
        counter = Integer.parseInt(currentTimer);

        timer2 = new ChainedCountDownTimer((counter*100), 100){
            public void onTick(long millisUntilFinished){
                Log.d("Raki_7", String.valueOf(counter));
                if (!isStopped) {
                    counter--;
                    aTextView.setText(String.valueOf(counter));
                }
                else {
                    this.cancel();
                    if (!isCleared) {
                        counter = Integer.parseInt(String.valueOf(aTextView.getText()));
                        Log.d("counter", String.valueOf(counter));
                    } else {
                        counter = Integer.parseInt(String.valueOf(0));
                        Log.d("Raki", "Cleared");
                    }
                }
            }
            public  void onFinish(){
                if (!isStopped) {
                    aTextView.setText(String.valueOf(counterStartValue));
                    constraintLayout.setBackgroundColor(Color.rgb(255, 255, 255));
                }
                else {
                    constraintLayout.setBackgroundColor(Color.rgb(255, 255, 255));
                    Log.d("Raki", "timerCanceled");
                }
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout = findViewById(R.id.viewId);

        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);
        buttonClear = findViewById(R.id.buttonClear);

        textView = findViewById(R.id.textViewTimer);
        editTextSec = findViewById(R.id.editTextSec);
        editTextMin = findViewById(R.id.editTextMin);
        mp = MediaPlayer.create(context, R.raw.alarm_clock);
        mp_finish = MediaPlayer.create(context, R.raw.alarm_clock_finish);

        buttonStart.setOnClickListener(v -> {
            startPreTimer(textView);
            protectValue(editTextSec, 60);
            protectValue(editTextMin, 99);
            getTimer(editTextMin, editTextSec, textView);
            manageTimer(textView);
            Log.d("Raki_6", String.valueOf("onCreate"));
            constraintLayout.setBackgroundColor(Color.rgb(202, 32, 32));
            timer1.setNext(timer2).start();
        });

        buttonStop.setOnClickListener(v -> isStopped = true);

        buttonClear.setOnClickListener(v -> {
            constraintLayout.setBackgroundColor(Color.rgb(255, 255, 255));
            isFirstStart = true;
            protectValue(editTextSec, 60);
            protectValue(editTextMin, 99);
            getTimer(editTextMin, editTextSec, textView);
            isStopped = true;
            isCleared = true;
        });
    }
}