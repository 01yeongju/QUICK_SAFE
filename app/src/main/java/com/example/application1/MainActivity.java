package com.example.application1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.Manifest;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private FirebaseAuth mFirebaseAuth;

    // bottom navigation 관련
    private BottomNavigationView bottomNavigationView; // 하단 네비게이션 뷰
    private FragmentManager fm, fragmentManager; // fragmentManager는 map관련
    private FragmentTransaction ft;
    private Frag1_c frag1;
    private Frag2_map frag2;
    private Frag3_mypage frag3;


    // map 관련
    private final int FINE_PERMISSON_CODE = 1;
    private GoogleMap googleMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        /*
        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그아웃 하기
                mFirebaseAuth.signOut();
                // 방법1. 로그아웃 후 로그인 페이지로 이동
                // Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                // startActivity(intent);
                // 방법2. app 종료
                finish();
            }
        });
        */
        // 탈퇴처리
        // mFirebaseAuth.getCurrentUser().delete();


        // bottom navigation관련
        bottomNavigationView = findViewById((R.id.bottomNavi));
        bottomNavigationView.setSelectedItemId(R.id.action_map); // 첫화면 대피소 뜰때, 아이콘도 똑같이 바뀌도록
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {

                int itemId = menuItem.getItemId();
                if (itemId == R.id.action_community) {
                    setFrag(0);
                } else if (itemId == R.id.action_map) {
                    setFrag(1);
                } else if (itemId == R.id.action_mypage) {
                    setFrag(2);
                }
            }
        });

        frag1 = new Frag1_c();
        frag2 = new Frag2_map();
        frag3 = new Frag3_mypage();
        setFrag(1); // 첫 프로그먼트 화면을 무엇으로 지정해줄것인지 -> 여기선 대피소가 기본화면으로

        // map 관련
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
    }

    // 프래그먼트 교체가 일어나는 실행문
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.main_frame, frag1);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, frag2);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, frag3);
                ft.commit();
                break;
        }

    }

    // map 관련

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSON_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    currentLocation = location;
                    // 앱 시작 시 지도 초기화 및 현재 위치 가져오기
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
                    if (mapFragment == null) {
                        mapFragment = SupportMapFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().add(R.id.googleMap, mapFragment).commit();
                    }
                    mapFragment.getMapAsync(MainActivity.this);
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

        /* 한밭대 마커 표시
        LatLng location = new LatLng(36.351073954997, 127.29801308566);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.title("한밭대");
        markerOptions.snippet("대학교");

        googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        */
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSON_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }else {
                Toast.makeText(this, "Location permission is denied, please allow the permission!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}