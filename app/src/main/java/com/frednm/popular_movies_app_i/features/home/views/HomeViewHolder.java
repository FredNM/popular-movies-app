package com.frednm.popular_movies_app_i.features.home.views;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.frednm.popular_movies_app_i.data.model.Movie;
import com.frednm.popular_movies_app_i.databinding.ItemHomeBinding;
import com.frednm.popular_movies_app_i.features.home.HomeViewModel;

public class HomeViewHolder extends RecyclerView.ViewHolder {

    private ItemHomeBinding binding;

    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = ItemHomeBinding.bind(itemView);
    }

    protected void bindTo(Movie movie, HomeViewModel viewModel) {
        binding.setMovie(movie);
        binding.setViewmodel(viewModel);
    }
}
