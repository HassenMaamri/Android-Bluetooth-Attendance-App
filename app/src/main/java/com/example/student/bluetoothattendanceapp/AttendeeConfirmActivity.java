package com.example.student.bluetoothattendanceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class AttendeeConfirmActivity extends AppCompatActivity {

    ConnectFromAttend info = new ConnectFromAttend();

    Button buttonConfirm;
    Button buttonReturn;

    TextView eventTitle;
    TextView eventHost;
    TextView eventDate;
    TextView eventStart;
    TextView eventEnd;
    TextView eventDesc;

    String title;
    String host;
    String date;
    String start;
    String end;
    String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee_confirm);
        final Intent newIntent = new Intent(AttendeeConfirmActivity.this, AttendeeSearchActivity.class);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("title");
            host = extras.getString("host");
            date = extras.getString("date");
            start = extras.getString("start");
            end = extras.getString("end");
            desc = extras.getString("desc");
        }

        buttonConfirm = (Button) findViewById(R.id.confirmButton);

        eventTitle = (TextView) findViewById(R.id.eventTitle);
        eventHost = (TextView) findViewById(R.id.eventAuthor);
        eventDate = (TextView) findViewById(R.id.eventDate);
        eventStart = (TextView) findViewById(R.id.eventStart);
        eventEnd = (TextView) findViewById(R.id.eventEnd);
        eventDesc = (TextView) findViewById(R.id.eventDesc);

        eventTitle.setText(title);
        eventHost.setText(host);
        eventDate.setText(date);
        eventStart.setText(start);
        eventEnd.setText(end);
        eventDesc.setText(desc);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AttendeeConfirmActivity.this, "Event Confirmed", Toast.LENGTH_SHORT).show();
                startActivity(newIntent);
            }
        });
    }
}
