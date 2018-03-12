package com.example.student.bluetoothattendanceapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.student.bluetoothattendanceapp.db.SQLiteDB;
import com.example.student.bluetoothattendanceapp.model.Meeting;


public class ActActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText personName;
    private EditText phone;

    private Button btnAdd, btnEdit, btnDelete, btnStart;

    private SQLiteDB sqLiteDB;
    private Meeting meeting;

    public static void start(Context context){
        Intent intent = new Intent(context, ActActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, Meeting meeting){
        Intent intent = new Intent(context, ActActivity.class);
        intent.putExtra(ActActivity.class.getSimpleName(), meeting);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act);

        personName = (EditText) findViewById(R.id.personText);
        phone = (EditText) findViewById(R.id.phoneText);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnAdd.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnStart.setOnClickListener(this);

        meeting = getIntent().getParcelableExtra(ActActivity.class.getSimpleName());
        if(meeting != null){
            btnAdd.setVisibility(View.GONE);

            personName.setText(meeting.getName());
            phone.setText(meeting.getPhone());
        }else{
            btnEdit.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
            btnStart.setVisibility(View.GONE);

        }

        sqLiteDB = new SQLiteDB(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnAdd){
            meeting = new Meeting();
            meeting.setName(personName.getText().toString());
            meeting.setPhone(phone.getText().toString());
            sqLiteDB.create(meeting);

            Toast.makeText(this, "Inserted!", Toast.LENGTH_SHORT).show();
            finish();
        }else if(v == btnEdit){
            meeting.setName(personName.getText().toString());
            meeting.setPhone(phone.getText().toString());
            sqLiteDB.update(meeting);

            Toast.makeText(this, "Edited!", Toast.LENGTH_SHORT).show();
            finish();
        }else if(v == btnDelete){
            sqLiteDB.delete(meeting.getId());

            Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show();
            finish();
        }else if (v == btnStart){
            Intent intent = new Intent(ActActivity.this, Menu.class);
            startActivity(intent);
        }
    }
}
