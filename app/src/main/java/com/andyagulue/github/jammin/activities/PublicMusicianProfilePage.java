package com.andyagulue.github.jammin.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.RoomDatabase.Favorite;
import com.RoomDatabase.FavoriteDatabase;
import com.amplifyframework.api.graphql.GraphQLOperation;
import com.amplifyframework.api.graphql.GraphQLRequest;
import com.amplifyframework.api.graphql.MutationType;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Band;
import com.amplifyframework.datastore.generated.model.Musician;
import com.andyagulue.github.jammin.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.login.LoginException;

import static com.amplifyframework.api.graphql.model.ModelMutation.update;

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
    ImageButton addToFavorites;
    Musician musician;
    Musician currentUser;
    Handler publicProfilePageHandler;
    FavoriteDatabase favoriteDatabase;
    List<String> favorites;
    View publicMusicianProfilePage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_musician_profile_page);

        favoriteDatabase = Room.databaseBuilder(getApplicationContext(),FavoriteDatabase.class, "My_Favorites")
                .allowMainThreadQueries()
                .build();



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
        publicMusicianProfilePage = findViewById(R.id.publicMusicianProfilePage);




        Band defaultBand = Band.builder()
                .name("The Rockers")
                .instruments("Default")
                .genres("String")
                .bio("Default")
                .vocalist(true)
                .build();


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
        Amplify.API.query(
                ModelQuery.list(Musician.class, Musician.USERNAME.eq(userName)),
                response -> {
                    currentUser = response.getData().iterator().next();
                    Log.i(TAG, "this is the currentUser" + currentUser);
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
        favorites = favoriteDatabase.favoriteDAO().findAll();
        if(favorites.toString().contains(username))addToFavorites.setColorFilter(getResources().getColor(R.color.greenPigment, getTheme()));
        if(username.equals(userName)) addToFavorites.setVisibility(View.INVISIBLE);



        addToFavorites.setOnClickListener(v -> {
            addToFavorites.setColorFilter(getResources().getColor(R.color.greenPigment, getTheme()));
            Snackbar addedSnackbar = Snackbar.make(publicMusicianProfilePage, username + " has been added to your favorites!", Snackbar.LENGTH_SHORT);
            addedSnackbar.setAction("View Favorites", v1 -> {
                Intent intent1 = new Intent(PublicMusicianProfilePage.this, MyFavoritesPage.class);
                startActivity(intent1);
            });
            addedSnackbar.show();
            Log.i(TAG, "this is me" + currentUser.getFirstName() + " " + currentUser.getLastName());
            Log.i(TAG, "currentUser favorites" + currentUser.favorites);

            Favorite favorite = new Favorite(username);
            favoriteDatabase.favoriteDAO().insert(favorite);


            for(String f : favorites){
                Log.i(TAG, "this is one of my favorites:  " + f);
            }
//            currentUser.favorites = username;
//            currentUser.bio = "Test";
//            Log.i(TAG, "currentUser favorites" + currentUser.favorites);
//
//            currentUser.band = defaultBand;
//            Amplify.API.query(
//                    ModelMutation.update(currentUser),
//                            r ->{
//                                Log.i(TAG, "successful update of user" + r.getData());
//                            },
//                            e ->{}
//            );


//            Musician item = Musician.builder()
//                    .firstName("Lorem ipsum dolor sit amet")
//                    .lastName("Lorem ipsum dolor sit amet")
//                    .vocalist(true)
//                    .instruments("Lorem ipsum dolor sit amet")
//                    .genres("Lorem ipsum dolor sit amet")
//                    .bio("Lorem ipsum dolor sit amet")
//                    .username("Lorem ipsum dolor sit amet")
//                    .favorites("Lorem ipsum dolor sit amet")
//                    .build();

//            Amplify.DataStore.save(
//                    item,
//                    success -> Log.i("Amplify", "Saved item: " + success.item().getId()),
//                    error -> Log.e("Amplify", "Could not save item to DataStore", error)
//            );


//            Amplify.DataStore.query(
//                    Musician.class,
////                    Where.id(currentUser.getId()),
//                    items -> {
//                        while (items.hasNext()) {
//                            Musician item = items.next();
//                            Log.i("Amplify", "Id " + item.getId());
//                        }
//                    },
//                    failure -> Log.e("Amplify", "Could not query DataStore", failure)
//            );




        });


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