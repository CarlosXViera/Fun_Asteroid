package vieradesigns.fun_asteroid;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xavier on 12/7/15.
 */
public class FragmentOptions extends Fragment {

    public FragmentOptions(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View option_view = inflater.inflate(R.layout.fragment_opt, container,false);
        return option_view;
    }
}
