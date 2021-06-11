package com.example.dietconsultant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.Document;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    public Button login;
    public EditText email;
    public EditText password;
    TextView pw;


    private FirebaseAuth myAuth;
    private FirebaseFirestore myRef;

    int flag;
    String user_name = "";
    Consultant_or_patient CoP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.signup);
        email = (EditText) findViewById(R.id.email_login);
        password = (EditText) findViewById(R.id.password_login);
        myAuth = FirebaseAuth.getInstance();
        myRef = FirebaseFirestore.getInstance();
        pw = (TextView) findViewById(R.id.forgot_pw);

        flag = 2;

        CoP = new Consultant_or_patient();
        CoP.set_flag(flag);

        login();
        forgot_pw();
        check_if_admin();


    }


    public void check_if_admin(){
        if(email.getText().toString().trim().equals("admin") && password.getText().toString().trim().equals("admin")){


            Intent i = new Intent(getApplicationContext(),Developers_dashboard.class);
            startActivity(i);
        }

    }


    public void login(){



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){


                } else {


                    //Toast.makeText(Login.this,"Logging in", Toast.LENGTH_LONG).show();
                    myAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("Pulled", "Oo shit");


                                consultant_or_patient();
                                finish();


                            }
                        }
                    });
                }

            }
        });

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
                 Toast.makeText(Login.this,"Welcome Consultant", Toast.LENGTH_SHORT).show();
                Log.d("IDK", "Consultant name:"+ q.getId());
                flag = 1;
                CoP.set_flag(flag);
                CoP.set_name(q.getString("name"));
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
                            Toast.makeText(Login.this,"Welcome Patient", Toast.LENGTH_SHORT).show();

                            flag = 0;
                            Log.d("User_name",String.valueOf(q.getString("name"))+"--UserName");


                            CoP.set_name(String.valueOf(q.getString("name")));
                            user_name = q.getString("name");

                            Log.d("User_name",String.valueOf(CoP.get_name())+"--UserName");

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

        Log.d("IDK", "Universal status:"+ String.valueOf(CoP.get_flag()));


    }

    public void forgot_pw(){

        pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().trim().isEmpty()){

                    Toast.makeText(Login.this,"Enter the email first",Toast.LENGTH_LONG).show();


                }
                else {
                    myAuth.sendPasswordResetEmail(email.getText().toString().trim());
                    Toast.makeText(Login.this,"Check your email for password reset link",Toast.LENGTH_LONG).show();

                }
            }
        });

    }



}