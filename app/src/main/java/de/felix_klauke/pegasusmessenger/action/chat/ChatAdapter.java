package de.felix_klauke.pegasusmessenger.action.chat;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by Klauke on 14.02.2016.
 */
public class ChatAdapter extends ArrayAdapter<String> {

    private static List<String> messages = Lists.newArrayList();

    public ChatAdapter(Context context, int resource) {
        super(context, resource, messages);
    }

    public void append(String string) {
        messages.add(string);
        notifyDataSetChanged();
    }

}
