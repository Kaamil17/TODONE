package com.example.asusrg.todone;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class EditFieldClass extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    EditText editText;
    TextView calenderID;
    TextView timeText;

    DatePickerDialog datePickerDialog;
    Button buttonImage;
    Button clockButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_layout);

        calenderID = findViewById(R.id.calenderID);
        buttonImage = findViewById(R.id.ButtonImage);
        clockButton = findViewById(R.id.clockButton);

        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calender = Calendar.getInstance();
                int year = calender.get(Calendar.YEAR);
                int month = calender.get(Calendar.MONTH);
                int day = calender.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(EditFieldClass.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String currentDateString = DateFormat.getDateInstance().format(calendar.getTime());
                        calenderID.setText(currentDateString);
                    }
                }, day, month, year);
                datePickerDialog.show();
            }

        });

        clockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditFieldClass.this, "Not Ready Yet!", Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void saveButtonClicked(View view) {
        editText = findViewById(R.id.taskMessage);
        String messageText = editText.getText().toString();
        if (messageText.equals("")) {
            Toast.makeText(this, "Empty Task Cannot Be Saved", Toast.LENGTH_SHORT).show();

        } else {
            Intent intent = new Intent();
            intent.putExtra(Intent_Constants.INTENT_MESSAGE_FIELD, messageText);
            setResult(Intent_Constants.INTENT_RESULT_CODE, intent);
            finish();
        }
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        updateTimeText(calendar);
      // startAlarm(calendar);

    }

    public void updateTimeText(Calendar calendar) {
        timeText = findViewById(R.id.timeText);
        String textForTime = "Alarm set for: ";
        textForTime += DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
        timeText.setText(textForTime);
    }

}