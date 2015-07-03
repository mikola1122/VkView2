package com.example.mikola11.vkview2;

import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ActionMenuView;

import java.util.List;


public class Fragment1 extends Fragment {

    private WebView myWebView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment1, null);
        //задаю вебвюшці юерельку
        myWebView = (WebView) v.findViewById(R.id.webView);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setSaveFormData(true);
        myWebView.clearCache(true);
        myWebView.loadUrl("https://oauth.vk.com/authorize?client_id=4980642&scope=friends,photos&redirect_uri=https://oauth.vk.com/blank.html&scope=12&display=mobile&v=5.34&response_type=token");





        return v;
    }
}
