package com.example.netflixproject.modals;

import java.util.Date;

public class Users
{
    String firstname,lastname,mobilenumber,plancost,email;
    Date valid_date;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Users(String firstname, String lastname, String email, String mobilenumber, String plancost, Date valid_date) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.mobilenumber = mobilenumber;
        this.plancost = plancost;
        this.valid_date = valid_date;
        this.email=email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getPlancost() {
        return plancost;
    }

    public void setPlancost(String plancost) {
        this.plancost = plancost;
    }

    public Date getValid_date() {
        return valid_date;
    }

    public void setValid_date(Date valid_date) {
        this.valid_date = valid_date;
    }
}
