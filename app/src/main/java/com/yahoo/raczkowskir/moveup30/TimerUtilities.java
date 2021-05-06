package com.yahoo.raczkowskir.moveup30;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;

public class TimerUtilities {
    private Boolean mIsCleared, mIsStopped;
    private int mCounter = 100;
    private int mPreTimerCounter = 3;
    private int mCounterStartValue = 100;
    private boolean mIsFirstStart = true;
    private ChainedCountDownTimer mTimer1, mTimer2;
    private final Context mContext;

    public MainActivity getmMainActivity() {
        return mMainActivity;
    }

    private final MainActivity mMainActivity;

    void setMIsStopped(Boolean mIsStopped) {
        this.mIsStopped = mIsStopped;
    }

    Boolean getIsStopped() { return mIsStopped; }

    TimerUtilities(
        Context aContext,
        MainActivity aMainActivity
    ) {
        this.mContext = aContext;
        this.mMainActivity = aMainActivity;
    }

    private void keepTimerValueInTheProperRange(int aValueOfTimer) {
        String contentOfEditTextSec = String.valueOf(mMainActivity.mEditTextSec.getText());
        if (contentOfEditTextSec.length() > 2
                || Integer.parseInt(contentOfEditTextSec) > aValueOfTimer) {
            mMainActivity.mEditTextSec.setText(String.valueOf(aValueOfTimer));
        }
    }

    int getTimer() {
        int editTextMin = Integer.parseInt(String.valueOf(mMainActivity.mEditTextMin.getText()));
        int editTextSec = Integer.parseInt(String.valueOf(mMainActivity.mEditTextSec.getText()));
        int fullTimer = (editTextMin * 60) + editTextSec;

        mCounter = fullTimer;
        mCounterStartValue = fullTimer;

        if (mIsFirstStart) {
            mMainActivity.mTextView.setText(String.valueOf(fullTimer));
            mIsFirstStart = false;
        }
        return fullTimer;
    }

    private void addSound(int aResId) {
        try {
            if (mMainActivity.mMp.isPlaying()) {
                mMainActivity.mMp.stop();
                mMainActivity.mMp.release();
            }
            mMainActivity.mMp = MediaPlayer.create(mContext, aResId);
            mMainActivity.mMp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startPreTimer() {
        mTimer1 = new ChainedCountDownTimer((4000), 1000) {

            public void onTick(long millisUntilFinished) {
                addSound(R.raw.alarm_clock);
                mMainActivity.mTextView.setText(String.valueOf(mPreTimerCounter));
                mPreTimerCounter--;
            }

            public void onFinish() {
                addSound(R.raw.alarm_clock_finish);
                mMainActivity.mConstraintLayout.setBackgroundColor(Color.rgb(76, 175, 80));
                mMainActivity.mTextView.setText(String.valueOf(0));
                mPreTimerCounter = 3;
            }
        };
    }
    private void manageTimer() {
        mIsStopped = false;
        mIsCleared = false;

        String currentTimer = String.valueOf(mMainActivity.mTextView.getText());
        mCounter = Integer.parseInt(currentTimer);

        mTimer2 = new ChainedCountDownTimer((mCounter * 100), 100) {
            public void onTick(long millisUntilFinished) {
                if (!mIsStopped) {
                    mCounter--;
                    mMainActivity.mTextView.setText(String.valueOf(mCounter));
                } else {
                    this.cancel();
                    if (!mIsCleared) {
                        mCounter = Integer.parseInt(String.valueOf(mMainActivity.mTextView.getText()));
                    } else {
                        mCounter = Integer.parseInt(String.valueOf(0));
                    }
                }
            }

            public void onFinish() {
                if (!mIsStopped) mMainActivity.mTextView.setText(String.valueOf(mCounterStartValue));
                mMainActivity.mConstraintLayout.setBackgroundColor(Color.rgb(
                        255,
                        255,
                        255));
            }
        };
    }

    void handelButtonStart() {
        startPreTimer();
        keepTimerValueInTheProperRange(60);
        keepTimerValueInTheProperRange(99);
        getTimer();
        manageTimer();
        mMainActivity.mConstraintLayout.setBackgroundColor(Color.rgb(202, 32, 32));
        mTimer1.setNext(mTimer2).start();
    }

    void handelButtonClear() {
        mMainActivity.mConstraintLayout.setBackgroundColor(Color.rgb(255, 255, 255));
        mIsFirstStart = true;
        keepTimerValueInTheProperRange(60);
        keepTimerValueInTheProperRange(99);
        getTimer();
        mIsStopped = true;
        mIsCleared = true;
    }
}
