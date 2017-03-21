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

    private MarkerOptions audi=new MarkerOptions().position(new LatLng(31.706826, 76.527862)).title("Auditorium");
    private MarkerOptions nescafe=new MarkerOptions().position(new LatLng(31.707344, 76.528386)).title("Nescafe");
    private MarkerOptions park=new MarkerOptions().position(new LatLng(31.707091, 76.528574)).title("Student Park");
    private MarkerOptions juice=new MarkerOptions().position(new LatLng(31.705630, 76.528488)).title("Juice Bar");
    private MarkerOptions ground=new MarkerOptions().position(new LatLng(31.706137, 76.524296)).title("Ground");
    private MarkerOptions oat=new MarkerOptions().position(new LatLng(31.705350, 76.525296)).title("OAT");
    private MarkerOptions sbi=new MarkerOptions().position(new LatLng(31.709634, 76.527522)).title("SBI ATM");
    private MarkerOptions ekta=new MarkerOptions().position(new LatLng(31.712125, 76.524279)).title("Ekta Cafe");
    private MarkerOptions h4=new MarkerOptions().position(new LatLng(31.710850, 76.526885)).title("4H FOOD COURT");
    private MarkerOptions g1=new MarkerOptions().position(new LatLng(31.701761, 76.522805)).title("Gate No.1");
    private MarkerOptions pgh=new MarkerOptions().position(new LatLng(31.703807, 76.526077)).title("PGH");
    private MarkerOptions kbh=new MarkerOptions().position(new LatLng(31.710529, 76.526711)).title("KBH");

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

        map.addMarker(audi);
        map.addMarker(nescafe);
        map.addMarker(park);
        map.addMarker(juice);
        map.addMarker(ground);
        map.addMarker(oat);
        map.addMarker(sbi);
        map.addMarker(ekta);
        map.addMarker(h4);
        map.addMarker(pgh);
        map.addMarker(kbh);
        map.addMarker(g1);

    }
}
