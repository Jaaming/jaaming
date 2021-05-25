package com.andyagulue.github.jammin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class MyFavoritesPage extends AppCompatActivity {

    private RecyclerView favRecyclerView;
    private FavoritesAdapter rvAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;
    ArrayList<FavoriteMusician> favoriteMusicians;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorites_page);

        createFavoriteMusicianList();
        buildRecyclerView();




//        findViewById(R.id.favMusicianViewProfileButton).setOnClickListener(v -> {
////            Toast.makeText(this, "You want to view this profile", Toast.LENGTH_SHORT).show();
//        });

    }

    public void createFavoriteMusicianList(){
        favoriteMusicians = new ArrayList<>();

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
    }

    public void buildRecyclerView(){
        favRecyclerView = findViewById(R.id.favoriteMusicianRecyclerView);
        favRecyclerView.setHasFixedSize(true);
        rvLayoutManager = new LinearLayoutManager(this);
        rvAdapter = new FavoritesAdapter(favoriteMusicians);
        favRecyclerView.setLayoutManager(rvLayoutManager);
        favRecyclerView.setAdapter(rvAdapter);

        rvAdapter.setOnMusicianClickListener(new FavoritesAdapter.OnMusicianClickListener() {
            @Override
            public void onMusicianClick(int position) {
                Toast.makeText(MyFavoritesPage.this, "You clicked on a musician" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}