package com.andyagulue.github.jammin;

import java.util.ArrayList;

public class Musician {
    private int ProfileImage;
    private String MusicianEmail;
    private String MusicianUsername;
    private String MusicianPassword;
    private String MusicianFistName;
    private String MusicianLastName;
    private String MusicianDateOfBirth;
    private ArrayList<String> Instruments;
    private ArrayList<String> Genres;
    private boolean Vocalist;
    private String Bio;

    public Musician(int profileImage, String musicianEmail, String musicianUsername, String musicianPassword, String musicianFistName, String musicianLastName, String musicianDateOfBirth, ArrayList<String> instruments, ArrayList<String> genres, boolean vocalist, String bio) {
        ProfileImage = profileImage;
        MusicianEmail = musicianEmail;
        MusicianUsername = musicianUsername;
        MusicianPassword = musicianPassword;
        MusicianFistName = musicianFistName;
        MusicianLastName = musicianLastName;
        MusicianDateOfBirth = musicianDateOfBirth;
        Instruments = instruments;
        Genres = genres;
        Vocalist = vocalist;
        Bio = bio;
    }

    public int getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(int profileImage) {
        ProfileImage = profileImage;
    }

    public String getMusicianEmail() {
        return MusicianEmail;
    }

    public void setMusicianEmail(String musicianEmail) {
        MusicianEmail = musicianEmail;
    }

    public String getMusicianUsername() {
        return MusicianUsername;
    }

    public void setMusicianUsername(String musicianUsername) {
        MusicianUsername = musicianUsername;
    }

    public String getMusicianPassword() {
        return MusicianPassword;
    }

    public void setMusicianPassword(String musicianPassword) {
        MusicianPassword = musicianPassword;
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

    public String getMusicianDateOfBirth() {
        return MusicianDateOfBirth;
    }

    public void setMusicianDateOfBirth(String musicianDateOfBirth) {
        MusicianDateOfBirth = musicianDateOfBirth;
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
