package com.andyagulue.github.jammin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.andyagulue.github.jammin.R;

import java.util.ArrayList;
import java.util.Collections;

public class BandCreationPage extends AppCompatActivity {

    TextView bandInstruments;
    TextView bandGenres;
    boolean[] bandSelectedInstruments;
    boolean[] bandSelectedGenres;
    ArrayList<Integer> bandSelectedInstrumentList = new ArrayList<>();
    ArrayList<Integer> bandSelectedGenreList = new ArrayList<>();
    String[] bandInstrumentsArray = {"Acoustic Guitar", "Electric Guitar", "Bass Guitar", "Drums", "Keyboard" };
    String[] bandGenresArray = {"Pop", "Rock", "Acoustic", "Jazz", "Reggae", "Folk", "Punk", "Americana", "Indie"};


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