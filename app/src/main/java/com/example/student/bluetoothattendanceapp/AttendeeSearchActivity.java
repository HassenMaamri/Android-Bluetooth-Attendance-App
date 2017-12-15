package com.example.student.bluetoothattendanceapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AttendeeSearchActivity extends AppCompatActivity {

    final int REQUEST_ENABLE_BT = 1;

    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

    Button buttonRefresh;
    Button buttonReturn;

    ListView eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee_search);

        final Intent returnIntent = new Intent(AttendeeSearchActivity.this, StartActivity.class);
        final Intent confirmIntent = new Intent(AttendeeSearchActivity.this, AttendeeConfirmActivity.class);

        enableBluetooth();
        findEvents();

        buttonRefresh = (Button) findViewById(R.id.refreshButton);
        buttonReturn = (Button) findViewById(R.id.returnButton);

        eventList = (ListView) findViewById(R.id.eventList);

        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AttendeeSearchActivity.this, "Return to Main", Toast.LENGTH_SHORT).show();
                AttendeeSearchActivity.this.startActivity(returnIntent);
            }
        });

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findEvents();
                //AttendeeSearchActivity.this.startActivity(confirmIntent);
            }
        });
    }

    private void enableBluetooth() {
        if (adapter == null) {
        }
        else if (adapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            adapter.startDiscovery();
        }
    }

    private void findEvents() {
        if (adapter == null) {
        }
        else if (adapter.isEnabled()) {
            Toast.makeText(this, "Searching for events...", Toast.LENGTH_SHORT).show();
        }
    }
}
