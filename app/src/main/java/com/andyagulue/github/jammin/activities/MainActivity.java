package com.andyagulue.github.jammin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Todo;
import com.andyagulue.github.jammin.R;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try{
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("JammingApp","Initialized Amplify");
        }catch(AmplifyException error){
            Log.e("JammingApp", "onCreate: Could not create");
        }

        Amplify.Auth.fetchAuthSession(
                result -> Log.i("AmplifyQuickStart", result.toString()),
                error -> Log.e("AmplifyQuickStart", error.toString())
        );

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
        //========================End temp buttons to be deleted=================================
    }


}

