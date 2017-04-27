package com.example.reza.task3jsonpars;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.HashMap;

/**
 * Created by REZA on 25/04/2017.
 */

public class WelcomeSucces extends AppCompatActivity {
    TextView txt_Welcome;
    SessionManager session;
    ProgressDialog pDialog;
    JSONArray user_list = null;
    String email,password,token;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_succes);

        session = new SessionManager(getApplicationContext());


        session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManager.PRef_Token);
        email= user.get(SessionManager.PRef_Email);
        password=user.get(SessionManager.PRef_password);
        txt_Welcome = (TextView) findViewById(R.id.label_Welcome);
        //membuat session untuk user
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn() +email +token, Toast.LENGTH_LONG).show();

    }

    public void LogOut(View Log){
        Intent keluar = new Intent(this,Login.class);
        session.logoutUser();
        startActivity(keluar);
    }
}
