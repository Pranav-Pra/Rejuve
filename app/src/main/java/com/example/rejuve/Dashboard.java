package com.example.rejuve;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class Dashboard extends AppCompatActivity {
    TextView heartTokensTV;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        heartTokensTV = findViewById(R.id.heartTokens);
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        firebaseHelper.getData(new FirebaseHelper.FirestoreCallback() {
            @Override
            public void onCallback(Paladin paladinGot) {
                String heartTokens = "" + paladinGot.getPoints();
                heartTokensTV.setText(heartTokens);
            }
        });
    }

    public void goToLeaderboard(View v) {
        Intent intent = new Intent(getApplicationContext(), Leaderboard.class);
        startActivity(intent);
    }
    public void goToExercise(View v) {
        Intent intent = new Intent(getApplicationContext(), Exercise.class);
        startActivity(intent);
    }
    public void goToNourishment(View v) {
        Intent intent = new Intent(getApplicationContext(), Nourishment.class);
        startActivity(intent);
    }

    public void signOut(View v) {
        FirebaseAuth.getInstance().signOut();

        Intent welcomeIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(welcomeIntent);
    }
}