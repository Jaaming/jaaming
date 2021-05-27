package com.andyagulue.github.jammin.activities;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Band;
import com.amplifyframework.datastore.generated.model.Musician;
import com.andyagulue.github.jammin.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

public class BandCreationPage extends AppCompatActivity {
    String TAG = "Band Creation Page";

    ImageView bandImage;
    ImageButton addBandImageButton;

    private static final int IMAG_REQ_CODE = 1989;
    private static final int PERMISSION_CODE = 1986;

    TextView bandInstruments;
    TextView bandGenres;
    boolean[] bandSelectedInstruments;
    boolean[] bandSelectedGenres;
    ArrayList<Integer> bandSelectedInstrumentList = new ArrayList<>();
    ArrayList<Integer> bandSelectedGenreList = new ArrayList<>();
    String[] bandInstrumentsArray = {"Acoustic Guitar", "Electric Guitar", "Bass Guitar", "Drums", "Keyboard" };
    String[] bandGenresArray = {"Pop", "Rock", "Acoustic", "Jazz", "Reggae", "Folk", "Punk", "Americana", "Indie"};

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
        setContentView(R.layout.activity_band_creation_page);

        bandInstruments = findViewById(R.id.createBandAddInstrumentTextView);
        bandGenres = findViewById(R.id.createBandAddGenreTextView);

        bandSelectedInstruments = new boolean[bandInstrumentsArray.length];
        bandSelectedGenres = new boolean[bandGenresArray.length];

        bandInstruments.setOnClickListener(v -> {
            AlertDialog.Builder instrumentBuilder = new AlertDialog.Builder(
                    BandCreationPage.this
            );
            instrumentBuilder.setTitle("Select Instrument(s)");
            instrumentBuilder.setCancelable(false);
            instrumentBuilder.setMultiChoiceItems(bandInstrumentsArray, bandSelectedInstruments, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                    if(isChecked){
                        bandSelectedInstrumentList.add(which);
                        Collections.sort(bandSelectedInstrumentList);
                    }else{
                        int j = bandSelectedInstrumentList.indexOf(which);
                        bandSelectedInstrumentList.remove(j);
                    }
                }
            });

            instrumentBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    StringBuilder sb = new StringBuilder();
                    for(int i = 0; i< bandSelectedInstrumentList.size(); i++){
                        sb.append(bandInstrumentsArray[bandSelectedInstrumentList.get(i)]);
                        if(i != bandSelectedInstrumentList.size()-1){
                            sb.append(", ");
                        }
                    }
                    bandInstruments.setText(sb.toString());
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

                    for(int i = 0; i< bandSelectedInstruments.length; i++){
                        bandSelectedInstruments[i] = false;
                        bandSelectedInstrumentList.clear();
                        bandInstruments.setText("");
                    }
                }
            });

            instrumentBuilder.show();
        });

        bandGenres.setOnClickListener(v -> {
            AlertDialog.Builder genreBuilder = new AlertDialog.Builder(
                    BandCreationPage.this
            );
            genreBuilder.setTitle("Select Genre(s)");
            genreBuilder.setCancelable(false);
            genreBuilder.setMultiChoiceItems(bandGenresArray, bandSelectedGenres, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                    if(isChecked){
                        bandSelectedGenreList.add(which);
                        Collections.sort(bandSelectedGenreList);
                    }else{
                        int k = bandSelectedGenreList.indexOf(which);
                        bandSelectedGenreList.remove(k);
                    }
                }
            });
            genreBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    StringBuilder gsb = new StringBuilder();
                    for(int l = 0; l <bandSelectedGenreList.size(); l++){
                        gsb.append(bandGenresArray[bandSelectedGenreList.get(l)]);

                        if(l != bandSelectedGenreList.size() -1){
                            gsb.append(", ");
                        }
                        bandGenres.setText(gsb.toString());
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
                    for(int l = 0; l < bandSelectedGenreList.size(); l++){
                        bandSelectedGenres[l] = false;
                        bandSelectedGenreList.clear();
                        bandGenres.setText("");
                    }
                }
            });
            genreBuilder.show();

        });

        Button submitCreateMusician = findViewById(R.id.submitCreateBandButton);
        submitCreateMusician.setOnClickListener(v -> {
            String bandName = ((TextView) findViewById(R.id.createBandNameEditText)).getText().toString();
            String instruments = bandInstruments.getText().toString();
            String genres = bandGenres.getText().toString();
            String bio = ((TextView) findViewById(R.id.createBandBioTextView)).getText().toString();
            @SuppressLint("UseSwitchCompatOrMaterialCode")
            Switch switchOn = findViewById(R.id.createBandAddVocalist);
            boolean isVocalist = switchOn.isChecked();

            Band newBand = Band.builder()
                    .name(bandName)
                    .instruments(instruments)
                    .genres(genres)
                    .bio(bio)
                    .vocalist(isVocalist)
                    .build();

            Log.i(TAG, "onCreate: Made it to 220");
            Amplify.API.query(
                    ModelQuery.list(Musician.class, Musician.USERNAME.eq(bandName)),
                    response-> {
                        if(!response.getData().getItems().iterator().hasNext()){
                            Amplify.API.mutate(
                                    ModelMutation.create(newBand),
                                    r ->{
                                        Log.i(TAG, "onCreate: Created a new musician" );
                                        Intent intent = new Intent(BandCreationPage.this, DiscoverPage.class);
                                        startActivity(intent);
                                    },
                                    err ->{
                                        Log.e(TAG, "onCreate: Unable to create musician -->",err );
                                    }
                            );
                            return;
                        }
                        Musician existingBand = response.getData().getItems().iterator().next();
                        Log.i(TAG, "band" + existingBand);
                        Band updatedBand = Band.builder()
                                .name(bandName)
                                .instruments(instruments)
                                .genres(genres)
                                .bio(bio)
                                .vocalist(isVocalist)
                                .id(existingBand.getId())
                                .build();

                        Log.i(TAG, "onCreate: updated band: " );

                        Amplify.API.mutate(
                                ModelMutation.create(updatedBand),
                                res-> {
                                    Log.i(TAG, "updated band" + res);
                                    Intent intent = new Intent(BandCreationPage.this, DiscoverPage.class);
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
                                ModelMutation.create(newBand),
                                response ->{
                                    Log.i(TAG, "onCreate: Created a new musician" );
                                    Intent intent = new Intent(BandCreationPage.this, DiscoverPage.class);
                                    startActivity(intent);
                                },
                                err ->{
                                    Log.e(TAG, "onCreate: Unable to create musician -->",error );
                                }
                        );
                    }
            );

        });

        //======profile Image
        bandImage = findViewById(R.id.createBandImageView);
        addBandImageButton = findViewById(R.id.createBandAddPicButton);

        addBandImageButton.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED){
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else {
                    pickImageFromPhotos();

                }
            }
            else{

                pickImageFromPhotos();

            }        });



    }

    private void pickImageFromPhotos() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAG_REQ_CODE);
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {


        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){

                    pickImageFromPhotos();
                }

                else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAG_REQ_CODE) {

            bandImage.setImageURI(data.getData());
        }
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