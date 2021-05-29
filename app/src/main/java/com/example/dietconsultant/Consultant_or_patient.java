package com.example.dietconsultant;

public class Consultant_or_patient {

    //0 is patien
    //1 is consultant
    //2 is nobody

    int F = 2;
    String name;
    public Consultant_or_patient(){

    }

    public int get_flag(){

        return F;
    }
    public String get_name(){

        return name;
    }
    public void set_flag(int value){
        F = value;
    }


    public void set_name(String value){
        name = value;
    }

}
