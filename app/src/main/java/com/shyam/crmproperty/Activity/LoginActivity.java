package com.shyam.crmproperty.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.shyam.crmproperty.API.RestClient;
import com.shyam.crmproperty.Other.ExtraFIle;
import com.shyam.crmproperty.Other.PrefHandler;
import com.shyam.crmproperty.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    PrefHandler prefHandler;
    String androidId, ip;
    ImageView lg_back_btn, lg_pass_btn;
    TextView lg_forgot_password, lg_sign_up;
    RelativeLayout lg_login_in;
    EditText lg_email_edittext, lg_password_edittext;
//    boolean allPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intView();

        ip = ExtraFIle.getIpAddress();
        androidId = ExtraFIle.getAndroidId(this);
        clickListener();

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
//        }else {
//            allPermission = true;
//        }

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
//        }


    }

    private void clickListener() {

        lg_back_btn.setOnClickListener(v -> {
            finish();
        });

        lg_forgot_password.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ForgotPassActivity.class));
        });

        lg_sign_up.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });

        lg_login_in.setOnClickListener(v -> {

        });

        lg_password_edittext.setTransformationMethod(PasswordTransformationMethod.getInstance());

        lg_pass_btn.setOnClickListener(v -> {
            if (lg_password_edittext.getTransformationMethod() instanceof PasswordTransformationMethod) {
                lg_password_edittext.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                lg_pass_btn.setImageResource(R.drawable.ic_eye);
            } else {
                lg_password_edittext.setTransformationMethod(PasswordTransformationMethod.getInstance());
                lg_pass_btn.setImageResource(R.drawable.ic_eye_off);
            }
            lg_password_edittext.setSelection(lg_password_edittext.length());
        });


        lg_login_in.setOnClickListener(v -> {

            String email = lg_email_edittext.getText().toString().trim();
            String password = lg_password_edittext.getText().toString().trim();

//            if (!isValidEmail(email)) {
//                setErrorWithoutIcon(lg_email_edittext, "Invalid email address");
//            } else if (!isValidPassword(password)) {
//                setErrorWithoutIcon(lg_password_edittext, "Password must be at least 8 characters long and include a number, a letter, and a special character");
//            } else {
//                Toast.makeText(this, "Validation Successful", Toast.LENGTH_SHORT).show();
//            }

            loginIn(email, password);
        });
    }

    private void loginIn(String email, String password) {

        Log.e("cedsgvgcfyseycdusfgfdssedse", email + "   " + password + "    " + ip + "     " + androidId);

        Call<ResponseBody> call = RestClient.post().login(email, password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");

//                    call.execute().body().string();

                    prefHandler.setACCESS_TOKEN("");
                    prefHandler.setACCOUNT_ID("");

//                    Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    if (status.equals("success")) {
////              GlobelElement.hideDialog(pd);
//
                        Intent intent;
//
//                        if (level_id.equals("1")) {


//                            String message = jsonObject.getString("message");
                        JSONObject dataObject = jsonObject.getJSONObject("user");
                        String api_key = dataObject.getString("api_key");
                        String email = dataObject.optString("email");
                        int account_id = dataObject.getInt("id");
//                            String level_id = dataObject.getString("level_id");
//
                        prefHandler.setIsLogin(true);
                        prefHandler.setACCESS_TOKEN(api_key);
                        prefHandler.setACCOUNT_ID("" + account_id);
                        prefHandler.setEMAIL(email);
                        prefHandler.setIS_ADMIN(false);
//
//                            Toast.makeText(LoginActivity.this, "User Login", Toast.LENGTH_SHORT).show();
//
//                        } else if (level_id.equals("20")) {
//
//                            prefHandler.setIsLogin(true);
//                            prefHandler.setACCESS_TOKEN(access_token);
//                            prefHandler.setACCOUNT_ID("" + account_id);
//                            prefHandler.setIS_ADMIN(true);
//
//                            Toast.makeText(LoginActivity.this, "Admin Login", Toast.LENGTH_SHORT).show();
//
//                        }else {
//                            Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
//                        }

                        intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (status.equals("error")) {
////                        GlobelElement.hideDialog(pd);
//
//                        Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
//                        startActivity(intent1);
//                        Toast.makeText(LoginActivity.this, jsonObject.getString("response"), Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this, "Invalid Username or Password !!", Toast.LENGTH_SHORT).show();
                        prefHandler.setIsLogin(false);
//                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException | JSONException e) {
//                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });

    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 1) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, you can retrieve the IMEI now
//                allPermission = true;
//            } else {
//                // Permission denied
//                allPermission = false;
//            }
//        }
//    }
//


//    private boolean isValidEmail(String email) {
//        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
//    }
//
//    private boolean isValidPassword(String password) {
//        // Password should be at least 8 characters long and contain at least one digit, one letter, and one special character
//        String passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).{8,}$";
//        return password.matches(passwordPattern);
//    }
//
//    private void setErrorWithoutIcon(EditText editText, String errorMessage) {
//        // Create a SpannableString to hold the error message
//        SpannableString errorText = new SpannableString(errorMessage);
//
//        // Set the error message using setError() but pass null for the Drawable
//        editText.setError(errorText, null);  // The null removes the icon
//    }


    private void intView() {

        prefHandler = new PrefHandler(this);

        lg_back_btn = findViewById(R.id.lg_back_btn);
        lg_forgot_password = findViewById(R.id.lg_forgot_password);
        lg_sign_up = findViewById(R.id.lg_sign_up);
        lg_login_in = findViewById(R.id.lg_login_in);
        lg_email_edittext = findViewById(R.id.lg_email_edittext);
        lg_password_edittext = findViewById(R.id.lg_password_edittext);
        lg_pass_btn = findViewById(R.id.lg_pass_btn);

    }
}