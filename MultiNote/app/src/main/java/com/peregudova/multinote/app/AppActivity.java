package com.peregudova.multinote.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.peregudova.multinote.R;
import com.peregudova.multinote.requests.NewAnswer;


public class AppActivity extends AppCompatActivity implements RecyclerViewClickListener{
    private TextView textView;
    AllNotesViewModel allNotesViewModel;
    RecyclerView rv;
    RVAdapter adapter;
    String user;
    String token;
    NoteFragment fragment;
    FragmentViewViewModel fragmentViewViewModel;
    FloatingActionButton actionButton;
    NewNoteViewModel newNoteViewModel;
    ExitViewModel exitViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_app);
        textView = findViewById(R.id.textView2);
        rv = findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        actionButton = findViewById(R.id.floatingActionButton);

        user = getIntent().getExtras().getString("user");
        token = getIntent().getExtras().getString("token");
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNoteViewModel.newNote(user, token);
            }
        });
        allNotesViewModel = ViewModelProviders.of(this).get(AllNotesViewModel.class);
        allNotesViewModel.getProgressState().observe(this, aBoolean -> {
            if (aBoolean) {
                showProgress();
            } else {
                hideProgress();
            }
        });
        allNotesViewModel.getNotesAnswerMutableLiveData().observe(this, notesAnswer -> {
            //логика обновления списка заметок
            if(notesAnswer.getNotes()!=null) {
                adapter = new RVAdapter(this, notesAnswer.getNotes(), this);
                rv.setAdapter(adapter);
            }
        });
        allNotesViewModel.getallnotes(user, token);

        fragmentViewViewModel = ViewModelProviders.of(this).get(FragmentViewViewModel.class);
        fragmentViewViewModel.getViewFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    setInvisible();
                } else{
                    setVisible();
                    fragment.saveNote();
                }
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = (NoteFragment) fragmentManager.findFragmentById(R.id.note_fragment);
        if(fragment!=null) {
            fragment.setFragmentViewViewModel(fragmentViewViewModel);
        }

        newNoteViewModel = ViewModelProviders.of(this).get(NewNoteViewModel.class);
        newNoteViewModel.getShowProgress().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    showProgress();
                } else{
                    hideProgress();
                }
            }
        });

        newNoteViewModel.getNewAnswerMutableLiveData().observe(this, new Observer<NewAnswer>() {
            @Override
            public void onChanged(NewAnswer newAnswer) {
                Toast.makeText(getApplicationContext(), newAnswer.getAnswer(), Toast.LENGTH_LONG).show();
                allNotesViewModel.getallnotes(user, token);
            }
        });
        exitViewModel = ViewModelProviders.of(this).get(ExitViewModel.class);
    }

    private void showProgress() {
        ProgressBar progressBar =  findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }
    private void hideProgress(){
        ProgressBar progressBar =  findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }


    @Override
    public void recyclerViewListClicked(View v, int position) {

        if(fragment!=null){
            fragment.setNote(adapter.getNoteByPosition(position), user, token);
            fragmentViewViewModel.setViewFragment(true);
        }
    }

    public void setVisible(){
        rv.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        actionButton.setVisibility(View.VISIBLE);
    }

    public void setInvisible(){
        rv.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        actionButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        //нажата клавиша назад делаем обновление списка и скрываем фрагмент
        fragmentViewViewModel.setViewFragment(false);
        allNotesViewModel.getallnotes(user, token);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exitViewModel.exit(token, user);
    }
}