package com.example.nadavlotan.autodemo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

// Imports the Google Cloud client library


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextToSpeech textToSpeech;
    Socket mSocket;
    String serverText = "Welcome to ABC. How can I help you?";
    JSONObject params = new JSONObject();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toast.makeText(this, "You are located on Ayalon Darom (Route no. 20)!",
                Toast.LENGTH_LONG).show();

        TextView locationTextView = findViewById(R.id.locationInfoTextView);
        locationTextView.setText("Ayalon Darom (Route no. 20)");
        locationTextView.setVisibility(View.VISIBLE);

        ImageButton micImageButton = (ImageButton) findViewById(R.id.micImageButton);
        micImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpeechInput();
            }
        });

        final TextView enterTextView = (TextView) findViewById(R.id.enterTextView);

        Button sendTextButton = (Button) findViewById(R.id.sendTextButton);
        sendTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params.remove("userMessage");
                try {
                    params.put("userMessage", enterTextView.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mSocket.emit("userMessage", params);
                Toast.makeText(MapsActivity.this, "Emitted Succesfully", Toast.LENGTH_LONG).show();
            }
        });


        // Initialize and sets the TextFetcher socket to connect and wait for input
        TextFetcher textFetcher = new TextFetcher();
        mSocket = textFetcher.getmSocket();
        mSocket.on("serverMessage", onServerMessage);
        mSocket.connect();

//        try {
//            params.put("userMessage", "help");
////            params.put("userMessage", "helloooo");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        /*
//        TODO: Get text input from server, use tts and present the text + play the voice
//        TODO: Get user's input as a recorded voice, convert it to text and send to server
//        **/
//
//        mSocket.emit("userMessage", params);
//        Toast.makeText(MapsActivity.this, "Succesfully Sent", Toast.LENGTH_LONG).show();

        // Set and make the text to speech to output the serverText
//        ttsSpeak();


        Button yesButton = (Button) findViewById(R.id.answerButton);
        Button noButton = (Button) findViewById(R.id.declineButton);


        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakYes();
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakNo();
            }
        });
    }

    /**
     * When user presses No, continue the flow accordingly
     */
    public void speakNo() {
        MediaPlayer mp = MediaPlayer.create(MapsActivity.this, R.raw.no);
        mp.start();

        for (int i = 0; i < 2000000000; i++) {
            for (int j = 0; j < 0; j++) {
                int k = 0;
            }
        }

        mp = MediaPlayer.create(MapsActivity.this, R.raw.no_1);
        mp.start();
    }

    /**
     * When user presses Yes, continue the flow accordingly
     */
    public void speakYes() {
        for (int j = 0; j < 130000000; j++) {
            int k = 0;
        }
        MediaPlayer mp = MediaPlayer.create(MapsActivity.this, R.raw.yes);
        mp.start();

        for (int i = 0; i < 1500000000; i++) {
            for (int j = 0; j < 0; j++) {
                int k = 0;
            }
        }

        mp = MediaPlayer.create(MapsActivity.this, R.raw.yes_1);
        mp.start();

        for (int i = 0; i < 2100000000; i++) {
            for (int j = 0; j < 1; j++) {
                int k = 1;
            }
        }
        Intent intent = new Intent(MapsActivity.this, IncomingCall.class);
        startActivity(intent);
    }

    /**
     * Text To Speech - Speak
     * <p>
     * Initialize a text to speech android object and launch it to speak the serverText
     */
    public void ttsSpeak() {
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.UK);
                    ttsLang = textToSpeech.setPitch(0.7f);
                    ttsLang = textToSpeech.setSpeechRate(1f);


                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");

                    int speechStatus = textToSpeech.speak(serverText, TextToSpeech.QUEUE_FLUSH, null);

                    if (speechStatus == TextToSpeech.ERROR) {
                        Log.e("TTS", "Error in converting Text to Speech!");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoomLevel = 15.0f;

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(32.155573, 34.814040);
        mMap.addMarker(new MarkerOptions().position(location).title("Marker in your location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));
    }

    public void getSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TextView enterTextView = (TextView) findViewById(R.id.enterTextView);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    serverText = result.get(0);
                    enterTextView.setText(serverText);

                    Toast.makeText(this, "text successfully saved: " + serverText, Toast.LENGTH_SHORT).show();

                    params.remove("userMessage");
                    try {
                        params.put("userMessage", enterTextView.getText());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mSocket.emit("userMessage", params);
                    Toast.makeText(MapsActivity.this, "Emitted Succesfully", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    /**
     * OnServerMessage
     * <p>
     * Gets a message from the server for the event serverMessage and updated the serverText
     */
    private Emitter.Listener onServerMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            MapsActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    serverText = (String) args[0];
                    Toast.makeText(MapsActivity.this, serverText, Toast.LENGTH_LONG).show();
                    ttsSpeak();

                    for (int i = 0; i < 8000000; i++) {

                    }

//                    try {
//                        Thread.sleep(8000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }

                    getSpeechInput();


//                    try {
//                        params.remove("userMessage");
//
////                        getSpeechInput();
//
//                        params.put("userMessage", serverText);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    mSocket.emit("userMessage", params);
                }
            });
        }
    };
}