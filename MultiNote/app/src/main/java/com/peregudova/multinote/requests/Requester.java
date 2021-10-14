package com.peregudova.multinote.requests;

import android.os.Build;

import com.google.gson.Gson;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Requester {
    private final String url = "http://mail.mioks.ru/notes/";


    public Requester() {}

    public LoginAnswer loginUser(User user) throws IOException {
        final URL url = new URL(this.url+"login/index.php");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("login", user.getLogin());
        parameters.put("password", user.getPassword());
        setData(con, parameters);
        return new Gson().fromJson(getAnswer(con), LoginAnswer.class);

    }

    private String getAnswer(HttpURLConnection con) {
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content.toString();
        } catch (final Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private String getParamsString(Map<String,String> params) {
        final StringBuilder result = new StringBuilder();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            params.forEach((name, value) -> {
                try {
                    result.append(URLEncoder.encode(name, "UTF-8"));
                    result.append('=');
                    result.append(URLEncoder.encode(value, "UTF-8"));
                    result.append('&');
                } catch (final UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            });
        }

        final String resultString = result.toString();
        return !resultString.isEmpty()
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    private void setData(HttpURLConnection con, Map<String, String> params) throws IOException {
        con.setDoOutput(true);
        final DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(getParamsString(params));
        out.flush();
        out.close();
    }

    public RegisterAnswer registerUser(User user) throws IOException {
        final URL url = new URL(this.url+"login/register.php");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("reg_login", user.getLogin());
        parameters.put("reg_password", user.getPassword());
        parameters.put("reg_email", user.getEmail());
        setData(con, parameters);
        return new Gson().fromJson(getAnswer(con), RegisterAnswer.class);
    }

    public NotesAnswer getallnotes(GetAllNotesCommand command) throws IOException {
        final URL url = new URL(this.url+"app/getnotes.php");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("command", command.getCommand());
        parameters.put("user", command.getUser());
        parameters.put("token", command.getToken());
        setData(con, parameters);
        return new Gson().fromJson(getAnswer(con), NotesAnswer.class);
    }

    public NoteAnswer getnote(GetNoteCommand getNoteCommand) throws IOException {
        final URL url = new URL(this.url+"app/getnotes.php");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("command", getNoteCommand.getCommand());
        parameters.put("user", getNoteCommand.getUser());
        parameters.put("token", getNoteCommand.getToken());
        parameters.put("id_note", getNoteCommand.getId_note());
        setData(con, parameters);
        return new Gson().fromJson(getAnswer(con), NoteAnswer.class);
    }

    public ChangeAnswer changenote(ChangeNoteCommand getNoteCommand) throws IOException {
        final URL url = new URL(this.url+"app/getnotes.php");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("command", getNoteCommand.getCommand());
        parameters.put("user", getNoteCommand.getUser());
        parameters.put("token", getNoteCommand.getToken());
        parameters.put("id_note", getNoteCommand.getId_note());
        setData(con, parameters);
        return new Gson().fromJson(getAnswer(con), ChangeAnswer.class);
    }

    public SaveAnswer saveNote(SaveNoteCommand saveNoteCommand) throws IOException {
        final URL url = new URL(this.url+"app/getnotes.php");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("command", saveNoteCommand.getCommand());
        parameters.put("user", saveNoteCommand.getUser());
        parameters.put("token", saveNoteCommand.getToken());
        parameters.put("id_note", saveNoteCommand.getId_note());
        parameters.put("text", saveNoteCommand.getText());
        setData(con, parameters);
        return new Gson().fromJson(getAnswer(con), SaveAnswer.class);
    }

    public NewAnswer newNote(NewNoteCommand newNoteCommand) throws IOException {
        final URL url = new URL(this.url+"app/getnotes.php");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("command", newNoteCommand.getCommand());
        parameters.put("user", newNoteCommand.getUser());
        parameters.put("token", newNoteCommand.getToken());
        setData(con, parameters);
        return new Gson().fromJson(getAnswer(con), NewAnswer.class);
    }

    public ExitAnswer exitAnswer(ExitCommand exitCommand) throws IOException {
        final URL url = new URL(this.url+"app/getnotes.php");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("command", exitCommand.getCommand());
        parameters.put("user", exitCommand.getUser());
        parameters.put("token", exitCommand.getToken());
        setData(con, parameters);
        return new Gson().fromJson(getAnswer(con), ExitAnswer.class);
    }

    public ListAccessAnswer listAccess(ListAccessCommand command) throws IOException {
        final URL url = new URL(this.url+"app/getnotes.php");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("command", command.getCommand());
        parameters.put("user", command.getUser());
        parameters.put("token", command.getToken());
        parameters.put("id_note", command.getId_note());
        setData(con, parameters);
        return new Gson().fromJson(getAnswer(con), ListAccessAnswer.class);
    }

    public AddAccessAnswer addAccess(AddAccessCommand command) throws IOException {
        final URL url = new URL(this.url+"app/getnotes.php");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("command", command.getCommand());
        parameters.put("user", command.getUser());
        parameters.put("token", command.getToken());
        parameters.put("id_note", command.getId_note());
        parameters.put("login", command.getLogin());
        setData(con, parameters);
        return new Gson().fromJson(getAnswer(con), AddAccessAnswer.class);
    }
}
