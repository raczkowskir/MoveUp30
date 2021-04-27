package com.yahoo.raczkowskir.moveup30;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;

public class TimerUtilities {

    int counter = 100;
    int preTimerCounter = 3;
    int counterStartValue = 100;
    boolean isFirstStart = true;
    Boolean isStopped, isCleared;
    ChainedCountDownTimer timer1;
    ChainedCountDownTimer timer2;
    Context context;
//    MediaPlayer mp = MediaPlayer.create(context, R.raw.alarm_clock);
//    MediaPlayer mp_finish = MediaPlayer.create(context, R.raw.alarm_clock_finish);
    MainActivity mainActivity;

    ConstraintLayout constraintLayout;

    public TimerUtilities(ConstraintLayout aConstraintLayout, Context aContext, MainActivity aMainActivity) {
        this.context = aContext;
        this.mainActivity = aMainActivity;
        this.constraintLayout = aConstraintLayout;
    }

    public TimerUtilities(ConstraintLayout aConstraintLayout) {
        this.constraintLayout = aConstraintLayout;
    }

    public void simpleStart() {
        Log.d("Raki_1", String.valueOf("Starting: start"));
        constraintLayout.setBackgroundColor(Color.rgb(202, 32, 32));
        Log.d("Raki_1", String.valueOf("Done: start"));
    }



    public void protectValue(EditText aEditText, int aValueOfTiemr) {
        String contentofEditTextSec = String.valueOf(aEditText.getText());
        if (contentofEditTextSec.length() > 2
                || Integer.parseInt(contentofEditTextSec) > aValueOfTiemr) {
            aEditText.setText(String.valueOf(aValueOfTiemr));
        }
        Log.d("Raki_3", String.valueOf("protectValue"));
    }

    public int getTimer(EditText aEditTextMin, EditText aEditTextSec, TextView aTextView) {
        Log.d("Raki_4", String.valueOf("getTimer"));
        int editTextMin = Integer.parseInt(String.valueOf(aEditTextMin.getText()));
        int editTextSec = Integer.parseInt(String.valueOf(aEditTextSec.getText()));
        int fullTimer = (editTextMin * 60) + editTextSec;
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
        timer1 = new ChainedCountDownTimer((4000), 1000) {

            public void onTick(long millisUntilFinished) {
//                try {
//                    if (mp.isPlaying()) {
//                        mp.stop();
//                        mp.release();
//                        mp = MediaPlayer.create(context, R.raw.alarm_clock);
//                    }
//                    mp.start();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                aTextView.setText(String.valueOf(preTimerCounter));
                preTimerCounter--;
                Log.d("Raki_1", String.valueOf(preTimerCounter));
            }

            public void onFinish() {
                //alarm_clock_finish

//                try {
//                    if (mp_finish.isPlaying()) {
//                        mp_finish.stop();
//                        mp_finish.release();
//                        mp_finish = MediaPlayer.create(context, R.raw.alarm_clock_finish);
//                    }
//                    mp_finish.start();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                mainActivity.constraintLayout.setBackgroundColor(Color.rgb(76, 175, 80));
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

        String currentTimer = String.valueOf(mainActivity.textView.getText());
        counter = Integer.parseInt(currentTimer);

        timer2 = new ChainedCountDownTimer((counter * 100), 100) {
            public void onTick(long millisUntilFinished) {
                Log.d("Raki_7", String.valueOf(counter));
                if (!isStopped) {
                    counter--;
                    aTextView.setText(String.valueOf(counter));
                } else {
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

            public void onFinish() {
                if (!isStopped) {
                    aTextView.setText(String.valueOf(counterStartValue));
                    mainActivity.constraintLayout.setBackgroundColor(Color.rgb(255, 255, 255));
                } else {
                    mainActivity.constraintLayout.setBackgroundColor(Color.rgb(255, 255, 255));
                    Log.d("Raki", "timerCanceled");
                }
            }
        };
    }

    public void handelButtonStart() {
        startPreTimer(mainActivity.textView);
        protectValue(mainActivity.editTextSec, 60);
        protectValue(mainActivity.editTextMin, 99);
        getTimer(mainActivity.editTextMin, mainActivity.editTextSec, mainActivity.textView);
        manageTimer(mainActivity.textView);
        Log.d("Raki_6", String.valueOf("onCreate"));
        mainActivity.constraintLayout.setBackgroundColor(Color.rgb(202, 32, 32));
        timer1.setNext(timer2).start();
    }

    public void handelButtonClear() {
        mainActivity.constraintLayout.setBackgroundColor(Color.rgb(255, 255, 255));
        isFirstStart = true;
        protectValue(mainActivity.editTextSec, 60);
        protectValue(mainActivity.editTextMin, 99);
        getTimer(mainActivity.editTextMin, mainActivity.editTextSec, mainActivity.textView);
        isStopped = true;
        isCleared = true;
    }
//    public void protectValue(EditText aEditText, int aValueOfTiemr) {
//        String contentofEditTextSec = String.valueOf(aEditText.getText());
//        if (contentofEditTextSec.length() > 2
//                || Integer.parseInt(contentofEditTextSec) > aValueOfTiemr) {
//            aEditText.setText(String.valueOf(aValueOfTiemr));
//        }
//        Log.d("Raki_3", String.valueOf("protectValue"));
//    }

//    public int getTimer(EditText aEditTextMin, EditText aEditTextSec, TextView aTextView) {
//        Log.d("Raki_4", String.valueOf("getTimer"));
//        int editTextMin = Integer.parseInt(String.valueOf(aEditTextMin.getText()));
//        int editTextSec = Integer.parseInt(String.valueOf(aEditTextSec.getText()));
//        int fullTimer = (editTextMin * 60) + editTextSec;
//        Log.d("Raki", "editTextMin " + editTextMin);
//        Log.d("Raki", "editTextSec " + editTextSec);
//        Log.d("Raki", "Full timer " + fullTimer);
//
//
//        counter = fullTimer;
//        counterStartValue = fullTimer;
//
//        if (isFirstStart) {
//            aTextView.setText(String.valueOf(fullTimer));
//            isFirstStart = false;
//        }
//
//        return fullTimer;
//    }
//
//    public Boolean startPreTimer(TextView aTextView) {
//        timer1 = new ChainedCountDownTimer((4000), 1000) {
//
//            public void onTick(long millisUntilFinished) {
////                try {
////                    if (mp.isPlaying()) {
////                        mp.stop();
////                        mp.release();
////                        mp = MediaPlayer.create(context, R.raw.alarm_clock);
////                    }
////                    mp.start();
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//
//                aTextView.setText(String.valueOf(preTimerCounter));
//                preTimerCounter--;
//                Log.d("Raki_1", String.valueOf(preTimerCounter));
//            }
//
//            public void onFinish() {
//                //alarm_clock_finish
//
////                try {
////                    if (mp_finish.isPlaying()) {
////                        mp_finish.stop();
////                        mp_finish.release();
////                        mp_finish = MediaPlayer.create(context, R.raw.alarm_clock_finish);
////                    }
////                    mp_finish.start();
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//
//                mainActivity.constraintLayout.setBackgroundColor(Color.rgb(76, 175, 80));
//                Log.d("Raki_2", String.valueOf(preTimerCounter));
//                aTextView.setText(String.valueOf(0));
//                preTimerCounter = 3;
//            }
//        };
//        return true;
//    }
//
//    public void manageTimer(TextView aTextView) {
//        Log.d("Raki_5", String.valueOf("manageTimer"));
//        isStopped = false;
//        isCleared = false;
//
//        String currentTimer = String.valueOf(mainActivity.textView.getText());
//        counter = Integer.parseInt(currentTimer);
//
//        timer2 = new ChainedCountDownTimer((counter * 100), 100) {
//            public void onTick(long millisUntilFinished) {
//                Log.d("Raki_7", String.valueOf(counter));
//                if (!isStopped) {
//                    counter--;
//                    aTextView.setText(String.valueOf(counter));
//                } else {
//                    this.cancel();
//                    if (!isCleared) {
//                        counter = Integer.parseInt(String.valueOf(aTextView.getText()));
//                        Log.d("counter", String.valueOf(counter));
//                    } else {
//                        counter = Integer.parseInt(String.valueOf(0));
//                        Log.d("Raki", "Cleared");
//                    }
//                }
//            }
//
//            public void onFinish() {
//                if (!isStopped) {
//                    aTextView.setText(String.valueOf(counterStartValue));
//                    mainActivity.constraintLayout.setBackgroundColor(Color.rgb(255, 255, 255));
//                } else {
//                    mainActivity.constraintLayout.setBackgroundColor(Color.rgb(255, 255, 255));
//                    Log.d("Raki", "timerCanceled");
//                }
//            }
//        };
//    }
//
//    public void handelButtonStart() {
//        startPreTimer(mainActivity.textView);
//        protectValue(mainActivity.editTextSec, 60);
//        protectValue(mainActivity.editTextMin, 99);
//        getTimer(mainActivity.editTextMin, mainActivity.editTextSec, mainActivity.textView);
//        manageTimer(mainActivity.textView);
//        Log.d("Raki_6", String.valueOf("onCreate"));
//        mainActivity.constraintLayout.setBackgroundColor(Color.rgb(202, 32, 32));
//        timer1.setNext(timer2).start();
//    }
//
//    public void handelButtonClear() {
//        mainActivity.constraintLayout.setBackgroundColor(Color.rgb(255, 255, 255));
//        isFirstStart = true;
//        protectValue(mainActivity.editTextSec, 60);
//        protectValue(mainActivity.editTextMin, 99);
//        getTimer(mainActivity.editTextMin, mainActivity.editTextSec, mainActivity.textView);
//        isStopped = true;
//        isCleared = true;
//    }



}
