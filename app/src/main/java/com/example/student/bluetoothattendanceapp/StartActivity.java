package com.example.student.bluetoothattendanceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    Button host;
    Button attend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        final Intent hostIntent = new Intent(StartActivity.this, MainActivity.class);
        final Intent attendIntent = new Intent(StartActivity.this, AttendeeSearchActivity.class);

        host = (Button) findViewById(R.id.hostButton);
        attend = (Button) findViewById(R.id.attendeeButton);

        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity.this.startActivity(hostIntent);
            }
        });

        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity.this.startActivity(attendIntent);
            }
        });
    }
}
