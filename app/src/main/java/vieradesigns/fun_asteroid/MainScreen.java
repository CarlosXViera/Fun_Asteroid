package vieradesigns.fun_asteroid;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainScreen extends Activity {
    MediaPlayer introMusic = null;
    RelativeLayout origin = null;
    Resources res =null;
    Drawable bg = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        origin = (RelativeLayout) findViewById(R.id.main_screen_layout);
        res = getResources();
        bg = res.getDrawable(R.drawable.bg);
        origin.setBackground(bg);

        TextView title = (TextView) findViewById(R.id.title);
        String fPath = "fonts/teck.ttf";
        Typeface rf = Typeface.createFromAsset(getAssets(), fPath);
        title.setTypeface(rf);
        title.setText("deep space");
        title.setTextColor(Color.WHITE);
        title.setTextSize(70);
        title.setHighlightColor(Color.GREEN);


        Button option_btn = (Button) findViewById(R.id.opt_btn);
        option_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent instruction_page = new Intent(MainScreen.this, Instruction.class);
                startActivity(instruction_page);
            }
        });

        Button play_page =(Button) findViewById(R.id.play);
        play_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent play_page = new Intent(MainScreen.this, GameEnvironment.class);
                startActivity(play_page);
            }
        });

        Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory();
        Log.v("onCreate", "maxMemory:" + Long.toString(maxMemory));



    }

    @Override
    protected void onResume() {
        super.onResume();

        introMusic = MediaPlayer.create(MainScreen.this, R.raw.intro);
        introMusic.setAudioStreamType(AudioManager.STREAM_MUSIC);
        introMusic.start();
        introMusic.setLooping(true);


    }

    @Override
    protected void onPause() {
        super.onPause();
        introMusic.release();
        bg.invalidateSelf();



    }
}
