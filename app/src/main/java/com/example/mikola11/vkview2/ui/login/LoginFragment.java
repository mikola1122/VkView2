package com.example.mikola11.vkview2.ui.login;

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

import com.example.mikola11.vkview2.event.GetUserData;
import com.example.mikola11.vkview2.utils.TokenStorage;
import com.example.mikola11.vkview2.event.GoToFriendsListEvent;
import com.example.mikola11.vkview2.R;
import com.example.mikola11.vkview2.ui.MainActivity;

import de.greenrobot.event.EventBus;


public class LoginFragment extends Fragment implements LoginInterf {

    private WebView myWebView;
    public static final String CHECK_TOKEN = "access_token";
    public static final String CHECK_ID = "user_id";

    public static final String LOG = "NIKI";

    public static final String REDIRECT_URI = "access_token=";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, null);

        ((MainActivity) getActivity()).getSupportActionBar().hide();
//        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity) getActivity()).setSearchVisibilCompletedLoad(false);
        Log.d("NIKI", "Main toolbar not show");

        myWebView = (WebView) v.findViewById(R.id.webView);
        myWebView.getSettings().setSaveFormData(true);
        myWebView.clearCache(true);
        myWebView.loadUrl(getString(R.string.url_start));

        loadAccessToken();

        return v;


    }

    private void loadAccessToken() {
        myWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Log.d(LOG, "URL = " + url);

                if (url.contains(REDIRECT_URI)) {
                    url = url.replace("#", "?");
                    Uri tokenUri = Uri.parse(url);
                    String accessToken = tokenUri.getQueryParameter(CHECK_TOKEN);
                    String userId = tokenUri.getQueryParameter(CHECK_ID);
                    int userIdInt=Integer.valueOf(userId);

                    TokenStorage.setAccesTokenPref(getActivity(), accessToken);
                    TokenStorage.setUserIdPref(getActivity(), userIdInt);

                    Log.d(LOG, "token = " + accessToken);
                    Log.d(LOG, "ID = " + userId);

                    EventBus.getDefault().post(new GetUserData());
                    EventBus.getDefault().post(new GoToFriendsListEvent());

                    return true;
                }
                return false;
            }
        });
    }


}
