package vieradesigns.fun_asteroid;

import android.app.Activity;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

public class GameOver extends Activity {
    private MediaPlayer spaceMusic = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int score = (int) intent.getLongExtra("GAMEOVER", 100);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_game_over);
        String endscore = String.valueOf(score);

        String fPath = "fonts/themachine.ttf";
        Typeface rf = Typeface.createFromAsset(getAssets(), fPath);



        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();
        ShareButton shareButton = (ShareButton)findViewById(R.id.button3);
        shareButton.setShareContent(content);
        shareButton.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        TextView scoreDisplay = (TextView) findViewById(R.id.endScore);
        scoreDisplay.setText("Your Score is: \n" + endscore);
        scoreDisplay.setTypeface(rf);
        scoreDisplay.setTextColor(Color.WHITE);
        scoreDisplay.setTextSize(50);
        scoreDisplay.setAllCaps(true);
        scoreDisplay.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
        spaceMusic = MediaPlayer.create(this, R.raw.intro);

        spaceMusic.setAudioStreamType(AudioManager.STREAM_MUSIC);
        spaceMusic.setLooping(true);
        spaceMusic.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
        spaceMusic.release();


    }
}
