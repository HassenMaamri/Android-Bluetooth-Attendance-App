package com.example.student.bluetoothattendanceapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class AttendeeSearchActivity extends AppCompatActivity{

    final int REQUEST_BLUETOOTH = 1;

    ConnectFromAttend info = new ConnectFromAttend();

    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

    Button buttonRefresh;
    Button buttonReturn;

    EditText nameInput;

    ListView eventList;

    ArrayList<BluetoothDevice> bluetoothDeviceList = new ArrayList<>();
    ArrayList<DeviceItem> deviceItemList = new ArrayList<>();
    ArrayList<String> deviceList = new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee_search);

        final Intent returnIntent = new Intent(AttendeeSearchActivity.this, StartActivity.class);
        final Intent confirmIntent = new Intent(AttendeeSearchActivity.this, AttendeeConfirmActivity.class);

        eventList = (ListView) findViewById(R.id.eventList);
        arrayAdapter = new ArrayAdapter<>(this, R.layout.activity_event_list, R.id.eventText, deviceList);

        buttonRefresh = (Button) findViewById(R.id.refreshButton);
        buttonReturn = (Button) findViewById(R.id.returnButton_Search);

        eventList = (ListView) findViewById(R.id.eventList);

        nameInput = (EditText) findViewById(R.id.nameText);

        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AttendeeSearchActivity.this, "Return to Main", Toast.LENGTH_SHORT).show();
                startActivity(returnIntent);
            }
        });

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableBluetooth();
            }
        });

        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                Log.d("DeviceConnect",bluetoothDeviceList.get(i).getName());

                if (info == null)
                {
                    Log.d("DeviceConnect","!!NULL!!");
                }

                boolean test;
                test = info.connect(bluetoothDeviceList.get(i));
                Log.d("DeviceConnect",Boolean.toString(test));

                try {
                    info.sendData(nameInput.getText().toString());
                } catch (IOException e) {
                    Log.d("DeviceConnect","Could not send string");
                }

                confirmIntent.putExtra("title", "Event Name");
                confirmIntent.putExtra("host", "Event Coordinator");
                confirmIntent.putExtra("date", "Event Date");
                confirmIntent.putExtra("start", "Start Time: Time");
                confirmIntent.putExtra("end", "End Time: Time");
                confirmIntent.putExtra("desc", "Event Description");
                startActivity(confirmIntent);

            }
        });

        enableBluetooth();
    }

    private void enableBluetooth() {
        Log.d("DEVICELIST", "Super called for DeviceListFragment onCreate\n");
        deviceItemList = new ArrayList<DeviceItem>();

        // Phone does not support Bluetooth so let the user know and exit.
        if (adapter == null) {
            new AlertDialog.Builder(this)

                    .setTitle("Not compatible")
                    .setMessage("Your phone does not support Bluetooth")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else {
            if (adapter.isEnabled() == false) {
                Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBT, REQUEST_BLUETOOTH);
            }

            Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();

            //Creates list of paired devices
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    bluetoothDeviceList.add(device);
                    DeviceItem newDevice = new DeviceItem(device.getName(), device.getAddress(), "false");
                    deviceItemList.add(newDevice);
                }
            }
        }
        findEvents();
    }

    private void findEvents() {
        String defaultText = "No Events Found";
        deviceList.clear();

        if ((adapter != null) && (adapter.isEnabled())) {
            Toast.makeText(this, "Searching for events...", Toast.LENGTH_SHORT).show();

            if (deviceItemList.isEmpty())
            {
                deviceList.add(defaultText);
                Log.d("DeviceName", defaultText);
            }
            else {
                for (DeviceItem device : deviceItemList) {
                    deviceList.add(device.getDeviceName());
                    Log.d("DeviceName", device.getDeviceName());
                }
            }

            eventList.setAdapter(arrayAdapter);
        } else {
            Toast.makeText(this, defaultText, Toast.LENGTH_SHORT).show();
            deviceList.add("1");
            deviceList.add("2");
            deviceList.add("3");
            Log.d("DeviceName", defaultText);
            eventList.setAdapter(arrayAdapter);
        }
    }
}


