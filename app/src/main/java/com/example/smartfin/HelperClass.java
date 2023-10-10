package com.example.smartfin;

public class HelperClass {
    String name , email , pass , confirm_pass;

    public HelperClass(String name, String email, String pass, String confirm_pass) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.confirm_pass = confirm_pass;
    }

    public HelperClass(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public HelperClass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getConfirm_pass() {
        return confirm_pass;
    }

    public void setConfirm_pass(String confirm_pass) {
        this.confirm_pass = confirm_pass;
    }
}
