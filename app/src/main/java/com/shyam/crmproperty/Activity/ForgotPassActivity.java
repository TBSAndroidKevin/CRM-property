package com.shyam.crmproperty.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.shyam.crmproperty.Other.Constants;
import com.shyam.crmproperty.R;

public class ForgotPassActivity extends AppCompatActivity {

    RelativeLayout fp_generate_otp;
    ImageView fp_back_btn;
    EditText fp_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_pass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intView();

        clickListener();
    }

    private void clickListener() {
        fp_back_btn.setOnClickListener(v -> finish());
        fp_generate_otp.setOnClickListener(v -> {
            if (!fp_email.getText().toString().trim().isEmpty()){
                generateOtp();
            }else {
                Toast.makeText(this, "Please Fill Email", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateOtp() {

        Intent intent = new Intent(this, OtpVerificationActivity.class);
        intent.putExtra(Constants.EMAIL,fp_email.getText().toString());
        startActivity(intent);

    }

    private void intView() {
        fp_generate_otp = findViewById(R.id.fp_generate_otp);
        fp_back_btn = findViewById(R.id.fp_back_btn);
        fp_email = findViewById(R.id.fp_email);
    }
}