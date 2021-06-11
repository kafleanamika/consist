package com.example.dietconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class booking_requests_consultant extends AppCompatActivity {

    FirebaseFirestore myRef;
    String consultant,flag;
    Button bt,bt2;
    LinearLayout ll;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_requests_consultant);

        myRef = FirebaseFirestore.getInstance();
        consultant = getIntent().getStringExtra("Consultant");

        ll = (LinearLayout) findViewById(R.id.requets_deck);

        bt = (Button) findViewById(R.id.chats);
        bt2 = (Button) findViewById(R.id.accepted);

        display_bookings();
        changing_layouts();

    }


    public void changing_layouts(){



        bt.setOnClickListener(new View.OnClickListener() {
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

        bt2.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {

                                      Intent i = new Intent(getApplicationContext(),once_meetings_over.class);
                                      i.putExtra("flag",flag);
                                      i.putExtra("Consultant",consultant);
                                      startActivity(i);

                                  }
                              }

        );

    }

    public void display_bookings(){

        myRef.collection("Bookings").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(QueryDocumentSnapshot q: queryDocumentSnapshots){


                    Log.i("flags_", (String) q.get("consultant"));

                    if(q.getString("consultant").equals(consultant)  ) {


                            if (q.getString("Consultant_status").equals("requests")) {

                                //check if the time is still there


                                //if not then

                                TextView tt = new TextView(booking_requests_consultant.this);
                                Button bt = new Button(booking_requests_consultant.this);
                                Button bt2 = new Button(booking_requests_consultant.this);

                                String name = (String) q.getString("patient");
                                String time = (String) q.getString("Time");

                                tt.setTextSize(18);
                                tt.setPadding(20, 20, 20, 20);
                                tt.setGravity(Gravity.CENTER);
                                tt.setText("Name : " + name + "\n" + "Time :" + time);

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    tt.setBackground(getDrawable(R.drawable.custon_listing_small_card));
                                }


                                bt.setText("ACCEPT");
                                bt2.setText("DENY");

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    bt.setTextColor(getColor(R.color.foreground));
                                    bt2.setTextColor(getColor(R.color.foreground));
                                    tt.setTextColor(getColor(R.color.white));

                                }
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    bt.setBackground(getDrawable(R.drawable.custom_));
                                    bt2.setBackground(getDrawable(R.drawable.custom_));

                                }


                                LinearLayout l = new LinearLayout(booking_requests_consultant.this);
                                LinearLayout l2 = new LinearLayout(booking_requests_consultant.this);

                                l.setWeightSum(1);
                                l.setOrientation(LinearLayout.HORIZONTAL);
                                l2.setOrientation(LinearLayout.VERTICAL);

                                LinearLayout.LayoutParams LP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                                LP.setMargins(10, 10, 0, 20);

                                l.setLayoutParams(LP);
                                l.addView(bt);
                                l.addView(bt2);
                                l2.addView(tt);
                                ll.addView(l2);
                                ll.addView(l);


                                bt.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Map<String, Object> data = new HashMap<>();
                                        data.put("Consultant_status", "Yes");

                                        myRef.collection("Bookings").document(q.getId()).update(data);
                                        l.removeAllViews();
                                        l2.removeAllViews();

                                    }
                                });

                                bt2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {


                                        Map<String, Object> data = new HashMap<>();
                                        data.put("Consultant_status", "No");

                                        myRef.collection("Bookings").document(q.getId()).update(data);
                                        l.removeAllViews();
                                        l2.removeAllViews();

                                    }
                                });


                            } else {

                              //  Toast.makeText(booking_requests_consultant.this,"Matched but 2nd idk",Toast.LENGTH_LONG).show();

                            }

                    }
                    else {

                       // Toast.makeText(booking_requests_consultant.this,"None matched",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });


    }
}