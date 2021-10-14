package com.peregudova.multinote.requests;

import java.util.Map;

public class ListAccessAnswer extends Answer{

    private Map<String, AccessLogin> listaccess;

    public ListAccessAnswer(String answer, Map<String, AccessLogin> listaccess) {
        super(answer);
        this.listaccess = listaccess;
    }

    public Map<String, AccessLogin> getListaccess() {
        return listaccess;
    }

    public void setListaccess(Map<String, AccessLogin> listaccess) {
        this.listaccess = listaccess;
    }

    @Override
    public String toString() {
        return "ListAccessAnswer{" +
                "listaccess=" + listaccess +
                "} " + super.toString();
    }
}
