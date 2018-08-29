package com.example.asusrg.todone;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class EditFieldClass extends AppCompatActivity {
    EditText editText;
    Button buttonImage;
    TextView calenderID;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_layout);

        calenderID = findViewById(R.id.calenderID);
        buttonImage = findViewById(R.id.ButtonImage);

        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calender = Calendar.getInstance();
                int year = calender.get(Calendar.YEAR);
                int month = calender.get(Calendar.MONTH);
                final int day = calender.get(Calendar.DAY_OF_MONTH);

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

}