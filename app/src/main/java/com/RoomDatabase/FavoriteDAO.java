package com.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavoriteDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Favorite favorite);

    @Query("SELECT * FROM Favorite")
    List<String> findAll();

    @Query("SELECT * FROM Favorite WHERE username = :username")
    List<Favorite> findByUsername(String username);

    @Update
    void update(Favorite favorite);

    @Delete
    void delete(Favorite favorite);
}
