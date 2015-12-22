package vieradesigns.fun_asteroid;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

public class Instruction extends Activity implements FragmentBtns.buttonClicked {
    FragmentOptions a = new FragmentOptions();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        if (findViewById(R.id.instruct_container) != null || findViewById(R.id.opt_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
        }

        getFragmentManager().beginTransaction().add(R.id.opt_container, a).commit();

        FragmentBtns fragment_buttons = new FragmentBtns();
        getFragmentManager().beginTransaction().add(R.id.instruct_container, fragment_buttons).commit();





    }


    @Override
    public void switchPane(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.opt_container, fragment, "something");
        transaction.commit();


    }
}
