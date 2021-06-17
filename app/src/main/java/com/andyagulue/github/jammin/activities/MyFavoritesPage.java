package com.andyagulue.github.jammin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.RoomDatabase.Favorite;
import com.RoomDatabase.FavoriteDatabase;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Musician;
import com.andyagulue.github.jammin.FavoriteMusician;
import com.andyagulue.github.jammin.R;
import com.andyagulue.github.jammin.adapters.FavoritesAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyFavoritesPage extends AppCompatActivity {
    String TAG = "myFavoritesPage";

    String userName;
    ArrayList<Musician> favoriteMusicians;
    FavoriteDatabase favoriteDatabase;
    Handler favoritesPageHandler;
    List<String> favorites;

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
        favoriteDatabase = Room.databaseBuilder(getApplicationContext(), FavoriteDatabase.class, userName + "'s Favorites")
                .allowMainThreadQueries()
                .build();
        favorites = favoriteDatabase.favoriteDAO().findAll();
        Amplify.API.query(
                ModelQuery.list(Musician.class),
                r ->{
                   for(Musician m : r.getData().getItems()){
                       if(favorites.toString().contains(m.getUsername())){
                           favoriteMusicians.add(m);
                           Log.i(TAG, "createFavoriteMusicianList: " + m.getUsername());
                       }
                   }
                   favoritesPageHandler.sendEmptyMessage(131);
                },
                e ->{}
        );




//        favoriteMusicians.add(new FavoriteMusician(R.drawable.electric_guitar, R.drawable.michelle_holder,
//                "Michelle Holder", "Seattle, WA"));
//        favoriteMusicians.add(new FavoriteMusician(R.drawable.electric_guitar, R.drawable.joey_shmoey,
//                "Joey Shmoey", "Little Rock, AR"));
//        favoriteMusicians.add(new FavoriteMusician(R.drawable.bass_guitar, R.drawable.mila_kunis,
//                "Mila Kunis", "Los Angeles, CA"));
//        favoriteMusicians.add(new FavoriteMusician(R.drawable.drummer, R.drawable.mon_day,
//                "Mon Day", "Chicago, IL"));
//        favoriteMusicians.add(new FavoriteMusician(R.drawable.keyboard, R.drawable.quinn_grover,
//                "Quinn Grover", "Seattle, WA"));
//        favoriteMusicians.add(new FavoriteMusician(R.drawable.mic, R.drawable.lady_love,
//                "Lady Love", "Chicago, IL"));
//        favoriteMusicians.add(new FavoriteMusician(R.drawable.mic, R.drawable.andy_lopez,
//                "Andy Lopez", "San Antonio, TX"));
    }

    public void buildRecyclerView(){
        RecyclerView favRecyclerView = findViewById(R.id.favoriteMusicianRecyclerView);
        favRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(this);
        FavoritesAdapter rvAdapter = new FavoritesAdapter(favoriteMusicians);
        favRecyclerView.setLayoutManager(rvLayoutManager);
        favRecyclerView.setAdapter(rvAdapter);

        rvAdapter.setOnMusicianClickListener(new FavoritesAdapter.OnMusicianClickListener() {
            @Override
            public void onMusicianClick(int position) {
                Toast.makeText(MyFavoritesPage.this, "You clicked on a musician " + position, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onMusicianClick: " + favoriteMusicians.get(position).getUsername());
                String username = favoriteMusicians.get(position).getUsername();
                Intent intent = new Intent(MyFavoritesPage.this, PublicMusicianProfilePage.class);
                intent.putExtra("username", username);
                startActivity(intent);

            }

            @Override
            public void onRemoveClick(int position) {
                Toast.makeText(MyFavoritesPage.this, "You want to view this profile " + position, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onMusicianClick: " + favoriteMusicians.get(position).getUsername());
                String musicianToBeRemoved = favoriteMusicians.get(position).getUsername();
                 List<Favorite> favoriteToBeRemoved = favoriteDatabase.favoriteDAO().findByUsername(musicianToBeRemoved);
                Log.i(TAG, "onRemoveClick: " + favoriteToBeRemoved.get(0).getUsername());
                favoriteDatabase.favoriteDAO().delete(favoriteToBeRemoved.get(0));
                favoriteMusicians.remove(position);
                rvAdapter.notifyItemRemoved(position);
                Log.i(TAG, "onRemoveClick: " + favorites);
            }

            @Override
            public void onConnectClick(int position) {
                Toast.makeText(MyFavoritesPage.this, "You want to connect with this user " + position, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onMusicianClick: " + favoriteMusicians.get(position).getUsername());
                Intent intent = new Intent(MyFavoritesPage.this, ChatPage.class);
                intent.putExtra("username", favoriteMusicians.get(position).getUsername());
                startActivity(intent);
            }
        });

        favoritesPageHandler = new Handler(this.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == 131){
                    Objects.requireNonNull(favRecyclerView.getAdapter()).notifyDataSetChanged();
                }
            }
        };
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