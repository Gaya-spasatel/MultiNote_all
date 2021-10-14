package com.peregudova.multinote.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.peregudova.multinote.app.AppActivity;
import com.peregudova.multinote.R;
import com.peregudova.multinote.requests.LoginAnswer;
import com.peregudova.multinote.requests.RegisterAnswer;

public class LoginActivity extends AppCompatActivity {
    private Button enter;
    private Button register;
    private EditText login;
    private EditText password;
    LoginViewModel viewModel;
    private String log;
    private String pas;
    RegisterViewModel registerViewModel;
    LogRegViewModel logRegViewModel;
    RegisterFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        enter = (Button) findViewById(R.id.login_button);
        register = (Button)findViewById(R.id.button_register);
        login = (EditText)findViewById(R.id.enter_login);
        password=(EditText)findViewById(R.id.enter_password);

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        registerViewModel.getAnswerMutableLiveData().observe(this, new Observer<RegisterAnswer>() {
            @Override
            public void onChanged(RegisterAnswer registerAnswer) {
                Toast.makeText(getApplicationContext(), registerAnswer.getAnswer()+registerAnswer.getConnection(), Toast.LENGTH_LONG).show();
            }
        });
        logRegViewModel = ViewModelProviders.of(this).get(LogRegViewModel.class);
        logRegViewModel.getFragmentVisible().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    setInvisible();
                }else{
                    setVisible();
                }
            }
        });
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //запуск проверки и после ее прохождения отправка данных на авторизацию
                log = login.getText().toString();
                pas = password.getText().toString();
                boolean answer = viewModel.check(log, pas);
                if(answer){
                    viewModel.logIn(log, pas);
                }else{
                    Toast.makeText(getApplicationContext(), "Error in login or password", Toast.LENGTH_SHORT).show();
                }
                enter.setClickable(false);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //логика открытия фрагмента регистрации
                if(fragment!=null){
                    logRegViewModel.setViewFragment(true);
                }
            }
        });
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        viewModel.getProgressState().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    showProgress();
                } else {
                    hideProgress();
                }
            }
        });
        viewModel.getAnswer().observe(this, new Observer<LoginAnswer>() {
            @Override
            public void onChanged(LoginAnswer loginAnswer) {

                enter.setClickable(true);
                if(loginAnswer.getConnection().equals("true") && loginAnswer.getAnswer().equals("Authorized") && !loginAnswer.getToken().equals("-")){
                    Intent intent = new Intent(LoginActivity.this, AppActivity.class);
                    intent.putExtra("user", log);
                    intent.putExtra("token", loginAnswer.getToken());
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Authorization Answer:"+loginAnswer.getAnswer(), Toast.LENGTH_LONG).show();
                }
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = (RegisterFragment) fragmentManager.findFragmentById(R.id.reg_fragment);
        if(fragment!=null){
            fragment.setLogRegViewModel(logRegViewModel);
            fragment.setRegisterViewModel(registerViewModel);
        }

    }

    private void setInvisible() {
        enter.setVisibility(View.INVISIBLE);
        register.setVisibility(View.INVISIBLE);
        login.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);
        findViewById(R.id.text_login).setVisibility(View.INVISIBLE);
        findViewById(R.id.textpassword).setVisibility(View.INVISIBLE);
        findViewById(R.id.textLogin).setVisibility(View.INVISIBLE);
    }

    private void setVisible(){
        enter.setVisibility(View.VISIBLE);
        register.setVisibility(View.VISIBLE);
        login.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        findViewById(R.id.text_login).setVisibility(View.VISIBLE);
        findViewById(R.id.textpassword).setVisibility(View.VISIBLE);
        findViewById(R.id.textLogin).setVisibility(View.VISIBLE);
    }

    private void showProgress() {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }
    private void hideProgress(){
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}