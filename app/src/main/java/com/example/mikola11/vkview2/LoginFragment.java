package com.example.mikola11.vkview2;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import de.greenrobot.event.EventBus;


public class LoginFragment extends Fragment {

    private WebView myWebView;
    public static final String CHECK_TOKEN = "access_token";
    public static final String CHECK_TIME = "expires_in";
    public static final String CHECK_ID = "user_id";

    public static final String NAME_PREF_TOKEN = "AccessToken";
    public static final String NAME_PREF_TIME = "ExpiresIn";
    public static final String NAME_PREF_ID = "UserId";

    public static final String LOG = "NIKI";

    public static final String REDIRECT_URI = "access_token=";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment, null);
        myWebView = (WebView) v.findViewById(R.id.webView);
        myWebView.getSettings().setSaveFormData(true);
        myWebView.clearCache(true);
        myWebView.loadUrl(getString(R.string.url_start));


        myWebView.setWebViewClient(new WebViewClient() {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Log.d(LOG, "URL = " + url);

                if (url.contains(REDIRECT_URI)) {

                    url = url.replace("#", "?");

                    Uri tokenUri = Uri.parse(url);
                    String accessToken = tokenUri.getQueryParameter(CHECK_TOKEN);
                    String expiresIn = tokenUri.getQueryParameter(CHECK_TIME);
                    String userId = tokenUri.getQueryParameter(CHECK_ID);

                    SharedPreferences saveLoginDataPref;
                    saveLoginDataPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = saveLoginDataPref.edit();
                    editor.putString(NAME_PREF_TOKEN, accessToken);
                    editor.putString(NAME_PREF_TIME, expiresIn);
                    editor.putString(NAME_PREF_ID, userId);
                    editor.apply();

                    Log.d(LOG, "token = " + accessToken);
                    Log.d(LOG, "time = " + expiresIn);
                    Log.d(LOG, "ID = " + userId);

                    EventBus.getDefault().post(new GoToFriendsListEvent());

                    return true;
                }
                return false;
            }
        });

        return v;


    }


}
