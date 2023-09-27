package com.example.btappbasic;

import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<BluetoothDevice> data;

    public MyAdapter(List<BluetoothDevice> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BluetoothDevice item = data.get(position);
        if (ActivityCompat.checkSelfPermission(holder.btaddress.getContext(), android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            holder.btname.setText(item.getName());
            holder.btaddress.setText(item.getAddress());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView btname,btaddress;

        public ViewHolder(View itemView) {
            super(itemView);
            btname = itemView.findViewById(R.id.btname);
            btaddress = itemView.findViewById(R.id.btaddress);
        }
    }
}

