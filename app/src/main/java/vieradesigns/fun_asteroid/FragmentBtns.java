package vieradesigns.fun_asteroid;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by xavier on 12/7/15.
 */
public class FragmentBtns extends Fragment implements View.OnClickListener {
    buttonClicked mCallback;
    Diff c = new Diff();
    FragmentOptions i = new FragmentOptions();
    int b;


    public FragmentBtns() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View btn_view = inflater.inflate(R.layout.fragment_btn, container, false);

        Button something = (Button) btn_view.findViewById(R.id.something);
        Button instructB = (Button) btn_view.findViewById(R.id.instructB);
        something.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = 1;
                switchit();
            }
        });
        instructB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = 2;
                switchit();
            }
        });
        return btn_view;



    }

    @Override
    public void onClick(View v) {

    }

    public interface buttonClicked {
        public void switchPane(Fragment fragment);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);


        try {
            mCallback = (buttonClicked) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement buttonCLicked");
        }
    }

    public void switchit(){


        if(b == 1) {

            mCallback.switchPane(c);
        }
        if(b==2){
            mCallback.switchPane(i);
        }
    }


}
