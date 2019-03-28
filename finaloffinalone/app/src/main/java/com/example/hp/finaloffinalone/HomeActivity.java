package com.example.hp.finaloffinalone;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    Typeface regular,bold;
    FontChanger regularFontChanger,boldFontChanger;
    RecyclerView moviesRV;
    List<com.example.hp.finaloffinalone.Movie> movieList;
    MovieAdapter movieAdapter;
    LinearLayoutManager layoutManager;
    ImageView backdropIV;
    SnapHelper snapHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        init();
        regularFontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));
        for(int i=0;i< GlobalData.movies.length;i++){
            List<String> genreList = new ArrayList<>();
            genreList.add(GlobalData.genres[i]);
            com.example.hp.finaloffinalone.Movie movie = new com.example.hp.finaloffinalone.Movie();
            movie.setOriginalTitle(GlobalData.movies[i]);
            movie.setPosterPath(GlobalData.posters[i]);
            movie.setOverview(GlobalData.desc[i]);
            movie.setGenres(genreList);
            movieList.add(movie);
            movieAdapter.notifyDataSetChanged();
        }
        Picasso.with(getApplicationContext()).load(movieList.get(0).getPosterPath()).into(backdropIV);


    }

    public void init(){

        //Changing the font throughout the activity

        regular = Typeface.createFromAsset(getAssets(), "fonts/product_san_regular.ttf");
        bold = Typeface.createFromAsset(getAssets(),"fonts/product_sans_bold.ttf");
        regularFontChanger = new FontChanger(regular);
        boldFontChanger = new FontChanger(bold);
        moviesRV = findViewById(R.id.moviesRV);
        movieList = new ArrayList<com.example.hp.finaloffinalone.Movie>();
        movieAdapter = new MovieAdapter(movieList,HomeActivity.this);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(moviesRV);
        moviesRV.setLayoutManager(layoutManager);
        moviesRV.addItemDecoration(new LinePagerIndicatorDecoration(HomeActivity.this));
        moviesRV.setAdapter(movieAdapter);

        backdropIV = findViewById(R.id.backdropIV);
        MiddleItemFinder.MiddleItemCallback callback =
                new MiddleItemFinder.MiddleItemCallback() {
                    @Override
                    public void scrollFinished(int middleElement) {
                        // interaction with middle item
                        onActiveCardChange(middleElement);
                    }
                };
        moviesRV.addOnScrollListener(
                new MiddleItemFinder(getApplicationContext(), layoutManager,
                        callback, RecyclerView.SCROLL_STATE_IDLE));




    }

    public void onActiveCardChange(int pos){

        Picasso.with(getApplicationContext()).load(movieList.get(pos).getPosterPath()).into(backdropIV);


    }



}
