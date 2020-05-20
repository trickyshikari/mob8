package com.example.lab3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String email;
    private int millisCount;
    private boolean vsbAnswers;
    private int textSize;

    private int time = 0;
    private Button corAnswer;
    private TextView timerView;
    private TextView nameColor;
    private TextView viewColor;
    private ProgressBar progBar;
    private CountDownTimer timer;

    private Spinner spinView;
    private int countAns;
    private int countTrue;
    private int countFalse;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         Bundle args = getIntent().getExtras();
         if (args != null) {
            email = args.getString("email");
         }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activityMain, new StartFragment())
                    .commit();
        }
         millisCount = 10;
         textSize = 18;
         vsbAnswers = true;


         Button start = findViewById(R.id.btnStart);
         timerView = findViewById(R.id.tvTimer);
         spinView  = findViewById(R.id.spinner);
         nameColor = findViewById(R.id.tvNamecolor);
         viewColor = findViewById(R.id.tvViewcolor);
         //vsbAnswers = findViewById(R.id.ansView);
         progBar = findViewById(R.id.progressBar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ansView:
                item.setChecked(!item.isChecked());
                vsbAnswers = item.isChecked();
                return true;
            case R.id.item10:
            case R.id.item30:
            case R.id.item60:
                millisCount = Integer.parseInt(item.getTitle().toString());
                Toast.makeText(getApplicationContext(), "Время игры установлено на " + millisCount + " секунд!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.fontHeightMin:
                textSize = 18;
                return true;
            case R.id.fontHeightMax:
                textSize = 24;
                return true;
            default:
                return true;
        }
    }




    public String getEmail() {  return email; }

    public boolean getVsbAnswers() {  return vsbAnswers; }

    public int getMillisCount() {  return millisCount; }

    public int getTextSize() {  return textSize; }

//    public TextView getNameColor() {return nameColor;}
//
//    public TextView getViewColor() {return viewColor;}
//
//    public TextView getTimerView() {return timerView;}
//
//    public TextView getTimerView() {return timerView;}
}
