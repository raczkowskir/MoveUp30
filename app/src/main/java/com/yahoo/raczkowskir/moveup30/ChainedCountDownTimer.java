package com.yahoo.raczkowskir.moveup30;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

public abstract class ChainedCountDownTimer {
        private final long mMillisInFuture;
        private final long mCountdownInterval;
        private long mStopTimeInFuture;
        private boolean mCancelled = false;
        private ChainedCountDownTimer first;
        private ChainedCountDownTimer next;
        private static final int MSG = 1;

        public abstract void onTick(long millisUntilFinished);
        public abstract void onFinish();


        /**
         * @param millisInFuture The number of millis in the future from the call
         *   to {@link #start()} until the countdown is done and {@link #onFinish()}
         *   is called.
         * @param countDownInterval The interval along the way to receive
         *   {@link #onTick(long)} callbacks.
         */
        public ChainedCountDownTimer(long millisInFuture, long countDownInterval) {
            mMillisInFuture = millisInFuture;
            mCountdownInterval = countDownInterval;
            first = this;
        }

        public void start() {
            Log.d("__DUPA_0", "__DUPA");
            first.startInternal();
        }
        public synchronized final ChainedCountDownTimer startInternal() {
            Log.d("__DUPA_1", "__DUPA");
            mCancelled = false;
            if (mMillisInFuture <= 0) {
                onFinish();

                if (next != null) {
                    next.startInternal();
                }

                return this;
            }
            mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
            mHandler.sendMessage(mHandler.obtainMessage(MSG));
            Log.d("__DUPA_2", "__DUPA");
            return this;
        }

        public synchronized final void cancel() {
            first.mCancelled = true;
            mHandler.removeMessages(MSG);
            Log.d("__DUPA_3", "__DUPA");
        }





        public ChainedCountDownTimer setNext(ChainedCountDownTimer next) {
            this.next = next;
            next.first = this.first;
            Log.d("__DUPA_4", "__DUPA");
            return this.next;
        }

        @SuppressLint("HandlerLeak")
        private Handler mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
//                Log.d("__DUPA_5", "__DUPA");
                synchronized (ChainedCountDownTimer.this) {
//                    Log.d("__DUPA_6", "__DUPA");
                    if (first.mCancelled) {
                        return;
                    }

                    final long millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();

                    if (millisLeft <= 0) {
                        onFinish();
                        Log.d("__DUPA_7", "__DUPA");
                        if (next != null) {
                            Log.d("__DUPA_8", "__DUPA");
                            next.startInternal();
                            Log.d("__DUPA_9", "__DUPA");
                        }
                        Log.d("__DUPA_9", "__DUPA");
                    } else if (millisLeft < mCountdownInterval) {
                        Log.d("__DUPA_10", String.valueOf(millisLeft));
                        // no tick, just delay until done
                        sendMessageDelayed(obtainMessage(MSG), millisLeft);
//                        Log.d("__DUPA_11", String.valueOf(millisLeft));
                    } else {
                        Log.d("__DUPA_12", "__DUPA");
                        long lastTickStart = SystemClock.elapsedRealtime();
                        onTick(millisLeft);
                        Log.d("__DUPA_13", "__DUPA");
                        // take into account user's onTick taking time to execute
                        long delay = lastTickStart + mCountdownInterval - SystemClock.elapsedRealtime();

                        // special case: user's onTick took more than interval to
                        // complete, skip to next interval
                        while (delay < 0) {delay += mCountdownInterval;
                            Log.d("__DUPA_14!!", "__DUPA");
                        }

                        sendMessageDelayed(obtainMessage(MSG), delay);
                    }
                }
            }
        };
    }
