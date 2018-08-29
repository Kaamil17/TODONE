package com.example.asusrg.todone;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    String messageText;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);

        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), EditMessage.class);
                intent.putExtra(Intent_Constants.INTENT_MESSAGE_DATA, arrayList.get(position));
                intent.putExtra(Intent_Constants.INTENT_ITEM_POSITION, position);
                startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE_TWO);
            }
        });

        try {
            Scanner scanner = new Scanner(openFileInput("Todo.txt"));
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                arrayAdapter.add(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        try {
            PrintWriter printWriter = new PrintWriter(openFileOutput("Todo.txt", Context.MODE_PRIVATE));
            for (String data : arrayList) {
                printWriter.println(data);
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finish();
    }

    public void floatingButtonClickListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, EditFieldClass.class);
        startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Intent_Constants.INTENT_REQUEST_CODE) {
            messageText = data.getStringExtra(Intent_Constants.INTENT_MESSAGE_FIELD);
            arrayList.add(messageText);
            arrayAdapter.notifyDataSetChanged();
        } else if (resultCode == Intent_Constants.INTENT_REQUEST_CODE_TWO) {
            messageText = data.getStringExtra(Intent_Constants.INTENT_CHANGED_MESSAGE);
            position = data.getIntExtra(Intent_Constants.INTENT_ITEM_POSITION, -1);
            arrayList.remove(position);
            arrayList.add(position, messageText);
            arrayAdapter.notifyDataSetChanged();


        }
    }
}
