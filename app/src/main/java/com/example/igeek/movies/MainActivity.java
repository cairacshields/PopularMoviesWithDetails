package com.example.igeek.movies;

import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ArrayList<Movies> moviesList;
    GridView gv;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gv = (GridView)findViewById(R.id.gridView);
       
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //I'm using a URL that is for The Movie Database and provides me with JSON for a list of popular movies
        //Remember that we need to generate a unique API_KEY
        if(id == R.id.data){
            requestData("http://api.themoviedb.org/3/movie/popular?api_key=74010db6f7f2dd66c9af70fcf78a5dbf");
        }
        return super.onOptionsItemSelected(item);
    }

    public void requestData(String url){

        FetchData task = new FetchData();
        task.execute(url);
    }

    //Here is our Async Task sub class that will send the url to our HTTPManager Class and then send the 
    //retreived data to the MoviesJSONParse class which will then give us back the ArrayList<Movies> that we need 
    //for our Adapter!
    public class FetchData extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            return HttpManager.getData(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            moviesList = MoviesJSONParse.parseFeed(s);
            
            //Remeber to set the Adapter! I always forget!
            gv.setAdapter(new GridAdapter(MainActivity.this, moviesList));

            super.onPostExecute(s);
        }
    }

    //Here is our Adapter that extends BaseAdapter. We will override the methods that we need and 
    //do the following implementation.
    public class GridAdapter extends BaseAdapter{

        //For this class, we will need a context and of course an ArrayList of data!
        private Context context;
        ArrayList<Movies> images;

        //Here is our constructor that initially sets our context and our data to the information that we 
        //pass in when setting our adapter to our gridview 
        public GridAdapter(Context context,ArrayList<Movies> images){
            this.context = context;
            this.images = images;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int i) {
            return images.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.grid_item_layout, viewGroup, false);
                imageView = (ImageView) convertView.findViewById(R.id.grid_item_image);
            } else {
                imageView = (ImageView) convertView;
            }

            //Make another instance of our Movies class and set it equal to the current movie by grabbing the position
            Movies movie = (Movies) this.getItem(i);
            
            //Set temporary properties for all the details that you will want to use
            final String title = movie.getTitle();
            final String releaseD = movie.getRelease_date();
            final String overview = movie.getOverview();
            final int votes = movie.getVote_count();
            final String poster = movie.getPoster_path();
            
            //I also use Picasso for simple image loading :)
            Picasso.with(context)
                    .load(poster)
                    .into(imageView);

            //Here is where we handle what happens when a Movie is clicked
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //We will call a custom method that will need certain parameters,
                    //it will need these parameters because they'll be sent to the next activity
                    //So all I did was pass in the variables I created above
                    goToDetails(votes, releaseD, overview, title, poster);
                }
            });
            return convertView;
        }

        //Here is the custom method that will create an intent and attach extra data to it that will 
        //also be sent to the next activity.
        private void goToDetails(int vote, String... params) {

            Intent i = new Intent(context, Details.class);
            //Make sure you remeber the keyword that you assign to each "Extra"
            i.putExtra("VOTE", vote);
            i.putExtra("RELEASE", params[0]);
            i.putExtra("OVERVIEW", params[1]);
            i.putExtra("TITLE", params[2]);
            i.putExtra("POSTER", params[3]);

            context.startActivity(i);
        }
    }
}
