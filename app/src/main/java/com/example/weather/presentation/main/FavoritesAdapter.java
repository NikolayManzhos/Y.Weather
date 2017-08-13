package com.example.weather.presentation.main;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.domain.models.FavoritePlace;
import com.example.weather.presentation.di.ActivityContext;
import com.example.weather.presentation.di.scope.PerActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@PerActivity
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder> {

    private List<FavoritePlace> items;
    private OnFavoriteClickListener listener;
    private Context context;

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.favoriteContainer)
        CardView favoriteContainer;

        @BindView(R.id.cityName)
        TextView cityName;

        @BindView(R.id.deleteFavorite)
        ImageButton deleteFavorite;

        FavoriteViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Inject
    FavoritesAdapter(@ActivityContext Context context) {
        this.context = context;
        items = new ArrayList<>();
    }

    @Override
    public FavoritesAdapter.FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vFavorites = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        FavoriteViewHolder vh = new FavoriteViewHolder(vFavorites);
        vh.favoriteContainer.setOnClickListener(view -> listener.onFavoriteClick(items.get(vh.getAdapterPosition())));
        vh.deleteFavorite.setOnClickListener(view -> {
            int aPosition = vh.getAdapterPosition();
            listener.onFavoriteRemoveClick(aPosition, items.get(aPosition));
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.FavoriteViewHolder holder, int position) {
        int aPosition = holder.getAdapterPosition();
        holder.cityName.setText(items.get(aPosition).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setData(List<FavoritePlace> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void setOnFavoriteClickListener(OnFavoriteClickListener listener) {
        this.listener = listener;
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    interface OnFavoriteClickListener {
        void onFavoriteRemoveClick(int position, FavoritePlace favoritePlace);
        void onFavoriteClick(FavoritePlace favoritePlace);
    }
}

