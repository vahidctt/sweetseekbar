package org.dakik.sweetseekbarcontrolexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.dakik.sweetseekbar.HorizontalSweetSeekbarView;
import org.dakik.sweetseekbar.SweetSeekbarView;
import org.dakik.sweetseekbar.interfaces.SweetSeekbarListener;

public class MainActivity extends AppCompatActivity {


    TextView tvMove,tvStop;
    TextView tvMoveH,tvStopH;
    EditText etMaxValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvMove = findViewById(R.id.tvMove);
        tvStop = findViewById(R.id.tvStop);
        tvMoveH = findViewById(R.id.tvMoveH);
        tvStopH = findViewById(R.id.tvStopH);
        etMaxValue = findViewById(R.id.etMaxValue);

        final SweetSeekbarView ss= findViewById(R.id.ss);
        ss.setListener(new SweetSeekbarListener() {
            @Override
            public void onStart(int value) {
                ss.setMaxValue(Integer.parseInt(etMaxValue.getText().toString()));
            }

            @Override
            public void onMove(int value) {
                tvMove.setText("value: "+value);
            }

            @Override
            public void onEnd(int value) {
                tvStop.setText("stop value: "+value);
            }
        });

        final HorizontalSweetSeekbarView ssh= findViewById(R.id.ssH);
        ssh.setListener(new SweetSeekbarListener() {
            @Override
            public void onStart(int value) {
                ssh.setMaxValue(Integer.parseInt(etMaxValue.getText().toString()));
            }

            @Override
            public void onMove(int value) {
                tvMoveH.setText("value: "+value);
            }

            @Override
            public void onEnd(int value) {
                tvStopH.setText("stop value: "+value);
            }
        });


       //ss.setEnabled(false);
        ss.setValue(20);
        //ss.setRadius(30f,30f,30f,15f);
    }
}
