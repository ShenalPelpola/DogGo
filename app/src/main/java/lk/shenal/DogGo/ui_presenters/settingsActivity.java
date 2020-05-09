package lk.shenal.DogGo.ui_presenters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import lk.shenal.DogGo.R;

public class settingsActivity extends AppCompatActivity {
    private Button mainPageButton;
    private Button soundButton;
    private Button exitButton;
    private MediaPlayer mediaPlayer;
    private boolean finished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mainPageButton = findViewById(R.id.mainPageButton);
        soundButton = findViewById(R.id.soundButton);
        exitButton = findViewById(R.id.exitButton2);
        mediaPlayer = MediaPlayer.create(this, R.raw.menu_button);


        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finished = true;
                Intent intent = new Intent(settingsActivity.this, MainPageActivity.class);
                intent.putExtra("exit", finished);
                startActivity(intent);
            }
        });
        mainPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent = new Intent(settingsActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });

        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settingsActivity.this, MainPageActivity.class);
                intent.putExtra("exit", finished);
                mediaPlayer.release();
            }
        });

    }
}
