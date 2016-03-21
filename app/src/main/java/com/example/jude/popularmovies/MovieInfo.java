package com.example.jude.popularmovies;

/**
 * Created by Jude on 3/6/2016.
 * original title
 * movie poster image thumbnail
 * A plot synopsis (called overview in the api)
 * user rating (called top_rated in the api)
 * release date
 */
public class MovieInfo {

    String title;
    String posterThumbnail;
    String synopsis;
    String userRating;
    String releaseDate;

    public MovieInfo() {
        this.title           = "title";
        this.posterThumbnail = "posterThumbnail";
        this.synopsis        = "synopsis";
        this.userRating      =  "0.0";
        this.releaseDate     = null;
    }

    public MovieInfo(String title, String posterThumbnail, String synopsis,
                     String userRating, String releaseDate ) {
        this.title           = title;
        this.posterThumbnail = posterThumbnail;
        this.synopsis        = synopsis;
        this.userRating      = userRating;
        this.releaseDate     = releaseDate;
    }

}
