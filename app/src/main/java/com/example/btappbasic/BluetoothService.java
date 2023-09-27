package com.example.btappbasic;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothService {
    BluetoothSocket bluetoothSocket;
    private BluetoothAdapter btAdapter;

    private Context context;

    private BluetoothDevice btDevice;
    private final UUID APP_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");

    BluetoothService(BluetoothAdapter btAdapter,BluetoothDevice device ,Context context) {
        this.btAdapter = btAdapter;
        this.context = context;
        this.btDevice = device;
    }

    //
    class BtConnection extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                bluetoothSocket = btDevice.createRfcommSocketToServiceRecord(APP_UUID);
                bluetoothSocket.connect();
                Log.d("bt connection status","Connection success full");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket socket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public ConnectedThread(BluetoothSocket socket) {
            this.socket = socket;

            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            inputStream = tmpIn;
            outputStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            try {
                bytes = inputStream.read(buffer);

                handler.obtainMessage(MainActivity.MESSAGE_READ, bytes, -1, buffer).sendToTarget();
            } catch (IOException e) {
                connectionLost();
            }
        }

        public void write(byte[] buffer) {
            try {
                outputStream.write(buffer);
                handler.obtainMessage(MainActivity.MESSAGE_WRITE, -1, -1, buffer).sendToTarget();
            } catch (IOException e) {

            }
        }

        public void cancel() {
            try {
                socket.close();
            } catch (IOException e) {

            }
        }
    }

}
