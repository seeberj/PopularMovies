package com.example.jude.popularmovies;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PMMainFragment extends Fragment {

    private MovieInfoAdapter movieInfoAdapter;

    MovieInfo[] movieInfos = {
            new MovieInfo("A","http://image.tmdb.org/t/p/w185//inVq3FRqcYIRl2la8iZikYYxFNR.jpg", "Stuff", 1.0,"2016"),
            new MovieInfo("B","http://image.tmdb.org/t/p/w185//inVq3FRqcYIRl2la8iZikYYxFNR.jpg", "Stuff", 2.0,"2016"),
            new MovieInfo("C","http://image.tmdb.org/t/p/w185//kqjL17yufvn9OVLyXYpvtyrFfak.jpg", "Stuff", 3.0,"2016"),
            new MovieInfo("D","http://image.tmdb.org/t/p/w185//jjBgi2r5cRt36xF6iNUEhzscEcb.jpg", "Stuff", 4.0,"2016"),
            new MovieInfo("E","http://image.tmdb.org/t/p/w185//5W794ugjRwYx6IdFp1bXJqqMWRg.jpg", "Stuff", 5.0,"2016"),
            new MovieInfo("F","http://image.tmdb.org/t/p/w185//hE24GYddaxB9MVZl1CaiI86M3kp.jpg", "Stuff", 6.0,"2016"),
            new MovieInfo("G","http://image.tmdb.org/t/p/w185//p11Ftd4VposrAzthkhF53ifYZRl.jpg", "Stuff", 7.0,"2016"),
            new MovieInfo("H","http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "Stuff", 8.0,"2016"),
            new MovieInfo("I","http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "Stuff", 9.0,"2016"),
            new MovieInfo("J","http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "Stuff", 10.0,"2016"),
    };


    public PMMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pmmain, container, false);

        //http://stackoverflow.com/questions/31842150/custom-adapter-does-not-clear-should-i-override

        movieInfoAdapter = new MovieInfoAdapter(getActivity(), new ArrayList<> (Arrays.asList(movieInfos)));
        // Inflate the layout for this fragment

        GridView gridView = (GridView) rootView.findViewById(R.id.movies_grid);
        gridView.setAdapter(movieInfoAdapter);

        return rootView;
    }

    private void updateMovies() {
      FetchMoviesTask moviesTask = new FetchMoviesTask();
      moviesTask.execute("None");
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
                        singleMovieInfo.getDouble(MDB_USER_RATING),
                        singleMovieInfo.getString(MDB_REL_DATE)));

//                movieInfo[i].setPosterThumbnail("http://image.tmdb.org/t/p/w185//kqjL17yufvn9OVLyXYpvtyrFfak");//+ singleMovieInfo.getString(MDB_POSTER);
//                movieInfo[i].setSynopsis(singleMovieInfo.getString(MDB_OVERVIEW));
//                movieInfo[i].setTitle(singleMovieInfo.getString(MDB_ORIGINAL_TITLE));
//                movieInfo[i].releaseDate     = singleMovieInfo.getString(MDB_REL_DATE);
//                movieInfo[i].userRating      = singleMovieInfo.getDouble(MDB_USER_RATING);
            }
            return movieInfo;
        }

        @Override
        protected List<MovieInfo> doInBackground(String... params) {
            // If there's no zip code, there's nothing to look up.  Verify size of params.
            if (params.length == 0) {
                return null;
            }

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String moviesJsonStr = null;
            String popularity = "popularity.desc";

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                final String MOVIEDB_POPULAR_BASE_URL = "http://api.themoviedb.org/3/discover/movie??";

                final String API_KEY_PARAM = "api_key";
                final String SORT_BY_PARAM = "sort_by";
                Uri builtMovieUri = Uri.parse(MOVIEDB_POPULAR_BASE_URL).buildUpon()
                        .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIE_DB_API_KEY)
                        .appendQueryParameter(SORT_BY_PARAM, popularity).build();

                URL movieUrl = new URL(builtMovieUri.toString());

                Log.v(LOG_TAG, "Built MOVIE URI " + builtMovieUri.toString());


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
                return getMovieInfoFromJson(moviesJsonStr); //, numDays);
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
