package com.peregudova.multinote.requests;

public class Note {
    private String id;
    private String owner;
    private String text;
    private String last_modified;
    private String login_modified;
    private String is_private;
    private String is_modified;

    public Note(String id, String owner, String text, String last_modified, String login_modified, String is_private, String is_modified) {
        this.id = id;
        this.owner = owner;
        this.text = text;
        this.last_modified = last_modified;
        this.login_modified = login_modified;
        this.is_private = is_private;
        this.is_modified = is_modified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
    }

    public String getLogin_modified() {
        return login_modified;
    }

    public void setLogin_modified(String login_modified) {
        this.login_modified = login_modified;
    }

    public String getIs_private() {
        return is_private;
    }

    public void setIs_private(String is_private) {
        this.is_private = is_private;
    }

    public String getIs_modified() {
        return is_modified;
    }

    public void setIs_modified(String is_modified) {
        this.is_modified = is_modified;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", text='" + text + '\'' +
                ", last_modified='" + last_modified + '\'' +
                ", login_modified='" + login_modified + '\'' +
                ", is_private='" + is_private + '\'' +
                ", is_modified='" + is_modified + '\'' +
                '}';
    }
}
