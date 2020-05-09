package lk.shenal.DogGo.ui_presenters;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import java.util.ArrayList;

import lk.shenal.DogGo.R;
import lk.shenal.DogGo.models.DogBreed;
import lk.shenal.DogGo.models.DogData;
import lk.shenal.DogGo.models.RandomizerDogImage;

//This class the used to display the slide show
public class SearchBreedActivity extends AppCompatActivity {
    //The constant variables a used to store the value in onInstanceState
    private static final String KEY_IS_SLIDESHOW_RUNNING = "KEY_IS_SLIDESHOW_RUNNING";
    private static final String KEY_SELECTED_IMAGES = "KEY_SELECTED_IMAGES";
    private static final String KEY_SELECTED_IMAGE = "KEY_SELECTED_IMAGE";
    private DogData dogData = new DogData();
    private EditText searchBreedEditText;
    private Button submitSearchButton;
    private ArrayList<String> searchedImages;
    private Button stopSlideShowButton;
    private CountDownTimer slideShowTimer;
    private ImageView slideShowImageView;
    private RandomizerDogImage randomizerDogImage= new RandomizerDogImage();
    private String searchedBreed;
    private String selectedImage = "no_image_displayed";
    private boolean isSlideShowRunning = false;
    private boolean breedAvailable = false;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_IS_SLIDESHOW_RUNNING, isSlideShowRunning);
        outState.putStringArrayList(KEY_SELECTED_IMAGES, searchedImages);
        outState.putString(KEY_SELECTED_IMAGE, selectedImage);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isSlideShowRunning = savedInstanceState.getBoolean(KEY_IS_SLIDESHOW_RUNNING);
        searchedImages = savedInstanceState.getStringArrayList(KEY_SELECTED_IMAGES);
        updateButtons();
        if(isSlideShowRunning){
            selectedImage = savedInstanceState.getString(KEY_SELECTED_IMAGE);
            startSlideShow();
        }else{
            selectedImage = savedInstanceState.getString(KEY_SELECTED_IMAGE);
            slideShowImageView.setImageResource(getResources().getIdentifier(selectedImage,"drawable", "lk.shenal.DogGo"));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initialize the views and buttons
        setContentView(R.layout.activity_search_breed);
        searchBreedEditText = findViewById(R.id.searchBreedEditText);
        submitSearchButton = findViewById(R.id.submitSearchButton);
        stopSlideShowButton = findViewById(R.id.stopSlideShowButton);
        slideShowImageView = findViewById(R.id.slideShowImageView);
        mediaPlayer = MediaPlayer.create(this, R.raw.menu_button);
        //slideShowImage is set to "no_image_displayed" at the beginning.
        slideShowImageView.setImageResource(getResources().getIdentifier(selectedImage, "drawable", "lk.shenal.DogGo"));


        submitSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchedBreed = searchBreedEditText.getText().toString().trim(); //what user enters in the editText
                searchedImages = getSelectedImages(searchedBreed);
                mediaPlayer.start();
                if(!breedAvailable){
                    slideShowImageView.setImageResource(getResources().getIdentifier("image_not_available", "drawable", "lk.shenal.DogGo"));
                }else {
                    startSlideShow();
                }
            }
        });

        stopSlideShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                stopSlideShow();
            }
        });
    }
    //starts the slide show timer
    public void startSlideShow(){
        //side show is infinite
        slideShowTimer = new CountDownTimer(Integer.MAX_VALUE, 5000) {
            @Override
            public void onTick(long millisUntilFinished) {
                selectedImage = searchedImages.get(randomizerDogImage.getArraySize(searchedImages));
                slideShowImageView.setImageResource(getResources().getIdentifier(selectedImage, "drawable", "lk.shenal.DogGo"));
            }

            @Override
            public void onFinish() {

            }
        }.start();
        isSlideShowRunning = true;
        updateButtons();
    }
    //End the slideshow
    public void stopSlideShow(){
        slideShowTimer.cancel();
        isSlideShowRunning = false;
        updateButtons();
    }
    //update the button based on if the timer in running or not
    public void updateButtons(){
        if(isSlideShowRunning) {
            stopSlideShowButton.setEnabled(true);
            submitSearchButton.setEnabled(false);
            submitSearchButton.setAlpha(.5f);
            stopSlideShowButton.setAlpha(1);
        }else {
            submitSearchButton.setEnabled(true);
            stopSlideShowButton.setEnabled(false);
            submitSearchButton.setAlpha(1);
        }
    }

    //This method takes what user enters an argument.
    //if the breed is available all the images related to the breed is returned
    public ArrayList<String> getSelectedImages(String searchedBreed){
        ArrayList<DogBreed> dogBreeds = dogData.getDogBreeds();
        ArrayList<String> breedImages = new ArrayList<>();
        for(DogBreed dogBreed : dogBreeds){
            if(dogBreed.getBreedName().equalsIgnoreCase(searchedBreed)){
                breedImages = dogBreed.getImageUrls();
                breedAvailable = true;
            }
        }
        return breedImages;
    }
}
