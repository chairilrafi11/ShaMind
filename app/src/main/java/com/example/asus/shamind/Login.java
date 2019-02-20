package com.example.asus.shamind;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    private TextView Register;
    private Button Login;
    private Button Login_google;


    private EditText etPassword, etEmail;

    private long backPressedTime;
    private Toast backToast;
    private ProgressDialog progressDialog;
    private int progressStatus = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    startActivity(new Intent(Login.this, Home.class));
                } else {
                    // User is signed out
                }
            }
        };



        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        Register = (TextView) findViewById(R.id.btn_register);
        Login = (Button) findViewById(R.id.Btn_Login);
        Login_google = (Button) findViewById(R.id.btnGoogle);
        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);

        Login_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startActivityForResult(
                        AuthUI.getInstance().createSignInIntentBuilder()
                                .setIsSmartLockEnabled(true)
                                .build(),LOGIN_PERMISSION);*/
                showSnackbar(v, "Layanan Belum Tersedia", 3000);

            }
        });


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    startActivity(new Intent(Login.this, Home.class));
                } else {
                    // User is signed out
                }
            }
        };


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String pass = etPassword.getText().toString();
                System.out.println("HAHAHAHA = " + email + pass);
                if (!email.equals("") && !pass.equals("")) {
                    mAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            startActivity(new Intent(Login.this, Home.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            toastMessage("Email atau Password Salah");
                        }
                    });
                } else {
                    showSnackbar(view, "Harap isi semua kolom", 3000);
                    return;
                }

                progressDialog.setMax(100);
                progressDialog.setMessage("Tunggu Sebentar");
                progressDialog.setTitle("Login");

                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setIndeterminate(false);
                progressDialog.show();
            progressStatus =0;
            final Handler handle = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    progressDialog.incrementProgressBy(1);
                }
            };
                new

            Thread(new Runnable() {
                @Override
                public void run () {
                    while (progressStatus < 100) {
                        progressStatus += 1;
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handle.post(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.setProgress(progressStatus);
                                if (progressStatus == 100) {
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    }
                }
            }).

            start();
        }
    });



     Register.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        Intent mIntent = new Intent(Login.this, Register.class);
        startActivity(mIntent);
    }
    });
}


    public void showSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            Intent intent = new Intent(Login.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        } else {
            backToast = Toast.makeText(getBaseContext(), "Tekan Lagi Untuk Keluar", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
