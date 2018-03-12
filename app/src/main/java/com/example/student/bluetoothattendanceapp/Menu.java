package com.example.student.bluetoothattendanceapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Menu extends AppCompatActivity {

    private BluetoothAdapter BTAdapter;
    public static int REQUEST_BLUETOOTH = 1;
    private BluetoothSocket bTSocket;
    private BluetoothServerSocket mmServerSocket;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;

    static ArrayList<String> people = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        //Set socket to initially null
        bTSocket = null;

        //Enable bluetooth
        BTAdapter = BluetoothAdapter.getDefaultAdapter();
        // Phone does not support  Bluetooth so let the user know and exit.
        if (BTAdapter == null) {
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
        }

        //Request enabling
        if (!BTAdapter.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }

         acceptConnect(BTAdapter);
         beginListenForData();

       /* Prospective p = new Prospective(new String[]{"Steve", "James", "Taylor", "Megan", "John",
                "Mark", "Emily", "Jamie", "Joseph" });

        String[] people = p.getPeople(); */

       // ArrayList<String> people = new ArrayList<String>();
/*
        if (people.contains("Steve") == false){
            people.add("Steve");

        }
        if (people.contains("James") == false){
            people.add("James");

        }
        if (people.contains("Taylor") == false){
            people.add("Taylor");

        }
*/

        final ListView mylv = (ListView) findViewById(R.id.mylv);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        people) {

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        View view = super.getView(position, convertView, parent);
                        TextView text = (TextView) view.findViewById(android.R.id.text1);

                        Attendee a1 = new Attendee("Taylor", "Taylor LN");
                        Attendee a2 = new Attendee("Emily", "Emily LN");
                        Attendee a3 = new Attendee("Mark", "Mark LN");
                        Attendee a4 = new Attendee("Steve", "Steve LN");

                       // String[] here = {a1.getfName(), a2.getfName(), a3.getfName(), a4.getfName()};
                        ArrayList<String> here = new ArrayList<String>();
                        here.add(a1.getfName());
                        here.add(a2.getfName());
                        here.add(a3.getfName());
                        here.add(a4.getfName());

                        String person = String.valueOf(mylv.getItemAtPosition(position));


                        if (here.contains(person) ){
                            text.setTextColor(Color.rgb(0,153,76));

                        }else{
                            text.setTextColor(Color.RED);

                        }

                        mylv.setOnItemClickListener(
                                new AdapterView.OnItemClickListener(){
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        //String[] here = {"Steve", "Taylor", "Emily", "Mark"};
                                        ArrayList<String> here = new ArrayList<String>();
                                        here.add("Steve");
                                        here.add("Taylor");
                                        here.add("Emily");
                                        here.add("Mark");

                                        String person = String.valueOf(parent.getItemAtPosition(position));
                                        if (Arrays.asList(here).contains(person)
                                                ){
                                            Toast.makeText(Menu.this, person + " is here", Toast.LENGTH_LONG).show();
                                        }
                                        else
                                        {
                                          //  Toast.makeText(Menu.this, person + " is not here", Toast.LENGTH_LONG).show();

                                        }


                                    }
                                }
                        );

                        return view;
                    }
                };

        mylv.setAdapter(adapter);
        mylv.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void back(View view)
    {
        Intent intent = new Intent(Menu.this, List.class);

        closeConnect();

        startActivity(intent);

    }

    void acceptConnect(BluetoothAdapter bTAdapter) {
        BluetoothServerSocket temp = null;
        Log.d("SERVERCONNECT", "Attempting to create server");
        UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
        try {
            //temp = bTAdapter.listenUsingRfcommWithServiceRecord(bTAdapter.getName(), mUUID);
            //Testing with hard coded name
            temp = bTAdapter.listenUsingInsecureRfcommWithServiceRecord("Attend", mUUID);
        }
        catch(IOException e) {
            Log.d("SERVERCONNECT", "Could not get a BluetoothServerSocket:" + e.toString());
        }
        //mmServerSocket = temp;
        while(true) {
            try {
                bTSocket = temp.accept();
                Log.d("SERVERCONNECT", "Changing temp to bTSocket");
            }
            catch (IOException e) {
                Log.d("SERVERCONNECT", "Could not accept an incoming connection.");
                break;
            }
            if (bTSocket != null) {

                try {
                    Log.d("SERVERCONNECT", "Closing temp");
                    temp.close();
                }
                catch (IOException e) {
                    Log.d("SERVERCONNECT", "Could not close ServerSocket:" + e.toString());
                }
                break;
            }
        }
    }

    public void closeConnect() {
        stopWorker = true;
        try {
            mmInputStream.close();
        } catch(IOException e){
            Log.d("SERVERCONNECT", "Could not close connection:" + e.toString());
        }
        try {
            bTSocket.close();
        } catch(IOException e) {
            Log.d("SERVERCONNECT", "Could not close connection:" + e.toString());
        }
    }

    void beginListenForData()
    {
        final Handler handler = new Handler();
        final byte delimiter = 10; //This is the ASCII code for a newline character

        try {
            mmInputStream = bTSocket.getInputStream();
        } catch(IOException e){
            Log.d("LISTEN", "Could not create input stream" + e.toString());
        }
        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
                        int bytesAvailable = mmInputStream.available();
                        if(bytesAvailable > 0)
                        {
                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);
                            for(int i=0;i<bytesAvailable;i++)
                            {
                                byte b = packetBytes[i];
                                if(b == delimiter)
                                {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable()
                                    {
                                        public void run()
                                        {
                                            people.add(data);
                                        }
                                    });
                                }
                                else
                                {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }

}
