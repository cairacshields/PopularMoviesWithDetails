package com.example.igeek.movies;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {

    TextView overview, title, rd;
    ImageView poster;
    String Title, Overview, rDate, Poster;
    int Vote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        overview = (TextView) findViewById(R.id.overview);
        title = (TextView)findViewById(R.id.title);
        rd = (TextView)findViewById(R.id.rd);
        poster = (ImageView)findViewById(R.id.poster);

        Intent i = this.getIntent();

        Title = i.getExtras().getString("TITLE");
        Overview = i.getExtras().getString("OVERVIEW");
        rDate = i.getExtras().getString("RELEASE");
        Poster = i.getExtras().getString("POSTER");
        Vote = i.getExtras().getInt("VOTES");


        updateDisplay();


    }

    public void updateDisplay(){
        overview.setText(Overview);
        title.setText(Title);
        rd.setText(rDate);

        Picasso.with(Details.this)
                .load(Poster)
                .into(poster);

        Toast.makeText(Details.this, "Method was called", Toast.LENGTH_LONG).show();
    }
}
