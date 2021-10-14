package com.peregudova.multinote.app;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.peregudova.multinote.checkers.LoginChecker;
import com.peregudova.multinote.requests.AddAccessAnswer;
import com.peregudova.multinote.requests.AddAccessCommand;
import com.peregudova.multinote.requests.ChangeAnswer;
import com.peregudova.multinote.requests.ChangeNoteCommand;
import com.peregudova.multinote.requests.GetNoteCommand;
import com.peregudova.multinote.requests.ListAccessAnswer;
import com.peregudova.multinote.requests.ListAccessCommand;
import com.peregudova.multinote.requests.Note;
import com.peregudova.multinote.requests.NoteAnswer;
import com.peregudova.multinote.requests.Requester;
import com.peregudova.multinote.requests.SaveAnswer;
import com.peregudova.multinote.requests.SaveNoteCommand;

import java.io.IOException;

public class NoteFragmentViewModel extends ViewModel {
    private MutableLiveData<NoteAnswer> noteAnswerMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ListAccessAnswer> listAccessAnswerMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<AddAccessAnswer> addAccessAnswerMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ChangeAnswer> changeAnswerMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<SaveAnswer> saveAnswerMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<NoteAnswer> getNoteAnswerMutableLiveData() {
        return noteAnswerMutableLiveData;
    }

    public MutableLiveData<ListAccessAnswer> getListAccessAnswerMutableLiveData() {
        return listAccessAnswerMutableLiveData;
    }

    public MutableLiveData<AddAccessAnswer> getAddAccessAnswerMutableLiveData() {
        return addAccessAnswerMutableLiveData;
    }

    public MutableLiveData<ChangeAnswer> getChangeAnswerMutableLiveData() {
        return changeAnswerMutableLiveData;
    }

    public MutableLiveData<SaveAnswer> getSaveAnswerMutableLiveData() {
        return saveAnswerMutableLiveData;
    }

    class NoteAsync extends AsyncTask<GetNoteCommand, Void, NoteAnswer>{

        @Override
        protected NoteAnswer doInBackground(GetNoteCommand... getNoteCommands) {
            NoteAnswer notesAnswer = new NoteAnswer("Error", null);
            for(GetNoteCommand getNoteCommand:getNoteCommands){
                try {
                    notesAnswer = new Requester().getnote(getNoteCommand);
                } catch (IOException ignored) {

                }
            }
            return notesAnswer;
        }

        @Override
        protected void onPostExecute(NoteAnswer noteAnswer) {
            noteAnswerMutableLiveData.postValue(noteAnswer);
        }
    }

    class ListAccessAsync extends AsyncTask<ListAccessCommand, Void, ListAccessAnswer>{

        @Override
        protected ListAccessAnswer doInBackground(ListAccessCommand... listAccessCommands) {
            ListAccessAnswer listAccessAnswer = new ListAccessAnswer("Error in app", null);
            for(ListAccessCommand listAccessCommand:listAccessCommands){
                try {
                    listAccessAnswer = new Requester().listAccess(listAccessCommand);
                } catch (IOException ignored) {

                }
            }
            return listAccessAnswer;
        }

        @Override
        protected void onPostExecute(ListAccessAnswer listAccessAnswer) {
            listAccessAnswerMutableLiveData.postValue(listAccessAnswer);
        }
    }

    class AddAccessAsync extends AsyncTask<AddAccessCommand, Void, AddAccessAnswer>{

        @Override
        protected AddAccessAnswer doInBackground(AddAccessCommand... addAccessCommands) {
            AddAccessAnswer addAccessAnswer = new AddAccessAnswer("Error in app");
            for(AddAccessCommand command: addAccessCommands){
                try {
                    addAccessAnswer = new Requester().addAccess(command);
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
            return addAccessAnswer;
        }

        @Override
        protected void onPostExecute(AddAccessAnswer addAccessAnswer) {
            addAccessAnswerMutableLiveData.postValue(addAccessAnswer);
        }
    }

    class ChangeAsync extends AsyncTask<ChangeNoteCommand, Void, ChangeAnswer>{

        @Override
        protected ChangeAnswer doInBackground(ChangeNoteCommand... changeNoteCommands) {
            ChangeAnswer changeAnswer = new ChangeAnswer("Error in app", null);
            for(ChangeNoteCommand changeNoteCommand: changeNoteCommands){
                try {
                    changeAnswer = new Requester().changenote(changeNoteCommand);
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }

            return changeAnswer;
        }

        @Override
        protected void onPostExecute(ChangeAnswer changeAnswer) {
            changeAnswerMutableLiveData.postValue(changeAnswer);
        }
    }

    class SaveAsync extends AsyncTask<SaveNoteCommand, Void, SaveAnswer>{

        @Override
        protected SaveAnswer doInBackground(SaveNoteCommand... saveNoteCommands) {
            SaveAnswer saveAnswer = new SaveAnswer("Error in app");
            for(SaveNoteCommand saveNoteCommand:saveNoteCommands){
                try {
                    saveAnswer = new Requester().saveNote(saveNoteCommand);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return saveAnswer;
        }

        @Override
        protected void onPostExecute(SaveAnswer saveAnswer) {
            saveAnswerMutableLiveData.postValue(saveAnswer);
        }
    }

    public void getNote(Note note, String user, String token){
        NoteAsync noteAsync = new NoteAsync();
        noteAsync.execute(new GetNoteCommand(token, user, note.getId()));
    }

    public void getListAccess(String user, String token, String id_note){
        ListAccessAsync listAccessAsync = new ListAccessAsync();
        listAccessAsync.execute(new ListAccessCommand(token, user, id_note));
    }

    public void addAccessList(String user, String token, String login, String id_note){
        AddAccessAsync addAccessAsync = new AddAccessAsync();
        addAccessAsync.execute(new AddAccessCommand(token, user, login, id_note));
    }

    public void changeNote(String user, String token, String id_note){
        ChangeAsync changeAsync = new ChangeAsync();
        changeAsync.execute(new ChangeNoteCommand(token, user, id_note));
    }

    public void saveNote(String token, String user, String id_note, String text){
        SaveAsync saveAsync = new SaveAsync();
        saveAsync.execute(new SaveNoteCommand(token, user, id_note, text));
    }

    public boolean check(String str){
        return new LoginChecker().check(str);
    }

}
