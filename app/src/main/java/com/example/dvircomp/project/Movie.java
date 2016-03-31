package com.example.dvircomp.project;


public class Movie {

    private String subject;
    private String id;
    private String body;
    private String year;// I put this on string because omdb some times return string in the field example : 1999-2000
                        // but if the user will choose to edit this movie it will change to 1999 or to what values that the user entered
    private String url;
    private String runTime;// I put this on string because omdb some times return string in the field example : N/A
    private String rating;// I put this on string because omdb some times return string in the field example : N/A
    private String genre;
    private boolean watch;
    private static int uniqueID;

    public Movie(String subject, String body, String url, String year, String runTime, String genre, String rating, boolean watch) {
        this.subject = subject;
        this.body = body;
        this.url = url;
        this.year = year;
        this.runTime = runTime;
        this.rating = rating;
        this.genre = genre;
        this.watch = watch;
        uniqueID++;
        this.id = Integer.toString(uniqueID);
    }

    public Movie(String subject, String body, String id, String url, String year, String runTime, String rating, String genre, boolean watch) {
        this.subject = subject;
        this.url = url;
        this.body = body;
        this.id = id;
        this.runTime = runTime;
        this.year = year;
        this.watch = watch;
        this.rating = rating;
        this.genre = genre;
    }

    public boolean isWatch() {
        return watch;
    }

    public void setWatch(boolean watch) {
        this.watch = watch;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
            this.year = year;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return subject + " | " + year;
    }
}