package com.example.lab3;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;


public class StartFragment extends Fragment {
    Button btnStart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnStart = getView().findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = new GameFragment();
                FragmentManager fManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fTrans = fManager.beginTransaction();
                fTrans.replace(R.id.activityMain, frag);
                fTrans.addToBackStack(null);
                fTrans.commit();
            }
        });

    }
}
