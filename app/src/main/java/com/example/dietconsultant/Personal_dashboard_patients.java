package com.example.dietconsultant;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Personal_dashboard_patients extends AppCompatActivity implements GestureDetector.OnGestureListener  {


    FirebaseFirestore myRef;
    FirebaseAuth myAuth;

    Button consultant;

    String flag;
    String user_name = "";

    private float x1,x2;
    GestureDetector gd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_dashboard_patients);

        this.gd = new GestureDetector(Personal_dashboard_patients.this,this);
        myAuth = FirebaseAuth.getInstance();
        myRef = FirebaseFirestore.getInstance();


        flag = getIntent().getStringExtra("Flag");
        user_name = getIntent().getStringExtra("User_name");

        Log.d("Pulled1",String.valueOf(flag) +" Flag");
        Log.d("Pulled1",String.valueOf(user_name)+"-- Uswrname");


        pull_data();
        sign_out();


    }

    public void sign_out(){

        Button s = (Button) findViewById(R.id.sign_out_p);

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),signout.class);
                startActivity(i);
            }
        });

    }


    public void pull_data(){


        String[] data = {"","",""};

        myRef.collection("Patient")
                .document(myAuth.getCurrentUser().getUid())
                .collection("Health_info").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    int n =0;

                    for(QueryDocumentSnapshot document : task.getResult()) {


                        Log.d("Pulled_data", document.getId() + " => " + document.getData());
                        String value = document.getId() + " => " + document.getData();
                        data[n] = value;
                        n = n+1;
                        try {







                                create_data_display(document.getString("Calorie"),document.getString("Time"), document.getId());



                        } catch (Exception e){


                        }

                    }

                    Log.d("Pulled_data", data[0]);




                }
            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void create_data_display(String calorie, String exercise, String week){

        LinearLayout ll  = (LinearLayout) findViewById(R.id.lL);
        TextView tt = new TextView(Personal_dashboard_patients.this);
        TextView t2 = new TextView(Personal_dashboard_patients.this);

        tt.setTextColor(Color.WHITE);
        tt.setTextSize(20);
        tt.setText(week+"\n"+"Calorie you need to eat "+" is - "+calorie +"\n" +"Minimum exercise you need to do - " +exercise +"minutes");
        tt.setBackground(getDrawable(R.drawable.custon_listing_small_card));
        tt.setPadding(20,20,20,20);
        ll.addView(tt);
        ll.addView(t2);


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

            float valX = x2 - x1;

            if (Math.abs(valX) > 150) {

                if (x2 > x1) {

                    //left

                    Intent i = new Intent(getApplicationContext(),patient_chat_list.class);
                    i.putExtra("Flag",flag);
                    i.putExtra("User_name",user_name);



                    startActivity(i);

                } else {

                   // animation vibration

                    //right

                    Intent i = new Intent(getApplicationContext(),Consultants_for_patients.class);
                    i.putExtra("Flag",flag);
                    i.putExtra("User_name",user_name);
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
