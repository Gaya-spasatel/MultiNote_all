package com.peregudova.multinote.requests;

public class NoteAnswer extends Answer{

    private Note note;

    public NoteAnswer(String answer, Note note) {
        super(answer);
        this.note = note;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "NoteAnswer{" +
                "note=" + note +
                "} " + super.toString();
    }
}
