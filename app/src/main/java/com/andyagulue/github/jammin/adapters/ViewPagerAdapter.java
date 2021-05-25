package com.andyagulue.github.jammin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andyagulue.github.jammin.Musician;
import com.andyagulue.github.jammin.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>  {

    private ArrayList<Musician> musicianList;
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

    public ViewPagerAdapter(ArrayList<Musician> musicians){
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

        holder.profileImageView.setImageResource(current.getProfileImage());
        holder.musicianUsername.setText(current.getMusicianUsername());
    }

    @Override
    public int getItemCount() {
        return musicianList.size();
    }



}
