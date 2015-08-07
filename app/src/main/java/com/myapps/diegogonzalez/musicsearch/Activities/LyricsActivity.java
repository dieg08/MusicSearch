package com.myapps.diegogonzalez.musicsearch.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.myapps.diegogonzalez.musicsearch.Constants.Utilities;
import com.myapps.diegogonzalez.musicsearch.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.PatternSyntaxException;

/**
 * Created by Diego Alanis on 7/24/2015.
 */

public class LyricsActivity extends Activity {

    /* Tag used for logging*/
    public static final String REQUEST_TAG = "LyricsActivity";

    /* Image view for album cover*/
    private ImageView albumCover;

    /* Text view for the song title*/
    private TextView title;

    /* Text view for artist name*/
    private TextView artist;

    /* Text View for lyrics*/
    private TextView lyrics;

    /* Queue for making network requests*/
    private RequestQueue mQueue;

    /* URL for making requests*/
    private String url ="http://lyrics.wikia.com/api.php?";

    /* String to store artist name*/
    private String mArtist;

    /* String to hold song name*/
    private String mTitle;

    /* String for Album Cover URL*/
    private String coverURL;

    /* URL for full song lyrics*/
    private String lyricsURL;

    /* Utilities class for needed methods*/
    private Utilities utilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);

        // Instantiate the textviews
        title = (TextView) findViewById(R.id.trackTitle);
        artist = (TextView) findViewById(R.id.by);
        lyrics = (TextView) findViewById(R.id.lyrics);
        albumCover = (ImageView) findViewById(R.id.bigCover);

        // Instantiate the utilities
        utilities = new Utilities(this);

        // get bundle elements
        Bundle bundle = getIntent().getExtras();
        mArtist = bundle.getString("artist");
        mTitle = bundle.getString("track");
        coverURL = bundle.getString("cover");

        // Instantiate the RequestQueue.
        mQueue = Volley.newRequestQueue(this);
        getLyrics();
    }

    /**
     * Cancels all requests if called
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (mQueue != null) {
            mQueue.cancelAll(REQUEST_TAG);
        }
    }

    /**
     * Makes request for Lyrics
     */
    private void getLyrics() {
    String lyricURL = utilities.buildLyricsURL(utilities.request(mTitle), utilities.request(mArtist), url);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, lyricURL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        setViews(response);
                    }
                }, new Response.ErrorListener() {


            // Gets called if an error occurs
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: Error On Response", Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(stringRequest);
    }

    /**
     * Sets the text views based on the json response
     * @param response
     */
    public void setViews(String response) {
        if (response != null) {
            try {
                lyrics.setText(response);
                JSONObject json = new JSONObject(response.substring(7));
                title.setText(json.getString("song"));
                artist.setText(json.getString("artist"));
                lyrics.setText(json.getString("lyrics"));
                Picasso.with(this)
                        .load(coverURL)
                        .into(albumCover);

                lyricsURL = json.getString("url");

            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Error: JSON Exception", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    public void launchBrowser(View view)  {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(lyricsURL));
        startActivity(browserIntent);
    }
}
