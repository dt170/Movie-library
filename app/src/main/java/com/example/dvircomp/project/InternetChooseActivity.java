package com.example.dvircomp.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dvircomp.project.AsyncTasks.MovieAsyncTask;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class InternetChooseActivity extends AppCompatActivity implements MovieAsyncTask.Callback {

    private ListView searchList;
    private ProgressDialog dialog;
    private ArrayAdapter<Movie> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_choose);
        searchList = (ListView) findViewById(R.id.searchList);
        //Calling the function onClickAddMovieByPosition
        onClickAddMovieByPosition();
        //disabling landscape only in this activity to prevent losing data in search
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    // Finish the current activity without saving
    public void buttonCancel_onClick(View view) {
        finish();
    }

    //After click on the search image its search by name using AsyncTask
    public void imageButtonSearch_onClick(View view) throws MalformedURLException {

        EditText editTextMovieName = (EditText) findViewById(R.id.editTextMovieName);
        String title = editTextMovieName.getText().toString();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(InternetChooseActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        URL url = new URL("http://www.omdbapi.com/?s=" + title.replace(" ", "+"));

        MovieAsyncTask movieAsyncTask = new MovieAsyncTask(this);
        movieAsyncTask.execute(url);
    }

    // Taking all the information from the chosen movie and send it to Main Activity in order to add it .
    private void onClickAddMovieByPosition() {
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Movie movie = (Movie) parent.getItemAtPosition(position);

                String title = movie.getSubject();
                String idImdb = movie.getId();
                String url = movie.getUrl();
                String body = movie.getBody();
                String year = movie.getYear();
                String runTime = movie.getRunTime();
                String imdbRate = movie.getRating();
                String genre = movie.getGenre();

                Intent intent = new Intent();
                intent.putExtra("title", title);
                intent.putExtra("body", body);
                intent.putExtra("id", idImdb);
                intent.putExtra("url", url);
                intent.putExtra("year", year);
                intent.putExtra("runTime", runTime);
                intent.putExtra("imdbRate", imdbRate);
                intent.putExtra("genre", genre);

                setResult(RESULT_OK, intent);
                finish();

            }
        });

    }
    // Using Callback Design Pattern in order to separate the AsyncTask from the UI
    @Override
    public void onAboutToStart() {
        dialog = new ProgressDialog(this);
        dialog.setTitle("Connecting...");
        dialog.setMessage("Please Wait...");
        dialog.show();
    }
    // Using Callback Design Pattern in order to separate the AsyncTask from the UI
    @Override
    public void onSuccess(ArrayList<Movie> Result) {
        dialog.dismiss();
        adapter = new ArrayAdapter<Movie>(this, android.R.layout.simple_list_item_1, Result);
        ListView listView = (ListView) this.findViewById(R.id.searchList);
        listView.setAdapter(adapter);
    }
    // Using Callback Design Pattern in order to separate the AsyncTask from the UI
    @Override
    public void onError(String errorMessage) {
        dialog.dismiss();
        Toast.makeText(this,"Error: "+errorMessage,Toast.LENGTH_LONG).show();
    }
}