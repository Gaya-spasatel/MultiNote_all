package com.peregudova.multinote.app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.peregudova.multinote.R;
import com.peregudova.multinote.requests.ChangeAnswer;
import com.peregudova.multinote.requests.ListAccessAnswer;
import com.peregudova.multinote.requests.Note;
import com.peregudova.multinote.requests.NoteAnswer;
import com.peregudova.multinote.requests.User;

import org.jetbrains.annotations.NotNull;

public class NoteFragment extends Fragment {

    NoteFragmentViewModel noteFragmentViewModel;
    Button button;
    TextView note_text;
    TextView note_info;
    EditText text_add_access;
    TextView list_access;
    Button change;
    FragmentViewViewModel fragmentViewViewModel;
    String user;
    Note note;
    String token;
    Button save;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noteFragmentViewModel = ViewModelProviders.of(this).get(NoteFragmentViewModel.class);
        noteFragmentViewModel.getNoteAnswerMutableLiveData().observe(this, new Observer<NoteAnswer>() {
            @Override
            public void onChanged(NoteAnswer noteAnswer) {
                //вызов функций отображения контента
                note = noteAnswer.getNote();
                showContent(noteAnswer);

            }
        });
        noteFragmentViewModel.getListAccessAnswerMutableLiveData().observe(this, new Observer<ListAccessAnswer>() {
            @Override
            public void onChanged(ListAccessAnswer listAccessAnswer) {
                showListAccess(listAccessAnswer);
            }
        });
        noteFragmentViewModel.getChangeAnswerMutableLiveData().observe(this, new Observer<ChangeAnswer>() {
            @Override
            public void onChanged(ChangeAnswer changeAnswer) {
                if(changeAnswer.getChange().equals("true")){
                    unBlockText();
                    change.setVisibility(View.GONE);
                }
            }
        });
    }

    private void showListAccess(ListAccessAnswer listAccessAnswer) {
        StringBuffer list = new StringBuffer();
        for(String key:listAccessAnswer.getListaccess().keySet()){
            list.append(key).append(". ").append(listAccessAnswer.getListaccess().get(key).getAccess_login()).append("\n");
        }
        list_access.setText(list);
    }

    public void setFragmentViewViewModel(FragmentViewViewModel fragmentViewViewModel) {
        this.fragmentViewViewModel = fragmentViewViewModel;
        this.fragmentViewViewModel.getViewFragment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    setVisible();
                } else{
                    setInvisible();
                }
            }
        });
    }

    public void setVisible(){
        getView().setVisibility(View.VISIBLE);
    }

    public void setInvisible(){
        getView().setVisibility(View.INVISIBLE);
    }

    private void showContent(NoteAnswer noteAnswer) {
        setVisible();
        note_text.setText(noteAnswer.getNote().getText());
        Note note = noteAnswer.getNote();
        String info = "Is modified: "+note.getIs_modified()+"\nLast modified: "+note.getLast_modified()+"\nLogin modified: "+note.getLogin_modified()+"\nOwner: "+note.getOwner();
        note_info.setText(info);
        change.setClickable(!note.getIs_modified().equals("1"));
        save.setVisibility(View.GONE);
        change.setVisibility(View.VISIBLE);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.note_fragment, container, false);
        button = inflate.findViewById(R.id.add_access_button);
        text_add_access = inflate.findViewById(R.id.new_access);
        button.setOnClickListener(view -> {
            //button add list access
            String login = text_add_access.getText().toString();
            if(noteFragmentViewModel.check(login)){
                noteFragmentViewModel.addAccessList(user, token, login, note.getId());
                noteFragmentViewModel.getListAccess(user, token, note.getId());
            }
        });
        change = inflate.findViewById(R.id.button_change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ask server to change note
                noteFragmentViewModel.changeNote(user, token, note.getId());
            }
        });
        save = inflate.findViewById(R.id.button_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clicked to save note
                saveNote();
                blockText();
            }
        });
        note_text = inflate.findViewById(R.id.note_text);
        note_info = inflate.findViewById(R.id.note_info);
        list_access = inflate.findViewById(R.id.list_access);
        inflate.setVisibility(View.GONE);
        return inflate;
    }

    public void saveNote() {
        blockText();
        String text = note_text.getText().toString();
        noteFragmentViewModel.saveNote(token, user, note.getId(), text);
    }


    public void setNote(Note note, String user, String token) {
        //команда от активности на загрузку data фрагмента
        this.user = user;
        this.token = token;
        noteFragmentViewModel.getNote(note, user, token);
        noteFragmentViewModel.getListAccess(user, token, note.getId());
    }

    private void unBlockText(){
        save.setVisibility(View.VISIBLE);
        note_text.setEnabled(true);
    }

    private void blockText(){
        save.setVisibility(View.GONE);
        note_text.setEnabled(false);
        change.setVisibility(View.VISIBLE);
    }
}
