package com.peregudova.multinote.checkers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginChecker implements Checker{

    @Override
    public boolean check(String line) {
        if(line.length()<5) return false;
        return Pattern.compile("[\\d\\w]+").matcher(line).matches();
    }
}
