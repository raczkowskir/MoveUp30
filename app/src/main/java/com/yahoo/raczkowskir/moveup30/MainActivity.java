package com.yahoo.raczkowskir.moveup30;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {
    private final MainActivity mMainActivity;
    private final Context mContext;
    TextView mTextView;
    EditText mEditTextMin, mEditTextSec;
    ConstraintLayout mConstraintLayout;
    MediaPlayer mMp, mMp_finish;
    private TimerUtilities mTimerUtilities;

    public MainActivity() {
        mMainActivity = this;
        mContext = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConstraintLayout = findViewById(R.id.viewId);
        Button buttonStart = findViewById(R.id.buttonStart);
        Button buttonStop = findViewById(R.id.buttonStop);
        Button buttonClear = findViewById(R.id.buttonClear);
        mTextView = findViewById(R.id.textViewTimer);
        mEditTextSec = findViewById(R.id.editTextSec);
        mEditTextMin = findViewById(R.id.editTextMin);
        mMp = MediaPlayer.create(mContext, R.raw.alarm_clock);
        mMp_finish = MediaPlayer.create(mContext, R.raw.alarm_clock_finish);
        mTimerUtilities = new TimerUtilities(mContext, mMainActivity);

        buttonStart.setOnClickListener(v -> mTimerUtilities.handelButtonStart());
        buttonStop.setOnClickListener(v -> mTimerUtilities.mIsStopped = true);
        buttonClear.setOnClickListener(v -> mTimerUtilities.handelButtonClear());
    }
}