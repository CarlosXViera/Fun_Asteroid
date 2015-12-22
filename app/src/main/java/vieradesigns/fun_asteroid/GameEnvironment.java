package vieradesigns.fun_asteroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class GameEnvironment extends Activity implements SensorEventListener {


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    private GameView gameView = null;
    private static int x, fx, lastx, y, fy;
    private boolean tapped = false;
    private MediaPlayer spaceMusic = null, laser = null, blast = null;
    private Handler h;
    private final int FRAME_RATE = 30;
    private RelativeLayout layout;
    private Score score;
    private SensorManager accelerometer = null;
    public int _screenHeight, _screenWidth;
    private Intent end_page;
    private Display display;
    private Point size;

    private DimensionConverter gameDimensions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        _screenWidth = size.x;
        _screenHeight = size.y;

        gameDimensions = new DimensionConverter(_screenWidth, _screenHeight);

        gameView = new GameView(this);
        layout = new RelativeLayout(this);
        score = new Score(this, _screenHeight, _screenWidth);
        h = new Handler();
        accelerometer = (SensorManager) getSystemService(SENSOR_SERVICE);



        setContentView(gameView);


        layout.addView(score);

        gameView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tapped = true;
                        lastx = x + _screenWidth/2;
                        laser.start();
                    }
                });


    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            x -= 5 * (int) event.values[0];
            y += 5 * (int) event.values[1];
        }
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        accelerometer.registerListener(this, accelerometer.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        accelerometer.registerListener(this, accelerometer.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
        spaceMusic = MediaPlayer.create(GameEnvironment.this, R.raw.spacemusic);
        laser = MediaPlayer.create(GameEnvironment.this, R.raw.laser_beam);
        blast = MediaPlayer.create(GameEnvironment.this, R.raw.blast);
        spaceMusic.setAudioStreamType(AudioManager.STREAM_MUSIC);
        spaceMusic.setLooping(true);
        spaceMusic.start();
        gameView.setWillNotDraw(false);


    }


    @Override
    protected void onStop() {
        super.onStop();
        accelerometer.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public class GameView extends ImageView {
        private final DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        final Resources res = getResources();
        private BitmapDrawable fireball;
        private Bitmap ship;
        private Bitmap background;
        private Bitmap healthbar;
        int y_offset = -100;
        int y_offset2 = 0;
        int ship_ylocation = (int) (_screenHeight * .80);
        int ship_xlocation = (int) (_screenWidth * .50) - gameDimensions.ship/2;

        OnScreenObjects asteroids = new OnScreenObjects(GameEnvironment.this, 1000, _screenHeight *2, _screenWidth, gameDimensions.asteroid, gameDimensions.asteroid, R.array.asteroids_images);

        public GameView(Context context) {
            super(context);


            //instantiate bitmaps
            if(_screenWidth >= 1440) {
                background = decodeSampledBitmapFromResource(res, R.drawable.gamebg2, gameDimensions.backgroundWidth, gameDimensions.backgroundHeight);
            } else {
                background = decodeSampledBitmapFromResource(res, R.drawable.low, gameDimensions.backgroundWidth, gameDimensions.backgroundHeight);
            }
            ship = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.ship), gameDimensions.ship, gameDimensions.ship, false);
            fireball = (BitmapDrawable) res.getDrawable(R.drawable.fireball);


        }

        private Runnable r = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        @Override
        protected void onDraw(Canvas canvas) {


            y_offset2 += 1;
            y_offset += 6;

            canvas.drawBitmap(background, 0, -(1 * _screenHeight) + y_offset2, null);

            if (tapped == true) {
                fx = _screenWidth / 2;
                fy -= 300;
                canvas.drawBitmap(fireball.getBitmap(), lastx, fy, null);
            }
            if (fy < 0) {
                tapped = false;

            }

            if (tapped == false) {
                fy = _screenHeight - 350;


            }

            if (asteroids.getCollided() == true) {
                blast.start();

                asteroids.setCollided(false);
                score.addScore(5000);
                tapped = false;


            }


            canvas.drawBitmap(ship, x + ship_xlocation, ship_ylocation, null);


            score.displayScore();

            asteroids.drawEnemy(y_offset, canvas);
            asteroids.collision(fy, lastx);
            asteroids.collision(ship_ylocation, x + ship_xlocation);
            asteroids.onHitHealthDepeletes(ship_ylocation, _screenWidth, _screenHeight, canvas);

            layout.measure(_screenWidth, _screenHeight);
            layout.layout(100, 100, _screenWidth, _screenHeight);
            layout.draw(canvas);


            if(asteroids.getGameOver() == true){

                end_page = new Intent(GameEnvironment.this, GameOver.class);
                end_page.putExtra("GAMEOVER", score.getScore());
                startActivity(end_page);
                finish();


            }
            Log.d("ScreenHeight", "this" + _screenWidth);
            h.postDelayed(r, FRAME_RATE);


        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        spaceMusic.release();
        gameView.setWillNotDraw(true);

        fy = 0;


    }


}
