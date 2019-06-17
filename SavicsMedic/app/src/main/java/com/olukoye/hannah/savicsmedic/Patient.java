package com.olukoye.hannah.savicsmedic;

public class Patient {

    int counter;
    String fullname;
    String email;
    int age;
    String gender;


    Patient(String fullname, String email, int age, String gender, int counter) {
        this.fullname = fullname;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.counter = counter;
    }
}
