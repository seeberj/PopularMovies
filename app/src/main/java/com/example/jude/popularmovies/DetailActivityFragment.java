package com.example.jude.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    private String mTitle;
    private String mSynopsis;
    private String mReleaseDate;
    private String mUserRating;
    private String mPoster;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null){
            Bundle extras = intent.getExtras();
            mTitle = extras.getString("EXTRA_TITLE");
            mPoster = extras.getString("EXTRA_POSTER");
            mReleaseDate = extras.getString("EXTRA_RELEASE_DATE");
            mUserRating = extras.getString("EXTRA_USER_RATING");
            mSynopsis = extras.getString("EXTRA_SYNOPSIS");

            ((TextView) rootView.findViewById(R.id.movie_title_textview)).setText(mTitle);
            ImageView iView = (ImageView) rootView.findViewById(R.id.movie_poster_imageview);
            Picasso.with(getActivity()).load(mPoster).into(iView);
            ((TextView) rootView.findViewById(R.id.release_date_textview)).setText(mReleaseDate);
            ((TextView) rootView.findViewById(R.id.user_rating_textview)).setText(mUserRating + "/10");
            Log.e("MovieDetails", mSynopsis);
            ((TextView) rootView.findViewById(R.id.movie_synopsis_textview)).setText(mSynopsis);

        }
        return rootView;
    }
}
