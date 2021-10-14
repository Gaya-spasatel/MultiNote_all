package com.peregudova.multinote.requests;

public class SaveNoteCommand extends GetNoteCommand{

    private String text;

    public SaveNoteCommand(String token, String user, String id_note, String text) {
        super(token, user, id_note);
        this.text = text;
        this.setCommand("save");
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
