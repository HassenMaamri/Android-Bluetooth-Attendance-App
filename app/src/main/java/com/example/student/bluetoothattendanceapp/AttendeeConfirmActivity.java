package com.example.student.bluetoothattendanceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AttendeeConfirmActivity extends AppCompatActivity {

    Button buttonConfirm;
    Button buttonReturn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee_confirm);
        Intent previousIntent = getIntent();
        final Intent newIntent = new Intent(AttendeeConfirmActivity.this, AttendeeSearchActivity.class);

        buttonConfirm = (Button) findViewById(R.id.confirmButton);
        buttonReturn = (Button) findViewById(R.id.returnButton);

        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AttendeeConfirmActivity.this, "Return to List", Toast.LENGTH_SHORT).show();
                AttendeeConfirmActivity.this.startActivity(newIntent);
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AttendeeConfirmActivity.this, "Event Confirmed", Toast.LENGTH_SHORT).show();
                AttendeeConfirmActivity.this.startActivity(newIntent);
            }
        });
    }
}
