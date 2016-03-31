package com.example.dvircomp.project.AsyncTasks;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

//Taking url String and preform AsyncTask in order to show the image
public class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

    ImageView bmImage;

    public DownloadImageAsyncTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            urldisplay = "http://3.bp.blogspot.com/_P4XQc5WUw_U/TAYY2mhoDeI/AAAAAAAAAsI/i9H8QbDHLs0/s1600/photo_not_available_BW.jpg";
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception ex) {
                Log.e("Error", ex.getMessage());
                e.printStackTrace();
            }
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
