package com.example.application1;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class Frag2_map extends Fragment implements OnMapReadyCallback {
    private View view;

    // map 관련
    private final int FINE_PERMISSON_CODE = 1;
    private GoogleMap googleMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag2_map, container, false);
        // map 관련
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
        getLastLocation();

        return view;
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSON_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    currentLocation = location;
                    // 앱 시작 시 지도 초기화 및 현재 위치 가져오기
                    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMap);
                    if (mapFragment == null) {
                        mapFragment = SupportMapFragment.newInstance();
                        getChildFragmentManager().beginTransaction().add(R.id.googleMap, mapFragment).commit();
                    }
                    mapFragment.getMapAsync(Frag2_map.this);
                }
            }
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMyLocationEnabled(true);
        // 현재 위치 마커 표시
        LatLng location = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(location).title("My Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));

        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                // 현재 위치 마커 표시
                if (currentLocation != null) {
                    LatLng myLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(myLocation).title("My Location"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
                }
                return true; // 이벤트 소비 (카메라 이동 실행)
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSON_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(requireContext(), "Location permission is denied, please allow the permission!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}