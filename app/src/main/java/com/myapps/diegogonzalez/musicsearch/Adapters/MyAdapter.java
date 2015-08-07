package com.myapps.diegogonzalez.musicsearch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapps.diegogonzalez.musicsearch.Constants.Utilities;
import com.myapps.diegogonzalez.musicsearch.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Diego Gonzalez on 8/3/2015.
 */
public class MyAdapter extends BaseAdapter {

    /* The application context*/
    private Context context;

    /* The inflator tool to inflate a view*/
    private LayoutInflater inflater;

    /* ArrayList of data passed in*/
    private ArrayList<HashMap<String, String>> data;

    /* ArrayList used to store the songs found*/
    private ArrayList<HashMap<String, String>> trackList;

    /* Utilities class for needed methods*/
    private Utilities utilities;

    public MyAdapter(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        HashMap<String, String> item;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        item = data.get(position);
        mViewHolder.track.setText(item.get(utilities.TAG_TRACK));
        mViewHolder.artist.setText(item.get(utilities.TAG_ARTIST));
        mViewHolder.album.setText(item.get(utilities.TAG_ALBUM));
        Picasso.with(context)
                .load(item.get(utilities.TAG_ALBUM_COVER))
                .into(mViewHolder.albumCover);

        return convertView;
    }

    private class ViewHolder {
        ImageView  albumCover;
        TextView track,artist, album;

        public ViewHolder(View item) {
            albumCover = (ImageView) item.findViewById(R.id.AlbumCover);
            track = (TextView) item.findViewById(R.id.trackName);
            artist = (TextView) item.findViewById(R.id.artist);
            album = (TextView) item.findViewById(R.id.album);
        }
    }
}
