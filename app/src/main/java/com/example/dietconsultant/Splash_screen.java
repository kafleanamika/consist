package com.example.dietconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Splash_screen extends AppCompatActivity {


    FirebaseAuth myAuth;
    FirebaseFirestore myRef;
    int flag;
    String user_name = "";
    String id;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        myAuth = FirebaseAuth.getInstance();
        myRef = FirebaseFirestore.getInstance();

        flag = 2;

        try {

            id = myAuth.getCurrentUser().toString();

        } catch (Exception e){


        }
        check_();


    }

    //checking is user was already logged in or not,


    public void check_(){

        if( id == null){

            Intent i = new Intent(getApplicationContext(),Homepage.class);
            startActivity(i);


        } else {

           

            consultant_or_patient();

        }
    }

    public  void consultant_or_patient(){


//initially no one

        //  Log.i("Pulled","Oo shit");
        //  Toast.makeText(Login.this,"IDK", Toast.LENGTH_SHORT).show();
        //
        myRef.collection("Consultant_list").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot q: queryDocumentSnapshots){
                    Log.d("IDK", "Consultant name:"+ q.getId());
                    if(myAuth.getCurrentUser().getUid().equals(q.getId())) {
                        //something universal will be set to something idk
                        Toast.makeText(Splash_screen.this,"Welcome Consultant", Toast.LENGTH_SHORT).show();
                        Log.d("IDK", "Consultant name:"+ q.getId());
                        flag = 1;
                        user_name = q.getString("name");

                        Intent i = new Intent(getApplicationContext(),Consultant_dashboard.class);
                        Log.d("Pulled",String.valueOf(flag) +" Flag");
                        Log.d("Pulled",String.valueOf(user_name)+"-- Uswrname");


                        i.putExtra("Flag",String.valueOf(flag));
                        i.putExtra("User_name",user_name);
                        startActivity(i);

                    }


                }
            }
        });

        if (flag == 2){
            myRef.collection("Patient_list").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for(QueryDocumentSnapshot q: queryDocumentSnapshots){
                        Log.d("IDK", "Patient name:"+ q.getId());
                        if(myAuth.getCurrentUser().getUid().equals(q.getId())) {
                            //something universal will be set to something idk
                            //its a patient
                            Toast.makeText(Splash_screen.this,"Welcome Patient", Toast.LENGTH_SHORT).show();

                            flag = 0;
                            Log.d("User_name",String.valueOf(q.getString("name"))+"--UserName");

                            user_name = q.getString("name");


                            Intent i = new Intent(getApplicationContext(),Personal_dashboard_patients.class);
                            Log.d("Pulled",String.valueOf(flag) +" Flag");
                            Log.d("Pulled",String.valueOf(user_name)+"-- Uswrname");


                            i.putExtra("Flag",String.valueOf(flag));
                            i.putExtra("User_name",user_name);
                            startActivity(i);

                        }


                    }
                }
            });
        }

        // Toast.makeText(Login.this,val, Toast.LENGTH_SHORT).show();



    }
}