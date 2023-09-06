package com.example.application1;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag3_mypage extends Fragment {
    private View view;
    private LinearLayout linear1, linear2, linear3, linear4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag3_mypage, container, false);

        linear1 = view.findViewById(R.id.medical_info_frame);
        linear2 = view.findViewById(R.id.call_frame);
        linear3 = view.findViewById(R.id.condition_frame);
        linear4 = view.findViewById(R.id.report_frame);


        linear1.setVisibility(View.INVISIBLE);
        linear2.setVisibility(View.INVISIBLE);
        linear3.setVisibility(View.INVISIBLE);
        linear4.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View medical_info_Button = view.findViewById(R.id.medical_info);
        View call_Button = view.findViewById(R.id.call);
        View condition_Button = view.findViewById(R.id.condition);
        View report_Button = view.findViewById(R.id.report);

        // '의료정보' 버튼 선택
        medical_info_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear1.setVisibility(View.VISIBLE);
                linear2.setVisibility(View.INVISIBLE);
                linear3.setVisibility(View.INVISIBLE);
                linear4.setVisibility(View.INVISIBLE);
            }
        });

        // '긴급연락처' 버튼 선택
        call_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear1.setVisibility(View.INVISIBLE);
                linear2.setVisibility(View.VISIBLE);
                linear3.setVisibility(View.INVISIBLE);
                linear4.setVisibility(View.INVISIBLE);
            }
        });

        // '상태신고' 버튼 선택
        condition_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear1.setVisibility(View.INVISIBLE);
                linear2.setVisibility(View.INVISIBLE);
                linear3.setVisibility(View.VISIBLE);
                linear4.setVisibility(View.INVISIBLE);
            }
        });

        // '기록' 버튼 선택
        report_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear1.setVisibility(View.INVISIBLE);
                linear2.setVisibility(View.INVISIBLE);
                linear3.setVisibility(View.INVISIBLE);
                linear4.setVisibility(View.VISIBLE);
            }
        });
    }
}
