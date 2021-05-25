package com.andyagulue.github.jammin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.logInNavButton).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LogInPage.class);
            startActivity(intent);
        });

        findViewById(R.id.signUpNavButton).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignUpPage.class);
            startActivity(intent);
        });
//====================Temp buttons, will delete later===================================
        findViewById(R.id.tempConfrimButton).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ConfirmationPage.class);
            startActivity(intent);
        });

        findViewById(R.id.tempbandProfileButton).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BandCreationPage.class);
            startActivity(intent);
        });

        findViewById(R.id.tempProfilePageButton).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MyMusicianProfilePage.class);
            startActivity(intent);
        });

        findViewById(R.id.tempFavesButton).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MyFavoritesPage.class);
            startActivity(intent);
        });

        findViewById(R.id.tempDiscoverButton).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DiscoverPage.class);
            startActivity(intent);
        });

        findViewById(R.id.mPublicProfilePage).setOnClickListener(v ->{
            Intent intent = new Intent(getApplicationContext(),PublicMusicianProfilePage.class);
            startActivity(intent);
        });
        //========================End temp buttons to be deleted=================================
    }


}