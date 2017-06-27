package com.example.juena.myrescall02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        WebView webView = new WebView(this);
        setContentView(webView);
        webView.loadUrl("http://globalbee.at");
    }
}
