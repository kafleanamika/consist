package com.example.dietconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class payment_gateway extends AppCompatActivity {

    FirebaseFirestore mref;

    Intent i;
    String flag;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

        mref = FirebaseFirestore.getInstance();

        flag = getIntent().getStringExtra("Flag");
        name = getIntent().getStringExtra("paitient");

        Button bt = (Button) findViewById(R.id.check);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_if_paid();
                check_if_accepted();


            }
        });
    }



public void check_if_paid(){




        mref.collection("Bookings").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(QueryDocumentSnapshot q: queryDocumentSnapshots) {





                    if (q.get("patient").equals(name)) {

                        String time = " For : "+ q.get("Time").toString();

                        if (q.getString("Payment_status") == null) {

                            //not paid
                            Toast.makeText(payment_gateway.this, time+"Pending for confirmation", Toast.LENGTH_LONG).show();


                        } else if (q.getString("Payment_status").equals("Yes") || q.getString("Consultant_status").equals("Yes")) {

                            //paid

                            i = new Intent(getApplicationContext(),patient_chat_list.class);
                            i.putExtra("status", q.getString("Payment_status"));
                            i.putExtra("acceptance_status", q.getString("Consultant_status"));
                            i.putExtra("consultant", q.getString("consultant"));
                            i.putExtra("User_name", name);
                            i.putExtra("Flag", flag);
                            Toast.makeText(payment_gateway.this, time+"Paid Congratulations and booked", Toast.LENGTH_LONG).show();
                            startActivity(i);


                        } else if (q.getString("Payment_status").equals("no") || q.getString("Payment_status").equals("No")) {
                            //delete

                           // Toast.makeText(payment_gateway.this, time+"Not Paid", Toast.LENGTH_LONG).show();


                        } else if (q.getString("Payment_status").equals("Yes") || q.getString("Consultant_status").equals("No")) {

                           // Toast.makeText(payment_gateway.this, time+"Sorry your booking was denied", Toast.LENGTH_LONG).show();


                        } else if (q.getString("Payment_status").equals("Yes") || q.getString("Consultant_status").equals("")) {

                           // Toast.makeText(payment_gateway.this, time+"You have paid but consultant is yet to confirm. Pleas wait!", Toast.LENGTH_LONG).show();


                        }


                    }

                }
                }



        });

}


    public void check_if_accepted(){

        mref.collection("Bookings").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(QueryDocumentSnapshot q: queryDocumentSnapshots){





                }


            }
        });

    }


}