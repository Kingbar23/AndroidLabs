package com.example.cst2335lab7;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DetailsFragment extends Fragment {

    private TextView fillMeTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        fillMeTextView = view.findViewById(R.id.fill_me);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String character = bundle.getString("character");
            fillMeTextView.setText(character);
        }

        return view;
    }
}