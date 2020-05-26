package com.example.blinkingbuttongame.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("WELCOME\n\n<---GUD STUF OVA HEE");
    }

    public LiveData<String> getText() {
        return mText;
    }
}