package com.peregudova.multinote.app;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.peregudova.multinote.requests.GetAllNotesCommand;
import com.peregudova.multinote.requests.NotesAnswer;
import com.peregudova.multinote.requests.Requester;

import java.io.IOException;

public class AllNotesViewModel extends ViewModel {
    private MutableLiveData<Boolean> showProgress = new MutableLiveData<>();

    private MutableLiveData<NotesAnswer> notesAnswerMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Boolean> getProgressState(){
        return showProgress;
    }
    class AllNotesAsync extends AsyncTask<GetAllNotesCommand, Void, NotesAnswer>{

        @Override
        protected NotesAnswer doInBackground(GetAllNotesCommand... getAllNotesCommands) {
            NotesAnswer notesAnswer = new NotesAnswer("Error");
            for(GetAllNotesCommand getAllNotesCommand: getAllNotesCommands){
                try {
                    notesAnswer = new Requester().getallnotes(getAllNotesCommand);
                } catch (IOException ignored) {

                }
            }
            return notesAnswer;
        }

        @Override
        protected void onPostExecute(NotesAnswer notesAnswer) {
            notesAnswerMutableLiveData.postValue(notesAnswer);
            showProgress.postValue(false);
        }
    }

    public MutableLiveData<NotesAnswer> getNotesAnswerMutableLiveData() {
        return notesAnswerMutableLiveData;
    }

    public void getallnotes(String user, String token){
        showProgress.postValue(true);
        AllNotesAsync async = new AllNotesAsync();
        async.execute(new GetAllNotesCommand(token, user));
    }
}
