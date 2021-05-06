package com.yahoo.raczkowskir.moveup30;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class TimerUtilitiesTest {

    @Test
    void getIsStopped() {
        MainActivity mainActivity = new MainActivity();
        TimerUtilities timerUtilities= new TimerUtilities(mainActivity.mContext, mainActivity.mMainActivity);
        timerUtilities.setMIsStopped(true);
        assertEquals(true, timerUtilities.getIsStopped());
    }

    @Test
    void checkGetTimer() {
        MainActivity mainActivity = new MainActivity();
        TimerUtilities timerUtilities= new TimerUtilities(mainActivity.mContext, mainActivity.mMainActivity);
//        timerUtilities.getmMainActivity().mEditTextMin.setText("dupa");

//        String result1;
        assertThrows(NullPointerException.class,() -> timerUtilities.getmMainActivity().mEditTextMin.setText("dupa"));
//        assertNotNull(timerUtilities.getTimer());

    }

    @Test
    void handelButtonClear() {
    }
}