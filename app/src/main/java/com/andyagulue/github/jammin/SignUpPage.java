package com.andyagulue.github.jammin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class SignUpPage extends AppCompatActivity {

    String TAG = "signupPage";

    TextView tvInstruments;
    TextView tvGenres;
    boolean[] selectedInstruments;
    boolean[] selectedGenres;
    ArrayList<Integer> instrumentList = new ArrayList<>();
    ArrayList<Integer> genreList = new ArrayList<>();
    String[] instrumentsArray = {"Acoustic Guitar", "Electric Guitar", "Bass Guitar", "Drums", "Keyboard" };
    String[] genresArray = {"Pop", "Rock", "Acoustic", "Jazz", "Reggae", "Folk", "Punk", "Americana", "Indie"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        tvInstruments = findViewById(R.id.addInstrumentTextView);
        tvGenres = findViewById(R.id.addgenreTextView);

        selectedInstruments = new boolean[instrumentsArray.length];
        selectedGenres = new boolean[genresArray.length];

        tvInstruments.setOnClickListener(v -> {
            AlertDialog.Builder instrumentBuilder = new AlertDialog.Builder(
                    SignUpPage.this
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
                    SignUpPage.this
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

    }
}