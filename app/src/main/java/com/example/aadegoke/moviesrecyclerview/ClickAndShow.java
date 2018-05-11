package com.example.aadegoke.moviesrecyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ClickAndShow extends AppCompatActivity {
    TextView description;
    ImageView pic;
    Toolbar ItemToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_and_show);
        Intent intent = getIntent();
        String message = intent.getStringExtra("description");
        String imgURL = intent.getStringExtra("Image");
        String title = intent.getStringExtra("Title");
        description = findViewById(R.id.contain_descript);
        pic = findViewById(R.id.MoviePic);
        description.setText(message);
        ItemToolbar = findViewById(R.id.mydisplaytoolbar);
        ItemToolbar.setTitle(title);
        Picasso.get().load("http://image.tmdb.org/t/p/w185/"+imgURL).into(pic);
    }
}
