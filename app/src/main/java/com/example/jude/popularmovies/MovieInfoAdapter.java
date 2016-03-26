package com.example.jude.popularmovies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jude on 3/6/2016.
 */
public class MovieInfoAdapter extends ArrayAdapter<MovieInfo> {

    public MovieInfoAdapter(Activity context, List<MovieInfo> movieInfoList) {
        super(context, 0, movieInfoList);
    }
    public MovieInfoAdapter(Activity context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MovieInfo movieInfo = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item,parent,false);
        }
        ImageView posterImage = (ImageView) convertView.findViewById(R.id.poster_image);
        //Using picasso for efficient loading of images
        Picasso.with(getContext()).load(movieInfo.posterThumbnail).into(posterImage);

        return convertView;

    }

    // Adding this method to support getting and saving the instance state of the main fragment
    // https://discussions.udacity.com/t/onsaveinstancestate-and-parcelable-question-suggestions-before-i-start-project-2/39475
    public ArrayList<MovieInfo> getMovieInfoArrayList(){
        int numItems = this.getCount();
        ArrayList<MovieInfo> MovieInfoList = new ArrayList<MovieInfo>(numItems);
        for (int item = 0; item < numItems; item++ )
        {
            MovieInfoList.add(this.getItem(item));
        }
        return MovieInfoList;
    }
}

