package com.peregudova.multinote.requests;

public class RegisterAnswer extends Answer{
    private String connection;
    public RegisterAnswer(String answer, String connection) {
        super(answer);
        this.connection = connection;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    @Override
    public String toString() {
        return "RegisterAnswer{" +
                "connection='" + connection + '\'' +
                super.toString();
    }
}
