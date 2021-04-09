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

    Button buttonStart, buttonStop;
    TextView textView;
    EditText editTextMin, editTextSec;
    Boolean isStopped;


    public void protectValue(EditText editText, int valueOfTiemr) {
        String contentofEditTextSec = String.valueOf(editText.getText());
        if (Integer.valueOf(contentofEditTextSec) > valueOfTiemr ) {
            editText.setText(String.valueOf(valueOfTiemr));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStop = (Button) findViewById(R.id.buttonStop);

        textView = (TextView) findViewById(R.id.textViewTimer);
        editTextSec = (EditText) findViewById(R.id.editTextSec);
        editTextMin = (EditText) findViewById(R.id.editTextMin);

        buttonStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                protectValue(editTextSec, 60);
                protectValue(editTextMin, 99);

                isStopped = false;
                String currentTimer = String.valueOf(textView.getText());
                counter = Integer.valueOf(currentTimer);

                new CountDownTimer((counter*100), 100){
                    public void onTick(long millisUntilFinished){
                        if (isStopped == false) {
                            counter--;
                            textView.setText(String.valueOf(counter));
                        }
                        else {
                            this.cancel();
                            counter = Integer.valueOf(String.valueOf(textView.getText()));
                            Log.d("counter", String.valueOf(counter));
                        }
                    }
                    public  void onFinish(){
                        if (isStopped == false) {
                            textView.setText(String.valueOf(counterStartValue));
                        }
                        else {
                            Log.d("Raki", "timerCanceled");
                        }
                    }
                }.start();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                isStopped = true;
            }
        });
    }
}