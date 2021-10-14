package com.peregudova.multinote.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.peregudova.multinote.R;
import com.peregudova.multinote.requests.RegisterAnswer;

import org.jetbrains.annotations.NotNull;

public class RegisterFragment extends Fragment{

    RegisterViewModel registerViewModel;
    EditText login;
    EditText password;
    EditText email;
    ProgressBar progressBar;
    Button register_button;
    Button back;
    private LogRegViewModel logRegViewModel;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void setLogRegViewModel(LogRegViewModel logRegViewModel) {
        this.logRegViewModel = logRegViewModel;
        this.logRegViewModel.getFragmentVisible().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    setVisible();
                }else {
                    setInvisible();
                }
            }
        });
    }

    public void setRegisterViewModel(RegisterViewModel registerViewModel) {
        this.registerViewModel = registerViewModel;
        registerViewModel.getShowProgress().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    showProgress();
                } else {
                    hideProgress();
                }
            }
        });
        registerViewModel.getAnswerMutableLiveData().observe(this, new Observer<RegisterAnswer>() {
            @Override
            public void onChanged(RegisterAnswer registerAnswer) {
                //got answer from server
                logRegViewModel.setViewFragment(false);
            }
        });
    }

    private void setInvisible() {
        getView().setVisibility(View.INVISIBLE);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.register_fragment, container, false);
        login = inflate.findViewById(R.id.reg_login);
        password = inflate.findViewById(R.id.reg_password);
        email = inflate.findViewById(R.id.reg_email);
        register_button = inflate.findViewById(R.id.reg_button);
        back = inflate.findViewById(R.id.back_button_register);
        progressBar = inflate.findViewById(R.id.progressBar2);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reg_login = login.getText().toString();
                String reg_password  = password.getText().toString();
                String reg_email = email.getText().toString();
                boolean answer = registerViewModel.checkData(reg_login, reg_password,reg_email);
                if(answer){
                    registerViewModel.registerUser(reg_login, reg_password, reg_email);
                }else{
                    Toast.makeText(getContext(), "Error in login or password or email", Toast.LENGTH_SHORT).show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logRegViewModel.setViewFragment(false);
            }
        });
        inflate.setVisibility(View.GONE);
        return inflate;
    }

    private void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void setVisible() {
        getView().setVisibility(View.VISIBLE);
    }
}
