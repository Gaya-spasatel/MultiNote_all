package com.peregudova.multinote.requests;

public class ChangeNoteCommand extends Command {

    private String id_note;

    public ChangeNoteCommand(String token, String user, String id_note) {
        super("change", token, user);
        this.id_note = id_note;
    }

    public String getId_note() {
        return id_note;
    }

    public void setId_note(String id_note) {
        this.id_note = id_note;
    }
}
