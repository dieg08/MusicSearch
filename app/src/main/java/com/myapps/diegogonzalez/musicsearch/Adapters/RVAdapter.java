package com.myapps.diegogonzalez.musicsearch.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    private ArrayList<HashMap<String, String>> trackList;
    private Context context;
    private Utilities util;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public ImageView albumCover;
        public TextView songTitle;
        public TextView artist;
        public TextView albumName;

        public ViewHolder(View view) {
            super(view);
            cv = (CardView) view.findViewById(R.id.cv);
            albumCover = (ImageView) view.findViewById(R.id.aCover);
            songTitle = (TextView) view.findViewById(R.id.songName);
            artist = (TextView) view.findViewById(R.id.songArtist);
            albumName = (TextView) view.findViewById(R.id.songAlbum);
        }
    }

    public RVAdapter(Context context, ArrayList<HashMap<String, String>> trackList) {
        this.trackList = trackList;
        this.context = context;
        this.util = new Utilities(context);
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        RVAdapter.ViewHolder holder = new RVAdapter.ViewHolder(v);
        return  holder;
    }

    @Override
    public void onBindViewHolder(RVAdapter.ViewHolder holder, int position) {
        HashMap<String, String> item = trackList.get(position);

        Picasso.with(context)
                .load(item.get(util.TAG_ALBUM_COVER))
                .into(holder.albumCover);
        holder.songTitle.setText(item.get(util.TAG_TRACK));
        holder.artist.setText(item.get(util.TAG_ARTIST));
        holder.albumName.setText(item.get(util.TAG_ALBUM));
    }


}
