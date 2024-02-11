package com.example.chess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BluetoothDeviceRecyclerViewAdapter extends RecyclerView.Adapter<BluetoothDeviceRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<BluetoothDeviceModel> pairedDevices;
    private RecyclerViewInterface recyclerViewInterface;

    public BluetoothDeviceRecyclerViewAdapter(Context context,
                                              ArrayList<BluetoothDeviceModel> pairedDevices,
                                              RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.pairedDevices = pairedDevices;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public BluetoothDeviceRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull BluetoothDeviceRecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.TW_deviceName.setText(pairedDevices.get(position).getDeviceName());
        holder.TW_deviceMac.setText(pairedDevices.get(position).getDeviceMac());

    }

    @Override
    public int getItemCount() {
        return pairedDevices.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView TW_deviceName, TW_deviceMac;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            TW_deviceName = itemView.findViewById(R.id.TW_deviceName);
            TW_deviceMac = itemView.findViewById(R.id.TW_macAddress);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }
}
