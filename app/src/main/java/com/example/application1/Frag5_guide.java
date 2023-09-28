package com.example.application1;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class Frag5_guide extends Fragment {
    private View view;
    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag5_guide, container, false);
        /*
        super.onCreate(savedInstanceState);
        webView = view.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // allow the js

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //화면이 계속 켜짐
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.safekorea.go.kr/idsiSFK/neo/sfk/cs/contents/prevent/prevent01.html?menuSeq=126");

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
           */
        return view;
    }
}