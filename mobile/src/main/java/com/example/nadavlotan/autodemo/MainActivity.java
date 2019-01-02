package com.example.nadavlotan.autodemo;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    private static TextToSpeech mTTS_1;
    private static TextToSpeech mTTS_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTTS_1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS_1.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    }
                }
            }
        });
        mTTS_2 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS_2.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    }
                }
            }
        });

        Button hiddenBtn = findViewById(R.id.hiddenBtn);
        hiddenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

    }

    protected static void speak() {
        String text = "Hi Noa. I see that you had an accident. Don't worry, I'm here to help you!";
        float pitch = (float) 1.1;
        float speed = 0.8f;

        mTTS_1.setPitch(pitch);
        mTTS_1.setSpeechRate(speed);

        mTTS_1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        for (int i = 0; i < 3000; i++) {
            int a = 1;
        }
        speak2();
    }

    protected static void speak2() {
        String text = "I have saved all your information, and transferred it to MDA and the police! This is a test!";
        float pitch = (float) 1.1;
        float speed = 0.8f;

        mTTS_2.setPitch(pitch);
        mTTS_2.setSpeechRate(speed);

        mTTS_2.speak(text, TextToSpeech.QUEUE_FLUSH, null);

    }
}


