package com.mohammadahtisham.learntrack.ui;

import com.mohammadahtisham.learntrack.entity.Person;

public class Main {
    public static void main(String[] args) {
        Person p1 = new Person(1, "Ali", "Khan", "ali@test.com");
        System.out.println(p1.getDisplayName());

        Person p2 = new Person();
        System.out.println(p2.getDisplayName());

        p2.setFirstName("Sara");
        p2.setLastName("Ahmed");
        System.out.println(p2.getDisplayName());

    }
}