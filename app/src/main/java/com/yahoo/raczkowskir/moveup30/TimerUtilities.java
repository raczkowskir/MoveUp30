package com.yahoo.raczkowskir.moveup30;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.widget.EditText;
import android.widget.TextView;

public class TimerUtilities {
    Boolean mIsStopped, mIsCleared;
    private int mCounter = 100;
    private int mPreTimerCounter = 3;
    private int mCounterStartValue = 100;
    private boolean mIsFirstStart = true;
    private ChainedCountDownTimer mTimer1, mTimer2;
    private Context mContext;
    private MainActivity mMainActivity;

    public TimerUtilities(
            Context aContext,
            MainActivity aMainActivity
    ) {
        this.mContext = aContext;
        this.mMainActivity = aMainActivity;
    }

    private void protectValue(EditText aEditText, int aValueOfTiemr) {
        String contentofEditTextSec = String.valueOf(aEditText.getText());
        if (contentofEditTextSec.length() > 2
                || Integer.parseInt(contentofEditTextSec) > aValueOfTiemr) {
            aEditText.setText(String.valueOf(aValueOfTiemr));
        }
    }

    private int getTimer(EditText aEditTextMin, EditText aEditTextSec, TextView aTextView) {
        int editTextMin = Integer.parseInt(String.valueOf(aEditTextMin.getText()));
        int editTextSec = Integer.parseInt(String.valueOf(aEditTextSec.getText()));
        int fullTimer = (editTextMin * 60) + editTextSec;

        mCounter = fullTimer;
        mCounterStartValue = fullTimer;

        if (mIsFirstStart) {
            aTextView.setText(String.valueOf(fullTimer));
            mIsFirstStart = false;
        }
        return fullTimer;
    }

    private Boolean startPreTimer(TextView aTextView) {
        mTimer1 = new ChainedCountDownTimer((4000), 1000) {

            public void onTick(long millisUntilFinished) {
                try {
                    if (mMainActivity.mMp.isPlaying()) {
                        mMainActivity.mMp.stop();
                        mMainActivity.mMp.release();
                        mMainActivity.mMp = MediaPlayer.create(mContext, R.raw.alarm_clock);
                    }
                    mMainActivity.mMp.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                aTextView.setText(String.valueOf(mPreTimerCounter));
                mPreTimerCounter--;
            }

            public void onFinish() {

                try {
                    if (mMainActivity.mMp_finish.isPlaying()) {
                        mMainActivity.mMp_finish.stop();
                        mMainActivity.mMp_finish.release();
                        mMainActivity.mMp_finish = MediaPlayer.create(mContext, R.raw.alarm_clock_finish);
                    }
                    mMainActivity.mMp_finish.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mMainActivity.mConstraintLayout.setBackgroundColor(Color.rgb(76, 175, 80));
                aTextView.setText(String.valueOf(0));
                mPreTimerCounter = 3;
            }
        };
        return true;
    }

    private void manageTimer(TextView aTextView) {
        mIsStopped = false;
        mIsCleared = false;

        String currentTimer = String.valueOf(mMainActivity.mTextView.getText());
        mCounter = Integer.parseInt(currentTimer);

        mTimer2 = new ChainedCountDownTimer((mCounter * 100), 100) {
            public void onTick(long millisUntilFinished) {
                if (!mIsStopped) {
                    mCounter--;
                    aTextView.setText(String.valueOf(mCounter));
                } else {
                    this.cancel();
                    if (!mIsCleared) {
                        mCounter = Integer.parseInt(String.valueOf(aTextView.getText()));
                    } else {
                        mCounter = Integer.parseInt(String.valueOf(0));
                    }
                }
            }

            public void onFinish() {
                if (!mIsStopped) {
                    aTextView.setText(String.valueOf(mCounterStartValue));
                    mMainActivity.mConstraintLayout.setBackgroundColor(Color.rgb(
                            255,
                            255,
                            255));
                } else {
                    mMainActivity.mConstraintLayout.setBackgroundColor(Color.rgb(
                            255,
                            255,
                            255));
                }
            }
        };
    }

    public void handelButtonStart() {
        startPreTimer(mMainActivity.mTextView);
        protectValue(mMainActivity.mEditTextSec, 60);
        protectValue(mMainActivity.mEditTextMin, 99);
        getTimer(mMainActivity.mEditTextMin, mMainActivity.mEditTextSec, mMainActivity.mTextView);
        manageTimer(mMainActivity.mTextView);
        mMainActivity.mConstraintLayout.setBackgroundColor(Color.rgb(202, 32, 32));
        mTimer1.setNext(mTimer2).start();
    }

    public void handelButtonClear() {
        mMainActivity.mConstraintLayout.setBackgroundColor(Color.rgb(255, 255, 255));
        mIsFirstStart = true;
        protectValue(mMainActivity.mEditTextSec, 60);
        protectValue(mMainActivity.mEditTextMin, 99);
        getTimer(mMainActivity.mEditTextMin, mMainActivity.mEditTextSec, mMainActivity.mTextView);
        mIsStopped = true;
        mIsCleared = true;
    }
}