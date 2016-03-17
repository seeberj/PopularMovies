package com.example.jude.popularmovies;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    private String mTitle;
    private String mSynopsis;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null){
            Bundle extras = intent.getExtras();
            mTitle = extras.getString("EXTRA_TITLE");
            mSynopsis = extras.getString("EXTRA_SYNOPSIS");
            ((TextView) rootView.findViewById(R.id.movie_title_textview)).setText(mTitle);
            ((TextView) rootView.findViewById(R.id.movie_synopsis_textview)).setText(mSynopsis);
        }
        return rootView;
    }
}
