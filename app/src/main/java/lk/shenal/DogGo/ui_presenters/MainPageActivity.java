package lk.shenal.DogGo.ui_presenters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import lk.shenal.DogGo.R;

public class MainPageActivity extends AppCompatActivity {
    private static final String SWITCH_KEY = "SWITCH_KEY";
    //Declaring the button variables
    private Button mIdentifyBreedButton;
    private Button mIdentifyDogButton;
    private Button mSearchBreedButton;
    private Switch timerSwitch;
    private boolean isSwitchOn = false;
    private MediaPlayer mediaPlayer;
    private ImageView settingsPageViewButton;
    private Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing the buttons with findViewById
        mIdentifyBreedButton = findViewById(R.id.identifyBreedButton);
        mIdentifyDogButton = findViewById(R.id.identifyDogButton);
        mSearchBreedButton = findViewById(R.id.searchBreedButton);
        timerSwitch = findViewById(R.id.timerSwitch);
        settingsPageViewButton = findViewById(R.id.settingsPageViewButton);
        mediaPlayer = MediaPlayer.create(this, R.raw.menu_button);
        exitButton = findViewById(R.id.exitButton);


        timerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mediaPlayer.start();
                if(isChecked){
                    isSwitchOn = true;
                }else{
                    isSwitchOn = false;
                }
            }
        });

        //setting onclick listeners
        mIdentifyBreedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayIdentifyBreed();
            }
        });

        mIdentifyDogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayIdentifyDog();
            }

        });

        mSearchBreedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySearchBreed();
            }

        });

        settingsPageViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySettings();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });

    }

    private void displayIdentifyBreed() {
        mediaPlayer.start();
        Intent intent = new Intent(this, IdentifyBreedActivity.class);
        intent.putExtra("switchValue", isSwitchOn);
        startActivity(intent);
    }

    private void displayIdentifyDog() {
        mediaPlayer.start();
        Intent intent = new Intent(this, IdentifyDogActivity.class);
        intent.putExtra("switchValue", isSwitchOn);
        startActivity(intent);
    }
    private void displaySearchBreed() {
        mediaPlayer.start();
        Intent intent = new Intent(this, SearchBreedActivity.class);
        startActivity(intent);
    }
    private void displaySettings() {
        mediaPlayer.start();
        Intent intent = new Intent(this, settingsActivity.class);
        startActivity(intent);
    }
    private void exit() {
        mediaPlayer.start();
        finish();
        System.exit(0);
    }
}
