package com.example.dietconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class consultant_information extends AppCompatActivity {


    Button done;
    FirebaseAuth myAuth;
    FirebaseFirestore myRef;
    EditText academics;
    EditText general_information;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_information);

        done = (Button) findViewById(R.id.done);
        academics = (EditText) findViewById(R.id.academic_info);
        general_information = (EditText) findViewById(R.id.general_info);


        myAuth = FirebaseAuth.getInstance();
        myRef = FirebaseFirestore.getInstance();
        Done();


    }

    public void push_info(){

        Map<String,Object> data = new HashMap<>();

        data.put("academics",academics.getText().toString().trim());
        data.put("general_information",general_information.getText().toString().trim());

        myRef.collection("Consultant").document(myAuth.getCurrentUser().getUid())
                .collection("Consultant_info").document("Consulatnat_info").set(data);



    }




    public void Done(){

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                push_info();
                Intent i = new Intent(getApplicationContext(), Consultant_dashboard.class);
                startActivity(i);
            }
        });
    }
}