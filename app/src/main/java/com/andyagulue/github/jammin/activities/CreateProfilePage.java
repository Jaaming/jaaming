package com.andyagulue.github.jammin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Band;
import com.amplifyframework.datastore.generated.model.Musician;
import com.andyagulue.github.jammin.R;

import java.util.ArrayList;
import java.util.Collections;

import javax.security.auth.login.LoginException;

public class CreateProfilePage extends AppCompatActivity {

    String TAG = "signupPage";

    TextView tvInstruments;
    TextView tvGenres;
    boolean[] selectedInstruments;
    boolean[] selectedGenres;
    ArrayList<Integer> instrumentList = new ArrayList<>();
    ArrayList<Integer> genreList = new ArrayList<>();
    String[] instrumentsArray = {"Standup Bass","Acoustic Guitar", "Electric Guitar", "Bass Guitar", "Drums","Violen","Fiddle","Chello", "Keyboard","Saxophone","Clarinet","Flute","Oboe","Triangle","Washboard","Harp"};
    String[] genresArray = {"Pop", "Rock", "Acoustic", "Jazz", "Reggae", "Folk", "Punk", "Americana", "Indie","Synth Pop","Trap","New World","Country"};

    Band defaultBand = Band.builder()
            .name("Default")
            .instruments("Default")
            .genres("String")
            .bio("Default")
            .vocalist(true)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile_page);
        Log.i(TAG, "onCreate: Made it to the signup page");

        AuthUser authUser = Amplify.Auth.getCurrentUser();
        if(authUser != null) {
            ((TextView) findViewById(R.id.profileUserNameEditText)).setText(authUser.getUsername());
            Log.i(TAG, "user is authenticated!");

        }


        tvInstruments = findViewById(R.id.addInstrumentTextView);
        Log.i(TAG, "onCreate: TV Instruments " + tvInstruments.getText());
        tvGenres = findViewById(R.id.addgenreTextView);

        selectedInstruments = new boolean[instrumentsArray.length];
        selectedGenres = new boolean[genresArray.length];

        tvInstruments.setOnClickListener(v -> {
            AlertDialog.Builder instrumentBuilder = new AlertDialog.Builder(
                    CreateProfilePage.this
            );
            instrumentBuilder.setTitle("Select Instrument(s)");
            instrumentBuilder.setCancelable(false);
            instrumentBuilder.setMultiChoiceItems(instrumentsArray, selectedInstruments, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                    if(isChecked){
                        instrumentList.add(which);
                        Collections.sort(instrumentList);
                    }else{
                        int j = instrumentList.indexOf(which);
                        instrumentList.remove(j);
                    }
                }
            });

            instrumentBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    StringBuilder sb = new StringBuilder();
                    for(int i = 0; i< instrumentList.size(); i++){
                        sb.append(instrumentsArray[instrumentList.get(i)]);
                        if(i != instrumentList.size()-1){
                            sb.append(", ");
                        }
                    }
                    tvInstruments.setText(sb.toString());
                }
            });
            instrumentBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            instrumentBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    for(int i = 0; i< selectedInstruments.length; i++){
                        selectedInstruments[i] = false;
                        instrumentList.clear();
                        tvInstruments.setText("");
                    }
                }
            });

            instrumentBuilder.show();
        });

        tvGenres.setOnClickListener(v -> {
            AlertDialog.Builder genreBuilder = new AlertDialog.Builder(
                    CreateProfilePage.this
            );
            genreBuilder.setTitle("Select Genre(s)");
            genreBuilder.setCancelable(false);
            genreBuilder.setMultiChoiceItems(genresArray, selectedGenres, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                    if(isChecked){
                        genreList.add(which);
                        Collections.sort(genreList);
                    }else{
                        int k = genreList.indexOf(which);
                        genreList.remove(k);
                    }
                }
            });
            genreBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    StringBuilder gsb = new StringBuilder();
                    for(int l = 0; l <genreList.size(); l++){
                        gsb.append(genresArray[genreList.get(l)]);

                        if(l != genreList.size() -1){
                            gsb.append(", ");
                        }
                        tvGenres.setText(gsb.toString());
                    }
                }
            });
            genreBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            genreBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for(int l = 0; l < genreList.size(); l++){
                        selectedGenres[l] = false;
                        genreList.clear();
                        tvGenres.setText("");
                    }
                }
            });
            genreBuilder.show();

        });

        Button submitCreateMusician = findViewById(R.id.signupSubmitButton);
        submitCreateMusician.setOnClickListener(v ->{
            String userName = ((TextView)findViewById(R.id.profileUserNameEditText)).getText().toString();
            String firstName = ((TextView)findViewById(R.id.addfirstNameEditText)).getText().toString();
            String lastName = ((TextView)findViewById(R.id.addLastNameEditText)).getText().toString();
            String bio = ((TextView)findViewById(R.id.signupBioTextView)).getText().toString();
            String genres = tvGenres.getText().toString();
            String instruments = tvInstruments.getText().toString();
            @SuppressLint("UseSwitchCompatOrMaterialCode")
            Switch switchOn = findViewById(R.id.signupAddVocalist);
            boolean isVocalist = switchOn.isChecked();

            Musician newMusician = Musician.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .vocalist(isVocalist)
                    .instruments(instruments)
                    .genres(genres)
                    .bio(bio)
                    .username(userName)
                    .band(defaultBand) //TODO: Musician may not be a part of a band.
                    .build();

            Log.i(TAG, "onCreate: Made it to 220");
            Amplify.API.query(
                    ModelQuery.list(Musician.class, Musician.USERNAME.eq(userName)),
                    response-> {
                        if(!response.getData().getItems().iterator().hasNext()){
                            Amplify.API.mutate(
                                    ModelMutation.create(newMusician),
                                    r ->{
                                        Log.i(TAG, "onCreate: Created a new musician" );
                                        Intent intent = new Intent(CreateProfilePage.this, DiscoverPage.class);
                                        startActivity(intent);
                                    },
                                    err ->{
                                        Log.e(TAG, "onCreate: Unable to create musician -->",err );
                                    }
                            );
                            return;
                        }
                        Musician existingMusician = response.getData().getItems().iterator().next();
                        Log.i(TAG, "musician" + existingMusician);
                        Musician updatedMusician = Musician.builder()
                                .firstName(firstName)
                                .lastName(lastName)
                                .vocalist(isVocalist)
                                .instruments(instruments)
                                .genres(genres)
                                .bio(bio)
                                .username(existingMusician.getUsername())
                                .id(existingMusician.getId())
                                .build();

                        Log.i(TAG, "onCreate: updated musician: " + updatedMusician.firstName);

                        Amplify.API.mutate(
                                ModelMutation.create(updatedMusician),
                                res-> {
                                    Log.i(TAG, "updated musician" + res);
                                    Intent intent = new Intent(CreateProfilePage.this, DiscoverPage.class);
                                    startActivity(intent);
                                },
                                err -> {
                                    Log.i(TAG, "did not update musician" + err);
                                }
                        );
                    },

                    error ->{
                        Log.i(TAG, "musician is not in the database");
                        Amplify.API.mutate(
                                ModelMutation.create(newMusician),
                                response ->{
                                    Log.i(TAG, "onCreate: Created a new musician" );
                                    Intent intent = new Intent(CreateProfilePage.this, DiscoverPage.class);
                                    startActivity(intent);
                                },
                                err ->{
                                    Log.e(TAG, "onCreate: Unable to create musician -->",error );
                                }
                        );
                    }
            );

        });

    }
}