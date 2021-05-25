package com.andyagulue.github.jammin.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.andyagulue.github.jammin.Musician;
import com.andyagulue.github.jammin.R;
import com.andyagulue.github.jammin.adapters.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class DiscoverPage extends AppCompatActivity {
    private ViewPager2 viewpager2;
    private ViewPagerAdapter adapter;
    ArrayList<Musician> musicianArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_page);
        createMusicianList();
        buildViewPager();
    }

    public void createMusicianList(){
        musicianArrayList = new ArrayList<>();

        ArrayList<String> instruments = new ArrayList<>(Arrays.asList("Acoustic Guitar"));
        ArrayList<String> genres = new ArrayList<>(Arrays.asList("POP"));
        musicianArrayList.add(new Musician(R.drawable.ic_baseline_account_circle_24, "testEmail.com", "username1",
                "12345", "Matt", "Matty", "12/07/2021", instruments, genres, false, "Was born to shred, I'll shred till my last breath"));
        musicianArrayList.add(new Musician(R.drawable.ic_baseline_self_improvement_24, "testEmail.com", "username2",
                "12345", "Matt", "Matty", "12/07/2021", instruments, genres, false, "Was born to shred, I'll shred till my last breath"));
        musicianArrayList.add(new Musician(R.drawable.electric_guitar, "testEmail.com", "username3",
                "12345", "Matt", "Matty", "12/07/2021", instruments, genres, false, "Was born to shred, I'll shred till my last breath"));
        musicianArrayList.add(new Musician(R.drawable.keyboard, "testEmail.com", "username4",
                "12345", "Matt", "Matty", "12/07/2021", instruments, genres, false, "Was born to shred, I'll shred till my last breath"));
        musicianArrayList.add(new Musician(R.drawable.ic_baseline_account_circle_24, "testEmail.com", "username5",
                "12345", "Matt", "Matty", "12/07/2021", instruments, genres, false, "Was born to shred, I'll shred till my last breath"));
    }

    public void buildViewPager(){
        viewpager2 = findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(musicianArrayList);
        viewpager2.setAdapter(adapter);
    }

}