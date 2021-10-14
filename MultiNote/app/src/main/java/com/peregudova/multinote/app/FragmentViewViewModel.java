package com.peregudova.multinote.app;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FragmentViewViewModel extends ViewModel {

    private MutableLiveData<Boolean> viewFragment = new MutableLiveData<>();

    public MutableLiveData<Boolean> getViewFragment() {
        return viewFragment;
    }

    public void setViewFragment(boolean b){
        viewFragment.postValue(b);
    }
}
