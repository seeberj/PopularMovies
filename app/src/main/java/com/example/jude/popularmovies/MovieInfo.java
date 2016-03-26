package com.example.jude.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jude on 3/6/2016.
 *
 * The MovieInfo class needs to contain the following details about the movie
 * Original title
 * Link movie poster image thumbnail
 * A plot synopsis (called overview in the api)
 * The user rating (called top_rated in the api)
 * The release date
 *
 * The techniques used in this file were introduced to the author by
 * the Udacity Google+ webcasts
 */
public class MovieInfo implements Parcelable {

    String title;
    String posterThumbnail;
    String synopsis;
    String userRating;
    String releaseDate;

    public MovieInfo (Parcel in){
        title           = in.readString();
        posterThumbnail = in.readString();
        synopsis        = in.readString();
        userRating      = in.readString();
        releaseDate     = in.readString();
    }

    public MovieInfo(String title, String posterThumbnail, String synopsis,
                     String userRating, String releaseDate ) {
        this.title           = title;
        this.posterThumbnail = posterThumbnail;
        this.synopsis        = synopsis;
        this.userRating      = userRating;
        this.releaseDate     = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(title);
      dest.writeString(posterThumbnail);
      dest.writeString(synopsis);
      dest.writeString(userRating);
      dest.writeString(releaseDate);
    }

    public final Parcelable.Creator<MovieInfo> CREATOR = new Parcelable.Creator<MovieInfo>(){

        @Override
        public MovieInfo createFromParcel(Parcel source) {
            return new MovieInfo(source);
        }



        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };
}
