package com.andyagulue.github.jammin.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Message;
import com.amplifyframework.datastore.generated.model.Musician;
import com.andyagulue.github.jammin.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatAdapter extends ArrayAdapter<Message> {
    public ChatAdapter(@NonNull @NotNull Context context, int resource, @NonNull @NotNull ArrayList<Message> messages) {
        super(context, resource, messages);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Message message = getItem(position);
        boolean isCurrentUserMessage;
        Log.i("chatAdapter**", "getView: " + message);
        String userId = Amplify.Auth.getCurrentUser().getUserId();
        Log.i("chatAdapter**", "getView: " + userId);
        Log.i("chatAdapter**", "getView: " + message.getMusician());

        isCurrentUserMessage = message.getMusician().getId().equals(userId);


        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        if(isCurrentUserMessage){
            view = layoutInflater.inflate(R.layout.message_sent_layout, parent, false);

        }else{
            view = layoutInflater.inflate(R.layout.message_received_layout, parent, false);

        }
        TextView messageContent = view.findViewById(R.id.messageContent);

        messageContent.setText(message.getContent());
        return view;
    }
}
