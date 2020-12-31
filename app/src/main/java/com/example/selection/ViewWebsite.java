package com.example.selection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ViewWebsite extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MsgBox("Hello from ViewWebsite");
        viewWebsite = findViewById(R.id.viewWebsite);
        viewWebsite.setWebViewClient(new WebViewClient());
        viewWebsite.loadUrl("https://www.google.com");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_view_website;
    }

    @Override
    protected String getActivityName() {
        return "Others";
    }
}
