package com.shyam.crmproperty.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.shyam.crmproperty.Other.Constants;
import com.shyam.crmproperty.R;

public class OtpVerificationActivity extends AppCompatActivity {

    ImageView ov_back_btn;
    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otp_verification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intView();

        clickListener();
    }

    private void clickListener() {

        ov_back_btn.setOnClickListener(v->finish());


    }

    private void intView() {

        ov_back_btn = findViewById(R.id.ov_back_btn);
        email = getIntent().getStringExtra(Constants.EMAIL);

        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();  // Testing Purpose only

    }
}