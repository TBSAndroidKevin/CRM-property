package com.shyam.crmproperty.Activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.shyam.crmproperty.Other.PrefHandler;
import com.shyam.crmproperty.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {

    private TextView tvTitle;
    public PrefHandler preferenceHelper;
    public Dialog dialog;
    public Toolbar toolbar;
    public String inputDateTime = "dd-MM-yyyy || HH:mm";
    //public String inputDateTime_AmPm = "dd-MM-yyyy || hh:mm aa";
    public String outputDateTime = "yyyy-MM-dd HH:mm:ss";
    public String inputDate = "dd-MM-yyyy";
    public String outputDate = "yyyy-MM-dd";

    public final static int PERMISSION_ALL = 1;
    public String[] STORAGE_PERMISSIONS = {

            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };
    public String[] GALLAERY_PERMISSION = {
            Manifest.permission.READ_MEDIA_IMAGES,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceHelper = new PrefHandler(this);

    }

    public abstract void findViewById();

    public abstract void setListeners();

    protected abstract boolean isValidate();

    protected void initToolbar() {
        toolbar = findViewById(R.id.appToolbar);
        tvTitle = findViewById(R.id.tvToolbarTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void gotoLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void openProgressDialog() {
        // custom dialog
        if (dialog == null) {
            dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_progress);
        }
        dialog.show();
    }

    public void cancelProgressDialog() {
        // custom dialog
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    public ArrayAdapter<String> getSpinnerAdapter(ArrayList<String> spinnerArray) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.layout_spinner,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        return spinnerArrayAdapter;

    }


    public void openDatePicker(final EditText editText) {
        final Calendar myCalendar = Calendar.getInstance();
        if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
            myCalendar.set(Calendar.YEAR, Integer.parseInt(editText.getText().toString().split("-")[2]));
            myCalendar.set(Calendar.MONTH, Integer.parseInt(editText.getText().toString().split("-")[1]) - 1);
            myCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(editText.getText().toString().split("-")[0]));
        }

        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = inputDate;
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                editText.setText(sdf.format(myCalendar.getTime()));
            }
        }, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public int getSpinnerIndex(Spinner spinner, String myString) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i;
            }
        }
        return index;
    }



}
