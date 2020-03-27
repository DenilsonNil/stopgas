package br.com.kualit.stopgas;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mapa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        FloatingActionButton floatingActionButton;
        floatingActionButton = findViewById(R.id.floatQuestion);
        floatingActionButton.setOnClickListener(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;

        LatLng stopGasLocation = new LatLng(-22.259142, -49.926547);
        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder.target(stopGasLocation).zoom(18);

        CameraPosition cameraPos = builder.build();

        CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPos);

        mapa.addMarker(new MarkerOptions().position(stopGasLocation).title("Stop Gás e água"));
        mapa.moveCamera(update);

    }

    @Override
    public void onClick(View v) {

        DialogAddress address = new DialogAddress();
        address.show(getSupportFragmentManager(), "2");


    }
}
