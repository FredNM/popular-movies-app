package com.frednm.popular_movies_app_i.features.detail;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.frednm.popular_movies_app_i.R;
import com.frednm.popular_movies_app_i.common.base.BaseActivity;
import com.frednm.popular_movies_app_i.common.base.BaseViewModel;
import com.frednm.popular_movies_app_i.databinding.ActivityDetailBinding;
import com.frednm.popular_movies_app_i.features.detail.utils.TextCreator;

import javax.inject.Inject;

public class DetailActivity extends BaseActivity {

    @Inject ViewModelProvider.Factory viewModelFactory;
    private DetailViewModel viewModel;

    ActivityDetailBinding binding;
    private TextCreator textCreator = new TextCreator();
    public static final String EXTRA_MOVIEID = "extra_movieId";
    private static final int DEFAULT_MOVIEID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(DetailViewModel.class);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);
        binding.setTextCreator(textCreator);

        Integer movieId = getIntent().getIntExtra(EXTRA_MOVIEID, DEFAULT_MOVIEID);
        viewModel.loadDataWhenActivityStarts(movieId);
    }

    public BaseViewModel getViewModel() {
        return viewModel ;
    }
}
