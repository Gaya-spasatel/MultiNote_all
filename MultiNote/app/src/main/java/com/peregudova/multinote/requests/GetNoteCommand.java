package com.peregudova.multinote.requests;

public class GetNoteCommand extends Command{

    private String id_note;

    public GetNoteCommand(String token, String user, String id_note) {
        super("getnote", token, user);
        this.id_note = id_note;
    }

    public String getId_note() {
        return id_note;
    }

    public void setId_note(String id_note) {
        this.id_note = id_note;
    }
}
