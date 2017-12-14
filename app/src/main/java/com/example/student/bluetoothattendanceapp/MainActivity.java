package com.example.student.bluetoothattendanceapp;

import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.student.bluetoothattendanceapp.adapter.MeetingListAdapter;
import com.example.student.bluetoothattendanceapp.db.SQLiteDB;
import com.example.student.bluetoothattendanceapp.listener.RecyclerItemClickListener;
import com.example.student.bluetoothattendanceapp.model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerItemClickListener {

    private RecyclerView lvContact;
    private FloatingActionButton btnAdd;

    private MeetingListAdapter meetingListAdapter;
    private LinearLayoutManager linearLayoutManager;

    private SQLiteDB sqLiteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvContact = (RecyclerView) findViewById(R.id.lvContact);
        btnAdd = (FloatingActionButton) findViewById(R.id.add);

        linearLayoutManager = new LinearLayoutManager(this);
        meetingListAdapter = new MeetingListAdapter(this);
        meetingListAdapter.setOnItemClickListener(this);

        lvContact.setLayoutManager(linearLayoutManager);
        lvContact.setAdapter(meetingListAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActActivity.start(MainActivity.this);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    void loadData(){
        sqLiteDB = new SQLiteDB(this);

        List<Meeting> meetingList = new ArrayList<>();

        Cursor cursor = sqLiteDB.retrieve();
        Meeting meeting;

        if (cursor.moveToFirst()) {
            do {

                meeting = new Meeting();

                meeting.setId(cursor.getInt(0));
                meeting.setName(cursor.getString(1));
                meeting.setPhone(cursor.getString(2));

                meetingList.add(meeting);
            }while (cursor.moveToNext());
        }

        meetingListAdapter.clear();
        meetingListAdapter.addAll(meetingList);
    }

    @Override
    public void onItemClick(int position, View view) {
        ActActivity.start(this, meetingListAdapter.getItem(position));
    }
}