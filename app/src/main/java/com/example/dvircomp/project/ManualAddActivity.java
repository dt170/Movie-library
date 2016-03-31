package com.example.dvircomp.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dvircomp.project.AsyncTasks.DownloadImageAsyncTask;

public class ManualAddActivity extends AppCompatActivity {

    private EditText editTextSubject;
    private EditText editTextBody;
    private EditText editTextURL;
    private EditText editTextYear;
    private EditText editTextRunTime;
    private EditText editTextGenre;
    private TextView textViewRate;
    private CheckBox checkBoxWatch;
    private RatingBar ratingBar;
    private int position;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_add);

        //Calling getIntentEditMovie function that receiving information from the movie you chose to edit .
        getIntentEditMovie();
        //Calling rating function
        OnClickListenerForStarRating();
        //Calling show Image -show image in the start
        showImageOnCreate();
    }

    // Closing this Activity and return to Main
    public void buttonCancel_onClick(View view) {
        finish();
    }

    // After pressing the ok button this function takes all the info and sent it to the Main activity using Intent.
    // It also checked if the user put a title (must do so in order to add or edit)
    // And if the user didnt entered a year it will show "N/A" as the Programmer I chosen to give the user the ability
    // to enter years from 0  to 9999 in order to give him freedom (imagination) to even add a movie from the feature or past .
        public void buttonOK_onClick(View view) {
        String subject = editTextSubject.getText().toString();
        if (subject.trim().matches("")) {
            Toast.makeText(this, "Enter a Title", Toast.LENGTH_LONG).show();
            editTextSubject.requestFocus();
        } else {
            String body = editTextBody.getText().toString();
            String url = editTextURL.getText().toString();
            String year = editTextYear.getText().toString();
            if (year.trim().matches("")) {
                year = "N/A";
            }
            String runTime = editTextRunTime.getText().toString();
            String genre = editTextGenre.getText().toString();
            String rate = textViewRate.getText().toString();
            boolean watch = checkBoxWatch.isChecked();

            Intent intent = new Intent();
            intent.putExtra("subject", subject);
            intent.putExtra("body", body);
            intent.putExtra("url", url);
            intent.putExtra("year", year);
            intent.putExtra("runTime", runTime);
            intent.putExtra("genre", genre);
            intent.putExtra("rate", rate);
            intent.putExtra("watch", watch);
            intent.putExtra("position", position);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    //Listener for start rating every touch give the values to the textView from there the information pass and saved
    //I did it in this way because i added this bonus only after i finished the project
    public void OnClickListenerForStarRating() {
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                float userRate = ratingBar.getRating();
                textViewRate.setText("" + userRate);
            }
        });
    }

    // Show the image that the user entered in the url text
    public void buttonShowImage_onClick(View view) {
        showImageOnCreate();
    }

    //Get Intent in order to edit movies and put there values in the correct place.
    private void getIntentEditMovie() {
        Intent edit = getIntent();

        String editSubject = edit.getStringExtra("subject");
        String editBody = edit.getStringExtra("body");
        String editUrl = edit.getStringExtra("url");
        String editYear = edit.getStringExtra("year");
        String editRunTime = edit.getStringExtra("runTime");
        String editGenre = edit.getStringExtra("genre");
        String editRate = edit.getStringExtra("rate");
        boolean editWatch = edit.getBooleanExtra("watch", false);
        position = edit.getIntExtra("position", 0);

        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextBody = (EditText) findViewById(R.id.editTextBody);
        editTextURL = (EditText) findViewById(R.id.editTextURL);
        editTextYear = (EditText) findViewById(R.id.editTextYear);
        editTextRunTime = (EditText) findViewById(R.id.editTextRunTime);
        editTextGenre = (EditText) findViewById(R.id.editTextGenre);
        textViewRate = (TextView) findViewById(R.id.TextViewRate);
        checkBoxWatch = (CheckBox) findViewById(R.id.checkBoxWatched);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        editTextSubject.setText(editSubject);
        editTextBody.setText(editBody);
        editTextURL.setText(editUrl);
        editTextYear.setText(editYear);
        editTextRunTime.setText(editRunTime);
        editTextGenre.setText(editGenre);
        textViewRate.setText(editRate);
        checkBoxWatch.setChecked(editWatch);
    }

    //Sharing all the text field(Strings value) of the movie
    public void imageButtonShare_onClick(View view) {

        String subject = editTextSubject.getText().toString();
        String body = editTextBody.getText().toString();
        String url = editTextURL.getText().toString();
        String year = editTextYear.getText().toString();
        String runTime = editTextRunTime.getText().toString();
        String genre = editTextGenre.getText().toString();
        String rate = textViewRate.getText().toString();
        boolean watch = checkBoxWatch.isChecked();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Subject: " + subject + "\nBody: " + body + "\nUrl: " + url + "\nYear: " +
                year + "\nRun time: " + runTime + "\nGenre: " + genre + "\nRating: " + rate + "\nWatch: " + watch);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    // Show the image that the user entered in the url text or show the default image.
    public void showImageOnCreate() {
        ImageView imageViewURL = (ImageView) findViewById(R.id.imageViewURL);
        TextView textViewURL = (TextView) findViewById(R.id.editTextURL);

        String imageURL = textViewURL.getText().toString();
        new DownloadImageAsyncTask(imageViewURL).execute(imageURL);
    }
}
