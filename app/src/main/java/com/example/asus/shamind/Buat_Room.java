package com.example.asus.shamind;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;

public class Buat_Room extends AppCompatActivity {
    private Button Kembali, Buat;
    private FirebaseDatabase database;


    private FirebaseAuth.AuthStateListener mAuthListener;

    private String userID,Username;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    private String NamaRoom, JumlahAnggota, Pendidikan, Olahraga, Masak, Teknologi, Politik, GayaHidup;
    private EditText etNamaRoom, etJumlahAnggota;
    private CheckBox cbPendidikan, cbOlahraga, cbMasak, cbTeknologi, cbPolitik, cbGayaHidup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_room);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ShaMind").child("Room");

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is Signed In
                } else {
                    // User is signed out
                }
            }
        };
        userID = user.getUid();
        Username = "Kazehiro";

        Kembali = (Button) findViewById(R.id.buttonKembaliBuatRoom);
        Buat = (Button) findViewById(R.id.buttonBuatRoom);


        etNamaRoom = (EditText) findViewById(R.id.editTextRoomName);
        etJumlahAnggota = (EditText) findViewById(R.id.editTextJumlahAnggota);
        cbPendidikan = (CheckBox) findViewById(R.id.checkBoxPendidikan);
        cbOlahraga = (CheckBox) findViewById(R.id.checkBoxOlahraga);
        cbMasak = (CheckBox) findViewById(R.id.checkBoxMasak);
        cbTeknologi = (CheckBox) findViewById(R.id.checkBoxTeknologi);
        cbPolitik = (CheckBox) findViewById(R.id.checkBoxPolitik);
        cbGayaHidup = (CheckBox) findViewById(R.id.checkBoxGayaHidup);


        Buat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbPendidikan.isChecked()){
                    Pendidikan = "true";
                }else{
                    Pendidikan = "false";
                }
                if(cbOlahraga.isChecked()){
                    Olahraga = "true";
                }else{
                    Olahraga = "false";
                }
                if(cbMasak.isChecked()){
                    Masak = "true";
                }else{
                    Masak= "false";
                }
                if(cbTeknologi.isChecked()){
                    Teknologi = "true";
                }else{
                    Teknologi = "false";
                }
                if(cbPendidikan.isChecked()){
                    Politik = "true";
                }else{
                    Politik = "false";
                }
                if(cbGayaHidup.isChecked()){
                    GayaHidup = "true";
                }else{
                    GayaHidup = "false";
                }
                NamaRoom = etNamaRoom.getText().toString();
                JumlahAnggota = etJumlahAnggota.getText().toString();
                String IdRoom = myRef.push().getKey();
                myRef.child(IdRoom).child("idroom").setValue(IdRoom);
                myRef.child(IdRoom).child("master").setValue(userID);
                myRef.child(IdRoom).child("namaroom").setValue(NamaRoom);
                myRef.child(IdRoom).child("Jumlahanggota").setValue(JumlahAnggota);
                myRef.child(IdRoom).child("anggotamasuk").setValue("1");
                myRef.child(IdRoom).child("anggota").setValue(userID);
                myRef.child(IdRoom).child("pendidikan").setValue(Pendidikan);
                myRef.child(IdRoom).child("olahraga").setValue(Olahraga);
                myRef.child(IdRoom).child("masak").setValue(Masak);
                myRef.child(IdRoom).child("teknologi").setValue(Teknologi);
                myRef.child(IdRoom).child("politik").setValue(Politik);
                myRef.child(IdRoom).child("gayahidup").setValue(GayaHidup);
                myRef.child(IdRoom).child("chat").setValue("!@!-!@!"+userID+","+Username+",Welcome to "+NamaRoom);
            }
        });

        Kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Buat_Room.this, Home.class);
                startActivity(mIntent);
            }
        });
    }





    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
