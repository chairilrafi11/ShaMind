package com.example.asus.shamind;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    private TextView Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Login = (TextView) findViewById(R.id.Btn_login);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Register.this, Login.class);
                startActivity(mIntent);
            }
        });
    }
}
