package com.peregudova.multinote.checkers;

import java.util.regex.Pattern;

public class PasswordChecker implements Checker{

    @Override
    public boolean check(String line) {
        if(line.length()<5) return false;
        return Pattern.compile("[\\d\\w]+").matcher(line).matches();
    }
}
