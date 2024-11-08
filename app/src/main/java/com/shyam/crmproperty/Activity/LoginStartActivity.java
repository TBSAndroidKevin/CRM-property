package com.shyam.crmproperty.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.shyam.crmproperty.R;

public class LoginStartActivity extends AppCompatActivity {

    RelativeLayout st_login_in,st_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_start);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intView();

        clickListener();
    }

    private void clickListener() {

        st_login_in.setOnClickListener(v -> {
            startActivity(new Intent(LoginStartActivity.this,LoginActivity.class));

//            startActivity(new Intent(LoginStartActivity.this,MainActivity.class));
        });

        st_sign_up.setOnClickListener(v -> startActivity(new Intent(LoginStartActivity.this,SignUpActivity.class)));
    }

    private void intView() {

        st_login_in = findViewById(R.id.st_login_in);
        st_sign_up = findViewById(R.id.st_sign_up);

    }
}