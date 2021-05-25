package com.andyagulue.github.jammin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MyFavoritesPage extends AppCompatActivity {

    private RecyclerView favRecyclerView;
    private RecyclerView.Adapter rvAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorites_page);

        ArrayList<FavoriteMusician> favoriteMusicians = new ArrayList<>();

        favoriteMusicians.add(new FavoriteMusician(R.drawable.acoustic_guitar, R.drawable.ic_baseline_account_circle_24,
                "Test Username", "Test City, Test State"));
        favoriteMusicians.add(new FavoriteMusician(R.drawable.electric_guitar, R.drawable.ic_baseline_account_circle_24,
                "Test Username1", "Test City, Test State"));
        favoriteMusicians.add(new FavoriteMusician(R.drawable.bass_guitar, R.drawable.ic_baseline_account_circle_24,
                "Test Username2", "Test City, Test State"));
        favoriteMusicians.add(new FavoriteMusician(R.drawable.drummer, R.drawable.ic_baseline_account_circle_24,
                "Test Username3", "Test City, Test State"));
        favoriteMusicians.add(new FavoriteMusician(R.drawable.keyboard, R.drawable.ic_baseline_account_circle_24,
                "Test Username4", "Test City, Test State"));
        favoriteMusicians.add(new FavoriteMusician(R.drawable.mic, R.drawable.ic_baseline_account_circle_24,
                "Test Username5", "Test City, Test State"));
        favoriteMusicians.add(new FavoriteMusician(R.drawable.mic, R.drawable.ic_baseline_account_circle_24,
                "Test Username6", "Test City, Test State"));


        favRecyclerView = findViewById(R.id.favoriteMusicianRecyclerView);
        favRecyclerView.setHasFixedSize(true);
        rvLayoutManager = new LinearLayoutManager(this);
        rvAdapter = new FavoritesAdapter(favoriteMusicians);
        favRecyclerView.setLayoutManager(rvLayoutManager);
        favRecyclerView.setAdapter(rvAdapter);
    }
}