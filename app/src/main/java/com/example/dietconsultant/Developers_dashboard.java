package com.example.dietconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Developers_dashboard extends AppCompatActivity {


    FirebaseFirestore mRef;

    LinearLayout ll,l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers_dashboard);

        mRef = FirebaseFirestore.getInstance();

        get_info();

    }

    public void get_info(){

        ll = (LinearLayout) findViewById(R.id.patient_f) ;
        l = (LinearLayout) findViewById(R.id.consultant_f) ;

        mRef.collection("Consultant_list").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                for(QueryDocumentSnapshot q: queryDocumentSnapshots){

                    TextView tt = new TextView(Developers_dashboard.this);
                    tt.setText(q.get("name").toString().trim());
                    tt.setTextColor(Color.WHITE);

                    tt.setPadding(10,10,10,10);
                    l.addView(tt);


                }


            }
        });


        mRef.collection("Patient_list").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                for(QueryDocumentSnapshot q: queryDocumentSnapshots){

                    TextView tt = new TextView(Developers_dashboard.this);
                    tt.setText(q.get("name").toString().trim());
                    tt.setTextColor(Color.WHITE);

                    tt.setPadding(10,10,10,10);
                    ll.addView(tt);


                }


            }
        });
    }


}