package vieradesigns.fun_asteroid;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.TextView;


/**
 * Created by xavier on 12/18/15.
 */
public class Score extends TextView {

    private int w;
    private int h;
    private int TEXT_SIZE;
    private long points = 0;

    public Score(Context context, int screenHeight, int screenWidth){
        super(context);
        h = screenHeight;
        w = screenWidth;
        TEXT_SIZE = (int) (w * .015);


        String fPath = "fonts/atari.ttf";
        Typeface rf = Typeface.createFromAsset(context.getAssets(), fPath);
        this.setTypeface(rf);
        this.setTextSize(TEXT_SIZE);
        this.setTextColor(Color.WHITE);
    }

    public void addScore(int add){
        points = points + add;

    }

    public  void multiplyScore(int time){
        points = points + (points * 2);


    }

    public void saveScore(){

    }
    public void setName(){

    }

    public void displayScore(){
        points += 1;
        String f_points = String.format("%010d", points);
        this.setText(f_points);
        this.setVisibility(VISIBLE);

    }
    public long getScore(){

        return points;
    }


}
