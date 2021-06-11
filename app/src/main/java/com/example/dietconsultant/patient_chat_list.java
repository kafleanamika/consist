package com.example.dietconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class patient_chat_list extends AppCompatActivity implements GestureDetector.OnGestureListener {
    LinearLayout layout;

    FirebaseFirestore myRef;
    FirebaseAuth myAuth;

    String patient_name;
    String flag;

    GestureDetector gd;
    float x1,x2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_chat_list);

        layout = (LinearLayout) findViewById(R.id.chat_list_patient);
        myAuth = FirebaseAuth.getInstance();
        myRef = FirebaseFirestore.getInstance();

        patient_name = getIntent().getStringExtra("User_name");
        flag = getIntent().getStringExtra("Flag");

        gd = new GestureDetector(patient_chat_list.this,this);
        check_status();
        pull_data();

    }



    public void check_status(){


     if(!(getIntent().getStringExtra("status") == null)){

         if(getIntent().getStringExtra("status").equals("Yes") || getIntent().getStringExtra("acceptance_status").equals("Yes") ) {
             String consultant = getIntent().getStringExtra("consultant");

             myRef.collection("Paitnet_Talked_to").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                 @Override
                 public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                     for(QueryDocumentSnapshot q: queryDocumentSnapshots){


                         if(q.get("for").equals(patient_name)&&q.get("name").equals(consultant)){


                         } else{

                             Map<String, Object> data = new HashMap<>();
                             data.put("name", consultant);
                             data.put("for",patient_name);
                             myRef.collection("Paitnet_Talked_to").add(data);
                             pull_data();
                         }
                     }

                 }
             });

             Log.i("Check","Working");


 } else {

             Log.i("Check"," 1 -- NOT Working");

         }


    }
     else{
         Log.i("Check"," NOT Working");

     }





    }
    public void pull_data(){

        myRef.collection("Paitnet_Talked_to").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot q:queryDocumentSnapshots){

                    if(String.valueOf(q.getString("for")).equals(patient_name)){

                        Button btn = new Button(patient_chat_list.this);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            btn.setBackground(getDrawable(R.drawable.custon_listing_small_card));
                        }
                        btn.setTextColor(Color.WHITE);
                        btn.setText(q.getString("name").toString().trim());

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
        i.putExtra("Consultant",btn_name);
        i.putExtra("Patient",patient_name);
        startActivity(i);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        gd.onTouchEvent(event);

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();

                break;


            case MotionEvent.ACTION_UP:
                x2 = event.getX();

                if (Math.abs(x2 - x1) > 150) {

                    if (x2 < x1) {

                        Intent i = new Intent(getApplicationContext(), Personal_dashboard_patients.class);
                        i.putExtra("Flag", flag);
                        i.putExtra("User_name", patient_name);
                        startActivity(i);

                    }

                }
        }

                return super.onTouchEvent(event);
        }



    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }



}