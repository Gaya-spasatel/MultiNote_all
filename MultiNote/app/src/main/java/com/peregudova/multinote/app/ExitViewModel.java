package com.peregudova.multinote.app;

import android.os.AsyncTask;

import androidx.lifecycle.ViewModel;

import com.peregudova.multinote.requests.ExitAnswer;
import com.peregudova.multinote.requests.ExitCommand;
import com.peregudova.multinote.requests.Requester;

import java.io.IOException;

public class ExitViewModel extends ViewModel {
    class ExitAsync extends AsyncTask<ExitCommand, Void, ExitAnswer>{

        @Override
        protected ExitAnswer doInBackground(ExitCommand... exitCommands) {
            ExitAnswer exitAnswer = new ExitAnswer("Error in app");
            for(ExitCommand exitCommand: exitCommands){
                try {
                    exitAnswer = new Requester().exitAnswer(exitCommand);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return exitAnswer;
        }
    }

    public void exit(String token, String user){
        ExitAsync exitAsync = new ExitAsync();
        exitAsync.execute(new ExitCommand(token, user));
    }
}
