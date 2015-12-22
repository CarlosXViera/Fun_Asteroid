package vieradesigns.fun_asteroid;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xavier on 12/7/15.
 */
public class Diff extends Fragment {
    public Diff(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View diff_view = inflater.inflate(R.layout.fragment_credits, container,false);
        return diff_view;

    }
}
