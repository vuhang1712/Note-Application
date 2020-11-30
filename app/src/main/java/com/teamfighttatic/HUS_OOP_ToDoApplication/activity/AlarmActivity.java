package com.teamfighttatic.HUS_OOP_ToDoApplication.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.teamfighttatic.HUS_OOP_ToDoApplication.R;

import java.util.concurrent.TimeUnit;

public class AlarmActivity extends AppCompatActivity {

    CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_alarm);

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.custom_dialog_alarm, null);
        final RelativeLayout layoutAlarm = alertLayout.findViewById(R.id.alarmLayout);
        final RelativeLayout layoutCoundown = alertLayout.findViewById(R.id.coundownLayout);
        final EditText editTextHour = alertLayout.findViewById(R.id.edtHour);
        final EditText editTextMinute = alertLayout.findViewById(R.id.edtMinute);
        final EditText editTextSecond = alertLayout.findViewById(R.id.edtSecond);
        final Button buttonStart = alertLayout.findViewById(R.id.btn_start);
        final Button buttonStop = alertLayout.findViewById(R.id.btn_stop);
        final Button buttonAlarm = alertLayout.findViewById(R.id.alarm);
        final Button buttonCoundown = alertLayout.findViewById(R.id.coundown);
        final Button buttonCancel = alertLayout.findViewById(R.id.cancel);

        final AlertDialog.Builder alert = new AlertDialog.Builder(AlarmActivity.this);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        layoutCoundown.setVisibility(View.GONE);
        buttonCoundown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAlarm.setVisibility(View.GONE);
                layoutCoundown.setVisibility(View.VISIBLE);
                buttonStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String hour = editTextHour.getText().toString();
                        String minute = editTextMinute.getText().toString();
                        String second = editTextSecond.getText().toString();
                        int time = Integer.parseInt(hour) * 60 * 60 * 1000 + Integer.parseInt(minute) * 60 * 1000 + Integer.parseInt(second) * 1000;
                        timer = new CountDownTimer(time, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                editTextHour.setText((TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                                        - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished))) + "");

                                editTextMinute.setText((TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                                        - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))) + "");

                                editTextSecond.setText((TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))) + "");
                            }

                            @Override
                            public void onFinish() {
                                editTextMinute.setText("Time up!");
                                editTextHour.setVisibility(View.INVISIBLE);
                                editTextSecond.setVisibility(View.INVISIBLE);
                            }
                        }.start();
                    }
                });
                buttonStop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timer.cancel();
                    }
                });

            }
        });
        buttonAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutCoundown.setVisibility(View.GONE);
                layoutAlarm.setVisibility(View.VISIBLE);
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}
