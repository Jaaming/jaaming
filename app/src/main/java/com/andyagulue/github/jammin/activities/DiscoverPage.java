package com.andyagulue.github.jammin.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;


import com.amplifyframework.core.async.Cancelable;
import com.amplifyframework.datastore.DataStoreItemChange;
import com.amplifyframework.datastore.generated.model.Musician;
import com.andyagulue.github.jammin.R;
import com.andyagulue.github.jammin.adapters.ViewPagerAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class DiscoverPage extends AppCompatActivity {
    String TAG = "discover Page";

    private String userName;
    private ImageView vocalistImage;
    public Musician musician;


    private TextView filterInstruments;
    private TextView filterGenres;
    private boolean[] selectedInstruments;
    private boolean[] selectedGenres;
    private final ArrayList<Integer> instrumentList = new ArrayList<>();
    private final ArrayList<Integer> genreList = new ArrayList<>();
    private final String[] instrumentsArray = {"Acoustic Guitar", "Electric Guitar", "Bass Guitar", "Drums","Keyboard"};
    private final String[] genresArray = {"Pop", "Rock", "Acoustic", "Jazz", "Reggae", "Folk", "Punk", "Americana", "Indie","Synth Pop","Trap","New World","Country"};

    private ViewPager2 viewpager2;
    public ArrayList<Musician> musicianArrayList;

    private Handler discoverPageHandler;
    private View discoverPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_page);
        discoverPage = findViewById(R.id.DiscoverPageLayout);




        populateDiscoverMusicians();
        buildViewPager();
        AuthUser authUser = Amplify.Auth.getCurrentUser();
        userName = authUser.getUsername();
        buildInstrumentAlertDialog();
        buildGenreAlertDialog();
        filterButtonFunction();

        Amplify.DataStore.observe(
                com.amplifyframework.datastore.generated.model.Message.class,
                cancelable -> Log.i(TAG, "Amplify Observation began:"),
                this::onNewMessageReceived,
                failure -> Log.i(TAG, "Observation failed"),
                () -> Log.i(TAG, "Observation completed:")
        );


    }

    private void onNewMessageReceived(DataStoreItemChange<com.amplifyframework.datastore.generated.model.Message> messageChanged) {
        if (messageChanged.type().equals(DataStoreItemChange.Type.CREATE)) {
            Log.i(TAG, "onNewMessageReceived: " + messageChanged);
            com.amplifyframework.datastore.generated.model.Message message = messageChanged.item();
            if(message.getRecipient().equals(userName)){
                Log.i(TAG, "onNewMessageReceived: " + message);
                Snackbar newMessageSnackBar = Snackbar.make(discoverPage, "you got a new message", Snackbar.LENGTH_LONG);
                newMessageSnackBar.show();
            }
        }
    }


    public void populateDiscoverMusicians(){
        musicianArrayList = new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(Musician.class),
                r -> {
                    Log.i(TAG, "populateDiscoverMusicians: ");
                    for(Musician musician: r.getData()) {
//                        Log.i(TAG, "populateDiscoverMusicians: " + r.getData().getItems() + 1);
                        if(instrumentList.isEmpty() && genreList.isEmpty()){
                            if(!musician.getUsername().equals(userName)) {
                                musicianArrayList.add(musician);
                            }
                        }else if(!instrumentList.isEmpty() && genreList.isEmpty()){
                            for(int instrument : instrumentList){
                                Log.i(TAG, "populateDiscoverMusicians: this should filter " + instrumentList);
                                Log.i(TAG, "populateDiscoverMusicians: this should filter  " + instrument);
                                Log.i(TAG, "populateDiscoverMusicians: this should filter " + instrumentsArray[instrument]);
                                if(musician.getInstruments().contains(instrumentsArray[instrument]) && !musician.getUsername().equals(userName)){
                                    musicianArrayList.add(musician);
                                }
                            }
                        }else if(instrumentList.isEmpty()){
                            for(int genre : genreList){
                                Log.i(TAG, "populateDiscoverMusicians: this should filter " + genreList);
                                Log.i(TAG, "populateDiscoverMusicians: this should filter  " + genre);
                                Log.i(TAG, "populateDiscoverMusicians: this should filter " + genresArray[genre]);
                                if(musician.getGenres().contains(genresArray[genre]) && !musician.getUsername().equals(userName)){
                                    musicianArrayList.add(musician);
                                }
                            }
                        }else{
                            for(int instrument : instrumentList){
                                for(int genre : genreList){
                                    Log.i(TAG, "populateDiscoverMusicians: this should filter " + genreList);
                                    Log.i(TAG, "populateDiscoverMusicians: this should filter  " + genre);
                                    Log.i(TAG, "populateDiscoverMusicians: this should filter " + genresArray[genre]);
                                    if(musician.getGenres().contains(genresArray[genre])
                                            && musician.getInstruments().contains(instrumentsArray[instrument])
                                            && !musician.getUsername().equals(userName)){
                                        musicianArrayList.add(musician);
                                    }
                                }
                            }
                        }
                    }
                    Collections.shuffle(musicianArrayList);
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
        ViewPagerAdapter adapter = new ViewPagerAdapter(musicianArrayList);
        viewpager2.setAdapter(adapter);

        discoverPageHandler = new Handler(this.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == 123){
                    Objects.requireNonNull(viewpager2.getAdapter()).notifyDataSetChanged();
                }
            }
        };

        adapter.setOnProfileClickListener(position -> {
            String username = musicianArrayList.get(position).getUsername();
            Intent intent = new Intent(DiscoverPage.this, PublicMusicianProfilePage.class);
            intent.putExtra("username", username);
            startActivity(intent);


        });
    }

    public void buildInstrumentAlertDialog(){
        filterInstruments = findViewById(R.id.filterInstrumentsTextView);
        selectedInstruments = new boolean[instrumentsArray.length];
        filterInstruments.setOnClickListener(v -> {
            Log.i(TAG, "instrumentList" + instrumentList.toString());
            AlertDialog.Builder instrumentBuilder = new AlertDialog.Builder(
                    DiscoverPage.this
            );
            instrumentBuilder.setTitle("Select Instrument(s)");
            instrumentBuilder.setCancelable(false);
            instrumentBuilder.setMultiChoiceItems(instrumentsArray, selectedInstruments, (dialog, which, isChecked) -> {


                if(isChecked){
                    Log.i(TAG, "isChecked" + which);
                    instrumentList.add(which);
                    Collections.sort(instrumentList);
                }else{
                    Log.i(TAG, "unchecked" + which);
                    int j = instrumentList.indexOf(which);
                    instrumentList.remove(j);
                }
            });

            instrumentBuilder.setPositiveButton("Ok", (dialog, which) -> {
                Log.i(TAG, "instrumentList" + instrumentList.toString());

                StringBuilder sb = new StringBuilder();
                for(int i = 0; i< instrumentList.size(); i++){
                    sb.append(instrumentsArray[instrumentList.get(i)]);
                    if(i != instrumentList.size()-1){
                        sb.append(", ");
                    }
                }
                filterInstruments.setText(sb.toString());
            });
            instrumentBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            instrumentBuilder.setNeutralButton("Clear All", (dialog, which) -> {

                Arrays.fill(selectedInstruments, false);
                instrumentList.clear();
                filterInstruments.setText("");
            });

            instrumentBuilder.show();
        });
    }

    public void buildGenreAlertDialog(){
        filterGenres = findViewById(R.id.filterGenresTextView);
        selectedGenres = new boolean[genresArray.length];
        filterGenres.setOnClickListener(v -> {
            Log.i(TAG, "genreList" + genreList.toString());
            Log.i(TAG, "selectedGenres" + Arrays.toString(selectedGenres));

            AlertDialog.Builder genreBuilder = new AlertDialog.Builder(
                    DiscoverPage.this
            );
            genreBuilder.setTitle("Select Genre(s)");
            genreBuilder.setCancelable(false);
            genreBuilder.setMultiChoiceItems(genresArray, selectedGenres, (dialog, which, isChecked) -> {

                if(isChecked){
                    Log.i(TAG, "isChecked" + which);
                    genreList.add(which);
                    Collections.sort(genreList);
                }else{
                    Log.i(TAG, "unchecked" + which);
                    genreList.remove((Integer) which);
                }
            });
            genreBuilder.setPositiveButton("Ok", (dialog, which) -> {
                Log.i(TAG, "genreList" + genreList.toString());
                Log.i(TAG, "selectedGenres" + Arrays.toString(selectedGenres));
                StringBuilder gsb = new StringBuilder();
                for(int l = 0; l <genreList.size(); l++){
                    gsb.append(genresArray[genreList.get(l)]);

                    if(l != genreList.size() -1){
                        gsb.append(", ");
                    }
                    filterGenres.setText(gsb.toString());
                }
            });
            genreBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            genreBuilder.setNeutralButton("Clear All", (dialog, which) -> {
                Arrays.fill(selectedGenres, false);
                genreList.clear();
                filterGenres.setText("");
            });
            genreBuilder.show();

        });
    }
    public void filterButtonFunction(){
        CardView filterCardView = findViewById(R.id.filterCardView);
        ImageButton filterImageButton = findViewById(R.id.filterImageButton);
        ImageButton cancelImageButton = findViewById(R.id.cancelButton);
        ImageButton filterButton = findViewById(R.id.filterButton);
        filterImageButton.setOnClickListener(v -> {
            filterCardView.setVisibility(View.VISIBLE);
            filterImageButton.setVisibility(View.INVISIBLE);
        });
        cancelImageButton.setOnClickListener(v -> {
            filterCardView.setVisibility(View.INVISIBLE);
            filterImageButton.setVisibility(View.VISIBLE);
        });
        filterButton.setOnClickListener(v -> {
            populateDiscoverMusicians();
            buildViewPager();
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
            case R.id.item4:
                Amplify.Auth.signOut(
                () -> {
                    Log.i(TAG, "The user was signed out");
                    Intent intent4 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent4);
                },
                error -> Log.i(TAG, "the user was not signed out")
                );
            case R.id.item5:
                Intent intent5 = new Intent(getApplicationContext(), MessagingActivity.class);
                startActivity(intent5);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


}