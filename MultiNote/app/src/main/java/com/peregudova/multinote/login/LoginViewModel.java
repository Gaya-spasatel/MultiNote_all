package com.peregudova.multinote.login;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.peregudova.multinote.checkers.LoginChecker;
import com.peregudova.multinote.checkers.PasswordChecker;
import com.peregudova.multinote.requests.LoginAnswer;
import com.peregudova.multinote.requests.Requester;
import com.peregudova.multinote.requests.User;

import java.io.IOException;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginAnswer> answer = new MutableLiveData<LoginAnswer>();

    private MutableLiveData<Boolean> showProgress = new MutableLiveData<>();

    class LoginTask extends AsyncTask<User, Void, LoginAnswer>{

        @Override
        protected LoginAnswer doInBackground(User... users) {
            LoginAnswer loginAnswer = new LoginAnswer("Nothing", "false", "-");
            for(User user:users){
                try {
                    loginAnswer = new Requester().loginUser(user);
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
            return loginAnswer;
        }

        @Override
        protected void onPostExecute(LoginAnswer loginAnswer) {
            answer.postValue(loginAnswer);
            showProgress.postValue(false);
        }
    }


    public void logIn(String login, String password){
        showProgress.postValue(true);
        LoginTask loginTask = new LoginTask();
        loginTask.execute(new User(login, password));
    }

    public MutableLiveData<Boolean> getProgressState(){
        return showProgress;
    }

    public MutableLiveData<LoginAnswer> getAnswer() {
        return answer;
    }

    public boolean check(String login, String password){
        return new LoginChecker().check(login) && new PasswordChecker().check(password);
    }
}
