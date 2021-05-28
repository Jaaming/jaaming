package com.andyagulue.github.jammin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.andyagulue.github.jammin.FavoriteMusician;
import com.andyagulue.github.jammin.R;
import com.andyagulue.github.jammin.adapters.FavoritesAdapter;

import java.util.ArrayList;

public class MyFavoritesPage extends AppCompatActivity {
    String TAG = "myFavoritesPage";

    String userName;

    private RecyclerView favRecyclerView;
    private FavoritesAdapter rvAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;
    ArrayList<FavoriteMusician> favoriteMusicians;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorites_page);

        AuthUser authUser = Amplify.Auth.getCurrentUser();
        userName = authUser.getUsername();

        createFavoriteMusicianList();
        buildRecyclerView();




//        findViewById(R.id.favMusicianViewProfileButton).setOnClickListener(v -> {
////            Toast.makeText(this, "You want to view this profile", Toast.LENGTH_SHORT).show();
//        });

    }

    public void createFavoriteMusicianList(){
        favoriteMusicians = new ArrayList<>();

        favoriteMusicians.add(new FavoriteMusician(R.drawable.electric_guitar, R.drawable.michelle_holder,
                "Michelle Holder", "Seattle, WA"));
        favoriteMusicians.add(new FavoriteMusician(R.drawable.electric_guitar, R.drawable.joey_shmoey,
                "Joey Shmoey", "Little Rock, AR"));
        favoriteMusicians.add(new FavoriteMusician(R.drawable.bass_guitar, R.drawable.mila_kunis,
                "Mila Kunis", "Los Angeles, CA"));
        favoriteMusicians.add(new FavoriteMusician(R.drawable.drummer, R.drawable.mon_day,
                "Mon Day", "Chicago, IL"));
        favoriteMusicians.add(new FavoriteMusician(R.drawable.keyboard, R.drawable.quinn_grover,
                "Quinn Grover", "Seattle, WA"));
        favoriteMusicians.add(new FavoriteMusician(R.drawable.mic, R.drawable.lady_love,
                "Lady Love", "Chicago, IL"));
        favoriteMusicians.add(new FavoriteMusician(R.drawable.mic, R.drawable.andy_lopez,
                "Andy Lopez", "San Antonio, TX"));
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
                Toast.makeText(MyFavoritesPage.this, "You clicked on a musician " + position, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onMusicianClick: " + favoriteMusicians.get(position).getFavMusicianUsername());


            }

            @Override
            public void onViewClick(int position) {
                Toast.makeText(MyFavoritesPage.this, "You want to view this profile " + position, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onMusicianClick: " + favoriteMusicians.get(position).getFavMusicianUsername());
            }

            @Override
            public void onConnectClick(int position) {
                Toast.makeText(MyFavoritesPage.this, "You want to connect with this user " + position, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onMusicianClick: " + favoriteMusicians.get(position).getFavMusicianUsername());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(this, "clicked profile", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), PublicMusicianProfilePage.class );
                intent.putExtra("username", userName);
                startActivity(intent);
                return true;
            case R.id.item2:
                Toast.makeText(this, "clicked home", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getApplicationContext(), DiscoverPage.class );
                startActivity(intent2);
                return true;
            case R.id.item3:
                Toast.makeText(this, "clicked favorites", Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(getApplicationContext(), MyFavoritesPage.class );
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}