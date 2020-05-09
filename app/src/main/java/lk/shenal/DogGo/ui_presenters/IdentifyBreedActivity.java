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
import android.widget.Spinner;
import android.widget.TextView;
import lk.shenal.DogGo.R;
import lk.shenal.DogGo.models.DogBreed;
import lk.shenal.DogGo.models.RandomizerDogImage;

public class IdentifyBreedActivity extends AppCompatActivity{
    private static final String KEY_SELECTED_BREED_NAME = "KEY_SELECTED_BREED_NAME";
    private static final String KEY_SELECTED_BREED_IMAGE = "KEY_SELECTED_BREED_IMAGE";
    private RandomizerDogImage randomizerDogImage = new RandomizerDogImage();
    private TextView breedNameTextView;
    private TextView timerTextView;
    private TextView answerTextView;
    private Button breedSubmitButton;
    private Spinner spinner;
    private ImageView dogImageView;
    private CountDownTimer countDownTimer;
    private boolean switchValue;
    private String selectedBreedName;
    private String userSelectedBreed;
    private String selectedBreedImage;
    private MediaPlayer correctSound;
    private MediaPlayer incorrectSound;


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.putString(KEY_SELECTED_BREED_NAME, selectedBreedName);
        savedInstanceState.putString(KEY_SELECTED_BREED_IMAGE, selectedBreedImage);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        selectedBreedName = outState.getString(KEY_SELECTED_BREED_NAME);
        selectedBreedImage = outState.getString(KEY_SELECTED_BREED_IMAGE);
//        dogImageView.setImageResource(getResources().getIdentifier(selectedBreedImage,"drawable", "lk.shenal.DogGo"));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_breed);

        dogImageView = findViewById(R.id.dogBreedView);
        spinner = findViewById(R.id.breedsSpinner);
        breedSubmitButton = findViewById(R.id.breedSubmitButton);
        answerTextView = findViewById(R.id.answerTextView);
        breedNameTextView = findViewById(R.id.dogNameTextView);
        timerTextView = findViewById(R.id.timerTextView);
        correctSound = MediaPlayer.create(this, R.raw.correct);
        incorrectSound = MediaPlayer.create(this, R.raw.incorrecct);
        //This intent is used to get the value that was passed from the main activity.
        Intent intent = getIntent();
        switchValue = intent.getExtras().getBoolean("switchValue");


        initializeDog();//selectedBreedName is set to a randomly generated value from initializeDog method.

        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String seconds = (millisUntilFinished / 1000 + 1) + "";
                timerTextView.setText(seconds);
                switch (seconds) {
                    case "10":
                        timerTextView.setTextColor(Color.GREEN);
                        break;
                    case "6":
                        timerTextView.setTextColor(Color.parseColor("#ffa500"));
                        break;
                    case "3":
                        timerTextView.setTextColor(Color.RED);
                        break;
                }
            }

            @Override
            public void onFinish() {
                spinner.setEnabled(false);
                timerTextView.setText("");
                timerTextView.setVisibility(View.INVISIBLE);
                incorrectSound.start();
                breedSubmitButton.setBackgroundResource(R.drawable.next_button);
                answerTextView.setText(R.string.wrong);
                answerTextView.setTextColor(Color.RED);
                breedNameTextView.setText(String.format("%s!", selectedBreedName));
                breedSubmitButton.setText(R.string.next);
            }
        };

        //If the switch is on switchValue is equal to true else false. If the switch value is true the countdown timer starts.
        if(switchValue) {
            countDownTimer.start();
            timerTextView.setVisibility(View.VISIBLE);//Timer textView is displayed.
        }



        breedSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //When the button is clicked first it checks the text value of the button
                userSelectedBreed = spinner.getSelectedItem().toString();
                //If the button text is "SUBMII" the below code is executed.
                if(breedSubmitButton.getText().toString().equalsIgnoreCase("SUBMIT")) {
                    checkAnswer(selectedBreedName, userSelectedBreed);
                    breedSubmitButton.setText(R.string.next);
                    breedSubmitButton.setBackgroundResource(R.drawable.next_button);
                    timerTextView.setText("");
                    countDownTimer.cancel();
                }
                //Else if button text is "Next" the below code is executed.
                else if(breedSubmitButton.getText().toString().equalsIgnoreCase("NEXT")){
                    initializeDog();
                    breedSubmitButton.setText(R.string.submit);
                    breedSubmitButton.setBackgroundResource(R.drawable.button_background);
                    breedNameTextView.setText("");
                    answerTextView.setText("");
                    if(switchValue) {
                        countDownTimer.start();
                        timerTextView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    //To generate a new question this method is used.
    //When the app starts this method is called and when the submit button is clicked it is called as well
    public void initializeDog(){
        DogBreed breed = randomizerDogImage.getRandomDogBreed();
        spinner.setEnabled(true);
        selectedBreedImage = breed.getImageUrls().get(randomizerDogImage.getImageIndex(breed));
        dogImageView.setImageResource(getResources().getIdentifier(selectedBreedImage,"drawable", "lk.shenal.DogGo"));
        selectedBreedName = breed.getBreedName();
    }

    //This method checks the user's answer is correct or not.
    //This method takes two arguments selectedBreed which was generated by the initializeDog method
    //and userSelectedBreed which was selected by getting the spinner value
    public void checkAnswer(String selectedBreed, String userSelectedBreed){
        if(selectedBreed.equalsIgnoreCase(userSelectedBreed)){
            answerTextView.setText(R.string.correct);
            correctSound.start();
            answerTextView.setTextColor(Color.GREEN);

        }else{
            answerTextView.setText(R.string.wrong);
            incorrectSound.start();
            answerTextView.setTextColor(Color.RED);
        }
        spinner.setEnabled(false);//After checking the answer user cannot select a another answer hence it's disabled.
        timerTextView.setVisibility(View.INVISIBLE);
        breedNameTextView.setText(String.format("%s!", selectedBreed));//The correct answer is displayed to the user
    }
}
