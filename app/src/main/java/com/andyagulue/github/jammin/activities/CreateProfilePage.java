package com.andyagulue.github.jammin.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Band;
import com.amplifyframework.datastore.generated.model.Musician;
import com.andyagulue.github.jammin.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.security.auth.login.LoginException;

public class CreateProfilePage extends AppCompatActivity {

    String TAG = "signupPage";

    //=========variables to create form=============
    AuthUser authUser;
    ImageView profileImage;
    ImageButton addProfileImageButton;
    File imageToUpload;

    private static final int IMAG_REQ_CODE = 1989;
    private static final int PERMISSION_CODE = 1986;

    TextView tvInstruments;
    TextView tvGenres;
    boolean[] selectedInstruments;
    boolean[] selectedGenres;
    ArrayList<Integer> instrumentList = new ArrayList<>();
    ArrayList<Integer> genreList = new ArrayList<>();
    String[] instrumentsArray = {"Acoustic Guitar", "Electric Guitar", "Bass Guitar", "Drums","Keyboard"};
    String[] genresArray = {"Pop", "Rock", "Acoustic", "Jazz", "Reggae", "Folk", "Punk", "Americana", "Indie","Synth Pop","Trap","New World","Country"};

    //===========end of variable to create form============

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

       authUser = Amplify.Auth.getCurrentUser();
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
            Log.i(TAG, "instrumentList" + instrumentList.toString());
            AlertDialog.Builder instrumentBuilder = new AlertDialog.Builder(
                    CreateProfilePage.this
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
                tvInstruments.setText(sb.toString());
            });
            instrumentBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            instrumentBuilder.setNeutralButton("Clear All", (dialog, which) -> {

                Arrays.fill(selectedInstruments, false);
                instrumentList.clear();
                tvInstruments.setText("");
            });

            instrumentBuilder.show();
        });

        tvGenres.setOnClickListener(v -> {
            Log.i(TAG, "genreList" + genreList.toString());
            Log.i(TAG, "selectedGenres" + Arrays.toString(selectedGenres));

            AlertDialog.Builder genreBuilder = new AlertDialog.Builder(
                    CreateProfilePage.this
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
                    tvGenres.setText(gsb.toString());
                }
            });
            genreBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            genreBuilder.setNeutralButton("Clear All", (dialog, which) -> {
                Arrays.fill(selectedGenres, false);
                genreList.clear();
                tvGenres.setText("");
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
            uploadImage();

        });

        //======profile Image
        profileImage = findViewById(R.id.createProfileImageView);
        addProfileImageButton = findViewById(R.id.createProfilePicButton);

        addProfileImageButton.setOnClickListener(v -> {
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

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
       if (resultCode == RESULT_OK && requestCode == IMAG_REQ_CODE) {
           imageToUpload = new File(getApplicationContext().getFilesDir(), "new Profile Image");
           try {
               assert data != null;
               InputStream inputStream = getContentResolver().openInputStream(data.getData());
               FileUtils.copy(inputStream, new FileOutputStream(imageToUpload));
               profileImage.setImageBitmap(BitmapFactory.decodeFile(imageToUpload.getPath()));
           }catch (IOException e) {
               e.printStackTrace();
           }

       }
    }

    private void uploadImage(){
        Amplify.Storage.uploadFile(
                authUser.getUsername(),
                imageToUpload,
                r -> {
                    Log.i(TAG, "uploadImage: successful");
                },
                e ->{
                    Log.i(TAG, "uploadImage: unsuccessful");
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