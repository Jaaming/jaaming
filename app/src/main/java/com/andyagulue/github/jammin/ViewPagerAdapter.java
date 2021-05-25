package com.andyagulue.github.jammin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>  {

    private ArrayList<Musician> musicianList;

    public static class ViewPagerViewHolder extends RecyclerView.ViewHolder implements com.andyagulue.github.jammin.ViewPagerViewHolder {
        public ImageView profileImageView;
        public TextView musicianUsername;
        public TextView musicianInstruments;
        public TextView musicianGenres;
        public Button viewProfileButton;

        public ViewPagerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.discoverPageImageView);
            musicianUsername = itemView.findViewById(R.id.discoverPageUsernameTextView);
            viewProfileButton = itemView.findViewById(R.id.discoverPageViewProfileButton);
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
        ViewPagerViewHolder vpvh = new ViewPagerViewHolder(v);
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
