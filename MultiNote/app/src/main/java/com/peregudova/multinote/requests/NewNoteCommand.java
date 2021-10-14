package com.peregudova.multinote.requests;

public class NewNoteCommand extends Command{
    public NewNoteCommand(String token, String user) {
        super("new", token, user);
    }
}
