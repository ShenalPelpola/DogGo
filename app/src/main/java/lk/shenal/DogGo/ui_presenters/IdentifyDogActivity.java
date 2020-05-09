package lk.shenal.DogGo.ui_presenters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import lk.shenal.DogGo.R;
import lk.shenal.DogGo.models.DogBreed;
import lk.shenal.DogGo.models.RandomizerDogImage;

public class IdentifyDogActivity extends AppCompatActivity {
    private static final String KEY_IMAGE1 = "KEY_IMAGE1";
    private static final String KEY_IMAGE2 = "KEY_IMAGE2";
    private static final String KEY_IMAGE3 = "KEY_IMAGE3";
    private static final String KEY_BREED1 = "KEY_BREED1";
    private static final String KEY_BREED2 = "KEY_BREED2";
    private static final String KEY_BREED3 = "KEY_BREED3";
    private static final String KEY_SELECTED_BREED = "KEY_SELECTED_BREED";
    private static final String KEY_VALIDITY = "KEY_VALIDITY";
    private static final String KEY_ANSWER_SELECTED = "KEY_ANSWER_SELECTED";
    private RandomizerDogImage randomizerDogImage = new RandomizerDogImage();
    private ArrayList<DogBreed> threeRandomDogBreeds;
    private ImageView dogImageView1;
    private ImageView dogImageView2;
    private ImageView dogImageView3;
    private TextView answerTextView;
    private Button nextImageSetButton;
    private TextView titleTextView;
    private String selected_breed;
    private boolean switchValue;
    private CountDownTimer timer;
    private TextView showTimerTextView;
    private String image1;
    private String image2;
    private String image3;
    private String breed1;
    private String breed2;
    private String breed3;
    private String validity;
    private boolean answerSelected = false;
    private MediaPlayer mediaPlayer1;
    private MediaPlayer mediaPlayer2;
    private MediaPlayer mediaPlayer3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_dog);
        dogImageView1 = findViewById(R.id.dogImageView1);
        dogImageView2 = findViewById(R.id.dogImageView2);
        dogImageView3 = findViewById(R.id.dogImageView3);
        answerTextView = findViewById(R.id.displayAnswerTextView);
        nextImageSetButton = findViewById(R.id.nextImageSetButton);
        titleTextView = findViewById(R.id.titleTextView);
        showTimerTextView = findViewById(R.id.showTimerTextView);
        mediaPlayer1 = MediaPlayer.create(this, R.raw.menu_button);
        mediaPlayer2 = MediaPlayer.create(this, R.raw.correct);
        mediaPlayer3 = MediaPlayer.create(this, R.raw.incorrecct);


        Intent intent = getIntent();
        switchValue = intent.getExtras().getBoolean("switchValue");


        threeRandomDogBreeds = randomizerDogImage.getThreeUniqueRandomBreeds(); //create the arraylist with three unique breeds
        selected_breed = randomizerDogImage.generateUniqueBreedName(threeRandomDogBreeds); // select a random breed out of the three breeds
        titleTextView.setText(String.format("Which Image is a %s?", selected_breed));
        titleTextView.setVisibility(View.VISIBLE);
        setRandomImages(threeRandomDogBreeds);
        nextImageSetButton.setEnabled(false);

        timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String seconds = (millisUntilFinished / 1000 + 1) + "";
                showTimerTextView.setText(seconds);
                switch (seconds) {
                    case "10":
                        showTimerTextView.setTextColor(Color.parseColor("#228B22"));
                        break;
                    case "6":
                        showTimerTextView.setTextColor(Color.parseColor("#ffa500"));
                        break;
                    case "3":
                        showTimerTextView.setTextColor(Color.RED);
                        break;
                }
            }

            @Override
            public void onFinish() {
                showTimerTextView.setText("");
                answerTextView.setText(R.string.wrong);
                answerTextView.setTextColor(Color.RED);
                showTimerTextView.setVisibility(View.INVISIBLE);
                dogImageView1.setEnabled(false);
                dogImageView2.setEnabled(false);
                dogImageView3.setEnabled(false);
                nextImageSetButton.setEnabled(true);
            }
        };

        if(switchValue) {
            timer.start();
            showTimerTextView.setVisibility(View.VISIBLE);
        }


        dogImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(selected_breed,dogImageView1.getContentDescription().toString());
                dogImageView1.setEnabled(false);
                dogImageView2.setEnabled(false);
                dogImageView3.setEnabled(false);
                nextImageSetButton.setEnabled(true);
                answerSelected = true;
                timer.cancel();
            }
        });
        dogImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(selected_breed,dogImageView2.getContentDescription().toString());
                dogImageView1.setEnabled(false);
                dogImageView2.setEnabled(false);
                dogImageView3.setEnabled(false);
                nextImageSetButton.setEnabled(true);
                answerSelected = true;
                timer.cancel();
            }
        });
        dogImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(selected_breed,dogImageView3.getContentDescription().toString());
                dogImageView1.setEnabled(false);
                dogImageView2.setEnabled(false);
                dogImageView3.setEnabled(false);
                nextImageSetButton.setEnabled(true);
                answerSelected = true;
                timer.cancel();
            }
        });


        nextImageSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchValue) {
                    timer.start();
                    showTimerTextView.setVisibility(View.VISIBLE);
                }
                answerTextView.setText("");
                dogImageView1.setEnabled(true);
                dogImageView2.setEnabled(true);
                dogImageView3.setEnabled(true);
                nextImageSetButton.setEnabled(false);
                mediaPlayer1.start();
                threeRandomDogBreeds = randomizerDogImage.getThreeUniqueRandomBreeds();
                selected_breed = randomizerDogImage.generateUniqueBreedName(threeRandomDogBreeds);
                setRandomImages(threeRandomDogBreeds);
                titleTextView.setText(String.format("Which Image is a %s?", selected_breed));
            }
        });
    }
    //This method sets up the images and their contentDescription
    public void setRandomImages(ArrayList<DogBreed> threeBreeds){
        image1 = threeBreeds.get(0).getImageUrls().get(randomizerDogImage.getImageIndex(threeBreeds.get(0)));
        image2 = threeBreeds.get(1).getImageUrls().get(randomizerDogImage.getImageIndex(threeBreeds.get(0)));
        image3 = threeBreeds.get(2).getImageUrls().get(randomizerDogImage.getImageIndex(threeBreeds.get(0)));

        dogImageView1.setImageResource(getResources().getIdentifier(image1,"drawable", "lk.shenal.DogGo"));
        breed1 = threeBreeds.get(0).getBreedName();
        dogImageView1.setContentDescription(breed1);

        dogImageView2.setImageResource(getResources().getIdentifier(image2,"drawable", "lk.shenal.DogGo"));
        breed2 = threeBreeds.get(1).getBreedName();
        dogImageView2.setContentDescription(breed2);

        dogImageView3.setImageResource(getResources().getIdentifier(image3,"drawable", "lk.shenal.DogGo"));
        breed3 = threeBreeds.get(2).getBreedName();
        dogImageView3.setContentDescription(breed3);
    }
    //When a image is clicked this method is called it checks the selected breed to selected images's content description
    public void checkAnswer(String selectedBreed, String userImage){
        if(selectedBreed.equalsIgnoreCase(userImage)){
            validity = "CORRECT!";
            answerTextView.setText(R.string.correct);
            answerTextView.setTextColor(Color.parseColor("#228B22"));
            mediaPlayer2.start();
        }else{
            validity = "WRONG!";
            answerTextView.setText(R.string.wrong);
            answerTextView.setTextColor(Color.RED);
            mediaPlayer3.start();
        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_IMAGE1, image1);
        outState.putString(KEY_IMAGE2, image2);
        outState.putString(KEY_IMAGE3, image3);
        outState.putString(KEY_BREED1, breed1);
        outState.putString(KEY_BREED2, breed2);
        outState.putString(KEY_BREED3, breed3);
        outState.putString(KEY_SELECTED_BREED, selected_breed);
        outState.putString(KEY_VALIDITY, validity);
        outState.putBoolean(KEY_ANSWER_SELECTED, answerSelected);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        image1 = savedInstanceState.getString(KEY_IMAGE1);
        image2 = savedInstanceState.getString(KEY_IMAGE2);
        image3 = savedInstanceState.getString(KEY_IMAGE3);
        breed1 = savedInstanceState.getString(KEY_BREED1);
        breed2 = savedInstanceState.getString(KEY_BREED2);
        breed3 = savedInstanceState.getString(KEY_BREED3);
        selected_breed = savedInstanceState.getString(KEY_VALIDITY);
        answerSelected = savedInstanceState.getBoolean(KEY_ANSWER_SELECTED);
        validity = savedInstanceState.getString(KEY_VALIDITY);

        selected_breed =savedInstanceState.getString(KEY_SELECTED_BREED);
        dogImageView1.setImageResource(getResources().getIdentifier(image1,"drawable", "lk.shenal.DogGo"));
        dogImageView1.setContentDescription(breed1);

        dogImageView2.setImageResource(getResources().getIdentifier(image2,"drawable", "lk.shenal.DogGo"));
        dogImageView2.setContentDescription(breed2);

        dogImageView3.setImageResource(getResources().getIdentifier(image3,"drawable", "lk.shenal.DogGo"));
        dogImageView3.setContentDescription(breed3);
        if(validity != null) {
            answerTextView.setText(validity);
            if (validity.equals("CORRECT!")) {
                answerTextView.setTextColor(Color.parseColor("#228B22"));
            }else if(validity.equals("WRONG!")){
                answerTextView.setTextColor(Color.RED);
            }
        }
        if(answerSelected){
            dogImageView1.setEnabled(false);
            dogImageView2.setEnabled(false);
            dogImageView3.setEnabled(false);
            nextImageSetButton.setEnabled(true);
        }
        titleTextView.setText(String.format("Which Image is a %s?", selected_breed));
    }
}
