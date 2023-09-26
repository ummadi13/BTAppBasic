package com.example.btappbasic;

import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btappbasic.bluetooth.BluetoothUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private BluetoothUtils bluetoothUtils;
    private ArrayAdapter<String> pairedDevicesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bluetoothUtils = new BluetoothUtils(this);

        pairedDevicesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        if (bluetoothUtils.isBluetoothAvailable()) {
            if (bluetoothUtils.isBluetoothEnabled()) {
                //set details
                //how to add bluetooth permission


                Set<BluetoothDevice> pairedDevices = bluetoothUtils.getPairedDevices(this);

                if (pairedDevices != null && !pairedDevices.isEmpty()) {
                    for (BluetoothDevice device : pairedDevices) {
                        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        Log.d("devices", device.getName() + "\n" + device.getAddress());
                        //pairedDevicesAdapter.add(device.getName() + "\n" + device.getAddress());
                    }
                }
            } else {
                // Bluetooth is not enabled, you can request the user to enable it here.
            }
        } else {
            // Bluetooth is not available on this device.
        }

       // recyclerview();
    }

    private void recyclerview() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> data = new ArrayList<>();
        data.add("Item 1");
        data.add("Item 2");
        data.add("Item 3");
        //bluetooth devices list.

        MyAdapter adapter = new MyAdapter(data);
        recyclerView.setAdapter(adapter);

    }

}


/*
* */