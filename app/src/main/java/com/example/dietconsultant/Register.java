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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore myRef;
    int flag ;
    Button register;
    EditText name;

    int OTP;

    int f = 0;

    OTP otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         register = (Button)findViewById(R.id.register);


        register(consultant_or());
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseFirestore.getInstance();

        name = (EditText)findViewById(R.id.input_name);

    }

    public int consultant_or(){


        flag = 100 ;



        Button patient  = (Button)findViewById(R.id.Patient);
        Button consultant = (Button)findViewById(R.id.Consultant);

        //Patient has different register than of consultant
        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int val = check_if_name_exists("Patient_list",name.getText().toString().trim());
               if( val  == 0) {
                   flag = 0;
                   Log.d("flag1", String.valueOf(flag));

               }


            }
        });
        consultant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int val = check_if_name_exists("Patient_list",name.getText().toString().trim());

                if (val == 0) {

                    flag = 1;
                    Log.d("flag1", String.valueOf(flag));

                }
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
                finish();

            }
        });


    }




    public void getData(){

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




Intent i = new Intent(getApplicationContext(),otp_act.class);
        i.putExtra("email",E);
        i.putExtra("password",P);
        i.putExtra("ph",Ph);
        i.putExtra("name",N);
        i.putExtra("flag",String.valueOf(flag));


        startActivity(i);




        return;


    }



    public int check_if_name_exists(String p_c, String nam){



        myRef.collection(p_c).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(QueryDocumentSnapshot q: queryDocumentSnapshots) {

                    String n = q.getString("name ");

                    if (!(n == null)) {


                        if (n.equals(nam)) {

                            name.setText("");
                            Toast.makeText(Register.this, "Username already exists.", Toast.LENGTH_LONG).show();
                            f = 1;

                        }

                    }
                }
            }
        });


        return f ;

    }
}