package com.example.dietconsultant;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class otp_act extends AppCompatActivity {

    OTP otp;
    String E;
    String P;
    String Ph;
    String N;

    int flag;
    int OTP_value;
    int max = 40000;
    int min = 3000;
    Button confirm;

    EditText otp_get;

    FirebaseFirestore myRef;
    FirebaseAuth mAuth;

    String email;
    String my_email = "Kafleanamika01@gmail.com";
    String password = "Asdfgh123@";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp2);

        E = getIntent().getStringExtra("email");
        P = getIntent().getStringExtra("password");
        Ph = getIntent().getStringExtra("ph");
        N = getIntent().getStringExtra("name");



        flag = Integer.parseInt(getIntent().getStringExtra("flag"));

        otp_get = (EditText) findViewById(R.id.OTP_input);

        OTP_value = (int)Math.floor(Math.random()*(max-min+1)+min);

        Log.i("Otp",String.valueOf(OTP_value));
                mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseFirestore.getInstance();


       // otp = new OTP(E, String.valueOf(OTP_value));
       // otp.send_OTP();


        Properties p = new Properties();
        p.put("mail.smtp.auth","true");
        p.put("mail.smtp.starttls.enable","true");
        p.put("mail.smtp.host","smtp.gmail.com");
        p.put("mail.smtp.port","587");


        StrictMode.ThreadPolicy pol = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(pol);

        Session s = Session.getInstance(p,

                new javax.mail.Authenticator() {

                    protected PasswordAuthentication getPWA(){

                        return  new PasswordAuthentication("kafleanamika01@gmail.com","Anamika123");
                    }
                });




        try {
            Message m = new MimeMessage(s);
            m.setFrom(new InternetAddress(my_email));
            m.setRecipients(Message.RecipientType.TO, InternetAddress.parse(E));
            m.setSubject("OTP FOR CONSULTANT DIET");
            m.setText(String.valueOf(OTP_value));
            Transport.send(m,"kafleanamika01@gmail.com","Anamika123");
            Toast.makeText(this," Sent otp",Toast.LENGTH_LONG).show();


        } catch (MessagingException e) {
            e.printStackTrace();
            Log.i("error", String.valueOf(e));
            Toast.makeText(this,"Couldnt send otp",Toast.LENGTH_LONG).show();

        }



        confirm ();
    }






    public void confirm (){

        confirm = (Button) findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (otp_get.getText().toString().trim().equals(String.valueOf(OTP_value))){

                    try {
                        changes();
                    }
                    catch (Exception e){
                        //Toast.makeText(otp_act.this,"Email already in use!", Toast.LENGTH_LONG).show();
                        Toast.makeText(otp_act.this,String.valueOf(flag), Toast.LENGTH_LONG).show();


                    }


                }
                else {

                    Toast.makeText(otp_act.this,"OTP DID NOT MATCH! RETRY", Toast.LENGTH_LONG).show();
                }

            }
        });



    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void changes(){

        if(flag==0 ) {


            mAuth.createUserWithEmailAndPassword(E, P)
                    .addOnCompleteListener(otp_act.this, new OnCompleteListener<AuthResult>() {
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
                    .addOnCompleteListener(otp_act.this, new OnCompleteListener<AuthResult>() {
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
                                intent.putExtra("consultant_name",N);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("flag1", "createUserWithEmail:failure", task.getException());

                            }
                        }
                    });


        }

        isDestroyed();
    }
}