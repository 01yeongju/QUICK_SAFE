package com.example.application1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;


    private BottomNavigationView bottomNavigationView; // 하단 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Frag1_c frag1;
    private Frag2_map frag2;
    private Frag3_mypage frag3;



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

    }

    // 프래그먼트 교체가 일어나는 실행문
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch(n) {
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


}