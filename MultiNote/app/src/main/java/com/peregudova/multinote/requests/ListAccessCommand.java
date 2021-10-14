package com.peregudova.multinote.requests;

public class ListAccessCommand extends Command{
    private String id_note;
    public ListAccessCommand(String token, String user, String id_note) {
        super("listaccess", token, user);
        this.id_note = id_note;
    }

    public String getId_note() {
        return id_note;
    }

    public void setId_note(String id_note) {
        this.id_note = id_note;
    }
}
