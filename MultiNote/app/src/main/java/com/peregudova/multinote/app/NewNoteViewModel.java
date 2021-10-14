package com.peregudova.multinote.app;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.peregudova.multinote.requests.NewAnswer;
import com.peregudova.multinote.requests.NewNoteCommand;
import com.peregudova.multinote.requests.Requester;

import java.io.IOException;

public class NewNoteViewModel extends ViewModel {
    private MutableLiveData<NewAnswer> newAnswerMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> showProgress = new MutableLiveData<>();

    public MutableLiveData<NewAnswer> getNewAnswerMutableLiveData() {
        return newAnswerMutableLiveData;
    }

    public MutableLiveData<Boolean> getShowProgress() {
        return showProgress;
    }

    class NewNoteAsync extends AsyncTask<NewNoteCommand, Void, NewAnswer>{

        @Override
        protected NewAnswer doInBackground(NewNoteCommand... newNoteCommands) {
            NewAnswer newAnswer = new NewAnswer("Error in app");
            for(NewNoteCommand command:newNoteCommands){
                Requester requester = new Requester();
                try {
                    newAnswer = requester.newNote(command);
                } catch (IOException ignored) {

                }
            }
            return newAnswer;
        }

        @Override
        protected void onPostExecute(NewAnswer newAnswer) {
            newAnswerMutableLiveData.postValue(newAnswer);
            showProgress.postValue(false);
        }
    }

    public void newNote(String user, String token){
        showProgress.postValue(true);
        NewNoteAsync newNoteAsync = new NewNoteAsync();
        newNoteAsync.execute(new NewNoteCommand(token, user));
    }
}
