package com.peregudova.multinote.requests;

public class User {
    private String login;
    private String password;
    private String email;

    public User(String login, String password){
        this.login = login;
        this.password = password;
    }
    public User(String login, String password, String email){
        this.login = login;
        this.password = password;
        this.email = email;
    }
    User(){}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
