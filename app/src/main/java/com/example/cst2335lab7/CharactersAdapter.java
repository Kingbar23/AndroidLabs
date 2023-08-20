package com.example.cst2335lab7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class CharactersAdapter extends BaseAdapter {

    private Context context;
    private List<String> characterNames;

    public CharactersAdapter(Context context, List<String> characterNames) {
        this.context = context;
        this.characterNames = characterNames;
    }

    @Override
    public int getCount() {
        return characterNames.size();
    }

    @Override
    public Object getItem(int position) {
        return characterNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_character, parent, false);
        }

        TextView characterNameTextView = convertView.findViewById(R.id.character_name);
        characterNameTextView.setText(characterNames.get(position));

        return convertView;
    }
}

