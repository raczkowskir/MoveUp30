package com.yahoo.raczkowskir.moveup30;

// MOV-7 epic - starting work !

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public int counter = 100;
    public int counterStartValue = 100;

    Button buttonStart, buttonStop, buttonClear;
    TextView textView;
    EditText editTextMin, editTextSec;
    Boolean isStopped, isCleared;
    boolean isFirstStart = true;

    public void protectValue(EditText editText, int valueOfTiemr) {
        String contentofEditTextSec = String.valueOf(editText.getText());
        if ( contentofEditTextSec.length() > 2
                || Integer.parseInt(contentofEditTextSec) > valueOfTiemr ) {
            editText.setText(String.valueOf(valueOfTiemr));
        }
    }

    public int getTimer(EditText aEditTextMin, EditText aEditTextSec, TextView aTextView) {
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

    public void manageTimer(TextView aTextView) {
        isStopped = false;
        isCleared = false;

        String currentTimer = String.valueOf(textView.getText());
        counter = Integer.parseInt(currentTimer);

        new CountDownTimer((counter*100), 100){
            public void onTick(long millisUntilFinished){
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
                }
                else {
                    Log.d("Raki", "timerCanceled");
                }
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);
        buttonClear = findViewById(R.id.buttonClear);

        textView = findViewById(R.id.textViewTimer);
        editTextSec = findViewById(R.id.editTextSec);
        editTextMin = findViewById(R.id.editTextMin);

        buttonStart.setOnClickListener(v -> {

            protectValue(editTextSec, 60);
            protectValue(editTextMin, 99);
            getTimer(editTextMin, editTextSec, textView);
            manageTimer(textView);
        });

        buttonStop.setOnClickListener(v -> isStopped = true);

        buttonClear.setOnClickListener(v -> {
            isFirstStart = true;
            protectValue(editTextSec, 60);
            protectValue(editTextMin, 99);
            getTimer(editTextMin, editTextSec, textView);
            isStopped = true;
            isCleared = true;
        });
    }
}