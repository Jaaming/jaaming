package com.andyagulue.github.jammin;

import java.util.ArrayList;

public class Musician {
    private String MusicianFistName;
    private String MusicianLastName;
    private String Instruments;
    private String Genres;
    private boolean Vocalist;
    private String Bio;

    public Musician( String musicianFistName, String musicianLastName, String instruments, String genres, boolean vocalist, String bio) {

        MusicianFistName = musicianFistName;
        MusicianLastName = musicianLastName;
        Instruments = instruments;
        Genres = genres;
        Vocalist = vocalist;
        Bio = bio;
    }

    public String getMusicianFistName() {
        return MusicianFistName;
    }

    public void setMusicianFistName(String musicianFistName) {
        MusicianFistName = musicianFistName;
    }

    public String getMusicianLastName() {
        return MusicianLastName;
    }

    public void setMusicianLastName(String musicianLastName) {
        MusicianLastName = musicianLastName;
    }

    public String getInstruments() {
        return Instruments;
    }

    public void setInstruments(String instruments) {
        Instruments = instruments;
    }

    public String getGenres() {
        return Genres;
    }

    public void setGenres(String genres) {
        Genres = genres;
    }

    public boolean isVocalist() {
        return Vocalist;
    }

    public void setVocalist(boolean vocalist) {
        Vocalist = vocalist;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }
}
