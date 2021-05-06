package com.yahoo.raczkowskir.moveup30;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import static com.yahoo.raczkowskir.moveup30.R.id.buttonClear;
import static com.yahoo.raczkowskir.moveup30.R.id.buttonStart;
import static com.yahoo.raczkowskir.moveup30.R.id.buttonStop;
import static com.yahoo.raczkowskir.moveup30.R.id.editTextMin;
import static com.yahoo.raczkowskir.moveup30.R.id.editTextSec;
import static com.yahoo.raczkowskir.moveup30.R.id.textViewTimer;
import static com.yahoo.raczkowskir.moveup30.R.id.viewId;
import static com.yahoo.raczkowskir.moveup30.R.raw;

public class MainActivity extends AppCompatActivity {
    /*private final */MainActivity mMainActivity;
    /*private final */Context mContext;
    TextView mTextView;
    EditText mEditTextMin, mEditTextSec;
    ConstraintLayout mConstraintLayout;
    MediaPlayer mMp;
    private TimerUtilities mTimerUtilities;

    public MainActivity() {
        mMainActivity = this;
        mContext = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeFieldsForTimerUtilities();

        Button startButton = findViewById(buttonStart);
        Button stopButton = findViewById(buttonStop);
        Button ClearButton = findViewById(buttonClear);
        mTimerUtilities = new TimerUtilities(mContext, mMainActivity);

        startButton.setOnClickListener(v -> mTimerUtilities.handelButtonStart());
        stopButton.setOnClickListener(v -> mTimerUtilities.setMIsStopped(true));
        ClearButton.setOnClickListener(v -> mTimerUtilities.handelButtonClear());

    }

    private void initializeFieldsForTimerUtilities() {
        mConstraintLayout = findViewById(viewId);
        mTextView = findViewById(textViewTimer);
        mEditTextSec = findViewById(editTextSec);
        mEditTextMin = findViewById(editTextMin);
        mMp = MediaPlayer.create(mContext, raw.alarm_clock);
    }
}