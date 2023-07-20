package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    CheckBox checkBox;
    boolean checkBoxChecked;
    private View parentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grid);

        button = findViewById(R.id.button2);
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        checkBox = findViewById(R.id.checkBox);
        checkBoxChecked = false;
        parentLayout = findViewById(R.id.parent_layout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredText = String.valueOf(editText.getText());
                textView.setText(enteredText);

                Toast.makeText(getApplicationContext(), R.string.toast_message,Toast.LENGTH_LONG).show();
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Snackbar snackbar;
                if(b) {
                    snackbar = Snackbar.make(parentLayout,getString(R.string.checkBox_state)+ " " + getString(R.string.on),BaseTransientBottomBar.LENGTH_LONG);
                } else {
                    snackbar = Snackbar.make(parentLayout, getString(R.string.checkBox_state)+ " "  +  getString(R.string.off), BaseTransientBottomBar.LENGTH_LONG);
                }
                snackbar.setAction("Undo", click -> checkBox.setChecked(!b));
                snackbar.show();

            }
        });
    }
}