package com.example.dietconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Health_Information extends AppCompatActivity {
    public EditText height;
    public EditText weight;
    public EditText age;
    public EditText calorie;
    public EditText exercise;
    public FirebaseFirestore myRef;
    public FirebaseAuth myAuth;
    public double h;
    public double w;
    public int ag;
    int Kal;
    public double BMI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_information);


            height = (EditText) findViewById(R.id.height_info);
            weight = (EditText) findViewById(R.id.weight_info);
            age = (EditText) findViewById(R.id.age_info);
            calorie = (EditText) findViewById(R.id.calorie_info);
           exercise = (EditText) findViewById(R.id.exercisee_info);
            myRef = FirebaseFirestore.getInstance();
            myAuth = FirebaseAuth.getInstance();
        Done();



    }



    public void BMI_calculator(){

        Kal = Integer.parseInt(calorie.getText().toString().trim());

        h = Double.parseDouble(height.getText().toString().trim());
        w = Double.parseDouble(weight.getText().toString().trim());
        h= h * h ;
        BMI = w/h;

        Log.i("Bmi",String.valueOf(BMI));


    }

    public void algo1(){
        BMI_calculator();
        if (BMI > 24.9) {
            //over weight
            Log.i("BMI","BMI overweight");
            over_weight();



        } else if( BMI < 18.5){

            //under weight
            Log.i("BMI","BMI under weight");


           under_weight();


        } else {

            //dosent make sense
fit_person();
        }

    }

    public void fit_person(){

        push_for_fit("Exercise_routine", Kal, exercise.getText().toString().trim(),"Exercise time");


    }


public void push_for_fit(String doc_name, Integer calorie, String exercise_time, String exceirse_name_or_time ){


        //Pushs database for week 1
        Map<String,Object> data = new HashMap<>();
        data.put("Calorie",String.valueOf(calorie));
        data.put(exceirse_name_or_time,String.valueOf(exercise_time));
        data.put("BMI", String.valueOf(BMI));
        data.put("age",String.valueOf(ag));
        data.put("Height",String.valueOf(h));
        data.put("Weight",String.valueOf(w));
        myRef.collection("Patient")
                .document(Objects.requireNonNull(myAuth.getCurrentUser()).getUid())
                .collection("Health_info").document(doc_name).set(data);





    }


    public void over_weight(){

        ag = Integer.parseInt(age.getText().toString().trim());
        w = Integer.parseInt(weight.getText().toString().trim());


        if(ag>=10 && ag< 15 ){
            Log.i("BMI","BMI  for 10 - 15 yrs weight");
            if(w < 40) {

                //high energy high weight
                Log.i("BMI","BMI high energy for 10 - 15 yrs weight");

                high_enegry_high_weight();

            }else{

                // low energy high weight
                Log.i("BMI","BMI low energy for 10 - 15 yrs weight");
                low_enegry_high_weight();
            }

        }


        if(ag>=15 && ag< 25 ){

            if(w < 55) {

                //high energy high weight
                high_enegry_high_weight();
                Log.i("BMI","BMI 2");
            }else{

                // low energy high weight
                low_enegry_high_weight();
                Log.i("BMI","BMI 2");
            }
        }


        if(ag>=25 && ag< 45 ){

            if(w < 65) {
                Log.i("BMI","Weighing done");
                //high energy high weight
                high_enegry_high_weight();

            }else{

                // low energy high weight
                low_enegry_high_weight();
            }
        }

        if(ag>=45 && ag<= 55 ){

            if(w < 70) {

                //medium energy high weight
                medium_enegry_high_weight();
            }else{

                // low energy high weight
                low_enegry_high_weight();


            }
        }

        if(ag> 55 ){

//not recommended anything, by suggested from basic works
        }
    }


    public void high_enegry_high_weight(){
        int C = Kal;

        //at start exercise intensive, moderate in calorie
        Log.i("BMI","Pushing data High energy for 10 - 15 yrs weight");

        push_databse("Week : 1",C, 30,"exercise time");
        Log.i("BMI","Pushed for once for 10 - 15 yrs weight");
        // exercise intensive, less calorie

        C= (int) (C - (0.2 * C));
        push_databse("Week : 2", C, 30, "exercise time");
        Log.i("BMI","Pushed for twice for 10 - 15 yrs weight");
        //constant


        push_databse("Week : 3", C, time_calroie_equals_exercise(C),"exercise name");
        Log.i("BMI","Pushed for thrice for 10 - 15 yrs weight");



    }


    public void medium_enegry_high_weight(){

        //at start exercise intensive, moderate in calorie
        int C = Kal;

        push_databse("Week : 1",C, 15,"exercise time");
        // exercise intensive, less calorie
        C= (int) (C - (0.2 * C));
        push_databse("Week : 2", C, 15, "exercise time");
        //exercise intensive
        C= (int) (C - (0.3 * C));
        push_databse("Week : 3", C, 30, "exercise time");


        //constant

        push_databse("Week : 3", C, time_calroie_equals_exercise(C),"exercise name");

    }
    public void low_enegry_high_weight(){

        //at start exercise moderate, moderate in calorie
        // exercise intensive, less calorie
        //constant
        //at start exercise intensive, moderate in calorie
      int C = Kal;
        push_databse("Week : 1",C, 15,"exercise time");
        // exercise intensive, less calorie
        C= (int) (C - (0.2 * C));
        push_databse("Week : 2", C, 15, "exercise time");
        //exercise intensive
        C= (int) (C - (0.3 * C));
        push_databse("Week : 3", C, 30, "exercise time");


        //constant

        push_databse("Week : 3", C, time_calroie_equals_exercise(C),"exercise name");


    }


    public void push_databse(String doc_name, Integer calorie, Integer exercise_time, String exceirse_name_or_time){


        //Pushs database for week 1
        Map<String,Object> data = new HashMap<>();
        data.put("Calorie",String.valueOf(calorie));
        data.put(exceirse_name_or_time,String.valueOf(exercise_time));
        data.put("BMI", String.valueOf(BMI));
        data.put("age",String.valueOf(ag));
        data.put("Height",String.valueOf(h));
        data.put("Weight",String.valueOf(w));
        myRef.collection("Patient")
                .document(Objects.requireNonNull(myAuth.getCurrentUser()).getUid())
                .collection("Health_info").document(doc_name).set(data);





    }

    public int time_calroie_equals_exercise(Integer Calorie){

        // returns the number for the type of exercise for the 3rd week

        int[] combination_sum = {86,450,580,290,536,666,376,1030,740,870,1116,826,956,1320,1406};


        for(int i=0;i<= combination_sum.length; i++ ){

            if (combination_sum[i] > Calorie - 50 && combination_sum[i] < Calorie + 50 ){
                return i+1;
            }

        }
//reutrns null if there is not match for calorie
return 0 ;
    }


    public void under_weight(){
        int C = Kal;
        C = (int) (C + (0.1 * C));
        int E = Integer.parseInt(exercise.getText().toString().trim());


        //week 1
        push_underweight("Week 1: ", String.valueOf(C),exercise.getText().toString().trim(),"Time");

        C = (int) (C + (0.2 * C));
        //week 2
        if(E == 0){
            E = 10;
        } else {
        E = (int) (E - (0.05*E));}

        push_underweight("Week 2: ", String.valueOf(C),String.valueOf(E),"Time");

        //week 3 -- more kals -- walk for 30 mins
        push_underweight("Week 2: ", String.valueOf(2300),String.valueOf(30),"Time");


        //week 5 -- which exercise
        E = time_calroie_equals_exercise(C);
        push_underweight("Week 3: ", String.valueOf(C),String.valueOf(E),"Not time");



    }

    public void push_underweight(String doc_name, String calorie, String exercise_time,String exceirse_name_or_time ){

        Map<String,Object> data = new HashMap<>();
        data.put("Calorie",String.valueOf(calorie));
        data.put(exceirse_name_or_time,String.valueOf(exercise_time));
        data.put("BMI", String.valueOf(BMI));
        data.put("age",String.valueOf(ag));
        data.put("Height",String.valueOf(h));
        data.put("Weight",String.valueOf(w));
        myRef.collection("Patient")
                .document(Objects.requireNonNull(myAuth.getCurrentUser()).getUid())
                .collection("Health_info").document(doc_name).set(data);


    }


    public void Done(){
        Button register = (Button)findViewById(R.id.input_health_info);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                algo1();
                Intent intent = new Intent(getApplicationContext(),Personal_dashboard_patients.class);
                startActivity(intent);
            }
        });
    }}