package model;

import java.sql.Timestamp;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int id;
    private String post;



    public User(){

    }


    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



//    private int id;
//    private String firstname;
//    private String surname;
//    private String numEmail;
//    private String password;
//    private String dob;
//    private String gender;
//
//    public User(String firstname, String surname, String numEmail, String password, String dob, String gender) {
//        this.firstname = firstname;
//        this.surname = surname;
//        this.numEmail= numEmail;
//        this.password = password;
//        this.dob = dob;
//        this.gender = gender;
//    }
//
//    public User(){
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getFirstname() {
//        return firstname;
//    }
//
//    public void setFirstname(String firstname) {
//        this.firstname = firstname;
//    }
//
//    public String getSurname() {
//        return surname;
//    }
//
//    public void setSurname(String surname) {
//        this.surname = surname;
//    }
//
//    public String getNumEmail() {
//        return numEmail;
//    }
//
//    public void setNumEmail(String numEmail) {
//        this.numEmail = numEmail;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getDob() {
//        return dob;
//    }
//
//    public void setDob(String dob) {
//        this.dob = dob;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
}

