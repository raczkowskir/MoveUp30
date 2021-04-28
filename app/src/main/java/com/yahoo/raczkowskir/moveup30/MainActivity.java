package com.yahoo.raczkowskir.moveup30;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button buttonStart, buttonStop, buttonClear;
    TextView textView;
    EditText editTextMin, editTextSec;
    ConstraintLayout constraintLayout;
    MainActivity mainActivity = this;
    TimerUtilities timerUtilities;

    MediaPlayer mp, mp_finish;
    Context context = this;

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

        timerUtilities = new TimerUtilities(context, mainActivity);
        mp = MediaPlayer.create(context, R.raw.alarm_clock);
        mp_finish = MediaPlayer.create(context, R.raw.alarm_clock_finish);

        buttonStart.setOnClickListener(v -> {
            timerUtilities.handelButtonStart();
        });

        buttonStop.setOnClickListener(v -> timerUtilities.isStopped = true);

        buttonClear.setOnClickListener(v -> {
            timerUtilities.handelButtonClear();
        });
    }
}