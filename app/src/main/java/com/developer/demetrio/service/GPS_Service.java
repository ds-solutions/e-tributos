package com.developer.demetrio.service;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.developer.demetrio.model.LatLng;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.security.Permission;
import java.security.Permissions;
import java.util.ArrayList;

public class GPS_Service extends LocationCallback implements LocationListener {
    public final static  int REQUEST_CODE_ERRO_PLAY_SERVICES = 9000;

    private final Context context;
    private boolean isGPSEnabled = false;
    private boolean isNetWorkEnabled = false;
    private boolean canGetLocation = false;

    private FusedLocationProviderClient client;

    private Location location;
    private Double lat, lng;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    private static final long MIN_TIME_BW_UPDATES = 0;

    protected LocationManager locationManager;
    private Activity activity;
    private ArrayList permissions;

    private Permission permission;

    private LatLng latLng;

    public GPS_Service(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        this.client = LocationServices.getFusedLocationProviderClient(this.context);
        getLocation();
    }

    @SuppressLint("MissingPermission")
    public void getLocation() {
        LocationRequest request = new LocationRequest();
        request.setInterval(100);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        request.setSmallestDisplacement(1);

        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},795);
        }
        //solicitando permissao receber sms
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},785);
        }

        client.requestLocationUpdates(request, this, null);
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        super.onLocationResult(locationResult);
        this.lat = locationResult.getLastLocation().getLatitude();
        this.lng = locationResult.getLastLocation().getLongitude();
        int dist = (int) locationResult.getLastLocation().getAccuracy();
        System.out.println("Dentro do onLocationChanged a distancia é: "+ dist+"  coord:  " +this.lat+","+this.lng);
    }

    /*
    @SuppressLint("MissingPermission")
    private Location getLocation() {
            solicitarPermissaoParaLocalizar();

        try {
            this.locationManager = (LocationManager) this.context.getSystemService(LOCATION_SERVICE);
            this.isGPSEnabled = this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            this.isNetWorkEnabled = this.locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!this.isNetWorkEnabled && !this.isGPSEnabled) {
                System.out.println("Nenhum provedor de Rede está habilitado");
            } else {
                this.canGetLocation = true;
                if (isNetWorkEnabled) {
                    this.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (this.locationManager != null) {
                        this.location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        System.out.println("Do NetWork LAT: "+this.location.getLatitude() + " LNG: "+this.location.getLongitude());
                    }
                }
                if (this.isGPSEnabled) {
                    this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("GPS", "GPS");
                    if (this.locationManager != null) {
                        this.location = this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        System.out.println("Do GPS LAT: "+this.location.getLatitude() + " LNG: "+this.location.getLongitude());
                    }
                }

                if (location != null) {
                    this.lat = this.location.getLatitude();
                    this.lng = this.location.getLongitude();
                    System.out.println("LAT: "+this.lat + " LNG: "+this.lng);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
*/
    @Override
    public void onLocationChanged(Location location) {

       int dist = (int) location.getAccuracy();
        System.out.println("Dentro do onLocationChanged a distancia é: "+ dist);
       if (dist <= 10) {
          this.latLng = new LatLng(location.getLatitude(), location.getLongitude());
           System.out.println("latLng não está nulo");
       }
    }

    public LatLng getLatLng() {
       return latLng;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void solicitarPermissaoParaLocalizar() {

        this.permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        this.permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);


    }


}
