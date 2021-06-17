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
        String userName = Amplify.Auth.getCurrentUser().getUsername();
        Log.i("chatAdapter**", "getView: " + userName);
        Log.i("chatAdapter**", "getView: " + message.getMusician());

        if(message.getMusician() != null){
            isCurrentUserMessage = message.getMusician().getUsername().equals(userName);
        }else{
            isCurrentUserMessage = false;
        }
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
