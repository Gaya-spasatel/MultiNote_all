package com.peregudova.multinote.requests;

public class GetAllNotesCommand extends Command{
    public GetAllNotesCommand(String token, String user) {
        super("getallnotes", token, user);
    }
}
