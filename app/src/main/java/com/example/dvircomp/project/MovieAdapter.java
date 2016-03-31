package com.example.dvircomp.project;


import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dvircomp.project.AsyncTasks.DownloadImageAsyncTask;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private Activity activity;
    private ArrayList<Movie> movies;
    private LayoutInflater layoutInflater;
    private float RateColorIndicator;

    public MovieAdapter(Activity activity, ArrayList<Movie> movies) {
        super(activity, 0, movies);

        this.activity = activity;
        this.movies = movies;

        layoutInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = movies.get(position);

        View itemLayout = layoutInflater.inflate(R.layout.item_movie, null);
        // change the font
        Typeface customFont = Typeface.createFromAsset(activity.getAssets(), "fonts/quanton.otf");
        TextView textViewSubject = (TextView) itemLayout.findViewById(R.id.textViewSubject);
        TextView textViewYearMin = (TextView) itemLayout.findViewById(R.id.textViewYearMin);
        TextView textViewBody = (TextView) itemLayout.findViewById(R.id.textViewBody);
        TextView textViewRate = (TextView) itemLayout.findViewById(R.id.textViewRate);
        TextView textViewStarIndicator = (TextView) itemLayout.findViewById(R.id.textViewStarIndicator);
        TextView textViewGenre = (TextView) itemLayout.findViewById(R.id.textViewGenre);
        ImageView imageViewWatch = (ImageView) itemLayout.findViewById(R.id.imageViewWatched);

        String title = movie.getSubject();
        String body = movie.getBody();
        String year = movie.getYear();
        String runTime = movie.getRunTime();
        String imdbRate = movie.getRating();
        String genre = movie.getGenre();
        String url = movie.getUrl();
        //Taking the rating and turn it into float if imdbRate = "N/A" or any other exception that movie
        //will be marked as 1 = red color star
        try {
            RateColorIndicator = Float.parseFloat(imdbRate);
        } catch (Exception ex) {
            RateColorIndicator = 1;
        }
        //Choose the color from the rating and change the color if needed to red(1) - 10(green)
        switch (Math.round(RateColorIndicator)) {
            case 0:
            case 1:
            case 2:
                textViewStarIndicator.setTextColor(Color.RED);
                break;
            case 3:
            case 4:
                textViewStarIndicator.setTextColor(Color.rgb(128, 0, 0));
                break;
            case 5:
            case 6:
                textViewStarIndicator.setTextColor(Color.rgb(34, 139, 34));
                break;
            case 7:
            case 8:
                textViewStarIndicator.setTextColor(Color.rgb(124, 252, 0));
                break;
            case 9:
            case 10:
                textViewStarIndicator.setTextColor(Color.rgb(0, 255, 0));
                break;
            default:
                textViewStarIndicator.setTextColor(Color.RED);
        }

        // Checking if the user watched the movie if yes its show eye icon if not the icon is hidden.
        boolean watch = movie.isWatch();
        if (watch) {
            imageViewWatch.setVisibility(View.VISIBLE);
        } else {
            imageViewWatch.setVisibility(View.INVISIBLE);
        }

        textViewSubject.setTypeface(customFont);
        textViewSubject.setText(title);
        textViewBody.setText(body);
        textViewGenre.setText(genre);
        textViewRate.setText(" Rating: " + imdbRate);
        textViewYearMin.setText(year + " | " + runTime);

        ImageView imageViewMovie = (ImageView) itemLayout.findViewById(R.id.imageViewMovie);
        //Setting the images by using asyncTask
        new DownloadImageAsyncTask(imageViewMovie).execute(url);

        return itemLayout;

    }

}
