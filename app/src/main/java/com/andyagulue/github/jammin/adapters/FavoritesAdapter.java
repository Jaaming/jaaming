package com.andyagulue.github.jammin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andyagulue.github.jammin.FavoriteMusician;
import com.andyagulue.github.jammin.R;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private ArrayList<FavoriteMusician> favoriteMusiciansList;
    private OnMusicianClickListener musicianListener;

    public interface OnMusicianClickListener{
        void onMusicianClick(int position);
    }
    public void setOnMusicianClickListener(OnMusicianClickListener listener){
        musicianListener = listener;
    }

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder{
        public ImageView instrumentImageView;
        public ImageView favMusicianProfileImageView;
        public TextView favMusicianUsernameTextView;
        public TextView favMusicianLocationTextView;

        public FavoritesViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView, OnMusicianClickListener listener) {
            super(itemView);
            instrumentImageView = itemView.findViewById(R.id.favoriteInstrumentIcon);
            favMusicianProfileImageView = itemView.findViewById(R.id.favMusicianProfileImage);
            favMusicianUsernameTextView = itemView.findViewById(R.id.favMusicianUsername);
            favMusicianLocationTextView = itemView.findViewById(R.id.favMusicianLocation);

            itemView.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onMusicianClick(position);
                    }
                }
            });
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
        FavoritesViewHolder fvh = new FavoritesViewHolder(v, musicianListener);
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
