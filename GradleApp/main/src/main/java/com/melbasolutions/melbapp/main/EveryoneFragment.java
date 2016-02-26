package com.melbasolutions.melbapp.main;


import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class EveryoneFragment extends Fragment {


    public EveryoneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_everyone, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Id's are hard coded.
        Button button = (Button) getView().findViewById(R.id.button_everyone_pim);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    HomeActivity home = (HomeActivity) getActivity();
                    if (home instanceof HomeActivity) {
                        home.addStreepje(3);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button button2 = (Button) getView().findViewById(R.id.button_everyone_sander);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    HomeActivity home = (HomeActivity) getActivity();
                    if (home instanceof HomeActivity) {
                        home.addStreepje(5);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
