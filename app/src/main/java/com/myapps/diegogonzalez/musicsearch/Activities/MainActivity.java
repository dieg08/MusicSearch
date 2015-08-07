package com.myapps.diegogonzalez.musicsearch.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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

import java.util.regex.PatternSyntaxException;

/**
 * Created by Diego Alanis on 7/24/2015.
 */

public class MainActivity extends Activity {

    /* Tag used for logging*/
    public static final String REQUEST_TAG = "MainActivity";
    /* Edit text used to capture user input*/
    private EditText text;
    /* Queue used for making network requests*/
    private RequestQueue mQueue;
    /* URL used to request information*/
    private String url = "https://itunes.apple.com/search?";
    /* Utilities class for needed methods*/
    private Utilities utilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // INnstantiate the utilities
        utilities = new Utilities(this);
        // Instantiate the EditText
        text = (EditText) findViewById(R.id.editText);
        text.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        // Instantiate the RequestQueue.
        mQueue = Volley.newRequestQueue(this);

        // Listen for keyboard enter
        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submit(v);
                    handled = true;
                }
                return handled;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * The function called when a user presses the submit button. Makes a request for the songs
     * and albums based on the words searched for,
     *
     * @param view
     */
    public void submit(View view) {
        //getting the user input
        String[] searchTerms = utilities.request(text.getText().toString());
        String searchURL = utilities.buildURL(searchTerms, url);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, searchURL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                            // The commented out code is for Recycler list view with cards
                            //Intent i = new Intent(getApplicationContext(), RecyclerList.class);
                            Intent i = new Intent(getApplicationContext(), ActivityTwo.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("response", response);
                            i.putExtras(bundle);
                            startActivity(i);
                    }
                }, new Response.ErrorListener() {


            // Gets called if an error occurs
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: Error On Response", Toast.LENGTH_LONG).show();
            }
        });

        // Add request to queue
        mQueue.add(stringRequest);
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
}