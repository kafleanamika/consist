package com.example.dietconsultant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore myRef;
    int flag ;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         register = (Button)findViewById(R.id.register);

        register(consultant_or());
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseFirestore.getInstance();
    }

    public int consultant_or(){


        flag = 100 ;



        Button patient  = (Button)findViewById(R.id.Patient);
        Button consultant = (Button)findViewById(R.id.Consultant);

        //Patient has different register than of consultant
        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 0;
                Log.d("flag1", String.valueOf(flag));

            }
        });
        consultant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 1;
             Log.d("flag1", String.valueOf(flag));

            }
        });

        return flag;

//chioce is consultant or not





        }

public void register(int flag) {


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //System.out.print(flag);
                getData();

            }
        });


    }




    public void getData(){
        EditText name = (EditText)findViewById(R.id.input_name);
        EditText email = (EditText)findViewById(R.id.input_email);
        EditText password = (EditText)findViewById(R.id.input_password);
        EditText re_password = (EditText)findViewById(R.id.input_passwordre);
        EditText phone = (EditText)findViewById(R.id.phone_number_input);

        String N = name.getText().toString().trim();
        String E = email.getText().toString().trim();
        String P = password.getText().toString().trim();
        String Rp = re_password.getText().toString().trim();
        String Ph = phone.getText().toString().trim();
        Log.d("flag1", String.valueOf("OK"));

        //empty check
        if(N.isEmpty()){
            name.setError("Enter Valid text!");
            name.requestFocus();
            return;

        }
        if(E.isEmpty()){
            email.setError("Enter Valid text!");
            email.requestFocus();
            return;

        }
        if(P.isEmpty()){
            password.setError("Enter Valid text!");
            password.requestFocus();
            return;

        }
        if(Rp.isEmpty()){
            re_password.setError("Enter Valid text!");
            re_password.requestFocus();
            return;

        }
        if(Ph.isEmpty()){
            phone.setError("Enter Valid text!");
            phone.requestFocus();
            return;

        }
        //Validity of email
        if(!Patterns.EMAIL_ADDRESS.matcher(E).matches()){

            email.setError("Enter Valid text!");
            email.requestFocus();
            email.setText("");
            return;

        }

        //Validity of password
        if(!P.equals(Rp)){
            password.setError("Paasword did not match!");
            password.requestFocus();
            password.setText("");
            return;
        }
        if(P.length() < 7 ){
            password.setError("Password too short!");
            password.requestFocus();
            password.setText("");
            return;
        }
        //Validity of phone number
        if(Ph.length() != 10){
            phone.setError("Password too short!");
            phone.requestFocus();
            phone.setText("");
            return;

        }



        if(flag==0 ) {


            mAuth.createUserWithEmailAndPassword(E, P)
                    .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, send data to firestore
                                Map<String,Object> patient_info = new HashMap<>();
                                patient_info.put("Email",E);
                                patient_info.put("Name",N);
                                patient_info.put("Phone",Ph);

                                myRef.collection("Patient").document(mAuth.getCurrentUser().getUid()).set(patient_info);

                                Map<String,Object> name = new HashMap<>();

                                name.put("name",N);
                                myRef.collection("Patient_list").document(mAuth.getCurrentUser().getUid()).set(name);


                                Intent intent = new Intent(getApplicationContext(), Health_Information.class);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("flag1", "createUserWithEmail:failure", task.getException());

                            }
                        }
                    });



        } else if (flag ==1){

            mAuth.createUserWithEmailAndPassword(E, P)
                    .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, send data to firestore
                                Map<String,Object> consultant_info = new HashMap<>();
                                consultant_info.put("Email",E);
                                consultant_info.put("Name",N);
                                consultant_info.put("Phone",Ph);
                                Map<String,Object> name = new HashMap<>();

                                name.put("name".toString(),N);


                                myRef.collection("Consultant").document(mAuth.getCurrentUser().getUid()).set(consultant_info);
                                myRef.collection("Consultant_list").document(mAuth.getCurrentUser().getUid()).set(name);


                                Intent intent = new Intent(getApplicationContext(), consultant_information.class);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("flag1", "createUserWithEmail:failure", task.getException());

                            }
                        }
                    });


        }

        return;


    }
}