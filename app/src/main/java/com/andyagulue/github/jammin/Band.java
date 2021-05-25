package com.andyagulue.github.jammin;

import java.util.ArrayList;

public class Band {
    private int ProfileImage;
    private String BandName;
    private ArrayList<String> Instruments;
    private ArrayList<String> Genres;
    private String Bio;
    private boolean vocalist;

    public Band(int profileImage, String bandName, ArrayList<String> instruments, ArrayList<String> genres, String bio, boolean vocalist) {
        ProfileImage = profileImage;
        BandName = bandName;
        Instruments = instruments;
        Genres = genres;
        Bio = bio;
        this.vocalist = vocalist;
    }

    public int getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(int profileImage) {
        ProfileImage = profileImage;
    }

    public String getBandName() {
        return BandName;
    }

    public void setBandName(String bandName) {
        BandName = bandName;
    }

    public ArrayList<String> getInstruments() {
        return Instruments;
    }

    public void setInstruments(ArrayList<String> instruments) {
        Instruments = instruments;
    }

    public ArrayList<String> getGenres() {
        return Genres;
    }

    public void setGenres(ArrayList<String> genres) {
        Genres = genres;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public boolean isVocalist() {
        return vocalist;
    }

    public void setVocalist(boolean vocalist) {
        this.vocalist = vocalist;
    }
}
