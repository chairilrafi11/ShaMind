package com.example.asus.shamind;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;

public class Room extends AppCompatActivity {
    private String ID;
    private ScrollView scrollView;
    private LinearLayout layout;
    private TextView tvKelas;

    private String userID;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ImageButton Kirim;
    private EditText Pesan;
    private String Hasil;
    private String Hasilregex[];
    private String Username;
    private int i = 1;

    String NamaRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);


        NamaRoom = getIntent().getStringExtra("ROOM");

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ID = getIntent().getStringExtra("ID");
        myRef = database.getReference();
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


        Kirim = (ImageButton) findViewById(R.id.Image_Send);
        Pesan = (EditText) findViewById(R.id.editTextMessageS);

        tvKelas = (TextView) findViewById(R.id.tvKelas);
        scrollView = (ScrollView) findViewById(R.id.scrollLayout);
        layout = (LinearLayout) findViewById(R.id.layout1);

        tvKelas.setText(NamaRoom);

        Kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Pesan.getText().toString().equals("")){
                    Username = "Kazehiro";
                    String Upload = Hasil + "!@!-!@!"+userID+","+Username+","+Pesan.getText().toString();
                    myRef.child("ShaMind").child("Room").child(ID).child("chat").setValue(Upload);
                    Pesan.setText("");
                }
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) {
        int Lokasi;
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Hasil =  ds.child("Room").child(ID).child("chat").getValue().toString();
            Hasilregex = Hasil.split("!@!-!@!");
            while(Hasilregex.length>i){
                String[] Asli = Hasilregex[i].split(",");
                if(Asli[0].equals(userID)){
                    System.out.println(Asli[0]+" = "+userID);
                    Lokasi = 0;
                    System.out.println("Lokasi = "+Lokasi);
                    addMessageBox(Asli[2],Lokasi);
                }else {
                    Lokasi = 1;
                    addMessageBox(Asli[2],Lokasi);
                }
                i++;
            }
        }
    }

    public void addMessageBox(String message, int type) {
        TextView textView = new TextView(Room.this);
        textView.setPadding(15,15,15,15);
        textView.setTextSize(20);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.setMargins(0,5,0,5);
        lp2.weight = 1.0f;

        if (type == 1) {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.bubble_in);
        } else {
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.bubble_out);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
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
