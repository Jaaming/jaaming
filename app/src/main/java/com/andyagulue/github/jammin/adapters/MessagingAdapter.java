package com.andyagulue.github.jammin.adapters;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Message;
import com.amplifyframework.datastore.generated.model.Musician;
import com.andyagulue.github.jammin.R;
import com.andyagulue.github.jammin.activities.MessagingActivity;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import javax.security.auth.login.LoginException;

public class MessagingAdapter extends RecyclerView.Adapter<MessagingAdapter.MessagingViewHolder> {
    private ArrayList<Message> musicianMessageList;
    private onMessagePreviewClickListener messageListener;



    public interface onMessagePreviewClickListener{
        void onMessageClick(int position);
    }
    public void setOnMessagePreviewClickListener(onMessagePreviewClickListener listener){
        messageListener = listener;
    }

    public static class MessagingViewHolder extends RecyclerView.ViewHolder{

        public ImageView musicianMessageProfileImage;
        public TextView musicianMessageFullName;
        public TextView musicianMessagePreview;

        public MessagingViewHolder(@NonNull @NotNull View itemView, onMessagePreviewClickListener listener) {
            super(itemView);

            musicianMessageProfileImage = itemView.findViewById(R.id.musicianMessageProfileImage);
            musicianMessageFullName = itemView.findViewById(R.id.musicianMessageFullName);
            musicianMessagePreview = itemView.findViewById(R.id.musicianMessagePreview);


            itemView.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onMessageClick(position);
                    }
                }
            });
        }
    }

    public MessagingAdapter(ArrayList<Message> messagesList) {
        musicianMessageList = messagesList;
    }

    @NonNull
    @NotNull
    @Override
    public MessagingViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.musician_message_cardview, parent, false);
        MessagingViewHolder mvh = new MessagingViewHolder(v, messageListener);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MessagingAdapter.MessagingViewHolder holder, int position) {
        Message currentMessage = musicianMessageList.get(position);
        String userName = Amplify.Auth.getCurrentUser().getUsername();

        if(currentMessage.getMusician().getUsername().equals(userName)){
            downloadImageFromS3(holder,currentMessage.getRecipient());
            amplifyQuery(holder, currentMessage.getRecipient());
        }else{
            downloadImageFromS3(holder,currentMessage.getMusician().getUsername());
            String fullName = currentMessage.getMusician().getFirstName() + " " + currentMessage.getMusician().getLastName();
            holder.musicianMessageFullName.setText(fullName);
        }
        holder.musicianMessagePreview.setText(currentMessage.getContent());

    }

    @Override
    public int getItemCount() {
        return musicianMessageList.size();
    }


    void downloadImageFromS3(MessagingAdapter.MessagingViewHolder holder, String key){
        Amplify.Storage.downloadFile(
                key,
                new File(holder.itemView.getContext().getFilesDir(), key),
                r -> {
                    holder.musicianMessageProfileImage.setImageBitmap(BitmapFactory.decodeFile(r.getFile().getPath()));
                },
                e -> {
                    holder.musicianMessageProfileImage.setImageResource(R.drawable.ic_baseline_account_circle_24);
                }
        );

    }
    void amplifyQuery(MessagingAdapter.MessagingViewHolder holder, String username){
        Amplify.DataStore.query(
                Musician.class,
                response ->{
                    while(response.hasNext()) {
                        Musician musician = response.next();
                        if(musician.getUsername().equals(username)){
                            String fullName1 = musician.getFirstName() + " " + musician.getLastName();
                            holder.musicianMessageFullName.setText(fullName1);
                        }

                    }
                },
                error -> {

                });
    }

}
