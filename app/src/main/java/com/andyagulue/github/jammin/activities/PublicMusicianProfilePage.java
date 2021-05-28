package com.andyagulue.github.jammin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Musician;
import com.andyagulue.github.jammin.R;

import java.io.File;

public class PublicMusicianProfilePage extends AppCompatActivity {

    String TAG = "publicMusicianProfilPage";
    String userName;

    ImageView profileImage;
    ImageView vocalistImage;
    ImageView keyboardIcon;
    ImageView acousticGuitarIcon;
    ImageView electricGuitarIcon;
    ImageView bassGuitarIcon;
    ImageView drummerIcon;

    TextView firstLastName;
    TextView instruments;
    TextView genres;
    TextView bio;
    TextView addToFavorites;
    Musician musician;
    Handler publicProfilePageHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_musician_profile_page);

        AuthUser authUser = Amplify.Auth.getCurrentUser();
        userName = authUser.getUsername();

        profileImage = findViewById(R.id.publicMusicianProfileImage);
        vocalistImage = findViewById(R.id.isVocalistIcon);
        firstLastName = findViewById(R.id.publicProfileFirstLastName);
        instruments = findViewById(R.id.publicMusicianIntruments);
        genres = findViewById(R.id.publicMusicianGenres);
        bio = findViewById(R.id.publicMusicianBio);
        addToFavorites = findViewById(R.id.addToFavoritesClickable);
        keyboardIcon = findViewById(R.id.keyboardIcon);
        acousticGuitarIcon = findViewById(R.id.acousticGuitarIcon);
        electricGuitarIcon = findViewById(R.id.electricGuitarIcon);
        bassGuitarIcon = findViewById(R.id.bassIcon);
        drummerIcon = findViewById(R.id.drummerIcon);


        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

//        if(username != null) {

            Amplify.API.query(
                    ModelQuery.list(Musician.class, Musician.USERNAME.eq(username)),
                    response -> {
                        musician = response.getData().iterator().next();
                        publicProfilePageHandler.sendEmptyMessage(124);

                    },
                    error ->{}
            );
//        }else{
//            Amplify.API.query(
//                    ModelQuery.list(Musician.class, Musician.USERNAME.eq(authUser.getUsername())),
//                    r -> {
//                        musician = r.getData().iterator().next();
//                        publicProfilePageHandler.sendEmptyMessage(124);
//                    },
//                    e -> {}
//            );
//        }

        publicProfilePageHandler = new Handler(this.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == 124){
                    String fullName = musician.getFirstName() + " " + musician.getLastName();
                    if(musician.vocalist) vocalistImage.setVisibility(View.VISIBLE);
                    downloadImageFromS3(username);
                    firstLastName.setText(fullName);
                    instruments.setText(musician.getInstruments());
                    if(musician.getInstruments().contains("Keyboard")) keyboardIcon.setVisibility(View.VISIBLE);
                    if(musician.getInstruments().contains("Acoustic Guitar")) acousticGuitarIcon.setVisibility(View.VISIBLE);
                    if(musician.getInstruments().contains("Electric Guitar")) electricGuitarIcon.setVisibility(View.VISIBLE);
                    if(musician.getInstruments().contains("Bass")) bassGuitarIcon.setVisibility(View.VISIBLE);
                    if(musician.getInstruments().contains("Drums")) drummerIcon.setVisibility(View.VISIBLE);
                    genres.setText(musician.getGenres());
                    bio.setText(musician.getBio());
                }
            }
        };





    }
    void downloadImageFromS3(String key) {
        Amplify.Storage.downloadFile(
                key,
                new File(getApplicationContext().getFilesDir(), key),
                r -> {
                    profileImage.setImageBitmap(BitmapFactory.decodeFile(r.getFile().getPath()));
                },
                e -> {
                    profileImage.setImageResource(R.drawable.ic_baseline_account_circle_24);
                }
        );
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