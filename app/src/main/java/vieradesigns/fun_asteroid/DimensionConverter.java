package vieradesigns.fun_asteroid;

import android.os.Bundle;

/**
 * Created by xavier on 12/21/15.
 */
public class DimensionConverter {

    int healthbarHeight;
    int healthbarWidth;
    double xxxhdpi = 1.00;
    double xxhdpi = .75;
    double xhdpi =.50;
    double mdpi = .25;
    double multiplier;
    int _Height;
    int _Width;
    int asteroid;
    int ship;
    int backgroundHeight;
    int backgroundWidth;



    public DimensionConverter(int width, int height){
        _Width = width;
        _Height = height;


        switch (width){
            case 1600: multiplier = xxxhdpi;
                break;
            case 1536: multiplier = xxxhdpi;
                break;
            case 1440: multiplier = xxxhdpi;
                break;
            case 1080: multiplier =xxhdpi;
                break;
            case 720: multiplier = xhdpi;
                break;
            case 768: multiplier =xhdpi;
                break;
            case 480: multiplier = mdpi;
                break;
            default: multiplier= mdpi;
        }

        asteroid = (int) (150 * multiplier);
        ship = (int) (300 * multiplier);
        backgroundHeight =  (int) (_Height * multiplier);
        backgroundWidth = (int) (_Width * multiplier);
        healthbarWidth = (int) (350 * multiplier);
        healthbarHeight =(int) (350 * multiplier);



    }

}
