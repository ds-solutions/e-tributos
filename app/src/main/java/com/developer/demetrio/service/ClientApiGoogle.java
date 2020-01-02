package com.developer.demetrio.service;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.developer.demetrio.tributos.ListaImoveis;
import com.developer.demetrio.tributos.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ClientApiGoogle extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private Location location;
    private Double lat, lng;
    private GoogleApiClient googleApiClient;
    private static final int PLAY_SERVICE_RESOLUTION_REQUEST = 9000;
    private LocationRequest locationRequest;
    private long UPDATE_INTERVAL = 50, FASTEST_INTRVAL = 50;

    private ArrayList<String> permissionsToRequest = new ArrayList<>();
    private ArrayList<String> permissonsRejected  = new ArrayList<>();
    private ArrayList<String> permissions  = new ArrayList<>();
    private static final int ALL_PERMISSIONS_RESULT = 1011;
    private Context context;
    private Activity activity;



    public void addPermissoes() {
        System.out.println("Entrou no método addPermissoes");
        this.permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        this.permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        this.permissionsToRequest = permissionsToTequest(this.permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(this.permissionsToRequest.toArray(new String[this.permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }

        googleApiClient = new GoogleApiClient.Builder(this.context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
    }

    private ArrayList<String> permissionsToTequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();
        System.out.println("No método permissionsToTequest: ");
        for (String permission: wantedPermissions) {
            if (!hasPermission(permission)) {
                System.out.println("No método permissionsToTequest tem a permissão: "+permission);
                result.add(permission);
            }
        }

        return result;
    }


    public ClientApiGoogle(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        addPermissoes();
    }

    private boolean checkePlayServices() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this.context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this.activity, resultCode, PLAY_SERVICE_RESOLUTION_REQUEST);
            } else {
                this.activity.finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (this.googleApiClient != null) {
            System.out.println("No método onStart não está nulo o googleApiClient!");
            this.googleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!checkePlayServices()) {
            Toast.makeText(this, "É necessário instalar o Google Play Services neste dispositivo!!!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(this.googleApiClient, this);
                  this.googleApiClient.disconnect();
        }
    }


    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(this.context, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        this.location = LocationServices.FusedLocationApi.getLastLocation(this.googleApiClient);
        if (this.location != null) {
            System.out.println("No método onConnected não está nulo o location!");
            Toast.makeText(this, "LAT: " + location.getLatitude() + " LONG: " + this.location.getLongitude(), Toast.LENGTH_LONG).show();

        }

        startLocationUpdates();
    }

    private void startLocationUpdates() {
        this.locationRequest = new LocationRequest();
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        this.locationRequest.setInterval(UPDATE_INTERVAL);
        this.locationRequest.setFastestInterval(FASTEST_INTRVAL);
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Você precisa habilitar permissão do GPS", Toast.LENGTH_SHORT);
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(this.googleApiClient, this.locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (this.location != null) {
            double dist = location.getAccuracy();
            System.out.println("coordenada e distância "+location.getLatitude()+","+location.getLongitude()+"  -  "+dist);
            if (1<= dist)
                System.out.println("distância "+ String.valueOf(dist));
                System.out.println("onLocationChanged "+location.getLatitude()+","+location.getLongitude());

            Toast.makeText(this, "LAT: " + location.getLatitude() + " LONG: " + location.getLongitude(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String permission : permissionsToRequest) {
                    if (!hasPermission(permission)) {
                        permissonsRejected.add(permission);
                    }
                }
                if (permissonsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissonsRejected.get(0))){
                            new AlertDialog.Builder(this.activity)
                                    .setMessage("Esta permissão monitora sua localização. Você precisa permitir isto!")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(permissonsRejected.toArray(new String[permissonsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                    }
                                }
                            }).setNegativeButton("Cancelar", null).create().show();
                            return;
                        }
                    }
                } else {
                    if (this.googleApiClient != null) {
                        this.googleApiClient.connect();
                    }
                }
        }
    }
}
