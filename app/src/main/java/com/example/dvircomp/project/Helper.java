package com.example.dvircomp.project;


import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Helper {
    //Save all movie data to txt file
    public static void saveFile(Context context, ArrayList<Movie> movieList) {
        try {

            // Get the application directory path:
            String applicationDirectory = context.getFilesDir().getAbsolutePath();

            // Create a full path and file name:
            String fullPathAndfileName = applicationDirectory + "/MyMovies.txt"; // .txt is not needed.

            // Create a writer for writing a text file:
            FileWriter writer = new FileWriter(fullPathAndfileName);

            for (int i = 0; i < movieList.size(); i++) {

                String subject = movieList.get(i).getSubject();
                String body = movieList.get(i).getBody();
                String url = movieList.get(i).getUrl();
                String id = movieList.get(i).getId();
                String year = movieList.get(i).getYear();
                String runTime = movieList.get(i).getRunTime();
                String imdbRate = movieList.get(i).getRating();
                String genre = movieList.get(i).getGenre();
                String  watch = String.valueOf(movieList.get(i).isWatch());

                writer.write("[subject]" + "\r\n");
                writer.write(subject + "\r\n");

                writer.write("[body]" + "\r\n");
                writer.write(body + "\r\n");

                writer.write("[url]" + "\r\n");
                writer.write(url + "\r\n");

                writer.write("[id]" + "\r\n");
                writer.write(id + "\r\n");

                writer.write("[year]" + "\r\n");
                writer.write(year + "\r\n");

                writer.write("[runTime]" + "\r\n");
                writer.write(runTime + "\r\n");

                writer.write("[imdbRate]" + "\r\n");
                writer.write(imdbRate + "\r\n");

                writer.write("[genre]" + "\r\n");
                writer.write(genre + "\r\n");

                writer.write("[watch]" + "\r\n");
                writer.write(watch + "\r\n");

                writer.write("[END]" + "\r\n");

                Toast.makeText(context, "Saved!", Toast.LENGTH_LONG).show();
            }
            writer.close();

        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //Load all data from txt file
    public static void loadFile(Context context, ArrayList<Movie> movieList) {
        try {
            String subject = "";
            String body = "";
            String url = "";
            String id = "";
            String year = "";
            String runTime = "";
            String imdbRate = "";
            String genre = "";
            String watch = "";
            // Get the application directory path:
            String applicationDirectory = context.getFilesDir().getAbsolutePath();

            // Create a full path and file name:
            String fullPathAndfileName = applicationDirectory + "/MyMovies.txt";

            // Create a FileReader object, but it is very hard to work with it.
            FileReader fileReader = new FileReader(fullPathAndfileName);

            // Create a buffered reader for reading the data:
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String oneLine = bufferedReader.readLine();

            while (oneLine != null) {
                oneLine = bufferedReader.readLine();
                while (!oneLine.equals("[body]")) {
                    subject += oneLine + " ";
                    oneLine = bufferedReader.readLine();
                }
                oneLine = bufferedReader.readLine();

                while (!oneLine.equals("[url]")) {
                    body += oneLine + " ";
                    oneLine = bufferedReader.readLine();
                }
                oneLine = bufferedReader.readLine();
                while (!oneLine.equals("[id]")) {
                    url += oneLine + " ";
                    oneLine = bufferedReader.readLine();
                }
                oneLine = bufferedReader.readLine();
                while (!oneLine.equals("[year]")) {
                    id += oneLine + " ";
                    oneLine = bufferedReader.readLine();
                }
                oneLine = bufferedReader.readLine();

                while (!oneLine.equals("[runTime]")) {
                    year += oneLine + " ";
                    oneLine = bufferedReader.readLine();
                }
                oneLine = bufferedReader.readLine();
                while (!oneLine.equals("[imdbRate]")) {
                    runTime += oneLine + " ";
                    oneLine = bufferedReader.readLine();
                }
                oneLine = bufferedReader.readLine();
                while (!oneLine.equals("[genre]")) {
                    imdbRate += oneLine + " ";
                    oneLine = bufferedReader.readLine();
                }
                oneLine = bufferedReader.readLine();
                while (!oneLine.equals("[watch]")) {
                    genre += oneLine + " ";
                    oneLine = bufferedReader.readLine();
                }
                oneLine = bufferedReader.readLine();
                while (!oneLine.equals("[END]")) {
                    watch += oneLine + " ";
                    oneLine = bufferedReader.readLine();
                }
                boolean watchedValue = Boolean.parseBoolean(watch.trim());
                // Using trim in order to get rid of space
                movieList.add(new Movie(subject, body, id, url, year.trim(), runTime.trim(), imdbRate.trim(), genre,watchedValue));
                oneLine = bufferedReader.readLine();
                subject = "";
                body = "";
                url = "";
                id = "";
                year = "";
                runTime = "";
                imdbRate = "";
                genre = "";
                watch = "";
            }
            // Close all:
            bufferedReader.close();
            fileReader.close();
        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //Clear all data in txt file
    public static void clearFile(Context context) {
        try {
            // Get the application directory path:
            String applicationDirectory = context.getFilesDir().getAbsolutePath();

            // Create a full path and file name:
            String fullPathAndfileName = applicationDirectory + "/MyMovies.txt"; // .txt is not needed.

            // Create a writer for writing a text file:
            FileWriter writer = new FileWriter(fullPathAndfileName);
            writer.write("");
            writer.close();

        } catch (Exception ex) {
            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
        }
    }

}