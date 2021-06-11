package com.andyagulue.github.jammin.adapters;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Musician;
import com.andyagulue.github.jammin.R;

import java.io.File;
import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private final ArrayList<Musician> favoriteMusiciansList;
    private OnMusicianClickListener musicianListener;

    public interface OnMusicianClickListener{
        void onMusicianClick(int position);
        void onRemoveClick(int position);
        void onConnectClick(int position);
    }
    public void setOnMusicianClickListener(OnMusicianClickListener listener){
        musicianListener = listener;
    }

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder{
        public ImageView favMusicianProfileImageView;
        public TextView favMusicianFullNameTextView;
        public ImageView messageIcon;
        public ImageView removeIcon;
        public ImageView micIcon;
        public ImageView acousticGuitarIcon;
        public ImageView electricGuitarIcon;
        public ImageView bassIcon;
        public ImageView drumIcon;
        public ImageView keysIcon;

        public FavoritesViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView, OnMusicianClickListener listener) {
            super(itemView);

            favMusicianProfileImageView = itemView.findViewById(R.id.favavMusicianProfileImage);
            favMusicianFullNameTextView = itemView.findViewById(R.id.favMusicainFullName);
            messageIcon = itemView.findViewById(R.id.favMessageIcon);
            removeIcon = itemView.findViewById(R.id.favRemoveIcon);
            micIcon = itemView.findViewById(R.id.favMicIcon);
            acousticGuitarIcon = itemView.findViewById(R.id.favAcousticGuitarIcon);
            electricGuitarIcon = itemView.findViewById(R.id.favElectricGuitarIcon);
            bassIcon = itemView.findViewById(R.id.favBassIcon);
            drumIcon = itemView.findViewById(R.id.favDrumIcon);
            keysIcon = itemView.findViewById(R.id.favKeysIcon);

            itemView.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onMusicianClick(position);
                    }
                }
            });
            messageIcon.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onConnectClick(position);
                    }
                }
            });
            removeIcon.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onRemoveClick(position);
                    }
                }
            });
        }
    }

    public FavoritesAdapter(ArrayList<Musician> favoriteMusicians){
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
        Musician currentMusician = favoriteMusiciansList.get(position);
        String fullName = currentMusician.getFirstName() + " " + currentMusician.getLastName();

        downloadImageFromS3(holder,currentMusician.getUsername());
        holder.favMusicianFullNameTextView.setText(fullName);
        if(currentMusician.getVocalist()) holder.micIcon.setVisibility(View.VISIBLE);
        if(currentMusician.getInstruments().contains("Acoustic Guitar")) holder.acousticGuitarIcon.setVisibility(View.VISIBLE);
        if(currentMusician.getInstruments().contains("Electric Guitar")) holder.electricGuitarIcon.setVisibility(View.VISIBLE);
        if(currentMusician.getInstruments().contains("Bass Guitar")) holder.bassIcon.setVisibility(View.VISIBLE);
        if(currentMusician.getInstruments().contains("Drums")) holder.drumIcon.setVisibility(View.VISIBLE);
        if(currentMusician.getInstruments().contains("Keyboard")) holder.keysIcon.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return favoriteMusiciansList.size();
    }

    void downloadImageFromS3(FavoritesAdapter.FavoritesViewHolder holder, String key){
        Amplify.Storage.downloadFile(
                key,
                new File(holder.itemView.getContext().getFilesDir(), key),
                r -> {
                    holder.favMusicianProfileImageView.setImageBitmap(BitmapFactory.decodeFile(r.getFile().getPath()));
                },
                e -> {
                    holder.favMusicianProfileImageView.setImageResource(R.drawable.ic_baseline_account_circle_24);
                }
        );

    }
}


