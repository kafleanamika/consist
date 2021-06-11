package com.example.dietconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class once_meetings_over extends AppCompatActivity {
    Button bt,bt2,bt3;
    FirebaseFirestore myRef;
    String consultant,flag;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_once_meetings_over);

        myRef = FirebaseFirestore.getInstance();
        consultant = getIntent().getStringExtra("Consultant");
        ll = (LinearLayout) findViewById(R.id.cancel_deck);

        bt = (Button) findViewById(R.id.o_requests);
        bt2 = (Button) findViewById(R.id.o_chats);

        changing_layouts();
        end();

    }

    public void changing_layouts(){



        bt2.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {

                                       Intent i = new Intent(getApplicationContext(),Consultant_dashboard.class);
                                       i.putExtra("User_name",consultant);
                                       flag = getIntent().getStringExtra("flag");
                                       i.putExtra("Flag",flag);
                                       startActivity(i);

                                   }
                               }

        );

        bt.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {

                                      Intent i = new Intent(getApplicationContext(),booking_requests_consultant.class);
                                      i.putExtra("flag",flag);
                                      i.putExtra("Consultant",consultant);
                                      startActivity(i);

                                  }
                              }

        );

    }


    public void end(){


        myRef.collection("Bookings").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                for(QueryDocumentSnapshot q: queryDocumentSnapshots) {

                    if (q.getString("consultant").equals(consultant)) {

                        if (q.getString("Consultant_status").equals("Yes") &&q.getString("Payment_status").equals("Yes") ) {

                            LinearLayout l = new LinearLayout(once_meetings_over.this);
                            l.setOrientation(LinearLayout.VERTICAL);

                            Button b = new Button(once_meetings_over.this);
                            b.setText("Report after the end of session");

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                b.setBackground(getDrawable(R.drawable.custom_));
                            }

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                b.setTextColor(getColor(R.color.foreground));
                            }


                            TextView t = new TextView(once_meetings_over.this);
                            t.setText("Confirmed Booking" + "\n" + "Name - " + q.getString("patient") + "\n" + "Time - " + q.getString("Time"));
                            t.setPadding(20, 20, 20, 20);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                t.setBackground(getDrawable(R.drawable.custon_listing_small_card));
                            }

                            LinearLayout.LayoutParams LP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                            LP.setMargins(10, 10, 0, 20);

                            l.setLayoutParams(LP);
                            l.addView(t);
                            l.addView(b);
                            ll.addView(l);

                            b.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    myRef.collection("Bookings").document(q.getId()).delete();
                                    l.removeView(b);

                                }
                            });


                        }

                    }
                }
                }
        });
    }
}