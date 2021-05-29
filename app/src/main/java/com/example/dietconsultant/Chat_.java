package com.example.dietconsultant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Chat_ extends AppCompatActivity {

    int flag;

    String consultant_name;
    String patient_name;


    TextView name_title;
    EditText send_box;
    Button send;
    LinearLayout chat_info;


    FirebaseFirestore myRef;
    FirebaseAuth myAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        consultant_name = getIntent().getStringExtra("Consultant");
        patient_name = getIntent().getStringExtra("Patient");
        flag = Integer.parseInt(getIntent().getStringExtra("Flag"));



        Log.d("Pulled1",String.valueOf(flag) +" Flag");
        Log.d("Pulled1",String.valueOf(patient_name)+"-- Uswrname");

        name_title = (TextView) findViewById(R.id.Name_);
        chat_info = (LinearLayout) findViewById(R.id.Chat_info);

        send_box = (EditText) findViewById(R.id.input_to_chat);
        send = (Button) findViewById(R.id.send_text);

        myAuth = FirebaseAuth.getInstance();
        myRef = FirebaseFirestore.getInstance();


        set_activity_name();
        send_();

    }

    @Override
    protected void onStart() {
        super.onStart();
        realtime_data_pull();

    }

    public void set_activity_name(){

        //if its doctor set the patient name
        Log.d("Chat11",String.valueOf(flag)+"--FLAG");

        if (flag == 0) {
            //patient
            name_title.setText(consultant_name);
            Log.d("Chat11",String.valueOf(flag)+"--FLAG");
            push_to_database(patient_name);
        } else if(flag == 1){

            // consultant

            name_title.setText(patient_name);
            Log.d("Chat11",String.valueOf(flag)+"--FLAG");
            push_to_database(consultant_name);

        } else {

            Toast.makeText(this,"ERROR!",Toast.LENGTH_LONG);
            Log.d("Chat11",String.valueOf(flag)+"--FLAG");

        }



        //if it is patient set consultant name
    }
int data_number = 0;
    String value = "";
    public void push_to_database(String sender){

        //every time a user sends a data send it to databse

        if(!send_box.getText().toString().trim().isEmpty()){

            Map<String,Object> data = new HashMap<>();
            myRef.collection("Chat").document(consultant_name).collection(patient_name).orderBy("number").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(queryDocumentSnapshots.isEmpty()){
                        data.put("data",sender + " -->"+ send_box.getText().toString().trim());
                        data.put("number",String.valueOf(data_number));
                        myRef.collection("Chat").document(consultant_name).collection(patient_name).add(data);
                        Log.d("data_number","wrong here");


                    }else{

                        for(QueryDocumentSnapshot q : queryDocumentSnapshots){

                            value = q.getString("number");
                            data_number = Integer.parseInt(value);
                            Log.d("data_number",value);

                        }
                        Log.d("data_number","\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");


                        data_number = Integer.parseInt(value);
                        data_number = data_number+1;
                        Log.d("data_number",value);
                        data.put("data",sender + " -->"+ send_box.getText().toString().trim());
                        data.put("number",String.valueOf(data_number));
                        myRef.collection("Chat").document(consultant_name).collection(patient_name).add(data);

                    }

                }
            });




        }





    }

    public void realtime_data_pull(){

        //pull data for that particular person and set it to the text on real time
        myRef.collection("Chat").document(consultant_name).collection(patient_name).orderBy("number").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                chat_info.removeAllViews();

                for(QueryDocumentSnapshot s : value){


                TextView view = new TextView(Chat_.this);
                view.setText(s.getString("data"));
                chat_info.addView(view);

                }



            }
        });



    }

    public void send_(){


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 0) {
                    //patient

                    push_to_database(patient_name);
                    Map<String,Object> data = new HashMap<>();

                    data.put("name",consultant_name);
                    myRef.collection("Paitnet_Talked_to").document(patient_name).set(data);

                } else if(flag == 1){

                    // consultant

                    push_to_database(consultant_name);


                } else {

                    Toast.makeText(Chat_.this,"ERROR!",Toast.LENGTH_LONG);
                    Log.d("Chat11",String.valueOf(flag)+"--FLAG");

                }

            }
        });




    }

}