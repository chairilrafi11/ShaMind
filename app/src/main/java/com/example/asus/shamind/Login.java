package com.example.asus.shamind;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Login extends AppCompatActivity {


    private TextView Register;
    private Button Login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Register = (TextView) findViewById(R.id.btn_register);
        Login    = (Button) findViewById(R.id.Btn_Login);


     Register.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent mIntent = new Intent(Login.this, Register.class);
             startActivity(mIntent);
         }
     });

     Login.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent mIntent = new Intent(Login.this, Home.class);
             startActivity(mIntent);
         }
     });

    }
}
