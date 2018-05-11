package com.example.aadegoke.moviesrecyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a.adegoke on 4/27/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    //contains the data
     ArrayList<Movies> moviesList;
    Context context;



    public void setFilter(List<Movies> newList){
        moviesList=new ArrayList<>();
       moviesList.addAll(newList);
        notifyDataSetChanged();
    }

    public MyAdapter(ArrayList<Movies> moviesList, Context ctx)
    {
        this.moviesList = moviesList;
        this.context = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items,parent,false);
        return new MyViewHolder(itemView,context,moviesList);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Movies movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.release_date.setText(movie.getRelease_date());
        holder.overview.setText(movie.getOverview());

        if(!TextUtils.isEmpty(movie.getPoster_path())){
            Picasso.get().load("http://image.tmdb.org/t/p/w185/" + movie.getPoster_path()).into(holder.poster_path);
        }

//        holder.rel_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("tag","A recycler view item was clicked!");
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title, release_date,overview;
        public ImageView poster_path;
        public RelativeLayout rel_layout;
        ArrayList<Movies> moviesList = new ArrayList<>();
        Context context;

        public MyViewHolder(View itemView, Context context, ArrayList<Movies> moviesList) {
            super(itemView);

            this.moviesList = moviesList;
            this.context = context;

            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.title) ;
            release_date = (TextView) itemView.findViewById(R.id.release_date) ;
            overview = (TextView) itemView.findViewById(R.id.overview);
            poster_path = (ImageView) itemView.findViewById(R.id.picture);
            rel_layout = (RelativeLayout) itemView.findViewById(R.id.rel_layout);

        }

        @Override
        //whenever a paticular item in the recycler view is clicked , this method is invoked
        public void onClick(View view) {
            int position = getAdapterPosition();
            Movies objMovie = moviesList.get(position);
            Intent intent = new Intent(context, ClickAndShow.class);
            intent.putExtra("description", objMovie.getOverview());
            intent.putExtra("Image", objMovie.getPoster_path());
            this.context.startActivity(intent);

        }
    }

}
