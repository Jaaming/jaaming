package com.andyagulue.github.jammin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private ArrayList<FavoriteMusician> favoriteMusiciansList;

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder{
        public ImageView instrumentImageView;
        public ImageView favMusicianProfileImageView;
        public TextView favMusicianUsernameTextView;
        public TextView favMusicianLocationTextView;

        public FavoritesViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            instrumentImageView = itemView.findViewById(R.id.favoriteInstrumentIcon);
            favMusicianProfileImageView = itemView.findViewById(R.id.favMusicianProfileImage);
            favMusicianUsernameTextView = itemView.findViewById(R.id.favMusicianUsername);
            favMusicianLocationTextView = itemView.findViewById(R.id.favMusicianLocation);
        }
    }

    public FavoritesAdapter(ArrayList<FavoriteMusician> favoriteMusicians){
        favoriteMusiciansList = favoriteMusicians;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_musician_cardview, parent, false);
        FavoritesViewHolder fvh = new FavoritesViewHolder(v);
        return fvh;
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull FavoritesAdapter.FavoritesViewHolder holder, int position) {
        FavoriteMusician currentMusician = favoriteMusiciansList.get(position);

        holder.instrumentImageView.setImageResource(currentMusician.getInstrumentImage());
        holder.favMusicianProfileImageView.setImageResource(currentMusician.getMusicianProfileImage());
        holder.favMusicianUsernameTextView.setText(currentMusician.getFavMusicianUsername());
        holder.favMusicianLocationTextView.setText(currentMusician.getFavMusicianLocation());
    }

    @Override
    public int getItemCount() {
        return favoriteMusiciansList.size();
    }
}
