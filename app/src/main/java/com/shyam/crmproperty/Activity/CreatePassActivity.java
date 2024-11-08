package com.shyam.crmproperty.Activity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.shyam.crmproperty.R;

public class CreatePassActivity extends AppCompatActivity {

    ImageView cp_back_btn,cp_new_pass_btn,cp_conf_pass_btn;
    EditText cp_email_edittext,cp_new_password_edittext,cp_confirm_password_edittext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_pass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intView();

        clickListener();


//        cp_new_password_edittext.setEnabled(false);
//        cp_new_password_edittext.setTextColor(Color.parseColor("#808080"));
//        cp_new_password_edittext.setBackgroundColor(Color.parseColor("#F0F0F0"));

    }

    private void clickListener() {

        cp_back_btn.setOnClickListener(v -> {
            finish();
        });


        cp_new_password_edittext.setTransformationMethod(PasswordTransformationMethod.getInstance());

        cp_new_pass_btn.setOnClickListener(v -> {
            if (cp_new_password_edittext.getTransformationMethod() instanceof PasswordTransformationMethod) {
                cp_new_password_edittext.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                cp_new_pass_btn.setImageResource(R.drawable.ic_eye);
            } else {
                cp_new_password_edittext.setTransformationMethod(PasswordTransformationMethod.getInstance());
                cp_new_pass_btn.setImageResource(R.drawable.ic_eye_off);
            }
            cp_new_password_edittext.setSelection(cp_new_password_edittext.length());
        });


        cp_confirm_password_edittext.setTransformationMethod(PasswordTransformationMethod.getInstance());

        cp_conf_pass_btn.setOnClickListener(v -> {
            if (cp_confirm_password_edittext.getTransformationMethod() instanceof PasswordTransformationMethod) {
                cp_confirm_password_edittext.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                cp_conf_pass_btn.setImageResource(R.drawable.ic_eye);
            } else {
                cp_confirm_password_edittext.setTransformationMethod(PasswordTransformationMethod.getInstance());
                cp_conf_pass_btn.setImageResource(R.drawable.ic_eye_off);
            }
            cp_confirm_password_edittext.setSelection(cp_confirm_password_edittext.length());
        });

    }

    private void intView() {

        cp_back_btn = findViewById(R.id.cp_back_btn);

//        cp_email_edittext = findViewById(R.id.cp_email_edittext);
        cp_new_password_edittext = findViewById(R.id.cp_new_password_edittext);
        cp_confirm_password_edittext = findViewById(R.id.cp_confirm_password_edittext);


        cp_new_pass_btn = findViewById(R.id.cp_new_pass_btn);
        cp_conf_pass_btn = findViewById(R.id.cp_conf_pass_btn);
    }

}