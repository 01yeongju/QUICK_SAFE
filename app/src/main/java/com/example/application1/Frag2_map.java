package com.example.application1;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SearchView;
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
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class Frag2_map extends Fragment implements OnMapReadyCallback {
    private View view;

    // map 관련
    private final int FINE_PERMISSON_CODE = 1;
    private GoogleMap googleMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    // 검색창
    private SearchView mapSearchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag2_map, container, false);

        // 검색창
        mapSearchView = view.findViewById(R.id.mapSearch);

        // map 관련
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMap);
        getLastLocation();

        // 검색창
        mapSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                String location = mapSearchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null || !location.equals("")) {

                    Geocoder geocoder = new Geocoder(requireContext());

                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (addressList != null && !addressList.isEmpty()) {
                        Address address = addressList.get(0);
                        LatLng latLing = new LatLng(address.getLatitude(), address.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(latLing).title(location));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLing, 15));
                    } else {
                        Toast.makeText(requireContext(), "Location not found", Toast.LENGTH_SHORT).show();
                    }

                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);

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
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMap);


        //googleMap.setPadding(0, 12, 0, 100);

        // 확대/축소 버튼 활성화
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

        // 현재 위치 버튼 활성화
        googleMap.setMyLocationEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        // 현재 위치 버튼 위치 설정
        View locationButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).
                getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
        // 화면 오른쪽 하단에서 버튼을 100dp 띄우기 위해 아래와 같이 설정
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        rlp.setMargins(0, 0, 12, 380); // 조정할 여백을 지정합니다.

        /*
        // 현재 위치 버튼 초기화 및 클릭 리스너 설정
        ImageButton myLocationButton = view.findViewById(R.id.myLocationButton);
        myLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 위치 버튼 클릭 시 동작 구현
                if (currentLocation != null) {
                    LatLng myLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(myLocation).title("My Location"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
                }
            }
        });
        */

        if (currentLocation != null) {
            // 현재 위치 마커 표시
            LatLng location = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(location).title("My Location"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));

            // 현재 위치로 지도 이동
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        }

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