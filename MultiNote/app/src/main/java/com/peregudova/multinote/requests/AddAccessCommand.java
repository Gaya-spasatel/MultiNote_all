package com.peregudova.multinote.requests;

public class AddAccessCommand extends Command {

    private String login;
    private String id_note;
    public AddAccessCommand(String token, String user, String login, String id_note) {
        super("addaccess", token, user);
        this.login = login;
        this.id_note = id_note;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getId_note() {
        return id_note;
    }

    public void setId_note(String id_note) {
        this.id_note = id_note;
    }
}
