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
    public static final String CHECK_URI = "REDIRECT_URI";
    public static final String CHECK_TOKEN = "access_token";
    public static final String CHECK_TIME = "expires_in";
    public static final String CHECK_ID = "user_id";
    public static final String NAME_PREF_TOKEN = "AccessToken";
    public static final String NAME_PREF_TIME = "ExpiresIn";
    public static final String NAME_PREF_ID = "UserId";
    public static final String PREFERENCES_NAME = "PreferencesToken";
    public static final String LOG = "NIKI";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment1, null);
        //задаю вебвюшці юерельку
        myWebView = (WebView) v.findViewById(R.id.webView);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setSaveFormData(true);
        myWebView.clearCache(true);
        myWebView.loadUrl(getString(R.string.url_start));

        // TODO use another method not onPageFinished
//        myWebView.setWebViewClient(new WebViewClient() {
//            public void onPageFinished(WebView view, String url) {
//                GettingAccessToken(url);
//            }
//        });

        

        return v;

    }

    // TODO move hardcoded strings to constants
    public void GettingAccessToken(String url) {
        if (url.contains(CHECK_URI)) {
            String[] separatedToken = url.split("[=&]+");
            for (int i = 0; i < separatedToken.length; i++) {
                if (separatedToken[i].contains(CHECK_TOKEN)) {
                    SaveAccessToken(separatedToken[i + 1]);
                } else if (separatedToken[i].contains(CHECK_TIME)) {
                    SaveExpiresIn(separatedToken[i + 1]);
                } else if (separatedToken[i].contains(CHECK_ID)) {
                    SaveUserId(separatedToken[i + 1]);
                }
            }
        }
        return;
    }

    // TODO move hardcoded strings to constants
    public SharedPreferences SaveAccessToken(String accessToken) {
        SharedPreferences saveAccessTokenPref;
        saveAccessTokenPref = getActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = saveAccessTokenPref.edit();
        editor.putString(NAME_PREF_TOKEN, accessToken);
        editor.commit();
        Log.i(LOG, saveAccessTokenPref.toString());
        return saveAccessTokenPref;
    }

    public SharedPreferences SaveExpiresIn(String expiresIn) {
        SharedPreferences saveExpiresInPref;
        saveExpiresInPref = getActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = saveExpiresInPref.edit();
        editor.putString(NAME_PREF_TIME, expiresIn);
        editor.commit();
        return saveExpiresInPref;
    }

    public SharedPreferences SaveUserId(String userId) {
        SharedPreferences saveUserIdPref;
        saveUserIdPref = getActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = saveUserIdPref.edit();
        editor.putString(NAME_PREF_ID, userId);
        editor.commit();
        return saveUserIdPref;
    }
}
