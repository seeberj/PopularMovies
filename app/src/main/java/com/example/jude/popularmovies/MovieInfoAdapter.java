package com.example.jude.popularmovies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Jude on 3/6/2016.
 */
public class MovieInfoAdapter extends ArrayAdapter<MovieInfo> {

    public MovieInfoAdapter(Activity context, List<MovieInfo> movieInfoList) {
        super(context, 0, movieInfoList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MovieInfo movieInfo = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item,parent,false);
        }
        ImageView posterImage = (ImageView) convertView.findViewById(R.id.poster_image);

        Picasso.with(getContext()).load(movieInfo.posterThumbnail).placeholder(R.drawable.icecream).into(posterImage);

        //posterImage.setImageResource(movieInfo.posterImage);

        return convertView;

    }
}

