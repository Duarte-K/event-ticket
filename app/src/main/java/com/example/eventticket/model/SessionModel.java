package com.example.eventticket.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionModel {
    private SharedPreferences preference;

    public SessionModel(Context context){
        preference = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUserName(String username) {
        preference.edit().putString("username", username).commit();
    }

    public String getUserName() {
        String username = preference.getString("username","");
        return username;
    }
}
