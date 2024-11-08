package com.shyam.crmproperty.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.shyam.crmproperty.R;

public class SignUpActivity extends AppCompatActivity {

    ImageView su_back_btn,su_pass_btn,su_conf_pass_btn;
    TextView su_sign_in;
    RelativeLayout su_sign_up;
    EditText su_password_edittext,su_conf_password_edittext,su_user_edittext,su_email_edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intView();

        clickListener();

    }

    private void clickListener() {

        su_back_btn.setOnClickListener(v -> {
            finish();
        });

        su_sign_in.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
        });

        su_password_edittext.setTransformationMethod(PasswordTransformationMethod.getInstance());

        su_pass_btn.setOnClickListener(v -> {
            if (su_password_edittext.getTransformationMethod() instanceof PasswordTransformationMethod) {
                su_password_edittext.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                su_pass_btn.setImageResource(R.drawable.ic_eye);
            } else {
                su_password_edittext.setTransformationMethod(PasswordTransformationMethod.getInstance());
                su_pass_btn.setImageResource(R.drawable.ic_eye_off);
            }
            su_password_edittext.setSelection(su_password_edittext.length());
        });

        su_password_edittext.setTransformationMethod(PasswordTransformationMethod.getInstance());

        su_conf_pass_btn.setOnClickListener(v -> {
            if (su_conf_password_edittext.getTransformationMethod() instanceof PasswordTransformationMethod) {
                su_conf_password_edittext.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                su_conf_pass_btn.setImageResource(R.drawable.ic_eye);
            } else {
                su_conf_password_edittext.setTransformationMethod(PasswordTransformationMethod.getInstance());
                su_conf_pass_btn.setImageResource(R.drawable.ic_eye_off);
            }
            su_conf_password_edittext.setSelection(su_conf_password_edittext.length());
        });

        su_sign_up.setOnClickListener(v -> {

            String name = su_user_edittext.getText().toString().trim();
            String email = su_email_edittext.getText().toString().trim();
            String pass = su_password_edittext.getText().toString().trim();
            String pass_conf = su_conf_password_edittext.getText().toString().trim();

            signup(name,email,pass);
        });

    }

    private void signup(String name, String email, String password) {



    }

    private void intView() {

        su_back_btn = findViewById(R.id.su_back_btn);
        su_sign_in = findViewById(R.id.su_sign_in);
        su_pass_btn = findViewById(R.id.su_pass_btn);
        su_conf_pass_btn = findViewById(R.id.su_conf_pass_btn);

        su_password_edittext = findViewById(R.id.su_password_edittext);
        su_conf_password_edittext = findViewById(R.id.su_conf_password_edittext);
        su_user_edittext = findViewById(R.id.su_user_edittext);
        su_email_edittext = findViewById(R.id.su_email_edittext);

        su_sign_up = findViewById(R.id.su_sign_up);

    }


}