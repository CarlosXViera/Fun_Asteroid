package vieradesigns.fun_asteroid;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by xavier on 12/17/15.
 */
public class OnScreenObjects extends ImageView {

    private final Bitmap broken;
    private int n_ofObjects;
    private int h;
    private int w;
    private int velocity;
    private int i_w;
    private int i_h;
    private int health;
    private int ycollidedLocation,xcollidedLocation;
    private boolean collided = false;
    private boolean gameOver =false;
    private Bitmap _screenObjects, img, a_break, healthObjects, i;
    Context gameview;
    Canvas canvas;

    private ArrayList<Integer> randomY = new ArrayList<>();
    private ArrayList<Integer> randomX = new ArrayList<>();
    private ArrayList<Bitmap> s_objects = new ArrayList<>();
    private ArrayList<Boolean> hasBeenHit = new ArrayList<>();
    private ArrayList<Bitmap> healthBar = new ArrayList<>();
    private DimensionConverter gameDimensions;

    private final Resources res = getResources();


    private TypedArray a_images = getResources().obtainTypedArray(R.array.health);





    public OnScreenObjects(Context context, int numberOfObjects, int screenHeight, int screenWidth, int imageWidth, int imgHeight, int typedArrayResource) {
        super(context);
        gameview = context;

        n_ofObjects = numberOfObjects;
        h = screenHeight;
        w = screenWidth;
        i_w = imageWidth;
        i_h = imgHeight;
        TypedArray images = getResources().obtainTypedArray(typedArrayResource);

        gameDimensions = new DimensionConverter(screenWidth, screenHeight);




        //produce random y-coordinate
        for (int a = 0; a < n_ofObjects; a++) {
            int rand = (int) (Math.random() * (-3 * h));
            randomY.add(a, rand);
        }

        //produce random x-coordinate
        for (int b = 0; b < n_ofObjects; b++) {
            int rand = (int) (Math.random() * w);
            randomX.add(b, rand);
        }

        //create asteroids
        for (int c = 0; c < n_ofObjects; c++) {
            int choice = (int) (Math.random() * images.length());

            img = BitmapFactory.decodeResource(res, images.getResourceId(choice, R.drawable.a1));
            _screenObjects = Bitmap.createScaledBitmap(img, img.getScaledWidth(imageWidth), img.getScaledHeight(imgHeight), true);
            s_objects.add(c, _screenObjects);
        }

        //create array of false hasBeenHit
        for(int d = 0; d < n_ofObjects; d++){
            hasBeenHit.add(d, false);

        }

        //create break image
        a_break = BitmapFactory.decodeResource(res, R.drawable.a_break);
        broken = Bitmap.createScaledBitmap(a_break, a_break.getScaledWidth(imageWidth), a_break.getScaledHeight(imgHeight), true);

        //create health array
        for (int g = 0; g < 10; g++) {
            i = BitmapFactory.decodeResource(res, a_images.getResourceId(g, R.drawable.tmp_0));
            healthObjects = Bitmap.createScaledBitmap(i, i.getScaledWidth(gameDimensions.healthbarWidth), i.getScaledHeight(gameDimensions.healthbarHeight), true);
            healthBar.add(g, healthObjects);
        }



    }

    public void drawEnemy(int speed, Canvas _canvas) {
        canvas = _canvas;
        velocity = speed;

        //increases the speed for every asteroid in the list.
        for (int d = 0; d < n_ofObjects; d++) {
            _canvas.drawBitmap(s_objects.get(d), randomX.get(d), randomY.get(d) + speed, null);


        }
        //if wave of asteroids recreate and redraw
    }


    public void collision(int colliderY, int colliderX) {
        int x_boundingbox;
        int y_boundingbox;


        //detects if the bounding box of the image has collided with collider coordinates.
        for (int f = 0; f < n_ofObjects; f++) {
            x_boundingbox = i_w + randomX.get(f);
            y_boundingbox = i_h + randomY.get(f) + velocity;

            //if collided replaces with transparent image + hasBeenHit
            if ((colliderY <= y_boundingbox - i_h) && (x_boundingbox >= colliderX  && x_boundingbox <= colliderX + 300) && hasBeenHit.get(f) == false) {
                collided = true;
                ycollidedLocation = y_boundingbox;
                xcollidedLocation = x_boundingbox;

                s_objects.set(f, broken);

                hasBeenHit.set(f, true);

            }
        }
    }

    public boolean getCollided(){
        return collided;
    }


    public void setCollided(boolean setCollided){
        collided = setCollided;
    }

    protected void onHitHealthDepeletes(int ylocation, int _screenWidth, int _screenHeight, Canvas c) {

        if ((collided == true) && (ycollidedLocation >= ylocation)) {
            health++;
            healthBar.set(0, healthBar.get(health));

            if(health == 9){
                gameOver = true;
                health = 0;
            }

        }
        c.drawBitmap(healthBar.get(0), (float) (_screenWidth * .60), (float) (_screenHeight * .92), null);

    }

    public boolean getGameOver(){
        return gameOver;
    }




}
