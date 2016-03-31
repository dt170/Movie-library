package com.example.dvircomp.project.AsyncTasks;



import android.os.AsyncTask;
import com.example.dvircomp.project.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MovieAsyncTask extends AsyncTask<URL, Void, ArrayList<Movie>> {

    private ArrayList<Movie> movieSearch = new ArrayList<>();
    private Callback callback;
    private String errorMessage = null;

    public MovieAsyncTask(Callback callback ) {
        this.callback = callback;
    }

    // Creating a progress dialog on the UI by calling onAboutToStart function
    protected void onPreExecute() {
        callback.onAboutToStart();
    }

    //Performing the AsyncTask that needed in order to get all the movies info (using Json)
    @Override
    protected ArrayList<Movie> doInBackground(URL... params) {

        try {
            URL url = params[0];
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            int httpStatusCode = connection.getResponseCode();

            if (httpStatusCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                errorMessage = "No Such Symbol";
                return null;
            }
            if (httpStatusCode != HttpURLConnection.HTTP_OK) {
                errorMessage = connection.getResponseMessage();
                return null;
            }

            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String result = "";

            String oneLine = bufferedReader.readLine();
            while (oneLine != null) {
                result += oneLine + "\n";
                oneLine = bufferedReader.readLine();
            }
            // using outside function to get all the data on each movie on the list
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray;
            jsonArray = jsonObject.getJSONArray("Search");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("Title");
                String imdbID = jsonObject.getString("imdbID");
                JSONObject movieInfo = findInfoById(imdbID);
                assert movieInfo != null;
                String poster = movieInfo.getString("Poster");
                String year = movieInfo.getString("Year");
                String plot = movieInfo.getString("Plot");
                String runTime = movieInfo.getString("Runtime");
                String imdbRate = movieInfo.getString("imdbRating");
                String genre = movieInfo.getString("Genre");

                Movie newMovie = new Movie(title, plot, imdbID, poster, year, runTime, imdbRate, genre,false);
                movieSearch.add(i, newMovie);
            }

            return movieSearch;
        } catch (Exception ex) {
            errorMessage = ex.getMessage();
            return null;
        }
    }

    // finding all the movies data using there id and return it
    private JSONObject findInfoById(String imdbID) throws IOException, JSONException {

        URL url = new URL("http://www.omdbapi.com/?i=" + imdbID);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        int httpStatusCode = connection.getResponseCode();

        if (httpStatusCode == HttpURLConnection.HTTP_BAD_REQUEST) {
            errorMessage = "No Such Symbol";
            return null;
        }
        if (httpStatusCode != HttpURLConnection.HTTP_OK) {
            errorMessage = connection.getResponseMessage();
            return null;
        }

        InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String result = "";

        String oneLine = bufferedReader.readLine();
        while (oneLine != null) {
            result += oneLine + "\n";
            oneLine = bufferedReader.readLine();
        }

        return new JSONObject(result);

    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movieSearchResult) {
        if (errorMessage !=null){
            callback.onError(errorMessage);
        }
        else {
            callback.onSuccess(movieSearchResult);
        }
    }
    // Using Callback Design Pattern in order to separate the AsyncTask from the UI
    public interface Callback{
        void onAboutToStart();
        void onSuccess(ArrayList<Movie> Result);
        void onError(String errorMessage);
    }
}
