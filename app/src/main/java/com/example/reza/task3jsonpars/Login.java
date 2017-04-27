package com.example.reza.task3jsonpars;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Login extends AppCompatActivity {
    private static final String TAG =Login.class.getSimpleName() ;
    EditText InputEmail, InputPassword;
    private static final String LOGIN_URL = "http://www.mocky.io/v2/59000c371200007106c7b4be";

    ArrayList<HashMap<String,String>> users_list;

    String passwordInput,emailInput;
    SessionManager session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        users_list  = new ArrayList<HashMap<String, String>>();

        session = new SessionManager(getApplicationContext());
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        InputEmail = (EditText) findViewById(R.id.txt_Email);
        InputPassword = (EditText) findViewById(R.id.txt_Password);



    }


    class getData extends AsyncTask<Void, Void, Void> {






        ProgressDialog pg_dialog ;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pg_dialog = new ProgressDialog(Login.this);
            pg_dialog.setMessage("Attempting login...");
            pg_dialog.setIndeterminate(false);
            pg_dialog.setCancelable(false);
            pg_dialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //String email ,password ;
            JsonParser jsparser = new JsonParser();
            String js_String = jsparser.getJsonfromUrl(LOGIN_URL);


            if (js_String!= null) {
                try {
                    JSONObject jsonObj = new JSONObject(js_String);


                    JSONArray hasil = jsonObj.getJSONArray("users");


                    for (int i = 0; i < hasil.length(); i++) {

                        JSONObject user = hasil.getJSONObject(i);
                        String email = user.getString("email").trim();
                        String password = user.getString("password").trim();
                        String token = user.getString("token_authentication").trim();

                        HashMap<String, String> user_list = new HashMap<>();


                       if(emailInput.equals( email) && passwordInput .equals(password)){
                           user_list.put("email", email);
                           user_list.put("password", password);
                           users_list.add(user_list);
                           session.createLoginSession(email, password,token);
                           Intent a = new Intent(Login.this, WelcomeSucces.class);
                           startActivity(a);
                           finish();
                           Log.e("ada", "Kita ambil datanya bang");
                           Log.e("emailnya",email);
                           Log.e("passwordnya", password);
                           Log.e("TOKENNYA",token);
                           break;
                       }else {runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               Toast.makeText(getApplicationContext(),
                                       "email dan password salah",
                                       Toast.LENGTH_LONG)
                                       .show();
                           }
                       });

                           }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
            }
        @Override
        protected void onPostExecute(Void result) {



            // TODO Auto-generated method stub
            super.onPostExecute(result);

            if (pg_dialog.isShowing()){
                pg_dialog.dismiss();}

            Intent a = new Intent(Login.this, WelcomeSucces.class);
            startActivity(a);
            finish();
            }
        }//class getData
    public void LogIn(View login){
        emailInput = InputEmail.getText().toString().trim();
        passwordInput = InputPassword.getText().toString().trim();


            new getData().execute();




        /*if(emailInput.equals("")  || passwordInput.equals("")){
            Toast.makeText(this,"Isi dulu email dan passwordnya",Toast.LENGTH_LONG).show();
        }
       else if (emailInput == "email" && passwordInput =="password"){*/

       /* } else{
        Toast.makeText(this,"Email dan password salah",Toast.LENGTH_LONG).show();;}*/
    }

}
