package com.example.mikola11.vkview2;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


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

        myWebView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                GettingAccessToken(url);
            }
        });
        return v;


    }

    public void GettingAccessToken(String url) {
        if (url.contains("REDIRECT_URI")) {
            String[] separatedToken = url.split("[=&]+");
            for (int i = 0; i<separatedToken.length; i++){
                if (separatedToken[i].contains("access_token")) {
                    SaveAccessToken(separatedToken[i + 1]);
                } else if (separatedToken[i].contains("expires_in")) {
                    SaveExpiresIn(separatedToken[i + 1]);
                } else if (separatedToken[i].contains("user_id")) {
                    SaveUserId(separatedToken[i + 1]);
                }

            }

        }
        return;
    }

    public SharedPreferences SaveAccessToken(String accessToken) {
        SharedPreferences saveAccessTokenPref;
        saveAccessTokenPref = getActivity().getSharedPreferences("AccessTokenPreferences", Context.MODE_PRIVATE);
        Editor editor = saveAccessTokenPref.edit();
        editor.putString("AccessToken", accessToken);
        editor.commit();
        Log.i("niki", saveAccessTokenPref.toString());
        return saveAccessTokenPref;
    }

    public SharedPreferences SaveExpiresIn(String expiresIn) {
        SharedPreferences saveExpiresInPref;
        saveExpiresInPref = getActivity().getSharedPreferences("AccessTokenPreferences", Context.MODE_PRIVATE);
        Editor editor = saveExpiresInPref.edit();
        editor.putString("AccessToken", expiresIn);
        editor.commit();
        return saveExpiresInPref;
    }

    public SharedPreferences SaveUserId(String userId) {
        SharedPreferences saveUserIdPref;
        saveUserIdPref = getActivity().getSharedPreferences("AccessTokenPreferences", Context.MODE_PRIVATE);
        Editor editor = saveUserIdPref.edit();
        editor.putString("AccessToken", userId);
        editor.commit();
        return saveUserIdPref;
    }
}
