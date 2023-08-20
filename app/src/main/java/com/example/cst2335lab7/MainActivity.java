package com.example.cst2335lab7;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> characterNames = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isTablet = getResources().getBoolean(R.bool.is_tablet);

        if (isTablet) {
            setContentView(R.layout.activity_main_tablet);
        } else {
            setContentView(R.layout.activity_main);
        }

        listView = findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, characterNames);
        listView.setAdapter(adapter);

        // Set up AsyncTask to fetch data from the Star Wars API
        new FetchCharactersTask().execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCharacter = characterNames.get(position);

                if (isTablet) {
                    DetailsFragment detailsFragment = new DetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("character", selectedCharacter);
                    detailsFragment.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.detailsContainer, detailsFragment)
                            .commit();
                } else {
                    Intent intent = new Intent(MainActivity.this, EmptyActivity.class);
                    intent.putExtra("character", selectedCharacter);
                    startActivity(intent);
                }
            }
        });
    }

    private class FetchCharactersTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://swapi.dev/api/people/?format=json");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    Scanner scanner = new Scanner(inputStream);
                    StringBuilder response = new StringBuilder();

                    while (scanner.hasNext()) {
                        response.append(scanner.nextLine());
                    }

                    scanner.close();
                    inputStream.close();

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    JSONArray charactersArray = jsonResponse.getJSONArray("results");

                    for (int i = 0; i < charactersArray.length(); i++) {
                        JSONObject character = charactersArray.getJSONObject(i);
                        String name = character.getString("name");
                        characterNames.add(name);
                    }
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
        }
    }
}
