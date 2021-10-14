package com.peregudova.multinote.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LogRegViewModel extends ViewModel {

    private MutableLiveData<Boolean> fragmentVisible = new MutableLiveData<>();

    public MutableLiveData<Boolean> getFragmentVisible() {
        return fragmentVisible;
    }

    public void setFragmentVisible(MutableLiveData<Boolean> fragmentVisible) {
        this.fragmentVisible = fragmentVisible;
    }

    public void setViewFragment(boolean b) {
        fragmentVisible.postValue(b);
    }
}
