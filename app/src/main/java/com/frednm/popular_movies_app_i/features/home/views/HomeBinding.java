package com.frednm.popular_movies_app_i.features.home.views;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.frednm.popular_movies_app_i.R;
import com.frednm.popular_movies_app_i.data.model.Movie;
import com.frednm.popular_movies_app_i.data.repository.utils.Resource;

import java.util.List;

public class HomeBinding {

    public static final String IMAGE_ENDPOINT_PREFIX = "https://image.tmdb.org/t/p/w500";

    static RequestOptions requestOptions = new RequestOptions()
            .error(R.drawable.ic_error_image_24dp)
            .diskCacheStrategy(DiskCacheStrategy.ALL);


    @BindingAdapter("app:imageUrl")
    public static void loadImage(ImageView view, String posterPath) {
        String url = IMAGE_ENDPOINT_PREFIX+posterPath;
        Glide.with(view.getContext()).load(url).apply(requestOptions).into(view);
    }

    @BindingAdapter("app:showWhenLoading")
    public static <T> void showWhenLoadingData(SwipeRefreshLayout view, Resource<T> resource ) {
        Log.d("HomeBinding", "Resource: "+resource);
        if (resource != null) {
            view.setRefreshing(resource.getStatus() == Resource.Status.LOADING);
        }
    }

    @BindingAdapter("items")
    public static void setItems (RecyclerView recyclerView,@NonNull Resource<List<Movie>> resource) {
        if(!(resource.getData() == null)) {
            if (recyclerView.getAdapter() instanceof HomeAdapter) {
                ((HomeAdapter) recyclerView.getAdapter()).updateData(resource.getData());
            }
        }
    }

    @BindingAdapter("showWhenEmptyList")
    public static void showMessageErrorWhenEmptyList(View view, Resource<List<Movie>> resource) {
        if (resource != null) {
            Boolean bool = resource.getStatus() == Resource.Status.ERROR
                    && resource.getData() != null
                    && resource.getData().isEmpty() ;
            view.setVisibility(bool ? View.VISIBLE : View.GONE);
        }
    }
}
