package com.andyagulue.github.jammin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.viewpager2.widget.ViewPager2;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;

import com.andyagulue.github.jammin.Musician;
import com.andyagulue.github.jammin.R;
import com.andyagulue.github.jammin.adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DiscoverPage extends AppCompatActivity {
    String TAG = "discover Page";
    String userName;
    ImageView vocalistImage;
    Musician musician;

    private ViewPager2 viewpager2;
    private ViewPagerAdapter adapter;
    ArrayList<com.amplifyframework.datastore.generated.model.Musician> musicianArrayList;
    ArrayList<Musician> displayMusiciansArrayList;
    Handler discoverPageHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_page);
//        createMusicianList();
        populateDiscoverMusicians();
        buildViewPager();
        AuthUser authUser = Amplify.Auth.getCurrentUser();
        userName = authUser.getUsername();

//        vocalistImage = findViewById(R.id.viewPagerVocalistIcon);





    }


    public void createMusicianList(){
        musicianArrayList = new ArrayList<>();

//        musicianArrayList.add(new Musician( "Test Firstname", "Test Last Name", "Acoustic Guitar",
//                "Folk", false, "I love to rock"));
//        musicianArrayList.add(new Musician( "Test Firstname2", "Test Last Name2", "Acoustic Guitar",
//                "Folk", false, "I love to rock"));
//        musicianArrayList.add(new Musician( "Test Firstname3", "Test Last Name3", "Acoustic Guitar",
//                "Folk", false, "I love to rock"));
//        musicianArrayList.add(new Musician( "Test Firstname4", "Test Last Name4", "Acoustic Guitar",
//                "Folk", false, "I love to rock"));
//        musicianArrayList.add(new Musician( "Test Firstname5", "Test Last Name5", "Acoustic Guitar",
//                "Folk", false, "I love to rock"));

    }

    public void populateDiscoverMusicians(){
        musicianArrayList = new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(com.amplifyframework.datastore.generated.model.Musician.class),
                r -> {
                    Log.i(TAG, "populateDiscoverMusicians: ");
                    for(com.amplifyframework.datastore.generated.model.Musician musician: r.getData()) {
                        Log.i(TAG, "populateDiscoverMusicians: " + r.getData().getItems() + 1);
                        musicianArrayList.add(musician);
                    }
                    discoverPageHandler.sendEmptyMessage(123);
                },
                e -> {
                    Log.d(TAG, "populateDiscoverMusicians error: " + e);
//                     createMusicianList();
                }
        );
//
    }

    public void buildViewPager(){
        viewpager2 = findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(musicianArrayList);
        viewpager2.setAdapter(adapter);

        discoverPageHandler = new Handler(this.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == 123){
                    viewpager2.getAdapter().notifyDataSetChanged();

                }
            }
        };

        adapter.setOnProfileClickListener(new ViewPagerAdapter.OnProfileClickListener() {
            @Override
            public void onViewClick(int position) {
                Toast.makeText(DiscoverPage.this, "You want to view this profile " + position + musicianArrayList.get(position).getUsername(),
                        Toast.LENGTH_SHORT).show();
                String username = musicianArrayList.get(position).getUsername();
                Intent intent = new Intent(DiscoverPage.this, PublicMusicianProfilePage.class);
                intent.putExtra("username", username);
                startActivity(intent);


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