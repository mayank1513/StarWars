package com.mayank.starwars;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends Activity {
    String baseUrl = "https://swapi.co/api/people/?format=json";
    public static String nextUrl = null;
    ListView listView;
    Button btn;
    FetchData fetchData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bootup).animate().scaleX(2).scaleY(2).alpha(0).setStartDelay(1000).setDuration(1500).start();
        listView = findViewById(R.id.list);
        btn = findViewById(R.id.loadMore);
        fetchData = new FetchData();
        fetchData.execute(baseUrl);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == characters.size()){
                    fetchData = new FetchData();
                    fetchData.execute(nextUrl);
                } else {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    Character c = characters.get(i);
                    intent.putExtra(Main2Activity.NAME, c.Name);
                    intent.putExtra(Main2Activity.HEIGHT, c.Height);
                    intent.putExtra(Main2Activity.MASS, c.Mass);
                    intent.putExtra(Main2Activity.TIME_CREATED, c.createdOn);
                    startActivity(intent);
                }
            }
        });
    }

    private class FetchData extends AsyncTask<String, Integer, String> {
        ProgressBar progressBar;
        int pos = 0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = (ProgressBar) (characters.isEmpty()?findViewById(R.id.progressBar):findViewById(R.id.progressBar1));
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            try {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader((new URL(url)).openConnection().getInputStream()));
                String line;
                while((line = reader.readLine())!=null){
                    sb.append(line);
                }
                JSONObject jsonObject = new JSONObject(sb.toString());
                nextUrl = null;
                nextUrl = jsonObject.getString("next");
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                pos = characters.size();
                for (int i = 0; i<jsonArray.length(); i++){
                    jsonObject = (JSONObject) jsonArray.get(i);
                    characters.add(new Character(jsonObject));
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return "err: " + e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
//                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                btn.setText(R.string.try_again);
                btn.setVisibility(View.VISIBLE);
            }
            progressBar.setVisibility(View.GONE);
            listView.setAdapter(new Adapter(MainActivity.this));
            listView.setSelection(pos - 1);
        }
    }

    public void onClick(View view){
        int id = view.getId();
        if(id == R.id.loadMore){
            nextUrl = null;
            characters.clear();
            fetchData = new FetchData();
            fetchData.execute(baseUrl);
            btn.setVisibility(View.GONE);
        }
    }

    public static ArrayList<Character> characters = new ArrayList<>();
    public class Character{
        String Name, createdOn, Height, Mass;
        Character(JSONObject jsonObject) throws JSONException {
            Name = jsonObject.getString("name");
            Height = jsonObject.getString("height");
            Mass = jsonObject.getString("mass");
            createdOn = jsonObject.getString("created");
        }
    }
}
