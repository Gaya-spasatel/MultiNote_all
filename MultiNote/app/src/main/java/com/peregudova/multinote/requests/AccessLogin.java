package com.peregudova.multinote.requests;

public class AccessLogin {
    private String access_login;

    public AccessLogin(String access_login) {
        this.access_login = access_login;
    }

    public String getAccess_login() {
        return access_login;
    }

    public void setAccess_login(String access_login) {
        this.access_login = access_login;
    }

    @Override
    public String toString() {
        return "AccessLogin{" +
                "access_login='" + access_login + '\'' +
                '}';
    }
}
