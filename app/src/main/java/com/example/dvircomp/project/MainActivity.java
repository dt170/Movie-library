package com.example.dvircomp.project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    // Data Members
    private ArrayList<Movie> movieList = new ArrayList<>();
    private MovieAdapter adapter;
    private ListView listViewMovie;
    private final static int INTERNET_MOVIE = 1;
    private final static int ADD_MANUAL_MOVIE = 2;
    private final static int EDIT_MOVIE = 3;
    private Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Creating the list view using custom adapter
        listViewMovie = (ListView) findViewById(R.id.moviesLayoutList);
        adapter = new MovieAdapter(this, movieList);
        listViewMovie.setAdapter(adapter);
        // Load all the saved data
        Helper.loadFile(MainActivity.this, movieList);
        // calling edit on click function
        editOnClick();
        // calling on long click function
        onLongClickOptions();
    }

    // Handel long press of the user and open options popup menu to edit or delete current movie
    private void onLongClickOptions() {

        listViewMovie.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                PopupMenu popup = new PopupMenu(MainActivity.this, view);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuItemDelete:
                                Toast.makeText(MainActivity.this, "Deleting item", Toast.LENGTH_LONG).show();
                                movieList.remove(position);
                                //Saving the changes
                                Helper.saveFile(MainActivity.this, movieList);
                                break;
                            case R.id.menuItemEdit:

                                Toast.makeText(MainActivity.this, "Editing..", Toast.LENGTH_LONG).show();
                                String subject = movieList.get(position).getSubject();
                                String body = movieList.get(position).getBody();
                                String url = movieList.get(position).getUrl();
                                String movieID = movieList.get(position).getId();
                                String year = movieList.get(position).getYear();
                                String runTime = movieList.get(position).getRunTime();
                                String rate = movieList.get(position).getRating();
                                String genre = movieList.get(position).getGenre();

                                Intent edit = new Intent(MainActivity.this, ManualAddActivity.class);
                                edit.putExtra("subject", subject);
                                edit.putExtra("body", body);
                                edit.putExtra("url", url);
                                edit.putExtra("id", movieID);
                                edit.putExtra("year", year);
                                edit.putExtra("runTime", runTime);
                                edit.putExtra("rate", rate);
                                edit.putExtra("genre", genre);
                                edit.putExtra("position", position);
                                startActivityForResult(edit, EDIT_MOVIE);
                                break;
                        }
                        adapter.notifyDataSetChanged();
                        return true;
                    }
                });

                popup.inflate(R.menu.long_press_options);
                popup.show();

                return true;
            }
        });
    }

    // Opens new intent for editing movies that already in your list + sending info about chosen movie..
    private void editOnClick() {

        listViewMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this, "Editing..", Toast.LENGTH_LONG).show();

                String subject = movieList.get(position).getSubject();
                String body = movieList.get(position).getBody();
                String url = movieList.get(position).getUrl();
                String movieID = movieList.get(position).getId();
                String year = movieList.get(position).getYear();
                String runTime = movieList.get(position).getRunTime();
                String rate = movieList.get(position).getRating();
                String genre = movieList.get(position).getGenre();
                boolean watch = movieList.get(position).isWatch();

                Intent edit = new Intent(MainActivity.this, ManualAddActivity.class);

                edit.putExtra("subject", subject);
                edit.putExtra("body", body);
                edit.putExtra("url", url);
                edit.putExtra("id", movieID);
                edit.putExtra("year", year);
                edit.putExtra("runTime", runTime);
                edit.putExtra("rate", rate);
                edit.putExtra("genre", genre);
                edit.putExtra("watch",watch);
                edit.putExtra("position", position);
                startActivityForResult(edit, EDIT_MOVIE);

            }
        });
    }

    // Open a dialog that the user can choose witch way he wants to add movie Manual or Internet
    public void imageButtonAdd_onClick(View view) {

        AlertDialog dialog = new AlertDialog.Builder(this).create();

        dialog.setTitle("Add Movie");
        dialog.setMessage("Choose the way you want to add");
        dialog.setIcon(R.drawable.dialog_image);
        dialog.setButton(Dialog.BUTTON_NEUTRAL, "Internet", this);
        dialog.setButton(Dialog.BUTTON_POSITIVE, "Manual", this);

        dialog.setCancelable(true);
        dialog.show();
    }

    // Opens new intents: Manual add, and internet add in order to receive information from the selected activity
    public void onClick(DialogInterface dialog, int which) {

        switch (which) {
            case Dialog.BUTTON_NEUTRAL:
                intent = new Intent(this, InternetChooseActivity.class);
                startActivityForResult(intent, INTERNET_MOVIE);
                break;

            case Dialog.BUTTON_POSITIVE:
                intent = new Intent(this, ManualAddActivity.class);
                startActivityForResult(intent, ADD_MANUAL_MOVIE);
                break;
        }
    }

    // Manage the information that received from the other activitys Internet add , Manual add and Editing .
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case INTERNET_MOVIE:
                String subject = data.getStringExtra("title");
                String body = data.getStringExtra("body");
                String id = data.getStringExtra("id");
                String url = data.getStringExtra("url");
                String year = data.getStringExtra("year");
                String runTime = data.getStringExtra("runTime");
                String imdbRate = data.getStringExtra("imdbRate");
                String genre = data.getStringExtra("genre");
                movieList.add(new Movie(subject, body, id, url, year, runTime,imdbRate,genre,false));
                break;
            case ADD_MANUAL_MOVIE:
                String subjectManual = data.getStringExtra("subject");
                String bodyManual = data.getStringExtra("body");
                String urlManual = data.getStringExtra("url");
                String yearManual = data.getStringExtra("year");
                String runTimeManual = data.getStringExtra("runTime");
                String genreManual = data.getStringExtra("genre");
                String rateManual = data.getStringExtra("rate");
                boolean watch = data.getBooleanExtra("watch",false);
                movieList.add(new Movie(subjectManual, bodyManual, urlManual,yearManual,runTimeManual,genreManual,rateManual,watch));
                break;
            case EDIT_MOVIE:
                int position = data.getIntExtra("position", 0);
                movieList.get(position).setSubject(data.getStringExtra("subject"));
                movieList.get(position).setBody(data.getStringExtra("body"));
                movieList.get(position).setUrl(data.getStringExtra("url"));
                movieList.get(position).setYear(data.getStringExtra("year"));
                movieList.get(position).setRunTime(data.getStringExtra("runTime"));
                movieList.get(position).setGenre(data.getStringExtra("genre"));
                movieList.get(position).setRating(data.getStringExtra("rate"));
                movieList.get(position).setWatch(data.getBooleanExtra("watch",false));
                break;
        }
        Helper.saveFile(MainActivity.this, movieList);
        adapter.notifyDataSetChanged();
    }

    // Preference Popup menu : clear files and exit app
    public void imageButtonPreference_onClick(View view) {
       PopupMenu preference = new PopupMenu(MainActivity.this,view);
        preference.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuAllItemDelete:
                        adapter.clear();
                        Helper.clearFile(MainActivity.this);
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.menuExit:
                        finish();
                        break;
                }
                return true;
            }

        });
        preference.inflate(R.menu.main_activity_preference);
        preference.show();

    }

}
