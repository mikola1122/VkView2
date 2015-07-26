package com.example.mikola11.vkview2;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class TokenStorage {
    private static final String NAME_PREF_TOKEN = "AccessToken";
    private static final String NAME_PREF_ID = "UserId";
    public static final String DEFAULT_PREF_TOKEN = "noToken";


    public static SharedPreferences getPrefs(Context context) {


        return PreferenceManager.getDefaultSharedPreferences(context);

    }
    public static String getAccesTokenPref(Context context) {

        return getPrefs(context).getString(NAME_PREF_TOKEN, DEFAULT_PREF_TOKEN);
    }

    public static int getUserIdPref(Context context) {

        return getPrefs(context).getInt(NAME_PREF_ID, 0);
    }

    public static void setAccesTokenPref(Context context, String value) {
        getPrefs(context).edit().putString(NAME_PREF_TOKEN, value).apply();
    }

    public static void setUserIdPref(Context context, int value) {
        getPrefs(context).edit().putInt(NAME_PREF_ID, value).apply();
    }
}
