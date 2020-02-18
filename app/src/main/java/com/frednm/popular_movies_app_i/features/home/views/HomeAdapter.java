package com.frednm.popular_movies_app_i.features.home.views;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.frednm.popular_movies_app_i.R;
import com.frednm.popular_movies_app_i.data.model.Movie;
import com.frednm.popular_movies_app_i.features.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Question: Why the HomeAdapter constructor here only needs viewModel !? How to decide what to pass to the Adapter constructor !??
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private List<Movie> movies = new ArrayList<>();
    private HomeViewModel viewModel;

    public HomeAdapter(HomeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.bindTo(movies.get(position), viewModel);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public void updateData(List<Movie> items) {
        HomeItemDiffCallback diffCallback = new HomeItemDiffCallback(movies, items);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        movies.clear();
        movies.addAll(items);
        diffResult.dispatchUpdatesTo(this);
    }
}
