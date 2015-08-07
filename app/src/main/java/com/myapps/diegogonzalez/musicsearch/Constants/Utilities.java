package com.myapps.diegogonzalez.musicsearch.Constants;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import java.util.regex.PatternSyntaxException;

/**
 * Created by Diego Gonzalez on 8/3/2015.
 */
public class Utilities {
    /* Tag for a songs name*/
    public static final String TAG_TRACK = "trackName";

    /* Tage for artist name*/
    public static final String TAG_ARTIST = "artistName";

    /* Tag for album name*/
    public static final String TAG_ALBUM = "collectionName";

    /* Tag for album cover url*/
    public static final String TAG_ALBUM_COVER = "artworkUrl60";

    /* Tag for album cover url for Lyrics page*/
    public static final String TAG_ALBUM_COVER_BIG = "artworkUrl100";

    /* Application context*/
    private Context context;

    public Utilities(Context context) {
        this.context = context;
    }

    /**
     * Splits a string into an array of strings.
     * @param txt
     * @return
     */
    public String[] request(String txt) {
        try {
            String[] splitArray = txt.split("\\s+");
            return splitArray;
        } catch (PatternSyntaxException ex) {
            Toast.makeText(context, "Error: Pattern Syntax Exception", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    /**
     * Builds the URL string based on the terms entered.
     *
     * @param terms
     * @return
     */
    public String buildURL(String[] terms, String url) {
        String searchURL = url;
        if (terms == null) {
            Toast.makeText(context, "Error: Terms are null", Toast.LENGTH_LONG).show();
        } else {
            for (int i = 0; i < terms.length; i++) {
                if (i == 0) {
                    searchURL = searchURL + "term=" + terms[i];
                } else {
                    searchURL = searchURL + "+" + terms[i];
                }
            }
        }
        return searchURL;
    }

    /**
     * Method to build url based on the song title and artist
     *
     * @param tNames
     * @param aNames
     * @return
     */
    public String buildLyricsURL(String[] tNames, String[] aNames, String url) {
        String searchURL = url;
        if (tNames == null || aNames == null) {
            Toast.makeText(context, "Error: strings are null", Toast.LENGTH_LONG).show();
        } else {
            for (int i = 0; i < aNames.length; i++) {
                if (i == 0) {
                    searchURL = searchURL + "artist=" + aNames[i];
                } else {
                    searchURL = searchURL + "+" + aNames[i];
                }
            }
            for (int j = 0; j < tNames.length; j++) {
                if (j == 0) {
                    searchURL = searchURL + "&song=" + tNames[j];
                } else {
                    searchURL = searchURL + "+" + tNames[j];
                }
            }
            searchURL += "&fmt=json";
        }
        return searchURL;
    }
}
