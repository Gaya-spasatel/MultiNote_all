package com.peregudova.multinote.requests;

public class ChangeAnswer extends Answer{

    private String change;

    public ChangeAnswer(String answer, String change) {
        super(answer);
        this.change = change;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    @Override
    public String toString() {
        return "ChangeAnswer{" +
                "change='" + change + '\'' + super.toString();
    }
}
