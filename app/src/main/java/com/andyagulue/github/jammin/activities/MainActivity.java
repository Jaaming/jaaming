package com.andyagulue.github.jammin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Action;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Musician;
import com.amplifyframework.logging.AndroidLoggingPlugin;
import com.amplifyframework.logging.LogLevel;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.andyagulue.github.jammin.FavoriteMusician;
import com.andyagulue.github.jammin.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/*============todo's==========
1 [x] Generate users
2 [x] Pull user info down with R.getData()
3 [x] Change the musician hard coded musician class to reflect what's in the database
4 [x] Populate the discover page with dynamic info from database
5 [] find user's public facing profile page based on the user that's signed in
6 [x] Get S3 working for images
7 [x] download images to phone
8 [x] populate picture from phone to profile & save to S3
9 [x] Connect what's saved to S3 with each musician
10[x] pull data from S3
11 [] Format app for looks
12 [x] Change instruments and genres from text views to recycler views
13 [x] work on public facing profile
14 [x] access by pushing view profile button
15 add to faves
16 [x] back button from discover page needs to go and adjust layout to be centered
17 add mike icon to discover page
18 take out buttons from splash page
19 back button refactoring
20 add card view to log in page
21 add card view to confirmation page
22 add card view to create profile page
23 create users with pictures


 */

public class MainActivity extends AppCompatActivity {
    String TAG = "mainActivity";

    String userName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        try{
            Amplify.addPlugin(new AndroidLoggingPlugin(LogLevel.VERBOSE));
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());
            Log.i("JammingApp","Initialized Amplify");
        }catch(AmplifyException error){
            Log.e("JammingApp", "onCreate: Could not create");
        }

//        Amplify.DataStore.clear(
//                ()->{
//                    Log.i(TAG, "onCreate: successful clear of datastore");
//                },
//                e->{
//                    Log.i(TAG, "onCreate: unsuccessful clear of datastore" + e);
//                }
//        );

        Amplify.Auth.fetchAuthSession(
                result -> Log.i("AmplifyQuickStart", "Success" + result.toString()),
                error -> Log.e("AmplifyQuickStart", "Failure" + error.toString())
        );

        AuthUser authUser = Amplify.Auth.getCurrentUser();



        if (authUser != null){
            Log.i(TAG, "onCreate: authUsername" + userName);
            userName = authUser.getUsername();
            Intent intent = new Intent(MainActivity.this, DiscoverPage.class);
            startActivity(intent);
        }

//        log out of amplify
//        Amplify.Auth.signOut(
//                () -> Log.i(TAG, "The user was signed out"),
//                error -> Log.i(TAG, "the user was not signed out")
//
//        );


//        Amplify.DataStore.clear(
//                () -> Amplify.DataStore.start(
//                        () -> Log.i("MyAmplifyApp", "DataStore started"),
//                        error -> Log.e("MyAmplifyApp", "Error starting DataStore: ", error)
//                ),
//                error -> Log.e("MyAmplifyApp", "Error clearing DataStore: ", error)
//        );



        uploadFile();



//        signupCognito();
//        Amplify.API.query(
//                ModelQuery.list(Musician.class, Musician.USERNAME.eq(authUser.getUsername())),
//                response-> {
//                    Log.i(TAG, "queryResponse" + response.getData().toString());
//                    Musician thisMusician = response.getData().getItems().iterator().next();
//                    Log.i(TAG, "musician" + thisMusician);
//
//                },
//                error ->{
//                    Log.i(TAG, "queryerror");
//                }
//        );

//        Amplify.API.mutate(ModelMutation.update(thisMusician))


//        Todo todo = Todo.builder()
//                .name("My fire todo")
//                .description("todo description")
//                .build();
//        Amplify.API.mutate(
//                ModelMutation.create(todo),
//                response -> Log.i("JammingAPP", "onCreate: Added Todo with id: " + response.getData()),
//                error -> Log.e("JammingApp", "onCreate: Create failed",error )
//        );


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
            Intent intent = new Intent(getApplicationContext(), PublicMusicianProfilePage.class);
            startActivity(intent);


        });
        findViewById(R.id.createProfilePageButton).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CreateProfilePage.class);
            startActivity(intent);
        });
        //========================End temp buttons to be deleted=================================
    }

    void signupCognito() {
        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), "m.parker.simms@gmail.com")
                .build();
        Amplify.Auth.signUp("username", "Password123", options,
                result -> Log.i("AuthQuickStart", "Result: " + result.toString()),
                error -> Log.e("AuthQuickStart", "Sign up failed", error)
        );
    }

    private void uploadFile() {
        File exampleFile = new File(getApplicationContext().getFilesDir(), "ExampleKey2");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(exampleFile));
            writer.append("Example file contents");
            writer.close();
        } catch (Exception exception) {
            Log.e("MyAmplifyApp", "Upload failed", exception);
        }
        Amplify.Storage.uploadFile(
                "ExampleKey2",
                exampleFile,
                result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
        );
    }


}

