package com.example.btappbasic.bluetooth;


import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import java.util.Set;

public class BluetoothUtils {

    private BluetoothAdapter bluetoothAdapter;
    private Context context;

    private static final int MY_BLUETOOTH_PERMISSIONS_REQUEST  = 124;

    public BluetoothUtils(Context context) {
        this.context = context;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    // Method to check if Bluetooth is available on the device
    public boolean isBluetoothAvailable() {
        return bluetoothAdapter != null;
    }

    // Method to check if Bluetooth is enabled on the device
    public boolean isBluetoothEnabled() {
        return bluetoothAdapter.isEnabled();
    }

    // Method to get a list of paired Bluetooth devices
    public Set<BluetoothDevice> getPairedDevices(Activity context) {
        if (bluetoothAdapter != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context,
                        new String[]{
                               Manifest.permission.BLUETOOTH,
                                Manifest.permission.BLUETOOTH_ADMIN,
                                Manifest.permission.BLUETOOTH_CONNECT
                        },
                        MY_BLUETOOTH_PERMISSIONS_REQUEST);
                return null;
            }
            else {
                return bluetoothAdapter.getBondedDevices();

            }
        } else {
            return null;
        }
    }
}
