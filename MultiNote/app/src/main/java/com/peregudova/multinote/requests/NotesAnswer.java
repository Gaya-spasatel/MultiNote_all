package com.peregudova.multinote.requests;

import java.util.Map;

public class NotesAnswer extends Answer{

    private Map<String, Note> notes;

    public NotesAnswer(String answer) {
        super(answer);
    }

    public Map<String, Note> getNotes() {
        return notes;
    }

    public void setNotes(Map<String, Note> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "NotesAnswer{" +
                "notes=" + notes +
                "} " + super.toString();
    }
}
