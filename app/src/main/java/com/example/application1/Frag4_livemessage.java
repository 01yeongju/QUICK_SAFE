package com.example.application1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Frag4_livemessage extends Fragment {

    private View view;
    private ScrollView scrollView;
    private LinearLayout linearLayout;
    // private Handler handler;
    private TextView messageTextView;
    private WebView webView;


    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag4_livemessage, container, false);
        // messageTextView = view.findViewById(R.id.message_textview);

        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        messageTextView = view.findViewById(R.id.message_textview);
        final Bundle bundle = new Bundle();

        new Thread(){
            @Override
            public void run() {
                Document doc = null;
                try {
                    // https://www.safekorea.go.kr/idsiSFK/neo/sfk/cs/sfc/dis/disasterMsgList.jsp?menuSeq=679
                    doc = Jsoup.connect("https://m.safekorea.go.kr/idsiSFK/neo/main_m/dis/disasterDataList.html").get();

                    Elements ulElements = doc.select("ul#gen");
                    Log.d("MyTag", "Content: " + ulElements);

                    Elements liElements = ulElements.select("li.has-map");
                    Log.d("MyTag", "Content: " + liElements);

                    Elements aElements = doc.select("ul#gen li.has-map a[href^=javascript:selectList(205779)]");
                    Log.d("MyTag", "Content: " + aElements);

                    Elements ahref = doc.select("a[href^=javascript:selectList(205779)]");
                    Log.d("MyTag", "Href: " + ahref);

                    /*
                    Bundle bundle = new Bundle();
                    bundle.putString("content", ahref); //핸들러를 이용해서 Thread()에서 가져온 데이터를 메인 쓰레드에 보내준다.
                    Message msg = handler.obtainMessage();
                    msg.setData(bundle);
                    handler.sendMessage(msg); */

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return view;
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            messageTextView.setText(bundle.getString("content"));
            return true;
        }
    });

}
