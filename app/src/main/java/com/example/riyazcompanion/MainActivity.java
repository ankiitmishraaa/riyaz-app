
package com.example.riyazcompanion;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    MediaPlayer tablaPlayer, tanpuraPlayer;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tablaPlayer = MediaPlayer.create(this, R.raw.teentaal);
        if(tablaPlayer == null){
            Log.e(TAG, "MediaPlayer.create() failed");
            Toast.makeText(this, "Error loading tabla sound", Toast.LENGTH_SHORT).show();
        }

        tanpuraPlayer = MediaPlayer.create(this, R.raw.tanpura_c);
        if(tanpuraPlayer == null){
            Log.e(TAG, "MediaPlayer.create() failed");
            Toast.makeText(this, "Error loading tanpura sound", Toast.LENGTH_SHORT).show();
        }

        Button playTabla = findViewById(R.id.playTabla);
        Button playTanpura = findViewById(R.id.playTanpura);

        playTabla.setOnClickListener(v -> {
            if (tablaPlayer != null) {
                try {
                    if (tablaPlayer.isPlaying()) {
                        tablaPlayer.pause(); // Or stop() and prepare() if you want to restart from beginning
                        tablaPlayer.seekTo(0); // If you want it to play from the start next time
                        // Update button text if needed, e.g., playTabla.setText("Play Tabla");
                    } else {
                        tablaPlayer.start();
                        // Update button text if needed, e.g., playTabla.setText("Pause Tabla");
                    }
                } catch (IllegalStateException e) {
                    Log.e(TAG, "IllegalStateException for tablaPlayer: " + e.getMessage());
                    // Handle the error, perhaps re-initialize the player
                    tablaPlayer = MediaPlayer.create(MainActivity.this, R.raw.teentaal);
                    if(tablaPlayer != null) tablaPlayer.start();
                }
            } else {
                Log.e(TAG, "tablaPlayer is null, cannot start.");
                Toast.makeText(this, "Cannot play tabla: player not loaded", Toast.LENGTH_SHORT).show();
            }
        });

        playTanpura.setOnClickListener(v -> {
            if (tanpuraPlayer != null) {
                try {
                    if (tanpuraPlayer.isPlaying()) {
                        tanpuraPlayer.pause();
                        tanpuraPlayer.seekTo(0);
                        // Update button text
                    } else {
                        tanpuraPlayer.start();
                        // Update button text
                    }
                } catch (IllegalStateException e) {
                    Log.e(TAG, "IllegalStateException for tanpuraPlayer: " + e.getMessage());
                    tanpuraPlayer = MediaPlayer.create(MainActivity.this, R.raw.tanpura_c);
                    if(tanpuraPlayer != null) tanpuraPlayer.start();
                }
            } else {
                Log.e(TAG, "tanpuraPlayer is null, cannot start.");
                Toast.makeText(this, "Cannot play tanpura: player not loaded", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer resources when the activity is destroyed
        if (tablaPlayer != null) {
            tablaPlayer.release();
            tablaPlayer = null;
        }
        if (tanpuraPlayer != null) {
            tanpuraPlayer.release();
            tanpuraPlayer = null;
        }
    }

    // It's also good practice to handle onStop or onPause to pause media
    @Override
    protected void onStop() {
        super.onStop();
        if (tablaPlayer != null && tablaPlayer.isPlaying()) {
            tablaPlayer.pause();
            // You might want to store the current position if you want to resume later
        }
        if (tanpuraPlayer != null && tanpuraPlayer.isPlaying()) {
            tanpuraPlayer.pause();
        }
    }
}