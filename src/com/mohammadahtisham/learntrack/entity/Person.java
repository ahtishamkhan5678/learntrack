package com.mohammadahtisham.learntrack.entity;

public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String email;

    // Default constructor (package-private: only classes in the entity package can use it)
    Person() {

    }

    // Parameterized constructor
    public Person(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // DisplayName
    public String getDisplayName() {
        return firstName + " " + lastName;
    }
}
