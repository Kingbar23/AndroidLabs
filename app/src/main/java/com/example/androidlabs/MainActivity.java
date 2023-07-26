package com.example.androidlabs;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView ;
    EditText editText;
    String nameEntered;
    private View parentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.nextButton);
        textView = findViewById(R.id.nameTextview);
        editText = findViewById(R.id.editTextPersonName);
        parentLayout = findViewById(R.id.parent_layout);



        // Load the user's name from SharedPreferences and put it in the EditText
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedName = sharedPreferences.getString("user_name", "");
        if(!savedName.equals(""))
            editText.setText(savedName);

        button.setOnClickListener(view -> {
            nameEntered = String.valueOf(editText.getText());

            // Launch the NameActivity using startActivityForResult
            Intent intent = new Intent(MainActivity.this, NameActivity.class);
            intent.putExtra("user_name",nameEntered);
            nameActivityLauncher.launch(intent);
        });

    }

    private final ActivityResultLauncher<Intent> nameActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == 1) {
                    // If the result code is RESULT_OK (1), the user clicked "Thank You" in NameActivity,
                    // so we can proceed to close the app.
                    finish();
                } else if (result.getResultCode() == 0) {
                    // If the result code is 0, the user wants to change their name.
                }
            }
    );

    @Override
    protected void onPause() {
        // Save the current value inside the EditText to SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_name", nameEntered);
        editor.apply();

        super.onPause();
    }
}