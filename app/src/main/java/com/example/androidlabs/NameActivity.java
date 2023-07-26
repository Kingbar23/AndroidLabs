package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NameActivity extends AppCompatActivity {

    TextView welcomeTextView;
    Button dontCallMeButton,thankYouButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        welcomeTextView = findViewById(R.id.welcomeTextView);
        dontCallMeButton = findViewById(R.id.dontCallMeThatButton);
        thankYouButton = findViewById(R.id.thankYouButton);

        Intent intent = getIntent();
        if (intent != null) {
            String userName = intent.getStringExtra("user_name");
            // Update the TextView to welcome the user
            if (userName != null && !userName.isEmpty()) {
                String welcomeMessage = "Welcome " + userName + "!";
                welcomeTextView.setText(welcomeMessage);
            }
        }

        dontCallMeButton.setOnClickListener(view -> onDontCallMeThatClick());
        thankYouButton.setOnClickListener(view -> onThankYouClick());
    }

    public void onThankYouClick() {
        // Set the result code to 0 (change name) and return to the previous activity (MainActivity)
        setResult(1);
        finish();
    }

    public void onDontCallMeThatClick() {
        // Set the result code to 1 (happy) and return to the previous activity (MainActivity)
        setResult(0);
        finish();
    }
}