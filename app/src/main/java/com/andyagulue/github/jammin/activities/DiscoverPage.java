package com.andyagulue.github.jammin.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Musician;
import com.andyagulue.github.jammin.R;
import com.andyagulue.github.jammin.adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DiscoverPage extends AppCompatActivity {
    String TAG = "Discover Page";

    private ViewPager2 viewpager2;
    private ViewPagerAdapter adapter;
    ArrayList<com.andyagulue.github.jammin.Musician> musicianArrayList;
    ArrayList<Musician> displayMusiciansArrayList;
//    ArrayList<String> instruments;
//    ArrayList<String> genres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_page);
//        createMusicianList();
        populateDiscoverMusicians();
        buildViewPager();

    }



//    public void createMusicianList(){
//        musicianArrayList = new ArrayList<>();
//
//        ArrayList<String> instruments = new ArrayList<>(Arrays.asList("Acoustic Guitar"));
//        ArrayList<String> genres = new ArrayList<>(Arrays.asList("POP"));
//        musicianArrayList.add(new Musician(R.drawable.ic_baseline_account_circle_24, "testEmail.com", "username1",
//                "12345", "Matt", "Matty", "12/07/2021", instruments, genres, false, "Was born to shred, I'll shred till my last breath"));
//        musicianArrayList.add(new Musician(R.drawable.ic_baseline_self_improvement_24, "testEmail.com", "username2",
//                "12345", "Matt", "Matty", "12/07/2021", instruments, genres, false, "Was born to shred, I'll shred till my last breath"));
//        musicianArrayList.add(new Musician(R.drawable.electric_guitar, "testEmail.com", "username3",
//                "12345", "Matt", "Matty", "12/07/2021", instruments, genres, false, "Was born to shred, I'll shred till my last breath"));
//        musicianArrayList.add(new Musician(R.drawable.keyboard, "testEmail.com", "username4",
//                "12345", "Matt", "Matty", "12/07/2021", instruments, genres, false, "Was born to shred, I'll shred till my last breath"));
//        musicianArrayList.add(new Musician(R.drawable.ic_baseline_account_circle_24, "testEmail.com", "username5",
//                "12345", "Matt", "Matty", "12/07/2021", instruments, genres, false, "Was born to shred, I'll shred till my last breath"));
//    }

    public void buildViewPager(){
        viewpager2 = findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(musicianArrayList);
        viewpager2.setAdapter(adapter);

        adapter.setOnProfileClickListener(new ViewPagerAdapter.OnProfileClickListener() {
            @Override
            public void onViewClick(int position) {
                Toast.makeText(DiscoverPage.this, "You want to view this profile " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void populateDiscoverMusicians(){
        musicianArrayList = new ArrayList<>();
         Amplify.API.query(
                 ModelQuery.list(Musician.class),
                 r -> {
                     Log.i(TAG, "populateDiscoverMusicians: ");
                     for(Musician musician: r.getData()) {
                         if(r.getData().getItems().iterator() != null) Log.i(TAG, "populateDiscoverMusicians: " + r.getData().getItems());


//                         instruments = new ArrayList<>(Collections.singletonList(musician.getInstruments()));
//                         genres = new ArrayList<>(Collections.singletonList(musician.getGenres()));

//                         ArrayList<String> instruments = new ArrayList<>(Arrays.asList("Acoustic Guitar"));
//                         ArrayList<String> genres = new ArrayList<>(Arrays.asList("POP"));
//
//                         musicianArrayList.add(new com.andyagulue.github.jammin.Musician(R.drawable.drummer,
//                                 "", musician.getUsername(), "", musician.getFirstName(),
//                                 musician.getLastName(), "", instruments,
//                                 genres, musician.getVocalist(), ""));
                     }
                 },
                 e -> {
                     Log.d(TAG, "populateDiscoverMusicians: " + e);
                 }
         );

    }


}