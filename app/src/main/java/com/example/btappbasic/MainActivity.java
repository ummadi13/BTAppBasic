package com.example.btappbasic;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final int BLUETOOTH_PERMISSION_REQUEST_CODE = 111;
    BluetoothAdapter btAdapter;
    Set<BluetoothDevice> pairedDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBtSettins();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void checkBtSettins()
    {
        if (btAdapter != null && !btAdapter.isEnabled()) {
            Intent intent = new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
            startActivity(intent);
        } else {
            getPairedDevices();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkBtSettins();
    }

    private void getPairedDevices() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Set<BluetoothDevice> devices = btAdapter.getBondedDevices();
            List<BluetoothDevice> devicesList = new ArrayList<>(devices);
            recyclerview(devicesList);
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, BLUETOOTH_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == BLUETOOTH_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted; you can use Bluetooth
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PermissionChecker.PERMISSION_GRANTED) {
                    getPairedDevices();
                }
            } else {
                checkBtSettins();
            }
        }
    }



    private void recyclerview(List<BluetoothDevice> devicesList) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter = new MyAdapter(devicesList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BluetoothDevice item, int position) {
                BluetoothDevice selectedDevice  = item;
                BluetoothSocket btSocket = selectedDev
            }
        });

    }

}


/*
* */