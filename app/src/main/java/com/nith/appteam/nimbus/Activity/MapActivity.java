package com.nith.appteam.nimbus.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nith.appteam.nimbus.R;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap map;
    private boolean map_available=false;

    private CameraPosition hamirpur=new CameraPosition.Builder()
            .tilt(25)
            .bearing((float) 112.5)
            .zoom(17)
            .target(new LatLng(31.7084,76.5274))
            .build();

    private CameraPosition current_location;
    private MarkerOptions current_marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        MapFragment mapFragment= (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map_available=true;
        map=googleMap;

        map.moveCamera(CameraUpdateFactory.newCameraPosition(hamirpur));
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    }
}
