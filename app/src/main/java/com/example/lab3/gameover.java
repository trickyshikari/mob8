package com.example.lab3;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class gameover extends AppCompatActivity {

    private FirebaseListAdapter<Result> adapter;
    private Bundle results;
    private int countTrue;
    private int countFalse;
    private double time;
    private String email;
    private TextView tvTime;
    private TextView tvTrue;
    private TextView tvFalse;
    private Button btnSend;
    private Button btnTry;
    private Button btnExit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        results = getIntent().getExtras();
        tvTime = findViewById(R.id.tvTime);
        tvTrue = findViewById(R.id.tvTrue);
        tvFalse = findViewById(R.id.tvFalse);
        btnSend = findViewById(R.id.btnSend);
        btnTry = findViewById(R.id.btnTry);
        btnExit = findViewById(R.id.btnExit);

        email = results.getString("email");
        time = results.getDouble("time");
        countTrue = results.getInt("correct");
        countFalse = results.getInt("wrong");

        tvTime.setText(String.valueOf(time));
        tvTrue.setText(String.valueOf(countTrue));
        tvFalse.setText(String.valueOf(countFalse));

        final TextView textview = findViewById(R.id.textView2);
        registerForContextMenu(textview);

        View.OnClickListener oclTry = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent result = new Intent(gameover.this, MainActivity.class);
                result.putExtra("email", email);
                startActivity(result);
            }
        };

        View.OnClickListener oclEmail = new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Результат игры");
                emailIntent.putExtra(Intent.EXTRA_TEXT,
                        "Время игры: " + String.format("%.2f", time) + " c.\n" +
                                "Правильных ответов: " + countTrue + "\n" +
                                "Неправильных ответов: " + countFalse + "\n");
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send email"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(gameover.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        View.OnClickListener oclExit = new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v){
                finishAffinity();
            }
        };

        btnTry.setOnClickListener(oclTry);
        btnSend.setOnClickListener(oclEmail);
        btnExit.setOnClickListener(oclExit);
        displayRes();
    }

    private void displayRes() {

        ListView listOfMessages = findViewById(R.id.list_of_results);

        //Log.d("debag123", "model.getNameQuiz()");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Results/null");
        FirebaseListOptions<Result> options =
                new FirebaseListOptions.Builder<Result>()
                        .setQuery(ref, Result.class)
                        .setLayout(R.layout.listitem_gameover)
                        .build();


        adapter = new FirebaseListAdapter<Result>(options) {
            @Override
            protected void populateView(View v, Result model, int position) {
                final TextView tvTime;
                final TextView tvTrueAns;
                final TextView tvFalseAns;

                tvTime = v.findViewById(R.id.timeRes);
                tvTrueAns = v.findViewById(R.id.trueRes);
                tvFalseAns = v.findViewById(R.id.falseRes);

                tvTime.setText(String.valueOf(model.getTime()));
                tvTrueAns.setText(String.valueOf(model.getTrueAns()));
                tvFalseAns.setText(String.valueOf(model.getFalseAns()));
            }

        };


        adapter.startListening();
        listOfMessages.setAdapter(adapter);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onContextItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case R.id.send:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Результат игры");
                emailIntent.putExtra(Intent.EXTRA_TEXT,
                        "Время игры: " + String.format("%.2f", time) + " c.\n" +
                                "Правильных ответов: " + countTrue + "\n" +
                                "Неправильных ответов: " + countFalse + "\n");
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send email"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(gameover.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.back:
                Intent result = new Intent(gameover.this, MainActivity.class);
                result.putExtra("email", email);
                startActivity(result);
                return true;
            case R.id.finish:
                finishAffinity();
                return true;
            default:
                return true;
        }
    }
}
