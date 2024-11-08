package com.shyam.crmproperty.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.shyam.crmproperty.Other.PrefHandler;
import com.shyam.crmproperty.R;

public class SplashScreen extends AppCompatActivity {

    PrefHandler prefHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intView();

        boolean isLogin = prefHandler.getIsLogin();

        new Handler().postDelayed(() -> {

            if (isLogin) {
                startActivity(new Intent(SplashScreen.this,MainActivity.class));
            }
            else{
                startActivity(new Intent(SplashScreen.this, LoginStartActivity.class));
            }

            finish();

        },2000);

    }

    private void intView() {
        prefHandler = new PrefHandler(this);
    }
}