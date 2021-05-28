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

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>  {

    private ArrayList<com.amplifyframework.datastore.generated.model.Musician> musicianList;
    private ViewPagerAdapter.OnProfileClickListener profileClickListener;

    public interface OnProfileClickListener{
        void onViewClick(int position);
    }
    public void setOnProfileClickListener(OnProfileClickListener listener){
        profileClickListener = listener;
    }

    public static class ViewPagerViewHolder extends RecyclerView.ViewHolder {
        public ImageView profileImageView;
        public TextView musicianUsername;
        public TextView musicianInstruments;
        public TextView musicianGenres;
        public Button viewProfileButton;

        public ViewPagerViewHolder(@NonNull @NotNull View itemView, OnProfileClickListener listener) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.discoverPageImageView);
            musicianUsername = itemView.findViewById(R.id.discoverPageUsernameTextView);
            viewProfileButton = itemView.findViewById(R.id.discoverPageViewProfileButton);
            musicianInstruments = itemView.findViewById(R.id.discoverPageInstrumentsTextView);
            musicianGenres = itemView.findViewById(R.id.discoverPageGenreTextView);


            viewProfileButton.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onViewClick(position);
                    }
                }
            });
        }
    }

    public ViewPagerAdapter(ArrayList<com.amplifyframework.datastore.generated.model.Musician> musicians){
        musicianList = musicians;
    }

    @NonNull
    @NotNull
    @Override
    public ViewPagerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.musician_profile_page_viewpager, parent, false);
        ViewPagerViewHolder vpvh = new ViewPagerViewHolder(v, profileClickListener);
        return vpvh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewPagerAdapter.ViewPagerViewHolder holder, int position) {
        Musician current = musicianList.get(position);
        String firstAndLast = current.getFirstName() + " " + current.getLastName();

// ----------- Todo get image to populate with s3 info -------------------\\


        downloadImageFromS3(holder, current.getUsername());
        holder.musicianUsername.setText(firstAndLast);
        holder.musicianGenres.setText(current.getGenres());
        holder.musicianInstruments.setText(current.getInstruments());

    }

    @Override
    public int getItemCount() {
        return musicianList.size();
    }

    void downloadImageFromS3(ViewPagerAdapter.ViewPagerViewHolder holder, String key){
        Amplify.Storage.downloadFile(
                key,
                new File(holder.itemView.getContext().getFilesDir(), key),
                r -> {
                    holder.profileImageView.setImageBitmap(BitmapFactory.decodeFile(r.getFile().getPath()));
                },
                e -> {
                    holder.profileImageView.setImageResource(R.drawable.ic_baseline_account_circle_24);
                }
        );

    }



}
