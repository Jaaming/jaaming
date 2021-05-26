package com.andyagulue.github.jammin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Band;
import com.amplifyframework.datastore.generated.model.Musician;
import com.andyagulue.github.jammin.R;

import java.util.ArrayList;
import java.util.Collections;

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
//      Amplify
        //Create instruments



//        Musician musician = Musician.builder()
//                .firstName("Bob")
//                .lastName("Evans")
//                .email("be@gmail.com")
//                .vocalist(true)
//                .bio("I like screaming until your ears bleed")
//                .band(band) //TODO: Musician may not be a part of a band.
//                .build();
//
//        Amplify.API.mutate(
//                    ModelMutation.create(musician),
//                    response -> {
//                        Log.i(TAG, "onCreate: Created Musician " + musician.getFirstName());
//                   },
//                    error -> {
//                        Log.e(TAG, "onCreate: Could not create Musician named " + musician.getFirstName(),error );
//                    }
//            );


        //Create genres

//


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
            String firstName = ((TextView)findViewById(R.id.addfirstNameEditText)).getText().toString();
            String lastName = ((TextView)findViewById(R.id.addLastNameEditText)).getText().toString();
            String email = ((TextView)findViewById(R.id.addEmailEditText)).getText().toString();
            String bio = ((TextView)findViewById(R.id.signupBioTextView)).getText().toString();
            String genres = ((TextView)tvGenres).getText().toString();
            String instruments = ((TextView)tvInstruments).getText().toString();
            @SuppressLint("UseSwitchCompatOrMaterialCode")
            Switch switchOn = findViewById(R.id.signupAddVocalist);
            boolean isVocalist = switchOn.isChecked();

            Musician newMusician = Musician.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .vocalist(isVocalist)
                    .instruments(instruments)
                    .genres(genres)
                    .bio(bio)
                    .band(defaultBand) //TODO: Musician may not be a part of a band.
                    .build();

            Log.i(TAG, "onCreate: Made it to 220");
            Amplify.API.mutate(
                    ModelMutation.create(newMusician),
                    response ->{
                        Log.i(TAG, "onCreate: Created a new musician" );
                    },
                    error ->{
                        Log.e(TAG, "onCreate: Unable to create musician -->",error );
                    }
            );
        });

    }
}