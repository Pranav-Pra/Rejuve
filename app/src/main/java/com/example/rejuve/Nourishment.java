package com.example.rejuve;

import static com.example.rejuve.MainActivity.firebaseHelper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Nourishment extends AppCompatActivity {
    public static final int dailyRequirement = 8;
    Button rewardButton;
    Button homeButton;
    Button logButton;
    TextView streakTV;
    TextView dailyTV;
    String streakOutput;
    String dailyOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nourishment);

        rewardButton = (Button) findViewById(R.id.reward);
        homeButton = (Button) findViewById(R.id.homeButton);
        logButton = (Button) findViewById(R.id.submit);

        rewardButton.setVisibility( View.GONE );
        homeButton.setVisibility( View.VISIBLE );
        logButton.setVisibility( View.VISIBLE );

        streakTV = findViewById(R.id.streak);
        dailyTV = findViewById(R.id.cupsLogged);

        // display
        streakOutput = "Streak: " + firebaseHelper.getPaladin().getStreak();
        streakTV.setText(streakOutput);
        dailyOutput = "Daily Progress: " + firebaseHelper.getPaladin().getDrinks();
        dailyTV.setText(dailyOutput);
    }

    public void refresh(View v){
        streakOutput = "Streak: " + firebaseHelper.getPaladin().getStreak();
        streakTV.setText(streakOutput);
        dailyOutput = "Daily Progress: " + firebaseHelper.getPaladin().getDrinks();
        dailyTV.setText(dailyOutput);
    }

    public void log(View v){
        EditText logET = findViewById(R.id.logET);
        String loggedCups = logET.getText().toString();
        Log.d("TEST", "In log(): \n" + firebaseHelper.getPaladin());
        int newDaily = firebaseHelper.getPaladin().getDrinks() + Integer.parseInt(loggedCups);
        // firebase method
        firebaseHelper.setDrinks(newDaily);
        // update display
        dailyOutput = "Daily Progress: " + newDaily;
        dailyTV.setText(dailyOutput);
        checkDaily(newDaily);
    }

    public void checkDaily(int total) {
        Log.d("TEST", "In checkDaily(): \n" + firebaseHelper.getPaladin());
        if (!firebaseHelper.getPaladin().isStreakIncremented() && total >= dailyRequirement) {
            Log.d("TEST", "In if: \n" + firebaseHelper.getPaladin());
            int currentStreak = firebaseHelper.getPaladin().getStreak();
            currentStreak++;
            streakOutput = "Streak: " + currentStreak;
            streakTV.setText(streakOutput);
            // firebase method
            firebaseHelper.setStreak(currentStreak);
            firebaseHelper.setStreakIncremented(true);
            checkStreak(currentStreak);
            Toast.makeText(Nourishment.this, "Congratulations on meeting your daily goal!", Toast.LENGTH_SHORT).show();

        } else if (total < dailyRequirement) {
            Toast.makeText(Nourishment.this, "You're on your way to reaching your daily goal!", Toast.LENGTH_SHORT).show();
        }
    }


    public void checkStreak(int streak){
        if (streak == 7){
            Toast.makeText(Nourishment.this, "Congratulations on your 7-day streak! Please proceed to claim your reward.", Toast.LENGTH_SHORT).show();
            firebaseHelper.setStreak(0);
            homeButton.setVisibility( View.GONE );
            rewardButton.setVisibility(View.VISIBLE);
            streakOutput = "Streak: 0";
        }
    }

    public void returnHome(View v){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    public void proceedToReward(View v){
        Intent intent = new Intent(this, Reward.class);
        startActivity(intent);
    }

}