package com.frednm.popular_movies_app_i.common.base;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.LiveData;

import com.frednm.popular_movies_app_i.common.utils.Event;
import com.google.android.material.snackbar.Snackbar;

import dagger.android.AndroidInjection;

/**
 * Question: Why the getContentIfNotHandled() Event method doesn't work !? In fact it's almost always returning null, even when the
 * event has not yet been handled !? This is why I used peekContent() method in handleSnackBar(), but the drawback here is
 * that during a screen rotation it will fire again, so the SnackBar will be shown !!!
 */

abstract public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.configureDagger();
        super.onCreate(savedInstanceState);
    }

    // Read second comment here (https://stackoverflow.com/questions/7621349/do-you-use-onpostcreate-method)
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        setupSnackbar(getViewModel().getSnackbarError(), Snackbar.LENGTH_LONG);
        super.onPostCreate(savedInstanceState);
    }

    abstract protected BaseViewModel getViewModel() ;

    private void configureDagger(){
        AndroidInjection.inject(this);
    }


    /**
     * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
     */
    private void setupSnackbar(LiveData<Event<Integer>> snackbarEvent, Integer timeLength) {
        snackbarEvent.observe(this, (event) -> handleSnackBarEvent(event, timeLength));
    }

    private void handleSnackBarEvent(Event<Integer> event, Integer timeLength){
        Log.d("BaseActivity","The event content is "+getString(event.peekContent()));
        if (event.getContentIfNotHandled() != null) {
            Log.d("BaseActivity","The text is "+event.getContentIfNotHandled());
            showSnackbar(getString(event.peekContent()), timeLength);
        }
    }

    private void showSnackbar(String snackbarText, Integer timeLength) {
        Snackbar.make(findViewById(android.R.id.content), snackbarText, timeLength).show();
    }
}
