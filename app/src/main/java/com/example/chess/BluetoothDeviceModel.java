package com.example.chess;

import android.bluetooth.BluetoothDevice;

public class BluetoothDeviceModel {

    private BluetoothDevice bluetoothDevice;
    private String deviceName;
    private String deviceMac;


    public BluetoothDeviceModel(String deviceName, String deviceMac, BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
        this.deviceName = deviceName;
        this.deviceMac = deviceMac;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }
}
