package com.example.chess;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class HomeActivity extends AppCompatActivity implements RecyclerViewInterface{

    public static final int STATE_LISTENING = 1;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_CONNECTION_FAILED = 4;
    public static final int STATE_MESSAGE_RECEIVED = 5;
    //
    public static final int MESSAGE_READ = 10;
    public static final int MESSAGE_WRITE = 11;
    public static final int MESSAGE_TOAST = 12;

    private Button B_play, B_quit, B_back, B_passNPlay, B_bluetooth, B_server, B_client;
    private int pageCounter;
    private boolean BT_connect, BT_advertise, BT_scan, BT_location;
    private ActivityResultLauncher<String> permissionResultLauncher;
    private ActivityResultLauncher<Intent> intentResultLauncher;

    private BluetoothService bluetoothService;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private ArrayList<BluetoothDeviceModel> pairedDevices;
    private RecyclerView mRecyclerView;
    BluetoothDevice device;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupBluetooth();
        setupResultLaunchers();
        findViewByIds();
        onClickListeners();
    }

    private void setupBluetooth() {
        bluetoothManager = getSystemService(BluetoothManager.class);
        bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth Unavailable", Toast.LENGTH_LONG).show();
        }

    }

    private void setupResultLaunchers() {

        permissionResultLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        if (!checkBlueoothPermissions())
                            requestBluetoothPermission();
                    } else {
                        backPage();
                    }
                });

        intentResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Not Ok", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void findViewByIds() {
        B_play = findViewById(R.id.B_play);
        B_quit = findViewById(R.id.B_quit);
        B_back = findViewById(R.id.B_back);
        B_passNPlay = findViewById(R.id.B_passNPlay);
        B_bluetooth = findViewById(R.id.B_bluetooth);
        B_server = findViewById(R.id.B_server);
        B_client = findViewById(R.id.B_client);
        mRecyclerView = findViewById(R.id.mRecyclerView);
    }

    private void onClickListeners() {
        B_play.setOnClickListener(v -> nextPage());

        B_quit.setOnClickListener(v -> {
            finishAndRemoveTask();
            System.exit(0);
        });

        B_back.setOnClickListener(v -> backPage());

        B_passNPlay.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        });

        B_bluetooth.setOnClickListener(v -> {
            checkBlueoothPermissions();
            if (checkBlueoothPermissions())
                nextPage();
            else
                showPermissionExplanation();
        });

        B_server.setOnClickListener(v -> {
            if (!checkServerPermissions())
                requestServerPermission();
            turnBluetoothOn();

            Intent discoverableIntent;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED) {
            }
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.putExtra("Bluetooth", true);
            intent.putExtra("Server", true);
            startActivity(intent);
        });

        B_client.setOnClickListener(v -> {

            if(!checkClientPermissions())
                requestClicentPermission();
            turnBluetoothOn();

            if(bluetoothAdapter.isEnabled()) {
                nextPage();

                Set<BluetoothDevice> bondedSet = bluetoothAdapter.getBondedDevices();
                pairedDevices = new ArrayList<>();

                for (BluetoothDevice device : bondedSet) {
                    pairedDevices.add(new BluetoothDeviceModel(device.getName(), device.getAddress(), device));
                }

                BluetoothDeviceRecyclerViewAdapter adapter = new BluetoothDeviceRecyclerViewAdapter(this, pairedDevices, this);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            }
        });
    }

    private void turnBluetoothOn() {

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            intentResultLauncher.launch(enableBtIntent);
        }
    }

    private void showPermissionExplanation () {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            builder.setTitle("A Few Permissions Required");
            builder.setMessage("Bluetooth requires location and bluetooth permission");
        }
        else {
            builder.setTitle("Location Permission Required");
            builder.setMessage("Bluetooth requires location permission");
        }

        // Add a button to close the dialog
        builder.setPositiveButton("OK", (dialog, which) -> {
            nextPage();
            dialog.dismiss();
        });
        builder.setNegativeButton("NO", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void nextPage() {

        switch (pageCounter) {
            case 0:
                B_play.setVisibility(View.GONE);
                B_quit.setVisibility(View.GONE);
                B_back.setVisibility(View.VISIBLE);
                B_passNPlay.setVisibility(View.VISIBLE);
                B_bluetooth.setVisibility(View.VISIBLE);
                break;

            case 1:
                B_bluetooth.setVisibility(View.GONE);
                B_passNPlay.setVisibility(View.GONE);
                B_server.setVisibility(View.VISIBLE);
                B_client.setVisibility(View.VISIBLE);
                if(!checkBlueoothPermissions())
                    requestBluetoothPermission();
                break;

            case 2:
                B_server.setVisibility(View.GONE);
                B_client.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
        }
        pageCounter++;
    }

    private void backPage() {

        switch (pageCounter) {
            case 1:
                B_back.setVisibility(View.GONE);
                B_passNPlay.setVisibility(View.GONE);
                B_bluetooth.setVisibility(View.GONE);
                B_play.setVisibility(View.VISIBLE);
                B_quit.setVisibility(View.VISIBLE);
                break;

            case 2:
                B_server.setVisibility(View.GONE);
                B_client.setVisibility(View.GONE);
                B_passNPlay.setVisibility(View.VISIBLE);
                B_bluetooth.setVisibility(View.VISIBLE);
                break;

            case 3:
                B_server.setVisibility(View.VISIBLE);
                B_client.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
        }
        pageCounter--;

    }

    private boolean checkBlueoothPermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
                == PackageManager.PERMISSION_GRANTED) {
            BT_connect = true;
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            BT_location = true;
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return BT_connect && BT_location;
        }
        else {
            return BT_location;
        }
    }

    private boolean checkServerPermissions() {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADVERTISE)
                == PackageManager.PERMISSION_GRANTED) {
            BT_advertise = true;
            return true;
        }
        return false;
    }

    private boolean checkClientPermissions() {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN)
                == PackageManager.PERMISSION_GRANTED) {
            BT_scan = true;
            return true;
        }
        return false;
    }

    private void requestBluetoothPermission() {

        if (BT_location) {
            Toast.makeText(this, "loc_true", Toast.LENGTH_SHORT).show();
        }
        else {
            permissionResultLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && BT_location) {

            if (BT_connect) {
                Toast.makeText(this, "conn_true", Toast.LENGTH_SHORT).show();

            }
            else {
                permissionResultLauncher.launch(
                        Manifest.permission.BLUETOOTH_CONNECT);
            }
        }
    }

    private void requestServerPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && BT_location) {

            if (BT_advertise) {
                Toast.makeText(this, "adve_true", Toast.LENGTH_SHORT).show();
            }
            else {
                permissionResultLauncher.launch(
                        Manifest.permission.BLUETOOTH_ADVERTISE);
            }
        }

    }

    private void requestClicentPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && BT_location) {

            if (BT_scan) {
                Toast.makeText(this, "scan_true", Toast.LENGTH_SHORT).show();
            }
            else {
                permissionResultLauncher.launch(
                        Manifest.permission.BLUETOOTH_SCAN);
            }
        }
    }

    @Override
    public void onItemClick(int position) {

        device = pairedDevices.get(position).getBluetoothDevice();
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        intent.putExtra("Bluetooth", true);
        intent.putExtra("Bluetooth Device", device);
        startActivity(intent);

    }
}
