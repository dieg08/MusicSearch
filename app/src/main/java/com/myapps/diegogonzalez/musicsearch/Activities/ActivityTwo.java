package com.myapps.diegogonzalez.musicsearch.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.myapps.diegogonzalez.musicsearch.Adapters.MyAdapter;
import com.myapps.diegogonzalez.musicsearch.Constants.Utilities;
import com.myapps.diegogonzalez.musicsearch.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Diego Alanis on 7/24/2015.
 */

public class ActivityTwo extends ListActivity {

    /* ArrayList used to store the songs found*/
    private ArrayList<HashMap<String, String>> trackList;
    /* Utilities class for needed methods*/
    private Utilities utilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Instatiating the utilities class
        utilities = new Utilities(this);

        // Retrieving the extras from the intent
        Intent i = getIntent();
        Bundle b = i.getExtras();


        ListView lv = getListView();

        trackList = new ArrayList<HashMap<String, String>>();

        if (b.getString("response") != null) {
            try {
                JSONObject json = new JSONObject(b.getString("response"));
                JSONArray tracks = json.getJSONArray("results");
                for (int j = 0; j < tracks.length(); j++) {
                    JSONObject c = tracks.getJSONObject(j);
                    String tName = c.getString(utilities.TAG_TRACK);
                    String artist = c.getString(utilities.TAG_ARTIST);
                    String album = c.getString(utilities.TAG_ALBUM);
                    String albumCover = c.getString(utilities.TAG_ALBUM_COVER);
                    String albumCoverBig = c.getString(utilities.TAG_ALBUM_COVER_BIG);

                    HashMap<String, String> trks = new HashMap<String, String>();
                    trks.put(utilities.TAG_TRACK, tName);
                    trks.put(utilities.TAG_ARTIST, artist);
                    trks.put(utilities.TAG_ALBUM, album);
                    trks.put(utilities.TAG_ALBUM_COVER, albumCover);
                    trks.put(utilities.TAG_ALBUM_COVER_BIG, albumCoverBig);

                    trackList.add(trks);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (trackList.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Error: I messed up the list", Toast.LENGTH_LONG).show();
        }
        MyAdapter adapter = new MyAdapter(this, trackList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String track = trackList.get(position).get(utilities.TAG_TRACK);
                String artist = trackList.get(position).get(utilities.TAG_ARTIST);
                String albumCoverBig = trackList.get(position).get(utilities.TAG_ALBUM_COVER_BIG);
                Bundle b = new Bundle();
                b.putString("track", track);
                b.putString("artist", artist);
                b.putString("cover", albumCoverBig);
                Intent i = new Intent(getApplicationContext(), LyricsActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
