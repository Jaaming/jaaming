package com.andyagulue.github.jammin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        void onViewClick(int position);
        void onConnectClick(int position);
    }
    public void setOnMusicianClickListener(OnMusicianClickListener listener){
        musicianListener = listener;
    }

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder{
        public ImageView instrumentImageView;
        public ImageView favMusicianProfileImageView;
        public TextView favMusicianUsernameTextView;
        public TextView favMusicianLocationTextView;
        public Button viewFavProfileButton;
        public Button connectFavMusicianButton;

        public FavoritesViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView, OnMusicianClickListener listener) {
            super(itemView);
            instrumentImageView = itemView.findViewById(R.id.favoriteInstrumentIcon);
            favMusicianProfileImageView = itemView.findViewById(R.id.favMusicianProfileImage);
            favMusicianUsernameTextView = itemView.findViewById(R.id.favMusicianUsername);
            favMusicianLocationTextView = itemView.findViewById(R.id.favMusicianLocation);
            viewFavProfileButton = itemView.findViewById(R.id.favMusicianViewProfileButton);
            connectFavMusicianButton = itemView.findViewById(R.id.favMusicianConnectButton);

            itemView.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onMusicianClick(position);
                    }
                }
            });
            viewFavProfileButton.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onViewClick(position);
                    }
                }
            });
            connectFavMusicianButton.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onConnectClick(position);
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
