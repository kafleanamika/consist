package com.example.dietconsultant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Consultants_for_patients extends AppCompatActivity {



    CollectionReference myRef;
    FirebaseAuth myAuth;
    LinearLayout layout;
    CollectionReference myRef_p;
    TextView text;
    Button recommened;
    Consultant_or_patient CoP;

    String flag;
    String user_name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultants_for_patients);
        myAuth = FirebaseAuth.getInstance();
        myRef = FirebaseFirestore.getInstance().collection("Consultant_list");
        myRef_p = FirebaseFirestore.getInstance().collection("Patient_list");
        layout = (LinearLayout) findViewById(R.id.layout_id);

        recommened = (Button) findViewById(R.id.see_recommended);

        flag = getIntent().getStringExtra("Flag");
        user_name = getIntent().getStringExtra("User_name");

        Log.d("Pulled1",String.valueOf(flag) +" Flag");
        Log.d("Pulled1",String.valueOf(user_name)+"-- Uswrname");

        CoP = new Consultant_or_patient();
       // see_personal_info();
        pull_data();


    }

    public void see_personal_info(){

        recommened.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),Personal_dashboard_patients.class);
                startActivity(i);

            }
        });
    }


    public void pull_data() {

        myRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                String mate ="";
                for(QueryDocumentSnapshot q : queryDocumentSnapshots){

                    retrive_pc_list c = q.toObject(retrive_pc_list.class);

                     String consultants  = c.getName() ;
                     add_consultant_to_view(consultants);

                    Log.d("Retrived",mate );

                }

            }
        });


    }


    public void add_consultant_to_view(String name_consultant){

        Button button = new Button(this);
        button.setText(name_consultant);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(getApplicationContext(),Chat_.class);
                Log.d("Names_C",button.getText().toString().trim()+"--name of user");

                i.putExtra("Consultant", button.getText().toString().trim());

                //run a query to retrieve the name of the patient here

                i.putExtra("Patient",user_name);

                Log.d("Names_C",user_name+"--name of user");

                i.putExtra("Flag",flag);

                Log.d("Names_C",flag+"--name of user");

                Log.d("Names_C",CoP.get_name()+"--name of user");
                startActivity(i);

            }
        });

        layout.addView(button);




    }




}