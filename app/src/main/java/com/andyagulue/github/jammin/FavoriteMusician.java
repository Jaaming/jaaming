package com.andyagulue.github.jammin;

public class FavoriteMusician {
    private int instrumentImage;
    private int musicianProfileImage;
    private String favMusicianUsername;
    private String favMusicianLocation;

    public FavoriteMusician(int instrumentImage, int musicianProfileImage, String favMusicianUsername, String favMusicianLocation) {
        this.instrumentImage = instrumentImage;
        this.musicianProfileImage = musicianProfileImage;
        this.favMusicianUsername = favMusicianUsername;
        this.favMusicianLocation = favMusicianLocation;
    }

    public int getInstrumentImage() {
        return instrumentImage;
    }

    public int getMusicianProfileImage() {
        return musicianProfileImage;
    }

    public String getFavMusicianUsername() {
        return favMusicianUsername;
    }

    public String getFavMusicianLocation() {
        return favMusicianLocation;
    }
}
