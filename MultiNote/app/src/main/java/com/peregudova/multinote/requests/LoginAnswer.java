package com.peregudova.multinote.requests;

public class LoginAnswer extends Answer{

    private String connection;
    private String token;

    public LoginAnswer(String answer, String connection, String token) {
        super(answer);
        this.connection = connection;
        this.token = token;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginAnswer{" +
                "connection='" + connection + '\'' +
                ", token='" + token + '\'' +
                " " + super.toString();
    }
}
