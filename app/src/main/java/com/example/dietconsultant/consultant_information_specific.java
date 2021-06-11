package com.example.dietconsultant;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

public class consultant_information_specific extends AppCompatActivity {



    TextView name;
    LinearLayout desc,g_desc;
    Button book;
    LinearLayout ll;
    LinearLayout v1;
    LinearLayout v2;
    LinearLayout v3;
    LinearLayout v4;
    LinearLayout v5;
    LinearLayout v6;


    FirebaseFirestore mRef;

    String consultant;
    String academics;
    String general;
    String patient;

    TextView cost;

    String flag;


    int[][] flags;
    int[] booked_timings_row;
    int[] booked_timings_col;

    int[] booked_timings;

    int[] buttons_state;

    String[] timings;

    int timing_conunter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_information_specific);

        name = (TextView) findViewById(R.id.consultant_name);
        desc = (LinearLayout) findViewById(R.id.information_space);
        g_desc = (LinearLayout) findViewById(R.id.information_space_general);
        book = (Button) findViewById(R.id.book);
        ll = (LinearLayout) findViewById(R.id.time_option);

        v1 =(LinearLayout) findViewById(R.id.v1);
        v2 =(LinearLayout) findViewById(R.id.v2);
        v3 =(LinearLayout) findViewById(R.id.v3);
        v4 =(LinearLayout) findViewById(R.id.v4);
        v5 =(LinearLayout) findViewById(R.id.v5);
        v6 =(LinearLayout) findViewById(R.id.v6);

        booked_timings = new int[42];
        buttons_state = new int[42];

        cost = (TextView) findViewById(R.id.cost_ref) ;

        flag = getIntent().getStringExtra("Flag");

        timings = new String[]{"8-10 S",
                "8-10 M",
                "8-10 T",
                "8-10 W",
                "8-10 T",
                "8-10 F",
                "8-10 ST",
                "10-12 S",
                "10-12 M",
                "10-12 T",
                "10-12 W",
                "10-12 T",
                "10-12 F",
                "10-12 ST",
                "12-2 S",
                "12-2 M",
                "12-2 T",
                "12-2 W",
                "12-2 T",
                "12-2 F",
                "12-2 ST",
                "2-4 S",
                "2-4 M",
                "2-4 T",
                "2-4 W",
                "2-4 T",
                "2-4 F",
                "2-4 ST",
                "4-6 S",
                "4-6 M",
                "4-6 T",
                "4-6 W",
                "4-6 T",
                "4-6 F",
                "4-6 ST",
                "6-7 S",
                "6-7 M",
                "6-7 T",
                "6-7 W",
                "6-7 T",
                "6-7 F",
                "6-7 ST"};

        mRef = FirebaseFirestore.getInstance();

        consultant = getIntent().getStringExtra("Consultant");
        patient = getIntent().getStringExtra("Patient");

        Log.i("flags_intent",consultant);

        flags = new int[7][6];
        booked_timings_col = new int[5];
        booked_timings_row = new int[5];
        iitialize_array();


        get_info(consultant);

        book_();

        try{
            check_if_for_check();


        } catch (Exception e){


        }

    }


    public void check_if_for_check(){


        mRef.collection("Bookings").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(QueryDocumentSnapshot q: queryDocumentSnapshots){

                    String s = q.get("Payment_status").toString();
                    if(s == null && q.get("patient").equals(patient) && q.get("finished").equals("no")){

                        Intent i = new Intent(getApplicationContext(),payment_gateway.class);
                        i.putExtra("paitient",patient);
                        i.putExtra("cost", (String) q.get("Consultant_cost"));
                        i.putExtra("Flag", flag);
                        startActivity(i);

                    } else if(q.get("Payment_status").equals("Yes") && q.get("patient").equals(patient) && q.get("finished").equals("no")){

                        Intent i = new Intent(getApplicationContext(),payment_gateway.class);
                        i.putExtra("paitient",patient);
                        i.putExtra("cost", (String) q.get("Consultant_cost"));
                        i.putExtra("Flag", flag);
                        startActivity(i);

                    }
                    Log.i("FFF",q.get("Payment_status").toString());
                }
            }
        });


    }

public void set_info(){



        name.setText(consultant);

        TextView tt = new TextView(this);
        TextView tt2 = new TextView(this);

        tt.setText(general);
        tt.setPadding(10,10,10,10);
        tt.setTextColor(Color.WHITE);


        tt2.setText(academics);
    tt2.setPadding(10,10,10,10);
    tt2.setTextColor(Color.WHITE);




    desc.addView(tt);
    g_desc.addView(tt2);




}

    public void iitialize_array(){
        for( int x = 0; x <= 6; x++){

            for(int y =0; y<=5; y++){


               flags[x] [y] = 0;

            }

        }


        for (int x = 0; x <= 41; x++) {

            booked_timings[x] = 1;


            // initial state
            buttons_state[x] = 0 ;
        }
    }



    public void get_info(String consul){



        mRef.collection("Consultant").document(consultant)
                .collection("Consultant_info").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(QueryDocumentSnapshot q: queryDocumentSnapshots){
                    academics = q.getString("academics");
                    general = q.getString("general_information");

                    cost.setText(q.getString("cost"));


                    for( int x = 0; x <= 6; x++){

                        for(int y =0; y<=5; y++){

                            String ad = String.valueOf(x)+"_"+String.valueOf(y);


                            flags[x][y] = Integer.parseInt(q.getString(ad));

                            Log.i("flags_value",String.valueOf(x)+"_"+String.valueOf(y));


                        }

                    }

                }

                set_info();
                set_time_table();


            }
        });



    }

    int counter_timings= 0;

    @SuppressLint("UseCompatLoadingForDrawables")
    public void set_time_table(){







            for(int y =0; y<=6; y++){




                Button bt = new Button(this);
                TextView tv = new TextView(this);

                bt.setText(String.valueOf(counter_timings));
                //tv.setPadding(2,2,2,2);


                if(flags[y][0] == 0){

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        bt.setBackground(getDrawable(R.drawable.circle));

                        //added
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            bt.setTextColor(getColor(R.color.foreground));
                        }
                    }

                    int finalY = y;
                    bt.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(View view) {


                            //specific time for booking

                            if(timing_conunter <+5){


                                //figure out how to get the timing based on the button click

                                Log.i("flags_",String.valueOf(booked_timings_col[0]+" "+String.valueOf(booked_timings_row[0])));


                                timing_conunter = timing_conunter + 1;


                                if(buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] == 0){

                                    bt.setBackground(getDrawable(R.drawable.green_circle));
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        bt.setTextColor(getColor(R.color.green));

                                        buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] = 1;
                                        booked_timings[Integer.parseInt(String.valueOf(bt.getText()))] = 0;



                                    }

                                } else  if(buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] == 1){

                                    bt.setBackground(getDrawable(R.drawable.circle));
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        bt.setTextColor(getColor(R.color.foreground));
                                        buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] = 0;
                                        booked_timings[Integer.parseInt(String.valueOf(bt.getText()))] = 1;
                                        timing_conunter = timing_conunter - 1;

                                    } }



                            } else {
                                String s = "You have already reached your maximum limit";
                                Toast.makeText(consultant_information_specific.this,s,Toast.LENGTH_LONG).show();
                            }

                            //they can choose multiple bookings, the first that gets accepted will be sent


                        }
                    });

                } else{

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        bt.setBackground(getDrawable(R.drawable.black_circle));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            bt.setTextColor(getColor(R.color.black));
                        }
                        bt.setPadding(5,20,5,5);
                    }
                }


                v1.addView(bt);

                if(y == 6 ){

                } else {
                    v1.addView(tv);
                }

                counter_timings = counter_timings + 1;

            }






        for(int y =0; y<=6; y++){


            Button bt = new Button(this);
            TextView tv = new TextView(this);
            bt.setText(String.valueOf(counter_timings));
            //tv.setPadding(2,2,2,2);


            if(flags[y][1] == 0){

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bt.setBackground(getDrawable(R.drawable.circle));
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    bt.setTextColor(getColor(R.color.foreground));
                }

                int finalY = y;
                bt.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View view) {

                        //specific time for booking

                        if(timing_conunter <+5){

                            if(buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] == 0){

                                bt.setBackground(getDrawable(R.drawable.green_circle));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    bt.setTextColor(getColor(R.color.green));

                                    buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] = 1;
                                    booked_timings[Integer.parseInt(String.valueOf(bt.getText()))] = 0;



                                }

                            } else  if(buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] == 1){

                                bt.setBackground(getDrawable(R.drawable.circle));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    bt.setTextColor(getColor(R.color.foreground));
                                    buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] = 0;
                                    booked_timings[Integer.parseInt(String.valueOf(bt.getText()))] = 1;
                                    timing_conunter = timing_conunter - 1;

                                } }


                        } else {
                            String s = "You have already reached your maximum limit";
                            Toast.makeText(consultant_information_specific.this,s,Toast.LENGTH_LONG).show();
                        }

                        //they can choose multiple bookings, the first that gets accepted will be sent


                    }
                });

            } else{

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bt.setBackground(getDrawable(R.drawable.black_circle));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        bt.setTextColor(getColor(R.color.black));
                    }
                    bt.setPadding(5,20,5,5);
                }
            }


            v2.addView(bt);

            if(y == 6 ){

            } else {
                v2.addView(tv);
            }
            counter_timings = counter_timings + 1;


        }


        for(int y =0; y<=6; y++){


            Button bt = new Button(this);
            TextView tv = new TextView(this);
            bt.setText(String.valueOf(counter_timings));
            //tv.setPadding(2,2,2,2);


            if(flags[y][2] == 0){

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bt.setBackground(getDrawable(R.drawable.circle));
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    bt.setTextColor(getColor(R.color.foreground));
                }
                int finalY = y;
                bt.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View view) {

                        //specific time for booking

                        if(timing_conunter <+5){

                            if(buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] == 0){

                                bt.setBackground(getDrawable(R.drawable.green_circle));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    bt.setTextColor(getColor(R.color.green));

                                    buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] = 1;
                                    booked_timings[Integer.parseInt(String.valueOf(bt.getText()))] = 0;



                                }

                            } else  if(buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] == 1){

                                bt.setBackground(getDrawable(R.drawable.circle));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    bt.setTextColor(getColor(R.color.foreground));
                                    buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] = 0;
                                    booked_timings[Integer.parseInt(String.valueOf(bt.getText()))] = 1;
                                    timing_conunter = timing_conunter - 1;

                                } }

                        } else {
                            String s = "You have already reached your maximum limit";
                            Toast.makeText(consultant_information_specific.this,s,Toast.LENGTH_LONG).show();
                        }

                        //they can choose multiple bookings, the first that gets accepted will be sent


                    }
                });

            } else{

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bt.setBackground(getDrawable(R.drawable.black_circle));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        bt.setTextColor(getColor(R.color.black));
                    }
                    bt.setPadding(5,20,5,5);
                }
            }


            v3.addView(bt);

            if(y == 6 ){

            } else {
                v3.addView(tv);
            }
            counter_timings = counter_timings + 1;


        }



        for(int y =0; y<=6; y++){


            Button bt = new Button(this);
            TextView tv = new TextView(this);
            bt.setText(String.valueOf(counter_timings));
            //tv.setPadding(2,2,2,2);


            if(flags[y][3] == 0){

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bt.setBackground(getDrawable(R.drawable.circle));
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    bt.setTextColor(getColor(R.color.foreground));
                }
                int finalY = y;
                bt.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View view) {

                        //specific time for booking

                        if(timing_conunter <+5){

                            if(buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] == 0){

                                bt.setBackground(getDrawable(R.drawable.green_circle));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    bt.setTextColor(getColor(R.color.green));

                                    buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] = 1;
                                    booked_timings[Integer.parseInt(String.valueOf(bt.getText()))] = 0;



                                }

                            } else  if(buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] == 1){

                                bt.setBackground(getDrawable(R.drawable.circle));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    bt.setTextColor(getColor(R.color.foreground));
                                    buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] = 0;
                                    booked_timings[Integer.parseInt(String.valueOf(bt.getText()))] = 1;
                                    timing_conunter = timing_conunter - 1;

                                } }

                        } else {
                            String s = "You have already reached your maximum limit";
                            Toast.makeText(consultant_information_specific.this,s,Toast.LENGTH_LONG).show();
                        }

                        //they can choose multiple bookings, the first that gets accepted will be sent


                    }
                });

            } else{

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bt.setBackground(getDrawable(R.drawable.black_circle));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        bt.setTextColor(getColor(R.color.black));
                    }
                    bt.setPadding(5,20,5,5);
                }
            }


            v4.addView(bt);

            if(y == 6 ){

            } else {
                v4.addView(tv);
            }

            counter_timings = counter_timings + 1;

        }




        for(int y =0; y<=6; y++){


            Button bt = new Button(this);
            TextView tv = new TextView(this);
            bt.setText(String.valueOf(counter_timings));
            //tv.setPadding(2,2,2,2);


            if(flags[y][4] == 0){

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bt.setBackground(getDrawable(R.drawable.circle));
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    bt.setTextColor(getColor(R.color.foreground));
                }
                int finalY = y;
                bt.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View view) {

                        //specific time for booking

                        if(timing_conunter <+5){

                            if(buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] == 0){

                                bt.setBackground(getDrawable(R.drawable.green_circle));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    bt.setTextColor(getColor(R.color.green));

                                    buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] = 1;
                                    booked_timings[Integer.parseInt(String.valueOf(bt.getText()))] = 0;



                                }

                            } else  if(buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] == 1){

                                bt.setBackground(getDrawable(R.drawable.circle));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    bt.setTextColor(getColor(R.color.foreground));
                                    buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] = 0;
                                    booked_timings[Integer.parseInt(String.valueOf(bt.getText()))] = 1;
                                    timing_conunter = timing_conunter - 1;

                                } }

                        } else {
                            String s = "You have already reached your maximum limit";
                            Toast.makeText(consultant_information_specific.this,s,Toast.LENGTH_LONG).show();
                        }

                        //they can choose multiple bookings, the first that gets accepted will be sent


                    }
                });

            } else{

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bt.setBackground(getDrawable(R.drawable.black_circle));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        bt.setTextColor(getColor(R.color.black));
                    }
                    bt.setPadding(5,20,5,5);
                }
            }


            v5.addView(bt);

            if(y == 6 ){

            } else {
                v5.addView(tv);
            }

            counter_timings = counter_timings + 1;

        }




        for(int y =0; y<=6; y++){


            Button bt = new Button(this);
            TextView tv = new TextView(this);
            bt.setText(String.valueOf(counter_timings));

            //tv.setPadding(2,2,2,2);


            if(flags[y][5] == 0){

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bt.setBackground(getDrawable(R.drawable.circle));
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    bt.setTextColor(getColor(R.color.foreground));
                }

                int finalY = y;
                bt.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View view) {

                        //specific time for booking

                        if(timing_conunter <+5){

                            if(buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] == 0){

                                bt.setBackground(getDrawable(R.drawable.green_circle));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    bt.setTextColor(getColor(R.color.green));

                                    buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] = 1;
                                    booked_timings[Integer.parseInt(String.valueOf(bt.getText()))] = 0;



                                }

                            } else  if(buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] == 1){

                                bt.setBackground(getDrawable(R.drawable.circle));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    bt.setTextColor(getColor(R.color.foreground));
                                    buttons_state[Integer.parseInt(String.valueOf(bt.getText()))] = 0;
                                    booked_timings[Integer.parseInt(String.valueOf(bt.getText()))] = 1;
                                    timing_conunter = timing_conunter - 1;

                                } }

                        } else {
                            String s = "You have already reached your maximum limit";
                            Toast.makeText(consultant_information_specific.this,s,Toast.LENGTH_LONG).show();
                        }

                        //they can choose multiple bookings, the first that gets accepted will be sent


                    }
                });

            } else{

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bt.setBackground(getDrawable(R.drawable.black_circle));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        bt.setTextColor(getColor(R.color.black));
                    }
                    bt.setPadding(5,20,5,5);
                }
            }


            v6.addView(bt);

            if(y == 6 ){

            } else {
                v6.addView(tv);
            }
            counter_timings = counter_timings + 1;


        }









    }





    public void book_(){

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //goes to payment place
                check_timings_push();
                String c = (String) cost.getText();


                Intent i = new Intent(getApplicationContext(),payment_gateway.class);
                i.putExtra("paitient",patient);
                i.putExtra("cost", c);
                i.putExtra("Flag", flag);

                startActivity(i);





            }
        });


    }


    public void check_timings_push(){

        for(int x = 0; x <= 41 ; x++){

            if(booked_timings[x] == 0){
                push_booking(timings[x],"","requests");

            }

        }

    }


    public void push_booking(String time, String Payment_status, String Consultant_status ){


        Map<String,Object> data = new HashMap<>();
        data.put("patient",patient);
        data.put("consultant",consultant);
        data.put("Time",time);
        data.put("Payment_status",Payment_status);
        data.put("Consultant_status",Consultant_status);
        data.put("Consultant_cost",(String) cost.getText());
        data.put("finished","no");




        mRef.collection("Bookings").add(data);
    }




}