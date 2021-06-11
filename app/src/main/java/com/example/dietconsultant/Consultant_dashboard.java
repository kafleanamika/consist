package com.example.dietconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Consultant_dashboard extends AppCompatActivity {

    LinearLayout layout;

    FirebaseFirestore myRef;
    FirebaseAuth myAuth;

    Button bt,bt2;

    String consultant_name;
    String flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_dashboard);

        layout = (LinearLayout) findViewById(R.id.layout);
        myAuth = FirebaseAuth.getInstance();
        myRef = FirebaseFirestore.getInstance();
        consultant_name = getIntent().getStringExtra("User_name");
        flag = getIntent().getStringExtra("Flag");

        Toast.makeText(this,String.valueOf(flag),Toast.LENGTH_LONG).show();
        bt = (Button) findViewById(R.id.d_requests);
        bt2 = (Button) findViewById(R.id.d_chats);
        changing_layouts();
         pull_data();
        sign_out();
    }

    public void sign_out(){

        Button s = (Button) findViewById(R.id.sign_out);

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),signout.class);
                startActivity(i);
            }
        });

    }

    public void changing_layouts(){


        bt.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {

                                      Intent i = new Intent(getApplicationContext(),booking_requests_consultant.class);
                                      i.putExtra("flag",flag);
                                      i.putExtra("Consultant",consultant_name);
                                      startActivity(i);

                                  }
                              }

        );

        bt2.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {

                                      Intent i = new Intent(getApplicationContext(),once_meetings_over.class);
                                      i.putExtra("flag",flag);
                                      i.putExtra("Consultant",consultant_name);
                                      startActivity(i);

                                  }
                              }

        );


    }
    public void pull_data(){

        myRef.collection("Paitnet_Talked_to").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot q:queryDocumentSnapshots){
                    q.getString("name");

                    if(q.getString("name").equals(consultant_name)){

                        Button btn = new Button(Consultant_dashboard.this);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            btn.setBackground(getDrawable(R.drawable.custon_listing_small_card));
                        }
                        btn.setTextColor(Color.WHITE);
                        btn.setText(q.getString("for"));
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                 _chat_box(btn.getText().toString().trim());
                            }
                        });
                        layout.addView(btn);

                    }
                }
            }
        });


    }

    public void _chat_box(String btn_name){

        Intent i = new Intent(getApplicationContext(),Chat_.class);
        i.putExtra("Flag",String.valueOf(flag));
        i.putExtra("Consultant",consultant_name);
        i.putExtra("Patient",btn_name);
        startActivity(i);
    }



}