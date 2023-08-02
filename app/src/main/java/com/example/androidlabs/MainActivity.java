package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private EditText editText;
    private Switch switchUrgent;
    private Button btnAdd;
    private List<TodoItem> todoList; // Use List<TodoItem> instead of ArrayList<TodoItem>
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        listView = findViewById(R.id.listView);
        editText = findViewById(R.id.editText);
        switchUrgent = findViewById(R.id.switchUrgent);
        btnAdd = findViewById(R.id.btnAdd);

        // Initialize the todoList List to store todo items
        todoList = new ArrayList<>();

        // Initialize the BaseAdapter and set it to the ListView
        adapter = new CustomListAdapter(this, todoList);
        listView.setAdapter(adapter);

        // Set the onClickListener for the Add Button
        btnAdd.setOnClickListener(v -> addItemToList());

        // Set the onItemLongClickListener for the ListView
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            showDeleteConfirmationDialog(position);
            return true;
        });
    }

    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.alert_dialog_title));
        builder.setMessage(getString(R.string.alert_dialog_message) +  " " + position);
        builder.setPositiveButton(getString(R.string.alert_dialog_positive) , (dialog, which) -> deleteItem(position));
        builder.setNegativeButton(getString(R.string.alert_dialog_negative), null);
        builder.show();
    }

    private void deleteItem(int position) {
        if (position >= 0 && position < todoList.size()) {
            todoList.remove(position);
            adapter.notifyDataSetChanged();
        }
    }


    private void addItemToList() {
        // Get the title from the EditText
        String text = editText.getText().toString().trim();

        // Check if the title is not empty
        if (!text.isEmpty()) {
            // Get the urgency status from the Switch
            boolean isUrgent = switchUrgent.isChecked();

            // Create a new TodoItem with the title and urgency status
            TodoItem todoItem = new TodoItem(text, isUrgent);

            // Add the new todoItem to the todoList
            todoList.add(todoItem);

            // Clear the EditText
            editText.getText().clear();

            // Notify the adapter that the data has changed
            adapter.notifyDataSetChanged();
        }
    }

    // Custom BaseAdapter class
    private static class CustomListAdapter extends BaseAdapter {
        private Context context;
        private List<TodoItem> todoList;

        CustomListAdapter(Context context, List<TodoItem> todoList) {
            this.context = context;
            this.todoList = todoList;
        }

        @Override
        public int getCount() {
            return todoList.size();
        }

        @Override
        public Object getItem(int position) {
            return todoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Reuse views if possible
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            // Get the TodoItem at the current position
            TodoItem todoItem = todoList.get(position);

            // Set the text of the TextView to the todo text
            TextView textView = convertView.findViewById(android.R.id.text1);
            textView.setText(todoItem.getText());

            // If the todo item is urgent, set the background to red and text color to white
            if (todoItem.isUrgent()) {
                convertView.setBackgroundColor(Color.RED);
                textView.setTextColor(Color.WHITE);
            } else {
                // Reset background and text color for non-urgent items
                convertView.setBackgroundColor(Color.TRANSPARENT);
                textView.setTextColor(Color.BLACK);
            }

            return convertView;
        }
    }
}

