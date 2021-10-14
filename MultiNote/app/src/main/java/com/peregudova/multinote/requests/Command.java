package com.peregudova.multinote.requests;

public class Command {

    private String command;
    private String token;
    private String user;

    public Command(String command, String token, String user) {
        this.command = command;
        this.token = token;
        this.user = user;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
