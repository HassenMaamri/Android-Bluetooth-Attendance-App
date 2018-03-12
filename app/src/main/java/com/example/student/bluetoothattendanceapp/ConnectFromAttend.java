package com.example.student.bluetoothattendanceapp;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class ConnectFromAttend {
    private BluetoothSocket bTSocket;
    private OutputStream mmOutputStream;

    public boolean connect(BluetoothDevice bTDevice) {
        BluetoothSocket temp = null;
        UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
        try {
            temp = bTDevice.createRfcommSocketToServiceRecord(mUUID);
        } catch (IOException e) {
            Log.d("DeviceConnect", "Could not create RFCOMM socket:" + e.toString());
            return false;
        }
        bTSocket = temp;

        try {
            bTSocket.connect();
        } catch (IOException e) {
            Log.d("DeviceConnect", "Could not connect:" + e.toString());
        }

        if (bTSocket.isConnected()){
            Log.d("DeviceConnect","Connected");
        } else {
            Log.d("DeviceConnect","Not Connected");
        }

        return true;
    }

    public boolean cancel() {
        try {
            bTSocket.close();
        } catch (IOException e) {
            Log.d("CONNECTTHREAD", "Could not close connection:" + e.toString());
            return false;
        }
        return true;
    }

    public void sendData(String msg) throws IOException {

        mmOutputStream = bTSocket.getOutputStream();
        msg += "\n";

        if (bTSocket.isConnected()){
            mmOutputStream.write(msg.getBytes(),0,msg.getBytes().length);
            Log.d("DeviceConnect","Connected");
        } else {
            Log.d("DeviceConnect","Not Connected");
        }
    }
}
