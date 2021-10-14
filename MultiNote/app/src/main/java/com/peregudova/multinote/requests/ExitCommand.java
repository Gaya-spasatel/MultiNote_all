package com.peregudova.multinote.requests;

public class ExitCommand extends Command{
    public ExitCommand(String token, String user) {
        super("exit", token, user);
    }
}
