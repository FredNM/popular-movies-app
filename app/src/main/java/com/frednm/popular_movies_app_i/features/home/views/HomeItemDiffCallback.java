package com.frednm.popular_movies_app_i.features.home.views;

import androidx.recyclerview.widget.DiffUtil;

import com.frednm.popular_movies_app_i.data.model.Movie;

import java.util.List;

public class HomeItemDiffCallback extends DiffUtil.Callback {

    private List<Movie> oldList;
    private List<Movie> newList;

    public HomeItemDiffCallback(List<Movie> oldList, List<Movie> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
       return oldList.get(oldItemPosition) == newList.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId()== newList.get(newItemPosition).getId()
               && oldList.get(oldItemPosition).getPosterPath()== newList.get(newItemPosition).getPosterPath();
    }
}
