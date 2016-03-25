package com.example.jude.popularmovies;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PMMainFragment extends Fragment {

    private MovieInfoAdapter movieInfoAdapter;

    public PMMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pmmain, container, false);

        movieInfoAdapter = new MovieInfoAdapter(getActivity());

        GridView gridView = (GridView) rootView.findViewById(R.id.movies_grid);
        gridView.setAdapter(movieInfoAdapter);

        gridView.setOnItemClickListener( new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieInfo m = movieInfoAdapter.getItem(position);

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                //Use a Bundle to pass movie details to the Detail Activity

                //http://stackoverflow.com/questions/8452526/android-can-i-use-putextra-to-pass-multiple-values
                Bundle extras = new Bundle();
                extras.putString("EXTRA_TITLE", m.title);
                extras.putString("EXTRA_SYNOPSIS", m.synopsis);
                extras.putString("EXTRA_RELEASE_DATE", m.releaseDate);
                extras.putString("EXTRA_POSTER", m.posterThumbnail);
                extras.putString("EXTRA_USER_RATING", m.userRating);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        return rootView;
    }

    //Call the
    private void updateMovies() {
      FetchMoviesTask moviesTask = new FetchMoviesTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = prefs.getString("Order","popular");
      moviesTask.execute(sortBy);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, List<MovieInfo>> {
        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        private List<MovieInfo> getMovieInfoFromJson(String movieListInfoJsonStr)
                throws JSONException
        {
            final String MDB_RESULTS = "results";
            final String MDB_ORIGINAL_TITLE = "original_title";
            final String MDB_POSTER = "poster_path";
            final String MDB_OVERVIEW = "overview";
            final String MDB_USER_RATING = "vote_average";
            final String MDB_REL_DATE = "release_date";

            JSONObject movieListInfoJson = new JSONObject(movieListInfoJsonStr);
            JSONArray movieArray = movieListInfoJson.getJSONArray(MDB_RESULTS);

            Log.v(LOG_TAG, "Number of movies found" + movieArray.length());
            List<MovieInfo> movieInfo = new ArrayList<MovieInfo> ();

            for(int i = 0; i < movieArray.length(); i++) {

                JSONObject singleMovieInfo = movieArray.getJSONObject(i);

                movieInfo.add(new MovieInfo(singleMovieInfo.getString(MDB_ORIGINAL_TITLE),
                        "http://image.tmdb.org/t/p/w185/" + singleMovieInfo.getString(MDB_POSTER),
                        singleMovieInfo.getString(MDB_OVERVIEW),
                        singleMovieInfo.getString(MDB_USER_RATING),
                        singleMovieInfo.getString(MDB_REL_DATE)));
            }
            return movieInfo;
        }

        @Override
        protected List<MovieInfo> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String moviesJsonStr = null;

            try {
                // Construct the URL for the MovieDB query
                final String MOVIEDB_POPULAR_BASE_URL = "http://api.themoviedb.org/3/movie/" + params[0] +"?";

                final String API_KEY_PARAM = "api_key";

                Uri builtMovieUri = Uri.parse(MOVIEDB_POPULAR_BASE_URL).buildUpon()
                        .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIE_DB_API_KEY).build();

                URL movieUrl = new URL(builtMovieUri.toString());

               // Log.v(LOG_TAG, "Built MOVIE URI " + builtMovieUri.toString());


                // Create the request to MovieDB, and open the connection
                urlConnection = (HttpURLConnection) movieUrl.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                moviesJsonStr = buffer.toString();

                Log.v(LOG_TAG, "Movie string: " + moviesJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovieInfoFromJson(moviesJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }

        @Override
        protected void onPostExecute(List<MovieInfo> movieInfos) {
            if(movieInfos.size() > 0){
                movieInfoAdapter.clear();
                for (int i = 0; i < movieInfos.size(); i++){
                    movieInfoAdapter.add(movieInfos.get(i));
                }
            }
        }
    }



}
