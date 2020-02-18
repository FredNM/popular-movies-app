package com.frednm.popular_movies_app_i.features.home;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.frednm.popular_movies_app_i.R;
import com.frednm.popular_movies_app_i.common.base.BaseActivity;
import com.frednm.popular_movies_app_i.common.base.BaseViewModel;
import com.frednm.popular_movies_app_i.databinding.ActivityHomeBinding;
import com.frednm.popular_movies_app_i.features.detail.DetailActivity;
import com.frednm.popular_movies_app_i.features.home.views.HomeAdapter;
import com.frednm.popular_movies_app_i.features.settings.SettingsActivity;

import javax.inject.Inject;

/**
 * Question: Should I have encapsulated the navigation event in a LiveData ? When configuring navigation like here without
 * encapsulating it in a single livedata event, is it wrong ? Or at least, could it lead to some drawbacks ?
 */

public class HomeActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener, HomeViewModel.Listeners {

    @Inject ViewModelProvider.Factory viewModelFactory;
    @NonNull
    private HomeViewModel viewModel;

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, viewModelFactory).get(HomeViewModel.class);
        viewModel.implementedListener(this); // just to say to ViewModel that its implemented Listener is in this Activity

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        this.configureRecyclerView();
        this.setupSharedPreferences();
    }

    public BaseViewModel getViewModel() {
        return viewModel ;
    }


    private void configureRecyclerView() {
        binding.activityHomeRecyclerView.setAdapter(new HomeAdapter(viewModel));
    }

    @Override
    public void navigateToDetailActivity(Integer movieId) {
        Log.d("HomeActivity", "Entering in navigateToDetailActivity method");
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIEID, movieId);
        startActivity(intent);
    }

    // --- Just for Preference settings
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
                startActivity(startSettingsActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortingCriteria = sharedPreferences.getString(getString(R.string.pref_sort_key),getString(R.string.pref_sort_popular_value));
    // The value of the sortingCriteria could be popular or topRated. The default value is popular.
        viewModel.userChangesSettings(sortingCriteria); // Initialise the UI with the default sorting criteria.
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    // Listener, for listening setting changes !
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d("HomeActivity", "Entering in SharedPreference Listener");
        if (key.equals(getString(R.string.pref_sort_key))) {
            Log.d("HomeActivity", "Entering in SharedPreference Listener, sort key changes");
            String sortingCriteria = sharedPreferences.getString(getString(R.string.pref_sort_key),getString(R.string.pref_sort_popular_value));
            viewModel.userChangesSettings(sortingCriteria);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
}
