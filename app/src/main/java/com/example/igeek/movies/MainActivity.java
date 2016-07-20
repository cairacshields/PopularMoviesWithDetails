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


    List<String> moviesList;
    GridView gv;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gv = (GridView)findViewById(R.id.gridView);
        //imageView = (ImageView)findViewById(R.id.grid_item_image);
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

    public void updateDisplay(){


        if(moviesList != null){

            for(String movie : moviesList){
//
            }
        }
    }

    public class FetchData extends AsyncTask<String, String, String>{


        @Override
        protected String doInBackground(String... params) {
            return HttpManager.getData(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            moviesList = MoviesJSONParse.parseFeed(s);
            //updateDisplay();
            gv.setAdapter(new GridAdapter(MainActivity.this, MoviesJSONParse.urlStr));

            super.onPostExecute(s);
        }
    }

    public class GridAdapter extends BaseAdapter{


        private Context context;
        ArrayList<String> images;

        public GridAdapter(Context context,ArrayList<String> images){
            this.context = context;
            this.images = images;
            notifyDataSetChanged();
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

            View view = null;

            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.grid_item_layout, viewGroup, false);
                imageView = (ImageView)convertView.findViewById(R.id.grid_item_image);
            }else{
                imageView = (ImageView)convertView;
            }
            Picasso.with(context)
                    .load(images.get(i))
                    .into(imageView);
            return convertView;
        }
    }
}
