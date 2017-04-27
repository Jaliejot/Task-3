package com.example.reza.task3jsonpars;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by REZA on 26/04/2017.
 */

public class SessionManager {
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context cOntext;
    private static final String IS_LOGIN = "IsLoggedIn";
    public static String PRef_Email = "email";
    public static String PRef_password = "password";
    public static String PRef_Token = "token_authentication";

    public SessionManager(Context context){
        this.cOntext = context;
        pref = context.getSharedPreferences(PRef_Email, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String email, String password,String token) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(PRef_Token,token);
        editor.putString(PRef_Email, email);
        editor.putString(PRef_password, password);
        editor.commit();
    }
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(PRef_Token,pref.getString(PRef_Token,null));
        user.put(PRef_Email, pref.getString(PRef_Email, null));
        user.put(PRef_password, pref.getString(PRef_password, null));

        return user;
    }

    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            Intent i = new Intent(cOntext, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            cOntext.startActivity(i);
            //((Activity)_context).finish();
        }

    }
   public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        Intent i = new Intent(cOntext, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        cOntext.startActivity(i);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
