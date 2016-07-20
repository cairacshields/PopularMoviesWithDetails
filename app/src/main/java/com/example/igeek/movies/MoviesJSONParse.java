package com.example.igeek.movies;

import android.widget.BaseAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igeek on 7/16/16.
 */
public class MoviesJSONParse {

    static ArrayList<String> urlStr = new ArrayList<>();


    public static ArrayList<String> parseFeed(String content){

        final String URL_BASE = "http://image.tmdb.org/t/p/w185";

        try {
            //since our JSON Data begins with an object we have to first intitialize a new JSONObject
            JSONObject ar = new JSONObject(content);
            //After we've initialized our base JSON object we can then reach into the main inner
            //array which holds an array of different movie objects
            //So, declare a new JSONArray and then utilize the method "getJSONArray" to pass in the name of the array that holds our movie objects
            //In our case, the name of the array that holds the movie objects is "results"
            JSONArray mAR = ar.getJSONArray("results");

            //Then we declare a list of Movie Objects that will hold our movie data
            List<Movies> mMovies = new ArrayList<>();
            //Make sure we clear our list before adding new values to avoid getting duplicates
            urlStr.clear();
            //Then we need to use a for loop to iterate through each particular object in the "results" array
            for(int i=0; i< mAR.length(); i++){

                //Here we declare another JSONObject which will temporarily hold the current object and enable
                //us to grab all of its properties before it switches to the next object and repeats the process
                JSONObject obj = mAR.getJSONObject(i);
                //Herr I am creating a Completed URL from the provided poster path by attaching the Base Url to the poster path
                String moviePoster = obj.getString("poster_path");
                //Here is my completed Url
                String URL_COMPLETE = URL_BASE + moviePoster;
                //We also need to get an instance of our Movies POJO class which will help us to access each property
                Movies movie = new Movies();


                movie.setId(obj.getInt("id"));
                movie.setRelease_date(obj.getString("release_date"));
                movie.setOriginal_title(obj.getString("original_title"));
                movie.setOverview(obj.getString("overview"));
                movie.setPopularity(obj.getDouble("popularity"));
                movie.setTitle(obj.getString("title"));
                movie.setPoster_path(obj.getString("poster_path"));
                movie.setVote_count(obj.getInt("vote_count"));

                //After we've set each property for the current object, we will need to add it to our mMovies list
                mMovies.add(movie);
                //Each time the loop goes around, it will add another movie poster URL to the ArrayList
                urlStr.add(URL_COMPLETE);
            }
            //After each object has been added to the list, we will return that list for our application to use
            return urlStr;
             } catch (JSONException e) {
                  e.printStackTrace();
                return null;
        }


    }

}
