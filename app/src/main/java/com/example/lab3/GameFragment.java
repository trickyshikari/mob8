package com.example.lab3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GameFragment extends Fragment {
    private String stringColors[] = new String[] { "Черный", "Синий", "Зеленый", "Красный", "Жёлтый" };
    private int colors[] = new int[] { Color.BLACK, Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW };
    private int curColor;
    private int curColorName;
    private int time = 0;
    private Button corAnswer;
    private TextView timerView;
    private TextView nameColor;
    private TextView viewColor;
    private ProgressBar progBar;
    private CountDownTimer timer;
    private Button btnYes;
    private Button btnNo;

    private Spinner spinView;
    private int countAns;
    private int countTrue;
    private int countFalse;
    private int flag = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_game, container, false).getRootView();
        setHasOptionsMenu(false);



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progBar = getView().findViewById(R.id.progressBar);
        timerView = getView().findViewById(R.id.tvTimer);
        nameColor = getView().findViewById(R.id.tvNamecolor);
        viewColor = getView().findViewById(R.id.tvViewcolor);

        nameColor.setTextSize(((MainActivity)getActivity()).getTextSize());
        viewColor.setTextSize(((MainActivity)getActivity()).getTextSize());
        timerView.setTextSize(((MainActivity)getActivity()).getTextSize());

        View.OnClickListener ocl = new View.OnClickListener() {
            // Обработчик событий кнопок-ответов
            @Override
            public void onClick(View v) {

                if(v == corAnswer) {
                    countTrue++;
                    Toast.makeText(getActivity().getApplicationContext(), "Правильно!", Toast.LENGTH_SHORT).show();
                } else {
                    countFalse++;
                    Toast.makeText(getActivity().getApplicationContext(), "Неправильно!", Toast.LENGTH_SHORT).show();

                }

                generate();
            }
        };

        btnYes = getView().findViewById(R.id.btnYes);
        btnNo = getView().findViewById(R.id.btnNo);
        btnYes.setOnClickListener(ocl);
        btnNo.setOnClickListener(ocl);

        start();
    }

    private void start() {

        progBar.setMax(((MainActivity)getActivity()).getMillisCount());


        timer = new CountDownTimer(((MainActivity)getActivity()).getMillisCount() * 1000, 100) {

            public void onTick(long millisUntilFinished) {
                timerView.setText(millisUntilFinished / 1000 + "." + (millisUntilFinished % 1000) / 100);
                time -= 100;
                progBar.setProgress(progBar.getMax() - (int)(millisUntilFinished / 1000));
            }

            public void onFinish() {

                Result res = new Result(((MainActivity)getActivity()).getMillisCount(), countTrue, countFalse);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = database.getReference();

                databaseReference.child("Results/" + ((MainActivity)getActivity()).getEmail()).push().setValue(res, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    }
                });

                Intent result = new Intent(getActivity(), gameover.class);
                result.putExtra("time", Double.valueOf(((MainActivity)getActivity()).getMillisCount()));
                result.putExtra("correct", countTrue);
                result.putExtra("wrong", countFalse);
                result.putExtra("email", ((MainActivity)getActivity()).getEmail());
                countTrue = 0;
                countFalse = 0;
                countAns = 0;
                startActivity(result);
            }

        }.start();

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countAns++;
                if(v == corAnswer) {
                    countTrue++;
                    if(((MainActivity)getActivity()).getVsbAnswers())
                        Toast.makeText(getActivity().getApplicationContext(), "Правильно!", Toast.LENGTH_SHORT).show();
                } else
                {
                    countFalse++;
                    if(((MainActivity)getActivity()).getVsbAnswers())
                        Toast.makeText(getActivity().getApplicationContext(), "Неправильно!", Toast.LENGTH_SHORT).show();
                }

                generate();
            }
        };

        Button btnYes = getView().findViewById(R.id.btnYes),
                btnNo = getView().findViewById(R.id.btnNo);

        btnYes.setOnClickListener(onClickListener);
        btnNo.setOnClickListener(onClickListener);

        generate();
    }

    private void generate() {

        curColorName = (int)Math.floor(Math.random() * stringColors.length);
        curColor = colors[(int)Math.floor(Math.random() * colors.length)];
        TextView tvLeft = getView().findViewById(R.id.tvNamecolor);
        tvLeft.setText(stringColors[curColorName]);
        tvLeft.setTextColor(curColor);
        curColor = (int)Math.floor(Math.random() * colors.length);
        TextView tvRight = getView().findViewById(R.id.tvViewcolor);
        tvRight.setText(stringColors[(int)Math.floor(Math.random() * stringColors.length)]);
        tvRight.setTextColor(colors[curColor]);
        if (colors[curColorName] == colors[curColor])
            corAnswer = getView().findViewById(R.id.btnYes);
        else
            corAnswer = getView().findViewById(R.id.btnNo);
    }

}
