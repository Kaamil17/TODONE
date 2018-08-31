package com.example.asusrg.todone;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;


import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    SwipeMenuListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    String messageText;
    int position;
    EditText taskMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        taskMessage = findViewById(R.id.taskMessage);



        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        // For swiping ListView.
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(170);
                // set item title
                openItem.setTitle("Edit");
                // set item title font size
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete_black_24dp);
                // add to menu
                menu.addMenuItem(deleteItem);

                // swipe start
                SwipeMenuItem doneItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item Background
                doneItem.setBackground(new ColorDrawable(Color.rgb(0x25,
                        0x3F, 0x25)));
                //
                doneItem.setWidth(150);
                // set an icon
                doneItem.setIcon(R.drawable.ic_done_black_24dp);
                // add to menu
                menu.addMenuItem(doneItem);

            }
        };

        // set creator
        listView.setMenuCreator(creator);


        // specifying what left and right swipe should do.
        listView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:

                        Toast.makeText(MainActivity.this, "Editing Task", Toast.LENGTH_SHORT).show();
                        //   ItemClickListening Method
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), EditMessage.class);
                        intent.putExtra(Intent_Constants.INTENT_MESSAGE_DATA, arrayList.get(position));
                        intent.putExtra(Intent_Constants.INTENT_ITEM_POSITION, position);
                        startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE_TWO);

                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "Deleting Task", Toast.LENGTH_SHORT).show();

                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();

                }
                return true;
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
