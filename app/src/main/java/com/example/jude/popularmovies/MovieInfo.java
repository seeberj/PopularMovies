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
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterThumbnail() {
        return posterThumbnail;
    }

    public void setPosterThumbnail(String posterThumbnail) {
        this.posterThumbnail = posterThumbnail;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    String title;
    String posterThumbnail;
    String synopsis;
    double userRating;
    String releaseDate;

    public MovieInfo() {
        this.title           = "title";
        this.posterThumbnail = "posterThumbnail";
        this.synopsis        = "synopsis";
        this.userRating      =  0;
        this.releaseDate     = null;
    }

    public MovieInfo(String title, String posterThumbnail, String synopsis,
                     double userRating, String releaseDate ) {
        this.title           = title;
        this.posterThumbnail = posterThumbnail;
        this.synopsis        = synopsis;
        this.userRating      = userRating;
        this.releaseDate     = releaseDate;
    }

}
