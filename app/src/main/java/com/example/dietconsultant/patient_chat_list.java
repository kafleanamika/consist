package com.example.dietconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class patient_chat_list extends AppCompatActivity {
    LinearLayout layout;

    FirebaseFirestore myRef;
    FirebaseAuth myAuth;

    String patient_name;
    String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_chat_list);

        layout = (LinearLayout) findViewById(R.id.chat_list_patient);
        myAuth = FirebaseAuth.getInstance();
        myRef = FirebaseFirestore.getInstance();
        patient_name = getIntent().getStringExtra("User_name");
        flag = getIntent().getStringExtra("Flag");



    }


    public void pull_data(){

        myRef.collection("Paitnet_Talked_to").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot q:queryDocumentSnapshots){

                    if(q.getId().equals(patient_name)){

                        Button btn = new Button(patient_chat_list.this);
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
        i.putExtra("Consultant",patient_name);
        i.putExtra("Patient",btn_name);
        startActivity(i);
    }


}