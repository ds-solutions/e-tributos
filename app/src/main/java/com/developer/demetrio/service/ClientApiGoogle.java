package com.developer.demetrio.service;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ClientApiGoogle {

    private Location location;
    private Double lat, lng;
    private static final int PLAY_SERVICE_RESOLUTION_REQUEST = 9000;
    private long UPDATE_INTERVAL = 50, FASTEST_INTRVAL = 50;

    private ArrayList<String> permissionsToRequest = new ArrayList<>();
    private ArrayList<String> permissonsRejected  = new ArrayList<>();
    private ArrayList<String> permissions  = new ArrayList<>();
    private static final int ALL_PERMISSIONS_RESULT = 1011;
    private Context context;
    private Activity activity;


    public ClientApiGoogle() {
    }
}
