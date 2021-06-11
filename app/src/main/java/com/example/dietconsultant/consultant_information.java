package com.example.dietconsultant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class consultant_information extends AppCompatActivity {


    Button done;
    FirebaseAuth myAuth;
    FirebaseFirestore myRef;
    EditText academics;
    EditText general_information;
    EditText cost;


    Button o_o,o_1,o_2,o_3,o_4,o_5;

    Button one_zero;
    Button one_one;
    Button one_two;
    Button one_three;
    Button one_four;
    Button one_five;



    Button two_zero;
    Button two_one;
    Button two_two;
    Button two_three;
    Button two_four;
    Button two_five;

    Button three_zero;
    Button three_one;
    Button three_two;
    Button three_three;
    Button three_four;
    Button three_five;

    Button four_zero;
    Button four_one;
    Button four_two;
    Button four_three;
    Button four_four;
    Button four_five;


    Button five_zero;
    Button five_one;
    Button five_two;
    Button five_three;
    Button five_four;
    Button five_five;

    Button size_zero;
    Button size_one;
    Button size_two;
    Button size_three;
    Button size_four;
    Button size_five;

    int flags[][];

    String consultant_name;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_information);

        done = (Button) findViewById(R.id.done);
        academics = (EditText) findViewById(R.id.academic_info);
        general_information = (EditText) findViewById(R.id.general_info);

        consultant_name = getIntent().getStringExtra("consultant_name");

        flags = new int[7][6];


       o_o = (Button) findViewById(R.id.o_o);
        o_1= (Button)findViewById (R.id.o_1);
        o_2= (Button)findViewById (R.id.o_2);
        o_3= (Button)findViewById (R.id.o_3);
        o_4= (Button)findViewById (R.id.o_4);
        o_5= (Button)findViewById (R.id.o_5);

         one_zero= (Button)findViewById (R.id.one_zero);
         one_one= (Button)findViewById (R.id.one_one);
         one_two= (Button)findViewById (R.id.one_two);
         one_three= (Button)findViewById (R.id.one_three);
         one_four= (Button)findViewById (R.id.one_four);
         one_five= (Button)findViewById (R.id.one_five);

         two_zero= (Button)findViewById (R.id.two_zero);
         two_one= (Button)findViewById (R.id.two_one);
         two_two= (Button)findViewById (R.id.two_two);
         two_three= (Button)findViewById (R.id.two_three);
         two_four= (Button)findViewById (R.id.two_four);
         two_five= (Button)findViewById (R.id.two_five);

        three_zero= (Button)findViewById (R.id.three_zero);
         three_one= (Button)findViewById (R.id.three_one);
         three_two= (Button)findViewById (R.id.three_two);
         three_three= (Button)findViewById (R.id.three_three);
         three_four= (Button)findViewById (R.id.three_four);
         three_five= (Button)findViewById (R.id.three_five);

         four_zero= (Button)findViewById (R.id.four_zero);
         four_one= (Button)findViewById (R.id.four_one);
         four_two= (Button)findViewById (R.id.four_two);
         four_three= (Button)findViewById (R.id.four_three);
         four_four= (Button)findViewById (R.id.four_four);
         four_five= (Button)findViewById (R.id.four_five);

         five_zero= (Button)findViewById (R.id.five_zero);
         five_one= (Button)findViewById (R.id.five_one);
         five_two= (Button)findViewById (R.id.five_two);
         five_three= (Button)findViewById (R.id.five_three);
         five_four= (Button)findViewById (R.id.five_four);
         five_five= (Button)findViewById (R.id.five_five);

         size_zero= (Button)findViewById (R.id.size_zero);
         size_one= (Button)findViewById (R.id.size_one);
         size_two= (Button)findViewById (R.id.size_two);
         size_three= (Button)findViewById (R.id.size_three);
         size_four= (Button)findViewById (R.id.size_four);
         size_five= (Button)findViewById (R.id.size_five);




         cost = (EditText) findViewById(R.id.cost);




        myAuth = FirebaseAuth.getInstance();
        myRef = FirebaseFirestore.getInstance();


        Done();

        button_color();


    }

    @Override
    protected void onStart() {

        initialize_array();

        super.onStart();
    }

    public void initialize_array(){

        for( int i = 0; i <=6;i++){

            for(int x = 0; x<=5; x++){

                Log.i("ARRAY",String.valueOf(i)+"_"+String.valueOf(x));
                flags[i][x] = 1;

            }
        }

    }


    public void button_color(){
//if buttons are zero then they selected
        //all ones are not selected

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        o_o.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {

                if (flags[0][0] == 0) {

                    flags[0][0] = 1;
                    o_o.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));




                } else if (flags[0][0] == 1) {

                    flags[0][0] = 0;
                    o_o.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));


                }
            }
        });


        o_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flags[0][1] == 0) {
                    o_1.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));
                    flags[0][1] = 1;

                } else if (flags[0][1] == 1) {
                    flags[0][1] = 0;
                    o_1.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));


                }
            }
        });
        o_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flags[0][2] == 0) {
                    o_2.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));
                    flags[0][2] = 1;

                } else if (flags[0][2] == 1) {
                    flags[0][2] = 0;
                    o_2.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));


                }
            }
        });
        o_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flags[0][3] == 0) {
                    flags[0][3] = 1;
                    o_3.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));

                } else if (flags[0][3] == 1) {
                    flags[0][3] = 0;
                    o_3.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));


                }
            }
        });
        o_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flags[0][4] == 0) {
                    flags[0][4] = 1;
                    o_4.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));

                } else if (flags[0][4] == 1) {
                    flags[0][4] = 0;
                    o_4.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));


                }
            }
        });
        o_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flags[0][5] == 0) {
                    o_5.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));
                    flags[0][5] = 1;
                } else if (flags[0][5] == 1) {
                    flags[0][5] = 0;
                    o_5.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));


                }
            }
        });

        one_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flags[1][0] == 0) {
                    flags[1][0] = 1;
                    one_zero.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));

                } else if (flags[1][0] == 1) {
                    flags[1][0] = 0;
                    one_zero.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));


                }
            }
        });


        one_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flags[1][1] == 0) {
                    one_one.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));
                    flags[1][1] = 1;

                } else if (flags[1][1] == 1) {
                    flags[1][1] = 0;

                    one_one.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));


                }
            }
        });
        one_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flags[1][2] == 0) {
                    one_two.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));
                    flags[1][2] = 1;

                } else if (flags[1][2] == 1) {
                    flags[1][2] = 0;

                    one_two.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));


                }
            }
        });
        one_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flags[1][3] == 0) {
                    flags[1][3] = 1;

                    one_three.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));

                } else if (flags[1][3] == 1) {
                    flags[1][3] = 0;

                    one_three.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });
        one_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flags[1][4] == 0) {
                    flags[1][4] = 1;

                    one_four.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));

                } else if (flags[1][4] == 1) {
                    flags[1][4] = 0;
                    one_four.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));


                }
            }
        });
        one_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flags[1][5] == 0) {
                    flags[1][5] = 1;
                    one_five.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));

                } else if (flags[1][5] == 1) {
                    flags[1][5] = 0;
                    one_five.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));


                }
            }
        });



        two_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[2][0] == 0){
                    flags[2][0] = 1;
                    two_zero.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));
                } else if(flags[2][0] == 1){
                    flags[2][0] = 0;

                    two_zero.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));
                }
            }
        });


        two_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[2][1] == 0){
                    flags[2][1] = 1;
                    two_one.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));
                } else if(flags[2][1] == 1){
                    flags[2][1] = 0;
                    two_one.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));
                }
            }
        });
        two_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[2][2] == 0){
                    flags[2][2] = 1;
                    two_two.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));
                } else if(flags[2][2] == 1){
                    flags[2][2] = 0;
                    two_two.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));
                }
            }
        });
        two_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[2][3] == 0){
                    flags[2][3] = 1;

                    two_three.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));
                } else if(flags[2][3] == 1){
                    flags[2][3] = 0;
                    two_three.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));
                }
            }
        });
        two_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[2][4] == 0){
                    flags[2][4] = 1;
                    two_four.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));
                } else if(flags[2][4] == 1){
                    flags[2][4] = 0;
                    two_four.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));
                }
            }
        });
        two_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[2][5] == 0){
                    flags[2][5] = 1;
                    two_five.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));
                } else if(flags[2][5] == 1){
                    flags[2][5] = 0;
                    two_five.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));
                }
            }
        });

        three_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[3][0] == 0){
                    flags[3][0] = 1;
                    three_zero.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));
                } else if(flags[3][0] == 1){
                    flags[3][0] = 0;
                    three_zero.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });


        ////////////////////////////////

        three_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[3][1] == 0){
                    flags[3][1] = 1;
                    three_one.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));

                } else if(flags[3][1] == 1){
                    flags[3][1] = 0;

                    three_one.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });
        three_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[3][2] == 0){
                    flags[3][2] = 1;
                    three_two.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));

                } else if(flags[3][2] == 1){
                    flags[3][2] = 0;
                    three_two.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });
        three_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[3][3] == 0){
                    three_three.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));
                    flags[3][3] = 1;
                } else if(flags[3][3] == 1){
                    three_three.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));
                    flags[3][3] = 0;


                }
            }
        });
        three_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[3][4] == 0){
                    three_four.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));
                    flags[3][4] = 1;

                } else if(flags[3][4] == 1){
                    flags[3][4] = 0;
                    three_four.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });
        three_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[3][5] == 0){
                    three_five.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));
                    flags[3][5] = 1;

                } else if(flags[3][5] == 1){
                    flags[3][5] = 0;
                    three_five.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });
        four_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[4][0] == 0){
                    four_zero.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));
                    flags[4][0] = 1;
                } else if(flags[4][0] == 1){
                    flags[4][0] = 0;
                    four_zero.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });


        four_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[4][1] == 0){
                    four_one.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));
                    flags[4][1] = 1;

                } else if(flags[4][1] == 1){
                    flags[4][1] = 0;
                    four_one.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });
        four_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[4][2] == 0){
                    four_two.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));
                    flags[4][2] = 1;

                } else if(flags[4][2] == 1){
                    flags[4][2] = 0;
                    four_two.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });
        four_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[4][3] == 0){
                    four_three.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));
                    flags[4][3] = 1;
                } else if(flags[4][3] == 1){
                    flags[4][3] = 0;
                    four_three.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });
        four_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[4][4] == 0){
                    flags[4][4] = 1;
                    four_four.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));

                } else if(flags[4][4] == 1){
                    flags[4][4] = 0;
                    four_four.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });
        four_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[4][5] == 0){
                    flags[4][5] = 1;

                    four_five.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));

                } else if(flags[4][5] == 1){
                    flags[4][5] = 0;
                    four_five.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });

        five_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[5][0] == 0){
                    flags[5][0] = 1;

                    five_zero.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));

                } else if(flags[5][0] == 1){
                    flags[5][0] = 0;
                    five_zero.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });


        five_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[5][1] == 0){
                    flags[5][1] = 1;
                    five_one.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));

                } else if(flags[5][1] == 1){
                    flags[5][1] = 0;

                    five_one.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });
        five_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[5][2] == 0){
                    flags[5][2] = 1;

                    five_two.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));

                } else if(flags[5][2] == 1){
                    flags[5][2] = 0;
                    five_two.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });

        five_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[5][3] == 0){
                    flags[5][3] = 1;
                    five_three.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));

                } else if(flags[5][3] == 1){
                    flags[5][3] = 0;

                    five_three.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });
        five_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[5][4] == 0){
                    flags[5][4] = 1;
                    five_four.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));

                } else if(flags[5][4] == 1){
                    flags[5][4] = 0;
                    five_four.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });
        five_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[5][5] == 0){
                    flags[5][5] = 1;
                    five_five.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));

                } else if(flags[5][5] == 1){
                    flags[5][5] = 0;
                    five_five.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });

        size_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[6][0] == 0){
                    size_zero.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));
                    flags[6][0] = 1;

                } else if(flags[6][0] == 1){
                    flags[6][0] = 0;
                    size_zero.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });


        size_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[6][1] == 0){
                    flags[6][1] = 1;
                    size_one.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));

                } else if(flags[6][1] == 1){
                    flags[6][1] = 0;
                    size_one.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });
        size_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[6][2] == 0){
                    flags[6][2] = 1;
                    size_two.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));

                } else if(flags[6][2] == 1){
                    flags[6][2] = 0;
                    size_two.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });
        size_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[6][3] == 0){

                    flags[6][3] = 1;
                    size_three.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));

                } else if(flags[6][3] == 1){
                    flags[6][3] = 0;

                    size_three.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });
        size_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[6][4] == 0){
                    flags[6][4] = 1;
                    size_four.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));

                } else if(flags[6][4] == 1){
                    flags[6][4] = 0;

                    size_four.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });

        size_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flags[6][5] == 0){
                    flags[6][5] = 1;
                    size_five.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.circle));

                } else if(flags[6][5] == 1){
                    flags[6][5] = 0;
                    size_five.setBackground(ContextCompat.getDrawable(consultant_information.this,R.drawable.green_circle));

                }
            }
        });


    }




    }



    public void push_info(){


        try {
            if(Integer.parseInt(cost.getText().toString())< 100){

                Toast.makeText(consultant_information.this,"Too low, either charge for free or increase to atleast 100",Toast.LENGTH_LONG).show();
                cost.setText("");


            } else if (Integer.parseInt(cost.getText().toString()) > 2500){
                Toast.makeText(consultant_information.this,"Cost is too higher, decrease below 2500",Toast.LENGTH_LONG).show();
                cost.setText("");

            }else if( Integer.parseInt(cost.getText().toString())> 99 && Integer.parseInt(cost.getText().toString()) < 25001 ){


                Map<String,Object> data = new HashMap<>();

                data.put("academics",academics.getText().toString().trim());
                data.put("general_information",general_information.getText().toString().trim());
                data.put("cost","NRS/-" + cost.getText().toString().trim());
                for(int i = 0; i <= 6;i++ ){
                    for(int a = 0; a <= 5; a++ ){

                        String na_me = String.valueOf(i) +"_"+String.valueOf(a);
                        data.put(na_me,String.valueOf(flags[i][a]));

                    }
                }

                myRef.collection("Consultant").document(consultant_name)
                        .collection("Consultant_info").document("Consulatnat_info").set(data);

            }
        } catch (Exception e){

            Toast.makeText(consultant_information.this,"Invalid Entry",Toast.LENGTH_LONG).show();

        }


    }




    public void Done(){

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                push_info();
                Intent i = new Intent(getApplicationContext(), Consultant_dashboard.class);
                startActivity(i);
                finish();
            }
        });
    }
}