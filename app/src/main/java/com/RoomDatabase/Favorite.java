package com.RoomDatabase;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@Entity
public class Favorite {

    @NonNull
    @PrimaryKey
    String username;

    public Favorite(@NotNull String username) {
        this.username = username;
    }


    public @NotNull String getUsername() {
        return username;
    }

    public void setUsername(@NotNull String username) {
        this.username = username;
    }

}
