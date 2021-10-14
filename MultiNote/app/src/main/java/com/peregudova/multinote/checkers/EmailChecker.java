package com.peregudova.multinote.checkers;

import java.util.regex.Pattern;

public class EmailChecker implements Checker{
    @Override
    public boolean check(String line) {
        if(line.length()<5) return false;
        return Pattern.compile("[\\d\\w]+@[\\d\\w]+.[\\d\\w]{2,3}").matcher(line).matches();
    }
}
