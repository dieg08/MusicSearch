package com.myapps.diegogonzalez.musicsearch.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.myapps.diegogonzalez.musicsearch.Adapters.RVAdapter;
import com.myapps.diegogonzalez.musicsearch.Constants.Utilities;
import com.myapps.diegogonzalez.musicsearch.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerList extends Activity {

    private Utilities utilities;
    private ArrayList<HashMap<String, String>> trackList;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_list);

        // Instatiating the utilities class
        utilities = new Utilities(this);

        // Retrieving the extras from the intent
        Intent i = getIntent();
        Bundle b = i.getExtras();

        trackList = new ArrayList<HashMap<String, String>>();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

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

        adapter = new RVAdapter(this, trackList);
        mRecyclerView.setAdapter(adapter);
    }

}
