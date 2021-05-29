package com.example.dietconsultant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Personal_dashboard_patients extends AppCompatActivity {


    FirebaseFirestore myRef;
    FirebaseAuth myAuth;

    TextView text;
    Button consultant;

    String flag;
    String user_name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_dashboard_patients);

        myAuth = FirebaseAuth.getInstance();
        myRef = FirebaseFirestore.getInstance();

        text = (TextView) findViewById(R.id.text_for_patients);
        consultant = (Button) findViewById(R.id.see_consultant);

        flag = getIntent().getStringExtra("Flag");
        user_name = getIntent().getStringExtra("User_name");

        Log.d("Pulled1",String.valueOf(flag) +" Flag");
        Log.d("Pulled1",String.valueOf(user_name)+"-- Uswrname");


        pull_data();
        see_consultant();

    }

    public void see_consultant(){

        consultant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),Consultants_for_patients.class);
                i.putExtra("Flag",flag);
                i.putExtra("User_name",user_name);
                startActivity(i);

            }
        });
    }

    public void pull_data(){


        String[] data = {"","",""};

        myRef.collection("Patient")
                .document(myAuth.getCurrentUser().getUid())
                .collection("Health_info").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    int n =0;

                    for(QueryDocumentSnapshot document : task.getResult()) {


                        Log.d("Pulled_data", document.getId() + " => " + document.getData());
                        String value = document.getId() + " => " + document.getData();
                        data[n] = value;
                        n = n+1;


                    }

                    Log.d("Pulled_data", data[0]);

                    text.setText( data[0]+"\n" +data[1] +"\n" +data[2] );



                }
            }
        });

    }




}